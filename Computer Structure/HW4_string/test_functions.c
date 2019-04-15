/* test2.c --- 
 * 
 * Filename: test2.c
 * Description: 
 * Author: Bryce Himebaugh
 * Maintainer: 
 * Created: Tue Oct 27 12:32:24 2015
 * Last-Updated: 
 *           By: 
 *     Update #: 0
 * Keywords: 
 * Compatibility: 
 * 
 */

/* Commentary: 
 * 
 * 
 * 
 */

/* Change log:
 * 
 * 
 */

/* Copyright (c) 2004-2007 The Trustees of Indiana University and 
 * Indiana University Research and Technology Corporation.  
 * 
 * All rights reserved. 
 * 
 * Additional copyrights may follow 
 */

/* Code: */

#include <stdio.h>
#include <stdint.h> 
#include <stddef.h>
#include <stdlib.h>
#include <string.h>
#include "string_compare.h"

void test(void) {
  printf("Compare %s and %s, C result: %d, Assembly result: %d\n", "ABCD", "ABCD", string_compare_n_c("ABCD", "ABCD", 4), string_compare_n("ABCD", "ABCD", 4));
  printf("Compare %s and %s, C result: %d, Assembly result: %d\n", "BBBB", "AAAA", string_compare_n_c("BBBB", "AAAA", 4), string_compare_n("BBBB", "AAAA", 4));  
  printf("Compare %s and %s, C result: %d, Assembly result: %d\n", "AAAA", "BBBB", string_compare_n_c("AAAA", "BBBB", 4), string_compare_n("AAAA", "BBBB", 4)); 
}


/* test.c ends here */
