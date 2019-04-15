;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-lambda-reader.ss" "lang")((modname |Assingment-11-Siyi Xian|) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
; Book Exercise

; [List-of 1String] N -> [List-of String]
; bundles chunks of s into strings of length n
; idea take n items and drop n at a time
(define (bundle s n)
  (cond
    [(empty? s) '()]
    [else
     (cons (implode (take s n)) (bundle (drop s n) n))]))
(check-expect (bundle (list "h" "e" "l" "l" "o") 2)
              (list "he" "ll" "o"))
 
; [List-of X] N -> [List-of X]
; keep the first n items from l if possible or everything
(define (take l n)
  (cond
    [(zero? n) '()]
    [(empty? l) '()]
    [else (cons (first l) (take (rest l) (sub1 n)))]))
 
; [List-of X] N -> [List-of X]
; remove the first n items from l if possible or everything
(define (drop l n)
  (cond
    [(zero? n) l]
    [(empty? l) l]
    [else (drop (rest l) (sub1 n))]))


; Exercise 422

; [ListOf Any] Nat -> [ListOf [ListOf Any]]
; The function’s result is a list of list chunks of size n.
; Each chunk represents a sub-sequence of items in l
(define (list->chunks lo n)
  (cond
    [(empty? lo) empty]
    [else (cons (take lo n) (list->chunks (drop lo n) n))]))
(check-expect (list->chunks (list "h" "e" "l" "l" "o") 2)
              (list (list "h" "e") (list "l" "l") (list "o")))


; Exercise 423

; String Nat -> [ListOf String]
; Put every n letter in a single string, and come out with a list of string
(define (partition str n)
  (let [(l (string-length str))]
    (cond
      [(zero? n) empty]
      [(< l n) (list str)]
      [else (cons (substring str 0 n) (partition (substring str n l) n))])))
(check-expect (partition "hello" 0) '())
(check-expect (partition "hello" 2) (list "he" "ll" "o"))


; Exercise 429

; [List-of Number] Number -> [List-of Number]
; Keep the number in the given list which is larger than given number
(define (largers alon n)
  (filter (λ (x) (> x n)) alon))
(check-expect (largers '(1 2 3 4 5 6 7) 4) '(5 6 7))
 
; [List-of Number] Number -> [List-of Number]
; Keep the number in the given list which is smaller than given number
(define (smallers alon n)
  (filter (λ (x) (< x n)) alon))
(check-expect (smallers '(1 2 3 4 5 6 7) 4) '(1 2 3))


(define ε 0.1)
; Exercise 449

; [Number -> Number] Number Number -> Number
; determines R such that f has a root in [R,(+ R ε)]
; assume f is continuous 
; assume (or (<= (f left) 0 (f right)) (<= (f right) 0 (f left)))
; generative divide interval in half, the root is in one of the two
; halves, pick according to assumption
(define (find-root f left right)
  (local [(define mid (/ (+ left right) 2))
          (define f@mid (f mid))
          (define f@left (f left))
          (define f@right (f right))]
    (cond
      [(<= (- right left) ε) left]
      [else
       (cond
         [(or (<= f@left 0 f@mid) (<= f@mid 0 f@left))
          (find-root f left mid)]
         [(or (<= f@mid 0 f@right) (<= f@right 0 f@mid))
          (find-root f mid right)])])))
; Find the root of function 2x^2+x-4 between 1 and 2
(check-within (find-root (λ (x) (- (+ (* x x 2) x) 4)) 1 2)
              (/ (- (sqrt (- (* 1 1)
                             (* 4 2 -4)))
                    1)
                 (* 2 2))
              ε)


; Exercise 461
 
; [Number -> Number] Number Number -> Number
; computes the area under the graph of f between a and b
; assume (< a b) holds 
(define (integrate-adaptive f a b)
  (let [(mid (/ (+ a b) 2))
        (di (- b a))]
    (if (<= di ε)
        (abs (* di (f mid)))
        (+ (integrate-adaptive f a mid)
           (integrate-adaptive f mid b)))))
(check-within (integrate-adaptive (λ (x) 20) 12 22) 200 ε)
(check-within (integrate-adaptive (λ (x) (* 2 x)) 0 10) 100 ε)
(check-within (integrate-adaptive (λ (x) (* 3 (sqr x))) 0 10)
              1000
              ε)


; A ball on a table

; Exercise 1

; A Ball is : (make-ball Posn Number Number)
; Pos : the position of the given ball
; VX & VY : the velocity on x direction and y direction
(define-struct ball (pos v-x v-y))


; Exercise 2

; A Table is : (make-table Number Posn)
; R : the radius of the given table
; Pos : the position of the given table
(define-struct table (r pos))

; Ball Table -> Boolean
; Check if the given ball is on the table
(define (on-table? b t)
  (<= (sqrt (+ (sqr (- (posn-x (ball-pos b))
                       (posn-x (table-pos t))))
               (sqr (- (posn-y (ball-pos b))
                       (posn-y (table-pos t)))))) (table-r t)))
(check-expect (on-table? (make-ball (make-posn 2 2) 2 2)
                         (make-table 5 (make-posn 0 0))) #true)
(check-expect (on-table? (make-ball (make-posn 2 2) 2 2)
                         (make-table 1 (make-posn 0 0))) #false)


; Exercise 3

; Ball -> Ball
; Move the given ball by the given velocity
(define (move-ball b)
  (make-ball (make-posn (+ (posn-x (ball-pos b))
                           (ball-v-x b))
                        (+ (posn-y (ball-pos b))
                           (ball-v-y b)))
             (ball-v-x b) (ball-v-y b)))
(check-expect (move-ball (make-ball (make-posn 2 2) 2 2))
              (make-ball (make-posn 4 4) 2 2))


; Exercise 4

; Ball Table
; Count the steps when the given ball move out of the given table
(define (how-long b t)
  (if (on-table? b t) (add1 (how-long (move-ball b) t)) 0))
(check-expect (how-long (make-ball (make-posn 3 4) 3 4)
                        (make-table 15 (make-posn 0 0))) 3)
(check-expect (how-long (make-ball (make-posn 2 2) 2 2)
                        (make-table 1 (make-posn 0 0))) 0)