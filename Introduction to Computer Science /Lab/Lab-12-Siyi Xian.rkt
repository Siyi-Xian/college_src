;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-lambda-reader.ss" "lang")((modname |Lab-12-Siyi Xian|) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
; 0. sum the numbers in a list of numbers
;
; [ListOf Number] -> Number
; sums the numbers in the given list
;
(define (sum lon)
  (local (
          ; [ListOf Number] Number -> Number
          ; adds the numbers in the list one by one to the second argument
          ;
          ; accumulator: remaining-numbers, numbers still left to be processed
          ; accumulator: sum-thus-far, the answer being built iteration by iteration
          ;
          ; example:
          ;   remaining-numbers    sum-thus-far
          ; -------------------------------------------------------
          ;  (1 2 3 4 5)                  0
          ;    (2 3 4 5)              0 + 1 = 1
          ;      (3 4 5)              1 + 2 = 3
          ;        (4 5)              3 + 3 = 6
          ;          (5)              6 + 4 = 10
          ;        empty             10 + 5 = 15
          ;
          (define (helper remaining-numbers sum-thus-far)
            (cond ((empty? remaining-numbers)                 ; termination condition
                   sum-thus-far)                              ; sum-thus-far becomes the final answer
                  (else (helper (rest remaining-numbers)      ; fewer numbers remain
                                (+ (first remaining-numbers)  ; update sum-thus-far
                                   sum-thus-far)))))
          )
    (helper lon 0))) ; initial call to the helper function
    ; (notice the initial values of the accumulators)
 
(check-expect (sum '(1 2 3 4 5)) (+ 0 1 2 3 4 5))


; Prime Factors
; Exercise 1

; Number -> [ListOf Numbers]
; Find all the prime factors of the given number
(define (factors-of n)
  (local [; Number Number [ListOf Number] -> [ListOf Number]
          ; Add the given divisor to the list if it is divisible

          ; Accumulator: number, the given number which needs to find factors
          ; Accumulatoe: divisor, the given divisor at this step
          ; Accumulator: factors, the answer which has add the factors

          ; example:
          ;       number       divisor       factors
          ; --------------------------------------------------
          ;        150           2            empty
          ;         75           2            (cons 2 empty)
          ;         75           3            (cons 2 empty)
          ;         25           3            (cons 3 (cons 2 empty))
          ;         25           4            (cons 3 (cons 2 empty))
          ;         25           5            (cons 3 (cons 2 empty))
          ;          5           5            (cons 5 (cons 3 (cons 2 empty)))
          ;          1           5            (cons 5 (cons 5 (cons 2 (cons 2 empty))))

          (define (find-factors-of number divisor factors)
            (cond
              [(= 1 number) (reverse factors)]
              [else (if (zero? (remainder number divisor))
                        (find-factors-of (/ number divisor) divisor (cons divisor factors))
                        (find-factors-of number (add1 divisor) factors))]))]
    (find-factors-of n 2 empty)))

(check-expect (factors-of 1) empty)
(check-expect (factors-of 150) (list 2 3 5 5))
(check-expect (factors-of 41) (list 41))


; Prime Numbers

; Number -> Boolean
; is the number prime or not?
(define (prime? number)
  (local ((define factors (factors-of number))) ; examine number
    (equal? factors (list number)))) ; if it's its only factor it's prime
 
(check-expect (map prime? '( 1  2  3  4  5  6  7 13 17 19 23))
              '(#f #t #t #f #t #f #t #t #t #t #t))

; Exrcise 2

; Number -> [ListOf Number]
; Produce the first n prime numbers
(define (primes-first n)
  (local [; Number [ListOf Number] Number -> [ListOf Number]
          ; Add candidate to the primes if it is prime number

          ; Accumulator: candidate, the given number which needs to check if it is prime number
          ; Accumulatoe: primes, the list that has already add some prime numbers
          ; Accumulator: how-many, the amount of rest of primes

          ; example:
          ;         candidate    primes      how-many
          ;     -----------------------------------------
          ;                      empty          7
          ;            2         '(2)           6
          ;            3         '(3 2)         5
          ;            4         '(3 2)         5
          ;            5         '(5 3 2)       4
          ;            6         '(5 3 2)       4
          ;            7         '(7 5 3 2)     3
          ;            8

          (define (get-primes-first candidate primes how-many)
            (cond
              [(zero? how-many) (reverse primes)]
              [else (if (prime? candidate)
                        (get-primes-first (add1 candidate)
                                          (cons candidate primes)
                                          (sub1 how-many))
                        (get-primes-first (add1 candidate) primes how-many))]))]
    (get-primes-first 2 empty 7)))

(check-expect (primes-first 7) (list 2 3 5 7 11 13 17))


; Buffon Needle Experiment
; Exercise 3

; Number -> Number
; Produce pi
(define (buffon-experiment n)
  (local [; Number Number -> Number
          (define (find-buffon-experiment hits tries)
            (cond
              [(= n tries) (/ tries hits)]
              [else (if (< 2 (+ (/ (random 20000000) 10000000)
                                (sin (* pi (/ (random 180) 180)))))
                        (find-buffon-experiment (add1 hits) (add1 tries))
                        (find-buffon-experiment hits (add1 tries)))]))]
    (find-buffon-experiment 0 0)))

(check-within (buffon-experiment 100000) pi 0.05)


; Binary Digits
; Exercise 4

; Number -> String
; Produce the binary digits of the given number
(define (to-binary n)
  (local [; Number String -> String
          (define (find-binary leftover answer)
            (cond
              [(zero? leftover) answer]
              [else (find-binary (quotient leftover 2)
                                 (string-append (number->string (remainder leftover 2)) answer))]))]
    (find-binary n "")))

(check-expect (to-binary 13) "1101")


; Alternating Sum
; Exercise 5

; [ListOf Number] -> Number
; Count the Alternating Sum of the given list of number
(define (alternating-sum n)
  (local [; [ListOf Number] Number Number -> Number
          (define (get-alternating-sum remaining-numbers current-sign sum-thus-far)
            (cond
              [(empty? remaining-numbers) sum-thus-far]
              [else (get-alternating-sum (rest remaining-numbers)
                                         (- 0 current-sign)
                                         (+ sum-thus-far
                                            (* current-sign (first remaining-numbers))))]))]
    (get-alternating-sum n 1 0)))

(check-expect (alternating-sum (list 1 4 9 16 9 7 4 9 11)) -2)