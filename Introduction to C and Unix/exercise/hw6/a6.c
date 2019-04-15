//
//  main.c
//  Assigment6
//
//  Created by 鲜思轶 on 10/9/17.
//  Copyright © 2017 Siyi Xian. All rights reserved.
//

#include <stdio.h>

const char metrix[][4] = {{0,0,0,0},{0,0,0,0},{'A','B','C',0},{'D','E','F',0},{'G','H','I',0},{'J','K','L',0},{'M','N','O',0},{'P','Q','R','S'},{'T','U','V',0},{'W','X','Y','Z'}};
char ans[4000][7];
int counter;


void findnumber(char * num, int np) {
    if (*num == '\0') {
        counter++;
        return;
    }
    int i = 0;
    while (metrix[*num - '0'][i] != 0 && i < 4) {
        ans[counter][np] = metrix[*num - '0'][i];
        findnumber(num + 1, np + 1);
        int j;
        for (j = 0; j < 7; j++) ans[counter][j] = ans[counter - 1][j];
        i++;
    }
    return;
}

 
int main() {
    char file_name[100];
    printf ("Please enter a file name: ");
    scanf("%s", file_name);
    FILE * fp = fopen(file_name, "w");
    char phone_number[8];
    printf ("Please enter a phone number: ");
    scanf("%s", phone_number);
    
    findnumber(phone_number, 0);
    int j;
    for (j = 0; j < 7; j++) ans[counter][j] = ' ';
    
    int i;
    for (i = 0; ans[i][0] != 0; i++){
        fwrite(ans[i], 1, sizeof(ans[i]), fp);
        fwrite("\n", 1, sizeof(char), fp);
    }
    
    
    
    return 0;
}

