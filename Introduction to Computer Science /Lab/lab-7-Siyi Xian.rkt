;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-reader.ss" "lang")((modname |lab-7-Siyi Xian|) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
(require 2htdp/image)
(require 2htdp/universe)

; Exercise 1

; A Triple is structure:
; - (make-triple Number Number Number)
(define-struct triple (a b c))


; Exercise 2
#;
(define (process-triple T)
  (... (triple-a T)
   ... (triple-b T)
   ... (triple-c T) ...))


; Exercise 3

; Triple -> Boolean
; To judge if the triple is in ascending order
(define (sorted-triple T)
  (and (< (triple-a T) (triple-b T))
       (< (triple-b T) (triple-c T))))
(check-expect (sorted-triple (make-triple 1 2 3)) #true)
(check-expect (sorted-triple (make-triple 4 2 3)) #false)


; Exercise 4

; Triple -> Triple
; Sort the given Triple in ascending order
(define (sort-triple T)
  (make-triple (min (triple-a T)
                    (triple-b T)
                    (triple-c T))
               (- (+ (triple-a T)
                     (triple-b T)
                     (triple-c T))
                  (min (triple-a T)
                       (triple-b T)
                       (triple-c T))
                  (max (triple-a T)
                       (triple-b T)
                       (triple-c T)))
               (max (triple-a T)
                    (triple-b T)
                    (triple-c T))))
(check-expect (sort-triple (make-triple 4 2 3)) (make-triple 2 3 4))


;; A ThreeTree is one of:
;; - Number
;; - (make-three ThreeTree ThreeTree ThreeTree)
(define-struct three (left mid right))

; Exercise 5
#;
(define (process-three T)
  (... (three-left T)
   ... (three-mid T)
   ... (three-right T) ...))
(define tree0 3)
(define tree1 (make-three tree0 tree0 tree0))
(define tree2 (make-three tree1 tree1 tree0))


; Exercise 6

; ThreeTree -> Number
; Count the amount of the given ThreeTree
(define (count-tree T)
  (cond
    [(number? T) 1]
    [else (+ (count-tree (three-left T))
             (count-tree (three-mid T))
             (count-tree (three-right T)))]))
(check-expect (count-tree tree2) 7)


; Exercise 7

; ThreeTree -> Number
; Sum all number in the given ThreeTree
(define (sum-tree T)
  (cond
    [(number? T) T]
    [else (+ (sum-tree (three-left T))
             (sum-tree (three-mid T))
             (sum-tree (three-right T)))]))
(check-expect (sum-tree tree2) 21)


; Exercise 8

; ThreeTree -> Number
; Multiplies all number in the given ThreeTree
(define (product-tree T)
  (cond
    [(number? T) T]
    [else (* (product-tree (three-left T))
             (product-tree (three-mid T))
             (product-tree (three-right T)))]))
(check-expect (product-tree tree2) 2187)


; Exercise 9

; ThreeTree -> ThreeTree
; Produce a new ThreeTree which all the number has been reduced 7
(define (decrease-tree T)
  (cond
    [(number? T) (- T 7)]
    [else (make-three (decrease-tree (three-left T))
                      (decrease-tree (three-mid T))
                      (decrease-tree (three-right T)))]))
(check-expect (decrease-tree tree1) (make-three -4 -4 -4))


; Exercise 10

; A ListOfString is one of:
; - empty
; (cons String ListOfString)
#;
(define (process-list ls)
  (cond
    [(empty? ls) ...]
    [else (... (first ls)
           ... (process-list (rest ls))...)]))


; Exercise 11

; ListOfString -> Nature Number
; Sum the total length of given ListOfString
(define (total-string-length ls)
  (cond
    [(empty? ls) 0]
    [else (+ (string-length (first ls))
             (total-string-length (rest ls)))]))
(check-expect (total-string-length (list "Apple" "Banana")) 11)


; Exercise 12

; ListOfString -> Nature Number
; Produce the longest string of the given ListOfString
(define (max-string-length ls)
  (cond
    [(empty? ls) 0]
    [else (max (string-length (first ls))
               (max-string-length (rest ls)))]))
(check-expect (max-string-length (list "Apple" "Banana")) 6)


; Exercise 13

; Nature Number -> string
(define (find-longest ls num)
  (cond
    [(empty? ls) ""]
    [else (if (= (string-length (first ls)) num)
              (first ls)
              (find-longest (rest ls) num))]))
; ListOfString -> String
; Produce the longest string of the given ListOfString
(define (longest-string ls)
  (find-longest ls (max-string-length ls)))
(check-expect (longest-string (list "Apple" "Banana")) "Banana")