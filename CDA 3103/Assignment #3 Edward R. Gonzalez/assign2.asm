;  PROGRAMMER:  Edward R. Gonzalez  954-895-6182
;
;  CLASS:       COP 2210-02   MW 11:00 a.m.
;
;  INSTRUCTOR:  Norman Pestaina  ECS 364
;
;  ASSIGNMENT:  #3
;
;  CERTIFICATION: I certify that this work is my own and that
;                 none of it is the work of any other person.
;
; R0: Char # User   | R2:               | R4: Ends Loop      | R6: 
; R1: Char # Offset | R3: Strings Local | R5: ST Match Local | R7:

;Main Program #################################################################
	.ORIG	x3000
	
	JSR 	STARTUP

LOOP	LDR	R1, R3, #0	;Load the current character from text source.
	ADD	R4, R1, #-4	;

	BRZ	FINLOOP		;Check if R4 is null.
	
	JSR	CCFS		;Go to check character from source subroutine.

	JSR	CMATCH		;Go to character match subroutine.

NEXT	ADD	R3, R3, #1	;Increments R3 to the next
	BRNZP	LOOP		;character of the text source.
 
FINLOOP	JSR 	PRCOUNT		;

	JSR	PLOFSET		;

DONE	
	TRAP	x25

;Variables
PNTR	.FILL	x3100
MATCHES	.FILL	x3500
NEWLN	.FILL	x0A
MSG	.STRINGZ " Matches:";
;Register Save Area
TEMP1R7 .FIll	x0


;Subroutine Startup*********************************************************L1*
STARTUP
	ST 	R7, TEMP1R7
	
	AND	R2, R2, #0	;Clear register 2.
	LD	R3, PNTR	;Set register 3 to the pointer to text source.
	LD	R5, MATCHES	;Load register 5 with the pointer to matches.
	TRAP	x23		;Get character from user.
	
	LD 	R7, TEMP1R7
	RET

;Subroutine Check character from source*************************************L1*
CCFS
        ST  	R7, TEMP1R7
 
        NOT 	R1, R1      	;Make register 1 negative
        ADD 	R1, R1, #1  	;------------------------
        ADD 	R1, R1, R0  	;Add user character to R1 to see if match.
 
        BRNP    NEXT        ;If not a match proceed to next character.
 
        LD  	R7, TEMP1R7
        RET
 
;Subroutine Character Match*************************************************L1*
CMATCH
        ST  	R7, TEMP1R7
 
        ADD 	R2, R2, #1  	;Increment register 1 indicating matching char.
        STR 	R3, R5, #0  	;Store address of matching character to save.
        ADD 	R5, R5, #1  	;Increment R5 to match next save offset.
   
        LD  	R7, TEMP1R7
        RET
 
;Subroutine Print count of matches******************************************L1*
PRCOUNT
        ST  	R7, TEMP1R7
   
        ;Parameters(UPRINT) (R0 = count, R1 = base)
        ADD 	R0, R2, #0  	;R0 = count
        AND 	R1, R1, #0  	;R1 = 10 (base, Deicmal)
        ADD 	R1, R1, #10 	;
        ;-----------------------------------------------
        JSR 	UPRINT      	;Display match count with above parameters.
 
        LD  	R7, TEMP1R7
        RET

;Subroutine Print Loop offsets for character matches************************L1*
PLOFSET
	ST 	R7, TEMP1R7
	
	LEA	R0, MSG		;Set R0 to message.
	TRAP	x22		;Print message.

	LD	R0, NEWLN	;Print new line.
	TRAP	x21		;---------------

	ADD	R1, R1, #6	;R1 = 16(base)(Line 40) Here for efficency.
	LD	R5, MATCHES	;Address of list of stored matches from source.

PRLOOP	ADD	R2, R2, #-1
	BRN	DONE

	;Parameters(UPRINT) (R0 = count, R1 = base)
	LDR	R0, R5, #0	;R0 = next match address
				;R1 see line 40.
	;-----------------------------------------------
	JSR	UPRINT		;Display 

	LD	R0, NEWLN

	TRAP	x21

	ADD	R5, R5, #1	;Advance to next
	BRNZP	PRLOOP

	LD 	R7, TEMP1R7
	RET

;Subroutine UPRINT**********************************************************L2*
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
	TRAP	x22		;Print number.

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


;Subroutine Divide**********************************************************L3*
DIVIDE	;R0: Numerator		R1: Divisor Register
	;R2: Quotient Register	R3: Accumulator register(REM)

	ST	R7, DIV7	;Save Working Registers	
	ST	R6, DIV6
	ST	R5, DIV5
	ST	R4, DIV4
	ST	R1, DIV1

	AND	R2, R2, #0	;Quo = 0
	ADD	R3, R0, #0	;Rem = Num

	AND	R7, R7, #0	;Clear R7
	ADD	R7, R1, #-1	;Add divisor/base to R7 -1.

	NOT	R1, R1
	ADD	R1, R1, #1	;Div = -Div

	ADD	R2, R0, #0	;Store Numerator to quotient register.
	

DIVLOOP ADD	R7, R7, #-1	;Decrement N-bits done by 1.
	BRZ	DIVEXIT		;Leave if R7 is zero.
	
	AND	R4, R4, #0	;Clear R4.
	LD	R4, MASK	;Load mask into register 4.
	
	AND	R4, R4, R2	;Check if there is a carry
	BRZ	BITSHFT		;Skip to bitshift.

	ADD	R3, R3, #1	;Add carry to accumulator register.

BITSHFT	ADD	R2, R2, R2	;Bitshift quotient to the left.
	
	AND	R6, R6, #0	;Get R6 ready for check.
	ADD	R6, R1, R3	;Subtract R3 form R1 to see if REM > DIV.

	BRP	DIVLESS		;Check if result of REM - divisor is positve.
	BRNZP	DIVLOOP

DIVLESS	ADD	R3, R1, R1	;Subtract divisor register from accumulator.
	
	AND	R5, R5, #0	;
	AND	R5, R2, #-1	;Set R5 to quotient low-bit only.
	BRP	DIVLOOP		;Restart loop early.

	ADD	R2, R2, #1	;
	BRNZP	DIVLOOP		;

DIVEXIT
	LD	R1, DIV1	;Restore Working Registers
	LD	R4, DIV4
	LD	R5, DIV5
	LD	R6, DIV6
	LD	R7, DIV7
	RET

DIV1	.BLKW	1
DIV4	.BLKW	1
DIV5	.BLKW	1
DIV6	.BLKW	1
DIV7	.BLKW	1

ACUREG	.BLKW	1
QUOREG	.BLKW	1
DIVREG	.BLKW	1
MASK	.FILL	x8000

	.END