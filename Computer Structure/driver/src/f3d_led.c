/************************
 *f3d_led.c - contains intializations/functions for the leds
 ************************/

#include <stm32f30x.h>
#include <stm32f30x_gpio.h>
#include <stm32f30x_rcc.h>
#include <f3d_led.h>

GPIO_InitTypeDef GPIO_InitStructure;

//intializes the port and pins for the leds on the board
void f3d_led_init(void) {
  RCC_AHBPeriphClockCmd(RCC_AHBPeriph_GPIOE, ENABLE);
  GPIO_StructInit(&GPIO_InitStructure);
  GPIO_InitStructure.GPIO_Mode = GPIO_Mode_OUT;
  GPIO_InitStructure.GPIO_OType = GPIO_OType_PP;
  GPIO_InitStructure.GPIO_Speed = GPIO_Speed_50MHz;
  GPIO_InitStructure.GPIO_PuPd = GPIO_PuPd_NOPULL;
  GPIO_Init(GPIOE, &GPIO_InitStructure);
}
/*Turns on the appropriate led as specified by the parameter.*/
void f3d_led_on(int led) {
  f3d_led_init();
  GPIO_InitStructure.GPIO_Pin = GPIO_Pin_8 << led;
  GPIOE->BSRR = GPIO_Pin_8 << led;
}

/*Turns off the approiate led as specified by the parameter*/ 
void f3d_led_off(int led) {
  f3d_led_init();
  GPIO_InitStructure.GPIO_Pin = GPIO_Pin_8 << led;
  GPIOE->BRR = GPIO_Pin_8 << led;
} 

/*Turns on all LEDs*/
void f3d_led_all_on(void) {
  int i = 0;
  while (i < 8)
    f3d_led_on(i++);
} 

/*Turns off all LEDs*/
void f3d_led_all_off(void) {
  int i = 0;
  while (i < 8)
    f3d_led_off(i++);
} 

/* led.c ends here */

