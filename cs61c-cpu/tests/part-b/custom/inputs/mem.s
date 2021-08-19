addi sp sp -4
lui s0 2222
auipc a3 2222
addi s5, x0, 0x100
sw s0 0(s5)
lw s1 0(s5)
sh s0 20(s5)
lui s1 2000
sw s1 20(s5)
sb s1 21(s5)
sh s1 40(s5)
sb s1 -6(s5)
sb s1 -1000(s5)
### s2
addi s2 x0, 2000
sh s1 -400(s5)
sw s1 -1000(s5)
lw s1 -1000(s5)
addi s6, x0, 0b11111111
sw s1 2040(s6)
sw s1 -2040(s6)
sw s1 -4(s6)
sb s1 10(s6)
sb s1 -5(s6)
sh s1 16(s6)
sh s1 18(s6)
sh s1 -6(s6)

addi s5, x0, -5
sw s5 24(s6)
lb s4 24(s6)

sw a0 0(s5)
sw a1 0(s5)
sw a2 0(s5)
sw a3 0(s5)
sw a4 0(s5)
sw a5 0(s5)
sw a6 0(s5)
sw a7 0(s5)
sw s0 0(s5)
sw s1 0(s5)
sw s2 0(s5)
sw s3 0(s5)
sw s4 0(s5)
sw s6 0(s5)
sw s7 0(s5)
sw s8 0(s5)
sw s9 0(s5)
sw s10 0(s5)
sw s11 0(s5)
sw t0 0(s5)
sw t1 0(s5)
sw t2 0(s5)
sw t3 0(s5)
sw t4 0(s5)
sw t5 0(s5)
sw t6 0(s5)


lw t0 0(s5)
lw t1 0(s5)
lw t2 0(s5)
lw t3 0(s5)
lw t4 0(s5)
lw t5 0(s5)
lw t6 0(s5)


