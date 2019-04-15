;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-reader.ss" "lang")((modname |Lecture 8|) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
(require 2htdp/universe)
(require 2htdp/image)

;Pro1
;1
;A vertex is (make-vertex Number Number)

(define-struct vertex (x y))

(define v1 (make-vertex 3 3))
(define v2 (make-vertex 0 10))

;2
;ListOfVertex is one of:
; - empty
; - (cons Vertex ListOfVertex)

(define lov1 empty)
(define lov (cons v1 (cons v2 empty)))

;3
; Vertex Vertex-> Vertex
; (make-small v1 v1) produces a vertex with two smaller x and y coordinates
(define (make-small v1 v2)
  (make-vertex
   (min (vertex-x v1) (vertex-x v2))
   (min (vertex-y v1) (vertex-y v2))))

;4
; ListOfVertex -> Vertex
; (min-vertex lov) finds the minimum vertex of the given list
(define (min-vertex lov)
  (cond
    [(empty? (empty? lov) (make-vertex 0 0))]
    [else (cond [(empty? (rest lov)) (first lov)]
                [else (make-samll (first lov) (min-vertex (rest lov)))])]))

;(min-vertex empty) -> (make-vertex 0 0)
;(min-vertex (list (make-vertex 1 5) (make-vertex 10 2))) -> (make-vertex 1 2)

;5
; Vertex Vertex Vertex -> Boolean
; (inside v1 v2 v3) determines if v is in the boarding box

(define (inside lo v hi)
  (and (< (vertex-x lo) (vertex-x v) (vertex-x hi))
       (< (vertex-y lo) (vertex-y v) (vertex-y hi))))

;(inside (make-vertex 0 0)
;        (make-vertex 2 2)
;        (make-vertex 4 4))   -> #true

;Pro2

;1
; A ListOfNumber is one of:
; -empty
; - (cons Number ListOfNumber)

; ListOfNumber Number -> ListOfNumber
; (insert lon n) 
(define (insert lon n)
  (cond[(empty? lon) (cons n empty)]
       [else (cond[(< n (first lon)) (cons n lon)]
                  [(> n (first lon)) (cons (first lon) (insert (rest lon) n))])]))

;2
; ListOfNumber -> ListOfNumber
; (sort lon) sort the given list
(define (sort lon)
  (cond[(empty? lon) empty]
       [else (insert (sort (rest lon))
                     (first lon))]))

;Pro3
;1
; A Person is one of:
; - (make-parent Person Person Sting String)
; - (male-childless String Number)

(define-struct parent (child1 child2 eye height))
(define-struct childless (eye height))

;2
;Person -> Number
; count blue eyed descendent
(define (blues p)
  (cond[(childless? p) (if (string=? "blue" (childless-eye p))
                           1
                           0)]
       [(parent? p) (+ (if (string=? "blue" (parent-eye p))
                        1
                        0)
                       (blues (parent-child1 p))
                       (blues (parent-child2 p)))]))

;Pro4
;1
; A FootbalTeam is (make-team String Number Number)
(define-struct team (name wins losses))

;2
; FootballTeam -> Boolean
(define (playpffs? ff)
  (> (/ (team-wins ff) (team-losses ff))
     (/ 10 6)))

;Pro5
;1
; Number Set -> Boolean
; is n in the given set?
(define (number-of n s)
  (cond[(empty-set? s) #false]
       [(one-set? s) (=(one-set-n s) n)]
       [(two-set? s) (or (= (one-set-n s) n)
                         (= (two-set-n s) n))]
       [(more? s) (or (= (more-n s) n)
                      (number-of n (more-set s)))]))

;2
; add-to: Number Set -> Set
(define (addto n s)
  (make-more n s))

;family tree
;data-definition
;structure-definition
;big-bang
;constructor