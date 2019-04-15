;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-lambda-reader.ss" "lang")((modname |Lab-14-Siyi Xian|) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
(require 2htdp/image)
(require 2htdp/universe)

(define WIDTH 800)
(define HEIGHT 600)

(define MTSCN (empty-scene WIDTH HEIGHT))

;Constans for the ball
(define RADIUS 10)

(define BALL (circle RADIUS "solid" "red"))

(define LEFT RADIUS)
(define RIGHT (- WIDTH RADIUS))

(define TOP RADIUS)
(define BOTTOM (- HEIGHT RADIUS))

;Constants for the paddles
(define PWIDTH 20)
(define PHEIGHT 50)

(define PADDLE (rectangle PWIDTH PHEIGHT "solid" "blue"))

(define PADDLE1 (* 1.5 PWIDTH))
(define PADDLE2 (- WIDTH (* 1.5 PWIDTH)))

(define PHALF (/ PHEIGHT 2))
(define PDIST (+ (* 2 PWIDTH) RADIUS))

(define PLEFT PDIST)
(define PRIGHT (- WIDTH PDIST))

(define PTOP PHALF)
(define PBOTTOM (- HEIGHT PHALF))

(define VELOCITY 5)
(define PVELOCITY (* 2 VELOCITY))

; E1
; A world is a (make-w Number Number Number Number Number Number)
; - p1 is the y-coordinate of paddle1
; - p2 is the y-coordinate of paddle2
; - bx is the x-coordinate of the ball
; - by is the y-coordinate of the ball
; - dx is the x-velocity of the ball
; - dy is the y-velocity of the ball
(define-struct w (p1 p2 bx by dx dy))

(define w1 (make-w (/ HEIGHT 2) (/ HEIGHT 2) (/ WIDTH 2) (/ HEIGHT 2) VELOCITY VELOCITY))
(define w2 (make-w PTOP         (/ HEIGHT 2) (/ WIDTH 2) (/ HEIGHT 2) VELOCITY VELOCITY))
(define w3 (make-w PBOTTOM      (/ HEIGHT 2) (/ WIDTH 2) (/ HEIGHT 2) VELOCITY VELOCITY))
(define w4 (make-w (/ HEIGHT 2) PTOP         (/ WIDTH 2) (/ HEIGHT 2) VELOCITY VELOCITY))
(define w5 (make-w (/ HEIGHT 2) PBOTTOM (/ WIDTH 2) (/ HEIGHT 2) VELOCITY VELOCITY))

; E2
; draw-world: World -> Image
; Draws the given world of the ping pong game
(define (draw-world w)
  (place-image BALL (w-bx w) (w-by w)
               (place-image PADDLE PADDLE1 (w-p1 w)
                            (place-image PADDLE PADDLE2 (w-p2 w) MTSCN))))
(check-expect (draw-world w1)
              (place-image BALL (w-bx w1) (w-by w1)
               (place-image PADDLE PADDLE1 (w-p1 w1)
                            (place-image PADDLE PADDLE2 (w-p2 w1) MTSCN))))

; E3
; move-paddle: World Keyevent -> World
; Moves the paddle in the direction of the key pressed while performing collision detection
(define (move-paddle w key)
  (cond[(string=? key "w") (if (<= (w-p1 w) PTOP) w
                               (make-w (- (w-p1 w1) VELOCITY) (w-p2 w1) (w-bx w1) (w-by w1) (w-dx w1) (w-dy w1)))]
       [(string=? key "s") (if (<= (w-p1 w) PBOTTOM) w
                               (make-w (+ (w-p1 w1) VELOCITY) (w-p2 w1) (w-bx w1) (w-by w1) (w-dx w1) (w-dy w1)))]
       [(string=? key "up") (if (<= (w-p1 w) PTOP) w
                               (make-w (w-p1 w1) (- (w-p2 w1) VELOCITY) (w-bx w1) (w-by w1) (w-dx w1) (w-dy w1)))]
       [(string=? key "down")(if (<= (w-p1 w) PBOTTOM) w
                               (make-w (w-p1 w1) (+ (w-p2 w1) VELOCITY) (w-bx w1) (w-by w1) (w-dx w1) (w-dy w1)))]
       [else w]))

(check-expect (move-paddle w1 "w") (make-w (- (w-p1 w1) VELOCITY) (w-p2 w1) (w-bx w1) (w-by w1) (w-dx w1) (w-dy w1)))
(check-expect (move-paddle w1 "s") (make-w (+ (w-p1 w1) VELOCITY) (w-p2 w1) (w-bx w1) (w-by w1) (w-dx w1) (w-dy w1)))
(check-expect (move-paddle w1 "up") (make-w (w-p1 w1) (- (w-p2 w1) VELOCITY) (w-bx w1) (w-by w1) (w-dx w1) (w-dy w1)))
(check-expect (move-paddle w1 "down") (make-w (w-p1 w1) (- (w-p2 w1) VELOCITY) (w-bx w1) (w-by w1) (w-dx w1) (w-dy w1)))

(check-expect (move-paddle w1 "a") w1)
(check-expect (move-paddle w1 "w") w2)
(check-expect (move-paddle w1 "s") w3)
(check-expect (move-paddle w1 "up") w4)
(check-expect (move-paddle w1 "down") w5)


; E4
; move-ball: World -> World
; Moves the ball by the given velocity while performing collision detection
(define (move-ball w)
  (cond[(or (<= (w-bx w) LEFT) (>= (w-bx w) RIGHT))
        (make-w (w-p1 w) (w-p2 w) (w-bx w) (w-by w) 0 0)]
       [(or (<= (w-by w) TOP) (>= (w-by w) BOTTOM))
        (make-w (w-p1 w) (w-p2 w) (+ (w-bx w) (w-dx w)) (- (w-by w) VELOCITY) (w-dx w) (- (w-dy w)))]
       [(or (and (<= (w-bx w) PLEFT) (collision? (w-p1 w) (w-by w)))
            (and (>= (w-bx w) PRIGHT) (collision? (w-p2 w) (w-by w))))
        (make-w (w-p1 w) (w-p2 w) (- (w-bx w) (w-dx w)) (+ (w-by w) VELOCITY) (- (w-dx w)) (w-dy w))]
       [else (make-w (w-p1 w) (w-p2 w)
                     (+ (w-bx w) (w-dx w))
                     (+ (w-by w) (w-dy w))
                     (w-dx w) (w-dy w))]))

; collision?: Number Number -> Boolean
; checks whether the y-coordinate of the ball is within the range of the paddle
(define (collision? p b)
  (<= (abs (- p b) PHALF)))

#;
(big-bang w1
          [to-draw draw-world]
          [on-key move-paddle]
          [on-tick move-ball])