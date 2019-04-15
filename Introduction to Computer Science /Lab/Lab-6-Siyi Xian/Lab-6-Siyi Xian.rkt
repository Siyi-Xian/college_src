;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-reader.ss" "lang")((modname |Lab-6-Siyi Xian|) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
(require 2htdp/image)
(require 2htdp/universe)
(require 2htdp/batch-io)

; Exercise 1

; A ListOfStrings is one of:
; - empty
; - (cons String ListOfStrings)

(define (process-string s)
  (cond
    [(empty? s) ...]
    [(cons? s) (... (first s) ... (process-string (rest s)))]))


; Exercise 2

; has-word is a function to find is there are same string in the list
; ListOfString String -> Boolean
(define (has-word l s)
  (cond
    [(empty? l) #false]
    [(cons? l) (or (string=? s (first l))
                   (has-word (rest l) s))]))
(check-expect (has-word (cons "hello" (cons "world" empty)) "world") #true)


; Exercise 3

; filelist is all the name of files
; -> Cons
(define filelist
  (cons "thefly.txt"
        (cons "thegerm.txt"
              (cons "theoctopus.txt"
                    (cons "theostrich.txt"
                          (cons "thetermite.txt" empty))))))
(define (input name)
  (cond
    [(empty? name) empty]
    [(cons? name) (cons (read-lines (first name))
                        (input (rest name)))]))

; Exercise 4

; file-has-word is a function to find if there is a same word in the given file
; String String -> Boolean
(define (file-has-word name s)
  (has-word (read-words name) s))
(check-expect (file-has-word "thefly.txt" "The") #true)


; Exercise 5

; search-files is a function to check is there is a same word in the given files
; ListOfString String -> ListOfString
(define (search-files l s)
  (cond
    [(empty? l) empty]
    [(cons? l) (if (file-has-word (first l) s)
                   (cons (first l) (search-files (rest l) s))
                   (search-files (rest l) s))]))
(check-expect (search-files filelist "and")
              (cons "theostrich.txt" (cons "thetermite.txt" empty)))


; Exercise 6

(search-files filelist "one")