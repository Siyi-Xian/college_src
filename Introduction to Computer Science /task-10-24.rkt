;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-reader.ss" "lang")((modname task-10-24) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
; split : [ListOf Number] Number -> [ListOf Number]
; split the list in the middle, return the first half
(define (take l n)
  (cond [(empty? l) empty]
        [(>= 0 n) empty]
        [else (cons (first l)
                    (take (rest l) (sub1 n)))]))
(check-expect (take empty 0) empty)
(check-expect (take (list 1 2) 0) empty)
(check-expect (take (list 1 2 3 4) 2) (list 1 2))
(check-expect (take (list 1 2 3) 2) (list 1 2))


; drop : [ListOf Number] Number -> [ListOf Number]
; split the list in the middle, return the second half
(define (drop l n)
  (cond [(empty? l) empty]
        [(>= 0 n) l]
        [else (drop (rest l) (sub1 n))]))
(check-expect (drop empty 0) empty)
(check-expect (drop (list 1 2) 0) (list 1 2))
(check-expect (drop (list 1 2 3 4) 2) (list 3 4))
(check-expect (drop (list 1 2 3) 2) (list 3))


; first-half : [ListOf Number] -> [ListOf Number]
(define (first-half l)
  (take l (/ (length l) 2)))


; mergesort : [ListOf Number] -> [ListOf Number]
; sort a list by spliting in the middle
; Termination argument : take and drop always make the input
; smaller if the list is at least length 2
(define (mergesort l)
  (cond [(empty? l) empty]
        [(= 1 (length l)) l]
        [else (merge (mergesort (take l (/ (length l) 2)))
                     (mergesort (drop l (/ (length l) 2))))]))
(check-expect (mergesort empty) empty)
(check-expect (mergesort (list 1 2 3)) (list 1 2 3))
(check-expect (mergesort (list 3 2 1)) (list 1 2 3))


; merge : [ListOf Number] [ListOf Number] -> [ListOf Number]
; combine two sorted lists into cons
(define (merge l1 l2)
  (cond [(empty? l1) l2]
        [(empty? l2) l1]
        [else (cond [(< (first l1) (first l2))
                     (cons (first l1)
                           (merge (rest l1) l2))]
                    [else (cons (first l2)
                                (merge l1 (rest l2)))])]))
(check-expect (merge empty empty) empty)
(check-expect (merge (list 1 2 3) empty) (list 1 2 3))
(check-expect (merge (list 1 2 5) (list 0 4 7)) (list 0 1 2 4 5 7))


(require 2htdp/image)

; sierpinski : Number -> Image
; draw the sierpinski triangle
; Termination argument : dividing by 2 repeatedly eventualli gets less than 10
(define (sierpinski n)
  (cond [(< n 10) (triangle n "outline" "red")]
        [else (above (sierpinski (/ n 2))
                     (beside (sierpinski (/ n 2))
                             (sierpinski (/ n 2))))]))

(check-expect (sierpinski 1)
              (triangle 1 "outline" "red"))