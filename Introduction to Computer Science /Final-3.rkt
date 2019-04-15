;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-lambda-reader.ss" "lang")((modname Final-3) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
(define-struct tree (name spouse children))

(define grandson (make-tree "Bob" #false empty))
(define son      (make-tree "Tom" #true (list grandson)))
(define father   (make-tree "Jay" #true (list son)))

(define (married-people t)
  (cond
    [(empty? t) 0]
    [else (local [(define check-children
                    (foldr (λ (x r)
                             (+ (married-people x) r)) 0
                           (tree-children t)))]
            (if (tree-spouse t)
                (add1 check-children)
                check-children))]))
(check-expect (married-people empty) 0)
(check-expect (married-people father) 2)