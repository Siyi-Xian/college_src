//
//  main.c
//  Assigment6PartTwo
//
//  Created by 鲜思轶 on 10/10/17.
//  Copyright © 2017 Siyi Xian. All rights reserved.
//


#include <stdio.h>

int main() {
    char file_name[100];
    printf("Please enter the name of dictionary: ");
    scanf ("%s", file_name);
    
    printf("Please enter the words you want to find: ");
    char find[8];
    scanf ("%s", find);
    
    FILE * f = fopen(file_name, "r");
    char words[8];
    int i = 0;
    int answer = 0;
    while (fgets(words, 8, f) != NULL && answer == 0){
        int i, counter = 0;
        for (i = 0; i < 7; i++)
	    if (words[i] == find[i])
		counter++;
	if (counter == 7)
	    answer = 1;
    }
    
    if (answer)
	printf ("You find it!\n");
    else
	printf ("You didn't find it\n");
    return 0;
}

