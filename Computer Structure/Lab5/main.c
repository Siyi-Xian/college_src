#include <stm32f30x.h>  // Pull in include files for F30x standard drivers 
#include <f3d_uart.h>     // Pull in include file for the local drivers
#include <f3d_gyro.h>
#include <f3d_led.h>
#include <f3d_user_btn.h>
#include <stdio.h>
#include <stdlib.h>

static char AXIS_NAME[3] = {'X', 'Y', 'Z'};
static int WEST_SEQU[4] = {0, 7, 6, 5};
static int EAST_SEQU[4] = {2, 3, 4, 5};
static int STEP = 1000;

void open_led(int axis_val, int *led_sequ) {
  f3d_led_all_off();
  f3d_led_on(1);

  int i = 0;
  while (axis_val > STEP * (i + 1) && i < 4)
    f3d_led_on(led_sequ[i++]);
}

int main() {
  f3d_uart_init();
  f3d_gyro_init();
  setvbuf(stdin, NULL, _IONBF, 0);
  setvbuf(stdout, NULL, _IONBF, 0);
  setvbuf(stderr, NULL, _IONBF, 0);

  /*
  while (1) {
    float pfData[3] = {0.1, 0.2, 0.3};
    f3d_gyro_getdata(pfData);
    printf("%f, %f, %f\n", pfData[0], pfData[1], pfData[2]);
  }
  */
  
  
  int axis = 0; // 0 - X, 1 - Y, 2 - Z
  int axis_value[3] = {0, 0, 0};

  char axis_char = getchar();
  switch(axis_char) {
  case 'X' :
    axis = 0;
    break;
  case 'Y' :
    axis = 1;
    break;
  case 'Z' :
    axis = 2;
    break;
  default:
    axis = axis;
    break;
  }

  while (1) {
    if (user_btn_read() == (int) Bit_SET) {
      while (user_btn_read() == (int) Bit_SET);
      axis = (axis + 1) % 3;
    }

    // get axis value
    float pfData[3] = {0, 0, 0};
    f3d_gyro_getdata(pfData);
    int i = -1;
    while (++i < 3)
      if (pfData[i] > 25 || pfData[i] < -25) axis_value[i] += pfData[i];

    printf("Axis %c: %d", AXIS_NAME[axis], axis_value[axis]);

    if (axis_value[axis] > 5)
      open_led(axis_value[axis], WEST_SEQU);
    else if (axis_value[axis] < -5)
      open_led(abs(axis_value[axis]), EAST_SEQU);
    else
      f3d_led_all_off();

    printf("\n");
  }
}


#ifdef USE_FULL_ASSERT
void assert_failed(uint8_t* file, uint32_t line) {
/* Infinite loop */
/* Use GDB to find out why we're here */
  while (1);
}
#endif
