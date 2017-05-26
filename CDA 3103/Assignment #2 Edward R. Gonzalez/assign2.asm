;  PROGRAMMER:  Edward R. Gonzalez  954-895-6182
;
;  CLASS:       COP 2210-02   MW 11:00 a.m.
;
;  INSTRUCTOR:  Norman Pestaina  ECS 364
;
;  ASSIGNMENT:  #1 Arithmetic & Simple I/O.   DUE: 4/16
;
;  CERTIFICATION: I certify that this work is my own and that
;                 none of it is the work of any other person.
;
; R0: Char # User   | R2:               | R4: Ends Loop      | R6: 
; R1: Char # Offset | R3: Strings Local | R5: ST Match Local | R7:

;Main Program ###########################################################################
	.ORIG	x3000

	JSR	STARTUP		;Run Startup routine.
	
LOOP	JSR	GCFS		;Call the get char subroutine.

	JSR	CCFM		;Call the character checking subroutine.

INCR3	ADD	R3, R3, 1	;Set R3 to next offset.
	BR	LOOP		;Move on to next offset of source.

;Variables
ASCII	.FILL	x0030	;ASCII '0'
NEWLN	.FILL	x0A	;ASCII new-line


;Subroutine Startup*******************************************************************L1*
STARTUP	ST	R7, TEMP1R7

	LD	R5, MATCHES	;Load matches into R5.
	AND	R2, R2, 0	;Clearing R2.
	LD	R3, PNTR	;Load Pointer to strings into R3.

	TRAP	x23		;Get a character from the user.

	LD	R7, TEMP1R7
	RET

;Variables
MATCHES	.FILL	x3500	;Location to start storing match offset values.
PNTR	.FILL	x3100	;String Address	


;Subroutine Get char from source******************************************************L1*
GCFS	ST	R7, TEMP1R7
	LDR	R1, R3, 0	;Get char # in offset source R5.
	ADD	R4, R1, -4	;Used for the branch to finish check.

	BRZ	FINISH		;Call Finish subroutine if R3 offset is null.	

	LD	R7, TEMP1R7
	RET


;Subroutine Check Chars for match*****************************************************L1*
CCFM	ST	R7, TEMP1R7
	NOT	R1, R1		;Complement R1.
	ADD	R1, R1, 1	;Increment R1. At this point R1 is negative of char val.
	ADD	R1, R1, R0	;Add R0 to R1. Final value must be zero for match...
	
	BRNP	INCR3		;If not a match end subroutine early.
	
	ADD	R2, R2, 1	;Increment the match counter by 1.

	JSR	STMATCH		;Go to STMATCH Subroutine.

	LD	R7, TEMP1R7
	RET


;Subroutine Store Matches*************************************************************L2*
;Stores offsets that have matching characters.
STMATCH	

	STR	R3, R5, 0	;Store R3 at location R5.
	ADD	R5, R5, 1	;Increment R5 by 1.
	RET


;Subroutine Finish********************************************************************L1*
FINISH	
	JSR	UPRINT		;Call UPRINT.
	TRAP	x25
	
	RET


;Subroutine UPRINT********************************************************************L2*
;Displays an unsigned integer in any base up to 16, e.g. binary, octal, decimal
;Parameters - R0: the integer to be printed
;	    - R1: the base - 2, 8, 10, 16
UPRINT
	ST	R0, TEMP1R0	; (May use L1 save as program ends with finish)
	ST	R1, TEMP1R1	;
	ST	R2, TEMP1R2	;
	ST	R3, TEMP1R3	;
	ST	R4, TEMP1R4	;
	ST	R5, TEMP1R5	;
	ST	R6, TEMP1R6	;
	ST	R7, TEMP1R7	;

	ST	R2, MCOUNT	;Store number of matches into VALUE
	
;Set Divisor
	AND	R1, R1, 0	;Clear R1
	ADD	R1, R1, 5	;
	ADD	R1, R1, 5	;Set base to 10.
	ST	R1, BASE	;Store Divisor in base.

;Print Number
	ST	R2, OUTNUM	;Set outnumber to quotient.
	JSR	PRNUM		;Call the print number subroutine.

	LD	R0, TEMP1R0	;
	LD	R1, TEMP1R1	;
	LD	R2, TEMP1R2	;
	LD	R3, TEMP1R3	;
	LD	R4, TEMP1R4	;
	LD	R5, TEMP1R5	;
	LD	R6, TEMP1R6	;
	LD	R7, TEMP1R7	;
	LD	R7, TEMP1R7	;
	RET


;Variables
DIGITS	.STRINGZ "0123456789ABCDEF"	;Digits
	.BLKW	18	-1		;Output Buffer
BUFFER	.FILL	x0			;Null
QUOTIE	.FILL 	x0		;
REMAIN	.FILL	x0		;
MCOUNT	.FILL	x0		;
BASE	.FILL	x0		;
OUTNUM	.FILL	x0		;


;Subroutien Print Number**************************************************************L2*
PRNUM
	ST	R0, TEMP2R0
	ST	R1, TEMP2R1
	ST	R2, TEMP2R2
	ST	R3, TEMP2R3
	ST	R4, TEMP2R4
	ST	R5, TEMP2R5
	ST	R6, TEMP2R6
	ST	R7, TEMP2R7

	AND	R0, R0, 0	;
	AND	R1, R1, 0	;
	AND	R2, R2, 0	;
	AND	R3, R3, 0	;
	AND	R4, R4, 0	;
	AND	R5, R5, 0	;
	AND	R6, R6, 0	;
	AND	R7, R7, 0	;Clear registers.

;Startup	
	LEA 	R0, BUFFER	;Load the buffer address to R0.
	LD	R1, OUTNUM	;Load number to print into R1.
	LD	R2, BASE	;Load base to R2.
	LD	R6, ASCII	;Load ascii offset to R6.

	BR	POPARRY

CONVERT	LEA	R0, BUFFER	;
	ADD	R0, R0, -1	;
	

ASCIARY	LDR 	R4, R0, 0	;Load the value at address to R4.
	BRN	PRINT		;Go to print if result is zero.
	ADD	R4, R4, R6	;Add ascii value to array.
	STR	R4, R0, 0	;Store resulting value in R4 to address.
	ADD	R0, R0, -1	;Decrement address.
	BR	ASCIARY		;Loop.

;Populate array loop.
POPARRY	ST	R1, MCOUNT	;Store OUTNUM to MCOUNT
	JSR	DIVIDE		;Call divide.
	LD	R5, REMAIN	;Load remainder to R5
	LD	R3, QUOTIE	;Load quotient  to R3
	ADD	R0, R0, -1	;Decremetn Address.
	ADD	R3, R3, 0	;Set focus to R3.

	BRZ	SKIP		;Go skip stroing of quotient if zero.

	STR	R3, R0, 0	;Store result into address
	ST	R5, MCOUNT	;Set the remainder as the MCOUNT for the next position.
	ADD	R5, R5, 0	;

SKIP	BRP	POPARRY		;Goto Here if remainder is positive.

	STR	R5, R0, 0		;
	BRNZP	CONVERT		;Loop.
	
PRINT	LEA	R0, BUFFER	;
	ADD	R0, R0, -1	;
	PUTs			;

	LD	R0, TEMP2R0
	LD	R1, TEMP2R1
	LD	R2, TEMP2R2
	LD	R3, TEMP2R3
	LD	R4, TEMP2R4
	LD	R5, TEMP2R5
	LD	R6, TEMP2R6
	LD	R7, TEMP2R7
	RET
	
	
;Subroutine DIVIDE********************************************************************L3*
;Calculate the quotient and remainder of a simple unsigned integer division
;Parameters	R0: Value	R1: Divisor   R4: NOT-Div
;	    	R2: Quotient	R3: Remainder R5: Work.
DIVIDE
	ST	R0, TEMP3R0
	ST	R1, TEMP3R1
	ST	R2, TEMP3R2
	ST	R3, TEMP3R3
	ST	R4, TEMP3R4
	ST	R5, TEMP3R5
	ST	R6, TEMP3R6
	ST	R7, TEMP3R7
;Setup
	AND	R0, R0, 0	;Clear R0.
	AND	R1, R1, 0	;Clear R1.
	AND	R2, R2, 0	;Clear R2.
	AND	R3, R3, 0	;CLear R3.
	AND	R4, R4, 0	;CLear R4.
	AND	R5, R5, 0	;Clear R5.
	AND	R6, R6, 0	;Clear R6.

	LD	R0, MCOUNT	;Load #Matches into R0.
	LD	R5, MCOUNT	;Load #Matches into R5
	LD	R1, BASE	;Load divisor into R1.
	LD	R4, BASE	;Load divisor into R4.

	NOT	R4, R4		;Complement R4
	ADD	R4, R4, 1	;Make R4 negative of R1.
	

;Operation
OPER	ADD	R5, R5, R4	;Add negative of divisor to R2.
	BRNZ	RDIV		;If result is negative leave BSHIFT.
	ADD	R2, R2, 1	;Increment quotient.
	BR	OPER		;Loop BSHIFT.


RDIV	ST	R2, QUOTIE	;Store Counter in R6.
	ADD	R5, R5, R1	;Add divisor to work.
	ST	R5, REMAIN	;Store the remaineder in REMAIN.

	LD	R0, TEMP3R0
	LD	R1, TEMP3R1
	LD	R2, TEMP3R2
	LD	R3, TEMP3R3
	LD	R4, TEMP3R4
	LD	R5, TEMP3R5
	LD	R6, TEMP3R6
	LD	R7, TEMP3R7
	RET


;Register Save Area 
;(Level 1)
TEMP1R0	.FILL	x0
TEMP1R1	.FILL	x0
TEMP1R2	.FILL	x0
TEMP1R3	.FILL	x0
TEMP1R4	.FILL	x0
TEMP1R5	.FILL	x0
TEMP1R6	.FILL	x0
TEMP1R7	.FILL	x0
;(Level 2)
;Divide's Register Save area.
TEMP2R0	.FILL x0
TEMP2R1	.FILL x0
TEMP2R2	.FILL x0
TEMP2R3	.FILL x0
TEMP2R4	.FILL x0
TEMP2R5	.FILL x0
TEMP2R6	.FILL x0
TEMP2R7	.FILL x0
;(Level 3)
TEMP3R0	.FILL x0
TEMP3R1	.FILL x0
TEMP3R2	.FILL x0
TEMP3R3	.FILL x0
TEMP3R4	.FILL x0
TEMP3R5	.FILL x0
TEMP3R6	.FILL x0
TEMP3R7	.FILL x0


	.END