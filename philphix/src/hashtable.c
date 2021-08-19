#include "hashtable.h"
#include <stdlib.h>
#include <stdio.h>

/*
 * This creates a new hash table of the specified size and with
 * the given hash function and comparison function.
 */
HashTable *createHashTable(int size, unsigned int (*hashFunction)(void *),
                           int (*equalFunction)(void *, void *)) {
  int i = 0;
  HashTable *newTable = malloc(sizeof(HashTable));
  if (NULL == newTable) {
    fprintf(stderr, "malloc failed \n");
    exit(1);
  }
  newTable->size = size;
  newTable->data = malloc(sizeof(HashBucket *) * size);
  if (NULL == newTable->data) {
    fprintf(stderr, "malloc failed \n");
    exit(1);
  }
  for (i = 0; i < size; i++) {
    newTable->data[i] = NULL;
  }
  newTable->hashFunction = hashFunction;
  newTable->equalFunction = equalFunction;
  return newTable;
}

/*
 * This inserts a key/data pair into a hash table.  To use this
 * to store strings, simply cast the char * to a void * (e.g., to store
 * the string referred to by the declaration char *string, you would
 * call insertData(someHashTable, (void *) string, (void *) string).
 */
void insertData(HashTable *table, void *key, void *data) {
  // -- TODO --
  // HINT:
  // 1. Find the right hash bucket location with table->hashFunction.
  // 2. Allocate a new hash bucket struct.
  // 3. Append to the linked list or create it if it does not yet exist. 
  unsigned int location = (table->hashFunction)(key) % (table->size);

  HashBucket *bucket = (struct HashBucket *) malloc(sizeof(struct HashBucket));
  bucket->key = key;
  bucket->data = data;
  bucket->next = NULL;

  if (table->data[location] == NULL) {
    table->data[location] = bucket;
  } else {
    struct HashBucket *curr = table->data[location];

    // iterate over the linkedlist 
    while(1) {
      if (curr->next == NULL) {
        curr->next = bucket;
        break;
      }
      curr = curr->next;
    }
  }
}

/*
 * This returns the corresponding data for a given key.
 * It returns NULL if the key is not found. 
 */
void *findData(HashTable *table, void *key) {
  // -- TODO --
  // HINT:
  // 1. Find the right hash bucket with table->hashFunction.
  // 2. Walk the linked list and check for equality with table->equalFunction.
  unsigned int location = (table->hashFunction)(key) % (table->size);

  struct HashBucket *prt = table->data[location];

  while(prt != NULL) {
    if ((table->equalFunction)(key, prt->key)) {
      return prt->data;
    }
    prt = prt->next;
  }
  return NULL;
}
