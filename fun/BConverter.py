base_dictionary = {
	'0' : 0,
	'1' : 1,
	'2' : 2,
	'3' : 3,
	'4' : 4,
	'5' : 5,
	'6' : 6,
	'7' : 7,
	'8' : 8,
	'9' : 9,
	'A': 10,
	'B': 11,
	'C': 12,
	'D': 13,
	'E': 14,
	'F': 15
}
reverse_base_dictionary = {
	0 : '0',
	1 : '1',
	2 : '2',
	3 : '3',
	4 : '4',
	5 : '5',
	6 : '6',
	7 : '7',
	8 : '8',
	9 : '9',
	10 : 'A',
	11 : 'B',
	12 : 'C',
	13 : 'D',
	14 : 'E',
	15 : 'F'
}

# return a base 10 integer   
def convert_to_base_10(input_number, input_base):
	output_number = 0
	sign = 1
	if input_number[0] == '-':
		input_number = input_number[1:]
		sign = - 1
	exp = len(input_number) - 1
	while input_number != '':
		current_base_10 = (base_dictionary[input_number[0]]) * (input_base ** exp)
		output_number += current_base_10
		exp -= 1
		input_number = input_number[1:len(input_number)]
	return output_number * sign

def converter(input_number, input_base, output_base):
	base_10_num = convert_to_base_10(input_number, input_base)
	remainder, output = [], ''
	if base_10_num < 0:
		sign = '-'
		base_10_num = -base_10_num
	else:
		sign = ''
	while base_10_num >= output_base:
		remainder.append(reverse_base_dictionary[base_10_num % output_base])
		base_10_num = base_10_num // output_base
	remainder.append(reverse_base_dictionary[base_10_num])
	while remainder != []:
		output += remainder.pop()
	return sign + output











