;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-reader.ss" "lang")((modname |Lab-1-Siyi Xian|) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
(require 2htdp/image)
(require 2htdp/universe)

;Exercise 1

(+ 13645313654 1321313)
(* 1354132 25246)
(- 85846838825 63873)
(/ 5646465 3213)


;Exercise 2

; purpose: a function to calculate the time
; signiture: distance -> time
; distance; time
; number, the distance of the total move

(define(distance x)
  (/ x 60)
  )

; example
; 60 -> 1
; 270 -> 4.5


; Exercise 3

; purpose: a function to caculate the distance
; signiture: time -> distance
; time; distance
; number, the time that the move takes

(define (time x)
  (ceiling (* (/ x 60) 70))
  )

; example
; 120 -> 140
; 355 -> 415


; Exercise 4

; syntax error
;(1 + 1)

; run-time error
(define (run-time-error x)
  (* x 5660)
  )
; example:
; > (run-time-error "hello")
; run-time-error: this function is not defined

; logical error
; purpose: count 10 plus 1
(* 10 1)


; Exercise 5
; purpose: a function to show an image
; signiture: input soccer -> an image of soccer ball

(define soccer "./soccer")

; example:
; > soccer
; "./soccer"


; Exercise 6
; purpose: to put an image in a scene

(place-image "./soccer" 150 100 (empty-scene 300 200))


; Exercise 7

(rectangle 10 25 "outline" "green")
(rectangle 20 50 "outline" "green")
(rectangle 30 75 "outline" "green")
(rectangle 40 100 "outline" "green")
(rectangle 50 125 "outline" "green")
(rectangle 60 150 "outline" "green")

; Exercise 8
; purpose: a function to print a rectangle
; signiture: number -> image of rectangle
; rec; number
; number, a number means the height and half of width

(define (rec x)
  (rectangle (* x 2) x "solid" "blue")
  )

; example
; > (rec 100)
; 
; > (rec 35)
; 


; Exercise 9
; purpose: a function to put a circle inside an empty scene
; signiture: radiu -> image
; rad, number
; number, a number means the radiu of the circle, a quarter of width, and a sixth of the height

(define (rad x)
  (place-image (circle x "solid" "green") 200 300 (empty-scene 400 600))
  )

; example
; > (rad 35)
; 


; Exercise 10

(animate rad)


; Exercise 11

; purpose: a function to put a rectangle inside an empty scene
; signiture: length -> image
; my-ellipse, number
; number, a number means half of the weidth and a third of height of the rectangle
(define (my-rectangle x)
  (place-image (rectangle (* 3 x) (* 2 x) "solid" "red") 200 300 (empty-scene 400 600))
  )

(animate my-rectangle)

; Exercise 12

; purpose: a function to put a ellipse inside an empty scene
; signiture: length -> image
; my-ellipse, number
; number, a number means a and c of the ellipse
(define (my-ellipse x)
  (place-image (ellipse (* 3 x) (* 2 x) "solid" "red") 200 300 (empty-scene 400 600))
  )

(animate my-ellipse)

