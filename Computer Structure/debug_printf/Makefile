TEMPLATEROOT = ../Fall2018-Lab/

# compilation flags for gdb

CFLAGS  = -O1 -g
ASFLAGS = -g 

# object files

F3D = f3d_uart.o f3d_led.o f3d_user_btn.o
STM32 = stm32f30x_usart.o stm32f30x_rcc.o stm32f30x_gpio.o
LIB = newlib_stubs.o

OBJS=  $(STARTUP) $(F3D) $(STM32) $(LIB) main.o

# include common make file

include $(TEMPLATEROOT)/c335.mk


