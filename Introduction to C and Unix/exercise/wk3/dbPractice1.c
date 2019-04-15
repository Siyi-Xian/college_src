#include <stdio.h>

int values[10000];
double average(int, double); 
int sum(int); 

int main(void) 
{   
	int n, i;
	printf( "Debugging Practice 1 - Quiz 3, Q3\n\n");
	printf( "Please enter the number of values to be used: ");
	scanf("%d", &n);
	printf("Please enter %d values: ",n);
	for (i=0; i<n; i++) 
        	scanf("%d", &values[i]);
	
	double sigma = sum(n);
	double avg = average(n, sigma);
	printf( "The average of %0.0f / %d is: %f\n", sigma, n, avg); 
    	return 0;
}

double average(int num_scores, double sum)
{
	return sum / num_scores;
}

int sum(int num_scores)  
{
	int sum, i;
    	for (i =0; i<num_scores; i++)
		sum += values[i]; 
    return sum;
}
