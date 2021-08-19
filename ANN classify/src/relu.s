.globl relu

.text
# ==============================================================================
# FUNCTION: Performs an inplace element-wise ReLU on an array of ints
# Arguments:
# 	a0 (int*) is the pointer to the array
#	a1 (int)  is the # of elements in the array
# Returns:
#	None
# Exceptions:
# - If the length of the vector is less than 1,
#   this function terminates the program with error code 32
# ==============================================================================
relu:
    # Prologue
    addi sp sp -16
    sw ra 0(sp)
    sw s0 4(sp)
    sw s1 8(sp)
    sw s2 12(sp)
    
    addi s0 a0 0
    addi s1 a1 0
    addi s2 x0 0
    
    bge x0 s1 exit


loop_start:
	lw t0 0(s0)
    bge t0 zero loop_continue
    sw x0 0(s0)
    



loop_continue:
	addi s0 s0 4
    addi s2 s2 1
    beq s1 s2 loop_end
    j loop_start



loop_end:


    # Epilogue
    lw s2 12(sp)
    lw s1  8(sp)
	lw s0  4(sp)	
	lw ra  0(sp)
    addi sp sp 16
	ret
    
exit:
    addi a1 x0 32
    jal exit2
	
