# CS61CPU

Look ma, I made a CPU! Here's what I did:
We first classify the signals(Regan, PCSel, ASel, BSel, Brun, Bre, Blt, Mem) for different 
types of instruction. Next, we use the opcode, fucn3, and func7 to figure out which instruction
it is and then compare the logic gate as an if statement to select which signal we need. 

Disadvantage: time-consuming.

Advantage: You do not need to build an integrated circuit. We need to compare, integrate the 
comparison, and use the output to select the signal.

Since memory address is mul of four, we select the last two-bit (which is the remainder of mul 
four) mul by eight, we generate a bit selector. We then use this bit selector to choose how 
many bits we need to select. And we use the func3 to select whether it is a byte, half-word, or
word. which corresponds to 8bits, 16bits, and 32 bits. 

i.e. sh 0xAABBCCDD 2(s0); WRITE_ENABLE = 1100; WRITE_DATA = 0xCCDD0000
Block should be in this format(where each [] represents a byte):
[][][ ][ ] | [][][][] | [][][][] | [][][][] | ... (s0)
     |  | 
     data

[][][DD][CC] | [][][][] | [][][][] | [][][][] | ... (s0)

if don't shift:

i.e. sh 0xAABBCCDD 2(s0); WRITE_ENABLE = 0011; WRITE_DATA = 0xAABBCCDD
[ ][ ][][] | [][][][] | [][][][] | [][][][] | ... (s0)
 |  | 
 data

[DD][CC][][] | [][][][] | [][][][] | [][][][] | ... (s0)


Bug: wrongly select set opcode to 0-7 which is 8 bits (it should be 0-6 7 bits). 

-





