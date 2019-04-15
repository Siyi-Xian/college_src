#include <stdio.h>

void display();

int main(void) 
{   
    	int size=5;
	int Matrix1[size][size];
	int i, j;
	for (i=0; i<size; i++) 
 	    for (j=0; j<size; j++) 
            	Matrix1[i][j]=i+j;
	display(Matrix1, size);
    return 0.0;
}

void display(int Matrix1[][5], int size)
{
	int i, j;
	for (i=0; i<size; i++) {
 	    for (j=0; j<size; j++) 
            	printf("%d, ", Matrix1[i][j]);
            printf("\n");
        }
    return;
}
