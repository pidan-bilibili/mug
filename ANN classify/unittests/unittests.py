from unittest import TestCase
from framework import AssemblyTest, print_coverage


class TestAbs(TestCase):
    def test_minus_one(self):
    	# Indicates we are creating the test for the `abs.s` file
        t = AssemblyTest(self, "abs.s")
    	# Setting the argument register a0 to have value of -1
        t.input_scalar("a0", -1)
    	# Calling the abs function
        t.call("abs")
    	# The a0 register is now the return value
    	# Checking if a0 is now 1
        t.check_scalar("a0", 1)
        t.execute()
    
    def test_zero(self):
        t = AssemblyTest(self, "abs.s")
        # load 0 into register a0
        t.input_scalar("a0", 0)
        # call the abs function
        t.call("abs")
        # check that after calling abs, a0 is equal to 0 (abs(0) = 0)
        t.check_scalar("a0", 0)
        # generate the `assembly/TestAbs_test_zero.s` file and run it through venus
        t.execute()

    def test_one(self):
        # same as test_zero, but with input 1
        t = AssemblyTest(self, "abs.s")
        t.input_scalar("a0", 1)
        t.call("abs")
        t.check_scalar("a0", 1)
        t.execute()

    @classmethod
    def tearDownClass(cls):
        print_coverage("abs.s", verbose=False)


class TestRelu(TestCase):
    def test_simple(self):
        t = AssemblyTest(self, "relu.s")
        # create an array in the data section
        array0 = t.array([1, -2, 3, -4, 5, -6, 7, -8, 9])
        # load address of `array0` into register a0
        t.input_array("a0", array0)
        # set a1 to the length of our array
        t.input_scalar("a1", len(array0))
        # call the relu function
        t.call("relu")
        # check that the array0 was changed appropriately
        t.check_array(array0, [1, 0, 3, 0, 5, 0, 7, 0, 9])
        # generate the `assembly/TestRelu_test_simple.s` file and run it through venus
        t.execute()

    def test_exit32(self):
        t = AssemblyTest(self, "relu.s")
        # create an array in the data section
        array0 = t.array([])
        # load address of `array0` into register a0
        t.input_array("a0", array0)
        # set a1 to the length of our array
        t.input_scalar("a1", len(array0))
        # call the relu function
        t.call("relu")
        # checks that program exits with code 32
        t.execute(code=32) 
        # generate the `assembly/TestRelu_test_simple.s` file and run it through venus
    def test_largeArray(self):
        t = AssemblyTest(self, "relu.s")
        # create an array in the data section
        array0 = t.array([-1, -2, -3, -4, -5, -6, -7, -8, -9, -10, -11, -12, -13, -14, 0])
        # load address of `array0` into register a0
        t.input_array("a0", array0)
        # set a1 to the length of our array
        t.input_scalar("a1", len(array0))
        # call the relu function
        t.call("relu")
        t.check_array(array0, [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0])
        t.execute() 
        # generate the `assembly/TestRelu_test_simple.s` file and run it through venus
    def test_oneElement(self):
        t = AssemblyTest(self, "relu.s")
        # create an array in the data section
        array0 = t.array([0])
        # load address of `array0` into register a0
        t.input_array("a0", array0)
        # set a1 to the length of our array
        t.input_scalar("a1", len(array0))
        # call the relu function
        t.call("relu")
        t.check_array(array0, [0])
        t.execute()
    
    def test_zero(self):
        t = AssemblyTest(self, "relu.s")
        # create an array in the data section
        array0 = t.array([0, 0, 0, 0, 0, 1, 0])
        # load address of `array0` into register a0
        t.input_array("a0", array0)
        # set a1 to the length of our array
        t.input_scalar("a1", len(array0))
        # call the relu function
        t.call("relu")
        t.check_array(array0, [0, 0, 0, 0, 0, 1, 0])
        t.execute()
    
    def test_wrongLength(self):
        t = AssemblyTest(self, "relu.s")
        # create an array in the data section
        array0 = t.array([0, 0, 0, 0, 0, 1, 0])
        # load address of `array0` into register a0
        t.input_array("a0", array0)
        # set a1 to the length of our array
        t.input_scalar("a1", 1)
        # call the relu function
        t.call("relu")
        t.check_array(array0, [0])
        t.execute()

    def test_negdim(self):
        t = AssemblyTest(self, "relu.s")
        # create an array in the data section
        array0 = t.array([0])
        # load address of `array0` into register a0
        t.input_array("a0", array0)
        # set a1 to the length of our array
        t.input_scalar("a1", -100)
        # call the relu function
        t.call("relu")
        t.check_array(array0, [0])
        t.execute(code=32)

    @classmethod
    def tearDownClass(cls):
        print_coverage("relu.s", verbose=False)


class TestArgmax(TestCase):
    def test_simple(self):
        t = AssemblyTest(self, "argmax.s")
        # create an array in the data section
        array0 = t.array([0, 1, 2, 3, 4, 5, 6, 7, 6, 5, 0, 10, 100, 2, 1, 0, 0])
        # TODO
        t.input_array("a0", array0)
        # TODO
        t.input_scalar("a1", len(array0))
        # TODO
        t.call("argmax")
        # TODO
        # check that the register a0 contains the correct output
        t.check_scalar("a0", 12)
        # TODO
        # generate the `assembly/TestArgmax_test_simple.s` file and run it through venus
        t.execute()

    def test_singleElement(self):
        t = AssemblyTest(self, "argmax.s")
        # create an array in the data section
        array0 = t.array([0])
        # TODO
        t.input_array("a0", array0)
        # TODO
        t.input_scalar("a1", len(array0))
        # TODO
        t.call("argmax")
        # TODO
        # check that the register a0 contains the correct output
        t.check_scalar("a0", 0)
        # TODO
        # generate the `assembly/TestArgmax_test_simple.s` file and run it through venus
        t.execute()

    def test_empty(self):
        t = AssemblyTest(self, "argmax.s")
        # create an array in the data section
        array0 = t.array([])
        # TODO
        t.input_array("a0", array0)
        # TODO
        t.input_scalar("a1", len(array0))
        # TODO
        t.call("argmax")
        # generate the `assembly/TestArgmax_test_simple.s` file and run it through venus
        t.execute(code=32)

    def test_last(self):
        t = AssemblyTest(self, "argmax.s")
        # create an array in the data section
        array0 = t.array([0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10])
        # TODO
        t.input_array("a0", array0)
        # TODO
        t.input_scalar("a1", len(array0))
        # TODO
        t.call("argmax")
        # TODO
        # check that the register a0 contains the correct output
        t.check_scalar("a0", 10)
        # TODO
        # generate the `assembly/TestArgmax_test_simple.s` file and run it through venus
        t.execute()

    def test_negdim(self):
        t = AssemblyTest(self, "argmax.s")
        # create an array in the data section
        array0 = t.array([0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10])
        # TODO
        t.input_array("a0", array0)
        # TODO
        t.input_scalar("a1", -100)
        # TODO
        t.call("argmax")
        # TODO
        # check that the register a0 contains the correct output
        t.check_scalar("a0", 10)
        # TODO
        # generate the `assembly/TestArgmax_test_simple.s` file and run it through venus
        t.execute(code=32)

    def test_first(self):
        t = AssemblyTest(self, "argmax.s")
        # create an array in the data section
        array0 = t.array([10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 9, 5, 4, 0])
        # TODO
        t.input_array("a0", array0)
        # TODO
        t.input_scalar("a1", len(array0))
        # TODO
        t.call("argmax")
        # TODO
        # check that the register a0 contains the correct output
        t.check_scalar("a0", 0)
        # TODO
        # generate the `assembly/TestArgmax_test_simple.s` file and run it through venus
        t.execute()

    def test_negativearray(self):
        t = AssemblyTest(self, "argmax.s")
        # create an array in the data section
        array0 = t.array([-10, -9, -8, -7, -6, -5, -4, -3, -2, -1, -0, -9, -5, -4, -0])
        # TODO
        t.input_array("a0", array0)
        # TODO
        t.input_scalar("a1", len(array0))
        # TODO
        t.call("argmax")
        # TODO
        # check that the register a0 contains the correct output
        t.check_scalar("a0", 10)
        # TODO
        # generate the `assembly/TestArgmax_test_simple.s` file and run it through venus
        t.execute()

    def test_mulmax(self):
        t = AssemblyTest(self, "argmax.s")
        # create an array in the data section
        array0 = t.array([100, 100, 100, 100, 100, 100, 100, 100])
        # TODO
        t.input_array("a0", array0)
        # TODO
        t.input_scalar("a1", len(array0))
        # TODO
        t.call("argmax")
        # TODO
        # check that the register a0 contains the correct output
        t.check_scalar("a0", 0)
        # TODO
        # generate the `assembly/TestArgmax_test_simple.s` file and run it through venus
        t.execute()

    def test_negativearrayAndmulmax(self):
        t = AssemblyTest(self, "argmax.s")
        # create an array in the data section
        array0 = t.array([-100, 100, -100, 100, -100, 100, -100, 100, 9, 900, 900])
        # TODO
        t.input_array("a0", array0)
        # TODO
        t.input_scalar("a1", len(array0))
        # TODO
        t.call("argmax")
        # TODO
        # check that the register a0 contains the correct output
        t.check_scalar("a0", 9)
        # TODO
        # generate the `assembly/TestArgmax_test_simple.s` file and run it through venus
        t.execute()


    @classmethod
    def tearDownClass(cls):
        print_coverage("argmax.s", verbose=False)


class TestDot(TestCase):
    def test_simple(self):
        t = AssemblyTest(self, "dot.s")
        # create arrays in the data section
        array0 = t.array([0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10])
        array1 = t.array([0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10])
        t.input_array("a0", array0)
        t.input_array("a1", array1)
        t.input_scalar("a2", len(array0))
        t.input_scalar("a3", 1)
        t.input_scalar("a4", 1)
        t.call("dot")
        t.check_scalar("a0", 385)
        t.execute()

    def test_negative(self):
        t = AssemblyTest(self, "dot.s")
        # create arrays in the data section
        array0 = t.array([0, -1, -2, -3, -4, -5, -6, -7, -8, -9, -10])
        array1 = t.array([0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10])
        t.input_array("a0", array0)
        t.input_array("a1", array1)
        t.input_scalar("a2", len(array0))
        t.input_scalar("a3", 1)
        t.input_scalar("a4", 1)
        t.call("dot")
        t.check_scalar("a0", -385)
        t.execute()

    def test_empty(self):
        t = AssemblyTest(self, "dot.s")
        # create arrays in the data section
        array0 = t.array([])
        array1 = t.array([])
        t.input_array("a0", array0)
        t.input_array("a1", array1)
        t.input_scalar("a2", len(array0))
        t.input_scalar("a3", 1)
        t.input_scalar("a4", 1)
        t.call("dot")
        t.execute(code=32)

    def test_negdim(self):
        t = AssemblyTest(self, "dot.s")
        # create arrays in the data section
        array0 = t.array([])
        array1 = t.array([])
        t.input_array("a0", array0)
        t.input_array("a1", array1)
        t.input_scalar("a2", -100)
        t.input_scalar("a3", 1)
        t.input_scalar("a4", 1)
        t.call("dot")
        t.execute(code=32)

    def test_array1stride(self):
        t = AssemblyTest(self, "dot.s")
        # create arrays in the data section
        array0 = t.array([0, -1, -2, -3, -4, -5, -6, -7, -8, -9, -10])
        array1 = t.array([0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10])
        t.input_array("a0", array0)
        t.input_array("a1", array1)
        t.input_scalar("a2", len(array0))
        t.input_scalar("a3", 0)
        t.input_scalar("a4", 1)
        t.call("dot")
        t.execute(code=33)

    def test_array1Negstride(self):
        t = AssemblyTest(self, "dot.s")
        # create arrays in the data section
        array0 = t.array([0, -1, -2, -3, -4, -5, -6, -7, -8, -9, -10])
        array1 = t.array([0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10])
        t.input_array("a0", array0)
        t.input_array("a1", array1)
        t.input_scalar("a2", len(array0))
        t.input_scalar("a3", -10)
        t.input_scalar("a4", 1)
        t.call("dot")
        t.execute(code=33)

    def test_array2stride(self):
        t = AssemblyTest(self, "dot.s")
        # create arrays in the data section
        array0 = t.array([0, -1, -2, -3, -4, -5, -6, -7, -8, -9, -10])
        array1 = t.array([0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10])
        t.input_array("a0", array0)
        t.input_array("a1", array1)
        t.input_scalar("a2", len(array0))
        t.input_scalar("a3", 1)
        t.input_scalar("a4", 0)
        t.call("dot")
        t.execute(code=33)

    def test_array2Negstride(self):
        t = AssemblyTest(self, "dot.s")
        # create arrays in the data section
        array0 = t.array([0, -1, -2, -3, -4, -5, -6, -7, -8, -9, -10])
        array1 = t.array([0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10])
        t.input_array("a0", array0)
        t.input_array("a1", array1)
        t.input_scalar("a2", len(array0))
        t.input_scalar("a3", 1)
        t.input_scalar("a4", -10)
        t.call("dot")
        t.execute(code=33)

    def test_moresample(self):
        t = AssemblyTest(self, "dot.s")
        # create arrays in the data section
        array0 = t.array([100, 20, 33, 8, -9, 1, 20, 5, 7])
        array1 = t.array([0, 1, 2, 3, 4, 5, 6, 7, 8])
        t.input_array("a0", array0)
        t.input_array("a1", array1)
        t.input_scalar("a2", len(array0))
        t.input_scalar("a3", 1)
        t.input_scalar("a4", 1)
        t.call("dot")
        t.check_scalar("a0", 20+66+24-36+5+120+35+56)
        t.execute()

    def test_stride1(self):
        t = AssemblyTest(self, "dot.s")
        # create arrays in the data section
        array0 = t.array([0, -1, -2, 3, -4, 5])
        array1 = t.array([0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10])
        t.input_array("a0", array0)
        t.input_array("a1", array1)
        t.input_scalar("a2", len(array0))
        t.input_scalar("a3", 1)
        t.input_scalar("a4", 2)
        t.call("dot")
        t.check_scalar("a0", 0-2-8+18-32+50)
        t.execute()
    
    def test_stride2(self):
        t = AssemblyTest(self, "dot.s")
        # create arrays in the data section
        array0 = t.array([0, -1, -2, 3, -4, 5])
        array1 = t.array([0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10])
        t.input_array("a0", array0)
        t.input_array("a1", array1)
        t.input_scalar("a2", 1)
        t.input_scalar("a3", 1)
        t.input_scalar("a4", 2)
        t.call("dot")
        t.check_scalar("a0", 0)
        t.execute()
    
    def test_stride3(self):
        t = AssemblyTest(self, "dot.s")
        # create arrays in the data section
        array0 = t.array([0, -1, -2, 3, -4, 5, 8])
        array1 = t.array([0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10])
        t.input_array("a0", array0)
        t.input_array("a1", array1)
        t.input_scalar("a2", 3)
        t.input_scalar("a3", 2)
        t.input_scalar("a4", 2)
        t.call("dot")
        t.check_scalar("a0", 0-4-16)
        t.execute()

    def test_nolength(self):
        t = AssemblyTest(self, "dot.s")
        # create arrays in the data section
        array0 = t.array([0, -1, -2, 3, -4, 5])
        array1 = t.array([0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10])
        t.input_array("a0", array0)
        t.input_array("a1", array1)
        t.input_scalar("a2", 4)
        t.input_scalar("a3", 1)
        t.input_scalar("a4", 2)
        t.call("dot")
        t.check_scalar("a0", 0-2-8+18)
        t.execute()

    @classmethod
    def tearDownClass(cls):
        print_coverage("dot.s", verbose=False)


class TestMatmul(TestCase):

    def do_matmul(self, m0, m0_rows, m0_cols, m1, m1_rows, m1_cols, result, code=0):
        t = AssemblyTest(self, "matmul.s")
        # we need to include (aka import) the dot.s file since it is used by matmul.s
        t.include("dot.s")

        # create arrays for the arguments and to store the result
        array0 = t.array(m0)
        array1 = t.array(m1)
        array_out = t.array([0] * len(result))

        # load address of input matrices and set their dimensions
        t.input_array("a0", array0)
        t.input_scalar("a1", m0_rows)
        t.input_scalar("a2", m0_cols)
        t.input_array("a3", array1)
        t.input_scalar("a4", m1_rows)
        t.input_scalar("a5", m1_cols)
        # TODO
        t.input_array("a6", array_out)
        # TODO

        # call the matmul function
        t.call("matmul")

        # check the content of the output array
        # TODO
        t.check_array(array_out, result)
        # generate the assembly file and run it through venus, we expect the simulation to exit with code `code`
        t.execute(code=code)

    def test_simple(self):
        self.do_matmul(
            [1, 2, 3, 4, 5, 6, 7, 8, 9], 3, 3,
            [1, 2, 3, 4, 5, 6, 7, 8, 9], 3, 3,
            [30, 36, 42, 66, 81, 96, 102, 126, 150]
        )

    def test_m0colError(self):
        self.do_matmul(
            [1, 2, 3, 4, 5, 6, 7, 8, 9], -1, 3,
            [1, 2, 3, 4, 5, 6, 7, 8, 9], 3, 3,
            [30, 36, 42, 66, 81, 96, 102, 126, 150], 34
        )

    def test_m0rowError(self):
        self.do_matmul(
            [1, 2, 3, 4, 5, 6, 7, 8, 9], 3, -1,
            [1, 2, 3, 4, 5, 6, 7, 8, 9], 3, 3,
            [30, 36, 42, 66, 81, 96, 102, 126, 150], 34
        )

    def test_m1colError(self):
        self.do_matmul(
            [1, 2, 3, 4, 5, 6, 7, 8, 9], 3, 3,
            [1, 2, 3, 4, 5, 6, 7, 8, 9], -1, 3,
            [30, 36, 42, 66, 81, 96, 102, 126, 150], 34
        )

    def test_m1rowError(self):
        self.do_matmul(
            [1, 2, 3, 4, 5, 6, 7, 8, 9], 3, 3,
            [1, 2, 3, 4, 5, 6, 7, 8, 9], 3, -1,
            [30, 36, 42, 66, 81, 96, 102, 126, 150], 34
        )

    def test_notMatch(self):
        self.do_matmul(
            [1, 2, 3, 4, 5, 6, 7, 8, 9], 9, 1,
            [1, 2, 3, 4, 5, 6, 7, 8, 9], 3, 3,
            [30, 36, 42, 66, 81, 96, 102, 126, 150], 34
        )

    def test_negative(self):
        self.do_matmul(
            [-1, -2, -3, -4, -5, -6, -7, -8, -9], 3, 3,
            [1, 2, 3, 4, 5, 6, 7, 8, 9], 3, 3,
            [-30, -36, -42, -66, -81, -96, -102, -126, -150]
        )

    def test_identity(self):
        self.do_matmul(
            [1, 0, 0, 0, 1, 0, 0, 0, 1], 3, 3,
            [1, 2, 3, 4, 5, 6, 7, 8, 9], 3, 3,
            [1, 2, 3, 4, 5, 6, 7, 8, 9]
        )

    def test_notSquare1(self):
        self.do_matmul(
            [1, 2, 3], 3, 1,
            [1, 2, 3, 4, 5, 6, 7, 8, 9], 1, 9,
            [1, 2, 3, 4, 5, 6, 7, 8, 9, 2, 4, 6, 8, 10, 12, 14, 16, 18, 3, 6, 9, 12, 15, 18, 21, 24, 27]
        )

    def test_notSquare2(self):
        self.do_matmul(
            [1, 1, 1, 1, 1, 1, 1, 1, 1], 1, 9,
            [1, 2, 3, 4, 5, 6, 7, 8, 9], 9, 1,
            [45]
        )

    def test_notSquare3(self):
        self.do_matmul(
            [1, -8, 4, 7, 20, -4, 99, -27], 2, 4,
            [1, 22, 3, -4, -5, 6, 77, 8], 4, 2,
            [496, 134, -2566, 834]
        )
    
    def test_notSquare4(self):
        self.do_matmul(
            [1, 2, 9, 3, 0, 8, 2, 5, 6], 3, 3,
            [22, 4, 15], 3, 1,
            [165, 186, 154]
        )
    
    def test_notSquare5error(self):
        self.do_matmul(
            [1, 2, 9, 3, 0, 8, 2, 5, 6], 0, 3,
            [22, 4, 15], 3, 1,
            [165, 186, 154], 34
        )

    def test_length1vector1(self):
        self.do_matmul(
            [1], 1, 1,
            [0], 1, 1,
            [0]
        )

    def test_length1vector2(self):
        self.do_matmul(
            [1 ,2 ,3 ,4 ,5 ,6 ,7, 8, 9], 9, 1,
            [0], 1, 1,
            [0, 0, 0, 0, 0, 0, 0, 0, 0]
        )

    def test_length1vector3(self):
        self.do_matmul(
            [1 ,2 ,3 ,4 ,5 ,6 ,7, 8, 9], 9, 1,
            [1], 1, 1,
            [1, 2, 3, 4, 5, 6, 7, 8, 9]
        )
    
    def test_length1vector4(self):
        self.do_matmul(
            [0], 1, 1,
            [0], 1, 1,
            [0]
        )
    
    def test_length1vector5(self):
        self.do_matmul(
            [1 ,2 ,4 ,4 ,5 ,6 ,7, 8, 9], 9, 1,
            [2], 1, 1,
            [2, 4, 8, 8, 10, 12, 14, 16, 18]
        )

    def test_length1vector6(self):
        self.do_matmul(
            [2], 1, 1,
            [1 ,2 ,4 ,4 ,5 ,6 ,7, 8, 9], 1, 9,
            [2, 4, 8, 8, 10, 12, 14, 16, 18]
        )
    
    def test_length1vector7(self):
        self.do_matmul(
            [0], 1, 1,
            [1 ,2 ,4 ,4 ,5 ,6 ,7, 8, 9], 1, 9,
            [0, 0, 0, 0, 0, 0, 0, 0, 0]
        )
    
    def test_length1vector8(self):
        self.do_matmul(
            [-1], 1, 1,
            [1 ,2 ,4 ,4 ,5 ,6 ,7, 8, 9], 1, 9,
            [-1 ,-2 ,-4 ,-4 ,-5 ,-6 ,-7, -8, -9]
        )




    @classmethod
    def tearDownClass(cls):
        print_coverage("matmul.s", verbose=False)


class TestReadMatrix(TestCase):

    def do_read_matrix(self, fail='', code=0):
        t = AssemblyTest(self, "read_matrix.s")
        # load address to the name of the input file into register a0
        t.input_read_filename("a0", "inputs/test_read_matrix/test_input.bin")

        # allocate space to hold the rows and cols output parameters
        rows = t.array([-1])
        cols = t.array([-1])

        # load the addresses to the output parameters into the argument registers
        t.input_array("a1", rows)
        t.input_array("a2", cols)
        # call the read_matrix function
        t.call("read_matrix")

        t.check_array(rows, [3])
        t.check_array(cols, [3])
        t.check_array_pointer("a0", [1,2,3,4,5,6,7,8,9])

        # generate assembly and run it through venus
        t.execute(fail=fail, code=code)

    def test_simple(self):
        self.do_read_matrix()
        self.do_read_matrix('malloc', 48)
        self.do_read_matrix('fopen', 64)
        self.do_read_matrix('fread', 66)
        self.do_read_matrix('fclose', 65)

    @classmethod
    def tearDownClass(cls):
        print_coverage("read_matrix.s", verbose=False)


class TestWriteMatrix(TestCase):

    def do_write_matrix(self, fail='', code=0):
        t = AssemblyTest(self, "write_matrix.s")
        outfile = "outputs/test_write_matrix/student.bin"
        # load output file name into a0 register
        t.input_write_filename("a0", outfile)
        # load input array and other arguments
        matrix = t.array([1, 2, 3, 4, 5, 6, 7, 8, 9])
        t.input_array("a1", matrix)
        t.input_scalar("a2", 3)
        t.input_scalar("a3", 3)
        # call `write_matrix` function
        t.call("write_matrix")
        # generate assembly and run it through venus
        t.execute(fail=fail, code=code)
        # compare the output file against the reference
        if not fail:
            t.check_file_output(outfile, "outputs/test_write_matrix/reference.bin")

    def test_simple(self):
        self.do_write_matrix()
        self.do_write_matrix('fopen', 64)
        self.do_write_matrix('fwrite', 67)
        self.do_write_matrix('fclose', 65)

    @classmethod
    def tearDownClass(cls):
        print_coverage("write_matrix.s", verbose=False)


class TestClassify(TestCase):

    def make_test(self):
        t = AssemblyTest(self, "classify.s")
        t.include("argmax.s")
        t.include("dot.s")
        t.include("matmul.s")
        t.include("read_matrix.s")
        t.include("relu.s")
        t.include("write_matrix.s")
        return t

    def test_simple0_input0(self):
        t = self.make_test()
        out_file = "outputs/test_basic_main/student0.bin"
        ref_file = "outputs/test_basic_main/reference0.bin"
        args = ["inputs/simple0/bin/m0.bin", "inputs/simple0/bin/m1.bin",
                "inputs/simple0/bin/inputs/input0.bin", out_file]
        # call classify function
        t.call("classify")
        # generate assembly and pass program arguments directly to venus
        t.check_scalar("a0", 2)
        t.execute(args=args)

        # compare the output file and
        # TODO
        t.check_stdout('2')
        # compare the classification output with `check_stdout`

    def error35(self):
        t = self.make_test()
        out_file = "outputs/test_basic_main/student0.bin"
        ref_file = "outputs/test_basic_main/reference0.bin"
        args = ["inputs/simple0/bin/m0.bin", "inputs/simple0/bin/m1.bin",
                out_file]
        t.call("classify")
        t.execute(args=args, code=35)

    def error48(self):
        t = self.make_test()
        out_file = "outputs/test_basic_main/student0.bin"
        ref_file = "outputs/test_basic_main/reference0.bin"
        args = ["inputs/simple0/bin/m0.bin", "inputs/simple0/bin/m1.bin",
                "inputs/simple0/bin/inputs/input0.bin", out_file]
        # call classify function
        t.call("classify")
        t.execute(args=args, fail='malloc', code=48)

    @classmethod
    def tearDownClass(cls):
        print_coverage("classify.s", verbose=False)


# The following are some simple sanity checks:
import subprocess, pathlib, os
script_dir = pathlib.Path(os.path.dirname(__file__)).resolve()

def compare_files(test, actual, expected):
    assert os.path.isfile(expected), f"Reference file {expected} does not exist!"
    test.assertTrue(os.path.isfile(actual), f"It seems like the program never created the output file {actual}!")
    # open and compare the files
    with open(actual, 'rb') as a:
        actual_bin = a.read()
    with open(expected, 'rb') as e:
        expected_bin = e.read()
    test.assertEqual(actual_bin, expected_bin, f"Bytes of {actual} and {expected} did not match!")

class TestMain(TestCase):
    """ This sanity check executes src/main.S using venus and verifies the stdout and the file that is generated.
    """

    def run_main(self, inputs, output_id, label):
        args = [f"{inputs}/m0.bin", f"{inputs}/m1.bin",
                f"{inputs}/inputs/input0.bin",
                f"outputs/test_basic_main/student{output_id}.bin"]
        reference = f"outputs/test_basic_main/reference{output_id}.bin"

        t= AssemblyTest(self, "main.s", no_utils=True)
        t.call("main")
        t.execute(args=args)
        t.check_stdout(label)
        t.check_file_output(args[-1], reference)

    def test0(self):
        self.run_main("inputs/simple0/bin", "0", "2")

    def test1(self):
        self.run_main("inputs/simple1/bin", "1", "1")

    def test2(self):
        self.run_main("inputs/simple0/bin", "0", "2")
        self.run_main("inputs/simple1/bin", "1", "1")
