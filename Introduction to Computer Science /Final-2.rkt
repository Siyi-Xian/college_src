;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-lambda-reader.ss" "lang")((modname Final-2) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
(define-struct playlist (song artist next-song))

(define list1 (make-playlist "A" "Tom" empty))
(define list2 (make-playlist "B" "Bob" list1))
(define list3 (make-playlist "C" "Jack" list2))

(define (beginning p n)
  (cond
    [(zero? n) empty]
    [else (make-playlist (playlist-song p)
                         (playlist-artist p)
                         (beginning (playlist-next-song p)
                                    (sub1 n)))]))
(check-expect (beginning empty 0) empty)
(check-expect (beginning list3 1) (make-playlist "C" "Jack" empty))