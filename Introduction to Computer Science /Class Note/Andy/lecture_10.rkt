;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-reader.ss" "lang")((modname lecture_10) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
; Lecture 10, lists...
; Hardest part of Hmwk 5? >

; Making three templates for each. Two of the are recursive. 

; quick version of tower of numbers from last time
(define-struct empty-tower ())
(define-struct bigger-tower (top-number rest-of-tower))

(require 2htdp/image)
(require 2htdp/universe)

; If we have bigger-tower, then we essentially have the top number and the rest of the 
; tower. Just like with most of the other 'sequence' type objects that we've dealt with:

; - TowerOfNumbers
; - Planets
; - Things...

; We have a struct which holds some data, then holds a another structure of the 
; same type, or some terminating symbol.


; A TowerOfNumbers (ToN) is one of:
;  | (make-empty-tower)
;  | (make-bigger-tower Number ToN)

; The argument name should be desriptive of the type of information the argument will 
; hold. In this case, the argument will represent a TowerOfNumbers
; (template)
;(define (process-ton ton)
;  (cond ; Because TowerOfNumbers is an enumeration
;    [(empty-tower? ton) ... ]
;    [(bigger-tower? ton) ... (bigger-tower-top-number ton) ... 
;    (process-ton (bigger-tower-rest-of-tower ton)) ... ])
;  )

; Want to do the same thing as process-ton, but with a list. Notice the structure 
; definition doesn't tell us that it is a tower of numbers. The fact that the first 
; one is empty is just something we commit to with the data definition, and the same 
; goes for bigger-tower. In the case of lists, an empty-tower would be an empty list 
; and bigger-tower would be a cons

; List Keywords
; -------------
;
; * empty -- the empty symbol
; * cons  -- construct a new pair
; * list  -- construct a list (a sequence of pairs)
; * first (or car)   -- grab the 1st part of a pair
; * rest (or cdr)   -- grap the 2nd part of a pair



; Etymology
; Lisp was originally implemented on the IBM 704 computer, in the late 1950s. The 704 
; hardware had special support for splitting a 36-bit machine word into four parts, an 
; "address part" and "decrement part" of 15 bits each and a "prefix part" and "tag part" 
; of three bits each.
; 
; * car (short for "Contents of the Address part of Register number"),
; * cdr ("Contents of the Decrement part of Register number"),
; * cpr ("Contents of the Prefix part of Register number"), and
; * ctr ("Contents of the Tag part of Register number"),
; 
; each of which took a machine address as an argument, loaded the corresponding word 
; from memory, and extracted the appropriate bits.
; 
; A machine word could be reassembled by cons, which took four arguments (a,d,p,t).
;
; The prefix and tag parts were dropped in the early stages of Lisp's design, 
; leaving CAR, CDR, and a two-argument CONS.

; cons
; ----
; (cons a d) ->  pair?
;   a : any/c
;   d : any/c
; Returns a newly allocated pair whose first element is a and second element is d.
; Examples:
(cons 1 empty)


; list
; ----
; construct a list (a sequence of pairs)
; typeing cons over and over is annoying
; list takes a set of n arguments and constructs a list
; examples
(list 1 2 3 4)


; first
; ---
; grab the first part of a pair
; examples
(first (list 1 2 3 4))


; rest
; ---
; Grab the second part of a pair. Note, grabing the second part of a pair is
; equivalent to saying we grab the trailing part of a list. 
; car : grab the head of a list
; cdr : grab everything after the head of a list
; examples
(rest (list 1 2 3 4))


; A ListOfNumbers (LoN) is one of:
;  | empty (this just means the empty list)
;  | (cons Number LoN)

; cons looks like a regular operator, but the two things put together are very different 
; things. The first is the top number and the second is the rest of the list. 
; For example, 1 2 3 4 5 is really just a number 1 stuck to the list 2 3 4 5 by cons.

; Here are some lists of numbers:
; empty
; (cons 1 empty) ; This only has one member in it. Remember that the empty stands for a 
; list with nothing in it.
; (cons 4 (cons 1 empty))
; (cons 16 (cons 9 ( cons 4 (cons 1 empty))))

; Each of the above 4 items is a valid list of numbers. The last is the list we think of 
; as 16 9 4 1. Do you think about a list a things of equal importance stuck in a row. 
; Our functions work with the item that is at the front of the list. In this case, that 
; would be 16. It is really difficult to put an item at the end of a list, and it is 
; really difficult to put an item in the center of a list.
; Why?


; This function will take a ListOfNumbers as input and do some process on them
; How does the template start? >

; With cond, we know this because ListOfNumber is a


; A ListOfNumbers (LoN) is
;   | empty
;   | (cons number LoN)

; "one of"
; signature:
; (process-lon lon:ListOfNumbers) -> ?
;(define (process-lon lon)
;  (cond
;    [(empty? lon) ... ]
;    [(cons? lon) ... (first lon) ... (process-lon (rest lon)) ... ]



; (first lon) gets the first number in the ListOfNumbers. For (rest lon), 
; looks at the rest of the list after the first number has been removed 
; from the list, and then calls the whole function process-lon on it.
;    )
;  )

; What happens if we run first on (cons 4 (cons 1 empty)), 
; (first (cons 4 (cons 1 empty)))

; What about (rest (cons 4 (cons 1 empty)))? >

; (conds 1 empty) >

; It is the rest of the list after the first number
; What about (rest (cons 16 (cons 9 ( cons 4 (cons 1 empty)))))? >


; (cons 9 ( cons 4 (cons 1 empty)))


; What about (rest (rest (cons 9 ( cons 4 (cons 1 empty))))) ? >


; (cons 1 empty)

; If have (cons 4 8 12), what would the result be?

; This does not work. It does not make sense to BSL. cons takes the first argument and 
; adds it to a list that is the second argument. Need to write: 
; (cons 4 (cons 8 (cons 12 empty))), which is the same as what we think of as 4 8 12.

; Doing something with a list:
; We are going to do something similar to adding dots to a screen so that there is one 
; dot for each mouse-click. We want a list as our world state in this function.

; What is the world state? >

; drawing pairs, so WS is a ListOfPairs...

; It is the thing that changes. 

; What is the thing that changes? > 

; The world state cannot be a single location, because there will be multiple dots. 
; Need to know how many dots and for each dot where its position is. This is why we 
; need lists because it allows us to add more dots to the list.

; We'll use the built in posn type
; A Posn is (make-posn Number Number)
; * the data definition gives you a generic form for a data type,
; so everytime you make one you have an idea of what it should look like


; world state data definition
; A ListOfPosns (LoP) is one of:
;   | empty
;   | (cons (make-posn x:number y:number) LoP)

; This is a recursive definition

; Whenever asked to give an example of a list, it is either "empty" or it starts with 
; "cons"

; Two ways to do this: start with one position at the bottom and then move one or start 
; with no positions. It is better to be able to handle not having any dots

; What do we need our function to be. 
; (1) If we want dots to appear on the screen, then we need to draw dots on the screen, 
;     so we need a to-draw function. What does the signature of a to-draw function need 
;     to be? What goes in and what comes out? >

;     Even if a bit lost, write down what you know. This will help. The output is an 
;     image. The input is a ListOfPosns. ListOfPosns -> Image

; (2) We want it to add a dot when we click, therefore we will need a "on-mouse" 
;     function. What does the function need to be an "on-mouse" functions? >


;     ws:LoP x:Number y:Number me:MouseEvent -> LoP . In general, the input shoudl be a world 
;     state and the output should be some type of world state.

; (3) If we want the dots to move, then we need an "on-tick" function. 
;     What does it need? > Lop -> LoP. It will take the current ListOfPosns and will 
;     return a new ListOfPosns on each and every tick.

; Starting with add-dot:
; (Signature) : Lop Number Number MouseEvent -> LoP
; (Header) : (define (add-dot current-dots mouse-x mouse-y me) ... )
; (Purpose Statement) : When me is "button-down", add a dot at the mouse's coordinates to 
; current-dots
; Only draw or handle images within the function that is for "to-draw". Only draw and 
; deal with images in the "to-draw" function
; (tests)
(check-expect (add-dot empty 50 75 "button-down") (cons (make-posn 50 75) empty))

; Whenever you want to write something that is a piece data, remember to just look back 
; at the data definition. Whatever value is given to add-dot, it needs to be either a LoP
; or empty
(check-expect (add-dot empty 50 75 "move") empty)

; It is the same as what we started with, because only want to do something when click 
; the button
(check-expect (add-dot (cons (make-posn 50 75) empty) 120 66 "button-down")
              (cons (make-posn 120 66)(cons (make-posn 50 75) empty)))

; (Template)
; This is teh template for the structure
;(define (add-dot ws mouse-x mouse-y me) 
;  (cond
;    [(empty? ws) ... ] ; nothing further that the template requires for the empty case
;    [(cons? ws) ... (first ws) ... (add-dots (rest ws) mouse-x mouse-y me) ... ]
;    )
;  )


; This is the template, but this is a case, a rare case, where we do not need all of 
; the template. Do you need to look at where the first posn is in order to add a posn 
; to the list? >

; No. You need a cons to add to a list. Do we even care if it is empty? >


; No, because only one thing is being done with the current state, namely adding the 
; current position. Therefore, we really only need the template for the mouse event

; When do we want to add a dot? Only when the mouse event is "button-down". 
; How do you test is me is "button-down"? >

; (string=? "button-down" me)
; This is the mouse-event template
;(define (add-dot ws mouse-x mouse-y me) 
;  (cond
;    [(string=? "button-down" me) ... ]
;    [else ... ]
;    )
;  )

; Let's deal with the else branch first, what do I do if the me is not "button-down"? >


; We want the current-dots list to go back out, because we want to give back a list of 
; position, but not with any changes.
;(define (add-dot ws mouse-x mouse-y me) 
;  (cond
;    [(string=? "button-down" me) ... ]
;    [else ws ]
;    )
;  )

; What do we do if we have me as a "button-down"? >

; Need to use cons to add the 
; coordinates from the mouse event to the list of current-dots.
(define (add-dot ws fred barney  me) 
  (cond
    [(string=? "button-down" me) (cons (make-posn fred barney) ws) ]
    [else ws ]
    )
  )

; Whenever you no longer have "..." in you function, you should always run. 
; This is why it is important to write check-expects for each of your functions not 
; matter how big or small the function seems. This ensures that your helper functions 
; are working before you start working on the main function.

; Let's work on the "to-draw" function!!
; (Signature) : LoP, Image -> Image
; (Header) : (define (draw-dots lop img) ...)
; (Purpose Statement) : Draw a radius 5 black dot at each position in lop on an empty 
;                       scene 200x200 pixels
; (Tests) > Need at least 2 check-expects: one for when it is empty and one for when 
;           there are dots
;(check-expect (draw-dots (empty)  (empty-scene 200 200) ) (empty-scene 200 200))
;(check-expect (draw-dots (cons (make-posn 50 75) empty)  (empty-scene 200 200))
;              (place-image (circle 5 "solid" "black") 50 75 (empty-scene 200 200)))

; (Template)
;(define (draw-dots lop img)
;  (cond
;    [(empty? lop) ... ]
;    [(cons? lop) ... (first lop) ... (draw-dots (rest lop)) ... ] 
;    ; LoP is rescursive, so need to draw the dots at the rest of the posn in the list
;    )
;  )


; Lets test out some drawing code...
; (place-image (circle 5 "solid" "black") 5 5 (empty-scene 200 200))


; If I already have some dots, then how do I add a dot to be draw to the image? 
(define (draw-dots lop img) 
(cond
    [(empty? lop) img ]                       ; its empty, nothing to draw
    [(cons? lop)
     (place-image (circle 5 "solid" "black")  ; draw the point on top of an image
                  (posn-x (first lop))        ; the x coord out of first element
                  (posn-y (first lop))        ; y coor out first element
                  (draw-dots (rest lop) img)  ; the image where I'm going to draw,
                                              ; get this image from the rest of the list
                  )
     ] 
    ; LoP is rescursive, so need to draw the dots at the rest of the posn in the list
    )
  )

(define (draw ws) (draw-dots ws (empty-scene 200 200)))

(big-bang empty
          (to-draw draw)
          (on-mouse add-dot))

