;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-lambda-reader.ss" "lang")((modname Final-1) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
(define-struct person (name age height))

(define person1 (make-person "Andy" 40 65))
(define person2 (make-person "Sam" 30 67))
(define people (list person1 person2))

(define (average-age p)
  (cond
    [(empty? p) 0]
    [else (/ (foldr (λ (x r)
                      (+ (person-age x) r)) 0 p)
             (length p))]))
(check-expect (average-age empty) 0)
(check-expect (average-age people) 35)


(define (remove-young p)
  (local [(define ave (average-age p))]
    (filter (λ (x) (>= (person-age x) ave)) p)))
(check-expect (remove-young empty) empty)
(check-expect (remove-young people) (list person1))


(define-struct playlist (song artist next-song))

(define list1 (make-playlist "A" "Tom" empty))
(define list2 (make-playlist "B" "Bob" list1))
(define list3 (make-playlist "C" "Jack" list2))

(define (beginning p n)
  (cond
    [(zero? n) empty]
    [else (make-playlist (playlist-song p)
                         (playlist-artist p)
                         (beginning (playlist-next-song p)
                                    (sub1 n)))]))
(check-expect (beginning empty 0) empty)
(check-expect (beginning list3 1) (make-playlist "C" "Jack" empty))


(define-struct tree (name spouse children))

(define grandson (make-tree "Bob" #false empty))
(define son      (make-tree "Tom" #true (list grandson)))
(define father   (make-tree "Jay" #true (list son)))

(define (married-people t)
  (cond
    [(empty? t) 0]
    [else (local [(define check-children
                    (foldr (λ (x r)
                             (+ (married-people x) r)) 0
                           (tree-children t)))]
            (if (tree-spouse t)
                (add1 check-children)
                check-children))]))
(check-expect (married-people empty) 0)
(check-expect (married-people father) 2)

; Lon : the given list which need to
; invariant : the initial value of the accumulator is 0
; and for every number we get from the list the accumulator will be updated
; So that it is equal to the sum of all the numbers we have ever been seen.
(define (sum/acc lon s)
  (cond
    [(empty? lon) s]
    [else (sum/acc (rest lon) (+ s (first lon)))]))
(check-expect (sum/acc empty 0) 0)
(check-expect (sum/acc (list 1 2 3 4) 0) 10)

(define (prod/acc lon p)
  (cond
    [(empty? lon) p]
    [else (prod/acc (rest lon) (* p (first lon)))]))
(check-expect (prod/acc empty 1) 1)
(check-expect (prod/acc (list 1 2 3 4) 1) 24)

(define (sum lon)
  (sum/acc lon 0))
(define (prod lon)
  (prod/acc lon 1))

(define (abs/acc lon r op)
  (cond
    [(empty? lon) r]
    [else (abs/acc (rest lon) (op r (first lon)) op)]))
(define (sum/acc.v2 lon s) (abs/acc lon s +))
(define (prod/acc.v2 lon p) (abs/acc lon p *))
(check-expect (sum/acc.v2 empty 0) 0)
(check-expect (sum/acc.v2 (list 1 2 3 4) 0) 10)
(check-expect (prod/acc.v2 empty 1) 1)
(check-expect (prod/acc.v2 (list 1 2 3 4) 1) 24)
