;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-lambda-reader.ss" "lang")((modname |Lab-15-Siyi Xian|) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
; Accumulators

;; A ByTwo is one of:
;; - empty
;; - (make-two Number Number ByTwo)
(define-struct two (first second rest))


; Exercise 1

; ByTwo -> Number
; Computes the length of a ByTwo
(define (two-length t)
  (cond
    [(empty? t) 0]
    [else (+ 2 (two-length (two-rest t)))]))
(check-expect (two-length empty) 0)
(check-expect (two-length (make-two 1 2 (make-two 3 4 empty))) 4)


; Exercise 2

; ByTwo -> Number
; computes the sum of all the numbers in a ByTwo
(define (sum t)
  (cond
    [(empty? t) 0]
    [else (+ (two-first t) (two-second t) (sum (two-rest t)))]))
(check-expect (sum empty) 0)
(check-expect (sum (make-two 1 2 (make-two 3 4 empty))) 10)


; Exercise 3

; ByTwo -> Number
; computes the products of all the numbers in a ByTwo
(define (products t)
  (cond
    [(empty? t) 1]
    [else (* (two-first t) (two-second t) (products (two-rest t)))]))
(check-expect (products empty) 1)
(check-expect (products (make-two 1 2 (make-two 3 4 empty))) 24)


; Exercise 4

; ByTwo Number -> Number
; Computes the length of a ByTwo
(define (length-acc bt ans)
  (cond
    [(empty? bt) ans]
    [else (length-acc (two-rest bt) (+ 2 ans))]))
(check-expect (length-acc empty 0) 0)
(check-expect (length-acc (make-two 1 2 (make-two 3 4 empty)) 0) 4)
;   (two-length (make-two 1 2 (make-two 3 4 empty)))
; = (+ 2 (2 (0)))
;   (length-acc (make-two 1 2 (make-two 3 4 empty)) 0)
; = (length-acc (make-two 3 4 empty) (+ 2 0))
; = (length-acc empty (+ 2 (2 0)))
; = (+ 2 (2 0))

; ByTwo Number -> Number
; computes the sum of all the numbers in a ByTwo
(define (sum-acc bt ans)
  (cond
    [(empty? bt) ans]
    [else (sum-acc (two-rest bt) (+ (two-first bt) (two-second bt) ans))]))
(check-expect (sum-acc empty 0) 0)
(check-expect (sum-acc (make-two 1 2 (make-two 3 4 empty)) 0) 10)
;   (sum (make-two 1 2 (make-two 3 4 empty)))
; = (+ 1 2 (sum (make-two 3 4 empty)))
; = (+ 1 2 (3 4 (sum empty)))
; = (+ 1 2 (3 4 (0)))
;   (sum-acc (make-two 1 2 (make-two 3 4 empty)) 0)
; = (sum-acc (make-two 3 4 empty) (+ 1 2 0))
; = (sum-acc empty (+ 3 4 (1 2 0)))
; = (+ 3 4 (1 2 0))

; ByTwo Number -> Number
; computes the products of all the numbers in a ByTwo
(define (products-acc bt ans)
  (cond
    [(empty? bt) ans]
    [else (products-acc (two-rest bt) (* (two-first bt) (two-second bt) ans))]))
(check-expect (products-acc empty 1) 1)
(check-expect (products-acc (make-two 1 2 (make-two 3 4 empty)) 1) 24)
;   (products (make-two 1 2 (make-two 3 4 empty)))
; = (* 1 2 (products (make-two 3 4 empty)))
; = (* 1 2 (3 4 (products empty)))
; = (* 1 2 (3 4 (1)))
;   (products-acc (make-two 1 2 (make-two 3 4 empty)) 1)
; = (products-acc (make-two 3 4 empty) (* 1 2 1))
; = (products-acc empty (* 3 4 (1 2 1)))
; = (* 3 4 (1 2 1))


; Abstraction, local, and lambda

; Exercise 5

; Number Operation ByTwo -> Number
; Abstract Sum and Products
(define (bt-op base op bt)
  (cond
    [(empty? bt) base]
    [else (op (two-first bt) (two-second bt) (bt-op base op (two-rest bt)))]))

; ByTwo -> Number
; computes the sum of all the numbers in a ByTwo
(define (sum-op bt)
  (bt-op 0 + bt))
(check-expect (sum-op empty) 0)
(check-expect (sum-op (make-two 1 2 (make-two 3 4 empty))) 10)

; ByTwo -> Number
; computes the products of all the numbers in a ByTwo
(define (products-op bt)
  (bt-op 1 * bt))
(check-expect (products-op empty) 1)
(check-expect (products-op (make-two 1 2 (make-two 3 4 empty))) 24)


; Exercise 7

; A ByTwoS is one of:
; - empty
; - (make-two String String ByTwoS)
(define-struct two-string (first second rest))

; ByTwoS -> Number
; computes the length of the given ByTwoS
(define (length-bts bts)
  (cond
    [(empty? bts) 0]
    [else (+ (string-length (two-string-first bts))
             (string-length (two-string-second bts))
             (length-bts (two-string-rest bts)))]))
(check-expect (length-bts empty) 0)
(check-expect (length-bts (make-two-string "a" "b" (make-two-string "c" "d" empty))) 4)


; Exercise 8

; ByTwo [Number -> Number] -> ByTwo
; applies the function to every element in the ByTwo
(define (apply-bt bt f)
  (cond
    [(empty? bt) empty]
    [else (make-two (f (two-first bt))
                    (f (two-second bt))
                    (apply-bt (two-rest bt) f))]))
(check-expect (apply-bt (make-two 1 2 (make-two 3 4 empty))
                        (λ (x) (+ x 2)))
              (make-two 3 4 (make-two 5 6 empty)))
(check-expect (apply-bt (make-two 1 2 (make-two 3 4 empty))
                        (λ (x) (if (odd? x) (add1 x) (sub1 x))))
              (make-two 2 1 (make-two 4 3 empty)))