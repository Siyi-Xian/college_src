;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-lambda-reader.ss" "lang")((modname |Lab-13-Siyi Xian|) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
; Exercise 1

; A RLE is : (make-rle Number Number)
(define-struct rle (number times))


; Exercise 2

; [ListOf RLE] -> [ListOf Number]
; Decompress the given list of rle
(define (decompress-numbers r)
  (foldr (λ (c result) (append (make-list (rle-times c) (rle-number c)) result)) '() r))
(check-expect (decompress-numbers '()) '())
(check-expect (decompress-numbers (list (make-rle 1 2)
                                        (make-rle 2 3)
                                        (make-rle 3 4)
                                        (make-rle 4 5)
                                        (make-rle 5 6)
                                        (make-rle 6 7)))
              '(1 1 2 2 2 3 3 3 3 4 4 4 4 4 5 5 5 5 5 5 6 6 6 6 6 6 6))


; Exercise 3

; [ListOf Number] -> [ListOf RLE]
; Compress the given list of number
(define (compress-numbers l)
  (local [; [ListOf Number] Number Number [ListOf RLE] -> [listOf RLE]
          ; LoN : the list that has not been compressed
          ; Num : current status of numbers
          ; Times : the appeal times of the number
          ; Ans : the answer
          (define (help-compress-numbers lon num times ans)
            (cond
              [(empty? lon) (reverse (cons (make-rle num times) ans))]
              [else (if (= (first lon) num)
                        (help-compress-numbers (rest lon) num (add1 times) ans)
                        (help-compress-numbers (rest lon) (first lon) 1 (cons (make-rle num times) ans)))]))]
    (cond
      [(empty? l) '()]
      [else (help-compress-numbers l (first l) 0 '())])))
(check-expect (compress-numbers empty) '())
(check-expect (compress-numbers '(1 1 2 2 2 3 3 3 3 4 4 4 4 4 5 5 5 5 5 5 6 6 6 6 6 6 6))
              (list (make-rle 1 2)
                    (make-rle 2 3)
                    (make-rle 3 4)
                    (make-rle 4 5)
                    (make-rle 5 6)
                    (make-rle 6 7)))


; Exercise 4

(check-expect (decompress-numbers (compress-numbers '(1 2 3 3 2 1))) '(1 2 3 3 2 1))
(check-expect (decompress-numbers (compress-numbers '(1 1 1 1 1 1))) '(1 1 1 1 1 1))
(check-expect (decompress-numbers (compress-numbers '(1 2 3 4 5 6))) '(1 2 3 4 5 6))
