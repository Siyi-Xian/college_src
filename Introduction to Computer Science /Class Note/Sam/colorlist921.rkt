;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-reader.ss" "lang")((modname color-list-9-21) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
(require 2htdp/image)



(define-struct strings (one some))
;; A SomeStrings is one of:
;; - #false
;; - (make-strings String SomeStrings)
#;
;; process-somestrings : SomeStrings -> ??
(define (process-somestrings ss)
  (cond [(false? ss) ...]
        [(strings? ss) (strings-one ss)
                       (process-somestrings (strings-some ss))]))

(define-struct posns (this-one the-others))
;; A LotOfPosns is one of:
;; - "no posns here"
;; - (make-posns Posn LotOfPosns)


;; A ListOfNumbers is one of:
;; - empty
;; - (cons Number ListOfNumbers)
#;
;; process-lon : ListOfNumbers -> ???
(define (process-lon lon)
  (cond [(empty? lon) ...]
        [(cons? lon) (first lon)
                     (process-lon (rest lon))]))

;; sum : ListOfNumbers -> Number
;; add together all the numbers in the given list
(define (sum lon)
  (cond [(empty? lon) 0]
        [(cons? lon) (+ (first lon)
                        (sum (rest lon)))]))
(check-expect (sum empty) 0)
(check-expect (sum (cons 1 (cons 2 (cons 3 empty)))) 6)

;; add5toall : ListOfNumbers -> ListOfNumbers
;; add 5 to every element of the list
(define (add5toall lon)
  (cond [(empty? lon) empty]
        [(cons? lon) (cons (+ 5 (first lon))
                           (add5toall (rest lon)))]))
(check-expect (add5toall empty) empty)
(check-expect (add5toall (cons 10 (cons 12 empty)))
              (cons 15 (cons 17 empty)))

;; transform-image : Image -> Image
;; transform the image to a ListOfColor and back
(define (transform-image img)
  (color-list->bitmap (manipulate-image (image->color-list img))
                      (image-width img) (image-height img)))

              

;; A ListOfColor is one of:
;; - empty
;; - (cons Color ListOfColor) 

;; manipulate-image : ListOfColor -> ListOfColor
;; change all the colors in the given list
(define (manipulate-image cl)
  (cond [(empty? cl) empty]
        [else (cons (manip-color (first cl))
                    (manipulate-image (rest cl)))]))
(check-expect (manipulate-image empty) empty)
(check-expect (manipulate-image (cons (make-color 10 10 10 0) empty))
              (cons (manip-color (make-color 10 10 10 0)) empty))


;; manip-color : Color -> Color
;; transform the given color
(define (manip-color c)
  (cond
    [(not (zero? (random 3))) (make-color (random 256)
                                          (random 256)
                                          (random 256)
                                          255)]
    [else c]))



(check-expect (manip-color (make-color 255 0 0 255))
              (make-color 255 10 10 255))

(transform-image (square 20 "outline" "red"))

;; A ListOfStrings is one of:
;; - empty
;; - (cons String ListOfStrings)
#;
;; process-los : ListOfStrings -> ???
(define (process-los los)
  (cond [(empty? los) ...]
        [(cons? los) (first los)
                     (process-los (rest los))]))







