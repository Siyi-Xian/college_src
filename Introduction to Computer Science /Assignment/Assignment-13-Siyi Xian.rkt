;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-lambda-reader.ss" "lang")((modname |Assignment-13-Siyi Xian|) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
; An SOE is a non-empty Matrix.
; constraint for (list r1 ... rn), (length ri) is (+ n 1)
; interpretation represents a system of linear equations
 
; An Equation is a [List-of Number].
; constraint an Equation contains at least two numbers. 
; interpretation if (list a1 ... an b) is an Equation, 
; a1, ..., an are the left-hand side variable coefficients 
; and b is the right-hand side
 
; A Solution is a [List-of Number]
 
(define M ; an SOE 
  (list (list 2 2  3 10) ; an Equation 
        (list 2 5 12 31)
        (list 4 1 -2  1)))
 
(define S '(1 1 2)) ; a Solution

; Equation -> [List-of Number]
; extracts the left-hand side from a row in a matrix
(check-expect (lhs (first M)) '(2 2 3))
(define (lhs e) (reverse (rest (reverse e))))
 
; Equation -> Number
; extracts the right-hand side from a row in a matrix
(check-expect (rhs (first M)) 10)
(define (rhs e) (first (reverse e)))


; Exercise 464

; SOE Solution -> Boolean
; Check if the given solution is the one that given
(define (check-solution e solution)
  (local [; [ListOf Number] Solution -> Number
          ; get the value of the left side of the given one
          ; Ans : the answer of this function
          (define (value eq s ans)
            (cond
              [(or (empty? eq) (empty? s)) ans]
              [else (value (reverse (rest (reverse eq)))
                           (reverse (rest (reverse s)))
                           (+ (* (first (reverse eq))
                                 (first (reverse s))) ans))]))]
    (foldr (λ (eq r)
             (and (= (rhs eq)
                     (value (lhs eq) solution 0)) r))
           #true e)))
; When do it by hand, we get both solutions are
; x = 1, y = 1, z = 2.
; Then we put our answers into the check-solution function:
(check-expect (check-solution '((2  2  3  10)
                                (   3  9  21)
                                (  -3 -8 -19))
                              S) #true)
(check-expect (check-solution M S) #true)


; Exercise 466

; A TM is a [List-of Equation]
; such that the Equations are of decreasing length: 
;   n + 1, n, n - 1, ..., 2. 
; interpretation represents a triangular matrix

; SOE -> TM
; triangulates the given system of equations 
(define (triangulate s)
  (local [; [ListOf Number] [ListOf Number] Number -> [ListOf Number]
          (define (help-counteract e base t)
            (cond
              [(or (empty? e) (empty? base)) empty]
              [else (cons (- (first e) (* t (first base)))
                          (help-counteract (rest e) (rest base) t))]))
          ; [ListOf Number] [ListOf [ListOf Number]] -> SOE
          ; Counteract the first index of the given equations
          (define (counteract base e)
            (map (λ (eq)
                   (help-counteract (rest eq) (rest base)
                                    (/ (first eq) (first base)))) e))]
    (cond
      [(empty? s) empty]
      [else (cons (first s)
                  (triangulate (counteract (first s) (rest s))))])))
(check-expect (triangulate M)
              '((2  2  3  10)
                (   3  9  21)
                (      1   2)))


; Exercise 467

; SOE -> TM
; triangulates the given system of equations
; This algorithm terminate are only suit for all the given metrix
; which is legally. That means the given group of equation must have solution.
; The reason is that if there is some variables do not have exactly value,
; there must be a condition that all the first number of each equation are zero.
; So that this one will report an error.
(define (triangulate.v2 s)
  (local [; [ListOf [ListOf Number]] Number -> [ListOf [ListOf Number]]
          ; Check if the first number of the given list is zero
          ; Num : the most times remain that this function can be run
          (define (check-zero e num)
            (cond
              [(zero? num) e]
              [else (if (zero? (first (first e)))
                        (check-zero (append (rest e) (list (first e)))
                                    (sub1 num)) e)]))
          ; [ListOf Number] [ListOf Number] Number -> [ListOf Number]
          (define (help-counteract e base t)
            (cond
              [(or (empty? e) (empty? base)) empty]
              [else (cons (- (first e) (* t (first base)))
                          (help-counteract (rest e) (rest base) t))]))
          ; [ListOf Number] [ListOf [ListOf Number]] -> SOE
          ; Counteract the first index of the given equations
          (define (counteract base e)
            (check-zero (map (λ (eq)
                               (help-counteract (rest eq) (rest base)
                                                (/ (first eq) (first base)))) e)
                        (- (length base) 2)))]
    (cond
      [(empty? s) empty]
      [else (cons (first s)
                  (triangulate.v2 (counteract (first s) (rest s))))])))
(check-expect (triangulate.v2 '((2  3  3 8)
                                (2  3 -2 3)
                                (4 -2  2 4)))
              '((2  3  3   8)
                (  -8 -4 -12)
                (     -5  -5)))

; Exercise 471

; A Node is a Symbol.

; A Path is a [List-of Node].
; interpretation The list of nodes specifies a sequence
; of immediate neighbors that leads from the first 
; Node on the list to the last one. 
(define sample-graph
  '((A B E)
    (B E F)
    (C D)
    (D)
    (E C F)
    (F D G)
    (G)))

; Node Graph -> [ListOf Node]
; find the nighbors of the given origination
(define (neighbors origination G)
  (foldr (λ (current result)
           (if (symbol=? (first current) origination)
               (rest current) result)) empty G))
(check-expect (neighbors 'A sample-graph) '(B E))
(check-expect (neighbors 'D sample-graph) '())


;; Data definitions
 
;; Radio Show  (RS) is a (make-rs String Number ListOfAd)
(define-struct rs (name minutes ads))
 
;; Ad is a (make-ad String Number Number)
(define-struct ad (name minutes profit))
 
;; a ListfAd is either
;; empty, or
;; (cons Ad ListOfAd)
 
;; Examples of data:
 
(define ipod-ad (make-ad "ipod" 2 100))
(define ms-ad (make-ad "ms" 1 500))
(define xbox-ad (make-ad "xbox" 2 300))
 
(define news-ads (list ipod-ad ms-ad ipod-ad xbox-ad))
(define game-ads (list ipod-ad ms-ad ipod-ad ms-ad xbox-ad ipod-ad))
(define bad-ads (list ipod-ad ms-ad ms-ad ipod-ad xbox-ad ipod-ad))
 
(define news (make-rs "news" 60 news-ads))
(define game (make-rs "game" 120 game-ads))
 
 
; Exercise 1

;; compute the total time for all ads in the given list
;; total-time: ListOfAd -> Number
(define (total-time adlist)
  (local [; [ListOf Ad] Number -> Number
          ; Adt : the given list of ads
          ; T : the total time that has already been counted
          (define (total-time-acc adt t)
            (cond
              [(empty? adt) t]
              [else (total-time-acc (rest adt) (+ (ad-minutes (first adt)) t))]))]
    (total-time-acc adlist 0)))

(check-expect (total-time news-ads) 7)
(check-expect (total-time game-ads) 10)


; Exercise 2

; RS -> Number
; Count the total profit of all ads in the given radio shows
(define (total-profit r)
  (local [; [ListOf Ad] String Number -> Number
          ; Ads : the ads that has not been counted the profits
          ; Ans : the profit that has already been counted
          (define (total-profit-acc ads ans)
            (cond
              [(empty? ads) ans]
              [else (total-profit-acc (rest ads) (+ ans (ad-profit (first ads))))]))]
    (total-profit-acc (rs-ads r) 0)))
(check-expect (total-profit news) (+ 100 500 100 300))
(check-expect (total-profit game) (+ 100 500 100 500 300 100))


; Exercise 3

; [ListOf Ad] -> Boolean
; Determain whether the given list of ads is acceptable
(define (no-repeat adlist)
  (local [; [ListOf Ad] Ad Boolean -> Boolean
          ; Ads : the list of ads that have not been checked
          ; Previous : the last ad
          ; Ans : the answer till now
          (define (no-repeat-acc ads previous ans)
            (cond
              [(empty? ads) ans]
              [else (no-repeat-acc (rest ads) (first ads)
                                   (and ans (not (equal? previous (first ads)))))]))]
    (no-repeat-acc adlist (make-ad "" 0 0) #true)))
(check-expect (no-repeat news-ads) #true)
(check-expect (no-repeat game-ads) #true)
(check-expect (no-repeat bad-ads) #false)


; Exercise 4

; I need accumulators in the function above.
; The reason are that we need to use both previous ad's name,
; which is used to determain whether the list is OK, and the answer to return.
; However, I can use a function which doesn't need any accumulator.
; [ListOf Ad] -> Boolean
(define (no-repeat.v2 adlist)
  (cond
    [(empty? (rest adlist)) #true]
    [else (and (not (equal? (first adlist) (second adlist)))
               (no-repeat.v2 (rest adlist)))]))
(check-expect (no-repeat.v2 news-ads) #true)
(check-expect (no-repeat.v2 game-ads) #true)
(check-expect (no-repeat.v2 bad-ads) #false)