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
#include "mult_by_7.h"

void test(void) {
  int error_count=0;
  int i;
  for (i=0;i<100;i++) {
    if (mult_by_7(i)!=mult_by_7_c(i)) {    
      printf("Error: Input %d, Expected %d, Received %d\n",i,mult_by_7_c(i),mult_by_7(i));
      error_count++;
    }
  }
  if (!error_count) {
    printf("Tests Passed\n");
  }
  else {
    printf("Test: %d errors\n",error_count);
  }
}


/* test.c ends here */
