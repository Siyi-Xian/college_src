;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-reader.ss" "lang")((modname task-10-05) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
(require 2htdp/image)

; A [Listof X] is one of:
; - empty
; - (cons X [Listof X])

; A LoN is [Listof Number]

; compute : [Listof Number] Number [Number Number -> Number] -> Number
; compute : [Listof String] String [String String -> String] -> String
; compute : [Listof Number] Image [Number Image -> Image] -> Image
; compute : [Listof X] Y [X Y -> Y] -> Y

; apply the given operation to compute all the numbers in the given list
(define (compute lon n op)
  (cond
    [(empty? lon) n]
    [else (op (first lon)
              (compute (rest lon) n op))]))

#;
(define (process-list lox)
  (cond
    [(empty? lox) ...]
    [else (first lox)
          ...
          (process-list (rest lox))]))

(compute (list "a" "b" "c") "" string-append)

; add-all: [Listof Number] -> Number
#;
(define (add-all lon)
  (cond
    [(empty? lon) 0]
    [else (+ (first lon)
             (add-all (rest lon)))]))

; circles : [Listof Number] -> Image
; draw circles of the given sizes arrange horizontally
(define (circles lon)
  (cond
    [(empty? lon) (circle 0 "solid" "red")]
    [else (beside (circle (first lon) "solid" "red")
                  (circles (rest lon)))]))

; combine-circles : Number Image -> Image
; draw a circle with the given radius next to the given image
(define (combine-circles n i)
  (beside (circle n "solid" "red") i))

; circles.v2 : [Listof Number] -> Image
; draw circles of the given sizes arrange horizontally
(define (circles.v2 lon)
  (compute lon (circle 0 "solid" "red") combine-circles)
  #;
  (cond
    [(empty? lon) (circle 0 "solid" "red")]
    [else (combine-circles (first lon)
                           (circles (rest lon)))]))

(check-expect (circles empty) (circle 0 "solid" "red"))
(check-expect (circles (list 10)) (circle 10 "solid" "red"))
(check-expect (circles (list 10 20))
              (beside (circle 10 "solid" "red")
                      (circle 20 "solid" "red")))

(check-expect (circles.v2 empty) (circle 0 "solid" "red"))
(check-expect (circles.v2 (list 10)) (circle 10 "solid" "red"))
(check-expect (circles.v2 (list 10 20))
              (beside (circle 10 "solid" "red")
                      (circle 20 "solid" "red")))

; add-2 : [Listof Number] -> [Listof Number]
; add 2 to every element of the given list
(define (add-2 lon)
  (map add2-one lon))
; add2-one : Number -> Number
(define (add2-one n) (+ 2 n))

(define (say-hi a) (string-append "hi " a))
(map say-hi (list "Sam" "Andy"))

(filter even? (list 1 2 3 4 5 6 7 8 9))

(build-list 10 add1)

(define (c n) (circle n "solid" "red"))
(build-list 10 c)

(define-struct node (n left right))
; A [Treeof X] is one of
; - empty
; - (make-node Number Tree Tree)

; add-all : [Treeof Number] -> Number
; add all the number in the tree
(define (add-all t)
  (cond
    [(empty? t) 0]
    [else (+ (node-n t)
             (add-all (node-left t))
             (add-all (node-right t)))]))

; mult-all : [Treeof Number] -> Number
; multiply all the number in the tree
(define (mult-all t)
  (cond
    [(empty? t) 1]
    [else (* (node-n t)
             (mult-all (node-left t))
             (mult-all (node-right t)))]))