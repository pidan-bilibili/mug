import sys
from os.path import basename, normpath
import random
import os

def generate_file(amount, path):
    with open(path, "w") as file:
        file.write("lui sp 0xfffc\n")
        file.write("addi sp sp -2048\n")
        file.write("addi a0 sp 0\n")
        file.write("lui a1 0xfffc\n")
        file.write("loop: addi x0 x0 0\n")
        file.write('sw x0 ' + '0(a0)\n')
        file.write("addi a0 a0 4\n")
        file.write('beq a0 a1 done\n')
        file.write('beq x0 x0 loop\n')
        file.write("done: addi x0 x0 0\n")

        for idx in range(amount):
            file.write(generate_instruction() + "\n")
        file.write("\n")
        file.close()

r_type = ['add', 'mul', 'sub', 'sll', 'mulh', 'mulhu', \
              'slt', 'xor', 'or', 'and']
i_type = ['addi', 'slti', \
              'xori', 'ori', 'andi']
s_type = ['sb']
load = ['lb']
b_type = ['beq', 'bne', 'blt', 'bge', 'bltu', 'bgeu']
u_type = ['auipc', 'lui']
uj_type = ['jal']
j_type = ['jalr']
register_list = ['x0', 'ra', 'sp', 'gp', 'tp', 't0', 't1', \
                     't2', 's0', 's1', 'a0', 'a1', 'a2', 'a3', \
                     'a4', 'a5', 'a6', 'a7', 's2', 's3', 's4', \
                     's5', 's6', 's7', 's8', 's9', 's10', 's11', \
                     't3', 't4', 't5', 't6']

register_list_nosp = ['x0', 'ra', 'gp', 'tp', 't0', 't1', \
                     't2', 's0', 's1', 'a0', 'a1', 'a2', 'a3', \
                     'a4', 'a5', 'a6', 'a7', 's2', 's3', 's4', \
                     's5', 's6', 's7', 's8', 's9', 's10', 's11', \
                     't3', 't4', 't5', 't6']

generate_imm = lambda min, max: str(random.randint(min, max))
generate_imm4 = lambda min, max: str(random.randint(min, max)//4*4)
generate_uimm = lambda: str(random.randint(0, 1048575))
generate_reg = lambda: random.choice(register_list)
generate_reg_nosp = lambda: random.choice(register_list_nosp)

generate_r_type = lambda: random.choice(r_type) + ' ' + generate_reg_nosp() + ' ' + generate_reg() + ' ' + generate_reg()
generate_i_type = lambda: random.choice(i_type) + ' ' + generate_reg_nosp() + ' ' + generate_reg() + ' ' + generate_imm(-2048,2047)
generate_s_type = lambda: random.choice(s_type) + ' ' + generate_reg_nosp() + ' ' + generate_imm(0,2044) + '(sp)'
generate_b_type = lambda: random.choice(b_type) + ' ' + generate_reg() + ' ' + generate_reg() + ' ' + generate_imm4(-2048,2047)
generate_u_type = lambda: random.choice(u_type) + ' ' + generate_reg_nosp() + ' ' + generate_uimm()
generate_uj_type = lambda: random.choice(uj_type) + ' ' + generate_reg_nosp() + ' ' + generate_uimm()
generate_load = lambda: random.choice(load) + ' ' + generate_reg_nosp() + ' ' + generate_imm(0,2044) + '(sp)'
generate_j_type = lambda: random.choice(j_type) + ' ' + generate_reg_nosp() + ' x0 ' + generate_imm4(0,2047)

def generate_instruction():
    return random.choice([generate_r_type, generate_i_type, generate_s_type, generate_u_type, generate_load, \
                          generate_uj_type, generate_uj_type, generate_j_type, generate_j_type])() # generate_b_type not included

amount = 11 * 45 * 14 + 1919 + 810

generate_file(amount, './custom/inputs/test_randomized.s')

