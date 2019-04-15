;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-reader.ss" "lang")((modname gradebook) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))


(define-struct two-num (first second))
;; A Gradebook is one of
;; - Number
;; - (make-two-num Number Gradebook)

(define c211 (make-two-num 100 (make-two-num 98 (make-two-num 70 95))))

;; sum-grades : Gradebook -> Number
;; total all the grades in the gradebook
(define (sum-grades gb)
  (cond [(number? gb) gb]
        [(two-num? gb) (+ (two-num-first gb)
                          (sum-grades (two-num-second gb)))]))
(check-expect (sum-grades 3) 3)
(check-expect (sum-grades c211) 363)

;; count-grades : Gradebook -> Number
;; count how many grades are in the given gradebook
(define (count-grades gb)
  (cond [(number? gb) 1]
        [(two-num? gb) (+ 1 (count-grades (two-num-second gb)))]))

(check-expect (count-grades 3) 1)
(check-expect (count-grades 100) 1)
(check-expect (count-grades c211) 4)


