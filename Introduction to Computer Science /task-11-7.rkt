;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-lambda-reader.ss" "lang")((modname task-11-7) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
(require 2htdp/image)

(define MinLen 20)
(define MaxLen 200)

(define (iplot f x y len)
  (local [(define v1 (if (>= (f x y) 0) 1 0))
          (define v2 (if (>= (f (+ x len) y) 0) 1 0))
          (define v3 (if (>= (f (+ x len) (+ len y))0) 1 0))
          (define v4 (if (>= (f x (+ len y)) 0) 1 0))

          (define nzero (+ v1 v2 v3 v4))]
    (cond
      [(and (or (= nzero 0) (= nzero 4))
            (<= len MaxLen)) (square len "outline" "LightGreen")]
      [(<= len MinLen) (circle (/ len 2) "solid" "black")]
      [else (local [(define half (/ len 2))]
              (above (beside (iplot f x (+ half y) half)
                             (iplot f (+ half x) (+ half y) half))
                     (beside (iplot f x y half)
                             (iplot f (+ half x) y half))))])))

(define (icircle x y) (- (+ (* x x) (* y y)) (* 200 200)))

(iplot icircle -250 -250 500)
