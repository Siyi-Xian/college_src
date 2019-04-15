;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-reader.ss" "lang")((modname random-posns) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
(define-struct many (posn manyposn))
(require 2htdp/image)
(require 2htdp/universe)
;; A ManyPosn is one of:
;; - #false
;; - (make-many Posn ManyPosn)

;; draw : ManyPosn -> Image
;; draw all the given posns in a 500x500 scene
(define (draw mp)
  (cond [(false? mp) (empty-scene 500 500)]
        [else (place-image
               (triangle 10 "solid" "blue")
               (posn-x (many-posn mp)) (posn-y (many-posn mp))
               (draw (many-manyposn mp)))]))
(check-expect (draw #false) (empty-scene 500 500))
(check-expect (draw (make-many (make-posn 10 10)
                               #false))
              (place-image
               (triangle 10 "solid" "blue")
               10 10
               (empty-scene 500 500)))

;; add-random-posn : ManyPosn -> ManyPosn
;; add a new posn at a random location

(define (add-random-posn mp)
  (make-many (make-posn (random 500) (random 500))
             mp))

(check-random (add-random-posn #false)
              (make-many (make-posn (random 500) (random 500)) #false))

;; key : World KeyEvent -> World
;; remove all the posns when a key is pressed
(define (key mp ke)
  #false)
(check-expect (key #false "h") #false)
(check-expect (key (make-many (make-posn 423 53) #false) "down")
              #false)

;; A World is one of
;; - ManyPosn
(big-bang #false
          [to-draw draw]
          [on-key key]
          [on-tick add-random-posn])



;; draw-posn : Posn -> Image
;; produce a triangle
(define (draw-posn p) (triangle 10 "solid" "blue"))
(check-expect (draw-posn (make-posn 10 10)) (triangle 10 "solid" "blue"))

