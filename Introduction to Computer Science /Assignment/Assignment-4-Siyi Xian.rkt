;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-reader.ss" "lang")((modname |Assignment-4-Siyi Xian|) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
(require 2htdp/image)
(require 2htdp/universe)

; Exercise 1

; tank is a structure that contains location of tank
; x dx: PositiveNumber PositiveNumber
(define-struct tank (x dx))

; fired is a structure that contains ufo, tank, and missile
; ufo, tank, missile: posn, tank, posn
(define-struct fired (ufo tank missile))

; Define a function that output is the image of tank
; -> image
(define tank-image
  (overlay (circle 10 "solid" "yellow")
           (rectangle 40 10 "solid" "yellow")))

; Define a function that output is the image of ufo
; -> image
(define ufo-image
  (rectangle 40 10 "solid" "blue"))

; Define a function that output is the image of missile
; -> image
(define missile-image
  (triangle 10 "solid" "red"))

; canvas is the function that define the background
(define canvas (empty-scene 200 200))

; Define a function to draw tank at the given position and background
; Tank Image -> Image 
(define (tank-render t im)
  (place-image tank-image
               (tank-x t) (- 200 (tank-dx t))
               im))

; Define a function to draw ufo at the given position and background
; UFO Image -> Image 
(define (ufo-render u im)
  (place-image ufo-image
               (posn-x u) (- 200 (posn-y u))
               im))

; Define a function to draw missile at the given position and background
; Missile Image -> Image 
(define (missile-render m im)
  (place-image missile-image
               (posn-x m) (- 200 (posn-y m))
               im))

; s is structure of fired
(define s (make-fired (make-posn 145 165)
                      (make-tank 95 20)
                      (make-posn 95 160)))

; t1 the the function that do tank-render first then ufo-render
; fired -> image
(define (t1 s) (tank-render
                      (fired-tank s)
                      (ufo-render (fired-ufo s)
                                  (missile-render (fired-missile s)
                                                  canvas))))
; t2 the the function that do ufo-render first then tank-render
; fired -> image
(define (t2 s) (ufo-render
                      (fired-ufo s)
                      (tank-render (fired-tank s)
                                   (missile-render (fired-missile s)
                                                   canvas))))

(check-expect (t1 s) (t2 s))


; Exercise 2
; Number number number -> date
(define-struct date (year month day))

(define my-birthday
  (make-date 1998 9 17))

(define when-come-to-us
  (make-date 2016 8 5))

(define first-day-of-college
  (make-date 2016 8 22))

(define her-birthday
  (make-date 1998 5 27))

(define first-day-to-school
  (make-date 2004 9 1))


; Exercise 3
; Define a struct that contains a house number, a street name, and city, and a state
; String string string string -> address
(define-struct address
  (house-number street-name city state))


; Exercise 4
; Define a template that putin an address
; address -> ？
(define (use-address add)
  (
   ...
   (address-house-number add) ...
   ...
   (address-street-name add)...
   ...
   (address-city)...
   ...
   (address-state)...))


; Exercise 5
; even-side is a function that determines whether the house is on the even side or not
; address -> blooean
(define (even-side add)
  (even? (address-house-number add)))

(check-expect (even-side (make-address 3102 "Rose Ave." "Bloomington" "IN")) #true)
(check-expect (even-side (make-address 1257 "Central St." "New York City" "NY")) #false)


; Exercise 6
; small-one is a function that give the output of the smaller house number
; address address -> address
(define (small-one ad1 ad2)
  (cond
   ((< (address-house-number ad1) (address-house-number ad2)) ad1)
   ((> (address-house-number ad1) (address-house-number ad2)) ad2)
   (else "The street number of the first address is equal to that of the second")))

(check-expect
 (small-one (make-address 10047 "Forest Ave." "Champagne" "IL")
            (make-address 2048 "Rose Ave." "Bloomington" "IN"))
 (make-address 2048 "Rose Ave." "Bloomington" "IN"))
(check-expect
 (small-one (make-address 31 "Jordan Ave." "Bloomington" "IN")
            (make-address 227 "1st St." "Indianapolis" "IN"))
 (make-address 31 "Jordan Ave." "Bloomington" "IN"))
(check-expect
 (small-one (make-address 455 "5th St." "San Diago" "CA")
            (make-address 455 "College Ave." "Bloomington" "IN"))
 "The street number of the first address is equal to that of the second")


; Exercise 7
; mailing-address is a function that output is a format of the address
; address -> string
(define (mailing-address add)
   (string-append (number->string (address-house-number add))
                  " "
                  (address-street-name add)
                  ", "
                  (address-city add)
                  ", "
                  (address-state add)))

(check-expect (mailing-address (make-address 150 "Rose Ave." "Bloomington" "IN"))
              "150 Rose Ave., Bloomington, IN")


; Exercise Quadrants
; quadrants is function that gives an image
; PositiveNumber string string string string -> image
(define (quadrants wide color1 color2 color3 color4)
  (above (beside (circle (/ wide 2) "solid" color1)
                  (square wide "solid" color2))
         (beside (ellipse wide (/ wide 2) "solid" color3)
                  (triangle wide "solid" color4))))

(check-expect (quadrants 100 "red" "blue" "green" "purple")
              (above (beside (circle 50 "solid" "red")
                             (square 100 "solid" "blue"))
                     (beside (ellipse 100 50 "solid" "green")
                             (triangle 100 "solid" "purple"))))


; Exercise 8
; draw is a function that darw some word
; string -> word
(define (draw word)
  (place-image (text word 40 "blue")
               300 25
               (empty-scene 600 50)))

(check-expect (draw "HOLLE WORLD!") 
              (place-image 
                           300 25
                           (empty-scene 600 50)))


; Exercise 9 & 10

; type is a function that do on-key
; string key -> string
(define (type word a-key)
  (if (key=? a-key " ")
      (reset word)
      (string-append word a-key)))

; reset function is going to reset the scene when type " "
; string -> empty scene
(define (reset word) "")

; show the word that has been typed
; key -> word
(big-bang
 ""
 (to-draw draw)
 (on-key type))