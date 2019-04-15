/* game.c --- 
 * 
 * Filename: game.c
 * Description: 
 * Author: Bryce Himebaugh
 * Maintainer: 
 * Created: Tue Sep  6 11:08:59 2016
 * Last-Updated: 
 *           By: 
 *     Update #: 0
 * Keywords: 
 * Compatibility: 
 * 
 */

/* Commentary: 
 * 
 * 
 * 
 */

/* Change log:
 * 
 * 
 */

/* Copyright (c) 2016 The Trustees of Indiana University and 
 * Indiana University Research and Technology Corporation.  
 * 
 * All rights reserved. 
 * 
 * Additional copyrights may follow 
 */

/* Code: */
#include <unistd.h> 
#include <ncurses.h>
#include <time.h>
#include "highscore.h"
#include "game.h"
#include "well.h"
#include "tetris.h"
#include "tetromino.h"
#include "key.h"

void init_game(void) {
  int x,y;
}

highscore_t *game(highscore_t *highscores) {
  static int state = INIT;
  tetromino_t *next = NULL;
  tetromino_t *current = NULL;
  well_t *w;
  int x,y;
  int c;
  int arrow;
  struct timespec tim = {0,1000000};
  struct timespec tim_ret;
  int move_counter = 0;
  int move_timeout = 500;
  int status;
  int counter = 0;
  int lines_cleared = 0;
  int score = 0;
  char str[80];  
  double tstart = clock();
  double tnow = clock();
  int rate;
  highscore_t *list;
  char name[20];
  printf("Enter your name.\n");
  scanf("%s",&name);
  while(1) {
    switch(state) {
    case INIT:               // Initialize the game, only run one time 
      initscr();
      nodelay(stdscr,TRUE);  // Do not wait for characters using getch.  
      noecho();              // Do not echo input characters 
      getmaxyx(stdscr,y,x);  // Get the screen dimensions 
      w = init_well(((x/2)-(WELL_WIDTH/2)),3,WELL_WIDTH,WELL_HEIGHT);
      draw_well(w);
      srand(time(NULL));     // Seed the random number generator with the time. Used in create tet. 
      display_score(score,w->upper_left_x-15,w->upper_left_y);
      mvprintw(8,((x/2)-(WELL_WIDTH/2))-30,"You have spend %d secounds.",(int)(tnow-tstart)/50000);
      mvprintw(10,((x/2)-(WELL_WIDTH/2))-30,"You have %d secounds left.",300 - (int)(tnow-tstart)/50000); 
     
      state = ADD_PIECE;
      break;
    case ADD_PIECE:          // Add a new piece to the game
      tnow = clock();
      rate = (int)(BASE_FALL_RATE * (rand()%100/50.0));
      score = compute_score(score, prune_well(w));
      display_score(score, w->upper_left_x-15,w->upper_left_y);
      mvprintw(8,((x/2)-(WELL_WIDTH/2))-30,"You have spend %d secounds.",(int)(tnow-tstart)/50000);
      mvprintw(10,((x/2)-(WELL_WIDTH/2))-30,"You have %d secounds left.",300 - (int)(tnow-tstart)/50000);
     
    
      if (next) {
	undisplay_next_tetromino (((x/2)+(WELL_WIDTH/2))+10, 5, next);
	current = next;
	next = create_tetromino ((w->upper_left_x+(w->width/2)), w->upper_left_y);
	display_next_tetromino (((x/2)+(WELL_WIDTH/2))+10, 5, next);
      }
      else {
	current = create_tetromino ((w->upper_left_x+(w->width/2)), w->upper_left_y);
	next = create_tetromino ((w->upper_left_x+(w->width/2)), w->upper_left_y);
	display_next_tetromino (((x/2)+(WELL_WIDTH/2))+10, 5, next);
      }
      if (move_tet(current,current->upper_left_x,current->upper_left_y+1) == MOVE_FAILED)
        state = GAME_OVER;
      else {
        display_tetromino(current);
        state = MOVE_PIECE;
      }
      break;
    case MOVE_PIECE:         // Move the current piece
      tnow = clock(); 
      mvprintw(8,((x/2)-(WELL_WIDTH/2))-30,"You have spend %d secounds.",(int)(tnow-tstart)/50000);
      mvprintw(10,((x/2)-(WELL_WIDTH/2))-30,"You have %d secounds left.",300 - (int)(tnow-tstart)/50000);


      if (tnow - tstart > 50000 * 60 * 5) {
        state = GAME_OVER;
        break;
      }
      if ((arrow = read_escape(&c)) != NOCHAR) {
	if (arrow == UP) {
	  undisplay_tetromino(current);
	  rotate_ccw(current);
	  display_tetromino(current);
	}
	else if (arrow == DOWN) {
	  undisplay_tetromino(current);
	  rotate_cw(current);
	  display_tetromino(current);
	}
	else if (arrow == LEFT) {
	  undisplay_tetromino(current);
	  move_tet(current,current->upper_left_x-1,current->upper_left_y);
	  display_tetromino(current);
	}
	else if (arrow == RIGHT) {
	  undisplay_tetromino(current);
	  move_tet(current,current->upper_left_x+1,current->upper_left_y);
	  display_tetromino(current);
	}
      	else if (arrow == REGCHAR) {
	  if (c == ' ') {
	    move_timeout = DROP_RATE;
 	  }
	  if (c == 'q') {
	    state = GAME_OVER;
 	  }
	}
      } 
      if (move_counter++ >= move_timeout) {
	counter++;
	undisplay_tetromino(current);
	status = move_tet(current,current->upper_left_x,current->upper_left_y+1);
	display_tetromino(current);
	if (status == MOVE_FAILED) {
	  state = ADD_PIECE;
	  move_timeout = rate;
	}
	move_counter = 0;
      }
      break;
    case GAME_OVER:
      nodelay(stdscr,FALSE);
      clear();
      
      list = load_scores("highscore.txt");
      list =insert_score(list,name,score);
      if (compare_highscore(list,score,10))
      {
        store_scores("highscore.txt",list);
      }      
      print_score_list(list,x/2-5,10,10);
      getmaxyx(stdscr,y,x);
      mvprintw(1,x/2-5,"  GAME_OVER  ");
      mvprintw(2,x/2-5,"Your score is %d", score);
      mvprintw(3,x/2-10,"You have spend %d secounds.",(int)(tnow-tstart)/50000);
      mvprintw(20,x/2-5,"Hit q to exit");
      getch(); // Wait for a key to be pressed. 
      state = EXIT;
      break;
    case EXIT:
      return(highscores);  // Return the highscore structure back to main to be stored on disk. 
      break;
    }
    refresh();
    nanosleep(&tim,&tim_ret);
  }
}

/* game.c ends here */
