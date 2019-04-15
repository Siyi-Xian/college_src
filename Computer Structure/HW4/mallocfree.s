	.text
	.syntax unified
	.thumb
	.global	main
	.type main, %function
main:
	push {r4-r7,lr}
	movs r0, #8      @ r0 = 8
	bl malloc        @ r0 = malloc(r0)
	movs r1, r0      @ r1 = r0
	movs r4, r0      @ r4 = r0 -- copy for later
	ldr r0, .L3      @ r0 = &fstring
	bl printf        @ printf(r0,r1)
	movs r0, r4      @ r0 = r4
	bl free          @ free(r0)
	pop {r4-r7,pc}   @ return
	.align 2
.L3:    
        .word fstring
fstring:
	.asciz "Malloc return %d\n"  @ null terminated string
	.align 2                     @ make sure section is still aligned
