.globl matmul
.text
# =======================================================
# FUNCTION: Matrix Multiplication of 2 integer matrices
#   d = matmul(m0, m1)
# Arguments:
#   a0 (int*)  is the pointer to the start of m0 
#   a1 (int)   is the # of rows (height) of m0
#   a2 (int)   is the # of columns (width) of m0
#   a3 (int*)  is the pointer to the start of m1
#   a4 (int)   is the # of rows (height) of m1
#   a5 (int)   is the # of columns (width) of m1
#   a6 (int*)  is the pointer to the the start of d
# Returns:
#   None (void), sets d = matmul(m0, m1)
# Exceptions:
#   Make sure to check in top to bottom order!
#   - If the dimensions of m0 do not make sense,
#     this function terminates the program with exit code 34
#   - If the dimensions of m1 do not make sense,
#     this function terminates the program with exit code 34
#   - If the dimensions of m0 and m1 don't match,
#     this function terminates the program with exit code 34
# =======================================================
matmul:

    # Error checks
    bne a2 a4 exit
    bge x0 a1 exit
    bge x0 a2 exit
    bge x0 a4 exit
    bge x0 a5 exit
    # Prologue
    addi sp sp -40
    sw ra 0(sp) 
    sw s0 4(sp) 
    sw s1 8(sp) 
    sw s2 12(sp) 
    sw s3 16(sp)
    sw s4 20(sp)
    sw s5 24(sp)
    sw s6 28(sp)
    sw s7 32(sp)
    sw s8 36(sp)
    
    add s0 a0 x0 # #####pointer to matrix m0
    add s1 a1 x0 #row of matrix m0
    add s2 a2 x0 #column of matrix m0
    add s3 a3 x0 # #####pointer to matrix m1
    add s4 a4 x0 #row of matrix m1
    add s5 a5 x0 #column of matrix m1
    add s6 a6 x0 # #####output
    add s7 x0 x0 #count for outer


outer_loop_start:
    beq s7 s1 outer_loop_end
    add s8 x0 x0 #count for inner

inner_loop_start:
    addi t2 x0 1 #value 1

    beq s8 s5 inner_loop_end
    addi t0 x0 4 #calculate position of m0
    mul t0 t0 s7 
    mul t0 t0 s2 
    add t0 t0 s0 
    addi t1 x0 4 #calculate position of m1
    mul t1 t1 s8
    add t1 t1 s3
    add a0 x0 t0 #set input
    add a1 x0 t1
    add a2 x0 s2
    add a3 x0 t2
    add a4 x0 s5

    jal ra dot #call dot function

    sw a0 0(s6) #store value 
    addi s6 s6 4

    addi s8 s8 1 #iteration

    j inner_loop_start
    
inner_loop_end:
    addi s7 s7 1
    j outer_loop_start

outer_loop_end:
    addi a6 s6 100
    # Epilogue
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
    addi sp sp 40
    
    ret

exit:
    addi a1 x0 34
    jal exit2
