//
//  main.c
//  Lab2
//
//  Created by 鲜思轶 on 1/18/19.
//  Copyright © 2019 Siyi Xian. All rights reserved.
//

#include <stdio.h>

int main(int argc, const char * argv[]) {
    int c; /* current character */
    int line = 0, words = 0, characters = 0;
    
    while ((c = getchar()) != EOF) {
        /* do something with c */
        if (c == ' ' || c == '\t' || c == '\r' || c == '\f' || c == '\a')
            words++;
        if (c == '\n')
            line++;
        characters++;
    }
}

