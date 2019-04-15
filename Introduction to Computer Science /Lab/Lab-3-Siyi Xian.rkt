;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-reader.ss" "lang")((modname Lab-3) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
(define-struct Person
  (name age sex))

(define tom (make-Person "Tom" 18 "M"))
(define marry (make-Person "Marry" 17 "F"))

(define (is-audlt person)
  (>= (Person-age person) 18))

(check-expect (is-audlt tom) #true)
(check-expect (is-audlt marry) #false)

(define (is-sibling person)
  (cond
    [(string=? (Person-name person) "Tom") "marry"]
    [(string=? (Person-name person) "Marry") "tom"]))

(check-expect (is-sibling tom) "marry")

(define-struct photo (image tag))
; A photo is a structure:
;   (make-photo iamge string)

(define-struct 3d (x y z))
; A 3d is a structure:
;   (make-3d number number number)



(define-struct item (tag price))
; An Item is a structure:
;   (make-item String PositiveNumber)

(define book (make-item "Harry Potter" 106))
(define table (make-item "IKEA-table" 25))
 
(define-struct AI (name field pay-rate))
; An AI is (represented by) a structure:
;   (make-AI String Field PositiveNumber)
; A Field is one of:
; – "biology"
; – "english"
; – "computer science"
; - "business"

(define jerry (make-AI "Jerry" "computer science" 20))
(define bob (make-AI "Bob" "english" 15))

(define-struct Cat (name species age feeding-time))
; A Cat is a structure
;   (make-Cat string string PositiveNumber string)

(define (Book name)
  (...(item-price name)...(item-tag name)...))

(define (what name)
  (...(AI-name name)...(AI-field name)...(AI-pay-rate name)...))

(define (when name)
  (...(Cat-name name)...(Cat-species name)...(Cat-age name)...(Cat-feeding-time name)...))


; Pay-raise is a function that give a new structure of AI from A old AI structure
; AI number -> AI
(define (pay-raise name multi)
  (make-AI (AI-name name) (AI-field name) (* multi (AI-pay-rate name))))

(check-expect (pay-raise jerry 3) (make-AI "Jerry" "computer science" 60))


