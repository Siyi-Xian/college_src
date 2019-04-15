#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <stdlib.h>

int main(int argc, const char * argv[]) {
//    while (0 == 0) {
//        sleep(3);
//        printf("Hello World!\n");
//    }
    for (int i = 0; i >= 0; i++) {
        FILE *fp = NULL;
        char file_name[20];
        sprintf(file_name, "./test_%d.txt", i);
        fp = fopen(file_name, "w+");
        fprintf(fp, "This is testing for fprintf...\n");
        fputs("This is testing for fputs...\n", fp);
        fclose(fp);
        
        sleep(3);
        printf("Hello World!\n");
    }
    return 0;
}
