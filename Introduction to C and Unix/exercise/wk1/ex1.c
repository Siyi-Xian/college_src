/* main.c ---
 *
 * Filename: main.c
 * Description: Basic C program to print out hello world!
 * Author: Siyi Xian
 * User Name: siyixian
 *
 */

#include <stdio.h>
#include <unistd.h>

int main(void) {
    printf("Hello World!\n");
    
    while (0 == 0) {
        sleep(1);
        printf(".");
	fflush(stdout);
    }
    
    return(0);
}

