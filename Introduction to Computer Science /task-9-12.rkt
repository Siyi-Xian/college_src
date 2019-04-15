;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-reader.ss" "lang")((modname task-9-12) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
(require 2htdp/image)
(require 2htdp/universe)

(define (number->square s)
  (square s "solid" "red"))

(big-bang 100
  [to-draw number->square]
  [on-tick sub1]
  [stop-when zero?])

; big bang to draw two circles
; a ws is a posn posn
(define-struct ws (posn1 posn2))
(make-ws (make-posn 4 6) (make-posn 9 17))

(define canvas (empty-scene 400 400))

; draw the world sate on the canvas
; signature:
; ws -> image
(define (ws-draw ws)
  (draw-circle-at (ws-posn2 ws) (draw-circle-at (ws-posn1 ws) canvas))
  )

; draws a circle on another image
; signature
; pos: posn
; im: image
; -> image
(define (draw-circle-at pos im) ...)