#include <stm32f30x.h>  // Pull in include files for F30x standard drivers 
#include <f3d_uart.h>     // Pull in include file for the local drivers
#include <stdio.h>

int main() {
  f3d_uart_init();
  setvbuf(stdin, NULL, _IONBF, 0);
  setvbuf(stdout, NULL, _IONBF, 0);
  setvbuf(stderr, NULL, _IONBF, 0);

  int c; 
  int line = 0, words = 0, characters = 0;
  
  while ((c = getchar()) != 0x1b) {
    if (c == ' ' || c == '\t' || c == '\r' || c == '\f' || c == '\a' || c == '\n')
      words++;
    if (c == '\n')
      line++;
    characters++;
  }

  
  //printf("hellooo\n");
  printf("line = %d, words = %d, characters = %d\n", line, words, characters);
}


#ifdef USE_FULL_ASSERT
void assert_failed(uint8_t* file, uint32_t line) {
/* Infinite loop */
/* Use GDB to find out why we're here */
  while (1);
}
#endif
