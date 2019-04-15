;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-reader.ss" "lang")((modname task-9.21) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
(require 2htdp/image)
(require 2htdp/universe)

; transform-image : image -> image
; transform the image to a ListOfColor and back
(define (transform-image img)
  (color-list->bitmap (image->color-list) (image-width img) (image-height img)))


; A ListOfColor is one of
; - empty
; - (cons color ListOfColor)

; mainpulate-image : ListOfColor -> ListOfColor
; change all the colors in the given list
(define (mainpulate-image cl)
  (cond [(empty? cl) empty]
        [else (cons (manip-color (first cl))
                    (mainpulate-image (rest cl)))]))
(check-expect (mainpulate-image (cons (make-color 0 10 10 0) empty))
                                (cons (manip-color (make-color 10 10 10 0)) empty))


; manip-color : color -> color
; transform the given color
(define (manip-color c)
  (make-color (min 255 (+ 10 (color-red c)))
              (min 255 (+ 10 (color-green c)))
              (min 255 (+ 10 (color-blue c)))
              (min 255 (+ 10 (color-alpha c)))))
(check-expect (manip-color (make-color 255 0 0 255))
              (make-color 255 10 10 255))