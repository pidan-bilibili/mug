#include "matrix.h"
#include <stddef.h>
#include <stdio.h>
#include <stdlib.h>
#include <omp.h>

// Include SSE intrinsics
#if defined(_MSC_VER)
#include <intrin.h>
#elif defined(__GNUC__) && (defined(__x86_64__) || defined(__i386__))
#include <immintrin.h>
#include <x86intrin.h>
#endif

/* Below are some intel intrinsics that might be useful
 * void _mm256_storeu_pd (double * mem_addr, __m256d a)
 * __m256d _mm256_set1_pd (double a)
 * __m256d _mm256_set_pd (double e3, double e2, double e1, double e0)
 * __m256d _mm256_loadu_pd (double const * mem_addr)
 * __m256d _mm256_add_pd (__m256d a, __m256d b)
 * __m256d _mm256_sub_pd (__m256d a, __m256d b)
 * __m256d _mm256_fmadd_pd (__m256d a, __m256d b, __m256d c)
 * __m256d _mm256_mul_pd (__m256d a, __m256d b)
 * __m256d _mm256_cmp_pd (__m256d a, __m256d b, const int imm8)
 * __m256d _mm256_and_pd (__m256d a, __m256d b)
 * __m256d _mm256_max_pd (__m256d a, __m256d b)
*/

/* Generates a random double between low and high */
double rand_double(double low, double high) {
    double range = (high - low);
    double div = RAND_MAX / range;
    return low + (rand() / div);
}

/* Generates a random matrix */
void rand_matrix(matrix *result, unsigned int seed, double low, double high) {
    srand(seed);
    for (int i = 0; i < result->rows; i++) {
        for (int j = 0; j < result->cols; j++) {
            set(result, i, j, rand_double(low, high));
        }
    }
}

/*
 * Allocates space for a matrix struct pointed to by the double pointer mat with
 * `rows` rows and `cols` columns. You should also allocate memory for the data array
 * and initialize all entries to be zeros. `parent` should be set to NULL to indicate that
 * this matrix is not a slice. You should also set `ref_cnt` to 1.
 * You should return -1 if either `rows` or `cols` or both have invalid values. Return -2 if any
 * call to allocate memory in this function fails. Remember to set the error messages in numc.c.
 * Return 0 upon success.
 */
int allocate_matrix(matrix **mat, int rows, int cols) {
    /* TODO: YOUR CODE HERE */
    if (rows <= 0 || cols <= 0) {
        return -1;
    }

    *mat = malloc(sizeof(struct matrix));
    if (mat == NULL || *mat == NULL) {
        return -2;
    }

    (*mat)->rows = rows;
    (*mat)->cols = cols;

    int total_size_of_mat = rows * cols;
    (*mat)->data = (double *) calloc(total_size_of_mat, sizeof(double));
    if ((*mat)->data == NULL) {
        free(mat);
        return -2;
    }

    (*mat)->ref_cnt = (int *) malloc(sizeof(int));
    if ((*mat)->ref_cnt == NULL) {
        free(mat);
        free((*mat)->data);
        return -2;
    }

    *((*mat)->ref_cnt) = 1;
    (*mat)->parent = NULL;
    (*mat)->reference = 0;
    return 0;
}

/*
 * Allocates space for a matrix struct pointed to by `mat` with `rows` rows and `cols` columns.
 * Its data should point to the `offset`th entry of `from`'s data (you do not need to allocate memory)
 * for the data field. `parent` should be set to `from` to indicate this matrix is a slice of `from`.
 * You should return -1 if either `rows` or `cols` or both have invalid values. Return -2 if any
 * call to allocate memory in this function fails.
 * Remember to set the error messages in numc.c.
 * Return 0 upon success.
 */
int allocate_matrix_ref(matrix **mat, matrix *from, int offset, int rows, int cols) {
    /* TODO: YOUR CODE HERE */
    if (rows <= 0 || cols <= 0 || from == NULL) {
        return -1;
    }

    if (rows * cols > (from->rows * from->cols) - offset) {
        return -3;
    }

    *mat = malloc(sizeof(struct matrix));
    if (mat == NULL || *mat == NULL) {
        return -2;
    }

    (*mat)->rows = rows;
    (*mat)->cols = cols;
    (*mat)->data = (from->data) + offset;
    (*mat)->parent = from;
    *(from->ref_cnt) += 1;

    (*mat)->ref_cnt = from->ref_cnt;
    from->reference = 1;
    (*mat)->reference = 0;

    return 0;
}

/*
 * You need to make sure that you only free `mat->data` if `mat` is not a slice and has no existing slices,
 * or that you free `mat->parent->data` if `mat` is the last existing slice of its parent matrix and its parent matrix has no other references
 * (including itself). You cannot assume that mat is not NULL.
 */

/*
    example: we have a matrix A and a slice B
    Case I:
        if I call deallocate_matrix(A) or deallocate_matrix(B) independently, nothing will happen.
        
    Case I:
        deallocate_matrix(A)
        after this matrix A->ref_cnt == 1 (which represent there is still one matrix (B) using this data)
        we will not free the structure of A.
        deallocate_matrix(B)
        after this matrix B-ref_cnt == 0, and A->ref_cnt decrease by 1 (means B no longer need A->data).
        we will check whether A->ref_cnt == 0 or not. if the statement is false: we will only free(B)
        else: we will first free(B->parent->data), then free(B->parent), the will free(B).
              we are not going to free B->data, since it is already freed when we call free(B->parent->data).
    Case II:
        deallocate_matrix(B)
        after this matrix B->ref_cnt == 0, and matrix A->ref_cnt will decrease by 1 since matrix B no longer needs these data.
        we will free(B)
        deallocate_matrix(A)
        after this matrix A->ref_cnt decrease by 1.
        we will check whether A->ref_cnt == 0 or not. if the statement is false: we still have other slices connecting to A->data, and we will not free anything in this case.
        else: we will free(A->data) then free(A).
    */
void deallocate_matrix(matrix *mat) {
    if (mat == NULL) {
        return;
    }

    //mat->ref_cnt -= 1;
    if (mat->parent == NULL) { //free matrix if no slice
        /// the object (mat->ref_cnt).val -= 1;
        *(mat->ref_cnt) = *(mat->ref_cnt) - 1;
        if (*(mat->ref_cnt) == 0) {
            free(mat->ref_cnt);
            free(mat->data);
            free(mat);
        }
    } else if (mat->parent != NULL) {  //free slice if its the only slice
        if (*(mat->ref_cnt) == 1) {
            deallocate_matrix(mat->parent);
            free(mat);
        } else {
            *(mat->ref_cnt) = *(mat->ref_cnt) - 1;
            if (mat->reference == 0) {
                free(mat);
            }
        }
    }
}

/*
 * Returns the double value of the matrix at the given row and column.
 * You may assume `row` and `col` are valid.
 */
inline double get(matrix *mat, int row, int col) {
    /* TODO: YOUR CODE HERE */
    return (mat->data)[mat->cols * row + col];
}

/*
 * Sets the value at the given row and column to val. You may assume `row` and
 * `col` are valid
 */
inline void set(matrix *mat, int row, int col, double val) {
    /* TODO: YOUR CODE HERE */
    (mat->data)[mat->cols * row + col] = val;
}

/*
 * Sets all entries in mat to val
 */
void fill_matrix(matrix *mat, double val) {
    /* TODO: YOUR CODE HERE */
    for (int i = 0; i < mat->rows; i++) {
        for (int j = 0; j < mat->cols; j++) {
            set(mat, i, j, val);
        }
    }
}

/*
 * Store the result of adding mat1 and mat2 to `result`.
 * Return 0 upon success and a nonzero value upon failure.
 */
int add_matrix(matrix *result, matrix *mat1, matrix *mat2) {
    /* TODO: YOUR CODE HERE */

    // Optimization
    //SIMD and Parallel programming
    if (mat1->rows <= 0 || mat1->cols <= 0 || result->rows <= 0 || result->cols <= 0 || mat2->rows <= 0 || mat2->cols <= 0) {
        return -1;
    }

    if (mat1->rows != mat2->rows || mat1->cols != mat2->cols || result == NULL || result->rows != mat1->rows || result->cols != mat1->cols) {
        return -3;
    }
    int total = mat1->rows * mat1->cols;
    int mod4total = total - (mat1->rows * mat1->cols) % 4;
    #pragma omp parallel for //There supposed to be four threads doing SIMD at the same time
        for (int i = 0; i < mod4total; i += 4) { // can only do 4 since m128
            __m256d tmp_1 = _mm256_loadu_pd((double *) (mat1->data + i)); /// each row for matrix 1  ####
            __m256d tmp_2 = _mm256_loadu_pd((double *) (mat2->data + i));  ///m2 ####
            __m256d sum_vec = _mm256_add_pd(tmp_1, tmp_2);  ////Now I have all the rows after the addition _mm256_max_pd(tmp1, tmp2)
            _mm256_storeu_pd((double *) (result->data + i), sum_vec);
        }

    for (int i = mod4total; i < total; i++) {
        (result->data)[i] = (mat1->data)[i] + (mat2->data)[i];
    }
    return 0;
}

/*
 * (OPTIONAL)
 * Store the result of subtracting mat2 from mat1 to `result`.
 * Return 0 upon success and a nonzero value upon failure.
 */
int sub_matrix(matrix *result, matrix *mat1, matrix *mat2) {
    if (mat1->rows <= 0 || mat1->cols <= 0 || result->rows <= 0 || result->cols <= 0 || mat2->rows <= 0 || mat2->cols <= 0) {
        return -1;
    }

    if (mat1->rows != mat2->rows || mat1->cols != mat2->cols || result->rows != mat1->rows || result->cols != mat1->cols || result == NULL) {
        return -3;
    }


    for (int i = 0; i < mat1->rows; i++) {
        for (int j = 0; j < mat1->cols; j++) {
            double val = get(mat1, i, j) - get(mat2, i, j);
            set(result, i, j, val);
        }
    }
    return 0;
}

/*
inline void transpose_blocking(int n, int blocksize, double *dst, double *src) {
        // YOUR CODE HERE
        for(int i=0; i < (n/blocksize+1); i++){
            for(int j=0; j < (n/blocksize+1); j++){
                for(int m=0; m < blocksize; m++){
                    for(int l=0; l < blocksize; l++){
                        if(i*blocksize + m >= n || j*blocksize + l >= n) {
                            continue;
                        }
                        dst[j*n*blocksize + i*blocksize + l*n + m] = src[i*n*blocksize + j*blocksize + m*n + l];
                        //printf("%d %d\n",j*n*blocksize + i*blocksize + k*n + m, i*n*blocksize + j*blocksize + m*n + k);
                    }
                }
            }
        }
    }
*/

/*
 * Store the result of multiplying mat1 and mat2 to `result`.
 * Return 0 upon success and a nonzero value upon failure.
 * Remember that matrix multiplication is not the same as multiplying individual elements.
 */
int mul_matrix(matrix *result, matrix *mat1, matrix *mat2) {
    /* TODO: YOUR CODE HERE */
    if (mat1->rows <= 0 || mat1->cols <= 0 || result->rows <= 0 || result->cols <= 0 || mat2->rows <= 0 || mat2->cols <= 0) {
        return -1;
    }

    if (mat1->cols != mat2->rows || result->rows != mat1->rows || result->cols != mat2->cols || result == NULL) {
        return -3;
    }

    matrix *m2_transpose = NULL;
    allocate_matrix(&m2_transpose, mat2->cols, mat2->rows);
    if (m2_transpose == NULL) {
        return -2;
    }

    
    #pragma omp parallel for
        for (int i = 0; i < mat2->rows; i += 1) {
            for (int j = 0; j < mat2->cols; j += 1) {
                // set(m2_transpose, j, i, get(mat2, i, j));
                (m2_transpose->data)[m2_transpose->cols * j + i] = (mat2->data)[mat2->cols * i + j];
            }
        }
    /*
    for (int i = 0; i < m2_transpose->rows; i += 1) {
        for (int j = 0; j < m2_transpose->cols; j += 1) {
            printf("row: %d, col: %d, value: %f\n", i, j, get(m2_transpose, i, j));
        }
    }
    */
    int total = mat1->cols;
    int mod16total = total - total % 16;
    // __m256d sum_vec;
    #pragma omp parallel for //There supposed to be four threads doing SIMD at the same time
        for (int l = 0; l < mat1->rows; l +=1) { //rows of m1
            for (int k = 0; k < m2_transpose->rows; k +=1) { //cols of m2
                __m256d sum_vec = _mm256_set1_pd(0); //{0, 0, 0, 0}
                for (int i = 0; i < mod16total; i += 16) { // can only do 4
                    sum_vec = _mm256_fmadd_pd((__m256d) _mm256_loadu_pd((double *) (mat1->data + (l * mat1->cols) + i+0)), (__m256d) _mm256_loadu_pd((double *) (m2_transpose->data + (k * m2_transpose->cols) + i+0)), sum_vec);  //// vector sum + (vector tmp1 * tmp2)
                    sum_vec = _mm256_fmadd_pd((__m256d) _mm256_loadu_pd((double *) (mat1->data + (l * mat1->cols) + i+4)), (__m256d) _mm256_loadu_pd((double *) (m2_transpose->data + (k * m2_transpose->cols) + i+4)), sum_vec);  //// vector sum + (vector tmp1 * tmp2)
                    sum_vec = _mm256_fmadd_pd((__m256d) _mm256_loadu_pd((double *) (mat1->data + (l * mat1->cols) + i+8)), (__m256d) _mm256_loadu_pd((double *) (m2_transpose->data + (k * m2_transpose->cols) + i+8)), sum_vec);  //// vector sum + (vector tmp1 * tmp2)
                    sum_vec = _mm256_fmadd_pd((__m256d) _mm256_loadu_pd((double *) (mat1->data + (l * mat1->cols) + i+12)), (__m256d) _mm256_loadu_pd((double *) (m2_transpose->data + (k * m2_transpose->cols) + i+12)), sum_vec);  //// vector sum + (vector tmp1 * tmp2)
                }
                double sum_array[4];
                double row_sum = 0.0;
                _mm256_storeu_pd(sum_array, sum_vec);
                row_sum = sum_array[0] + sum_array[1] + sum_array[2] + sum_array[3];
                for (int j = mod16total; j < total; j++){ //tail case to clean up (will run 0 to 3 times)
                    row_sum += ((mat1->data)[l * mat1->cols + j]) * ((m2_transpose->data)[k * m2_transpose->cols + j]);
                }
                // printf("row: %d, col: %d, row_sum:: %f\n", l, k, row_sum);
                // set(result, l, k, row_sum);
                (result->data)[result->cols * l + k] = row_sum;
            }
        }
    deallocate_matrix(m2_transpose);
    return 0;
}
/*
 * Store the result of raising mat to the (pow)th power to `result`.
 * Return 0 upon success and a nonzero value upon failure.
 * Remember that pow is defined with matrix multiplication, not element-wise multiplication.
 */
int pow_matrix(matrix *result, matrix *mat, int pow) {
    /* TODO: YOUR CODE HERE */
    if (mat->rows <= 0 || mat->cols <= 0 || result->rows <= 0 || result->cols <= 0) {
        return -1;
    }

    if (result->rows != mat->rows || result->cols != mat->cols || mat->rows != mat->cols || result->rows != result->cols) {
        return -3;
    }

    if (pow < 0) {
        return -4;
    }

    // return identity matrix if pow is 0
    matrix *helper = NULL;
    allocate_matrix(&helper, mat->rows, mat->cols);
    if (helper == NULL) {
        return -2;
    }

    int mat_rows = mat->rows;

    for (int i = 0; i < mat_rows; i++) {
        (helper->data)[mat_rows * i + i] = 1;
    }

    if (pow == 0) {
        for (int i = 0; i < mat_rows; i++) {
            (result->data)[mat_rows * i + i] = 1;
        }
    } else if (pow == 1) {
        for (int i = 0; i < mat_rows; i++) {
            for (int j = 0; j < mat_rows; j++) {
                // set(result, i, j, get(mat, i, j));
                (result->data)[mat_rows * i + j] = (mat->data)[mat_rows * i + j];
            }
        }
    } else {
        int p;
        for (p = 0; x_to_the_n(2, p) <= pow; p++);
        matrix *mat1 = NULL;
        matrix *mat2 = NULL;
        int a = allocate_matrix(&mat1, mat_rows, mat_rows);
        int b = allocate_matrix(&mat2, mat_rows, mat_rows);
        if (a != 0 || b != 0) {
            deallocate_matrix(helper);
        }
        for (int i = 0; i < mat_rows; i++) {
            for (int j = 0; j < mat_rows; j++) {
                // set(mat1, i, j, get(mat, i, j));
                // set(mat2, i, j, get(mat, i, j));
                (mat1->data)[mat_rows * i + j] = (mat->data)[mat_rows * i + j];
                (mat2->data)[mat_rows * i + j] = (mat->data)[mat_rows * i + j];
            }
        }
        // calculate binary pow in decimal.
        int choose=0;
        int count=0;
        for (int i = pow; i != 1; i = i / 2) {
            int num = i % 2;
            choose += num * x_to_the_n(10, count);
            count += 1;
        }
        choose = choose + 1 * x_to_the_n(10, count);

        // mul_matrix does not support a=a*a, so we need another matrix b=a*a.
        int mat1_or_mat2 = 0;
        int helper_or_result = 0;
        for (int i = 0; i < p; i++) {
            int choose_or_not = choose % 10;
            if (choose_or_not == 1) {
                if (helper_or_result == 0 && mat1_or_mat2 == 0) {
                    mul_matrix(result, helper, mat1);
                    mul_matrix(mat2, mat1, mat1);
                    helper_or_result = 1;
                    mat1_or_mat2 = 1;
                } else if (helper_or_result == 0 && mat1_or_mat2 == 1) {
                    mul_matrix(result, helper, mat2);
                    mul_matrix(mat1, mat2, mat2);
                    helper_or_result = 1;
                    mat1_or_mat2 = 0;
                } else if (helper_or_result == 1 && mat1_or_mat2 == 0) {
                    mul_matrix(helper, result, mat1);
                    mul_matrix(mat2, mat1, mat1);
                    helper_or_result = 0;
                    mat1_or_mat2 = 1;
                } else {
                    mul_matrix(helper, result, mat2);
                    mul_matrix(mat1, mat2, mat2);
                    helper_or_result = 0;
                    mat1_or_mat2 = 0;
                }
            } else {
                if (mat1_or_mat2 == 0) {
                    mul_matrix(mat2, mat1, mat1);
                    mat1_or_mat2 = 1;
                } else {
                    mul_matrix(mat1, mat2, mat2);
                    mat1_or_mat2 = 0;
                }
            }
            choose = choose / 10;
        }

        // solution contains in the helper.
        if (helper_or_result == 0) {
            for (int i = 0; i < mat_rows; i++) {
                for (int j = 0; j < mat_rows; j++) {
                    // set(result, i, j, get(helper, i, j));
                    (result->data)[mat_rows * i + j] = (helper->data)[mat_rows * i + j];
                }
            }
        }
        // deallocate matrix in square_array.
        deallocate_matrix(mat1);
        deallocate_matrix(mat2);
    }
    // free helper. 
    deallocate_matrix(helper);
    return 0;
}

/*
 * 要求一个矩阵的6次方， 先算出 x = 2多少次方最接近6 x=2，x+=1，x = 3。我们binary number 就是110
 * 然后再根据x=3 去malloc 一个 matrix array。这个array的每一项都是前一项的平方。第零项是mat。。。
 * 然后根据上面的binary number 当成一个key 去算 110 第0位是0 所以我们不把array的第0项乘进去，第一项是1 所以要把第一项乘进去。
 * 
*/

/*
 * helper function. 
 * convert Int n into binary number and treat it as decimal.
 * add corresponding binary number to the array from index 1 to p. 
inline int convert_to_base10_binary(int n) {
    int sum=0;
    int count=0;
    for (int i = n; i != 1; i = i / 2) {
        int num = i % 2;
        sum = sum + num * x_to_the_n(10, count);
        count += 1;
    }
    sum = sum + 1 * x_to_the_n(10, count);
    //1111101000 p = 9
    return sum;
}
*/

/*
 * helper function. return x ** n
*/
inline int x_to_the_n(int x, int n) {
    int i; 
    int number = 1;

    for (i = 0; i < n; ++i) {
        number *= x;
    } 
    return number;
}

/*
 * (OPTIONAL)
 * Store the result of element-wise negating mat's entries to `result`.
 * Return 0 upon success and a nonzero value upon failure.
 */
int neg_matrix(matrix *result, matrix *mat) {
    /* TODO: YOUR CODE HERE */
    if (mat->rows <= 0 || mat->cols <= 0 || result->rows <= 0 || result->cols <= 0) {
        return -1;
    }

    if (result->rows != mat->rows || result->cols != mat->cols) {
        return -3;
    }


    for (int i = 0; i < mat->rows; i++) {
        for (int j = 0; j < mat->cols; j++) {
            double val = - get(mat, i, j);
            set(result, i, j, val);
        }
    }
    return 0;
}

/*
 * Store the result of taking the absolute value element-wise to `result`.
 * Return 0 upon success and a nonzero value upon failure.
 */
int abs_matrix(matrix *result, matrix *mat) {
   // Optimization
    int total = mat->rows * mat->cols;
    int mod4total = total - (mat->rows * mat->cols) % 4;
    #pragma omp parallel for //There supposed to be four threads doing SIMD at the same time
        for (int i = 0; i < mod4total; i += 4) { // can only do 4 since m128
            __m256d tmp_1 = _mm256_loadu_pd((double *) (mat->data + i)); /// each row for matrix 1  ####
            __m256d tmp_2 = _mm256_loadu_pd((double *) (mat->data + i));  /// another m1
            __m256d zero_v = _mm256_set1_pd(0);
            tmp_2 = _mm256_sub_pd (zero_v, tmp_2);
            __m256d max_vec = _mm256_max_pd(tmp_1, tmp_2);  ////Now I have all the rows after the addition 
            _mm256_storeu_pd((double *) (result->data + i), max_vec);
        }
    
        for (int i = mod4total; i < total; i++) {
            double val = (mat->data)[i];
            if (val < 0) {
                val = -val;
            }
            (result->data)[i] = val;
        }
    return 0;
}
