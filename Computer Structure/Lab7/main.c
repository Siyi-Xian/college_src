/* main.c ---
*
* Filename: main.c
* Description:
* Author:
* Maintainer:
* Created: Thu Jan 10 11:23:43 2013
/* Code: */

#include <stm32f30x.h> // Pull in include files for F30x standard drivers
#include <f3d_uart.h>
#include <f3d_user_btn.h>
#include <f3d_lcd_sd.h>
#include <f3d_i2c.h>
#include <f3d_accel.h>
#include <f3d_mag.h>
#include <stdio.h>
#include <math.h>
#include <stdbool.h>

#define TIMER 20000
#define RADIAN 180 / M_PI

void tilt_visual(int pitch, int roll) {
  f3d_lcd_drawString(10, 10, "Tilt", BLUE, GREEN);

  int x = 63, y = 75, i, j;
  for (i = 0; i < 50; i++) {
    f3d_lcd_drawPixel(x, y + i, BLUE);
    f3d_lcd_drawPixel(x, y - i, BLUE);
    f3d_lcd_drawPixel(x + i, y, BLUE);
    f3d_lcd_drawPixel(x - i, y, BLUE);
  }
  f3d_lcd_drawString(x + 5, y - 50, "Roll", BLUE, GREEN);
  f3d_lcd_drawString(x + 30, y + 5, "Pitch", BLUE, GREEN);

  f3d_lcd_drawPixel(x - roll, y - pitch, BLUE);
}

void comp_visual(double head) {
  f3d_lcd_drawString(10, 10, "Compass", BLUE, GREEN);

  head = -head;

  int x = 63, y = 75, i;
  for (i = 0; i < 50; i++) {
    f3d_lcd_drawPixel(x + sin(head) * i, y + cos(head) * i, BLUE);
    f3d_lcd_drawPixel(x - sin(head) * i, y - cos(head) * i, BLUE);
  }

  delay(50);
  for (i = 0; i < 50; i++) {
    f3d_lcd_drawPixel(x + sin(head) * i, y + cos(head) * i, GREEN);
    f3d_lcd_drawPixel(x - sin(head) * i, y - cos(head) * i, GREEN);
  }
}

int main(void) {
   setvbuf(stdin, NULL, _IONBF, 0);
   setvbuf(stdout, NULL, _IONBF, 0);
   setvbuf(stderr, NULL, _IONBF, 0);

   f3d_uart_init();
   bool type = 1;

   f3d_lcd_init();
   f3d_lcd_fillScreen(GREEN);

   // Set up your inits before you go ahead
   f3d_i2c1_init();
   delay(10);
   f3d_accel_init();
   delay(10);
   f3d_mag_init();
   delay(10);

   while(1) {
     float accel_value[] = {0, 0, 0};
     f3d_accel_read(accel_value);
     double pitch_r, roll_r, head_r;
     pitch_r = atan(accel_value[0] / (sqrt(accel_value[1] * accel_value[1] + accel_value[2] * accel_value[2])));
     roll_r  = atan(accel_value[1] / (sqrt(accel_value[0] * accel_value[0] + accel_value[2] * accel_value[2])));
   
     float mag_value[] = {0, 0, 0};
     f3d_mag_read(mag_value);

     // head_r = atan(mag_value[1] / mag_value[0]);
     
     double x, y;
     x = mag_value[0] * cos(pitch_r) + mag_value[2] * sin(pitch_r);
     y = mag_value[0] * sin(roll_r) * sin(pitch_r) + mag_value[1] * cos(roll_r) - mag_value[2] * sin(roll_r) * cos(pitch_r);
     head_r = atan(x / y);
     
     int pitch, roll, head;
     pitch = (int) (pitch_r * RADIAN);
     roll  = (int) (roll_r  * RADIAN);
     head  = (int) (head_r  * RADIAN);
     printf("Pitch: %d, Roll: %d, Heading: %d\n", pitch, roll, head);

     if (user_btn_read() == (int) Bit_SET) {
       while (user_btn_read() == (int) Bit_SET);
       f3d_lcd_fillScreen(GREEN);
       type = !type;
     }

     if (type)
       tilt_visual(pitch, roll);
     else
       comp_visual(head_r);
   }

while(1);
}

#ifdef USE_FULL_ASSERT
void assert_failed(uint8_t* file, uint32_t line) {
/* Infinite loop */
/* Use GDB to find out why we're here */
  while (1);
}
#endif

/* main.c ends here */

