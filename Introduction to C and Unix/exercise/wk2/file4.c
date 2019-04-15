/*Debugging quiz - File No: 4 */
/* This program should perform the duties of a basic calculator */
// Check for possible logical errors and rectify them 

#include<stdio.h>

int main(void){
	char input;
	float num1, num2, result;
	printf("Welcome to the Calculator\nOperation choices:\tAddition(A)\n\t\t\tSubtraction(S)\n\t\t\tMultiplication(M)\n\t\t\tDivision(D)\nEnter choice: ");
	scanf("%s",&input);
	if(input == 'A' || input == 'S' || input == 'M' || input == 'D'){
		printf("Enter both numbers in required sequence: ");
		scanf("%f%f",&num1,&num2);
		switch(input){
			case 'A': result = num1 + num2;
          			break;
			case 'S': result = num1 - num2;
          			break;
			case 'M': result = num1 * num2;
          			break;
			case 'D': if (num2 != 0 || num2 != 0.0)
					 result = num1 / num2;		//Are there any logical errors possible? If so, how will you solve it?
				else {
					printf ("The second number cannot be zero!\n");
					result = 0xffffffff;
				} 
	 			break;                			  
			default: break;
		}
		if(result != 0xffffffff)
			printf("The final result = %f\n", result);
	}
	else{
		printf("Please choose a valid operation\n");
	}
	return(0);
}
