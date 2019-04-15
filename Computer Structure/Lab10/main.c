/* main.c ---
*
* Filename: main.c
* Description: Debug printf
* Author: Siyi Xian
* Maintainer: Siyi Xian
* Created: Fri Mar 15 13:05 2019
/* Code: */
#include <stm32f30x.h>  // Pull in include files for F30x standard drivers 
#include <f3d_uart.h>     // Pull in include file for the local drivers
#include <f3d_gyro.h>
#include <f3d_led.h>
#include <f3d_user_btn.h>
#include <f3d_systick.h>
#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

int main() {
  f3d_uart_init();
  f3d_systick_init();
  setvbuf(stdin, NULL, _IONBF, 0);
  setvbuf(stdout, NULL, _IONBF, 0);
  setvbuf(stderr, NULL, _IONBF, 0);

  
  while (1) {
    putchar(getchar());
  }
}


#ifdef USE_FULL_ASSERT
void assert_failed(uint8_t* file, uint32_t line) {
/* Infinite loop */
/* Use GDB to find out why we're here */
  while (1);
}
#endif
