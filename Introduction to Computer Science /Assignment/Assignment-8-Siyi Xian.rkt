;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-reader.ss" "lang")((modname |Assignment-8-Siyi Xian|) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
; Exercise 1

; [ListOf Posn] Posn -> Posn
; find the posn in the given list that is close to the given posn
(define (smallest lp pos)
  (local
    [; MAX is the farthest Posn from the given pos
     ; -> Posn
     (define MAX (make-posn 99999999999999
                            99999999999999))
     ; Posn Posn -> Number
     ; count the distance between given two point
     (define (distance a b)
       (sqrt (+ (sqr (- (posn-x a)
                        (posn-x b)))
                (sqr (- (posn-y a)
                        (posn-y b))))))
     ; Posn Posn -> Posn]
     ; help to find the cloest one
     (define (help-smallest a b)
       (if (< (distance a pos) (distance b pos)) a b))]
    (foldr help-smallest MAX lp)))
(check-expect (smallest (list (make-posn 1 2)
                              (make-posn 5 3)
                              (make-posn 2 4)) (make-posn 0 0)) (make-posn 1 2))


; Exercise 2

; [ListOf Number] -> Nat
; count the amount of the given list
(define (count ln)
  (local
    [; Number Number -> Number
     ; add one when right a item in the list
     (define (add-one r l)
       (add1 l))]
    (foldr add-one 0 ln)))
(check-expect (count (list 1 2 3 4 5 6)) 6)

; abstraction
; [ListOf Any] -> Number
(define (count-any ln)
  (local
    [; Any Number -> Number
     ; add one when right a item in the list
     (define (add-one r l)
       (add1 l))]
    (foldr add-one 0 ln)))
(check-expect (count-any (list "a" "a" "a" "a" "a" "a")) 6)

; Book Exercises


; Exercise 238

; [ListOf Number] -> Number
; determines the smallest 
; number on l
(define (inf l) (min-max l <))
(define (inf.v2 l) (min-max.v2 l min))
    
; [ListOf Number] -> Number
; determines the largest 
; number on l
(define (sup l) (min-max l >))
(define (sup.v2 l) (min-max.v2 l max))

; [ListOf Number] [> or <] -> Number
; abstract the above two function
(define (min-max l op)
  (cond
    [(empty? (rest l)) (first l)]
    [else (if (op (first l) (min-max (rest l) op))
              (first l)
              (min-max (rest l) op))]))
(check-expect (inf (list 123 231 111)) 111)
(check-expect (sup (list 123 231 111)) 231)

; [ListOf Number] [max or min] -> Number
(define (min-max.v2 l op)
  (cond
    [(empty? (rest l)) (first l)]
    [else (op (first l) (min-max.v2 (rest l) op))]))
(check-expect (inf.v2 (list 25 24 23 22 21 20 19 18 17 16 15 14 13
                            12 11 10 9 8 7 6 5 4 3 2 1)) 1)
(check-expect (sup.v2 (list 25 24 23 22 21 20 19 18 17 16 15 14 13
                            12 11 10 9 8 7 6 5 4 3 2 1)) 25)

; Exercise 239

; A ListOfXY is one of
; - (cons Number (cons Number '()))
; - (cons Number (cons String '()))
; - (cons String (cons Boolean '()))


; Exercise 241

; A NEListOfAny is one of :
; - (cons Any '())
; - (cons Any NEListOfAny)


; Exercise 250

; Number -> [List-of Number]
; tabulates sin between n and 0 (incl.) in a list
(define (tab-sin n) (tabulate n sin))
  
; Number -> [List-of Number]
; tabulates sqrt between n and 0 (incl.) in a list
(define (tab-sqrt n) (tabulate n sqrt))

; Number [sin or sqrt] -> [ListOf Number]
(define (tabulate n op)
  (cond
    [(= n 0) (list (op 0))]
    [else (cons (op n) (tabulate (sub1 n) op))]))
(check-within (tab-sin 1) (list (sin 1) 0) 0.0001)
(check-expect (tab-sqrt 1) (list 1 0))

; Number -> [ListOf Number]
; tabulates sqr between n and 0 (incl.) in a list
(define (tab-sqr n) (tabulate n sqr))
; Number -> [ListOf Number]
; tabulates tan between n and 0 (incl.) in a list 
(define (tab-tan n) (tabulate n tan))
(check-within (tab-tan 1) (list (tan 1) 0) 0.0001)
(check-expect (tab-sqr 1) (list 1 0))


; Abstracting Trees

; A Tree is one of:
;  - (make-leaf Number)
;  - (make-node1 Number Tree)
;  - (make-node2 Number Tree Tree)

; A Leaf is (make-leaf Number)
(define-struct leaf (num))
; A Node1 is (make-node1 Number Tree)
(define-struct node1 (num tree))
; A Node2 is (make-node2 Number Tree Tree)
(define-struct node2 (num tree1 tree2))

(define tr1 (make-leaf 2))
(define tr2 (make-node1 4 tr1))
(define tr3 (make-node2 1 tr1 tr2))

; Exercise 3

; Tree -> Number
; count all the number in the given tree
(define (sum-tree tr)
  #;
  (cond
    [(leaf? tr) (leaf-num tr)]
    [(node1? tr) (+ (node1-num tr)
                    (sum-tree (node1-tree tr)))]
    [(node2? tr) (+ (node2-num tr)
                    (sum-tree (node2-tree1 tr))
                    (sum-tree (node2-tree2 tr)))])
  #;
  ; abstract this function in op tree
  (op-tree tr + + +)
  ; abstract this function in count-tree
  (process-tree tr + + +))
(check-expect (sum-tree tr1) 2)
(check-expect (sum-tree tr2) 6)
(check-expect (sum-tree tr3) 9)

; Tree -> Number
; multiplies together all of the elements in the given tree
(define (prod-tree tr)
  #;
  (cond
    [(leaf? tr) (leaf-num tr)]
    [(node1? tr) (* (node1-num tr)
                    (prod-tree (node1-tree tr)))]
    [(node2? tr) (* (node2-num tr)
                    (prod-tree (node2-tree1 tr))
                    (prod-tree (node2-tree2 tr)))])
  #;
  ; abstract this function in op-tree
  (op-tree tr * * *) 
  ; abstract this function in count-tree
  (process-tree tr * * *)) 
(check-expect (prod-tree tr1) 2)
(check-expect (prod-tree tr2) 8)
(check-expect (prod-tree tr3) 16)


; Exercies 4

; Op is one of :
; - *
; - +

; Tree Op Op Op-> Number
; abstract sum-tree and prod-tree into a single function op-tree
(define (op-tree tr leaf-op node1-op node2-op)
  (cond
    [(leaf? tr) (leaf-op (leaf-num tr))]
    [(node1? tr) (node1-op (node1-num tr)
                           (op-tree (node1-tree tr) leaf-op node1-op node2-op))]
    [(node2? tr) (node2-op (node2-num tr)
                           (op-tree (node2-tree1 tr) leaf-op node1-op node2-op)
                           (op-tree (node2-tree2 tr) leaf-op node1-op node2-op))]))
(check-expect (sum-tree tr3) 9)
(check-expect (prod-tree tr3) 16)


; Exercise 5

; Tree -> Number
; count the number of leaves in the given tree
(define (count-tree tr)
  #;
  (cond
    [(leaf? tr) 1]
    [(node1? tr) (+ 1
                    (count-tree (node1-tree tr)))]
    [(node2? tr) (+ 1
                    (count-tree (node2-tree1 tr))
                    (count-tree (node2-tree2 tr)))])
  ; abstract this function in count-tree
  (process-tree tr leaf-count node1-count node2-count))
(check-expect (count-tree tr1) 1)
(check-expect (count-tree tr2) 2)
(check-expect (count-tree tr3) 4)


; Exercise 6

; A CountOp is one of :
; - leaf-count
; - node1-count
; - node2-count

; A OP is one of :
; - +
; - *
; - CountOp

; Tree OP -> Number
; abstract sum-tree, prod-tree, and count-tree into a single function process-tree
; This function is totally the same as that in exercise 4,
; but we need three more helper function for count-tree
(define (process-tree tr leaf-op node1-op node2-op)
  (cond
    [(leaf? tr) (leaf-op (leaf-num tr))]
    [(node1? tr) (node1-op (node1-num tr)
                           (process-tree (node1-tree tr) leaf-op node1-op node2-op))]
    [(node2? tr) (node2-op (node2-num tr)
                           (process-tree (node2-tree1 tr) leaf-op node1-op node2-op)
                           (process-tree (node2-tree2 tr) leaf-op node1-op node2-op))]))
; Number -> Number
; help to do the count-tree
(define (leaf-count le) 1)
; Number Number -> Number
; help to do the node1-count
(define (node1-count le tr) (+ 1 tr))
; Number Number Number -> Number
; help to do the node2-count
(define (node2-count le tr1 tr2) (+ 1 tr1 tr2))

(check-expect (sum-tree tr3) 9)
(check-expect (prod-tree tr3) 16)
(check-expect (count-tree tr3) 4)


; Exercise 7

; A [Tree X] is one of :
;  - (make-leaf X)
;  - (make-node1 X Tree)
;  - (make-node2 X Tree Tree)

; A Leaf is (make-leaf X)
; A Node1 is (make-node1 X Tree)
; A Node2 is (make-node2 X Tree Tree)


; Exercise 8
; We do not need to change the process-tree function,
; but we need to give the right inputs of operation