from utils import *
from unittest import TestCase
import dumbpy as dp
import numc as nc
import numpy as np

"""
- For each operation, you should write tests to test  on matrices of different sizes.
- Keep in mind that the tests provided in the starter code are NOT comprehensive. That is, we strongly
advise you to modify them and add new tests.
- Hint: use dp_mc_matrix to generate dumbpy and numc matrices with the same data and use
      cmp_dp_nc_matrix to compare the results
"""
class TestAdd(TestCase):
    def test_add(self):
        # TODO: YOUR CODE HERE
        dp_mat1, nc_mat1 = rand_dp_nc_matrix(2, 2, seed=0)
        dp_mat2, nc_mat2 = rand_dp_nc_matrix(2, 2, seed=1)
        is_correct, speed_up = compute([dp_mat1, dp_mat2], [nc_mat1, nc_mat2], "add")
        self.assertTrue(is_correct)
    
    def test_nonsquare_add1(self):
        # TODO: YOUR CODE HERE
        dp_mat1, nc_mat1 = rand_dp_nc_matrix(2, 1, seed=0)
        dp_mat2, nc_mat2 = rand_dp_nc_matrix(2, 1, seed=1)
        is_correct, speed_up = compute([dp_mat1, dp_mat2], [nc_mat1, nc_mat2], "add")
        self.assertTrue(is_correct)
    
    def test_nonsquare_add2(self):
        # TODO: YOUR CODE HERE
        dp_mat1, nc_mat1 = rand_dp_nc_matrix(73, 1, seed=0)
        dp_mat2, nc_mat2 = rand_dp_nc_matrix(73, 1, seed=1)
        is_correct, speed_up = compute([dp_mat1, dp_mat2], [nc_mat1, nc_mat2], "add")
        self.assertTrue(is_correct)
    
    def test_nonsquare_add3(self):
        # TODO: YOUR CODE HERE
        dp_mat1, nc_mat1 = rand_dp_nc_matrix(1987, 1237, seed=0)
        dp_mat2, nc_mat2 = rand_dp_nc_matrix(1987, 1237, seed=1)
        is_correct, speed_up = compute([dp_mat1, dp_mat2], [nc_mat1, nc_mat2], "add")
        self.assertTrue(is_correct)

# (OPTIONAL) Uncomment the following TestSub class if you have implemented matrix subtraction.
# class TestSub(TestCase):
#    def test_small_sub(self):
#        # TODO: YOUR CODE HERE
#        dp_mat1, nc_mat1 = rand_dp_nc_matrix(2, 2, seed=0)
#        dp_mat2, nc_mat2 = rand_dp_nc_matrix(2, 2, seed=1)
#        is_correct, speed_up = compute([dp_mat1, dp_mat2], [nc_mat1, nc_mat2], "sub")
#        self.assertTrue(is_correct)
#        try:
#            nc.Matrix(3, 3) - nc.Matrix(2, 2)
#            self.assertTrue(False)
#        except ValueError as e:
#            print(e)
#            pass
#        print_speedup(speed_up)
#
#    def test_medium_sub(self):
#        # TODO: YOUR CODE HERE
#        pass
#
#    def test_large_sub(self):
#        # TODO: YOUR CODE HERE
#        pass

class TestAbs(TestCase):
    def test_small_abs1(self):
        # TODO: YOUR CODE HERE
        dp_mat, nc_mat = rand_dp_nc_matrix(3, 3, seed=0)
        is_correct, speed_up = compute([dp_mat], [nc_mat], "abs")
        self.assertTrue(is_correct)
    
    def test_small_abs2(self):
        # TODO: YOUR CODE HERE
        dp_mat, nc_mat = rand_dp_nc_matrix(73, 1, seed=0)
        is_correct, speed_up = compute([dp_mat], [nc_mat], "abs")
        self.assertTrue(is_correct)
    

# (OPTIONAL) Uncomment the following TestNeg class if you have implemented matrix negation.
# class TestNeg(TestCase):
#    def test_small_neg(self):
#        # TODO: YOUR CODE HERE
#        dp_mat, nc_mat = rand_dp_nc_matrix(2, 2, seed=0)
#        is_correct, speed_up = compute([dp_mat], [nc_mat], "neg")
#        self.assertTrue(is_correct)
#        print_speedup(speed_up)
#    def test_medium_neg(self):
#        # TODO: YOUR CODE HERE
#        pass

#    def test_large_neg(self):
#        # TODO: YOUR CODE HERE
#        pass

class TestMul(TestCase):
    def test_small_mul(self):
        # TODO: YOUR CODE HERE
        dp_mat1, nc_mat1 = rand_dp_nc_matrix(2, 2, seed=0)
        dp_mat2, nc_mat2 = rand_dp_nc_matrix(2, 2, seed=1)
        is_correct, speed_up = compute([dp_mat1, dp_mat2], [nc_mat1, nc_mat2], "mul")
        self.assertTrue(is_correct)

class TestPow(TestCase):
    def test_small_pow(self):
        # TODO: YOUR CODE HERE
        dp_mat, nc_mat = rand_dp_nc_matrix(3, 3, seed=0)
        is_correct, speed_up = compute([dp_mat, 3], [nc_mat, 3], "pow")
        self.assertTrue(is_correct)

    def test_zero_pow(self):
        dp_mat, nc_mat = rand_dp_nc_matrix(4, 4, seed=0)
        is_correct, speed_up = compute([dp_mat, 0], [nc_mat, 0], "pow")
        self.assertTrue(is_correct)

    def test_one_pow(self):
        dp_mat, nc_mat = rand_dp_nc_matrix(5, 5, seed=0)
        is_correct, speed_up = compute([dp_mat, 1], [nc_mat, 1], "pow")
        self.assertTrue(is_correct)
    
    def test_2_pow(self):
        dp_mat, nc_mat = rand_dp_nc_matrix(2, 2, seed=20)
        is_correct, speed_up = compute([dp_mat, 4], [nc_mat, 4], "pow")
        self.assertTrue(is_correct)
    
    def test_1000_pow(self):
        dp_mat, nc_mat = dp_nc_matrix([[1, 1], [0, 1]])
        is_correct, speed_up = compute([dp_mat, 1000], [nc_mat, 1000], "pow")
        self.assertTrue(is_correct)
        print_speedup(speed_up)

class TestGet(TestCase):
    def test_get(self):
        # TODO: YOUR CODE HERE
        dp_mat, nc_mat = rand_dp_nc_matrix(2, 2, seed=0)
        rand_row = np.random.randint(dp_mat.shape[0])
        rand_col = np.random.randint(dp_mat.shape[1])
        self.assertEqual(round(dp_mat.get(rand_row, rand_col), decimal_places),
            round(nc_mat.get(rand_row, rand_col), decimal_places))

class TestSet(TestCase):
    def test_set(self):
        # TODO: YOUR CODE HERE
        dp_mat, nc_mat = rand_dp_nc_matrix(2, 2, seed=0)
        rand_row = np.random.randint(dp_mat.shape[0])
        rand_col = np.random.randint(dp_mat.shape[1])
        dp_mat.set(rand_row, rand_col, 2)
        nc_mat.set(rand_row, rand_col, 2)
        self.assertTrue(cmp_dp_nc_matrix(dp_mat, nc_mat))

class TestShape(TestCase):
    def test_shape(self):
        # TODO: YOUR CODE HERE
        dp_mat, nc_mat = rand_dp_nc_matrix(2, 2, seed=0)
        self.assertTrue(dp_mat.shape == nc_mat.shape)

class TestIndexGet(TestCase):
    def test_index_get(self):
        # TODO: YOUR CODE HERE
        dp_mat, nc_mat = rand_dp_nc_matrix(2, 2, seed=0)
        rand_row = np.random.randint(dp_mat.shape[0])
        rand_col = np.random.randint(dp_mat.shape[1])
        self.assertEqual(round(dp_mat[rand_row][rand_col], decimal_places),
            round(nc_mat[rand_row][rand_col], decimal_places))

class TestIndexSet(TestCase):
    def test_set(self):
        # TODO: YOUR CODE HERE
        dp_mat, nc_mat = rand_dp_nc_matrix(2, 2, seed=0)
        rand_row = np.random.randint(dp_mat.shape[0])
        rand_col = np.random.randint(dp_mat.shape[1])
        dp_mat[rand_row][rand_col] = 2
        nc_mat[rand_row][rand_col] = 2
        self.assertTrue(cmp_dp_nc_matrix(dp_mat, nc_mat))
        self.assertEqual(nc_mat[rand_row][rand_col], 2)

class TestSlice(TestCase):
    def test_slice(self):
        # TODO: YOUR CODE HERE
        dp_mat, nc_mat = rand_dp_nc_matrix(2, 2, seed=0)
        self.assertTrue(cmp_dp_nc_matrix(dp_mat[0], nc_mat[0]))
        self.assertTrue(cmp_dp_nc_matrix(dp_mat[1], nc_mat[1]))

    def test_double_slice(self):
        # TODO: YOUR CODE HERE
        dp_mat, nc_mat = rand_dp_nc_matrix(4, 5, seed=0)
        self.assertTrue(dp_mat[0][1] == nc_mat[0][1])
        self.assertTrue(dp_mat[1][3] == nc_mat[1][3])

class TestPerformance(TestCase):
    # test add operation performance.
    def test_medium_add_performance(self):
        dp_mat1, nc_mat1 = rand_dp_nc_matrix(1000, 1000, seed=0)
        dp_mat2, nc_mat2 = rand_dp_nc_matrix(1000, 1000, seed=1)
        is_correct, speed_up = compute([dp_mat1, dp_mat2], [nc_mat1, nc_mat2], "add")
        self.assertTrue(is_correct)
        print_speedup(speed_up)

    def test_large_add_performance(self):
        dp_mat1, nc_mat1 = rand_dp_nc_matrix(10000, 10000, seed=0)
        dp_mat2, nc_mat2 = rand_dp_nc_matrix(10000, 10000, seed=1)
        is_correct, speed_up = compute([dp_mat1, dp_mat2], [nc_mat1, nc_mat2], "add")
        self.assertTrue(is_correct)
        print_speedup(speed_up)

    # test abs operation performance.
    def test_medium_abs_performance(self):
        dp_mat, nc_mat = rand_dp_nc_matrix(1000, 1000, seed=2)
        is_correct, speed_up = compute([dp_mat], [nc_mat], "abs")
        self.assertTrue(is_correct)
        print_speedup(speed_up)

    def test_large_abs_performance(self):
        dp_mat, nc_mat = rand_dp_nc_matrix(10000, 10000, seed=2)
        is_correct, speed_up = compute([dp_mat], [nc_mat], "abs")
        self.assertTrue(is_correct)
        print_speedup(speed_up)
    
    # test mul operation performance.
    def test_medium_mul_performance(self):
        dp_mat1, nc_mat1 = rand_dp_nc_matrix(100, 100, seed=0)
        dp_mat2, nc_mat2 = rand_dp_nc_matrix(100, 100, seed=1)
        is_correct, speed_up = compute([dp_mat1, dp_mat2], [nc_mat1, nc_mat2], "mul")
        self.assertTrue(is_correct)
        print_speedup(speed_up)

    def test_large_mul_performance(self):
        dp_mat1, nc_mat1 = rand_dp_nc_matrix(1000, 1000, seed=0)
        dp_mat2, nc_mat2 = rand_dp_nc_matrix(1000, 1000, seed=1)
        is_correct, speed_up = compute([dp_mat1, dp_mat2], [nc_mat1, nc_mat2], "mul")
        self.assertTrue(is_correct)
        print_speedup(speed_up)
    
    # test pow operation performance
    def test_medium_pow_performance(self):
        dp_mat, nc_mat = rand_dp_nc_matrix(100, 100, seed=0)
        is_correct, speed_up = compute([dp_mat, 1000], [nc_mat, 1000], "pow")
        self.assertTrue(is_correct)
        print_speedup(speed_up)

    def test_large_pow_performance(self):
        dp_mat, nc_mat = rand_dp_nc_matrix(1000, 1000, seed=1)
        is_correct, speed_up = compute([dp_mat, 10], [nc_mat, 10], "pow")
        self.assertTrue(is_correct)
        print_speedup(speed_up)
    



