	.text
	.syntax unified
	.thumb
	.global	string_compare_n	
	.type string_compare_n, %function
string_compare_n:
	push {r4-r7}
/*
	Your implementation goes here. 
*/
	movs r4, #0	// int i = 0
	b	.L1
	pop {r4-r7}	
	bx	lr
.L1:
	cmp r4, r2
	bge	.END
	ldr r5, [r0]
	ldr r6, [r1]
	cmp r5, r6
	bne	.END
	adds r0, #1
	adds r1, #1
	adds r4, #1
	b	.L1
.END:
	ldr r5, [r0]
	ldr r6, [r1]
	subs r0, r5, r6
