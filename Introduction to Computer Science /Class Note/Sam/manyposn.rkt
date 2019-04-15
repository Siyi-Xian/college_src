;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-reader.ss" "lang")((modname many-posn) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
(require 2htdp/image)
(require 2htdp/universe)
(define-struct many (one more))
;; A ManyPosn is one of
;; - #false
;; - (make-many Posn ManyPosn)
(define no-posns #false)
(define one-posn (make-many (make-posn 5 5) #false))

;; draw-many-posns : ManyPosn -> Image
;; draw all the posns as circles
(define (draw-many-posns mp)
  (cond [(false? mp) (empty-scene 500 500)]
        [(many? mp) (draw-posn (many-one mp)
                               (draw-many-posns (many-more mp)))]))

(check-expect (draw-many-posns #false) (empty-scene 500 500))
(check-expect (draw-many-posns one-posn)
              (place-image (circle 10 "outline" "black")
                           5 5
                           (empty-scene 500 500)))

;; draw-posn : Posn Image -> Image
;; draw a single posn on the given image
(define (draw-posn p im)
  (place-image
   (circle 10 "outline" "black")
   (posn-x p) (posn-y p) im))
(check-expect (draw-posn (make-posn 5 5)
                         (empty-scene 500 500))
              (place-image
               (circle 10 "outline" "black")
               5 5
               (empty-scene 500 500)))

;; mouse : ManyPosn Number Number MouseEvent -> ManyPosn
;; add the given x & y coords to the collection of posns
(define (mouse mp x y me)
  (make-many (make-posn x y) mp))

(check-expect (mouse #false 5 5 "move")
              (make-many (make-posn 5 5) #false))


(big-bang #false
          [to-draw draw-many-posns]
          [on-mouse mouse])

