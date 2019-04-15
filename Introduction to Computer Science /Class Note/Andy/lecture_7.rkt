;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-reader.ss" "lang")((modname lecture_7) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
; Review of structures and unions
; -------------------------------

; Big Bang Review
; ---------------

(require 2htdp/image) 

; load the animate function
(require 2htdp/universe)



; a function to create a square of the given size
(define (number->square s)
  (square s "solid" "red"))

; call the big-bang function with the given call-backs
  (big-bang 100
    [to-draw number->square]
    [on-tick sub1]
    [stop-when zero?])

; *to-draw - a function which accepts the world state and draws something
;            according to the world state
; Signature of the to-draw function: 
; (define (my-to-draw variable-of-type-world-state) ...)
; World-State-Type -> Image


; *on-tick - a function wich accepts a variable of type world state, 
;            and returns the next state
; (define (my-tick variable-of-type-world-state) ... )
; World-State-Type -> World-State-Type


; For bigbang function think of the state of the world as the position of the shape, 
; the shape's color, location, and size

; Responding to key presses
; -------------------------

; *on-key - a function wich accepts a variable of type world state, and a 
;           keyboard character, and returns a new world state 
; (define (my-on-key variable-of-type-world-state string) ... )
; World-State-Type String -> World-State-Type

 (define (my-on-key num key) (- num 1))

  (big-bang 100
    [to-draw number->square]
    [on-key my-on-key]
    [stop-when zero?])

; Reset the World
; ---------------

 (define (my-on-key-2 num key) 
   (if (string=? key " ") 100 (- num 1)))


; Stoping the big bang
; --------------------
 
; We want to stop the world in response to some condtion. big-bang also accepts a
; callback which evaluates to #true if the world should stop. This is specified 
; by using the 'stop-when' argument. 

 (big-bang 100
    [to-draw number->square]
    [on-key sub1]
    [stop-when zero?])

; we may modify out reset function, remember, this is given in the on-key handler, 
; and is called each time a keyboard key is pressed. Instead of always resetting
; the shape to a small size, this may return a whole new world state, and this
; world state may be checked by the 'stop-when' handler.

; *** NOTE ****
; to-draw, on-tick, on-key, or stop-when may be called in any order, 
; all of them must be written in a safe way, in that any function which 
; returns an altered world state must return a type which is compatible with 
; all of the specified handlers. 



; RECURSIVE STRUCTURES
; --------------------


; Moving onto something new ... well, kind of new. It is still going to be an 
; enumeration of structures, but this enumeration of structures will have  new 
; feature. It is a recursive structure, which means that it refers to itself.
; Does anyone know what a nested Russian doll is? We are going to make a data type 
; that describes a very simple nested doll, meaning we only care about whether or 
; not the  doll is the last doll, ie solid, or not the last doll, ie. hollow?

; Need a thing to declare being the solid doll. We are going to create a structure 
; for doing this. THis structure will be different, because it will not have fields, 
; because we only care if it is a solid doll.

; we are creating a data object that is one of a kind. All instances of a solid-doll 
; will be exactly the same

 (define-struct solid-doll []) 

; * Next need a structure to hold the hollow dolls
; Has an extra pieice of information, nameely what is inside of it? Another hollow 
; doll? Or, a solid doll? We have not told the hollow-doll structure
; what kind of thing goes into contents. In this case, contents will be some kind of 
; doll, either hollow-doll or solid-doll. That is the data object 
; object we want. A solid-doll versus hollow-doll

 (define-struct hollow-doll [inner]) 

; (Data Definition) > this is an enumeration, so we don't use it to talk to BSL, we 
; just use it to talk to one another, so we can use short hands (ie. MD). A MD can 
; be of one of
; two types; 

; solid-doll or hollow-doll
 
; A Matryoshka-doll (MD) is one of:
;     (make-solid-doll)      > only need "make-solid-doll" because solid-doll 
; doesn't use/need/accept any extra information
;     (make-hollow-doll MD)  > Takes contents, but what type of contents?> It takes 
; an enumeration? What kind of enumeration? > a MD. MD represents any number of 
; those things listed in the MD enumeration.

; What makes this special is that one of the enumeration takes a whole enumeration. 
; Only one of the branches can be recursive or else you could have a 
; program/function that ever ends.
; A Matryoshka-doll (MD) is one of:
; * (make-solid-doll)
; * (make-hollow-doll (make-solid-doll))
; * (make-hollow-doll (make-hollow-doll (make-solid-doll)))
; * (make-hollow-doll (make-hollow-doll (make-hollow-doll (make-solid-doll))))

; Writing the code!!!:
 (define-struct solid-doll ())
 (define-struct hollow-doll (contents))

; Let's make some examples we can work with!
; This would be the center doll
(define child (make-solid-doll)) 

; a hollow doll holding a hollow doll that holds the solid doll
(define mom (make-hollow-doll child))

; grand-parent holds a mom which holds a child
(define grand-parent (make-hollow-doll mom))

(define gpp (make-hollow-doll grand-parent))

; When hold child, you are only holding one doll. When holding the mom you are 
; holding two dolls, which means that is has a recursive depth of 2. When holding 
; the grand-parent doll, you are holding 3 dolls, a recursive depth of 3. You know 
; this because it is only one more than the dolls held by mom. If a doll is a 
; hollow-doll, then it will always hold one plus the number of dolls held by the 
; dolls inside it. As long as there is an otion for a bottom, ie. the chile, or the 
; point at which the recursion is no longer called, then the funciton will work.

; Let's write a function that will tell us how many dolls there are, ie. what the 
; depth of the dolls is


; The function's name will be: num-dolls
; What is the signature of this function? : MD -> Number
; What is the header of the function? :
; (define (num-dolls md) ... )
;  (purpose statement)
; num-dolls returns the number of dolls that make up the MD
; Tests/check-expects
(check-expect (num-dolls child) 1)
(check-expect (num-dolls mom) 2)
(check-expect (num-dolls grand-parent) 3)


; * Review of templates of unions
; _______________________________

; Rember, doll is actually a union of hollow-doll and solid-doll
; How do we write a template for this?

; (template)
(define (process md) 
  (cond
    [(hollow-doll? md) ... ]
    [(solid-doll? md) ...]
    )
  )

; Think about what kind of things is md > it is of type Matryoshka-doll, which is a 
; kind of enumeration/union/"or-thing" (solid-doll or hollow-doll). If it is an 
; enumeration, what do we have to write to let us check for its type and to deal 
; separately with its two different types. There are two possibilities, so need to 
; know what to do in each possibility

; Does the order of the conds make a difference? > Depends on what you are testing 
; for, ie. in the grade point example. The order matters when two different branches 
; can result in "yes this matches!" or is true.

; Are we done? > I say no, because we are working with structures. In the solid-doll 
; case, there is not much that needs to go on in here, becuase the structure does 
; not have any fields. This feels weird, but you'll see why this is true. The 
; hollow-doll does need something because it is a structure with fields. Need to get 
; at the contents of hollow-doll. Need to get at the contents function and call it 
; on my hollow-dolls


(define (num-doll md) 
  (cond
    [(hollow-doll? md) ... (hollow-doll-content md) ... ]
    [(solid-doll? md) ...]
    )
  )

; Whenever there is a recursion, you need to access the contents using the number of 
; dolls function. This is weird, but think about how we figured out how many dolls 
; were in the mom doll. First we asked how big are the contents of the daughter 
; doll, which is held by the mom doll. Then you can just add 1 to the contents of 
; the parent doll.

(define (num-doll-1 md) 
  (cond
    [(hollow-doll? md) ... (num-dolls (hollow-doll-content md)) ... ]
    [(solid-doll? md) ...]
    )
  )

; Any times want to do something with the MD, need to have a 
; (num-dolls (hollow-doll-content md)) withing the function
; if know the number of dolls in the content, then how do you figure the total 
; number of dolls? > add one to the number of dolls in the current dolls. The fact 
; that it is a solid doll, tells us that the number of dolls is one, just the solid 
; doll

; (code)
(define (num-dolls-2 md) 
  (cond
    [(hollow-doll? md) (+ (num-dolls (hollow-doll-content md)) 1) ] 
                                                                      
    [(solid-doll? md) 1] 
    )
  )

; If you follow the template and the design recipe, then building more complicated 
; and abstract functions becomes easier to handle mentally.
; num-dolls is out first recursive definition!
; If you write down the recipe and write out the data definition on paper and draw 
; the arrow (as it was drawn on the board in class), then it makes it easy to figure 
; out what your function requires and whether or not it will have recursion.

; Questions? > none.
; This will get easier with practice, but it does require practice. If you struggle 
; at all, make up examples a solve them. There are patterns, so the more you 
; practice the easier it will be write functions quickly and effectively.

; Set-up the next thing:
; Idea for last example is, for the dolls all that matter was how many dolls there 
; were and not their order. So, we are going to make a tower of numbers. Will either 
; have a  number or number stacked on top of a base: 

(define-struct stacked-num (value base)) 

; something that is a number on top of numbers. value is a number and base is the 
; numbers value is on top of

; A Tower-of-Numbers (ToN) is one of:
;    - a Number
;    - (make-stacked-num Number ToN)

; Let's make some ToN's

(define squares3 (make-stacked-num 9 (make-stacked-num 4 1))) 

; a stack of numbers built on top of a number, 1, with 4 on top of 1 and 9 on top of 
; the stack

; This function adds up the values of the numbers in a stack
; (signature)
; ToN -> Number
; (header)
(define (sum-0 tower) ... )
; (purpose statement)
; sum adds upt all the numbers in the tower and returns the result
; (test/check-expects)
(check-expect (sum squares3) 14)
(check-expect (sum 257) 257)
; (template)
(define (sum-1 tower) 
  (cond
    [(number? tower) ... ]
    [(stacked-num? tower) ... ]
    )
  )

; The data definition tells us that the template needs a cond
; Because we have a number in the function, then tempalte needs to use the variable 
; tower and access its value
(define (sum-2 tower) 
  (cond
    [(number? tower) ...  tower ...]
    [(stacked-num? tower) ... (stacked-num-value tower) ... 
       (stacked-num-base tower) ...]
    )
  )

; Because we have a recursive date definition we know that we need to do the 
; function we are defining on the stacked-num-base used in the function
(define (sum-3 tower) 
  (cond
    [(number? tower) ...  tower ...]
    [(stacked-num? tower) ... (stacked-num-value tower) ... 
      (sum (stacked-num-base tower)) ...]
    )
  )

; If the tower of numbers is really just one number, then the sum is just that one 
; number. It is that tower, because that tower is just a number
(define (sum-4 tower) 
  (cond
    [(number? tower)  tower]
    [(stacked-num? tower) ... (stacked-num-value tower) ... 
      (sum (stacked-num-base tower)) ...]
    )
  )

; What do you do if the tower is a stacked-num? > If you know the value of 
; everything beneath the value and know the the value, then what do you need to do? 
; You just add them the value and the sum of the (stacked-num-base tower)
(define (sum tower) 
  (cond
    [(number? tower)  tower]
    [(stacked-num? tower) (+ (stacked-num-value tower) 
       (sum (stacked-num-base tower)))]
    )
  )