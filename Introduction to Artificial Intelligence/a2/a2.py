##################
# My Teammates is Zhongyu Yang (zy7)
##################

import csv
import itertools

class Board():

    ##########################################
    ####   Constructor
    ##########################################
    def __init__(self, filename):

        #initialize all of the variables
        self.n2 = 0
        self.n = 0
        self.spaces = 0
        self.board = None
        self.valsInRows = None
        self.valsInCols = None
        self.valsInBoxes = None
        self.unSolved = None

        #load the file and initialize the in-memory board with the data
        self.loadSudoku(filename)


    #loads the sudoku board from the given file
    def loadSudoku(self, filename):

        with open(filename) as csvFile:
            self.n = -1
            reader = csv.reader(csvFile)
            for row in reader:

                #Assign the n value and construct the approriately sized dependent data
                if self.n == -1:
                    self.n = int(len(row) ** (1/2))
                    if not self.n ** 2 == len(row):
                        raise Exception('Each row must have n^2 values! (See row 0)')
                    else:
                        self.n2 = len(row)
                        self.spaces = self.n ** 4
                        self.board = {}
                        self.valsInRows = [set() for _ in range(self.n2)]
                        self.valsInCols = [set() for _ in range(self.n2)]
                        self.valsInBoxes = [set() for _ in range(self.n2)]
                        self.unSolved = set(itertools.product(range(self.n2), range(self.n2)))

                #check if each row has the correct number of values
                else:
                    if len(row) != self.n2:
                        raise Exception('Each row mus\t have the same number of values. (See row ' + str(reader.line_num - 1) + ')')

                #add each value to the correct place in the board; record that the row, col, and box contains value
                for index, item in enumerate(row):
                    if not item == '':
                        self.board[(reader.line_num-1, index)] = int(item)
                        self.valsInRows[reader.line_num-1].add(int(item))
                        self.valsInCols[index].add(int(item))
                        self.valsInBoxes[self.rcToBox(reader.line_num-1, index)].add(int(item))
                        self.unSolved.remove((reader.line_num-1, index))

    ##########################################
    ####   Move Functions - YOUR IMPLEMENTATIONS GO HERE
    ##########################################

    #gets the unsolved space with the most current constraints
    def getMostConstrainedUnsolvedSpace(self):
        tupleDomainSize={}
        c = set(range(1, self.n2 + 1))
        for space in self.unSolved:
            valueRow = self.valsInRows[space[0]]
            valueCol = self.valsInCols[space[1]]
            valueBox = self.valsInBoxes[self.rcToBox(space[0],space[1])]
            constraints = valueRow.union(valueCol, valueBox)
            domain = c - constraints
            tupleDomainSize[(space)] = len(domain)
        return min(tupleDomainSize, key=lambda k: tupleDomainSize[k])

    #returns True if the move is not blocked by any constraints
    def isValidMove(self,space,val):
        return (val not in self.valsInRows[space[0]]
                and val not in self.valsInCols[space[1]]
                and val not in self.valsInBoxes[self.rcToBox(space[0], space[1])])

    #makes a move, records that its in the row, col, and box, and removes the space from unSolved
    def makeMove(self, space, val):
        self.board[(space)] = val
        self.valsInRows[space[0]].add(val)
        self.valsInCols[space[1]].add(val)
        self.valsInBoxes[self.rcToBox(space[0], space[1])].add(val)
        self.unSolved.remove(space)

    #removes the move, its record in its row, col, and box, and adds the space back to unSolved
    def removeMove(self, space, val):
        del self.board[space]
        self.valsInRows[space[0]].remove(val)
        self.valsInCols[space[1]].remove(val)
        self.valsInBoxes[self.rcToBox(space[0],space[1])].remove(val)
        self.unSolved.add(space)


    ##########################################
    ####   Utility Functions
    ##########################################

    #converts a given row and column to its inner box number
    def rcToBox(self, row, col):
        return self.n * (row // self.n) + col // self.n


    #prints out a command line representation of the board
    def print(self):
        for r in range(self.n2):
            #add row divider
            if r % self.n == 0 and not r == 0:
                if self.n2 > 9:
                    print("  " + "----" * self.n2)
                else:
                    print("  " + "---" * self.n2)

            row = ""

            for c in range(self.n2):

                if (r,c) in self.board:
                    val = self.board[(r,c)]
                else:
                    val = None

                #add column divider
                if c % self.n == 0 and not c == 0:
                    row += " | "
                else:
                    row += "  "

                #add value placeholder
                if self.n2 > 9:
                    if val is None: row += "__"
                    else: row += "%2i" % val
                else:
                    if val is None: row += "_"
                    else: row += str(val)
            print(row)

class Solver:

    ##########################################
    ####   Constructor
    ##########################################
    def __init__(self,filename):
        self.board = Board(filename)
        self.solve()

    ##########################################
    ####   Solver
    ##########################################

    #recursively selects the most constrained unsolved space and attempts
    #to assign a value to it
    #
    #upon completion, it will leave the board in the solved state (or original
    #state if a solution does not exist)
    def solve(self):
        initial_board = self.board
        if len(self.board.unSolved) == 0:
            return

        c = set(range(1, self.board.n2 + 1))
        unsolved_space = self.board.getMostConstrainedUnsolvedSpace()
        value_row = self.board.valsInRows[unsolved_space[0]]
        value_col = self.board.valsInCols[unsolved_space[1]]
        value_box = self.board.valsInBoxes[self.board.rcToBox(unsolved_space[0], unsolved_space[1])]
        constraints = value_row.union(value_col, value_box)
        domain = c - constraints
        for possible_value in domain:
            self.board.makeMove(unsolved_space, possible_value)
            self.solve()
            if len(self.board.unSolved) == 0:
                return
            self.board.removeMove(unsolved_space, possible_value)
        return



if __name__ == "__main__":

    #change this to the input file that you'd like to test
    s = Solver('tests/example.csv')
