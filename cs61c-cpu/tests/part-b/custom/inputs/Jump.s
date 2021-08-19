beq x0 x0 label0

no_call:
    addi x0 x0 100
    jal ra exit

label0:
    addi t0 x0 1
    addi t1 x0 2
    beq t0 t1 label0
    bne t0 t1 label1

label1:
    addi t0 x0 1
    addi t1 x0 1
    bne t0 t1 label1
    beq t0 t1 label2

func_min2:
    addi a0 a0 0 
    jalr ra x0 exit
    jal ra exit

func_min1:
    mul a1 a0 a0


func0:
    addi a0 a0 1
    jal ra func_min1

label2:
    jal ra func1
    jal ra func0
    jal ra no_call

func1:
    addi x0 x0 0
    addi a0 x0 100
    jalr ra x0 func2
    jal ra exit

func2:
    jalr ra x0 no_call
    jalr ra x0 100
    jal ra front

front:
    jal ra back
    jal ra back
    jal ra back

back:
    jal ra front
    jal ra front
    jalr ra x0 front

exit:
    addi x0 x0 0





