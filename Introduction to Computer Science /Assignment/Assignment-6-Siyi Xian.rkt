;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-reader.ss" "lang")((modname |Assignment-6-Siyi Xian|) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
(require 2htdp/image)
(require 2htdp/universe)

; canvas is the background of the game
; -> image
(define canvas (empty-scene 400 400))

; Exercise 1

; coordinates is a structure that contains the position of player and mouse
; player mouse: Posn Posn
(define-struct coordinates (player mouse))

; Exercise 2

; player is function to draw the image of player
; -> image
(define draw-player
  (circle 5 "solid" "green"))
(check-expect draw-player (circle 5 "solid" "green"))

; draw-player is a function to draw player on the canvas
; Coordinates -> image
(define (draw-play pos)
  (place-image draw-player
               (posn-x (coordinates-player pos))
               (posn-y (coordinates-player pos))
               canvas))
(check-expect (draw-play (make-coordinates (make-posn 177 201) (make-posn 200 200)))
              (place-image (circle 5 "solid" "green")
                           177 201
                           canvas))

(big-bang (make-coordinates (make-posn 200 200) (make-posn 200 200))
          [to-draw draw-play])

; Exercise 3

; mouse is a event handler of on-mouse
; mouse -> coordinates
(define (player-mouse pos x y me)
  (make-coordinates (coordinates-player pos) (make-posn x y)))

(big-bang (make-coordinates (make-posn 200 200) (make-posn 200 200))
          [to-draw draw-play]
          [on-mouse player-mouse])

; Exercise 4

; player-move is a function to let the player move towards the mouse
; Coordinates -> Coordinates
(define (player-move pos)
  (make-coordinates (make-posn (cond
                                 [(< (posn-x (coordinates-mouse pos))
                                     (posn-x (coordinates-player pos)))
                                  (- (posn-x (coordinates-player pos)) 1)]
                                 [(= (posn-x (coordinates-mouse pos))
                                     (posn-x (coordinates-player pos)))
                                  (posn-x (coordinates-player pos))]
                                 [(> (posn-x (coordinates-mouse pos))
                                     (posn-x (coordinates-player pos)))
                                  (+ (posn-x (coordinates-player pos)) 1)])
                               (cond
                                 [(< (posn-y (coordinates-mouse pos))
                                     (posn-y (coordinates-player pos)))
                                  (- (posn-y (coordinates-player pos)) 1)]
                                 [(= (posn-y (coordinates-mouse pos))
                                     (posn-y (coordinates-player pos)))
                                  (posn-y (coordinates-player pos))]
                                 [(> (posn-y (coordinates-mouse pos))
                                     (posn-y (coordinates-player pos)))
                                  (+ (posn-y (coordinates-player pos)) 1)]))
                    (coordinates-mouse pos)))
(check-expect (player-move (make-coordinates (make-posn 0 0) (make-posn 10 10)))
              (make-coordinates (make-posn 1 1) (make-posn 10 10)))


(big-bang (make-coordinates (make-posn 200 200) (make-posn 200 200))
          [to-draw draw-play]
          [on-mouse player-mouse]
          [on-tick player-move])

; Exercise 5

; A SetOfZombies is one of
; - null
; - (make-zombies Posn SetOfZombies)

; Zombies is a structure to store a set of zombies
; this next: Posn SetOfZombies
(define-struct zombies (this next))


; Exercise 6

; get-zombie is function to get a zombie on the random position
; Number -> zombies
(define (get-zombie n)
  (cond
    [(zero? n) null]
    [else (make-zombies (make-posn (random 400) (random 400)) (get-zombie (- n 1)))]))

; all-zombies is a function to get all zombies we meed in this game
; For this game, we will set 5 zombies
; -> SetOfZombies
(define all-zombies (get-zombie 5))


; Exercise 7

; game is a structure that contains the position of player, mouse, and zombie
; player mouse zombie: Posn Posn Posn
(define-struct game (player mouse zombie))


; Exercise 8

; draw-zombie is a function to draw the image of a zombie
; -> image
(define draw-zombie
  (circle 5 "solid" "red"))
(check-expect draw-zombie
              (circle 5 "solid" "red"))

; draw-zombies is a function to draw a set of zombies at given positions
; a new zombies will show up each 2 seconds
; SetOfZombies -> image
(define (draw-zombies z)
  (cond
    [(null? z) canvas]
    [else (place-image draw-zombie
                       (posn-x (zombies-this z))
                       (posn-y (zombies-this z))
                       (draw-zombies (zombies-next z)))]))
(check-expect (draw-zombies (make-zombies (make-posn 200 200) null))
              (place-image (circle 5 "solid" "red")
                           200 200
                           canvas))

; Exercise 9

; draw is a new function that draw all zombies and player on the canvas
; game -> image
(define (draw g)
  (place-image draw-player
               (posn-x (game-player g))
               (posn-y (game-player g))
               (draw-zombies (game-zombie g))))

(big-bang (make-game (make-posn 200 200) (make-posn 200 200)
                     all-zombies)
          [to-draw draw])

; mouse is a new event handler of on-mouse
; mouse -> game
(define (mouse g x y me)
  (make-game (game-player g) (make-posn x y) (game-zombie g)))


; Exercise 10

; zombie-move is a function to move zombies towards given position
; SetOfZombies Posn -> SetOfZombies
(define (zombie-move z pos)
  (cond
    [(null? z) null]
    [else (make-zombies (make-posn (cond
                                     [(< (posn-x pos)
                                         (posn-x (zombies-this z)))
                                      (- (posn-x (zombies-this z)) 1)]
                                     [(= (posn-x pos)
                                         (posn-x (zombies-this z)))
                                      (posn-x (zombies-this z))]
                                     [(> (posn-x pos)
                                         (posn-x (zombies-this z)))
                                      (+ (posn-x (zombies-this z)) 1)])
                                   (cond
                                     [(< (posn-y pos)
                                         (posn-y (zombies-this z)))
                                      (- (posn-y (zombies-this z)) 1)]
                                     [(= (posn-y pos)
                                         (posn-y (zombies-this z)))
                                      (posn-y (zombies-this z))]
                                     [(> (posn-y pos)
                                         (posn-y (zombies-this z)))
                                      (+ (posn-y (zombies-this z)) 1)]))
                        (zombie-move (zombies-next z) pos))]))
(check-expect (zombie-move (make-zombies (make-posn 200 200) null) (make-posn 0 0))
              (make-zombies (make-posn 199 199) null))

(big-bang (make-game (make-posn 200 200) (make-posn 200 200)
                     all-zombies)
          [to-draw draw]
          [on-mouse mouse])


; Exercise 11

; move is a new event handler for on-tick
; game -> game
(define (move g)
  (make-game (make-posn (cond
               [(< (posn-x (game-mouse g)) (posn-x (game-player g))) (- (posn-x (game-player g)) 1)]
               [(= (posn-x (game-mouse g)) (posn-x (game-player g))) (posn-x (game-player g))]
               [(> (posn-x (game-mouse g)) (posn-x (game-player g))) (+ (posn-x (game-player g)) 1)])
             (cond
               [(< (posn-y (game-mouse g)) (posn-y (game-player g))) (- (posn-y (game-player g)) 1)]
               [(= (posn-y (game-mouse g)) (posn-y (game-player g))) (posn-y (game-player g))]
               [(> (posn-y (game-mouse g)) (posn-y (game-player g))) (+ (posn-y (game-player g)) 1)]))
             (game-mouse g)
             (zombie-move (game-zombie g) (game-player g))))

(big-bang (make-game (make-posn 200 200) (make-posn 200 200)
                     all-zombies)
          [to-draw draw]
          [on-mouse mouse]
          [on-tick move])

; Exercise 12

; distance is a function to account the distance between to point
; a b: Posn Posn
(define (distance a b)
  (sqrt (+ (* (- (posn-x a)
                 (posn-x b))
              (- (posn-x a)
                 (posn-x b)))
           (* (- (posn-y a)
                 (posn-y b))
              (- (posn-y a)
                 (posn-y b))))))
(check-expect (distance (make-posn 0 0) (make-posn 3 4)) 5)

; finish is a function to determine when should the big-bang stop
; This function only run zombies which were shown up;
; game -> Blooean
(define (finish g)
  (cond
    [(null? (game-zombie g)) #false]
    [else
     (if (< 10 (distance (zombies-this (game-zombie g)) (game-player g)))
         (finish (make-game (game-player g) (game-mouse g) (zombies-next (game-zombie g))))
         #true)]))
(check-expect (finish (make-game (make-posn 10 10)
                                 (make-posn 0 0)
                                 (make-zombies (make-posn 10 10) null)))
              #true)


; The final draft of big-bang for the game
(big-bang (make-game (make-posn 200 200) (make-posn 200 200)
                     all-zombies)
          [to-draw draw]
          [on-mouse mouse]
          [on-tick move]
          [stop-when finish])