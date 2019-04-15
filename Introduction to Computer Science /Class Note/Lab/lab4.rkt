;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-reader.ss" "lang")((modname lab4) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
(require 2htdp/image)
(require 2htdp/universe)

#|

Design Recipe
=============

1. Data Definitions
2. Signature, Purpose Statement, Header
3. Examples
4. Template
5. Body
6. Testing

|#

; Write a data structure and definition for shapes. A Shape can be either a circle or a rectangle.

; A Shape is one of:
; - (make-circ PositiveNumber Color)
; - (make-rect PositiveNumber PositiveNumber Color)
(define-struct circ (radius color))
(define-struct rect (width height color))

; Template for Shape:

#;
(define (process-shape s)
  (cond [(circ? s) ... (circ-radius s) ... (process-color (circ-color s)) ...]
        [(rect? s) ... (rect-width s) ... (rect-height s) ... (process-color (rect-color s)) ...]))

; A Color is one of:
; - "red"
; - "green"
; - "blue"

#;
(define (process-color c)
  (cond [(string=? c "red")   ...]
        [(string=? c "green") ...]
        [(string=? c "blue")  ...]))

; Design a function that computes the area of a given Shape.

; shape-area : Shape -> PositiveNumber
; Computes the area of the given shape.
(define (shape-area s)
  (cond [(circ? s) (* pi (circ-radius s) (circ-radius s))]
        [(rect? s) (* (rect-width s) (rect-height s))]))

(check-within (shape-area (make-circ 10 "red"))
              (* pi 10 10) 0.0000001)

(check-expect (shape-area (make-rect 10 20 "blue"))
              200)

; Exercise 1.

; A Frosting is one of:
;  - "chocolate"
;  - "vanilla"
 
; A Dessert is one of:
; - (make-cupcake Frosting)
; - (make-pie String Number)

(define-struct cupcake (frosting))
(define-struct pie (filling slices))

; Template for Frosting:

#;
(define (process-frosting f)
  (cond [(string=? f "chocolate") ...]
        [(string=? f "vanilla")   ...]))

; Template for Dessert:

#;
(define (process-dessert d)
  (cond [(cupcake? d) ... (process-frosting (cupcake-frosting d)) ...]
        [(pie? d) ... (pie-filling d) ... (pie-slices d) ...]))

; Exercise 2.

(define apple-pie (make-pie "Apple" 8))
(define choc-cake (make-cupcake "chocolate"))
(define vanilla-cake (make-cupcake "vanilla"))

; Exercise 3.

; dessert-cal : Dessert -> PositiveNumber
; Given a dessert this function returns the number of calories in the dessert.
(define (dessert-cal d)
  (cond [(cupcake? d) (cupcake-cal (cupcake-frosting d))]
        [(pie? d) (* (pie-slices d) 175)]))

(check-expect (dessert-cal choc-cake) 150)
(check-expect (dessert-cal vanilla-cake) 125)
(check-expect (dessert-cal apple-pie) (* 8 175))

; cupcake-cal : Frosting -> PositiveNumber
; Returns the calories of a cupcake with the given frosting.
(define (cupcake-cal f)
  (cond [(string=? f "chocolate") 150]
        [(string=? f "vanilla")   125]))

(check-expect (cupcake-cal "chocolate") 150)
(check-expect (cupcake-cal "vanilla") 125)

; Exercise 4.

; A CaseOfDesserts is one of:
; - empty
; - (make-case Dessert CaseOfDesserts)
(define-struct case (pastry more-cases))

; Exercise 5.

(define example1 (make-case apple-pie empty))
(define example2 empty)
(define example3 (make-case choc-cake (make-case apple-pie (make-case vanilla-cake empty))))

; Exercise 6.

#;
(define (process-cases c)
  (cond [(empty? c) ...]
        [(case? c) ... (process-dessert (case-pastry c)) ... (process-cases (case-more-cases c)) ...]))

; Exercise 7.

; total-calories : CaseOfDesserts -> PositiveNumber
; Calculates the total number of calories for all the given desserts.
(define (total-calories c)
  (cond [(empty? c) 0]
        [(case? c) (+ (dessert-cal (case-pastry c)) (total-calories (case-more-cases c)))]))

(check-expect (total-calories empty)    0)
(check-expect (total-calories example3) (+ 150 125 (* 8 175)))

; Exercise 8

; A World is one of:
; - empty
; - (make-posn Number Number)

; Exercise 9

#;
(define (process-world w)
  (cond [(empty? w) ...]
        [(posn? w) ... (posn-x w) ... (posn-y w) ...]))

; Exercise 10

; init-world : World
(define init-world empty)

(define MTSCN (empty-scene 500 500))
(define my-circ (circle 10 "outline" "black"))

; draw-world : World -> Image
; Either returns an empty scene or draws a circle at the given coordinate on the empty scene.
(define (draw-world w)
  (cond [(empty? w) MTSCN]
        [(posn? w) (place-image my-circ (posn-x w) (posn-y w) MTSCN)]))

(check-expect (draw-world empty) MTSCN)

(check-expect (draw-world (make-posn 10 10))
              (place-image my-circ 10 10 MTSCN))

; process-mouse : World PositiveNumber PositiveNumber MouseEvent -> World
; Updates the world state by moving the circle to the new mouse click position.
(define (process-mouse w x y mouse)
  (cond [(string=? mouse "button-down") (make-posn x y)]
        [else w]))

(check-expect (process-mouse empty 10 10 "mouse-move") empty)
(check-expect (process-mouse empty 10 10 "button-down") (make-posn 10 10))

(big-bang init-world
          [to-draw  draw-world]
          [on-tick  identity]
          [on-mouse process-mouse])
