CC = gcc
CFLAGS = -g
LDFLAGS = -g

disassember: disassember.o
	$(CC) $(LDFLAGS) -o disassember disassember.o

disassember.o: hw3.c
	gcc hw3.c -c -o disassember.o

test:
	cp test.asm test_case_file
	arm-none-eabi-as test_case_file -o test.o
	arm-none-eabi-objcopy test.o -O binary --only-section=.text test.bin
	hexdump -x test.bin |sed -e 's/^[^ ]*//' > test.hex
	make  
	cat test.hex |./disassember > test.out
	diff -b -B test.out test_case_file > test.diff

clean:
	rm -f test.bin test.o *~ *.o test_case_file test.hex d* test.out test.diff
