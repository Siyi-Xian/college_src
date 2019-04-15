// Abigail Alwine and Siyi Xian
// dump.c
//

#include <stdio.h>

void dump_memory(void *p, int len)
{
    int i;
    
    // print title
    printf("%s %9s %5s %6s %12s %13s %14s\n", "address", "char", "hexCh", "short", "integer", "float", "doubleFloat");
    
    for (i = 0; i < len; i++) {
        // address
        printf("%10p", p + i);
        // char
        if (*(unsigned char *)(p + i) == 0x14 || *(unsigned char *)(p + i) == 0 || *(unsigned char *)(p + i) == 0x18 || *(unsigned char *)(p + i) == 9 || *(unsigned char *)(p + i) == 0xa)
            printf(" %2c ", '?');
        else
            printf(" %2c ", *(char *)(p + i));
        // hexchar
        printf(" 0x%02x", *(unsigned char *)(p + i));
        if (i % 2 == 0) {
            // short
            printf(" %+6d", *(short *)(p + i));
            if (i % 4 == 0) {
                // int
                printf(" %+12d", *(int *)(p + i));
                //float
                printf(" %+.6e", *(float *)(p + i));
                if (i % 8 == 0)
                    // double float
                    printf(" %+.6e", *(double *)(p + i));
            }
        }
        // new line
        printf("\n");
    }
}
