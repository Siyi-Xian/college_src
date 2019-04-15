;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-reader.ss" "lang")((modname task-10-07-review) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
(require 2htdp/image)
(require 2htdp/universe)

;; A Dict is one of:
;;  - (make-empty-dict)
;;  - (make-item-dict Number String Dict)
(define-struct empty-dict ())
(define-struct item-dict (key val rest))
;; has-key? : Dict Number -> Boolean
;; Does this dict. have the given key?
(define (has-key? d k)
  (cond [(empty-dict? d) false]
        [else (or (= k (item-dict-key d))
                  (has-key? (item-dict-rest d) k))]))
;; lookup : Dict Number -> String
;; Produce the value associated with the given key in this dict.
;; Assume: the key exists in this dict. This means we don’t have to
;; worry about empty dicts.
(define (lookup d k)
  (cond [(= k (item-dict-key d)) (item-dict-val d)]
        [else (lookup (item-dict-rest d) k)]))

; 1.
(define dict-0 make-empty-dict)
(define dict-1 (make-item-dict 1 "Apple" dict-0))
(define dict-2 (make-item-dict 2 "Ball" dict-1))

; 2.
; Dict Number String -> Dict
; add a new item in to dictionary from the given String and Number
(define (set dict num str)
  (make-item-dict num str dict))

; 3.
(check-expect (lookup (set dict-0 1 "Fred") 1) "Fred")

; 4.
#;
(check-expect (lookup (set (set dict-0 1 "Fred") 1 "Wilam") 1) "Fred")


(filter odd? (list 1 2 3 4 5 6 7 8 9))

(map add1 (list 1 2 3 4 5 6 7 8 9))

(define (combine-circles n i)
  (beside (circle n "solid" "red") i))
(foldr combine-circles (circle 0 "solid" "red") (list 10 20 30 40 50 40 30 20 10))
(remove 1 (list 1 2 3 4 5 6))
(remove-all 1 (list 1 2 1 2 1 2 1 2))



; A Gifts is one of:
; - Box-shaped
; - Tube-shaped

(define-struct box-shaped (width length height))
(define-struct tube-shaped (length diameter))

; Constructor
; predicate
; Selectors / Accessors