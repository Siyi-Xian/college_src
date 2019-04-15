# Assignment 1
import math

#################################
# Problem 1
#################################
# Objectives:
# (1) Write a recursive function to compute the nth fibonacci number

def fib(n):
    if n == 0:
        return 0
    if n == 1:
        return 1
    return fib(n - 1) + fib(n - 2)



#################################
# Problem 2
#################################
# Objectives:
# (1) Write a function which returns a list of the first and last items in a given list

def firstLast(n):
    l = len(n)
    if l == 0:
        return 0
    if l == 1:
        return n
    return [n[0], n[l - 1]]




#################################
# Problem 3
#################################
# Objectives:
# (1) Write a function which takes a matrix and returns the transpose of that matrix
# Note: A matrix is represented as a 2-d array: [[1,2,3],[4,5,6],[7,8,9]]


def transpose(matrix):
    return [[matrix[j][i] for j in range(len(matrix))] for i in range(len(matrix[0]))]





#################################
# Problem 4
#################################
# Objectives:
# (1) Write a function which takes two points of the same dimension, and finds the euclidean distance between them
# Note: A point is represented as a tuple: (0,0)

def euclidean(p1,p2):
    if len(p1) != len(p2) :
        return -1
    l = len(p1)
    n = [(p1[i] - p2[i]) ** 2 for i in range(l)]
    return sum(n) ** 0.5






#################################
# Problem 5
#################################

# A Node is an object
# - value : Number
# - children : List of Nodes
class Node:
    def __init__(self, value, children):
        self.value = value
        self.children = children


exampleTree = Node(1,[Node(2,[]),Node(3,[Node(4,[Node(5,[]),Node(6,[Node(7,[])])])])])



# Objectives:
# (1) Write a function to calculate the sum of every node in a tree (iteratively)

def sumNodes(root):
    ans = 0
    stack = [root]
    while len(stack) != 0 :
        ans = ans + stack[0].value
        stack.extend(stack.pop(0).children)
    return ans

# (2) Write a function to calculate the sum of every node in a tree (recursively)

def sumNodesRec(root):
    return root.value + sum([sumNodes(c) for c in root.children])






#################################
# Problem 6
#################################
# Objectives:
# (1) Write a function compose, which takes an inner and outer function
# and returns a new function applying the inner then the outer function to a value

def compose(f_outer, f_inner):
    return lambda x: f_outer(f_inner(x))





#################################
# Bonus
#################################
# Objectives:
# (1) Create a string which has each level of the tree on a new line

def treeToString(root):
    stack = [root]
    string = ""
    while len(stack) != 0 :
        for j in [i.value for i in stack] :
            string = string + str(j)
        string = string + "\n"
        temp = [i for i in stack]
        stack = []
        while len(temp) != 0 :
            stack.extend(temp.pop(0).children)
    return string

