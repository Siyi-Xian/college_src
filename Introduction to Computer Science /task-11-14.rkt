;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-lambda-reader.ss" "lang")((modname task-11-14) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
(define-struct function (name body))

; eval : BSExpr -> Value
; evaluate the given expression as drracket would
(define (eval e)
  (cond
    [(number? e) e]
    [(boolean? e) e]
    [(empty? e) e]
    [(function? e) e]
    [(function? (first e)) (eval (subst (function-name (first e))
                                        (function-body (first e))
                                        (eval (second e))))]
    [(not (symbol? (first e))) (eval (subst (function-body (eval (first e)))
                                            (function-name (eval (first e)))
                                            (eval (second e))))]
    [(symbol=? (first e) 'lambda) (make-function (second e) (third e))]
    [(symbol=? (first e) 'let) (eval (subst (fourth e) (second e) (third e)))]
    [(symbol=? (first e) '+) (+ (eval (second e)) (eval (third e)))]
    [(symbol=? (first e) '-) (- (eval (second e)) (eval (third e)))]
    [(symbol=? (first e) '*) (* (eval (second e)) (eval (third e)))]
    [(symbol=? (first e) '=) (= (eval (second e)) (eval (third e)))]
    [(symbol=? (first e) 'if) (if (eval (second e)) (eval (third e)) (eval (fourth e)))]
    [(symbol=? (first e) 'cons) (cons (eval (second e)) (eval (third e)))]))

(check-expect (eval 17) 17)
(check-expect (eval #true) #true)
(check-expect (eval '(+ 1 2)) 3)
(check-expect (eval '(+ 1 (+ 3 5))) 9)
(check-expect (eval '(- 1 (+ 3 5))) -7)
(check-expect (eval '(* 1 (+ 3 5))) 8)
(check-expect (eval '(= 1 (+ 3 5))) #false)
(check-expect (eval '(if #true #true (+ #true #false))) #true)
(check-expect (eval '()) '())
(check-expect (eval '(cons 1 ())) (list 1))
(check-expect (eval '(lambda x (* x x))) (make-function 'x '(* x x)))


; subst : BSExpr Symbol Value -> BSExpr
; pre[lace all instance of name with val in e
(define (subst e name val)
  (cond
    [(number? e) e]
    [(boolean? e) e]
    [(empty? e) e]
    [(symbol? e) (cond
                   [(symbol=? e name) val]
                   [else e])]
    [(function? e) e]
    [(function? (first e)) (list (first e) (subst (second e) name val))]
    [(not (symbol? (first e))) (list (subst (first e) name val)
                                     (subst (second e) name val))]
    [(symbol=? (first e) '+) (list '+ (subst (second e) name val) (subst (third e) name val))]
    [(symbol=? (first e) '-) (list '- (subst (second e) name val) (subst (third e) name val))]
    [(symbol=? (first e) '*) (list '* (subst (second e) name val) (subst (third e) name val))]
    [(symbol=? (first e) '=) (list '= (subst (second e) name val) (subst (third e) name val))]
    [(symbol=? (first e) 'if) (list 'if (subst (second e) name val)
                                    (subst (third e) name val)
                                    (subst (fourth e) name val))]
    [(symbol=? (first e) 'cons) (list 'cons (subst (second e) name val) (subst (third e) name val))]
    [(symbol=? (first e) 'let) (list 'let (second e)
                                     (subst (third e) name val)
                                     (subst (fourth e) name val))]
    [else (list (subst (first e) name val)
                (subst (second e) name val))]))

(check-expect (subst 'x 'x 1) 1)
(check-expect (subst '(+ x 1) 'x 1) '(+ 1 1))
(check-expect (subst '(+ y 1) 'y 1) '(+ 1 1))
(check-expect (eval '(let x 1 (+ x x))) 2)
(check-expect (eval '(let x 1 (let y 2 (+ x (* y y))))) 5)
(check-expect (eval '(let z (lambda (x) 1) (z 7))) 1)