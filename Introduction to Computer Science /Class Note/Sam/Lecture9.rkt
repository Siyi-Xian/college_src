;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-reader.ss" "lang")((modname Lecture9) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
(require 2htdp/image)
(require 2htdp/universe)

(map add1 (list 1 2 3))

;; add2: Number -> Number
(define (add2 x) (+ x 2))

;; add3: Number -> Number
(define (add3 x) (+ x 3))

;; add2-all : [ListOf Number] -> [ListOf Number]
;; add 2 to every element
(define (add2-all lon)
  (map add2 lon))

;; add3-all : [ListOf Number] -> [ListOf Number]
;; add 3 to every element
(define (add3-all lon)
  (map add3 lon))

;; add-all: [ListOf Number]  Number -> [ListOf Number]
;; add value to every number
(define (add-all lon n)
  (local [;; addn : Number Number -> Number
          (define (addn x)  (+ n x))]
    (map addn lon)))
(check-expect (add-all (list 1 2 3) 5) (list 6 7 8))

;; circles: [ListOf Number] -> Image
;; draw circles horizontally
(define (circles lon)
  (cond[(empty? lon) (square 0 "solid" "white")]
       [else (beside (circle (first lon) "solid" "red")
                     (circles (rest lon)))]))

;; circles: [ListOf Number] -> Image
;; draw circles horizontally
(define (circlesv2 lon)
  (cond[(empty? lon) (square 0 "solid" "white")]
       [else
        (local [;; first-circle : Image
                (define first-circle (circle (first lon) "solid" "red"))
                ;; other-circles : Image
                (define other-circles (circlesv2 (rest lon)))]
          (beside first-circle other-circles))]))


;; min-list: [ListOf Number] -> Number
;; find the minimum element, if empty produce 1000000000
(define (min-list lon)
  (cond[(empty? lon) 1000000000]
       [else (cond [(< (first lon)
                       (min-list (rest lon)))
                    (first lon)]
                   [else (min-list (rest lon))])]))

;; min-list: [ListOf Number] -> Number
;; find the minimum element, if empty produce 1000000000
(define (min-list lon)
  (cond[(empty? lon) 1000000000]
       [else
        (local [;; min-rest : Number
                (define min-rest (min-list (rest lon)))
                ;; fst : Number
                (define fst (first lon))]
          (cond[(< fst min-rest)
                fst]
               [else min-rest]))]))

;; A Natural is one of:
;; - 0
;; - (add1 Natural)

(define (process-natural n)
  (cond[(zero? n)...]
       [else (process-natural (sub1 n))...]))

;; build-list : [X] Natural [Natural -> X] -> [ListOf X]
(define (my-build-list n f)
  (cond[(zero? n)]
       [else
        (cons (f (sub1 n)) (my-build-list (sub1 n) f))]))




