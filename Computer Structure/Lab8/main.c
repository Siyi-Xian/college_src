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
#include <f3d_nunchuk.h>
#include <stdio.h>
#include <math.h>
#include <stdbool.h>

#define TIMER 20000
#define RADIAN 180 / M_PI

void gory_visual(int pitch, int roll) {
  f3d_lcd_drawString(10, 10, "Gory", BLUE, GREEN);
  
  int k, l;
  for (k = 0; k < 10; k++)
    for (l = 0; l < 10; l++)
      f3d_lcd_drawPixel(pitch + k, roll + l, BLUE);
  delay(100);
  
  for (k = 0; k < 10; k++)
    for (l = 0; l < 10; l++)
      f3d_lcd_drawPixel(pitch + k, roll + l, GREEN);
}

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

void chuk_visual(unsigned short unx, unsigned short uny) {
  f3d_lcd_drawString(10, 10, "Chuk", BLUE, GREEN);

  int pitch = unx * 180 / 1024 - 90;
  int roll = uny * 180 / 1024 - 90;
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

int main(void) {
   setvbuf(stdin, NULL, _IONBF, 0);
   setvbuf(stdout, NULL, _IONBF, 0);
   setvbuf(stderr, NULL, _IONBF, 0);

   f3d_uart_init();
   printf("uart init\n");
   int type = 0;

   f3d_lcd_init();
   printf("LCD init\n");
   f3d_lcd_fillScreen(GREEN);

   // Set up your inits before you go ahead
   f3d_i2c1_init();
   printf("I2C init\n");
   delay(100);
   f3d_accel_init();
   printf("ACCEL init\n");
   delay(100);
   f3d_mag_init();
   printf("MAG init\n");
   delay(100);
   f3d_gyro_init();
   delay(100);

   nunchuk_t nun1;
   f3d_nunchuk_init();
   printf("INIT\n");
   delay(100);

   int axis_value[2] = {50, 50};

   char x_value[6] = "      ";
   char y_value[6] = "      ";

   while(1) {
     float pfData[3] = {0, 0, 0};
     f3d_gyro_getdata(pfData);
     int k;
     for (k = 0; k < 2; k++)
       if (pfData[k] > 25 || pfData[k] < -25) axis_value[k] += pfData[k] / 50;
     printf("%d, %d\n", axis_value[0], axis_value[1]);

     f3d_nunchuk_read(&nun1);
     //printf("jx: %u, jy: %u, ax: %u, ay: %u, az: %u, c: %u, z: %u\n", nun1.jx, nun1.jy, nun1.ax, nun1.ay, nun1.az, nun1.c, nun1.z);

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
     head_r = atan2(x,y);
     
     int pitch, roll, head;
     pitch = (int) (pitch_r * RADIAN);
     roll  = (int) (roll_r  * RADIAN);
     head  = (int) (head_r  * RADIAN);
     //printf("Pitch: %d, Roll: %d, Heading: %d\n", pitch, roll, head);

     if (user_btn_read() == (int) Bit_SET) {
       while (user_btn_read() == (int) Bit_SET);
       f3d_lcd_fillScreen(GREEN);
       type = (type + 1) & 3;
     }

     if (nun1.c || nun1.jx > 192) {
       while (nun1.c || nun1.jx > 192)
	 f3d_nunchuk_read(&nun1);
       f3d_lcd_fillScreen(GREEN);
       type = (type + 1) & 3;
     }

     if (nun1.z || nun1.jx < 64) {
       while (nun1.z || nun1.jx < 64)
	 f3d_nunchuk_read(&nun1);
       f3d_lcd_fillScreen(GREEN);
       type = (type + 3) & 3;
     }

     switch (type) {
     case 0:
       gory_visual(axis_value[0], axis_value[1]);
       break;
     case 1:
       tilt_visual(pitch, roll);
       break;
     case 2:
       comp_visual(head_r);
       break;
     case 3:
       chuk_visual(nun1.ax, nun1.ay);
       break;
     default:
       break;
     }
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

