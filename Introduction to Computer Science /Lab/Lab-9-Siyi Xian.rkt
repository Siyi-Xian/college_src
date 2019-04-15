;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-reader.ss" "lang")((modname |Lab-9-Siyi Xian|) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
; <document>
;  <title size="really-big">This is a title</title>
;  <content>This is the <font type="italics">body</font> of
;      the document.</content>
; </document>

; An XExpr (X-expression) is one of:
; - Symbol
; - String
; - Number
; - (cons Symbol (cons [Listof Attribute] [Listof XExpr]))
 
; An Attribute is a:
; (list Symbol String)
; Interpretation: '(a "b") represents the attribute
; a = "b" in a piece of XML data

; Exercise 1

; XExpr -> Boolean
; determines whether some given XExpr has an attribute or contains a XExpr that has an attribute labeled 'content
(define (x-expression-has-content-attribute xe)
  (cond
    [(or (symbol? xe) (string? xe) (number? xe)) #false]
    [else (local [; Attribute -> Boolean
                  (define (attribute-is-content attr)
                    (symbol=? 'content (first attr)))]
     (or (ormap attribute-is-content (second xe))
         (ormap x-expression-has-content-attribute (rest (rest xe)))))]))
(check-expect (x-expression-has-content-attribute (list 'document
                                                        empty
                                                        "\n "
                                                        (list 'title (list (list 'size "really-big")) "This is a title")
                                                        "\n "
                                                        (list 'content empty "This is the "
                                                              (list 'font (list (list 'type "italics")) "body")
                                                              "of the document."))) #false)


; A [Maybe X] is one of:
; - #false
; - X
 
; xexpr-name : XExpr -> [Maybe Symbol]
; Extracts the name of the XExpr if there is one
(define (xexpr-name xe)
  (cond [(or (symbol? xe) (string? xe) (number? xe)) #false]
        [else (first xe)]))
 
; xexpr-attributes : XExpr -> [Listof Attributes]
; Extracts the attributes from the XExpr if there are any
(define (xexpr-attributes xe)
  (cond [(or (symbol? xe) (string? xe) (number? xe)) '()]
        [else (second xe)]))
 
; xexpr-content : XExpr -> [Listof XExpr]
; Extracts the content from the XExpr if there is any
(define (xexpr-content xe)
  (cond [(or (symbol? xe) (string? xe) (number? xe)) '()]
        [else (rest (rest xe))]))

; Exercise 2

; [List-of Attribute] Symbol -> [Maybe String]
; produces the value of the Attribute with that name if it exists in the list
(define (attribute-value la s)
  (local [; Attriblute [Maybe String] -> [Maybe String]
          (define (is-attribute-we-want? attr result)
            (if (false? result)
                (if (symbol=? s (first attr))
                    (second attr)
                    #false)
                result)
            #;
            (cond
              [(symbol=? (first attr) s) (second attr)]
              [else result]))]
    (foldl is-attribute-we-want? #false la)))
(attribute-value (list (list 'a "b") (list 'c "d") (list 'e "f")) 'c)


; Exercise 3
; Use search tool to search 184.77 ont the web
; The symbolic name of the x-expression that holds this data is meta
; 