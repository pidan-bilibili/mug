
# =================================================================
# FUNCTION: Given a int vector, return the index of the largest
#	element. If there are multiple, return the one
#	with the smallest index.
# Arguments:
# 	a0 (int*) is the pointer to the start of the vector
#	a1 (int)  is the # of elements in the vector
# Returns:
#	a0 (int)  is the first index of the largest element
# Exceptions:
# - If the length of the vector is less than 1,
#   this function terminates the program with error code 32
# =================================================================
argmax:
    # Prologue
    addi sp sp -20
    sw ra 0(sp)
    sw s0 4(sp)
    sw s1 8(sp)
    sw s2 12(sp)
    sw s3 16(sp)
    
    add s0 x0 a0 #int array
    add s1 x0 a1 #sizeofzrray
    add s2 x0 x0 #count
    add s3 x0 x0 #result
    
    bge x0 s1 exit #return error code 32 if arraysize is 0.
    
    lw t1 0(s0) #first index be the max


loop_start:
	lw t2 0(s0)
    bge t1 t2 loop_continue
    add t1 t2 x0
    add s3 s2 x0



loop_continue:
	addi s0 s0 4
    addi s2 s2 1
    beq s2 s1 loop_end
    j loop_start


loop_end:
    add a0 s3 x0

    # Epilogue
    lw s3 16(sp)
    lw s2 12(sp)
    lw s1 8(sp)
    lw s0 4(sp)
    lw ra 0(sp)
    addi sp sp 20

exit:
    addi x0 x0 0
    
	
