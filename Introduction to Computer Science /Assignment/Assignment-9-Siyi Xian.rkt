;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-reader.ss" "lang")((modname |Assignment-9-Siyi Xian|) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
; Book Exercises

; Exercise 315

; A Child is a structure: 
;   (make-child Child Child String Number String)

(define-struct child (father mother name date eyes))
(define-struct no-parent [])

; A FT (short for family tree) is one of: 
; – (make-no-parent)
; – (make-child FT FT String N String)

(define NP (make-no-parent))
; A FT is one of: 
; – NP
; – (make-child FT FT String N String)

; Oldest Generation:
(define Carl (make-child NP NP "Carl" 1926 "green"))
(define Bettina (make-child NP NP "Bettina" 1926 "green"))
; Middle Generation:
(define Adam (make-child Carl Bettina "Adam" 1950 "hazel"))
(define Dave (make-child Carl Bettina "Dave" 1955 "black"))
(define Eva (make-child Carl Bettina "Eva" 1965 "blue"))
(define Fred (make-child NP NP "Fred" 1966 "pink"))
; Youngest Generation: 
(define Gustav (make-child Fred Eva "Gustav" 1988 "brown"))

; A FF (short for family forest) is one of: 
; – '()
; – (cons FT FF)
; interpretation a family forest represents several
; families (say a town) and their ancestor trees

(define ff1 (list Carl Bettina))
(define ff2 (list Fred Eva))

; FF Number -> Number
; Count the average age of the whole family forest
(define (average-age ff num)
  (local [; Number Number Number -> Number
          (define (add a b c) (+ 1 b c))
          ; FT -> Number
          (define (help-years ft op)
            (cond
              [(no-parent? ft) 0]
              [else (op (child-date ft)
                        (help-years (child-mother ft) op)
                        (help-years (child-father ft) op))]))
          ; FF -> Number
          (define (total-number ff op)
            (local [; Number Number -> Number
                    (define (sum-tree current result)
                      (+ (help-years current op) result))]
              (foldr sum-tree 0 ff)))]
    (- num (/ (total-number ff +) (total-number ff add)))))
(check-expect (average-age ff1 2016) 90)
(check-expect (average-age ff2 2016) 70.25)


; Exercise 323

(define-struct no-info [])
(define NONE (make-no-info))
 
(define-struct node [ssn name left right])
; A BinaryTree (short for BT) is one of:
; – NONE
; – (make-node Number Symbol BT BT)

(define bt1 (make-node 15 'd NONE (make-node 24 'i NONE NONE)))
(define bt2 (make-node 15 'd (make-node 87 'h NONE NONE) NONE))

; Number BinaryTree -> Symbol or Boolean
; Check if there is a given number in the given Binary tree
(define (search-bt num bt)
  (cond
    [(no-info? bt) #false]
    [else (if (= num (node-ssn bt))
              (node-name bt)
              (if (symbol? (search-bt num (node-left bt)))
                  (search-bt num (node-left bt))
                  (search-bt num (node-right bt))))]))
(check-expect (search-bt 24 bt1) 'i)
(check-expect (search-bt 15 bt2) 'd)
(check-expect (search-bt 30 bt1) #false)


; Exercise 388

; A Employee is : (make-employee String Number Number)
(define-struct employee (name ssn rate))
(define em1 (make-employee "James" 0 12.7))
(define em2 (make-employee "Bob" 1 2.8))
(define em3 (make-employee "Tom" 3 13.75))
(define ems (list em1 em2 em3))

; A WorkRecord is : (make-work-record String Number)
(define-struct work-record (name hours))
(define wr1 (make-work-record "James" 30))
(define wr2 (make-work-record "Bob" 60))
(define wr3 (make-work-record "Tom" 40))
(define wr4 (make-work-record "James" 10))
(define wrs (list wr1 wr2 wr3 wr4))

; A Wage is : (make-wage Name Number)
(define-struct wage (name number))
(define w1 (make-wage "James" (* (+ 30 10) 12.7)))
(define w2 (make-wage "Bob" (* 60 2.8)))
(define w3 (make-wage "Tom" (* 40 13.75)))
(define ws (list w1 w2 w3))

; Number Number -> Number
; computes the weekly wage from pay-rate and hours
(define (weekly-wage pay-rate hours)
  (* pay-rate hours))

; [List-of Employee] [List-of WorkRecord] -> [List-of Wage]
; multiplies the corresponding items on 
; hours and wages/h 
(define (wages*.v2 emplo rec)
  (local [; WorkRecord -> Wage
          ; Count everyone's weekly wages
          (define (wages em)
            (local [; WorkRecord Number -> Number
                    ; Find the total work hours of the given people
                    (define (find-pay-rate reco result)
                      (if (string=? (work-record-name reco) (employee-name em))
                          (+ (work-record-hours reco) result)
                          result))]
              (make-wage (employee-name em)
                         (weekly-wage (employee-rate em)
                                      (foldr find-pay-rate 0 rec)))))]
    (map wages emplo)))
(check-expect (wages*.v2 ems wrs) ws)


; Exercise 389

(define-struct phone-record [name number])
; A PhoneRecord is a structure:
;   (make-phone-record String String).
(define record (make-phone-record "Bob" "8129559999"))

(define names (list "Bob"))
(define numbers (list "8129559999"))
(define phone-records (list record))

; [ListOf String] [ListOf String] -> [ListOf PhoneRecord]
; Combine the names and phone numbers to produce phone records
; Assume the two given lists are of equal length
(define (zip na ph)
  (cond
    [(empty? na) empty]
    [else (cons (make-phone-record (first na) (first ph))
                (zip (rest na) (rest ph)))]))
(check-expect (zip names numbers) phone-records)


; Exercise 390

(define-struct branch [left right])
; A TOS is one of:
; – Symbol
; – (make-branch TOS TOS)

(define br0 'HelloWorld)
(define br1 (make-branch br0 br0))
(define br2 (make-branch br1 br0))
(define br3 (make-branch br2 br2))
(define br4 (make-branch br3 br3))
 
; A Direction is one of:
; – 'left
; – 'right
 
; A list of Directions is also called a path.

(define d0 empty)
(define d1 (list 'left 'right 'right 'left))
(define d2 (list 'left 'right 'left))

; TOS [ListOf Direction] -> TOS or Error
; Find the branch follow the given path
(define (tree-pick tr d)
  (cond
    [(empty? d) tr]
    [else (if (symbol? tr)
              (error "tree too short")
              (if (symbol=? 'left (first d))
                  (tree-pick (branch-left tr) (rest d))
                  (tree-pick (branch-right tr) (rest d))))]))
(check-expect (tree-pick br0 d0) 'HelloWorld)
(check-error (tree-pick br0 d1) "tree too short")
(check-expect (tree-pick br4 d0) br4)
(check-error (tree-pick br4 d1) "tree too short")
(check-expect (tree-pick br4 d2) br1)


; Mutually-Recursive Data Definitions

(define-struct section (title text subsections))
 
; A Section is a structure: (make-section String ListOfStrings ListOfSections)
; where the title field is the name of the section,
; the text field is the words in the section.
; and the subsections field is the contents of the section.

; A List-of-Sections (LoS) is one of:
; - empty
; - (cons Section LoS)


; Exercise 1

(define p1 (make-section "Page 1" (list "Apple" "is" "fruit.") empty))
(define p2 (make-section "Page 2" (list "Monkey" "is" "animal.") empty))
(define p3 (make-section "Page 3" (list "Jeep" "is" "car.") empty))
(define p4 (make-section "Page 4" (list "Computer" "has" "screen.") empty))
(define se1 (make-section "Section 1"
                          (list "Apple" "is" "fruit."
                                "Monkey" "is" "animal.")
                          (list p1 p2)))
(define se2 (make-section "Section 2"
                          (list "Jeep" "is" "car."
                                "Computer" "has" "screen.")
                          (list p3 p4)))
(define ch1 (make-section "Chapter 1"
                          (list "Apple" "is" "fruit."
                                "Monkey" "is" "animal."
                                "Jeep" "is" "car."
                                "Computer" "has" "screen.")
                          (list se1 se2)))


; Exercise 2

; Section -> ?
#;
(define (process-section se)
  ... (section-title se)
  ... (section-text se)
  ... (section-subsections se) ...)
; LoS -> ?
#;
(define (process-los l)
  (foldr process-section Anything l))


; Exercise 3

; Section String -> [ListOf String]
; Find the given word in the given sections
(define (find-word se word)
  (local [; [ListOf String] String -> Boolean
          ; Check if the given word is in the given list
          (define (search l)
            (local [; String Boolean -> Boolean
                    (define (same-string t result)
                      (or (string=? t word) result))]
              (foldr same-string #false l)))
          ; Section [ListOf String] -> [ListOf String]
          (define (find-sub current result)
            (append (find-word current word) result))]
    (if (search (section-text se))
        (cons (section-title se)
              (foldr find-sub empty (section-subsections se)))
        (foldr find-sub empty (section-subsections se)))))
(check-expect (find-word ch1 "map") empty)
(check-expect (find-word ch1 "Jeep") (list "Chapter 1" "Section 2" "Page 3"))
(check-expect (find-word ch1 "is") (list "Chapter 1"
                                         "Section 1"
                                         "Page 1"
                                         "Page 2"
                                         "Section 2"
                                         "Page 3"))