#include <stdbool.h>
#include <stm32f30x.h>  // Pull in include files for F30x standard drivers 
#include <f3d_led.h>     // Pull in include file for the local drivers
#include <f3d_user_btn.h>

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

int main(void) {
  /*
  GPIO_InitTypeDef GPIO_InitStructure;

  RCC_AHBPeriphClockCmd(RCC_AHBPeriph_GPIOE, ENABLE);
  GPIO_StructInit(&GPIO_InitStructure);
  GPIO_InitStructure.GPIO_Pin = GPIO_Pin_9;
  GPIO_InitStructure.GPIO_Mode = GPIO_Mode_OUT;
  GPIO_InitStructure.GPIO_OType = GPIO_OType_PP;
  GPIO_InitStructure.GPIO_Speed = GPIO_Speed_50MHz;
  GPIO_InitStructure.GPIO_PuPd = GPIO_PuPd_NOPULL;
  GPIO_Init(GPIOE, &GPIO_InitStructure);

  RCC_AHBPeriphClockCmd(RCC_AHBPeriph_GPIOA, ENABLE);
  GPIO_StructInit(&GPIO_InitStructure);
  GPIO_InitStructure.GPIO_Pin = GPIO_Pin_0;
  GPIO_InitStructure.GPIO_Mode = GPIO_Mode_IN;
  GPIO_InitStructure.GPIO_PuPd = GPIO_PuPd_NOPULL;
  GPIO_Init(GPIOA, &GPIO_InitStructure);

  while(1){
    if(GPIO_ReadInputDataBit(GPIOA,GPIO_Pin_0) == (uint8_t)Bit_SET){
      GPIOE->BSRR = GPIO_Pin_9;
    }
    else {
      GPIOE->BRR = GPIO_Pin_9;
    }
  }
  */
  
  /*
  GPIO_InitTypeDef GPIO_InitStructure;
  GPIO_StructInit(&GPIO_InitStructure);
  GPIO_InitStructure.GPIO_Pin = GPIO_Pin_9;
  GPIO_InitStructure.GPIO_Mode = GPIO_Mode_OUT;
  GPIO_InitStructure.GPIO_OType = GPIO_OType_PP;
  GPIO_InitStructure.GPIO_Speed = GPIO_Speed_50MHz;
  GPIO_InitStructure.GPIO_PuPd = GPIO_PuPd_NOPULL;
  RCC_AHBPeriphClockCmd(RCC_AHBPeriph_GPIOE, ENABLE);
  GPIO_Init(GPIOE, &GPIO_InitStructure);
  GPIOE->BSRR = GPIO_Pin_9;
  while (1);
  */
  
    int i = 1;
    bool led_on_off = 1;
    while (1) {
        if (user_btn_read() != (int) Bit_SET) {
	    if (i == 1)
	        led_on_off = !led_on_off;
            if (i == 8)
                i = 0;
            else {
                if (led_on_off)
                    f3d_led_on(i++);
                else
                    f3d_led_off(i++);
                delay();
            }
        }
        else {
            f3d_led_all_off();
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
