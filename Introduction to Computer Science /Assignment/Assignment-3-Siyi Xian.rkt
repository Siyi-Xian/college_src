;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-reader.ss" "lang")((modname |Assignment-3-Siyi Xian|) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
; extract-years
;
; String -> Number
; extracts the number of hours in the time representation time
; given: "09/02/2013" expected: 2013
(define (extract-years date)
  (string->number (substring date 6 10)))
 
(check-expect (extract-years "09/02/2013") 2013)


; extract-months
;
; String -> Number
; extracts the number of months in the date representation time
; given: "05/12/2015" expected: 5
(define (extract-months date)
  (string->number (substring date 0 2)))

(check-expect (extract-months "05/12/2015") 5)


; extract-days
;
; String -> Number
; extracts the number of days in the date representation time
; given: "05/12/2015" expected: 12
(define (extract-days date)
  (string->number (substring date 3 5)))

(check-expect (extract-days "05/12/2015") 12)


; months->days
;
; Number -> Number
; converts a month number into the total number of days up to this month
; given: 1 expected: 0
; given: 2 expected: 31
; given: 3 expected: 31+28
(define (months->days months)
  (cond
    [(= months 1) 0]
    [(= months 2) 31]
    [(= months 3) (+ 31 28)]
    [(= months 4) (+ 31 28 31)]
    [(= months 5) (+ 31 28 31 30)]
    [(= months 6) (+ 31 28 31 30 31)]
    [(= months 7) (+ 31 28 31 30 31 30)]
    [(= months 8) (+ 31 28 31 30 31 30 31)]
    [(= months 9) (+ 31 28 31 30 31 30 31 31)]
    [(= months 10) (+ 31 28 31 30 31 30 31 31 30)]
    [(= months 11) (+ 31 28 31 30 31 30 31 31 30 31)]
    [(= months 12) (+ 31 28 31 30 31 30 31 31 30 31 30)]))

(check-expect (months->days 1) 0)
(check-expect (months->days 2) 31)
(check-expect (months->days 3) (+ 31 28))



; years-months-days->days
;
; Number Number Number -> Number
; Converts a tuple of years, months, days to the total number of days in this date
; interval
(define (years-months-days->days years months days)
  (+ (* years 365) (months->days months) days))

(check-expect (years-months-days->days 1900 1 1) (+ (* 365 1900) 0 1))


; date->days
;
; String -> Number
; converts a date representation into an equivalent number of days
(define (date->days date)
  (years-months-days->days
   (extract-years date)
   (extract-months date)
   (extract-days date)))

(check-expect (date->days "09/17/1998") (+ (* 1998 365) (+ 31 28 31 30 31 30 31 31) 17))



; days->span
;
; Number -> String
; converts a number of days into date span representation
; given: 3 expected: "0 years and 3 days."
; given: 366 expected: "1 years and 1 days."
(define (days->span days)
  (string-append
   (number->string (quotient days 365))
   " years and "
   (number->string (remainder days 365))
   " days."))

(check-expect (days->span 3) "0 years and 3 days.")
(check-expect (days->span 366) "1 years and 1 days.")

; date-between
;
; String String -> String
; calculates the difference between d1 and d2 (in years, months, days)
; given: "09/02/2013" "10/10/2015"
; expected: "2 years and 38 days."
(define (time-between d1 d2)
  (days->span (abs (- (date->days d2) (date->days d1)))))
 
(check-expect (time-between "09/02/2013" "10/10/2015") "2 years and 38 days.")