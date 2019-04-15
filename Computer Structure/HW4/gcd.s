	.text
	.syntax unified
	.thumb
	.global	gcd	
	.type gcd, %function
gcd:
	push {r4-r7}
/*
	Your implementation goes here. 
*/
	movs r2, #0	// int d = 0
	b	.L1
	pop {r4-r7}
	bx	lr
.L1:
	movs r3, #1
	ands r3, r0
	cmp r3, #0
	bne	.L2	// a & 1 break loop
	movs r3, #1
	ands r3, r1
	cmp r3, #0
	bne	.L2	// b & 1 break loop
	lsrs r0, r0, #1	// a >>= 1
	lsrs r1, r1, #1	// b >>= 1
	adds r2, r2, #1	// d += 1
	b	.END
	b	.L1	// goto .L1
.L2:
	cmp r0, r1
	beq	.END
	movs r3, #1
	ands r3, r0
	cmp r3, #0
	beq	.I1	// !(a & 1)
	movs r3, #1
	ands r3, r1
	cmp r3, #0
	beq	.I2	// !(b & 1)
	cmp r0, r1
	blt	.I3	// a > b
	subs r1, r1, r0	// b = b - a
	lsrs r1, r1, #1	// b = b >> 1
	b	.L2
.I1:
	lsrs r0, r0, #1	// a >>= 1
	b	.L2
.I2:
	lsrs r1, r1, #1	// b >>= 1
	b	.L2
.I3:
	subs r0, r0, r1	// a = a - b
	lsrs r0, r0, #1	// a = a >> 1
	b	.L2
.R1:
	lsls r0, r0, r2
.END:
