/* tetromino.c --- 
 * 
 * Filename: tetromino.c
 * Description: 
 * Author: Bryce Himebaugh
 * Maintainer: 
 * Created: Fri Sep  2 09:38:49 2016
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

#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include <time.h>
#include <ncurses.h>
#include "tetromino.h"

const tetromino_t tetromino_types[9] = {
  {"block",
   {{0,0,0,0}, 
    {0,1,1,0},
    {0,1,1,0},
    {0,0,0,0}}, 
   0,
   0,
   '%',
   {0,0,0}},
  {"tee",
   {{0,0,0,0}, 
    {1,1,1,0},
    {0,1,0,0},
    {0,0,0,0}}, 
   0,
   0,
   '%',
   {0,0,0}},
  {"zigzag_l",
   {{0,0,1,0}, 
    {0,1,1,0},
    {0,1,0,0},
    {0,0,0,0}}, 
   0,
   0,
   '%',
   {0,0,0}},
  {"zigzag_r",
   {{0,1,0,0}, 
    {0,1,1,0},
    {0,0,1,0},
    {0,0,0,0}}, 
   0,
   0,
   '%',
   {0,0,0}},
  {"lform_l",
   {{0,0,1,0}, 
    {0,0,1,0},
    {0,1,1,0},
    {0,0,0,0}}, 
   0,
   0,
   '%',
   {0,0,0}},
  {"lform_r",
   {{0,1,0,0}, 
    {0,1,0,0},
    {0,1,1,0},
    {0,0,0,0}}, 
   0,
   0,
   '%',
   {0,0,0}},
  {"pipe",
   {{0,1,0,0},
    {0,1,0,0},
    {0,1,0,0},
    {0,1,0,0}}, 
   0,
   0,
   '%',
   {0,0,0}},
  {"concave_1",
   {{0,0,0,0},
    {1,0,1,0},
    {1,1,1,0},
    {0,0,0,0}},
   0,
   0,
   '%',
   {0,0,0}},
  {"concave_2",
   {{0,0,0,0},
    {1,0,0,1},
    {0,1,1,0},
    {0,0,0,0}},
   0,
   0,
   '%',
   {0,0,0}}
};

int check_collision (tetromino_t *tet) {
  int x,y;
  chtype row_buf[5];
  int num_chars;
  int i;
  for (y=0;y<+4;y++) {
    num_chars = mvinchnstr(tet->upper_left_y+y, tet->upper_left_x, row_buf, 4);
    for (x=0;x<4;x++) {
      if (tet->piece[x][y] && row_buf[x]!=' ') {
	return COLLIDE;
      }
    }
  }
  return SAFE;
}

int move_tet (tetromino_t *tet, int new_x, int new_y) {
  int old_x = tet->upper_left_x;
  int old_y = tet->upper_left_y;
  tet->upper_left_x = new_x;
  tet->upper_left_y = new_y;
  if (check_collision(tet) == COLLIDE) {
    tet->upper_left_x = old_x;
    tet->upper_left_y = old_y;
    return MOVE_FAILED;
  }  
  else {
    return MOVE_OK;
  }
}

int rotate_cw(tetromino_t *tet) {
  char temp[4][4];
  int x,y;
  tetromino_t temp_tet;
  memcpy(&temp_tet,tet,sizeof(tetromino_t));
  for (x=0;x<4;x++) {
    for (y=0;y<4;y++) {
      temp[x][y] = tet->piece[y][3-x]; 
    }
  }
  memcpy(tet->piece,&temp,sizeof(tet->piece));
  if (check_collision(tet) == COLLIDE) {
    memcpy(tet,&temp_tet,sizeof(tetromino_t));
    return MOVE_FAILED;
  }
  else {
    return MOVE_OK;
  }
}

int rotate_ccw(tetromino_t *tet) {
  char temp[4][4];
  int x,y;
  tetromino_t temp_tet;
  memcpy(&temp_tet,tet,sizeof(tetromino_t));
  for (x=0;x<4;x++) {
    for (y=0;y<4;y++) {
      temp[x][y] = tet->piece[3-y][x]; 
    }
  }
  memcpy(tet->piece,&temp,sizeof(tet->piece));
  if (check_collision(tet) == COLLIDE) {
    memcpy(tet,&temp_tet,sizeof(tetromino_t));
    return MOVE_FAILED;
  }
  else {
    return MOVE_OK;
  }
}

tetromino_t *create_tetromino (int initial_x, int initial_y) {
  int type;
  tetromino_t *tet = malloc(sizeof(tetromino_t));
  type = rand()%9;
  memcpy(tet, &tetromino_types[type], sizeof(tetromino_t));
  tet->upper_left_x = initial_x;
  tet->upper_left_y = initial_y;
  srand(time(NULL));
  int randColor = rand()%7 +1;
  tet->color[0] = randColor;
  return(tet);
}

display_tetromino(tetromino_t *tet) {
  int x,y;
  initscr();
  start_color();
  init_pair(tet->color[0],tet->color[0],tet->color[0]);
  attron(COLOR_PAIR(tet->color[0]));
  for (x=0;x<4;x++) {
    for (y=0;y<+4;y++) {
      if (tet->piece[x][y]) {
	mvprintw(tet->upper_left_y+y,tet->upper_left_x+x,"%c",tet->draw_char);
      }
    }
  }
  attroff(COLOR_PAIR(tet->color[0]));
  endwin();
  refresh();
}

undisplay_tetromino(tetromino_t *tet) {
  int x,y;
  for (x=0;x<4;x++) {
    for (y=0;y<+4;y++) {
      if (tet->piece[x][y]) {
	mvprintw(tet->upper_left_y+y,tet->upper_left_x+x," ",tet->draw_char);
      }
    }
  }
}

display_next_tetromino(int x, int y, tetromino_t *tet) {
    int i, j;
    mvprintw(y-2,x-8, "The next tetromino is:");
    initscr();
    start_color();
    init_pair(tet->color[0],tet->color[0],tet->color[0]);
    attron(COLOR_PAIR(tet->color[0]));
    for (i=0;i<4;i++) {
        for (j=0;j<+4;j++) {
            if (tet->piece[i][j]) {
                mvprintw(j+y,i+x,"%c",tet->draw_char);
            }
        }
    }
    attroff(COLOR_PAIR(tet->color[0]));
    endwin();
    refresh();
}

undisplay_next_tetromino(int x, int y, tetromino_t *tet) {
    int i, j;
    for (i=0;i<4;i++) {
        for (j=0;j<+4;j++) {
            if (tet->piece[i][j]) {
                mvprintw(j+y,i+x," ",tet->draw_char);
            }
        }
    }
}

int destroy_tetromino(tetromino_t *tet) {
  free(tet);
}




/* tetromino.c ends here */
