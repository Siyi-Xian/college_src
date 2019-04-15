;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-reader.ss" "lang")((modname task-9-14) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
(require 2htdp/image)
(require 2htdp/universe)

(define-struct tank (x dx))
(define-struct fired (ufo tank missile))

(define tank-image
  (overlay (circle 10 "solid" "yellow")
           (rectangle 40 10 "solid" "yellow")))
(define ufo-image
  (rectangle 40 10 "solid" "blue"))
(define missile-image
  (triangle 10 "solid" "red"))

(define canvas (empty-scene 200 200))

(define (tank-render t im)
  (place-image tank-image
               (tank-x t) (- 200 (tank-dx t))
               im))

(define (ufo-render u im)
  (place-image ufo-image
               (posn-x u) (- 200 (posn-y u))
               im))

(define (missile-render m im)
  (place-image missile-image
               (posn-x m) (- 200 (posn-y m))
               im))

(define s (make-fired (make-posn 45 75) (make-tank 115 20) (make-posn 115 90)))

(define (test-one s) (tank-render
                      (fired-tank s)
                      (ufo-render (fired-ufo s)
                                  (missile-render (fired-missile s)
                                                  canvas))))

(define (test-two s) (ufo-render
                      (fired-ufo s)
                      (tank-render (fired-tank s)
                                   (missile-render (fired-missile s)
                                                   canvas))))

(check-expect (test-one s) (test-two s))

