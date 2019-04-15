	.text
	.syntax unified
	.thumb
	.global	mult_by_7	
	.type mult_by_7, %function
mult_by_7:
	push {r4-r7}
/*
	Your implementation goes here. 
*/
	lsls r4, r0, #3	// y = x << 3
	subs r4, r4, r0	// y = y - x
	movs r0, r4
	pop {r4-r7}	
	bx	lr

.END:
