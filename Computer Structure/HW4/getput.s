	.text
	.syntax unified
	.thumb
	.global	main
	.type main, %function
main:
	push {r4-r7,lr}
.l0:    bl getchar       @ r0 = getchar()
	cmp r0, #0       @ if (r0 < 0) goto .L1
	blt .l1
	bl putchar       @ putchar(r0)
	b .l0            @ loop
.l1:    
	pop {r4-r7,pc}   @ return
