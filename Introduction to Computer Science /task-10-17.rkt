;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-reader.ss" "lang")((modname task-10-17) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
(require 2htdp/image)
(require 2htdp/universe)

; An Animal is (make-animal Posn Kind)
; Kind is one of:
; - "pid"
; - "horse"
; - "monkey"
; - "chicken"
(define-struct animal (p kind))

; ordered? : Number Number Number -> Boolean
; are these number in order?
(define (ordered? a b c)
  (and (< a b) (< b c))
  #;
  (cond [(< a b) (< b c)]
        [else #false]))


; A Person is (make-person String ListOfPerson)
(define-struct person (name kids))

; A ListOfPerson is one of:
; - empty
; - (cons Person ListOfPerson)

(define p1 (make-person "Jane" empty))
(define p2 (make-person "Jack" (list p1)))

#;
; process-person : Person -> ???
(define (process-person p)
  (person-name p) ... (process-lop (person-kids p)))

#;
; process-lop : ListOfPerson -> ???
(define (process-lop lop)
  (cond
    [(empty? lop) ...]
    [else (first lop) (process-lop (rest lop))]))


; count-descendants : Person -> Number
; count the number of decendants including the given person
(define (count-descendants p)
  #;
  (+ 1 (count-lop (person-kids p)))
  (local [; combine-p : Person -> Number
          ; add the desendants of p to n
          (define (combine-p p n)
            (+ n (count-descendants p)))]
  (+ 1 (foldr combine-p 0 (person-kids p)))))
(check-expect (count-descendants p1) 1)
(check-expect (count-descendants p2) 2)

#;
; combine-p : Person -> Number
; add the desendants of p to n
(define (combine-p p n)
  (+ n (count-descendants p)))

; cout-lop : ListOfPerson -> Number
; count all the desendants in a list of people
(define (count-lop lop)
  (cond
    [(empty? lop) 0]
    [else (+ (count-descendants (first lop))
              (count-lop (rest lop)))]))


; A TwoTree is one of:
; - Number
; - (make-two ThreeTree ThreeTree)
(define two (left right))
; ThreeTree is (make-three TwoTree TwoTree TwoTree)
(define three (left center right))

; add-all : TwoThree -> TwoTree
; add 1 to all numbers in the tree
(define (add1-all 2t)
  (cond [(number? 2t) (add1 2t)]
        [else (make-two (add1-3 (two-left 2t))
                        (add1-3 (two-right 2t)))]))

; add1-3 : ThreeTree -> ThreeTree
; add1 to all numbers in the tree
(define (add1-3 3t)
  (make-three (add1-all (three-left 3t))
              (add1-all (three-center 3t))
              (add1-all (three-right 3t))))

(check-expect (add1-all 5) 6)
(check-expect (add1-all (make-two (make-three 4 5 6) (make-three 4 5 6)))
              (make-two (make-three 5 6 7) (make-three 5 6 7)))