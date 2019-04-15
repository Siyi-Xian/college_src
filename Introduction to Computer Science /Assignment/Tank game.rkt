;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-reader.ss" "lang")((modname |Tank game|) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
(require 2htdp/image)
(require 2htdp/universe)

; ufo is posn that contains x-pos and y-pos
; pos: posn
(define-struct ufo (pos))

; A Tank is a structure
; loc, vel: number, number
(define-struct tank (loc vel))

; aim is a structure that contains ufo and tank
; ufo, tank: posn, tank
(define-struct aim (ufo tank))

; missile is posn that contains x-pos and y-pos
; pos: posn
(define-struct missile (pos))

; fired is a structure that contains ufo, tank, and missile
; ufo, tank, missile: posn, tank, image
(define-struct fired (ufo tank missile))

; Tank Image -> Image 
; adds t to the given image im
(define (tank-render t im) im)
 
; UFO Image -> Image 
; adds u to the given image im
(define (ufo-render u im) im)

; Missile Image -> Image 
; adds m to the given image im
(define (missile-render m im) im)

; canvas is the function that define the background
(define canvas (empty-scene 200 200))

; SIGS -> Image
; renders the given game state on top of BACKGROUND 
(define (si-render s)
  (cond
    ((aim? s)
     (tank-render (aim-tank s)
                  (ufo-render (aim-ufo s) canvas)))
    ((fired? s)
     (tank-render
       (fired-tank s)
       (ufo-render (fired-ufo s)
                   (missile-render (fired-missile s)
                                   canvas))))))

; s is structure of fired
(define s (make-fired (overlay (circle 10 "solid" "yellow")
                               (rectangle 40 10 "solid" "yellow"))
                      (rectangle 40 10 "solid" "blue")
                      (triangle 10 "solid" "red")))

; test-one the the function that do tank-render
; fired -> image
(define (test-one s) (tank-render
                      (fired-tank s)
                      (ufo-render (fired-ufo s)
                                  (missile-render (fired-missile s)
                                                  canvas))))
; test-two the the function that do ufo-render
; fired -> image
(define (test-two s) (ufo-render
                      (fired-ufo s)
                      (tank-render (fired-tank s)
                                   (missile-render (fired-missile s)
                                                   canvas))))

(check-expect (test-one s) (test-two s))