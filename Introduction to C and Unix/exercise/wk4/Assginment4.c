// Fig. 7.24: fig07_24.c
// Card shuffling and dealing.
/*
 Siyi Xian
 09/19/2017
*/
#include <stdio.h>
#include <stdlib.h>
#include <time.h>

#define SUITS 4
#define FACES 13
#define CARDS 52

// prototypes
void shuffle(unsigned int wDeck[][FACES]); // shuffling modifies wDeck
void deal(unsigned int wDeck[][FACES], const char *wFace[], const char *wSuit[]); // dealing doesn't modify the arrays
void pair();
void twoPair();
void threeKind();
void fourKind();
void flush();
void straight();
struct Poker {
    size_t face;
    size_t suit;
}fivePokers[5];

int main(void)
{
    // initialize deck array
    unsigned int deck[SUITS][FACES] = { 0 };
    
    srand(time(NULL)); // seed random-number generator
    
    shuffle(deck); // shuffle the deck
    
    // initialize suit array
    const char *suit[SUITS] =
    {"Hearts", "Diamonds", "Clubs", "Spades"};
    
    // initialize face array
    const char *face[FACES] =
    {"Ace", "Deuce", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King"};
    
    deal(deck, face, suit); // deal the deck
}

// shuffle cards in deck
void shuffle(unsigned int wDeck[][FACES])
{
    // for each of the cards, choose slot of deck randomly
    for (size_t card = 1; card <= CARDS; ++card) {
        size_t row; // row number
        size_t column; // column number
        
        // choose new random location until unoccupied slot found
        do {
            row = rand() % SUITS;
            column = rand() % FACES;
        } while(wDeck[row][column] != 0); // end do...while
        
        // place card number in chosen slot of deck
        wDeck[row][column] = card;
    }
}

// deal cards in deck
void deal(unsigned int wDeck[][FACES], const char *wFace[], const char *wSuit[])
{
    int i = 0;
    // deal each of the cards
    for (size_t card = 1; card <= 5; ++card) {
        // loop through rows of wDeck
        for (size_t row = 0; row < SUITS; ++row) {
            // loop through columns of wDeck for current row
            for (size_t column = 0; column < FACES; ++column) {
                // if slot contains current card, display card
                if (wDeck[row][column] == card) {
                    fivePokers[i].face = column;
                    fivePokers[i].suit = row;
                    i++;
                    printf("%5s of %-8s%zu\n", wFace[column], wSuit[row], card);
                    //printf("%d %d\n", column, row);
                } 
            } 
        } 
    }
    
    pair();
    twoPair();
    threeKind();
    fourKind();
    flush();
    straight();
}

// determain one pair
void pair() {
    for (size_t i = 0; i < 5; i++)
        for (size_t j = i + 1; j < 5; j++)
            if (fivePokers[i].face == fivePokers[j].face) {
                printf ("It contains a pair!\n");
                return;
            }
    printf ("It does not contain a pair!\n");
    return;
}

// determain two pairs
void twoPair() {
    int pairs = 0;
    int pairStack[] = {1, 1, 1, 1, 1};
    for (size_t i = 0; i < 5; i++)
        for (size_t j = i + 1; j < 5; j++)
            if (fivePokers[i].face == fivePokers[j].face && pairStack[i] && pairStack[j]) {
                pairStack[i] = 0;
                pairStack[j] = 0;
                pairs++;
            }
    if (pairs >= 2)
        printf ("It contains two pairs!\n");
    else
        printf ("It does not contain two pairs!\n");
    return;
}

// determain three kind
void threeKind() {
    int kind[] = {0 ,0 ,0 ,0};
    for (size_t i = 0; i < 5; i++)
        kind[fivePokers[i].suit]++;
    for (size_t i = 0; i < 4; i++)
        if (kind[i] >= 3) {
            printf ("It contains three of a kind!\n");
            return;
        }
    printf ("It does not contain three of a kind!\n");
    return;
}

// determain four kind
void fourKind() {
    int kind[] = {0 ,0 ,0 ,0};
    for (size_t i = 0; i < 5; i++)
        kind[fivePokers[i].suit]++;
    for (size_t i = 0; i < 4; i++)
        if (kind[i] >= 4) {
            printf ("It contains four of a kind!\n");
            return;
        }
    printf ("It does not contain four of a kind!\n");
    return;
}

// determain flush
void flush() {
    int kind[] = {0 ,0 ,0 ,0};
    for (size_t i = 0; i < 5; i++)
        kind[fivePokers[i].suit]++;
    for (size_t i = 0; i < 4; i++)
        if (kind[i] >= 5) {
            printf ("It contains flush!\n");
            return;
        }
    printf ("It does not contain flush!\n");
    return;
}

// quick sort
int comp (const void * elem1, const void * elem2)
{
    int f = *((int*)elem1);
    int s = *((int*)elem2);
    if (f > s) return  1;
    if (f < s) return -1;
    return 0;
}

// determain stright
void straight() {
    size_t origin[5];
    for (size_t i = 0; i < 5; i++)
        origin[i] = fivePokers[i].face;
    psort(origin, sizeof(origin)/sizeof(size_t), sizeof(size_t), comp);
    
    for (size_t i = 1; i < 5; i++)
        if (origin[i] != origin[i - 1] + 1) {
            printf("It does not contain a straight!\n");
            return;
        }
    printf("It contains a straight!\n");
    return;
}

