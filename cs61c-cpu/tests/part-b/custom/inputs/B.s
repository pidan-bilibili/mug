addi s0, x0, 1
addi s1, x0, 2
addi s2, x0, -2
addi, s3, x0, -4
addi, s4, x0, 1
addi, s6, x0, 10
beq s0, s4, second

second:
	addi s5, s5, 1
    beq s5, s6, gunna
    bne s1, s2, third 

third:
    blt s3, s2, fourth


fourth:
    bge s1, s2, fifth

fifth:
    bltu s0, s1 sixth

sixth:
    bgeu s1, s2, end

end:
    beq s0, s1 lol
    addi s1, x0, 5

lol:
    beq x0, x0, second

gunna:
	addi x0 x0 100


