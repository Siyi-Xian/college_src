;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-reader.ss" "lang")((modname |Lab-5-Siyi Xian|) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
(require 2htdp/image)
(require 2htdp/universe)

; canvas is a image that shows the backgroud
; -> image
(define canvas (empty-scene 400 400))

; draw-posn is a function to draw a posn point
; -> image
(define draw-posn (circle 3 "solid" "green"))

; Exercise 1

; A PosnList is one of:
;  | empty
;  | (cons (make-posn x:number y:number)  PosnList )
 
; A Posn is
;  (make-posn x:number y:number)
; Note: the posn structure is defined in Beginning Student,
; and looks like:
; (define-struct posn (x y))

; Template
(define (posnlist-process pl)
  (cond
    [(empty? pl) ...]
    [(cons? pl) ( ... (posn-x (first pl))
                  ... (posn-y (first pl))
                  ... (posnlist-process (rest pl)) ...)]))


; Exercise 2

; Examples
(define posnlist1
  (cons (make-posn 41 27) empty))
(define posnlist2
  (cons (make-posn 23 32) (cons (make-posn 33 54) empty)))
(define posnlist3
  (cons (make-posn -46 95)
        (cons (make-posn 115 -176)
              (cons (make-posn 64 77) empty))))


; Exercise 3

; many-positive is a function to check if all the position of pl is positive
; PosnList -> Blooean
(define (many-positive pl)
  (cond
    [(empty? pl) #true]
    [(cons? pl) (and (> (posn-x (first pl)) 0)
                           (many-positive (rest pl)))]))

(check-expect (many-positive posnlist2) #true)
(check-expect (many-positive posnlist3) #false)


; Exercise 4

; draw is a function to draw all points in PosnList on the canvas
; PosnList -> image
(define (draw pl)
  (cond
    [(empty? pl) canvas]
    [(cons? pl) (place-image draw-posn
                             (posn-x (first pl))
                             (posn-y (first pl))
                             (draw (rest pl)))]))

(check-expect (draw posnlist1) (place-image draw-posn 41 27 canvas))


; Exercise 5

; move is a function to add 1 on each y
; PosnList -> PosnList
(define (move pl)
  (cond
    [(empty? pl) empty]
    [(cons? pl) (cons (make-posn (posn-x (first pl))
                                 (+ 1 (posn-y (first pl))))
                      (move (rest pl)))]))

(check-expect (move posnlist1) (cons (make-posn 41 28) empty))


; Exercise 6

; add is a function that can add a random posn to posnlist
; PosnList KeyEvent -> PosnList
(define (add posnlist keyevent)
  (cons (make-posn (random 400) (random 400)) posnlist))

; Exercise 7

(big-bang
     empty
     [to-draw draw]
     [on-key add]
     [on-tick move])