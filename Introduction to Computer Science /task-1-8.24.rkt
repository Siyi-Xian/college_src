;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-reader.ss" "lang")((modname task-1-8.24) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
(require 2htdp/image)
(require 2htdp/universe)


(define wide (rectangle 100 5 "solid" "blue"))
(define tall (rectangle 5 100 "solid" "red"))


(define scene-height 160)
(define scene-width 192)

(define canavs (empty-scene scene-width scene-height))
(define re-ellipse (ellipse 40 10 "solid" "red"))

(define (hello nm) (string-append"hello, " nm))

(substring "hello" 1)
(substring "hello" 2)

; purpose: a function to say hello
; signiture:
; hello; name
; str:string, a name to say hello to
;
; examples
; "bob" -> "hello, bob"
; "wilma" -> "hello, wilma"

; substring
; purpose: a function which accepts a string, and a number,
; and hyphenates it at the given location
;
; signature:
; hyphenate: name
; str:string, the string to hyphenate
; index:number, the location where to hyphenate the string
; -> string, a hyphenated string
;
; example:
; "hello" 0 -> "-hello"
; "hello" 2 -> "he-llo"
; "hello" 1 -> "h-ello"

(substring "hello" 0 2)
(substring "hello" 1 3)

(define (hyphenate str index)
  (string-append (substring str 0 index) "-" (string-append str index)))