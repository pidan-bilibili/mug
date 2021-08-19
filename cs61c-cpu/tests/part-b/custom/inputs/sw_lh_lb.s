addi t0, x0, 0x700
addi s0 x0, 2000
sw t0, 0(s0)
lh t0, 2(s0)
lb, t0, 3(s0)
sw t1, 9(s0)
lw t1, 9(s0)
lw a0, 11(s0)
lw a1, 12(s0)
lw a2, 13(s0)