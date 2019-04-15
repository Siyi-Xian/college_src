//
//  main.c
//  HW0
//
//  Created by Siyi Xian on 1/23/19.
//  Copyright © 2019 Siyi Xian. All rights reserved.
//

#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>

typedef struct CELL *LIST;
struct CELL {
    int val;
    LIST next;
};

LIST stack = NULL;

void push(int val) {
    LIST c = (LIST) malloc(sizeof(struct CELL));
    if (c) {
        c->val = val;
        c->next = stack;
        stack = c;
    } else { /* handle error */ }
}

// recersivly print all element in stack
void printAll(LIST l) {
    if (l == NULL)
        return;
    printf("%d\n", l->val);
    printAll(l->next);
}

int main(int argc, const char * argv[]) {
    char c[10];
    
    while (scanf("%s", c) != EOF) {
        // check quit condition
        if (c[0] == 'q')
            break;
        
        // check numric condition
        int num = atoi(c);
        if (num != 0 || (c[0] == '0' && c[1] == '\0'))
            push(num);
        
        // do miscellaneous commands and binary operators
        else {
            switch (c[0]) {
                case 'p': //prints the top element and leaves the stack unchanged.
                    printf("%d\n", stack->val);
                    break;
                    
                case 'f': //prints the entire contents of the stack, top to bottom, with each entry on a separate line, and leaves the stack unchanged.
                    printAll(stack);
                    break;
                    
                case 'c': //clears the stack completely.
                    stack = NULL;
                    break;
                    
                case 'a': //replace the top stack element by its absolute value
                    stack->val = abs(stack->val);
                    break;
                    
                case 'm': //unary minus of top stack element
                    stack->val = -stack->val;
                    break;
                    
                case 'd': //duplicates the top stack element
                    push(stack->val);
                    break;
                    
                default: // binary operators
                    // check invalid binary operators
                    if (stack == NULL)
                        printf("rpn: stack empty\n");
                    else if (stack->next == NULL)
                        printf("rpn: only one number\n");
                    
                    // binary operators
                    else {
                        switch (c[0]) {
                            case '+':
                                stack->val  = stack->next->val + stack->val;
                                stack->next = stack->next->next;
                                break;
                                
                            case '-':
                                stack->val  = stack->next->val - stack->val;
                                stack->next = stack->next->next;
                                break;
                                
                            case '*':
                                stack->val  = stack->next->val * stack->val;
                                stack->next = stack->next->next;
                                break;
                                
                            case '/':
                                stack->val  = stack->next->val / stack->val;
                                stack->next = stack->next->next;
                                break;
                                
                            default:
                                break;
                        }
                    }
                    break;
            }
        }
    }
}
