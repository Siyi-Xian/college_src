	.text
	.syntax unified
	.thumb
	.global	fib	
	.type fib, %function
fib:
	push {r4-r7}
/*
	Your implementation goes here. 
*/
	movs r1, #1	// int prev2 = 1
	movs r2, #1	// int prev1 = 1
	movs r3, #1	// int val = 1
	movs r4, r0	// int i = num
	cmp r0, #0
	beq .END
	cmp r0, #1
	beq .END
	subs r4, r0, #2 // i = num - 2
	b .LOOP
	pop {r4-r7}
	bx	lr
.LOOP:
	movs r0, r3	// num = val
	cmp r4, #0	// if (i == 0)
	beq .END	// return
	adds r3, r2, r1	// val = prev1 + prev2
	movs r1, r2	// prev2 = prev1
	movs r2, r3	// prev1 = val
	subs r4, r4, #1	// i--
	b .LOOP
.END:
