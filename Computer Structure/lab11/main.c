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
#include <f3d_led.h>     // Pull in include file for the local drivers
#include <f3d_uart.h>
#include <f3d_gyro.h>
#include <f3d_lcd_sd.h>
#include <f3d_i2c.h>
#include <f3d_accel.h>
#include <f3d_mag.h>
#include <f3d_nunchuk.h>
#include <f3d_rtc.h>
#include <f3d_systick.h>
#include <ff.h>
#include <diskio.h>
#include <math.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define TIMER 20000
#define AUDIOBUFSIZE 128

extern uint8_t Audiobuf[AUDIOBUFSIZE];
extern int audioplayerHalf;
extern int audioplayerWhole;

FATFS Fatfs;		/* File system object */
FIL fid;		/* File object */
BYTE Buff[512];		/* File read buffer */
int ret;

int ind = 0;
char fileName[3][15] = {"500hz.wav", "500hz-16.wav", "thermo.wav"};

struct ckhd {
  uint32_t ckID;
  uint32_t cksize;
};

struct fmtck {
  uint16_t wFormatTag;      
  uint16_t nChannels;
  uint32_t nSamplesPerSec;
  uint32_t nAvgBytesPerSec;
  uint16_t nBlockAlign;
  uint16_t wBitsPerSample;
};

void readckhd(FIL *fid, struct ckhd *hd, uint32_t ckID) {
  f_read(fid, hd, sizeof(struct ckhd), &ret);
  if (ret != sizeof(struct ckhd))
    exit(-1);
  if (ckID && (ckID != hd->ckID))
    exit(-1);
}

void die (FRESULT rc) {
  printf("Failed with rc=%u.\n", rc);
  while (1);
}

void drawSquare(int n, uint16_t col) {
  int i, j;
  for (i = 0; i < 5; i++)
    for (j = 0; j < 5; j++)
      f3d_lcd_drawPixel(10 + i, 10 + 10 * n + j, col);
}

void play() {
  FRESULT rc;			/* Result code */
  DIR dir;			/* Directory object */
  FILINFO fno;			/* File information object */
  UINT bw, br;
  unsigned int retval;
  int bytesread;

  
  
  printf("\nOpen %s\n", fileName[ind]);
  rc = f_open(&fid, fileName[ind], FA_READ);
    
  if (!rc) {    
    struct ckhd hd;
    uint32_t  waveid;
    struct fmtck fck;
    
    readckhd(&fid, &hd, 'FFIR');
    
    f_read(&fid, &waveid, sizeof(waveid), &ret);
    if ((ret != sizeof(waveid)) || (waveid != 'EVAW'))
      return;
    
    readckhd(&fid, &hd, ' tmf');
    
    f_read(&fid, &fck, sizeof(fck), &ret);
    if (hd.cksize != 16) {
      printf("extra header info %d\n", hd.cksize - 16);
      f_lseek(&fid, hd.cksize - 16);
    }
    
    /*    
	  printf("audio format 0x%x\n", fck.wFormatTag);
	  printf("channels %d\n", fck.nChannels);
	  printf("sample rate %d\n", fck.nSamplesPerSec);
	  printf("data rate %d\n", fck.nAvgBytesPerSec);
	  printf("block alignment %d\n", fck.nBlockAlign);
	  printf("bits per sample %d\n", fck.wBitsPerSample);
    */
    // now skip all non-data chunks !
    
    while(1){
      readckhd(&fid, &hd, 0);
      if (hd.ckID == 'atad')
	break;
      f_lseek(&fid, hd.cksize);
    }
    
    printf("Samples %d\n", hd.cksize);
    
    // Play it !
    
    // audioplayerInit(fck.nSamplesPerSec);
    
    f_read(&fid, Audiobuf, AUDIOBUFSIZE, &ret);
    hd.cksize -= ret;
    audioplayerStart();
    
    while (hd.cksize) {
      printf("%d", ind);
      int next = hd.cksize > AUDIOBUFSIZE/2 ? AUDIOBUFSIZE/2 : hd.cksize;
      if (audioplayerHalf) {
	if (next < AUDIOBUFSIZE/2)
	  bzero(Audiobuf, AUDIOBUFSIZE/2);
	f_read(&fid, Audiobuf, next, &ret);
	hd.cksize -= ret;
	audioplayerHalf = 0;
      }
      if (audioplayerWhole) {
	if (next < AUDIOBUFSIZE/2)
	  bzero(&Audiobuf[AUDIOBUFSIZE/2], AUDIOBUFSIZE/2);
	f_read(&fid, &Audiobuf[AUDIOBUFSIZE/2], next, &ret);
	hd.cksize -= ret;
	audioplayerWhole = 0;
      }
    }
    audioplayerStop();
    printf("\nClose the file.\n"); 
    rc = f_close(&fid);    
  }
}

int main(void) { 

  setvbuf(stdin, NULL, _IONBF, 0);
  setvbuf(stdout, NULL, _IONBF, 0);
  setvbuf(stderr, NULL, _IONBF, 0);

  f3d_uart_init();
  f3d_timer2_init();
  f3d_dac_init();
  f3d_delay_init();
  f3d_rtc_init();
  f3d_systick_init();
  f3d_lcd_init();
  f3d_i2c1_init();
  printf("I2C init\n");
  delay(100);
  
  nunchuk_t nun1;
  f3d_nunchuk_init();
  printf("INIT\n");
  delay(100);

  /*
  while(1) {
    f3d_lcd_fillScreen(WHITE);
    delay(1000);
    f3d_lcd_fillScreen(BLUE);
    delay(1000);
  }
  */
  
  f3d_lcd_fillScreen(WHITE);
  f3d_lcd_drawString(20, 10, "1. 500hz.wav", BLUE, WHITE);
  f3d_lcd_drawString(20, 20, "2. 500hz-16.wav", BLUE, WHITE);
  f3d_lcd_drawString(20, 30, "3. therom.wav", BLUE, WHITE);
  //ind = 2;
  //play();
  
  printf("Reset\n");
  
  f_mount(0, &Fatfs);/* Register volume work area */

  while(1) {
    //play();
    f3d_nunchuk_read(&nun1);
    //printf("jx: %u, jy: %u, ax: %u, ay: %u, az: %u, c: %u, z: %u\n", nun1.jx, nun1.jy, nun1.ax, nun1.ay, nun1.az, nun1.c, nun1.z);
    if (nun1.z) {
      while(nun1.z)
	f3d_nunchuk_read(&nun1);
      drawSquare(ind, WHITE);
      ind = (ind + 2) % 3;
      drawSquare(ind, BLUE);
      play();
      delay(1000);
    }else if (nun1.c) {
      while(nun1.c)
	f3d_nunchuk_read(&nun1);
      drawSquare(ind, WHITE);
      ind = (ind + 1) % 3;
      drawSquare(ind, BLUE);
      play();
      delay(1000);
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
