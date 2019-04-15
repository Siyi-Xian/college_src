/* main.c --- 
 * 
 * Filename: main.c
 * Description: 
 * Author: 
 * Maintainer: 
 * Created: Thu Jan 10 11:23:43 2013
 * Last-Updated: 
 *           By: 
 *     Update #: 0
 * Keywords: 
 * Compatibility: 
 * 
 */

/* Commentary: 
 * 
 * 
 * 
 */

/* Change log:
 * 
 * 
 */
/* Code: */

#include <stm32f30x.h>  // Pull in include files for F30x standard drivers 
#include <f3d_led.h>
#include <f3d_uart.h>
#include <f3d_user_btn.h>
#include <f3d_gyro.h>
#include <f3d_lcd_sd.h>
#include <f3d_i2c.h>
#include <f3d_accel.h>
#include <f3d_mag.h>
#include <f3d_nunchuk.h>
#include <f3d_rtc.h>
#include <ff.h>
#include <diskio.h>
#include <stdio.h>
#include <fcntl.h>
#include <stdio.h>
#include <math.h>
#include "bmp.h"

struct bmpfile_magic magic;
struct bmpfile_header header;
BITMAPINFOHEADER info;
struct bmppixel image;

#define RADIAN 180 / M_PI

void die (FRESULT rc) {
  printf("Failed with rc=%u.\n", rc);
  while (1);
}

char *filenames[] = {"BMW.BMP", "A.BMP", "1.BMP"};

char footer[20];
int count=0;
int i;

int next = 0, pre = 0;

FRESULT rc;			/* Result code */
DIR dir;			/* Directory object */
FILINFO fno;			/* File information object */
UINT bw, br;
unsigned int retval;

FATFS Fatfs;		/* File system object */
FIL Fil;		/* File object */
BYTE Buff[128];		/* File read buffer */

void display(char *filename, int swap) {
  printf("\nOpen an existing file (%s).\n", filename);
  rc = f_open(&Fil, filename, FA_READ);
  if (rc) die(rc);
  
  printf("\nType the file content.\n");
  f_read(&Fil, &magic, 2, &br);
  printf("Magic %c%c\n", magic.magic[0], magic.magic[1]);
  f_read(&Fil, &header, sizeof header, &br);
  printf("file size %d offset %d\n", header.filesz, header.bmp_offset);
  f_read(&Fil, &info, sizeof info, &br);
  printf("Width %d Height %d, bitspp %d\n", info.width, info.height, info.bitspp);
  
  f3d_lcd_fillScreen(BLACK);
  uint8_t x = 0, y = 0;
  uint16_t color = 0;
  for (;x < 128 && y < 160;) {
    rc = f_read(&Fil, &image, sizeof image, &br);
    if (rc || !br)
      break;
    color += image.b & 0b11111;
    color <<= 6;
    color += image.g & 0b111111;
    color <<= 5;
    color += image.r & 0b11111;
    if (!swap)
      f3d_lcd_drawPixel(x, 160 - y, color);
    else
      f3d_lcd_drawPixel(160 - y, 128 - x, color);
    x++;
    y += x / 128;
    x %= 128;
    //printf("x: %u, y: %u, R: %u, G: %u, B: %u, color: %x\n", x, y, image.r, image.g, image.b, color);
  }
}

int main(void) { 
  setvbuf(stdin, NULL, _IONBF, 0);
  setvbuf(stdout, NULL, _IONBF, 0);
  setvbuf(stderr, NULL, _IONBF, 0);
  
  f3d_uart_init();
  f3d_lcd_init();
  f3d_delay_init();
  f3d_rtc_init();
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
  
  f_mount(0, &Fatfs);		/* Register volume work area (never fails) */

  int index = 0;
  int swap = 0;
  display(filenames[index], swap);
  while(1) {
    f3d_nunchuk_read(&nun1);
    //printf("jx: %u, jy: %u, ax: %u, ay: %u, az: %u, c: %u, z: %u\n", nun1.jx, nun1.jy, nun1.ax, nun1.ay, nun1.az, nun1.c, nun1.z);
     if (nun1.z) {
      index = (index + 2) % 3;
      display(filenames[index], swap);
    }
    
    if (nun1.c) {
      index = (index + 1) % 3;
      display(filenames[index], swap);
    }

    if (nun1.jx > 192) {
      f3d_lcd_drawString(10, 10, "NEXT", RED, WHITE);
      next = 1;
    }
    if (nun1.jx < 64) {
      f3d_lcd_drawString(10, 10, "PRE", RED, WHITE);
      pre = 1;
    }

    if (user_btn_read() == (int) Bit_SET && next) {
      index = (index + 1) % 3;
      display(filenames[index], swap);
      next = 0;
    }

    if (user_btn_read() == (int) Bit_SET && pre) {
      index = (index + 2) % 3;
      display(filenames[index], swap);
      pre = 0;
    }

    float accel_value[] = {0, 0, 0};
    f3d_accel_read(accel_value);
    double pitch_r, roll_r, head_r;
    pitch_r = atan(accel_value[0] / (sqrt(accel_value[1] * accel_value[1] + accel_value[2] * accel_value[2])));
    roll_r  = atan(accel_value[1] / (sqrt(accel_value[0] * accel_value[0] + accel_value[2] * accel_value[2])));

    int pitch, roll;
    pitch = (int) (pitch_r * RADIAN);
    roll  = (int) (roll_r  * RADIAN);
    if (pitch < -20 && swap) {
      swap = 0;
      display(filenames[index], swap);
    }
    if (roll < -20 && !swap) {
      swap = 1;
      display(filenames[index], swap);
    }
    printf("%d, %d\n", pitch, roll);
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
