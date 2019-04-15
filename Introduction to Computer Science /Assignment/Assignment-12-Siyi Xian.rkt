;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-lambda-reader.ss" "lang")((modname |Assignment-12-Siyi Xian|) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
; Exercise 1

; String -> String
; Recode the given DNA line by producing continuous same letter
(define (rle-encode dna)
  (local [; [ListOf Char] Char Char Number
          ; DNA : the rest of dna line that need to be re-code
          ; re-DNA : the list of dna that has already been recode
          ; current : the current coutinuous dna
          ; num : the number of the current dna character
          (define (help-rle-encode DNA re-DNA current num)
            (cond
              [(empty? DNA) (string-append re-DNA (string current) (number->string num))]
              [else (if (char=? (first DNA) current)
                        (help-rle-encode (rest DNA) re-DNA current (add1 num))
                        (help-rle-encode (rest DNA)
                                         (string-append re-DNA (string current) (number->string num))
                                         (first DNA) 1))]))]
    (let [(d (string->list dna))]
      (cond
        [(empty? d) ""]
        [else (help-rle-encode (rest d) "" (first d) 1)]))))
(check-expect (rle-encode "AAGCCCTTAAAAAAAAAA") "A2G1C3T2A10")
(check-expect (rle-encode "") "")


; Exercise 2

; String -> String
; Decode the given DNA line
(define (rle-decode dna)
  (local [; [ListOf Char] -> [ListOf Char]
          ; take the first number of the given list of characters
          (define (take l)
            (cond
              [(or (empty? l) (char<? #\9 (first l))) empty]
              [else (cons (first l) (take (rest l)))]))
          ; [ListOf Char] -> [ListOf Char]
          ; drop the first number of the given list of characters
          (define (drop l)
            (cond
              [(or (empty? l) (char<? #\9 (first l))) l]
              [else (drop (rest l))]))
          ; [ListOf Char] -> String
          (define (help-rle-decode DNA)
            (cond
              [(empty? DNA) ""]
              [else (string-append (make-string (string->number (list->string (take (rest DNA))))
                                                (first DNA))
                                   (help-rle-decode (drop (rest DNA))))]))]
    (help-rle-decode (string->list dna))))
(check-expect (rle-decode "A2G1C3T2A10") "AAGCCCTTAAAAAAAAAA")
(check-expect (rle-decode "") "")


; Exercise 3
; When the amount of the continue same number is bigger than two,
; this encoding way will be more efficiency. For example,
; "A3" is more efficiency than "AAA".
; Otherwise, when the amount of the continue same number is less than two,
; this encoding way is less efficiency. For example, "A" is more efficiency
; than "A1".
; When it is "AA", "CC", "GG", or "TT", it has the same efficiency with
; "A2", "C2", "G2", or "T2".


; Exercise 4

; [ListOf Number] -> Number
; cout how many times the '>=' is applied
(define (sort> l)
  (local [; A SwapTimes is : (make-swap-times Number Number)
          (define-struct swap-times (num times))
          ; [ListOf SwapTimes] -> [ListOf SwapTimes]
          ; produces a sorted version of l
          (define (make-sort> l)
            (cond
              [(empty? l) '()]
              [(cons? l) (insert (first l) (make-sort> (rest l)))]))
          
          ; SwapTimes [ListOf SwapTimes] -> [ListOf SwapTimes]
          ; inserts n into the sorted list of numbers l 
          (define (insert n l)
            (cond
              [(empty? l) (cons n '())]
              [else (if (>= (swap-times-num n) (swap-times-num (first l)))
                        (cons (make-swap-times (swap-times-num n)
                                               (add1 (swap-times-times n))) l)
                        (cons (make-swap-times (swap-times-num (first l))
                                               (add1 (swap-times-times (first l))))
                              (insert n (rest l))))]))]
    (foldr (λ (x y) (+ (swap-times-times x) y)) 0
           (make-sort> (map (λ (x) (make-swap-times x 0)) l)))))
(check-expect (sort> '(4 3 2 1)) 3)
(check-expect (sort> '(1 2 3 4)) 6)
(check-expect (sort> '()) 0)
(check-expect (sort> '(1)) 0)
(check-expect (sort> '(1 2)) 1)
(check-expect (sort> '(2 1)) 1)


; Book Exercises

; Exercise 472

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

; Node Node Graph -> [Maybe Path]
; finds a path from origination to destination in G
; if there is no path, the function produces #false
(define (find-path origination destination G)
  (cond
    [(symbol=? origination destination) (list destination)]
    [else (local [(define next (neighbors origination G))
                  (define candidate
                    (find-path/list next destination G))]
            (cond
              [(boolean? candidate) #false]
              [else (cons origination candidate)]))]))

; Node Graph -> [ListOf Node]
; find the nighbors of the given origination
(define (neighbors origination G)
  (foldr (λ (current result)
           (if (symbol=? (first current) origination)
               (rest current) result)) empty G))
(check-expect (neighbors 'A sample-graph) '(B E))
(check-expect (neighbors 'D sample-graph) '())

; [List-of Node] Node Graph -> [Maybe Path]
; finds a path from some node on lo-Os to D
; if there is no path, the function produces #false
(define (find-path/list lo-Os D G)
  (cond
    [(empty? lo-Os) #false]
    [else (local [(define candidate
                    (find-path (first lo-Os) D G))]
            (cond
              [(boolean? candidate)
               (find-path/list (rest lo-Os) D G)]
              [else candidate]))]))

(check-expect (find-path 'C 'D sample-graph) '(C D))
(check-member-of (find-path 'E 'D sample-graph) '(E F D) '(E C D))
(check-expect (find-path 'C 'G sample-graph) #false)

; Graph -> Boolean
; determines whether there is a path between any pair of nodes in the given graph
(check-expect (test-on-all-nodes '((A) (B) (C))) #false)
(check-expect (test-on-all-nodes sample-graph) #true)
(define (test-on-all-nodes G)
  (foldr (λ (x r1)
           (or (foldr (λ (y r2)
                        (if (symbol=? (first x) (first y)) r2
                            (or (or (not (false? (find-path (first x) (first y) G)))
                                    (not (false? (find-path (first y) (first x) G))))
                                r2))) #false G)
               r1)) #false G))
; this version of this problem is much easier
; We know that if there is a path between two points,
; there must be a neighbor of the given start point.
; So the length of this list must larger that two.
(define (test-on-all-nodes.v2 G)
  (foldr (λ (c r) (or (< 1 (length c)) r)) #false G))
(check-expect (test-on-all-nodes.v2 '((A) (B) (C))) #false)
(check-expect (test-on-all-nodes.v2 sample-graph) #true)

; Exercise 474

; Node Node Graph -> [Maybe Path]
; finds a path from origination to destination in G
; if there is no path, the function produces #false
(define (find-path.v2 origination destination G)
  (local [; -> [ListOf Node]
          ; find the nighbors of the given origination
          (define neighbors.v2
            (foldr (λ (current result)
                     (if (symbol=? (first current) origination)
                         (rest current) result)) empty G))
          ; [List-of Node] -> [Maybe Path]
          ; finds a path from some node on lo-Os to D
          ; if there is no path, the function produces #false
          (define (find-path/list.v2 lo-Os)
            (cond
              [(empty? lo-Os) #false]
              [else (local [(define candidates
                              (find-path.v2 (first lo-Os) destination G))]
                      (cond
                        [(boolean? candidates)
                         (find-path/list.v2 (rest lo-Os))]
                        [else candidates]))]))
          ; -> [Maybe Path]
          ; Find one accessible path to go to the destination
          ; from one of the following point of the origination point
          ; Help to reduce the time of running
          (define candidate
            (find-path/list.v2 neighbors.v2))]
    (cond
      [(symbol=? origination destination) (list destination)]
      [(boolean? candidate) #false]
      [else (cons origination candidate)])))
(check-expect (find-path.v2 'C 'D sample-graph) '(C D))
(check-member-of (find-path.v2 'E 'D sample-graph) '(E F D) '(E C D))
(check-expect (find-path.v2 'C 'G sample-graph) #false)