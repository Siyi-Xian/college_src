.text
.syntax unified
.thumb
	movs	r1, r0
	lsls	r0, r2, #1
	lsrs	r0, r2, #5
	asrs	r0, r2, #7
	adds	r0, r1, r2
	subs	r0, r1, r3
	adds	r1, r3, #5
	subs	r2, r4, #1
	movs	r3, #10
	cmp	r5, #100
	adds	r7, #99
	subs	r3, #77
	ands	r0, r1
	eors	r0, r2
	lsls	r0, r3
	lsrs	r0, r4
	asrs	r0, r5
	adcs	r0, r6
	sbcs	r0, r7
	rors	r0, r1
	tst	r0, r2
	rsbs	r0, r3, #0
	cmp	r0, r4
	cmn	r0, r5
	orrs	r0, r6
	muls	r0, r7, r0
	bics	r0, r1
	mvns	r0, r2
	add	r8, lr
	cmp	r9, r10
	mov	r11, r12
	str	r0, [r2, #4]
	ldr	r1, [r4, #120]
	strb	r2, [r3, #27]
	ldrb	r4, [r0, #15]
	strh	r0, [r6, #14]
	ldrh	r3, [r7, #32]
	str	r1, [sp, #256]
	ldr	r0, [sp, #512]
