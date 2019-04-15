;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-reader.ss" "lang")((modname task-9-7-2) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
(require 2htdp/image)
(require 2htdp/universe)

(define-struct solid-doll ())
(define-struct hollow-doll (contents))
(make-hollow-doll (make-solid-doll))

(define (num-dolls md)
  (cond
    [(hollow-doll? md) (+ (num-dolls (hollow-doll-contents md)) 1)]
    [(solid-doll? md) 1]
    )
  )