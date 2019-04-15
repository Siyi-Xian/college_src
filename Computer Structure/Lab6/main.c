/* main.c ---
*
* Filename: main.c
* Description: LCD
* Author: Siyi Xian
* Maintainer: Siyi Xian
/* Code: */

#include <stm32f30x.h> // Pull in include files for F30x standard drivers
#include <f3d_led.h> // Pull in include file for the local drivers
#include <f3d_uart.h>
#include <f3d_gyro.h>
#include <f3d_lcd_sd.h>
#include <stdio.h>

#define TIMER 20000

void draw_bar_chart(int *pos, uint16_t color) {
  int k = 0;
  while (k < 10) {
    int l = 0;
    while (l < 10)
      f3d_lcd_drawPixel(pos[1] + k, pos[0] + l++, color);
    k++;
  }
}

int main(void) {
  // If you have your inits set up, this should turn your LCD screen red
  f3d_lcd_init();
  f3d_uart_init();
  f3d_gyro_init();
  f3d_lcd_fillScreen(GREEN);
  char *text = "X: 00, Y:00";
  f3d_lcd_drawString(10, 10, text, BLUE, GREEN);

  int axis_value[3] = {50, 50, 50};

  while (1) {
    f3d_lcd_fillScreen(GREEN);  

    int i = 50;
    int j = 50;
    
     // get axis value
    float pfData[3] = {0, 0, 0};
    f3d_gyro_getdata(pfData);
    int k = -1;
    while (++k < 3)
      if (pfData[k] > 25 || pfData[k] < -25) axis_value[k] += pfData[k] / 50;
    
    text[3] = axis_value[0] / 10 + '0';
    text[4] = axis_value[0] % 10 + '0';
    text[9] = axis_value[1] / 10 + '0';
    text[10] = axis_value[1] % 10 + '0';

    f3d_lcd_drawString(10, 10, text, BLUE, GREEN); 

    draw_bar_chart(axis_value, RED);
    delay(30);
    
    printf ("X: %d, Y: %d\n", axis_value[0], axis_value[1]);
  }
}

#ifdef USE_FULL_ASSERT
void assert_failed(uint8_t* file, uint32_t line) {
/* Infinite loop */
/* Use GDB to find out why we're here */
  while (1);
}
#endif

/* main.c ends here */
