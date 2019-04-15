CC = gcc
CFLAGS = -g
LDFLAGS = -g

rpn: rpn.o
	$(CC) $(LDFLAGS) -o rpn rpn.o

rpn.o: rpn.c
	$(CC) $(CFLAGS) -c rpn.c

clean:
	rm -f rpn rpn.o next next- val

