
	.ORIG	x3000

	AND	R2, R2, #0
	LD	R3, PNTR
	LD	R5, HITS
	TRAP	x23
	LDR	R1, R3, #0

LOOP	ADD	R4, R1, #-4
	BRZ	EXIT
	NOT	R1, R1
	ADD	R1, R1, #1
	ADD	R1, R1, R0
	BRNP	NEXT
	ADD	R2, R2, #1
	STR	R3, R5, #0
	ADD	R5, R5, #1
NEXT	ADD	R3, R3, #1
	LDR	R1, R3, #0
	BRNZP	LOOP
EXIT
	ADD	R0, R2, #0	;R0 = count
	AND	R1, R1, #0	;R1 = 10 (base)
	ADD	R1, R1, #10
	JSR	UPRINT		;Display count in decimal
	LEA	R0, MSG
	TRAP	x22
	LD	R0, NEWLN
	TRAP	x21

	ADD	R1, R1, #6	;R1 = 16 (base)
	LD	R5, HITS	;R5 = address of hits-list
AGAIN
	ADD	R2, R2, #-1
	BRN	DONE
	LDR	R0, R5, #0	;R0 = next hit address
	JSR	UPRINT		;Display 
	LD	R0, NEWLN
	TRAP	x21
	ADD	R5, R5, #1	;Advance to next
	BRNZP	AGAIN
DONE	
	TRAP	x25
;Variables
PNTR	.FILL	x3100
HITS	.FILL	x3500
NEWLN	.FILL	x0A
MSG	.STRINGZ " Matches:"


;Subroutine UPRINT*************************************************************
;Displays an unsigned integer in any base up to 16, e.g. binary, octal, decimal
;Parameters - R0: the integer - R1: the base
UPRINT
	ST	R7, UPR7
	ST	R6, UPR6
	ST	R5, UPR5
	ST	R3, UPR3
	ST	R2, UPR2
	ST	R0, UPR0

	LEA	R5, DIGITS	;R5 points to DIGITS string
	LEA	R6, BUFFER	;R6 points to BUFFER base
REPEAT
	JSR	DIVIDE		;Number / Base
	ADD	R3, R3, R5	;R3 = DIGITS + Rem
	LDR	R3, R3, #0	;R3 = ASCII(Rem)
	ADD	R6, R6, #-1	;Index into buffer
	STR	R3, R6, #0	;Store into buffer
	ADD	R0, R2, #0	;Number = Quotient
	BRNP	REPEAT		;Repeat if != 0

	ADD	R0, R6, #0	;Display
	TRAP	x22

	LD	R0, UPR0
	LD	R2, UPR2
	LD	R3, UPR3
	LD	R5, UPR5
	LD	R6, UPR6
	LD	R7, UPR7
	RET
;Data
DIGITS	.STRINGZ "0123456789ABCDEF"	;Digits
	.BLKW	18			;Output Buffer
BUFFER	.FILL	x0000			;Null
UPR0	.BLKW	1
UPR2	.BLKW	1
UPR3	.BLKW	1
UPR5	.BLKW	1
UPR6	.BLKW	1
UPR7	.BLKW	1

DIVIDE	;R0: Numerator	R1: Divisor
	;R2: Quotient	R3: Remainder
	ST	R7, DIV7	;Save Working Registers
	ST	R4, DIV4
	ST	R1, DIV1

	AND	R2, R2, #0	;Quo = 0
	ADD	R3, R0, #0	;Rem = Num
	NOT	R1, R1
	ADD	R1, R1, #1	;Div = -Div
DIVLOOP
	ADD	R4, R3, R1	;R4 = Rem - Div
	BRN	DIVEXIT
	ADD	R2, R2, #1	;Quo = Quo + 1
	ADD	R3, R4, #0	;Rem = Rem - Div
	BRNZP	DIVLOOP
DIVEXIT
	LD	R1, DIV1	;Restore Working Registers
	LD	R4, DIV4
	LD	R7, DIV7
	RET
DIV1	.BLKW	1
DIV4	.BLKW	1
DIV7	.BLKW	1

	.END