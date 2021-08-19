addi s1, x0, 1
addi s2, x0, 1

bne s1, s2, end

jal ra back


back:
    addi s1, s1, 5
    jalr x0 ra -8

end: 
    add t0 x0, x0
    beq x0 x0 start
    jal x0 nop_land

start:
    addi x0, x0, 0
    jal ra next
    beq x0, x0, not_yet_finish

next:
    jalr x0 ra 0
    
not_yet_finish:
    beq x0, x0, nop_land
    addi s0, x0, 1
    
nop_land:
    addi x0, x0, 0
    jal x0 finish
    addi s1, s1, 3
    
finish:
    addi x0, x0, 0
    