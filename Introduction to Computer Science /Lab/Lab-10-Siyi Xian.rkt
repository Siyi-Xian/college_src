;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-reader.ss" "lang")((modname |Lab-10-Siyi Xian|) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
; Exercise 1

; Number -> [ListOf Number]
; creat n numbers randomly
(define (create-list n)
  (cond
    [(zero? n) empty]
    [else (cons (random 10) (create-list (sub1 n)))]))
(check-random (create-list 0) empty)
(check-random (create-list 5) (list (random 10)
                                    (random 10)
                                    (random 10)
                                    (random 10)
                                    (random 10)))


; Exercise 2

; Number [ListOf [ListOf Number]]
; insert number to the given list
(define (insert n l)
  (cond
    [(empty? l) (list (list n))]
    [(> 3 (length (first l))) (cons (cons n (first l)) (rest l))]
    [else (cons (list n) l)]))
(check-expect (insert 3 empty) (list (list 3)))
(check-expect (insert 2 (list (list 3))) (list (list 2 3)))
(check-expect (insert 5 '((2 3))) (list (list 5 2 3)))
(check-expect (insert 6 '((5 2 3))) (list (list 6) (list 5 2 3)))
(check-expect (insert 1 (insert 2 (insert 3 (insert 4 (insert 5 empty))))) (list (list 1 2) (list 3 4 5)))


; Exercise 3

; [ListOf Number] -> [ListOf [ListOf Number]]
; return the numbers which three of them are in a group
(define (bundle l)
  (foldr insert empty l))
(check-expect (bundle empty) empty)
(check-expect (bundle '(1)) (list (list 1)))
(check-expect (bundle '(4 2)) (list (list 4 2)))
(check-expect (bundle '(3 5 2)) (list (list 3 5 2)))
(check-expect (bundle '(-1 7 5 6)) (list (list -1) (list 7 5 6)))
(check-expect (bundle '(3 2 1 4 6)) (list (list 3 2) (list 1 4 6)))
(check-random (bundle (create-list 11)) (list (list (random 10) (random 10))
                                              (list (random 10) (random 10) (random 10))
                                              (list (random 10) (random 10) (random 10))
                                              (list (random 10) (random 10) (random 10))))
(check-random (bundle (create-list 17)) (list (list (random 10) (random 10))
                                              (list (random 10) (random 10) (random 10))
                                              (list (random 10) (random 10) (random 10))
                                              (list (random 10) (random 10) (random 10))
                                              (list (random 10) (random 10) (random 10))
                                              (list (random 10) (random 10) (random 10))))


; Exercise 4

; Number Number [ListOf Number] -> [ListOf [ListOf Number]]
; Using insert as a given number which means the number in the group
(define (insert-up-to num n l)
  (cond
    [(empty? l) (list (list n))]
    [(> num (length (first l))) (cons (cons n (first l)) (rest l))]
    [else (cons (list n) l)]))
(check-expect (insert-up-to 3 3 empty) (list (list 3)))
(check-expect (insert-up-to 3 7 '((3))) (list (list 7 3)))
(check-expect (insert-up-to 3 2 '((7 3))) (list (list 2 7 3)))
(check-expect (insert-up-to 3 5 '((2 7 3))) (list (list 5) (list 2 7 3)))


; Exercise 5

; Number [ListOf Number] -> [ListOf [ListOf Number]]
; return the numbers which three of them are in a group
(define (bundle-of n l)
  (local [; Number -> [ListOf [ListOf Number]]
          (define (get-bundle current result)
            (insert-up-to n current result))]
    (foldr get-bundle empty l)))
(check-expect (bundle-of 3 empty) empty)
(check-expect (bundle-of 3 '(1)) (list (list 1)))
(check-expect (bundle-of 3 '(4 2)) (list (list 4 2)))
(check-expect (bundle-of 3 '(3 5 2)) (list (list 3 5 2)))
(check-expect (bundle-of 3 '(-1 7 5 6)) (list (list -1) (list 7 5 6)))
(check-expect (bundle-of 3 '(3 2 1 4 6)) (list (list 3 2) (list 1 4 6)))
(check-random (bundle-of 3 (create-list 11)) (list (list (random 10) (random 10))
                                              (list (random 10) (random 10) (random 10))
                                              (list (random 10) (random 10) (random 10))
                                              (list (random 10) (random 10) (random 10))))
(check-random (bundle-of 3 (create-list 17)) (list (list (random 10) (random 10))
                                              (list (random 10) (random 10) (random 10))
                                              (list (random 10) (random 10) (random 10))
                                              (list (random 10) (random 10) (random 10))
                                              (list (random 10) (random 10) (random 10))
                                              (list (random 10) (random 10) (random 10))))


; Exercise 6

; [ListOf [ListOf Number]] -> [ListOf Number]
; Flatten given list of list of number into list of number
(define (flatten lol)
  (local [; [ListOf Number] [ListOf Number] -> [ListOf Number]
          (define (get-flatten current result)
            (append current result))]
    (foldr get-flatten empty lol)))
(define d (create-list 23))
(check-expect (flatten (bundle-of 7 d)) d)
(check-expect (flatten (bundle-of 5 d)) d)
(check-expect (flatten (bundle-of 3 d)) d)


; Exercise 7

; Number [ListOf Number] -> [ListOf Number]
; Take the first n elements of the given list
(define (take n l)
  (cond
    [(or (zero? n) (empty? l)) empty]
    [else (cons (first l) (take (sub1 n) (rest l)))]))
(define f (create-list 9))
(check-random (take 3 (create-list 9)) (list (random 10)
                                             (random 10)
                                             (random 10)))
(check-random (take 8 (create-list 9)) (list (random 10)
                                             (random 10)
                                             (random 10)
                                             (random 10)
                                             (random 10)
                                             (random 10)
                                             (random 10)
                                             (random 10)))
(check-random (take 9 (create-list 9)) (list (random 10)
                                             (random 10)
                                             (random 10)
                                             (random 10)
                                             (random 10)
                                             (random 10)
                                             (random 10)
                                             (random 10)
                                             (random 10)))
(check-random (take 10 (create-list 9)) (list (random 10)
                                              (random 10)
                                              (random 10)
                                              (random 10)
                                              (random 10)
                                              (random 10)
                                              (random 10)
                                              (random 10)
                                              (random 10)))
(check-random (take 11 (create-list 9)) (list (random 10)
                                              (random 10)
                                              (random 10)
                                              (random 10)
                                              (random 10)
                                              (random 10)
                                              (random 10)
                                              (random 10)
                                              (random 10)))
(check-random (take 0 (create-list 9)) empty)


; Exercise 8

; Number [ListOf Number] -> [ListOf Number]
; Drop the first n elements of the given list
(define (drop n l)
  (cond
    [(or (zero? n) (empty? l)) l]
    [else (drop (sub1 n) (rest l))]))
(define g (create-list 12))
(check-expect (append (take 5 g) (drop 5 g)) g)


; Exercise 9

; [ListOf Number] [< or >] -> Boolean
; Check if the given list is sorted
(define (sorted? l op)
  (cond
    [(empty? (rest l)) #true]
    [else (and (op (first l) (second l)) (sorted? (rest l) op))]))
(check-expect (sorted? (list 1 2 3 4 5 6) <) #true)
(check-expect (sorted? (list 1 2 3 4 6 5) <) #false)


; Exercise 10

; [ListOf Number] [< or >] -> [ListOf Number]
; The function goes through the list from left to right
; once, and for every pair of adjacent elements that don’t satisfy the predicate the elements are swapped
(define (one-pass l op)
  (cond
    [(empty? (rest l)) l]
    [else (if (op (first l) (second l))
              (cons (first l) (one-pass (rest l) op))
              (cons (second l) (one-pass (cons (first l) (rest (rest l))) op)))]))
(define z (list 1 8 2 0 2 9 6 7 7 9 5))
(check-expect (one-pass z <) (list 1 2 0 2 8 6 7 7 9 5 9))
(check-expect (one-pass z >) (list 8 2 1 2 9 6 7 7 9 5 0))
(check-expect (one-pass (list 1 2 3 4 5 6) >) (list 2 3 4 5 6 1))
(check-expect (one-pass (one-pass (list 1 2 3 4 5 6) >) >) (list 3 4 5 6 2 1))
(check-expect (one-pass (one-pass (one-pass (list 1 2 3 4 5 6) >) >) >)
              (list 4 5 6 3 2 1))

; [ListOf Number] [Number Number -> Boolean] -> [ListOf Number]
; attempts to sort the list of numbers according to the predicate (does it?)
(define (bubble-sort lon predicate)
  (cond ((sorted? lon predicate) lon)
        (else (bubble-sort (one-pass lon predicate) predicate))))
 
(bubble-sort (list 1 2 3 4 5 6) >)