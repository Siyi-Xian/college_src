ELF=$(notdir $(CURDIR)).elf
TOOLROOT=/l/arm2/devtools/bin

CC=$(TOOLROOT)/arm-none-eabi-gcc
LD=$(TOOLROOT)/arm-none-eabi-gcc
AR=$(TOOLROOT)/arm-none-eabi-ar
AS=$(TOOLROOT)/arm-none-eabi-as
GDB=$(TOOLROOT)/arm-none-eabi-gdb --tui
QEMU=$(TOOLROOT)/qemu-system-arm 
QFLAGS= -cpu cortex-m3 -nographic 
QFLAGS+= -monitor null -serial null 
QFLAGS+= -machine versatilepb -semihosting -kernel 

OBJDUMP=$(TOOLROOT)/arm-none-eabi-objdump

CFLAGS = -g -mcpu=cortex-m0 -mthumb -specs=rdimon.specs -lc -lrdimon
LDFLAGS = 

OBJS=  $(STARTUP) t1.o test.o

$(ELF) : $(OBJS)
	$(CC) $(CFLAGS) $(LDFLAGS) $(OBJS) -o $@

.s.o : 
	$(AS) -g $< -o $@
	$(OBJDUMP) -d $@ > $(@:.o=.lst)

.c.o : 
	$(CC) $(CFLAGS) -c $< -o $@

test : $(ELF)
	$(QEMU) $(QFLAGS) $< > output.txt

debug: $(ELF)
	$(QEMU) $(QFLAGS) $< -gdb tcp::51234 -S &
	$(GDB) $< --command=../main.gdb

clean: 
	rm -f $(OBJS) $(ELF) $(OBJS:.o=.lst)
