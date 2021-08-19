#include "CUnit/CUnit.h"
#include "CUnit/Basic.h"
#include "../src/matrix.h"
#include <stdio.h>

/* Test Suite setup and cleanup functions: */
int init_suite(void) { return 0; }
int clean_suite(void) { return 0; }

/************* Test case functions ****************/
void add_test(void) {
  matrix *result = NULL;
  matrix *mat1 = NULL;
  matrix *mat2 = NULL;
  CU_ASSERT_EQUAL(allocate_matrix(&result, 2, 2), 0);
  CU_ASSERT_EQUAL(allocate_matrix(&mat1, 2, 2), 0);
  CU_ASSERT_EQUAL(allocate_matrix(&mat2, 2, 2), 0);
  for (int i = 0; i < 2; i++) {
    for (int j = 0; j < 2; j++) {
      set(mat1, i, j, i * 2 + j);
      set(mat2, i, j, i * 2 + j);
    }
  }
  add_matrix(result, mat1, mat2);
  for (int i = 0; i < 2; i++) {
    for (int j = 0; j < 2; j++) {
      CU_ASSERT_EQUAL(get(result, i, j), 2 * (i * 2 + j));
    }
  }
  deallocate_matrix(result);
  deallocate_matrix(mat1);
  deallocate_matrix(mat2);
}

void sub_test(void) {
  matrix *result = NULL;
  matrix *mat1 = NULL;
  matrix *mat2 = NULL;
  CU_ASSERT_EQUAL(allocate_matrix(&result, 2, 2), 0);
  CU_ASSERT_EQUAL(allocate_matrix(&mat1, 2, 2), 0);
  CU_ASSERT_EQUAL(allocate_matrix(&mat2, 2, 2), 0);
  for (int i = 0; i < 2; i++) {
    for (int j = 0; j < 2; j++) {
      set(mat1, i, j, i * 2 + j);
      set(mat2, i, j, (i * 2 + j) * 3);
    }
  }
  sub_matrix(result, mat1, mat2);
  for (int i = 0; i < 2; i++) {
    for (int j = 0; j < 2; j++) {
      CU_ASSERT_EQUAL(get(result, i, j), (-2) * (i * 2 + j));
    }
  }
  deallocate_matrix(result);
  deallocate_matrix(mat1);
  deallocate_matrix(mat2);
}

void mul_test(void) {
  matrix *result = NULL;
  matrix *mat1 = NULL;
  matrix *mat2 = NULL;
  CU_ASSERT_EQUAL(allocate_matrix(&result, 3, 3), 0);
  CU_ASSERT_EQUAL(allocate_matrix(&mat1, 3, 3), 0);
  CU_ASSERT_EQUAL(allocate_matrix(&mat2, 3, 3), 0);
  for (int i = 0; i < 3; i++) {
    for (int j = 0; j < 3; j++) {
      set(mat1, i, j, i * 3 + j + 1);
      set(mat2, i, j, i * 3 + j + 1);
    }
  }
  mul_matrix(result, mat1, mat2);
  CU_ASSERT_EQUAL(get(result, 0, 0), 30);
  CU_ASSERT_EQUAL(get(result, 0, 1), 36);
  CU_ASSERT_EQUAL(get(result, 0, 2), 42);
  CU_ASSERT_EQUAL(get(result, 1, 0), 66);
  CU_ASSERT_EQUAL(get(result, 1, 1), 81);
  CU_ASSERT_EQUAL(get(result, 1, 2), 96);
  CU_ASSERT_EQUAL(get(result, 2, 0), 102);
  CU_ASSERT_EQUAL(get(result, 2, 1), 126);
  CU_ASSERT_EQUAL(get(result, 2, 2), 150);
  deallocate_matrix(result);
  deallocate_matrix(mat1);
  deallocate_matrix(mat2);

  matrix *mat3 = NULL;
  matrix *mat4 = NULL;
  matrix *non_square_result = NULL;
  matrix *false_result = NULL;
  CU_ASSERT_EQUAL(allocate_matrix(&mat3, 3, 4), 0);
  CU_ASSERT_EQUAL(allocate_matrix(&mat4, 4, 5), 0);
  CU_ASSERT_EQUAL(allocate_matrix(&non_square_result, 3, 5), 0);

  CU_ASSERT_EQUAL(allocate_matrix(&false_result, 3, 4), 0);
  CU_ASSERT_EQUAL(mul_matrix(false_result, mat3, mat4), -3);

  set(mat3, 0, 0, 2);
  set(mat3, 0, 1, 9);
  set(mat3, 0, 2, 3);
  set(mat3, 0, 3, 0);
  set(mat3, 1, 0, 1);
  set(mat3, 1, 1, 4);
  set(mat3, 1, 2, 7);
  set(mat3, 1, 3, 5);
  set(mat3, 2, 0, 4);
  set(mat3, 2, 1, 0);
  set(mat3, 2, 2, 11);
  set(mat3, 2, 3, 9);

  set(mat4, 0, 0, 9);
  set(mat4, 0, 1, 8);
  set(mat4, 0, 2, 2);
  set(mat4, 0, 3, 3);
  set(mat4, 0, 4, 7);
  set(mat4, 1, 0, 2);
  set(mat4, 1, 1, 11);
  set(mat4, 1, 2, 1);
  set(mat4, 1, 3, 33);
  set(mat4, 1, 4, 3);
  set(mat4, 2, 0, 0);
  set(mat4, 2, 1, 20);
  set(mat4, 2, 2, 1);
  set(mat4, 2, 3, 90);
  set(mat4, 2, 4, 0);
  set(mat4, 3, 0, 1);
  set(mat4, 3, 1, 9);
  set(mat4, 3, 2, 2);
  set(mat4, 3, 3, 12);
  set(mat4, 3, 4, 2);
  CU_ASSERT_EQUAL(mul_matrix(non_square_result, mat3, mat4), 0);
  /*
  for (int i = 0; i < non_square_result->rows; i++) {
    for (int j = 0; j < non_square_result->cols; j++) {
      printf("row: %d col: %d, sum: %f \n", i, j, get(non_square_result, i, j));
    }
  }
  */
  CU_ASSERT_EQUAL(get(non_square_result, 0, 0), 36);
  CU_ASSERT_EQUAL(get(non_square_result, 0, 1), 175);
  CU_ASSERT_EQUAL(get(non_square_result, 0, 2), 16);
  CU_ASSERT_EQUAL(get(non_square_result, 0, 3), 573);
  CU_ASSERT_EQUAL(get(non_square_result, 0, 4), 41);
  CU_ASSERT_EQUAL(get(non_square_result, 1, 0), 22);
  CU_ASSERT_EQUAL(get(non_square_result, 1, 1), 237);
  CU_ASSERT_EQUAL(get(non_square_result, 1, 2), 23);
  CU_ASSERT_EQUAL(get(non_square_result, 1, 3), 825);
  CU_ASSERT_EQUAL(get(non_square_result, 1, 4), 29);
  CU_ASSERT_EQUAL(get(non_square_result, 2, 0), 45);
  CU_ASSERT_EQUAL(get(non_square_result, 2, 1), 333);
  CU_ASSERT_EQUAL(get(non_square_result, 2, 2), 37);
  CU_ASSERT_EQUAL(get(non_square_result, 2, 3), 1110);
  CU_ASSERT_EQUAL(get(non_square_result, 2, 4), 46);

  deallocate_matrix(non_square_result);
  deallocate_matrix(false_result);
  deallocate_matrix(mat3);
  deallocate_matrix(mat4);
}

void neg_test(void) {
  matrix *result = NULL;
  matrix *mat = NULL;
  CU_ASSERT_EQUAL(allocate_matrix(&result, 2, 2), 0);
  CU_ASSERT_EQUAL(allocate_matrix(&mat, 2, 2), 0);
  for (int i = 0; i < 2; i++) {
    for (int j = 0; j < 2; j++) {
      set(mat, i, j, i * 2 + j);
    }
  }
  neg_matrix(result, mat);
  for (int i = 0; i < 2; i++) {
    for (int j = 0; j < 2; j++) {
      CU_ASSERT_EQUAL(get(result, i, j), -(i * 2 + j));
    }
  }
  deallocate_matrix(result);
  deallocate_matrix(mat);
} 

void abs_test(void) {
  matrix *result = NULL;
  matrix *mat = NULL;
  CU_ASSERT_EQUAL(allocate_matrix(&result, 2, 2), 0);
  CU_ASSERT_EQUAL(allocate_matrix(&mat, 2, 2), 0);
  for (int i = 0; i < 2; i++) {
    for (int j = 0; j < 2; j++) {
      if (j % 2 == 0)
        set(mat, i, j, i * 2 + j);
      else
        set(mat, i, j, -(i * 2 + j));
    }
  }
  abs_matrix(result, mat);
  for (int i = 0; i < 2; i++) {
    for (int j = 0; j < 2; j++) {
      CU_ASSERT_EQUAL(get(result, i, j), i * 2 + j);
    }
  }
  deallocate_matrix(result);
  deallocate_matrix(mat);
}

void pow_test(void) {
  matrix *result = NULL;
  matrix *mat = NULL;
  CU_ASSERT_EQUAL(allocate_matrix(&result, 2, 2), 0);
  CU_ASSERT_EQUAL(allocate_matrix(&mat, 2, 2), 0);
  set(mat, 0, 0, 1);
  set(mat, 0, 1, 1);
  set(mat, 1, 0, 1);
  set(mat, 1, 1, 0);
  pow_matrix(result, mat, 3);
  CU_ASSERT_EQUAL(get(result, 0, 0), 3);
  CU_ASSERT_EQUAL(get(result, 0, 1), 2);
  CU_ASSERT_EQUAL(get(result, 1, 0), 2);
  CU_ASSERT_EQUAL(get(result, 1, 1), 1);
  pow_matrix(result, mat, 10);
  CU_ASSERT_EQUAL(get(result, 0, 0), 89);
  CU_ASSERT_EQUAL(get(result, 0, 1), 55);
  CU_ASSERT_EQUAL(get(result, 1, 0), 55);
  CU_ASSERT_EQUAL(get(result, 1, 1), 34);
  deallocate_matrix(result);
  deallocate_matrix(mat);

  matrix *result1 = NULL;
  matrix *mat1 = NULL;
  CU_ASSERT_EQUAL(allocate_matrix(&result1, 2, 2), 0);
  CU_ASSERT_EQUAL(allocate_matrix(&mat1, 2, 2), 0);
  set(mat1, 0, 0, 1);
  set(mat1, 0, 1, 1);
  set(mat1, 1, 0, 1);
  set(mat1, 1, 1, 0);
  pow_matrix(result1, mat1, 2);
  CU_ASSERT_EQUAL(get(result1, 0, 0), 2);
  CU_ASSERT_EQUAL(get(result1, 0, 1), 1);
  CU_ASSERT_EQUAL(get(result1, 1, 0), 1);
  CU_ASSERT_EQUAL(get(result1, 1, 1), 1);

  deallocate_matrix(result1);
  deallocate_matrix(mat1);

  matrix *error_result = NULL;
  matrix *error_mat = NULL;

  CU_ASSERT_EQUAL(allocate_matrix(&error_result, 3, 2), 0);
  CU_ASSERT_EQUAL(allocate_matrix(&error_mat, 2, 2), 0);
  CU_ASSERT_EQUAL(pow_matrix(error_result, error_mat, 3), -3);

  deallocate_matrix(error_result);
  deallocate_matrix(error_mat);
}

void alloc_fail_test(void) {
  matrix *mat = NULL;
  CU_ASSERT_EQUAL(allocate_matrix(&mat, 0, 0), -1);
  CU_ASSERT_EQUAL(allocate_matrix(&mat, 0, 1), -1);
  CU_ASSERT_EQUAL(allocate_matrix(&mat, 1, 0), -1);
}

void alloc_success_test(void) {
  matrix *mat = NULL;
  CU_ASSERT_EQUAL(allocate_matrix(&mat, 3, 2), 0);
  CU_ASSERT_EQUAL(mat->parent, NULL);
  CU_ASSERT_EQUAL(*(mat->ref_cnt), 1);
  CU_ASSERT_EQUAL(mat->rows, 3);
  CU_ASSERT_EQUAL(mat->cols, 2);
  CU_ASSERT_NOT_EQUAL(mat->data, NULL);
  for (int i = 0; i < 3; i++) {
    for (int j = 0; j < 2; j++) {
      CU_ASSERT_EQUAL(get(mat, i, j), 0);
    }
  }
  deallocate_matrix(mat);
}

void alloc_ref_fail_test(void) {
  matrix *mat = NULL;
  matrix *from = NULL;
  CU_ASSERT_EQUAL(allocate_matrix_ref(&mat, from, 0, 0, 0), -1);
  CU_ASSERT_EQUAL(allocate_matrix_ref(&mat, from, 0, 0, 1), -1);
  CU_ASSERT_EQUAL(allocate_matrix_ref(&mat, from, 0, 1, 0), -1);
  allocate_matrix(&from, 3, 2);
  CU_ASSERT_EQUAL(allocate_matrix_ref(&mat, from, 2, 2, 3), -3);
  CU_ASSERT_EQUAL(allocate_matrix_ref(&mat, from, 3, 3, 2), -3);
  deallocate_matrix(from);
}

void alloc_ref_success_test(void) {
  matrix *mat = NULL;
  matrix *from = NULL;
  allocate_matrix(&from, 3, 2);
  for (int i = 0; i < 3; i++) {
    for (int j = 0; j < 2; j++) {
      set(from, i, j, i * 2 + j);
    }
  }
  CU_ASSERT_EQUAL(allocate_matrix_ref(&mat, from, 2, 2, 2), 0);
  CU_ASSERT_PTR_EQUAL(mat->data, from->data + 2);
  CU_ASSERT_PTR_EQUAL(mat->parent, from);
  CU_ASSERT_EQUAL(*(mat->parent->ref_cnt), 2);
  CU_ASSERT_EQUAL(*(mat->ref_cnt), 2);
  CU_ASSERT_EQUAL(mat->rows, 2);
  CU_ASSERT_EQUAL(mat->cols, 2);
  deallocate_matrix(from);
  //CU_ASSERT_EQUAL(*(mat->parent->ref_cnt), 1);
  //CU_ASSERT_EQUAL(*(mat->ref_cnt), 1);
  deallocate_matrix(mat);
}

void dealloc_null_test(void) {
  matrix *mat = NULL;
  deallocate_matrix(mat); // Test the null case doesn't crash
  /*
  //test chain dealloc.
  matrix *A = NULL;
  matrix *B = NULL;
  matrix *C = NULL;
  matrix *D = NULL;
  matrix *E = NULL;
  matrix *F = NULL;
  allocate_matrix(&A, 4, 4);
  CU_ASSERT_EQUAL(allocate_matrix_ref(&B, A, 4, 3, 4), 0);
  CU_ASSERT_EQUAL(allocate_matrix_ref(&C, B, 0, 3, 4), 0);
  CU_ASSERT_EQUAL(allocate_matrix_ref(&F, B, 0, 2, 6), 0);
  CU_ASSERT_EQUAL(allocate_matrix_ref(&D, C, 2, 2, 5), 0);
  CU_ASSERT_EQUAL(allocate_matrix_ref(&E, C, 6, 2, 3), 0);

  A: 
  1  2  3  4 
  5  6  7  8 
  9  10 11 12
  13 14 15 16

  B: parent: A
  5  6  7  8
  9  10 11 12
  13 14 15 16

  C: parent: B          F: parent: B
  5  6  7  8            5  6  7  8  9  10
  9  10 11 12           11 12 13 14 15 16
  13 14 15 16           

  D: pareent: C         E: parent: C
  7  8  9  10 11        11 12 13
  12 13 14 15 16        14 15 16

  set(A, 0, 0, 1);
  set(A, 0, 1, 2);
  set(A, 0, 2, 3);
  set(A, 0, 3, 4);
  set(A, 1, 0, 5);
  set(A, 1, 1, 6);
  set(A, 1, 2, 7);
  set(A, 1, 3, 8);
  set(A, 2, 0, 9);
  set(A, 2, 1, 10);
  set(A, 2, 2, 11);
  set(A, 2, 3, 12);
  set(A, 3, 0, 13);
  set(A, 3, 1, 14);
  set(A, 3, 2, 15);
  set(A, 3, 3, 16);
  
  CU_ASSERT_EQUAL(get(B, 2, 3), 16);
  CU_ASSERT_EQUAL(get(C, 2, 3), 16);
  CU_ASSERT_EQUAL(get(B, 0, 0), 5);
  CU_ASSERT_EQUAL(get(C, 0, 0), 5);
  CU_ASSERT_EQUAL(get(B, 0, 3), 8);
  CU_ASSERT_EQUAL(get(C, 0, 3), 8);
  CU_ASSERT_EQUAL(get(D, 0, 0), 7);
  CU_ASSERT_EQUAL(get(D, 0, 4), 11);
  CU_ASSERT_EQUAL(get(D, 1, 4), 16);
  CU_ASSERT_EQUAL(get(D, 1, 2), 14);
  CU_ASSERT_EQUAL(get(E, 1, 2), 16);
  CU_ASSERT_EQUAL(get(E, 0, 0), 11);
  CU_ASSERT_EQUAL(get(E, 0, 2), 13);

  CU_ASSERT_EQUAL(A->reference, 1);
  CU_ASSERT_EQUAL(B->reference, 2);
  CU_ASSERT_EQUAL(C->reference, 2);
  CU_ASSERT_EQUAL(D->reference, 0);
  CU_ASSERT_EQUAL(E->reference, 0);
  CU_ASSERT_EQUAL(F->reference, 0);

  set(A, 3, 3, 100);
  CU_ASSERT_EQUAL(get(A, 3, 3), 100);
  CU_ASSERT_EQUAL(get(B, 2, 3), 100);
  CU_ASSERT_EQUAL(get(C, 2, 3), 100);
  CU_ASSERT_EQUAL(get(D, 1, 4), 100);
  CU_ASSERT_EQUAL(get(E, 1, 2), 100);
  CU_ASSERT_EQUAL(get(F, 1, 5), 100);
  CU_ASSERT_EQUAL(*(A->ref_cnt), 6);
  CU_ASSERT_EQUAL(*(B->ref_cnt), 6);
  CU_ASSERT_EQUAL(*(C->ref_cnt), 6);
  CU_ASSERT_EQUAL(*(D->ref_cnt), 6);
  CU_ASSERT_EQUAL(*(E->ref_cnt), 6);
  CU_ASSERT_EQUAL(*(F->ref_cnt), 6);

  deallocate_matrix(B);
  CU_ASSERT_EQUAL(*(A->ref_cnt), 5);
  CU_ASSERT_EQUAL(*(C->ref_cnt), 5);
  CU_ASSERT_EQUAL(*(D->ref_cnt), 5);
  CU_ASSERT_EQUAL(*(E->ref_cnt), 5);
  CU_ASSERT_EQUAL(*(F->ref_cnt), 5);


  deallocate_matrix(A);
  CU_ASSERT_EQUAL(*(C->ref_cnt), 4);
  CU_ASSERT_EQUAL(*(D->ref_cnt), 4);
  CU_ASSERT_EQUAL(*(E->ref_cnt), 4);
  CU_ASSERT_EQUAL(*(F->ref_cnt), 4);
  CU_ASSERT_EQUAL(C->reference, 2);


  deallocate_matrix(D);
  CU_ASSERT_EQUAL(*(C->ref_cnt), 3);
  CU_ASSERT_EQUAL(*(E->ref_cnt), 3);
  CU_ASSERT_EQUAL(*(F->ref_cnt), 3);
  CU_ASSERT_EQUAL(C->reference, 1);


  deallocate_matrix(C);
  CU_ASSERT_EQUAL(get(E, 1, 2), 100);
  CU_ASSERT_EQUAL(get(E, 0, 0), 11);
  CU_ASSERT_EQUAL(get(E, 0, 2), 13);
  CU_ASSERT_EQUAL(*(E->ref_cnt), 2);
  CU_ASSERT_EQUAL(*(F->ref_cnt), 2);
  CU_ASSERT_EQUAL(C->reference, 1);

  deallocate_matrix(E);
  CU_ASSERT_EQUAL(get(F, 0, 3), 8);
  CU_ASSERT_EQUAL(*(F->ref_cnt), 1);
  CU_ASSERT_EQUAL(C->reference, 0);

  deallocate_matrix(F);
  */
}

void get_test(void) {
  matrix *mat = NULL;
  allocate_matrix(&mat, 2, 2);
  for (int i = 0; i < 2; i++) {
    for (int j = 0; j < 2; j++) {
      set(mat, i, j, i * 2 + j);
    }
  }
  CU_ASSERT_EQUAL(get(mat, 0, 0), 0);
  CU_ASSERT_EQUAL(get(mat, 0, 1), 1);
  CU_ASSERT_EQUAL(get(mat, 1, 0), 2);
  CU_ASSERT_EQUAL(get(mat, 1, 1), 3);
  deallocate_matrix(mat);
}

void set_test(void) {
  matrix *mat = NULL;
  allocate_matrix(&mat, 2, 2);
  for (int i = 0; i < 2; i++) {
    for (int j = 0; j < 2; j++) {
      set(mat, i, j, i * 2 + j);
    }
  }
  CU_ASSERT_EQUAL(get(mat, 0, 0), 0);
  CU_ASSERT_EQUAL(get(mat, 0, 1), 1);
  CU_ASSERT_EQUAL(get(mat, 1, 0), 2);
  CU_ASSERT_EQUAL(get(mat, 1, 1), 3);
  deallocate_matrix(mat);
}

/************* Test Runner Code goes here **************/

int main (void)
{
  Py_Initialize(); // Need to call this so that Python.h functions won't segfault
  CU_pSuite pSuite = NULL;

  /* initialize the CUnit test registry */
  if (CU_initialize_registry() != CUE_SUCCESS)
    return CU_get_error();

  /* add a suite to the registry */
  pSuite = CU_add_suite("mat_test_suite", init_suite, clean_suite);
  if (pSuite == NULL) {
    CU_cleanup_registry();
    return CU_get_error();
  }

   /* add the tests to the suite */
   if ((CU_add_test(pSuite, "add_test", add_test) == NULL) ||
        (CU_add_test(pSuite, "sub_test", sub_test) == NULL) ||
        (CU_add_test(pSuite, "neg_test", neg_test) == NULL) ||
        (CU_add_test(pSuite, "mul_test", mul_test) == NULL) ||
        (CU_add_test(pSuite, "abs_test", abs_test) == NULL) ||
        (CU_add_test(pSuite, "pow_test", pow_test) == NULL) ||
        (CU_add_test(pSuite, "alloc_fail_test", alloc_fail_test) == NULL) ||
        (CU_add_test(pSuite, "alloc_success_test", alloc_success_test) == NULL) ||
        (CU_add_test(pSuite, "alloc_ref_fail_test", alloc_ref_fail_test) == NULL) ||
        (CU_add_test(pSuite, "alloc_ref_success_test", alloc_ref_success_test) == NULL) ||
        (CU_add_test(pSuite, "dealloc_null_test", dealloc_null_test) == NULL) ||
        (CU_add_test(pSuite, "get_test", get_test) == NULL) ||
        (CU_add_test(pSuite, "set_test", set_test) == NULL)
     )
   {
      CU_cleanup_registry();
      return CU_get_error();
   }

  // Run all tests using the basic interface
  CU_basic_set_mode(CU_BRM_NORMAL);
  // CU_basic_set_mode(CU_BRM_VERBOSE);
  CU_basic_run_tests();
  printf("\n");
  CU_basic_show_failures(CU_get_failure_list());
  printf("\n\n");


  /* Clean up registry and return */
  CU_cleanup_registry();
  return CU_get_error();
}
