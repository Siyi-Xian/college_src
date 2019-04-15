;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-reader.ss" "lang")((modname lab4) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
(require 2htdp/image)
(require 2htdp/universe)

; cupcake is a structure
; A Frosting is one of:
;  - "chocolate"
;  - "vanilla"
(define-struct cupcake (frosting))

; pie is a structure
(define-struct pie (filling slices))

(define (pocess-frodting f)
  (cond [(string=? f "chocolate")...]
        []))

(define (process-edssert d)
  (cond [(cupcake? d) ... (process-frosting (cupcake-frosting))]
        ))
; Exercise 4
(define (how-many-calories dessert)
  (cond [cupcake? dessert
                  (cond [(string=? (cupcake-frosting dessert) "chocolate") 150]
                        [(string=? (cupcake-frosting dessert) "vanilla") 125])]
        [pie? dessert (* (pie-slices dessert) 175)]))

; Exercise 5
; A dessert is one of:
; - (make-cell dessert caseofdesssert)
; empty

(define-struct cell (dessert rest))

(define (case cell cal)
  (if (empty? cell)
      cal
      (case (cell-rest cell) (+ cal (how-many-calories (cell-dessert cell)))))