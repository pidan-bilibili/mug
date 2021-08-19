.globl write_matrix

.text
# ==============================================================================
# FUNCTION: Writes a matrix of integers into a binary file
# FILE FORMAT:
#   The first 8 bytes of the file will be two 4 byte ints representing the
#   numbers of rows and columns respectively. Every 4 bytes thereafter is an
#   element of the matrix in row-major order.
# Arguments:
#   a0 (char*) is the pointer to string representing the filename
#   a1 (int*)  is the pointer to the start of the matrix in memory
#   a2 (int)   is the number of rows in the matrix
#   a3 (int)   is the number of columns in the matrix
# Returns:
#   None
# Exceptions:
# - If you receive an fopen error or eof,
#   this function terminates the program with error code 64
# - If you receive an fwrite error or eof,
#   this function terminates the program with error code 67
# - If you receive an fclose error or eof,
#   this function terminates the program with error code 65
# ==============================================================================
write_matrix:
    # Prologue
    addi sp sp -24
    sw ra 0(sp)
    sw s0 4(sp)
    sw s1 8(sp)
    sw s2 12(sp)
    sw s3 16(sp)
    sw s4 20(sp)

    #set arguments
    mv s0 a0 
    mv s1 a1
    mv s2 a2
    mv s3 a3

    #####################################open file########################################
    mv a1 s0
    addi a2 x0 1

    jal ra fopen

    addi t0 x0 -1
    beq a0 t0 exit64
    mv s4 a0
    #####################################open file########################################
    #s4 open file
    #s2(row), s3(col), int

    #####################################write row########################################
    addi sp sp -4
    sw s2 0(sp)

    mv a1 s4
    mv a2 sp
    addi a3 x0 1
    addi a4 x0 4

    jal ra fwrite

    addi a3 x0 1
    bne a0 a3 exit67

    lw s2 0(sp)
    addi sp sp 4
    #####################################write row########################################


    #####################################write col########################################
    addi sp sp -4
    sw s3 0(sp)

    mv a1 s4
    mv a2 sp
    addi a3 x0 1
    addi a4 x0 4

    jal ra fwrite

    addi a3 x0 1
    bne a0 a3 exit67

    lw s3 0(sp)
    addi sp sp 4
    #####################################write col########################################

    ##################################write elements########################################
    mv a1 s4
    mv a2 s1
    mul a3 s2 s3
    addi a4 x0 4

    jal ra fwrite

    mul a3 s2 s3
    bne a0 a3 exit67
    ##################################write elements########################################

    ###################################close file###########################################
    mv a1 s4

    jal ra fclose

    bne a0 x0 exit65


    lw s4 20(sp)
    lw s3 16(sp)
    lw s2 12(sp)
    lw s1 8(sp)
    lw s0 4(sp)
    lw ra 0(sp)
    addi sp sp 24
    # Epilogue
    ret

exit64:
    li a1, 64
    jal exit2

exit65:
    li a1, 65
    jal exit2

exit67:
    li a1, 67
    jal exit2