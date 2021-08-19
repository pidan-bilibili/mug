/*
 * Include the provided hash table library.
 */
#include "hashtable.h"

/*
 * Include the header file.
 */
#include "philphix.h"

/*
 * Standard IO and file routines.
 */
#include <stdio.h>

/*
 * General utility routines (including malloc()).
 */
#include <stdlib.h>

/*
 * Character utility routines.
 */
#include <ctype.h>

/*
 * String utility routines.
 */
#include <string.h>

/*
 * This hash table stores the dictionary.
 */
HashTable *dictionary;

char* case2(char *s, char *news) {
  int i = 0;
  news[0] = s[0];
  for (i = 1; s[i] != '\0'; i++) {
    if (s[i] >= 'A' && s[i] <= 'Z') {
      news[i] = s[i] + 'z' - 'Z';
    } else {
      news[i] = s[i];
    }
  }
  news[i] = '\0';
  return news;
}

char* case3(char *s, char *news) {
  int i = 0;
  for (i = 0; s[i] != '\0'; i++) {
    if (s[i] >= 'A' && s[i] <= 'Z') {
      news[i] = s[i] + 'z' - 'Z';
    } else {
      news[i] = s[i];
    }
  }
  news[i] = '\0';
  return news;
}

/*
 * The MAIN routine.  You can safely print debugging information
 * to standard error (stderr) as shown and it will be ignored in 
 * the grading process.
 */
int main(int argc, char **argv) {
  if (argc != 2) {
    fprintf(stderr, "Specify a dictionary\n");
    return 1;
  }
  /*
   * Allocate a hash table to store the dictionary.
   */
  fprintf(stderr, "Creating hashtable\n");
  dictionary = createHashTable(0x61C, &stringHash, &stringEquals);

  fprintf(stderr, "Loading dictionary %s\n", argv[1]);
  readDictionary(argv[1]);
  fprintf(stderr, "Dictionary loaded\n");

  fprintf(stderr, "Processing stdin\n");
  processInput();

  /*
   * The MAIN function in C should always return 0 as a way of telling
   * whatever program invoked this that everything went OK.
   */
  return 0;
}

/*
 * This should hash a string to a bucket index.  void *s can be safely cast
 * to a char * (null terminated string)
 */
unsigned int stringHash(void *s) {
  // -- TODO --
  char *str = (char *) s;
  unsigned int hash = 5381;
  int c = 0;

  while((c = *str++)){
    hash = ((hash << 5) + hash) + c;
  }
  return hash;
}

/*
 * This should return a nonzero value if the two strings are identical 
 * (case sensitive comparison) and 0 otherwise.
 */
int stringEquals(void *s1, void *s2) {
  // -- TODO --
  char *str1 = (char *)s1;
  char *str2 = (char *)s2;
  while (*str1++ == *str2++){
    if(*str1 == '\0' && *str2 == '\0')
      return 1;
  }
  return 0;
  }

/*
 * This function should read in every word and replacement from the dictionary
 * and store it in the hash table.  You should first open the file specified,
 * then read the words one at a time and insert them into the dictionary.
 * Once the file is read in completely, return.  You will need to allocate
 * (using malloc()) space for each word.  As described in the spec, you
 * can initially assume that no word is longer than 60 characters.  However,
 * for the final bit of your grade, you cannot assumed that words have a bounded
 * length.  You CANNOT assume that the specified file exists.  If the file does
 * NOT exist, you should print some message to standard error and call exit(61)
 * to cleanly exit the program.
 */
void readDictionary(char *dictName) {
  // -- TODO --
  FILE *file = fopen(dictName, "r");
  // if file does not exist, exit the program.
  if (file == NULL) {
    fprintf(stderr, "file does not exist");
    exit(61);
  }

  // iterate over the file char by char. 
  char c = fgetc(file);
  while (c != EOF) {
    while (isspace(c)) {
      c = fgetc(file);
    }

    if (c == EOF) {
      break;
    }

    int key_size = 60;
    char *key = calloc(key_size, sizeof(char));
    int i = 0;
    while (!isspace(c) && c != EOF) {
      if (i + 1 >= key_size) {
        key = realloc(key, key_size * 2);
        key_size = key_size * 2;
      }
      key[i] = c;
      c = fgetc(file);
      i = i + 1;
    }
    key[i] = '\0';

    while (isspace(c)) {
      c = fgetc(file);
    }

    if (c == EOF) {
      break;
    }

    int replacement_size = 60; 
    char* replacement = (char *) calloc(replacement_size, sizeof(char));
    i = 0;
    while (!isspace(c) && c != EOF) {
      if (i + 1 >= replacement_size) {
        replacement = realloc(replacement, replacement_size * 2);
        replacement_size = replacement_size * 2;
      }
      replacement[i] = c;
      c = fgetc(file);
      i = i + 1;
    }
    replacement[i] = '\0';
    insertData(dictionary, (void *)key, (void *)replacement);
    if (c == EOF) {
      break;
    }
  }
}


/*
 * This should process standard input (stdin) and perform replacements as 
 * described by the replacement set then print either the original text or 
 * the replacement to standard output (stdout) as specified in the spec (e.g., 
 * if a replacement set of `taest test\n` was used and the string "this is 
 * a taest of  this-proGram" was given to stdin, the output to stdout should be 
 * "this is a test of  this-proGram").  All words should be checked
 * against the replacement set as they are input, then with all but the first
 * letter converted to lowercase, and finally with all letters converted
 * to lowercase.  Only if all 3 cases are not in the replacement set should 
 * it report the original word.
 *
 * Since we care about preserving whitespace and pass through all non alphabet
 * characters untouched, scanf() is probably insufficent (since it only considers
 * whitespace as breaking strings), meaning you will probably have
 * to get characters from stdin one at a time.
 *
 * Do note that even under the initial assumption that no word is longer than 60
 * characters, you may still encounter strings of non-alphabetic characters (e.g.,
 * numbers and punctuation) which are longer than 60 characters. Again, for the 
 * final bit of your grade, you cannot assume words have a bounded length.
 */
void processInput() {
  // -- TODO --
  int c = 0;

  while (1) {
    c = fgetc(stdin);
    if (c == EOF) {
      break;
    }

    int string_size = 60;
    char *string = (char *) calloc(string_size, sizeof(char));
    int i = 0;
    while(isalnum(c)) {
      if (i + 2 > string_size) {
        string = realloc(string, string_size * 2);
        string_size = string_size * 2;
      }
      if (isalnum(c)) {
        string[i] = c;
      }
      c = fgetc(stdin);
      i = i + 1;
    }

    string[i] = '\0';
    if (strlen(string) != 0) {
    char *case2_news = (char *) calloc((strlen(string) + 2), sizeof(char));
    char *case3_news = (char *) calloc((strlen(string) + 2), sizeof(char));

    if (findData(dictionary, string)) {
      printf("%s", (char *) findData(dictionary, string));
    } else if (findData(dictionary, case2(string, case2_news))) {
      printf("%s", (char *) findData(dictionary, case2(string, case2_news)));
    } else if (findData(dictionary, case3(string, case3_news))) {
      printf("%s", (char *) findData(dictionary, case3(string, case3_news)));
    } else {
      printf("%s", string);
    }

    free(case2_news);
    free(case3_news);
    }
    free(string);
    

    if(!isalnum(c) && c != EOF) {
      printf("%c", c);
    }
  }
}
