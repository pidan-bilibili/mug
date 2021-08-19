add x0 x0 x0
addi a1 x0 100
mul a0 x0 a1
sub a0 a1 x0
sll a0 a1 a1
mulh a0 a1 a1
mulhu a0 a1 a1
slt a0 a1 a1
xor a0 a1 a1
srl a0 a1 a1
sra a0 a1 a1
or a0 a1 a1
and a0 a1 x0 
lb a2 100(a0)
lh a2 100(a0)
lw a2 100(a0)
addi a1 a1 -2048
slli a0 a1 3
slti a0 a1 4
xori a0 a1 101
srli a0 a1 10
srai a0 a1 10
ori a0 a1 101
andi a0 a1 101
sb a0 100(a1)
sh a0 0(a1)
sw a0 2(a1)
auipc a0 1001
lui a0 2020
jal ra 100
jalr ra a0 100

