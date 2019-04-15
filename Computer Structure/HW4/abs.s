	.text
	.syntax unified
	.thumb
	.global	abs	
	.type abs, %function
abs:
	push {r4-r7}
/*
	Your implementation goes here. 
*/
	cmp r0, #0
	bgt .END
	lsls r1, r0, #1
	subs r0, r0, r1
	pop {r4-r7}	
	bx	lr

.END:
