.globl read_matrix

.text
# ==============================================================================
# FUNCTION: Allocates memory and reads in a binary file as a matrix of integers
#
# FILE FORMAT:
#   The first 8 bytes are two 4 byte ints representing the # of rows and columns
#   in the matrix. Every 4 bytes afterwards is an element of the matrix in
#   row-major order.
# Arguments:
#   a0 (char*) is the pointer to string representing the filename
#   a1 (int*)  is a pointer to an integer, we will set it to the number of rows
#   a2 (int*)  is a pointer to an integer, we will set it to the number of columns
# Returns:
#   a0 (int*)  is the pointer to the matrix in memory
# Exceptions:
# - If malloc returns an error,
#   this function terminates the program with error code 48
# - If you receive an fopen error or eof, 
#   this function terminates the program with error code 64
# - If you receive an fread error or eof,
#   this function terminates the program with error code 66
# - If you receive an fclose error or eof,
#   this function terminates the program with error code 65
# ==============================================================================
read_matrix:

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

    # set all the argument
    add s0 a0 x0
    add s1 a1 x0
    add s2 a2 x0


    #######################################open file##########################################

    # set input
    mv a1 s0
    mv a2 x0

    jal ra fopen
    addi t0 x0 -1
    beq a0 t0 endfopen64
    mv s4 a0
    #######################################open file##########################################
    # s4 is the openfile


    #######################################read row##########################################
    mv s0 s4
    mv a1 a0
    mv a2 s1
    addi a3 x0 4

    jal ra fread
    addi t0 x0 4
    bne a0 t0 endfread66
    #######################################read row##########################################




    #######################################read col##########################################
    # set input
    mv a1 s4
    mv a2 s2
    addi a3 x0 4

    jal ra fread

    #check failure
    addi t0 x0 4
    bne t0 a0 endfread66

    #######################################read col##########################################


    #s4 is file
    #s1(row), s2(col) pointer to an int.
    ####################################malloc space ##########################################
    lw s5 0(s1)
    lw s6 0(s2)
    mul s3 s5 s6
    slli s3 s3 2

    #set input
    mv a0 s3

    jal ra malloc

    beq a0 x0 endmalloc48
    mv s0 a0
    ####################################malloc space ##########################################
    #s0 return value (pointer)
    #s4 file
    #s3 malloc size
    #s1(row), s2(col) pointer to an int.


    ####################################read elements ##########################################
    # set input
    mv a1 s4
    mv a2 s0
    mv a3 s3

    jal ra fread
    bne a0 s3 endfread66

    ####################################read elements ##########################################
    #s0 return value (pointer) (result)
    #s4 file
    #s3 malloc size
    #s1(row), s2(col) pointer to an int.


    
    ####################################close file ##########################################

    #set input
    mv a1 s4
    jal ra fclose

    addi t0 x0 -1
    beq a0 t0 endfclose65

    ####################################close file ##########################################
    mv a0 s0
    mv a1 s1
    mv a2 s2


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


endmalloc48:
	li a1 48
	j exit2

endfopen64:
	li a1 64
	j exit2

endfread66:
	li a1 66
	j exit2

endfclose65:
	li a1 65
	j exit2
