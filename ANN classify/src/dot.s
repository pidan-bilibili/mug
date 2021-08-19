.globl dot

.text
# =======================================================
# FUNCTION: Dot product of 2 int vectors
# Arguments:
#   a0 (int*) is the pointer to the start of v0
#   a1 (int*) is the pointer to the start of v1
#   a2 (int)  is the length of the vectors
#   a3 (int)  is the stride of v0
#   a4 (int)  is the stride of v1
# Returns:
#   a0 (int)  is the dot product of v0 and v1
# Exceptions:
# - If the length of the vector is less than 1,
#   this function terminates the program with error code 32
# - If the stride of either vector is less than 1,
#   this function terminates the program with error code 33
# =======================================================
dot:

    # Prologue
    addi sp sp -32
    sw ra 0(sp)
    sw s0 4(sp)
    sw s1 8(sp)
    sw s2 12(sp)
    sw s3 16(sp)
    sw s4 20(sp)
    sw s5 24(sp)
    sw s6 28(sp)
    
    add s0 a0 x0 #pointer to v0
    add s1 a1 x0 #pointer to v1
    add s2 a3 x0 #v0 stride
    add s3 a4 x0 #v1 stride
    add s4 a2 x0 #length of the vector
    add s5 x0 x0 #count
    add s6 x0 x0 #output
    
    bge x0 s4 exit1
    bge x0 s2 exit3
    bge x0 s3 exit3
    
    slli s2 s2 2
    slli s3 s3 2
    


loop_start:
    lw t0 0(s0)
    lw t1 0(s1)
    
    mul t1 t0 t1
    add s6 s6 t1
    add s0 s0 s2
    add s1 s1 s3
    addi s5 s5 1
    
    bne s4 s5 loop_start

loop_end:
    add a0 s6 x0
    # Epilogue
    lw s6 28(sp)
    lw s5 24(sp)
    lw s4 20(sp)
    lw s3 16(sp)
    lw s2 12(sp)
    lw s1 8(sp)
    lw s0 4(sp)
    lw ra 0(sp)
    addi sp sp 32
    
    ret

exit1:
    addi a1 x0 32
    jal exit2

exit3:
    addi a1 x0 33
    jal exit2

