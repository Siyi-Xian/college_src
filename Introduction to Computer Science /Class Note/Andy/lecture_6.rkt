;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-reader.ss" "lang")((modname lecture_6) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f ())))

; Lecture 6

; Review Of Structures
; --------------------

; the usual includes
(require 2htdp/image)
(require 2htdp/universe)

; define a structure named student
(define-struct student (name class gpa))

;; The data definition -- what types are the struct fields
;; A Student is a structure:
;;  (make-student String Number Number)
(define bob (make-student "Bob" 1 3.2))
(define alice (make-student "Alice" 2 3.5))

;; Template for processing structures
;; ----------------------------------


; All functions that accepts a certain struct has things
; in common. 
; * Functions that accepts a struct will have to acess the 
; struct fields -- make use of the accessor functions. 

(define (process-student s)
  (
  ...
  (student-name s) (student-class s) (student-gpa s)
  ...
  )
  )

;; Start with a template (common behavior), then fill in the pieces

;; greet : Student -> String
;; Produce a greeting for the given student's name
(define (greet s)
  (string-append "What's up, " (student-name s) "?"))

(check-expect (greet bob) "What's up, Bob?")
(check-expect (greet alice) "What's up, Alice?")


; Introduction to Big Bang
; ------------------------

; * Making a trafic light *

;(big-bang "green"
;          [to-draw draw-light]
;          [on-tick change-color .5])


;; *to-draw - a function which accepts the world state and draws something
;;            according to the world state
;; Signature of the to-draw function: 
;; (define (my-to-draw variable-of-type-world-state) ...)
;; World-State-Type -> Image

;; *on-tick - a function wich accepts a variable of type world state, 
;;            and returns the next state
;; (define (my-tick variable-of-type-world-state) ... )
;; World-State-Type -> World-State-Type

;; A TrafficLight is one of:
;; - "red"
;; - "green"
;; - "yellow"

;; The 'world state' is a string. 

;; This will advance the world state from the current state to the next state. 

;; change-color : TrafficLight -> TrafficLight
;; changes the given color to the next color
(define (change-color tl)
  (cond [(string=? "red" tl) "green"]
        [(string=? "green" tl) "yellow"]
        [(string=? "yellow" tl) "red"]))

(check-expect (change-color "red") "green")
(check-expect (change-color "yellow") "red")
(check-expect (change-color "green") "yellow")

; Draw a red light
(define red-light 
  (underlay/xy (underlay/xy (overlay/xy (circle 100 "solid" "red") 0 0 (empty-scene 200 600))
                            0 200
                            (circle 100 "outline" "yellow"))
               0 400
               (circle 100 "outline" "green")))

; draw a green light
(define green-light
  (underlay/xy (underlay/xy (overlay/xy (circle 100 "outline" "red") 0 0 (empty-scene 200 600))
                            0 200
                            (circle 100 "outline" "yellow"))
               0 400
               (circle 100 "solid" "green")))

; draw a yellow light
(define yellow-light
  (underlay/xy (underlay/xy (overlay/xy (circle 100 "outline" "red") 0 0 (empty-scene 200 600))
                            0 200
                            (circle 100 "solid" "yellow"))
               0 400
               (circle 100 "outline" "green")))

;; Draws the appropriate image for the current world state.
; remember, the worlds state now is only a string. 

;; draw-light : TrafficLight -> Image
;; draws the given traffic light
(define (draw-light tl)
  (cond [(string=? "green" tl) green-light]
        [(string=? "red" tl) red-light]
        [(string=? "yellow" tl) yellow-light]))

(check-expect (draw-light "red") red-light)
(check-expect (draw-light "yellow") yellow-light)
(check-expect (draw-light "green") green-light)

;; Use big bang to automatically advance the world state to the next state. 

;; *** NOTE ****
;; to-draw, on-tick, on-key, or stop-when may be called in any order, 
;; all of them must be written in a safe way, in that any function which 
;; returns an altered world state must return a type which is compatible with 
;; all of the specified handlers. 

;(big-bang "green"
;          [to-draw draw-light]
;          [on-tick change-color .5])


;; Responding to key presses
;; -------------------------

; What if we want to manually advance to the next state?
; can respond to key-press events. 

; Want to modify the so  that when any key on the keyboard is pressed it will 
; advance to the next state. 

; Define the on-key event handler
; _______________________________

;; *on-key - a function wich accepts a variable of type world state, and a 
;;           keyboard character, and returns a new world state 
;; (define (my-on-key variable-of-type-world-state string) ... )
;; World-State-Type String -> World-State-Type

(define (my-on-key state key)
  (change-color state))

;(big-bang "green"
;          [to-draw draw-light]
;          [on-key my-on-key])

;; Lets say we want to explicitly change the color for a given key press

(define (my-on-key-1 state key)
  (cond
    ((string=? key "g") "green")
    ((string=? key "r") "red")
    ((string=? key "y") "yellow")
    (else (change-color state))))
    

;; Big Bang with structures
;; ------------------------

(define-struct point (x y))
;; A Point is a structure:
;;  (make-point Number Number)

(define origin (make-point 0 0))

;; draw : Point -> Image
;; draw the given point as a red circle
(define (draw p)
  (place-image (circle 10 "solid" "red")
               (point-x p) (point-y p)
               (empty-scene 400 400)))

(check-expect (draw origin)
              (place-image (circle 10 "solid" "red")
                           0 0
                           (empty-scene 400 400)))

;; move : Point -> Point
;; move the point 10 in both directions
(define (move p)
  (make-point (+ 10 (point-x p)) (+ 10 (point-y p))))

(check-expect (move origin) (make-point 10 10))
(check-expect (move (make-point 100 100))
              (make-point 110 110))

;; A World is a Point
;(big-bang origin
;          [on-tick move]
;          [to-draw draw])


;; Back to the shape structure from last week
;; __________________________________________



; We are going to make a shape structure. However, this shape structure with only be able to be 
;   either a ball or a box. Have a slight issue with ball versus box, because box requires
;   two values. The structure needs to be able to be one of two types, ie. one that takes two size 
;   values or one size value. Therefore, for the shape structure fields, will make an
;   enumeration of structures or a union of structures which will represent the box and ball 
;   information needed by the shape structure
; Becuase ball and box are already shapes in the images library we need to create them.
(define-struct ball (radius x-pos y-pos)) ; Chose "radius" and not diameter, because the built in 
                                          ; circle structure uses radius. Ignore colors in this 
                                          ; structure. 
                                          ; Chose "x-pos" and "y-pos" instead of "x" and "y", because 
                                          ; want to be super specific about what those two fields do
; Data definition of the ball structure:
; A ball is a structure:
;    (make-ball PositiveNumber PositiveNumber PositiveNumber)         demonstrates what a generic 
;                                                                     ball structure would look like)

; Need to make the structure for box
(define-struct box (width height x-pos y-pos))
; Data definition of box structure:
; A box is a structure:
;     (make-box PositiveNumber PositiveNumber PositiveNumber PositiveNumber)


; Data definition (for the draw-shape)
; A shape is one of:
;    (1) A ball    (make-ball PositiveNumber PositiveNumber PositiveNumber) 
;    (2) A box     (make-box PositiveNumber PositiveNumber PositiveNumber PositiveNumber)

; Want to be able to draw the shape defined by the shape structure. If I have a function that will 
;   draw a shape, then what will the signature of that function be? : "Shape > Image"
; (Signature)
; Shape > Image
; (Header of function that draws a shape)
;(define (draw-shape a-shape)
;  ... 
;  )

; (Purpose statement)
; Draw shape-draws the shape, a-shape, as a solid turquoise shape on a 200 x 200 scene
; (Need to do some check-expect statements. First, going to use the interactive area to figure out 
;    what things are going to look like)

;;;;;;The following was executed in the interactive command line;;;;;;;
; This produces a turquoise circle, which is what we want "draw-shape" to produce
(place-image (circle 20 "solid" "turquoise") 
             50 100 
             (empty-scene 200 200))

(check-expect (draw-shape (make-ball 20 50 100)) 
              (place-image (circle 20 "solid" "turquoise") 50 100 (empty-scene 200 200)))
(check-expect (draw-shape (make-box 20 50 100 24)) 
              (place-image (rectangle 20 50 "solid" "turquoise") 100 24 (empty-scene 200 200)))

; What does the template look like if we have a data defintion that looks like an 
; enumeratione (ie. is an enumeration)? : Need a conditional statement with as many 
; branches are there are items in the enumeration. In this case need two: one for 
; the possiblity that it is a box and one for the possibility that it is a ball.
; (Template)
;(define (draw-shape a-shape)
;  (cond
;    [(ball? a-shape) ...] ;Need to test if a-shape is a ball. "ball?" is one of the functions that 
;                          ;is defined when a structure named "ball" is created
;    [(box? a-shape) ...]  ;Need to test if a-shape is a box
;    )
;  )

; This much of the template is not enought, because this is just what we get from knowing that the 
; data definitons contains an enumeration. Need to fill in the "..."s a bit more.
; Need to at least write out the parts of the shape structure this function is going to use. 
; Because the enumeration is for structures, the template needs to include all of
; the different parts of the structure
;(define (draw-shape a-shape)
;  (cond
;    [(ball? a-shape) ... (ball-radius a-shape) ... (ball-x-pos a-shape) ... (ball-y-pos a-shape) ...]
;    [(box? a-shape) ... (box-width a-shape) ... (box-height a-shape) ... (box-x-pos a-shape) ... 
;                    (box-y-pos a-shape) ...] ; Involves a bunch of writing, but it is boring 
;                                             ; writing that has to be done anyway
;    )
;  )

; Moving onto the code step:
(define (draw-shape a-shape)
  (cond
    [(ball? a-shape) (place-image (circle (ball-radius a-shape) 
                                        "solid" "turquoise")
                                (ball-x-pos a-shape) (ball-y-pos a-shape) 
                                (empty-scene 200 200))] ; One of the branches is now filled out. It 
                                                        ; follows the same structure and format as 
                                                        ; the check-expect, just in the check-expect
                                                        ; the numbers were set for the shapes
    [(box? a-shape) (place-image (rectangle (box-width a-shape) (box-height a-shape)
                                          "solid" "turquoise")
                               (box-x-pos a-shape) (box-y-pos a-shape) 
                               (empty-scene 200 200))]
    )
  ) ; Now able to run the check-expect tests

; Will need all of this to make a function that will move a shape. This draws the shape, but now we 
;   need to animate it. Need a function that works with tick

; (Signature)
; Shape > Shape
; (Purpose Statement)
; The function grow-shape increases all values associated with the size of a shape by 1, ie. both 
;   the width and the height
; (Header)
;;(define (grow-shape s)
;;  ...
;;  )
; check-expects are much easier here. The purpose of this function is not to draw the shape, but to 
;   grow it.
;(check-expect (grow-shape (make-ball 15 22 77)) 
;              (make-ball 16 22 77))
;(check-expect (grow-shape (make-box 12 3 100 101)) 
;              (make-box 13 4 100 101))

; Time for the template. The template is defined entirely by the input. Essentially the same as 
;   draw-shape
;(define (grow-shape s)
;  [(ball? s) ... (ball-radius s) ... (ball-x-pos s) ... (ball-y-pos s) ...]
;  [(box? s) ... (ball-width s) ... (box-height s) (box-x-pos s) ... (box-y-pos s) ...]
;  )

; Need to fill in the "..." that let's us output a ball
;(define (grow-shape s)
;  [(ball? s) ... (make-ball (ball-radius s) ... (ball-x-pos s) ... (ball-y-pos s) ...)]
;  [(box? s) ... (box-width s) ... (box-height s) (box-x-pos s) ... (box-y-pos s) ...]
;  )

; Need to make the radius greater by 1
; Need to fill in the "..." that let's us output a ball
; Don't need to refer to drawing the ball or anything, because that has already been done with 
;    draw-shape
;(define (grow-shape s)
;  [(ball? s) (make-ball (+ (ball-radius s) 1) (ball-x-pos s) (ball-y-pos s))]
;  [(box? s) ... (box-width s) ... (box-height s) (box-x-pos s) ... (box-y-pos s) ...]
;  )

; Do essentially the same thing to update the box so that it can "grow"
(define (grow-shape s)
  (cond 
  [(ball? s) (make-ball (+ (ball-radius s) 1) (ball-x-pos s) (ball-y-pos s))]
  [(box? s) (make-box (+ (box-width s) 1) (+ (box-height s) 1) (box-x-pos s) (box-y-pos s))]
  )
  )

;; Big Bang
;; --------


;; a function to create a square of the given size
(define (number->square s)
  (square s "solid" "red"))

;; call the big-bang function with the given call-backs
;  (big-bang 100
;    [to-draw number->square]
;    [on-tick sub1]
;    [stop-when zero?])

;; *to-draw - a function which accepts the world state and draws something
;;            according to the world state
;; Signature of the to-draw function: 
;; (define (my-to-draw variable-of-type-world-state) ...)
;; World-State-Type -> Image


;; *on-tick - a function wich accepts a variable of type world state, 
;;            and returns the next state
;; (define (my-tick variable-of-type-world-state) ... )
;; World-State-Type -> World-State-Type


; For bigbang function think of the state of the world as the position of the shape, the shape's 
;  color, location, and size
; All of the functions for bigbang are a part of the state > everything is in terms of the state of 
;  the world

; Now this information can be put into bigbang
;(big-bang (make-ball 1 100 100)
;          (to-draw draw-shape) ; draw-shape is the to-draw function. Has to take world state and 
;                               ; outputs an image. The world state can be anything, but once make a 
;                               ; decision about what it should be, need to stick with that
;          (on-tick grow-shape) ; At every tick, on-tick calls draw-shape to draw the ball on an empty
;                               ; scene. If want to change anything about what is drawn, then need to 
;                               ; change the draw-shape function
;           )



;; Responding to key presses
;; -------------------------

; The above function will cause the shape to just keep on growing. Want to modify the 
; function so  that when any key on the keyboard is pressed it will reset the whole 
; animate. 

;; *on-key - a function wich accepts a variable of type world state, and a 
;;           keyboard character, and returns a new world state 
;; (define (my-on-key variable-of-type-world-state string) ... )
;; World-State-Type String -> World-State-Type

;(big-bang (make-ball 1 100 100)
;          (to-draw draw-shape)
;          (on-tick draw-shape)
;          (on-key ...) ; Need to takes a shape and a key press as input.
;           )


; Define function that will take a shape and key as input and will reset the world 
; state. It will output the initial state
; (Signature)
; Key & Shape > Shape
; In this case able to completely ignore the template, becuase we are not going to do anything with 
;    the shape. Can ignore parts of the template when you do not need that information. 
; Don't care about what shape it was or where is was, so don't need to worry about those parts of
;   the template
; Reset ignores s and just resets the world
(check-expect (reset (make-ball 50 22 77) "q") (make-ball 1 100 100)) ; No matter which shape is 
                                 ; passed as input, reset should always return the same initial state
(define (reset s ks)
  (make-ball 1 100 100)
  )

; Now, when the shape gets to big, able to press the "q" key and reset to the original world shape
;(big-bang (make-ball 1 100 100)
;          (to-draw draw-shape)
;          (on-tick grow-shape)
;          (on-key reset)
;           )


;; Stoping the big bang
;; --------------------
;; 
;; We want to stop the world in response to some condtion. big-bang also accepts a
;; callback which evaluates to #true if the world should stop. This is specified 
;; by using the 'stop-when' argument. 

; (big-bang 100
;    [to-draw number->square]
;    [on-tick sub1]
;    [stop-when zero?])

; we may modify out reset function, remember, this is given in the on-key handler, 
; and is called each time a keyboard key is pressed. Instead of always resetting
; the shape to a small size, this may return a whole new world state, and this
; world state may be checked by the 'stop-when' handler.

;; *** NOTE ****
;; to-draw, on-tick, on-key, or stop-when may be called in any order, 
;; all of them must be written in a safe way, in that any function which 
;; returns an altered world state must return a type which is compatible with 
;; all of the specified handlers. 

(define (quitting-reset s ks)
  (if (string=? "q" ks) (make-ball 0 100 100) (make-ball 1 100 100))
)

(define (stopping-cond s) (zero? (ball-radius s)))

;(big-bang (make-ball 1 100 100)
;          (to-draw draw-shape)
;          (on-tick grow-shape)
;          (on-key quitting-reset)
;          (stop-when stopping-cond)
;           )



