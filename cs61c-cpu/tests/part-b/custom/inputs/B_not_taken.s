addi s0, x0, 1
addi s1, x0, 2
addi s2, x0, -2
addi, s3, x0, -4
addi, s4, x0, 2
beq s0, s4, second
bne s1, s4, third
blt s1, s0, second
bge s0, s1, second
bltu s1, s0, second
bgeu s0, s1, second


second:
    addi s5, x0, 1



third:
    addi s0, x0, 1

