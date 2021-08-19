addi t0, x0, -1
addi s0, x0,-2048
addi t1, x0, -255
addi t2, t1, -16
lb s1, 0(s0)
lh s1, 0(s0)
lh s1, 0(s0)
lw s1, 0(s0)
addi s3 x0, 3
slli s3, s3, 2
slti s3, s3, 2
addi s4, x0, 1990
xori s3, s3, 12
srli s3, s3, 3
srai s4,s4, 2
ori s3, s4, 2
andi s4, s3, 299
addi x0, x1, 0b011111111111
addi x2, x1, 0b011111111111
slli x3, x1, 0b011111
slli x4, x2, 0b011111
xori x4, x2, 0b011111111111

