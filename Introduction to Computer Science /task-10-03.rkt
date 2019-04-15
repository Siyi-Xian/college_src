;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-reader.ss" "lang")((modname task-10-03) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
(define (add-2 l)
  (cond
    [(empty? l) empty]
    [else (cons (+ 2 (first l)) (add-2 (rest l)))]))
(check-expect (add-2 (list 2 3 4 )) (list 4 5 6))

(define (adder l n)
  (cond
    [(empty? l) empty]
    [else (cons (+ n (first l)) (adder (rest l) n))]))
(check-expect (adder (list 2 3 4 ) 2) (list 4 5 6))


(define (m l)
  (cond
    [(empty? l) 1]
    [(cons? l) (* (first l) (m (rest l)))]))
(check-expect (m (list 2 3 4 )) 24)


(define (operation lon n operator)
  (cond
    [(empty? lon) n]
    [(cons? lon) (operator (first lon) (operation (rest lon) n operator))]))
(check-expect (operation (list 2 3 4 ) 1 *) 24)
(check-expect (operation (list "a" "b" "c" "d") "" string-append) "abcd")