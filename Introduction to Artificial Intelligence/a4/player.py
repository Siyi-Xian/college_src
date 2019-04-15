import math

class Player:

    def __init__(self, depthLimit, isPlayerOne):

        self.isPlayerOne = isPlayerOne
        self.depthLimit = depthLimit
    # Helper function for heuristic
    def heuristic_helper(self, player_choices):
        # num_player_one = 0
        # num_player_two = 0
        # for k in player_choices:
        #     if k is 0:
        #         num_player_one += 1
        #     elif k is 1:
        #         num_player_two += 1
        # if num_player_one is 4 and num_player_two is 0:
        #     return math.inf / 2
        # if num_player_two is 4 and num_player_one is 0:
        #     return -math.inf / 2
        # if num_player_one is 3 and num_player_two is 0:
        #     return math.inf / 4
        # if num_player_two is 3 and num_player_one is 0:
        #     return -math.inf / 4
        # if num_player_one is 3 and num_player_two is 1:
        #     return -pow(10, num_player_one - num_player_one)
        # if num_player_two is 3 and num_player_one is 1:
        #     return pow(10, num_player_two - num_player_one)
        # if num_player_one > num_player_two:
        #     return pow(10, num_player_one - num_player_two)
        # elif num_player_two > num_player_one:
        #     return -pow(10, num_player_two - num_player_one)
        heur = 0
        if '0, 0, 0, 0' in str(player_choices):
            return math.pow(17, 6)
        if '1, 1, 1, 1' in str(player_choices):
            return -math.pow(17, 6)
        if '1, 1, 1' in str(player_choices) and '0' in str(player_choices):
            return math.pow(17, 5)
        if '1, 1, 0, 1' in str(player_choices):
            return math.pow(17, 5)
        if '1, 0, 1, 1' in str(player_choices):
            return math.pow(17, 5)
        if '1, 1, 1' in str(player_choices) and '0' in str(player_choices):
            return math.pow(17, 5)
        if '0, 0, 0' in str(player_choices) and '1' in str(player_choices):
            return -math.pow(17, 5)
        if '1, 1, 1' in str(player_choices):
            return -math.pow(17, 4)
        if '1, 0, 1' in str(player_choices):
            return -math.pow(17, 3)
        if '0, -1, 0' in str(player_choices):
            return math.pow(17, 3)
        if '0, 0, -1, 0' in str(player_choices):
            return math.pow(17, 3)
        if '0, -1, 0, 0' in str(player_choices):
            return math.pow(17, 3)
        if '0, 0, 0' in str(player_choices):
            return math.pow(17, 3)
        if '0, 0' in str(player_choices):
            heur += math.pow(17, 2)
        if '0' in str(player_choices):
            heur += math.pow(17, 1)
        if '1, 1' in str(player_choices):
            heur -= math.pow(17, 2)
        if '1' in str(player_choices):
            heur -= math.pow(17, 1)
        return heur

    # TODO
    # Returns a heuristic for the board position
    # Good positions for 0 pieces should be positive and good positions for 1 pieces
    # should be negative
    # this is really bad but whatever
    def heuristic(self, board):
        heur = 0
        this_board = board.board

        for j in range(0, board.WIDTH):
            for i in range(0, len(this_board[j])):
                # Horizontal
                if i + 3 < board.HEIGHT:
                    player_choices = []
                    for k in range(0, 4):
                        try:
                            player_choices.append(this_board[i + k][j])
                        except IndexError:
                            player_choices.append(-1)
                    heur += self.heuristic_helper(player_choices)
                # Vertical
                if j + 3 < board.WIDTH:
                    player_choices = []
                    for k in range(0, 4):
                        try:
                            player_choices.append(this_board[i][j + k])
                        except IndexError:
                            player_choices.append(-1)
                    heur += self.heuristic_helper(player_choices)
                if i + 3 < board.HEIGHT and j + 3 < board.WIDTH:
                    # Diagonal
                    player_choices = []
                    for k in range(0, 4):
                        try:
                            player_choices.append(this_board[i + k][j + k])
                        except IndexError:
                            player_choices.append(-1)
                    heur += self.heuristic_helper(player_choices)
                    # Anti-Diagonal
                    player_choices = []
                    for k in range(0, 4):
                        try:
                            player_choices.append(this_board[i + 3 - k][j + k])
                        except IndexError:
                            player_choices.append(-1)
                    heur += self.heuristic_helper(player_choices)

        return heur
    # def heuristic(self, board):
    #     p1Count = 0
    #     p2Count = 0
    #     for col in board.board:
    #         shouldDouble = False
    #         lastElSeen = -1
    #         for el in col:
    #             if el == lastElSeen:
    #                 if el == 0:
    #                     p1Count += 2 if shouldDouble else 1
    #                 else:
    #                     p2Count += 2 if shouldDouble else 1
    #                 shouldDouble = True
    #             else:
    #                 shouldDouble = False
    #                 lastElSeen = el
    #     return p1Count - p2Count


class PlayerMM(Player):
    def __init__(self, depthLimit, isPlayerOne):
        super().__init__(depthLimit, isPlayerOne)

    # TODO
    # returns the optimal column to move in by implementing the Minimax algorithm
    def findMove(self, board):
        move, score = self.minimax(board, self.depthLimit, self.isPlayerOne)
        return move

    def minimax(self, board, depth, player):
        if board.isTerminal() == 0: return None, 0
        # player one win
        if board.isTerminal() == 1: return None, math.inf
        # player two win
        if board.isTerminal() == 2: return None, -math.inf
        if depth == 0: return None, self.heuristic(board)

        best_move = None
        best_score = 0

        for move in board.children():
            score = self.minimax(move[1], depth - 1, not player)[1]
            if best_move is None or (player is True and score > best_score) or (
                    player is False and score < best_score):
                best_score = score
                best_move = move[0]

        return best_move, best_score


class PlayerAB(Player):

    def __init__(self, depthLimit, isPlayerOne):
        super().__init__(depthLimit, isPlayerOne)

    # TODO
    # returns the optimal column to move in by implementing the Alpha-Beta algorithm
    def findMove(self, board):
        move, score = self.alphaBeta(board, self.depthLimit, self.isPlayerOne, -math.inf, math.inf)
        return move

    def alphaBeta(self, board, depth, player, alpha, beta):
        # if (board.hash(), depth) in self.cache.keys():
        #     return self.cache.get((board.hash(), depth))
        terminate = board.isTerminal()

        if terminate == 0: return None, 0
        # player one win
        if terminate == 1: return None, math.inf / 2
        # player two win
        if terminate == 2: return None, -math.inf / 2
        if depth == 0: return None, self.heuristic(board)

        best_move = None
        best_score = 0

        for move in board.children():
            score = self.alphaBeta(move[1], depth - 1, not player, alpha, beta)[1]
            if player is True:
                if best_move is None or score > best_score:
                    best_score = score
                    best_move = move[0]

                if score > alpha:
                    alpha = score
                if alpha >= beta: break
            if player is False:
                if best_move is None or score < best_score:
                    best_score = score
                    best_move = move[0]

                if score < beta:
                    beta = score
                if alpha >= beta: break
        # print(self.cache)
        # self.cache[(board.hash(), depth)] = (best_move, best_score)
        return best_move, best_score


class PlayerABDP(Player):

    def __init__(self, depthLimit, isPlayerOne):
        super().__init__(depthLimit, isPlayerOne)

        self.resolved = {}

    # TODO
    # returns the optimal column to move in by implementing the Alpha-Beta algorithm with dynamic programming
    def findMove(self, board):
        pass


#######################################################
###########Example Subclass for Testing
#######################################################

# This will inherit your findMove from above, but will override the heuristic function with
# a new one; you can swap out the type of player by changing X in "class TestPlayer(X):"
class TestPlayer(PlayerMM):

    def __init__(self, depthLimit, isPlayerOne):
        super().__init__(depthLimit, isPlayerOne)

    # define your new heuristic here
    def heurisitic(self):
        pass
