;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-reader.ss" "lang")((modname |Assignment-7-Siyi Xian|) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
(require 2htdp/image)
(require 2htdp/universe)
(require 2htdp/batch-io)

; Exercise 1

; A Frequency is a structure:
; - (make-frequency String Number)
(define-struct frequency (str num))


; Exercise 2

; A ListOfString is one of:
; - empty
; - (cons String ListOfString)

; A ListOfFrequency is one of:
; - empty
; - (cons Frequency ListOfFrequency)


; Exercise 3

; count-word is a function to add 1 if there are same string
; ListOfFrequency String -> ListOfFrequency
(define (count-word l s)
  (cond
    [(empty? l) (cons (make-frequency s 1) empty)]
    [else (if (string=? (frequency-str (first l)) s)
              (cons (make-frequency (frequency-str (first l))
                                    (+ 1 (frequency-num (first l))))
                    (rest l))
              (cons (first l) (count-word (rest l) s)))]))

(check-expect (count-word (list (make-frequency "a" 1)) "a")
              (list (make-frequency "a" 2)))

; Exercise 4

; cpunt-all-words is a function to count all the same words
; ListOfString -> ListOfFrequency
(define (count-all-words l)
  (cond
    [(empty? l) empty]
    [(cons? l) (count-word (count-all-words (rest l)) (first l))]))
(check-expect (count-all-words (list "a" "a" "b"))
              (list (make-frequency "b" 1) (make-frequency "a" 2)))

; Exercise 5

; count-all is a function to read all the text and get the number of same words
; -> ListOfFrequency
(define count-all
  (count-all-words (read-words "Hamlet.txt")))


; Exercise 6

; get-100 is a function to print all the words that show up over 100 times
; -> ListOfFrequency
(define (get-100 l)
  (cond
    [(empty? l) empty]
    [(cons? l) (if (< 100 (frequency-num (first l)))
                   (cons (first l) (get-100 (rest l)))
                   (get-100 (rest l)))]))
(get-100 count-all)



; Family Tree Questions:

; A Child is a structure: 
;   (make-child Child Child String Number String)

(define-struct child (father mother name date eyes))
(define-struct no-parent [])

; A FT (short for family tree) is one of: 
; – (make-no-parent)
; – (make-child FT FT String N String)

(define NP (make-no-parent))
; A FT is one of: 
; – NP
; – (make-child FT FT String N String)

; Oldest Generation:
(define Carl (make-child NP NP "Carl" 1926 "green"))
(define Bettina (make-child NP NP "Bettina" 1926 "green"))
; Middle Generation:
(define Adam (make-child Carl Bettina "Adam" 1950 "hazel"))
(define Dave (make-child Carl Bettina "Dave" 1955 "black"))
(define Eva (make-child Carl Bettina "Eva" 1965 "blue"))
(define Fred (make-child NP NP "Fred" 1966 "pink"))
; Youngest Generation: 
(define Gustav (make-child Fred Eva "Gustav" 1988 "brown"))


; Exercise 310

; count-persons is a function to count all people on the family tree
; FT -> Number
(define (count-persons f)
  (if (no-parent? f)
      0
      (+ 1 (count-persons (child-father f)) (count-persons (child-mother f)))))
(check-expect (count-persons Gustav) 5)


; Exercise 311

; count-years is a function to count total yaers of the family tree
; FT -> Number
(define (count-years f)
  (if (no-parent? f)
      0
      (+ (child-date f) (count-years (child-father f)) (count-years (child-mother f)))))

; average-age is a fuction to count the avarage age of the family tree
; FT -> Number
(define (average-age f c)
  (- c (/ (count-years f)
          (count-persons f))))
(check-expect (average-age Gustav 2016) 61.8)


; Exercise 312

; eye-colors is a function to print color of eyes in the family tree
; FT -> List
(define (eye-colors f)
  (if (no-parent? f)
      empty
      (append (list (child-eyes f))
              (eye-colors (child-father f))
              (eye-colors (child-mother f)))))
(check-expect (eye-colors Gustav)
              (list "brown" "pink" "blue" "green" "green"))


; Exercise 313

; My firend's answer will never return ture.

; blue-eyed-ancestor is a function to konw if there are some blue eye ancestor
; FT -> Boolean
(define (blue-eyed-ancestor f)
  (if (no-parent? f)
      #false
      (or (if (no-parent? (child-father f))
              #false
              (string=? "blue" (child-eyes (child-father f))))
          (if (no-parent? (child-mother f))
              #false
              (string=? "blue" (child-eyes (child-mother f))))
          (blue-eyed-ancestor (child-father f))
          (blue-eyed-ancestor (child-mother f)))))


(check-expect (blue-eyed-ancestor Eva) #false)
(check-expect (blue-eyed-ancestor Gustav) #true)