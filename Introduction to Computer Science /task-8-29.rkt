;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-reader.ss" "lang")((modname task-8-29) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
; Using the check-expect function to test your functions
; has built in editor support.

(define (my-add a b) (+ a b))

; notice its first black

(check-expect (my-add 1 1) 2)
(check-expect (my-add 2 2) 4)

; what if it fails?

; (check-expect (my-add 1 1) 3)

; (define (only-positive-numbers x)
;   (if (>= x 0) x (error "Error, only postive numbers are accepted")))
; (check-expect (only-positive-numbers 5) 5)
; Lets test is:
; (+ 5 (only-positive-numbers -1))


(require 2htdp/image)
 
(require 2htdp/universe)

; Define our canvas size, and canvas, these are our data elements.

(define scene-height 160)
(define scene-width 192)
(define canvas (empty-scene scene-width scene-height))

(define my-circle (circle 5 "solid" "red"))

; Helper functions to convert between hight and the distance from top coordinate
(define (h->y h) (- scene-height h))
(define (y->h y) (- scene-height y))

; rotate an image by angle and draw it on top of our canvas at a
; given positon.
;
; signature:
; draw-rotated-image-at:symbol
; im:image, an image to draw
; angle:number, the angle to rotate the image
; x:number, offset of location to place image
; h:number, height of location to place image

(define (draw-rotated-image-at im angle x h)
  (place-image (rotate angle im)
             x (h->y h)
             canvas))

; Define a function that draws a sliding circle as a function of time
; t:number, the hight as a function of time
; -> image

(define (draw-sliding-circle t)
    (place-image (circle 20 "solid" "red")
             (modulo t scene-width) (h->y 100)
            canvas))


; Define a function which inputs an x value and a height value
; returns an image with a circle drawn on my scene at the given x and height

(define (draw-circle x h)
  (place-image
   (circle 5 "solid" "red") x (h->y h) canvas))

; A function which accepts an image, an x and a height coordinate, and draws that image at the specified height and x.

(define (draw-image-at image x h)
  (place-image image x (h->y h)
               canvas))

; Create a scene where a circle starts at the left and exists at the right, but is placed at a constant height of 100

(define (draw-sliding-scene t)
  (draw-image-at (circle 20 "solid" "red") (* t 10.0) 100))

; Draw a rotating rectangle

(define (draw-rotating-scene t)
  (draw-image-at (rotate t (rectangle 100 5 "solid" "red")) 100 100))


; square brackets are only to make more clear lets you select from a range of conditions
; ConditionExpression the expression that is checked
; ResultExpression what evaluates if that expression is true the last item is the else clause

(define (my-range1 x)
  (cond
    [(<= x 0) "x is less than 0"]
    [(< 0 x 5) "x is between 0 and 5"]
    [(>= x 5) "x is greater or equal to than 5"]))
 
(define (my-range2 x)
  (cond
    [(<= x 0) "x is less than 0"]
    [(< 0 x 5) "x is between 0 and 5"]
    [else "x is greater than 5"]))

; Example, how much money you win for each place:

; The medal you win for a certain score, each score is an *interval*
(define (medal s)
  (cond
    [(<= 0 s 10)              "bronze"]
    [(and (< 10 s) (<= s 20)) "silver"]
    [(< 20 s)                 "gold"]))


; how much money should they get for a medal? this is an *enumeration*, a finite set of items

(define (reward medal)
  (cond
    [(string=? medal "gold")   100]
    [(string=? medal "silver") 75]
    [else                      50]))
