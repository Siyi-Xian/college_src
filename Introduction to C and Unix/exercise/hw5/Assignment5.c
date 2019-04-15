//
//  main.c
//  Assignment5
//
//  Created by Siyi Xian on 9/26/17.
//  Copyright © 2017 Siyi Xian. All rights reserved.
//

#include <stdio.h>

struct customer {
    char lastName[ 15 ];
    char firstName[ 15 ];
    unsigned int customerNumber;
    struct {
        char phoneNumber[ 11 ];
        char address[ 50 ];
        char city[ 15 ];
        char state[ 3 ];
        char zipCode[ 6 ];
    } personal;
} customerRecord, *customerPtr;

// Problem 1
/*
(a) Member lastName of structure customerRecord.
     customerRecord.lastName
(b) Member lastName of the structure pointed to by customerPtr.
     (*customerPtr).lastName
(c) Member firstName of structure customerRecord.
     customerRecord.firstName
(d) Member firstName of the structure pointed to by customerPtr.
     (*customerPtr).firstName
(e) Member customerNumber of structure customerRecord.
     customerRecord.customerNumber
(f) Member customerNumber of the structure pointed to by customerPtr.
     (*customerPtr).customerNumber
(g) Member phoneNumber of member personal of structure customerRecord.
     customerRecord.person.phoneNumber
(h) Member phoneNumber of member personal of the structure pointed to by customerPtr.
     (*customerPtr).person.phoneNumber
(i) Member address of member personal of structure customerRecord.
     customerRecord.person.address
(j) Member address of member personal of the structure pointed to by customerPtr.
     (*customerPtr).person.address
(k) Member city of member personal of structure customerRecord.
     customerRecord.person.city
(l) Member city of member personal of the structure pointed to by customerPtr.
     (*customerPtr).person.city
(m) Member state of member personal of structure customerRecord.
     customerRecord.person.state
(n) Member state of member personal of the structure pointed to by customerPtr.
     (*customerPtr).person.state
(o) Member zipCode of member personal of structure customerRecord.
     customerRecord.person.zipCode
(p) Member zipCode of member personal of the structure pointed to by customerPtr.
     (*customerPtr).person.zipCode
*/

// Problem 2
// Structure of date
struct Date {
    int month, day, year;
} birth, today;

// Structure of health profile
struct HealthProfile {
    char * firstName;
    char * lastName;
    char * gender;
    struct Date * birthDay;
    float * height;
    float * weight;
} healthProfile;

void healthRecord() {
    // store data
    char charData[3][20];
    float floatData[2];
    
    // get data from user
    printf ("Please enter your first and last name:\n");
    scanf ("%s %s", charData[0], charData[1]);
    printf ("Please enter your gender (M or F):\n");
    scanf ("%s", charData[2]);
    printf ("Please enter your birth day (mm/dd/yyyy):\n");
    scanf ("%d/%d/%d", &birth.month, &birth.day, &birth.year);
    printf ("Please enter your height (in inches) and weight (in pounds):\n");
    scanf ("%f %f", &floatData[0], &floatData[1]);
    
    // initialize pointers' structure
    healthProfile.firstName = charData[0];
    healthProfile.lastName = charData[1];
    healthProfile.gender = charData[2];
    healthProfile.birthDay = &birth;
    healthProfile.height = &floatData[0];
    healthProfile.weight = &floatData[1];
    
    // Assume that the data of today is 09/26/2017
    today.day = 26;
    today.month = 9;
    today.year = 2017;
    
    // count age
    int age = today.year - (*healthProfile.birthDay).year;
    if ((today.month < (*healthProfile.birthDay).month) || (today.month == (*healthProfile.birthDay).month && today.day < (*healthProfile.birthDay).day)) age--;
    printf ("\nYour age is %d.\nYour maximum heart rate is %d.\nYour target heart-rate range is %0.0f - %0.0f.\n", age, 220 - age, (220 - age) * 0.5, (220 - age) * 0.85);
    
    // count BMI
    float bmi = (*healthProfile.weight * 703) / (*healthProfile.height * *healthProfile.height);
    printf ("Your BMI index is: %0.1f.\nYour are ", bmi);
    if (bmi < 18.5)
        printf ("underweight.\n");
    else if (bmi < 25)
        printf ("normal.\n");
    else if (bmi < 30)
        printf ("overweight.\n");
    else printf ("obese.\n");
    
    return;
}

// Problem 3

// display bits of an unsigned int value
void displayBits(unsigned int value) {
    // define displayMask and left shift 31 bits
    unsigned int displayMask = 1 << 31;
    printf("%4u = ", value);
    // loop through bits
    for (unsigned int c = 1; c <= 32; ++c) {
        putchar(value & displayMask ? '1' : '0');
        value <<= 1; // shift value left by 1
        
        if (c % 8 == 0) // output space after 8 bits
            putchar(' ');
    }
    putchar('\n');
}

void reverseBits() {
    unsigned int num, reversedNum = 0;
    printf ("Please enter the number your want to reverse:\n");
    scanf ("%d", &num);
    displayBits(num);
    while (num > 0) {
        reversedNum = (reversedNum << 1) + (num & 1);
        num = num >> 1;
    }
    displayBits(reversedNum);
    printf ("The number after reverse is %d.\n", reversedNum);
    return;
}

int main(int argc, const char * argv[]) {
    // Problem 1
    customerPtr = &customerRecord;
    
    // Problem 2
    healthRecord();
    
    // Problem 3
    reverseBits();
    
    return 0;
}

