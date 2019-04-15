# Final Project

## Discription
Main goal is to design a Snake game ([Reference](https://en.wikipedia.org/wiki/Snake_(video_game_genre))). Inside this game, a snake will start at the center of the screen. 5 food will appear inside canvas at random place. Player need to use nunchuk to control the movement of snake. If remain nunchuk unmove, snake will moving towards the position last state nunchuk pointed to. For movement of snake, the speed will be constant. Score will be the amount of foods which snake has eaten. When snake toch the border or itself, game will end.

### Features
* Each time snake eat a food, audio will play "bin" and add score.
* Each food that has been eaten, a new food at random location will pop up.
* User can push button and 'z' or 'c' on nunchuk to puase and resume the game.
* After end the game, screen will show up best score this machine has. If player has a better score, this will replace the best score storage on SD card.

## Breakdown
* Data structure of snake. Structure of snake (Position, Direction, and etc.), Position of food, and score now will be included. (snake.h)
* Main logic of game. (main.c)
* Display API. (display.c/h)
* Nunchuk moving function. (main.c)
* Button and Nunchuk interuput handler.
* SD card O/I. (main.c)
* Audio playing. (main.c)

## Devices
* LCD
* Audio
* SD
* Nunchuk
* Button