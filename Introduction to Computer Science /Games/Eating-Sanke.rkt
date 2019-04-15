;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-reader.ss" "lang")((modname Eating-Sanke) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
(require 2htdp/image)
(require 2htdp/universe)

; canvas is the background of the game
; -> Image
; (define canvas (empty-scene 600 600))
(define canvas (place-image (overlay (text "Restart" 15 "red")
                                     (rectangle 100 30 "solid" "gray"))
                            535 25
                            (place-image "./backgroud"
                                         300 300
                                         (empty-scene 600 600))))

; candy is the image that snake are going to eat
; -> Image
(define candy (circle 5 "solid" "pink"))
; snake-point is every point of the snake
; -> Image
(define snake-point (circle 5 "solid" "green"))

; Snake is a structure: String ListOfPosn
(define-struct snake (to sa))
; C&S is a structure: Posn Snake
(define-struct c&s (c s l))

; begining point of the snake
; -> Snake
(define begin-snake (make-snake "left"
                                (list (make-posn 305 305)
                                      (make-posn 315 305)
                                      (make-posn 325 305)
                                      (make-posn 335 305)
                                      (make-posn 345 305))))

; Snake -> Image
(define (draw sa)
  (cond
    [(empty? (snake-sa (c&s-s sa))) (place-image candy
                                                 (posn-x (c&s-c sa))
                                                 (posn-y (c&s-c sa))
                                                 (place-image (beside (text "Length: " 14 "white")
                                                                      (text (number->string (c&s-l sa)) 14 "white"))
                                                              50 15
                                                              canvas))]
    [else (place-image snake-point
                       (posn-x (first (snake-sa (c&s-s sa))))
                       (posn-y (first (snake-sa (c&s-s sa))))
                       (draw (make-c&s (c&s-c sa)
                                       (make-snake (snake-to (c&s-s sa))
                                                   (rest (snake-sa (c&s-s sa))))
                                       (c&s-l sa))))]))

; C&S KeyEvent -> C&S
(define (key sa ke)
  (cond
    [(string=? "left" ke) (if (string=? "right" (snake-to (c&s-s sa)))
                              (make-c&s (c&s-c sa) (make-snake "right" (snake-sa (c&s-s sa))) (c&s-l sa))
                              (make-c&s (c&s-c sa) (make-snake "left" (snake-sa (c&s-s sa))) (c&s-l sa)))]
    [(string=? "right" ke) (if (string=? "left" (snake-to (c&s-s sa)))
                               (make-c&s (c&s-c sa) (make-snake "left" (snake-sa (c&s-s sa))) (c&s-l sa))
                               (make-c&s (c&s-c sa) (make-snake "right" (snake-sa (c&s-s sa))) (c&s-l sa)))]
    [(string=? "up" ke) (if (string=? "down" (snake-to (c&s-s sa)))
                            (make-c&s (c&s-c sa) (make-snake "down" (snake-sa (c&s-s sa))) (c&s-l sa))
                              (make-c&s (c&s-c sa) (make-snake "up" (snake-sa (c&s-s sa))) (c&s-l sa)))]
    [(string=? "down" ke) (if (string=? "up" (snake-to (c&s-s sa)))
                              (make-c&s (c&s-c sa) (make-snake "up" (snake-sa (c&s-s sa))) (c&s-l sa))
                              (make-c&s (c&s-c sa) (make-snake "down" (snake-sa (c&s-s sa))) (c&s-l sa)))]
    [else sa]))

; Posn ListOfPosn -> ListOfPosn
(define (change-place p sa)
  (cond
    [(empty? sa) empty]
    [else (cons p (change-place (first sa) (rest sa)))]))

; C&S -> C&S
(define (snake-move sa)
  (cond
    [(string=? "left" (snake-to (c&s-s sa)))
     (make-c&s (c&s-c sa)
               (make-snake "left"
                           (change-place (make-posn (- (posn-x (first (snake-sa (c&s-s sa)))) 10)
                                                    (posn-y (first (snake-sa (c&s-s sa)))))
                                         (snake-sa (c&s-s sa))))
               (c&s-l sa))]
    [(string=? "right" (snake-to (c&s-s sa)))
     (make-c&s (c&s-c sa)
               (make-snake "right"
                           (change-place (make-posn (+ (posn-x (first (snake-sa (c&s-s sa)))) 10)
                                                    (posn-y (first (snake-sa (c&s-s sa)))))
                                         (snake-sa (c&s-s sa))))
               (c&s-l sa))]
    [(string=? "up" (snake-to (c&s-s sa)))
     (make-c&s (c&s-c sa)
               (make-snake "up"
                           (change-place (make-posn (posn-x (first (snake-sa (c&s-s sa))))
                                                    (- (posn-y (first (snake-sa (c&s-s sa)))) 10))
                                         (snake-sa (c&s-s sa))))
               (c&s-l sa))]
    [(string=? "down" (snake-to (c&s-s sa)))
     (make-c&s (c&s-c sa)
               (make-snake "down"
                           (change-place (make-posn (posn-x (first (snake-sa (c&s-s sa))))
                                                    (+ (posn-y (first (snake-sa (c&s-s sa)))) 10))
                                         (snake-sa (c&s-s sa))))
               (c&s-l sa))]))

; Snake -> Snake
(define (add-one sa)
  (cond
    [(string=? "left" (snake-to sa))
     (make-snake "left"
                 (cons (make-posn (- (posn-x (first (snake-sa sa))) 10)
                                  (posn-y (first (snake-sa sa))))
                       (snake-sa sa)))]
    [(string=? "right" (snake-to sa))
     (make-snake "right"
                 (cons (make-posn (+ (posn-x (first (snake-sa sa))) 10)
                                  (posn-y (first (snake-sa sa))))
                       (snake-sa sa)))]
    [(string=? "up" (snake-to sa))
     (make-snake "up"
                 (cons (make-posn (posn-x (first (snake-sa sa)))
                                  (- (posn-y (first (snake-sa sa))) 10))
                       (snake-sa sa)))]
    [(string=? "down" (snake-to sa))
     (make-snake "down"
                 (cons (make-posn (posn-x (first (snake-sa sa)))
                                  (+ (posn-y (first (snake-sa sa))) 10))
                       (snake-sa sa)))]))

; Posn Posn -> Number
(define (distance a b)
  (sqrt (+ (* (- (posn-x a)
                 (posn-x b))
              (- (posn-x a)
                 (posn-x b)))
           (* (- (posn-y a)
                 (posn-y b))
              (- (posn-y a)
                 (posn-y b))))))

; C&S -> C&S
(define (eat-candy sa)
  (if (stop sa)
      sa
      (if (< (distance (c&s-c sa) (first (snake-sa (c&s-s sa)))) 10)
          (make-c&s (make-posn (+ 50 (random 500)) (+ 50 (random 500)))
                    (add-one (c&s-s sa))
                    (+ 1 (c&s-l sa)))
          (snake-move sa))))

; Number Number ListOfPosn -> Boolean
(define (touch-self x y sa)
  (cond
    [(empty? sa) #false]
    [else (or (< (distance (make-posn x y) (first sa)) 10)
              (touch-self x y (rest sa)))]))

; Number Number -> Boolean
(define (wall x y)
  (or (>= 5 x) (<= 595 x)
      (>= 5 y) (<= 595 y)))

; C&S -> Boolean
(define (stop sa)
  (or (touch-self (posn-x (first (snake-sa (c&s-s sa))))
                  (posn-y (first (snake-sa (c&s-s sa))))
                  (rest (snake-sa (c&s-s sa))))
      (wall (posn-x (first (snake-sa (c&s-s sa))))
            (posn-y (first (snake-sa (c&s-s sa)))))))

; C&S -> C&S
(define (restart s x y me)
  (cond
    [(and (< 485 x)
          (> 585 x)
          (< 10 y)
          (> 40 y)
          (string=? "button-down" me)) (make-c&s (make-posn (+ 50 (random 500)) (+ 50 (random 500)))
                                                 (make-snake "left"
                                                             (list (make-posn 505 305)
                                                                   (make-posn 515 305)
                                                                   (make-posn 525 305)
                                                                   (make-posn 535 305)
                                                                   (make-posn 545 305)))
                                                 5)]
    [else s]))

(define game
  (big-bang (make-c&s (make-posn (+ 50 (random 500)) (+ 50 (random 500)))
                      begin-snake
                      5)
            [to-draw draw]
            [on-key key]
            [on-mouse restart]
            [on-tick eat-candy 0.05]
            ;[stop-when stop]
            ))