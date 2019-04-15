;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-reader.ss" "lang")((modname |Assignment-2-Siyi Xian|) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
(require 2htdp/image)
(require 2htdp/universe)


; Define a function to get the initial vertical velocity
; number -> number

(define init-y-vel
  (* 1 (sqrt 2) 0.5))


; Define a function to get the initial horizontal velocity
; number -> number

(define init-x-vel
  (* 1 (sqrt 2) 0.5))


; Define a function which calculates the vertical 
; position as a function of time t.
; t: number, time
; number -> number

(define (vertical-postion t)
  (- (* init-y-vel t) (* 0.5 0.002 t t)))



; Define a function which calculates the horizontal 
; position as a function of time t.
; t: number, time
; number -> number

(define (horizontal-postion t)
  (* init-x-vel t))


; Define the empty scene

(define scene-height 200)
(define scene-width 500)
(define background
  (empty-scene scene-width scene-height))


; Define the position of the small image
; number -> number

(define (h->x x) x)
(define (h->y y) (- scene-height y))


; Define a function to draw a image which
; has already been given the position
; x: number, the position on the x-ray
; y: number, the position on the y-ray
; number -> image

(define (draw-a-picture x y)
  (place-image
   (circle 15 "solid" "green")
   (h->x x)
   (h->y y)
   background))


; Define a function to draw an image
; which the input is time
; t: number, time
; number -> image

(define (draw-an-image t)
  (draw-a-picture
  (horizontal-postion t)
  (vertical-postion t)))


; Defien a function to draw an animate picture
; number -> number which the time has been running

(define moving-racket
  (animate draw-an-image))
