addi t0, x0, 0x7fc
addi s1, x0, 0x100
sw t0, 0(s1)
lh t0, 2(s1)
lb, t0, 3(s1)

lh t1 0(s1)
lh t1 4(s1)
lh t1 3(s1)
lh t1 2(s1)
lh t1 1(s1)

lb t1 0(s1)
lb t1 4(s1)
lb t1 3(s1)
lb t1 2(s1)
lb t1 1(s1)

lw t1 0(s1)
lw t1 4(s1)
lw t1 3(s1)
lw t1 2(s1)
lw t1 1(s1)

sw t1 0(s1)
sw t1 1(s1)
sw t1 2(s1)
sw t1 3(s1)
sw t1 4(s1)

sh t1 0(s1)
sh t1 1(s1)
sh t1 2(s1)
sh t1 3(s1)
sh t1 4(s1)

sb t1 0(s1)
sb t1 1(s1)
sb t1 2(s1)
sb t1 3(s1)
sb t1 4(s1)

