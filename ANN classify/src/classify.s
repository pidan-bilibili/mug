.globl classify

.text
classify:
    # =====================================
    # COMMAND LINE ARGUMENTS
    # =====================================
    # Args:
    #   a0 (int)    argc
    #   a1 (char**) argv
    #   a2 (int)    print_classification, if this is zero, 
    #               you should print the classification. Otherwise,
    #               this function should not print ANYTHING.
    # Returns:
    #   a0 (int)    Classification
    # Exceptions:
    # - If there are an incorrect number of command line args,
    #   this function terminates the program with exit code 35
    # - If malloc fails, this function terminates the program with exit code 48
    #
    # Usage:
    #   main.s <M0_PATH> <M1_PATH> <INPUT_PATH> <OUTPUT_PATH>

    addi sp sp -52
    sw ra 0(sp)
    sw s0 4(sp)
    sw s1 8(sp)
    sw s2 12(sp)
    sw s3 16(sp) 
    sw s4 20(sp) #row m0
    sw s5 24(sp) #col m0
    sw s6 28(sp) 
    sw s7 32(sp) #row m1
    sw s8 36(sp) #col m1
    sw s9 40(sp)
    sw s10 44(sp) #row input
    sw s11 48(sp) #col input

    mv s0 a0
    mv s1 a1
    mv s2 a2

    addi t0 x0 5
    bne s0 t0 exit35



	# =====================================
    # LOAD MATRICES
    # =====================================
    #s0 num of command line
    #s1 path
    #s2 print or not





    # Load pretrained m0 ###############  store m0 into a3
    lw a0 4(s1)

    #malloc a1
    addi sp sp -8
    sw ra 0(sp)
    sw a0 4(sp)

    addi a0 x0 4

    jal ra malloc
    beq a0 x0 exit48
    mv a1 a0

    lw a0 4(sp)
    lw ra 0(sp)
    addi sp sp 8

    #malloc a2, store a1
    addi sp sp -12
    sw ra 0(sp)
    sw a0 4(sp)
    sw a1 8(sp)

    addi a0 x0 4

    jal ra malloc
    beq a0 x0 exit48
    mv a2 a0

    lw a1 8(sp)
    lw a0 4(sp)
    lw ra 0(sp)
    addi sp sp 12

    #call function
    #a0, a1, a2
    addi sp sp -12
    sw ra 0(sp)
    sw a1 4(sp)
    sw a2 8(sp)

    jal ra read_matrix
    mv a3 a0

    lw a2 8(sp)
    lw a1 4(sp)
    lw ra 0(sp)
    addi sp sp 12

    lw s4 0(a1)
    lw s5 0(a2)

    #free a1, so we need to store a2, and matrix a3
    addi sp sp -12
    sw ra 0(sp)
    sw a2 4(sp)
    sw a3 8(sp)

    mv a0 a1
    jal ra free

    lw a3 8(sp)
    lw a2 4(sp)
    lw ra 0(sp)
    addi sp sp 12

    #free a2. store matrix a3
    addi sp sp -8
    sw ra 0(sp)
    sw a3 4(sp)

    mv a0 a2
    jal ra free

    lw a3 4(sp)
    lw ra 0(sp)
    addi sp sp 8







    # Load pretrained m1
    lw a0 8(s1)

    #malloc a1, store matrix a3, and path a0
    addi sp sp -12
    sw ra 0(sp)
    sw a0 4(sp)
    sw a3 8(sp)

    addi a0 x0 4

    jal ra malloc
    beq a0 x0 exit48
    mv a1 a0

    lw a3 8(sp)
    lw a0 4(sp)
    lw ra 0(sp)
    addi sp sp 12

    #malloc a2, store a1 and matrix a3, and path a0
    addi sp sp -16
    sw ra 0(sp)
    sw a0 4(sp)
    sw a1 8(sp)
    sw a3 12(sp)

    addi a0 x0 4

    jal ra malloc
    beq a0 x0 exit48
    mv a2 a0

    lw a3 12(sp)
    lw a1 8(sp)
    lw a0 4(sp)
    lw ra 0(sp)
    addi sp sp 16

    # call function store a3, store matrix into a4
    # a0, a1, a2
    addi sp sp -16
    sw ra 0(sp)
    sw a1 4(sp)
    sw a2 8(sp)
    sw a3 12(sp)

    jal ra read_matrix
    mv a4 a0

    lw a3 12(sp)
    lw a2 8(sp)
    lw a1 4(sp)
    lw ra 0(sp)
    addi sp sp 16

    lw s7 0(a1)
    lw s8 0(a2)

    #free a1, so we need to store a2, and matrix a3, a4
    addi sp sp -16
    sw ra 0(sp)
    sw a2 4(sp)
    sw a3 8(sp)
    sw a4 12(sp)

    mv a0 a1
    jal ra free

    lw a4 12(sp)
    lw a3 8(sp)
    lw a2 4(sp)
    lw ra 0(sp)
    addi sp sp 16

    #free a2. store matirx a3, a4 
    addi sp sp -12
    sw ra 0(sp)
    sw a3 4(sp)
    sw a4 8(sp)

    mv a0 a2
    jal ra free

    lw a4 8(sp)
    lw a3 4(sp)
    lw ra 0(sp)
    addi sp sp 12




    # Load input matrix
    lw a0 12(s1)

    #malloc a1, store a0, a3 a4
    addi sp sp -16
    sw ra 0(sp)
    sw a0 4(sp)
    sw a3 8(sp)
    sw a4 12(sp)

    addi a0 x0 4

    jal ra malloc
    beq a0 x0 exit48
    mv a1 a0

    lw a4 12(sp)
    lw a3 8(sp)
    lw a0 4(sp)
    lw ra 0(sp)
    addi sp sp 16

    #malloc a2, store a0, a1, a3, a4
    addi sp sp -20
    sw ra 0(sp)
    sw a0 4(sp)
    sw a1 8(sp)
    sw a3 12(sp)
    sw a4 16(sp)

    addi a0 x0 4

    jal ra malloc
    beq a0 x0 exit48
    mv a2 a0

    lw a4 16(sp)
    lw a3 12(sp)
    lw a1 8(sp)
    lw a0 4(sp)
    lw ra 0(sp)
    addi sp sp 20

    #call function store a3, a4
    #a0, a1, a2
    addi sp sp -20
    sw ra 0(sp)
    sw a1 4(sp)
    sw a2 8(sp)
    sw a3 12(sp)
    sw a4 16(sp)

    jal ra read_matrix
    mv a5 a0

    lw a4 16(sp)
    lw a3 12(sp)
    lw a2 8(sp)
    lw a1 4(sp)
    lw ra 0(sp)
    addi sp sp 20

    lw s10 0(a1)
    lw s11 0(a2)

    #free a1, so we need to store a2, and matrixs a3 a4 a5
    addi sp sp -20
    sw ra 0(sp)
    sw a2 4(sp)
    sw a3 8(sp)
    sw a4 12(sp)
    sw a5 16(sp)

    mv a0 a1
    jal ra free

    lw a5 16(sp)
    lw a4 12(sp)
    lw a3 8(sp)
    lw a2 4(sp)
    lw ra 0(sp)
    addi sp sp 20

    #free a2. and matrixs a3 a4 a5
    addi sp sp -16
    sw ra 0(sp)
    sw a3 4(sp)
    sw a4 8(sp)
    sw a5 12(sp)

    mv a0 a2
    jal ra free

    lw a5 12(sp)
    lw a4 8(sp)
    lw a3 4(sp)
    lw ra 0(sp)
    addi sp sp 16






    # =====================================
    # RUN LAYERS
    # =====================================
    # 1. LINEAR LAYER:    m0 * input
    # 2. NONLINEAR LAYER: ReLU(m0 * input)
    # 3. LINEAR LAYER:    m1 * ReLU(m0 * input)
    # m0: a3 s4 s5
    # m1: a4 s7 s8
    # input: a5 s10 s11



    ###LINEAR LAYER

    #malloc space for d = matmul(m0, input)
    addi sp sp -16
    sw ra 0(sp)
    sw a3 4(sp)
    sw a4 8(sp)
    sw a5 12(sp)

    mul a0 s4 s11
    slli a0 a0 2
    jal ra malloc 
    beq a0 x0 exit48
    mv a6 a0

    lw a5 12(sp)
    lw a4 8(sp)
    lw a3 4(sp)
    lw ra 0(sp)
    addi sp sp 16

    #call matmul
    addi sp sp -24
    sw ra 0(sp)
    sw a0 4(sp)
    sw a3 8(sp)
    sw a4 12(sp)
    sw a5 16(sp)
    sw a6 20(sp)

    mv a0 a3
    mv a1 s4
    mv a2 s5
    mv a3 a5 
    mv a4 s10
    mv a5 s11
    mv a6 a6 

    jal ra matmul

    lw a6 20(sp)
    lw a5 16(sp)
    lw a4 12(sp)
    lw a3 8(sp)
    lw a0 4(sp)
    lw ra 0(sp)
    addi sp sp 24




    # a6 is the pointer to the matrix(1-D array)
    # 2. NONLINEAR LAYER: ReLU(m0 * input)
    addi sp sp -20
    sw ra 0(sp)
    sw a3 4(sp)
    sw a4 8(sp)
    sw a5 12(sp)
    sw a6 16(sp)

    mv a0 a6
    mul a1 s4 s11

    jal ra relu

    lw a6 16(sp)
    lw a5 12(sp)
    lw a4 8(sp)
    lw a3 4(sp)
    lw ra 0(sp)
    addi sp sp 20



    # 3. LINEAR LAYER:    m1 * ReLU(m0 * input)
    # ReLU(m0 * input): a6 s4 s11
    # m1 a4 s7 s8

    #malloc space for scores
    addi sp sp -20
    sw ra 0(sp)
    sw a3 4(sp)
    sw a4 8(sp)
    sw a5 12(sp)
    sw a6 16(sp)

    mul a0 s4 s8
    slli a0 a0 2
    jal ra malloc
    beq a0 x0 exit48
    mv a7 a0

    lw a6 16(sp)
    lw a5 12(sp)
    lw a4 8(sp)
    lw a3 4(sp)
    lw ra 0(sp)
    addi sp sp 20

    addi sp sp -28
    sw ra 0(sp)
    sw a0 4(sp)
    sw a3 8(sp)
    sw a4 12(sp)
    sw a5 16(sp)
    sw a6 20(sp)
    sw a7 24(sp)

    mv a0 a4
    mv a1 s7
    mv a2 s8
    mv a3 a6
    mv a4 s4
    mv a5 s11
    mv a6 a7

    jal ra matmul

    lw a7 24(sp)
    lw a6 20(sp)
    lw a5 16(sp)
    lw a4 12(sp)
    lw a3 8(sp)
    lw a0 4(sp)
    lw ra 0(sp)
    addi sp sp 28


    #a7 is the result matrix s7 * s11
    # =====================================
    # WRITE OUTPUT
    # =====================================
    # Write output matrix
    addi sp sp -24
    sw ra 0(sp)
    sw a3 4(sp)
    sw a4 8(sp)
    sw a5 12(sp)
    sw a6 16(sp)
    sw a7 20(sp)

    lw a0 16(s1)
    mv a1 a7
    mv a2 s7
    mv a3 s11

    jal ra write_matrix

    lw a7 20(sp)
    lw a6 16(sp)
    lw a5 12(sp)
    lw a4 8(sp)
    lw a3 4(sp)
    lw ra 0(sp)
    addi sp sp 24







    # =====================================
    # CALCULATE CLASSIFICATION/LABEL
    # =====================================
    # Call argmax
    addi sp sp -24
    sw ra 0(sp)
    sw a3 4(sp)
    sw a4 8(sp)
    sw a5 12(sp)
    sw a6 16(sp)
    sw a7 20(sp)

    mv a0 a7
    mul a1 s7 s11
    jal ra argmax
    mv s3 a0
    #s3 is max value
    lw a7 20(sp)
    lw a6 16(sp)
    lw a5 12(sp)
    lw a4 8(sp)
    lw a3 4(sp)
    lw ra 0(sp)
    addi sp sp 24

    #free a7
    addi sp sp -20
    sw ra 0(sp)
    sw a4 4(sp)
    sw a5 8(sp)
    sw a6 12(sp)
    sw a3 16(sp)

    mv a0 a7
    jal ra free

    lw a3 16(sp)
    lw a6 12(sp)
    lw a5 8(sp)
    lw a4 4(sp)
    lw ra 0(sp)
    addi sp sp 20

    #free a6
    addi sp sp -16
    sw ra 0(sp)
    sw a5 4(sp)
    sw a3 8(sp)
    sw a4 12(sp)

    mv a0 a6
    jal ra free

    lw a4 12(sp)
    lw a3 8(sp)
    lw a5 4(sp)
    lw ra 0(sp)
    addi sp sp 16



    #free a5 input
    addi sp sp -12
    sw ra 0(sp)
    sw a4 4(sp)
    sw a3 8(sp)

    mv a0 a5
    jal ra free

    lw a3 8(sp)
    lw a4 4(sp)
    lw ra 0(sp)
    addi sp sp 12

    #free a4
    addi sp sp -8
    sw ra 0(sp)
    sw a3 4(sp)

    mv a0 a4
    jal ra free

    lw a3 4(sp)
    lw ra 0(sp)
    addi sp sp 8

    #free a3
    addi sp sp -4
    sw ra 0(sp)

    mv a0 a3
    jal ra free

    lw ra 0(sp)
    addi sp sp 4



    # Print classification
    beq s2 x0 printC
    



    # Print newline afterwards for clarity
    lw s11 48(sp)
    lw s10 44(sp)
    lw s9 40(sp)
    lw s8 36(sp)
    lw s7 32(sp)
    lw s6 28(sp)
    lw s5 24(sp)
    lw s4 20(sp)
    lw s3 16(sp)
    lw s2 12(sp)
    lw s1 8(sp)
    lw s0 4(sp)
    lw ra 0(sp)
    addi sp sp 52

    ret

printC:
    mv a1 s3
    jal ra print_int

    addi a1 x0 10
    jal ra print_char

    lw s11 48(sp)
    lw s10 44(sp)
    lw s9 40(sp)
    lw s8 36(sp)
    lw s7 32(sp)
    lw s6 28(sp)
    lw s5 24(sp)
    lw s4 20(sp)
    lw s3 16(sp)
    lw s2 12(sp)
    lw s1 8(sp)
    lw s0 4(sp)
    lw ra 0(sp)
    addi sp sp 52

    ret

exit35:
    li a1, 35
    jal ra exit2

exit48:
    li a1, 48
    jal ra exit2

