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
#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

// Simple looping delay function
void delay(void) {
  int i = 40000;
  while (i-- > 0) {
    asm("nop"); /* This stops it optimising code out */
    // stop delay when button clicked
    if (user_btn_read() == (int) Bit_SET)
      return;
  }
}

void led_on_off() {
  f3d_led_all_on();
  delay();
  f3d_led_all_off();
  delay();
}

void printf_led() {
  f3d_led_all_on();
  int i = 5;
  while (i-- > 0) {
    printf("Hello World! %d\n", i);
    if (user_btn_read() == (int) Bit_SET)
      return;
  }
  f3d_led_all_off();
  //delay();
  printf("\n\n\n\n");
}

int main() {
  f3d_uart_init();
  setvbuf(stdin, NULL, _IONBF, 0);
  setvbuf(stdout, NULL, _IONBF, 0);
  setvbuf(stderr, NULL, _IONBF, 0);

  bool type = 1;
  while (1) {
    if (type)
      led_on_off();
    else
      printf_led();
    if (user_btn_read() == (int) Bit_SET) {
      while (user_btn_read() == (int) Bit_SET);
      type = !type;
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
