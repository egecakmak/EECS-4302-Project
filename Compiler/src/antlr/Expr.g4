grammar Expr;
/* The grammar name and file name must be the same*/

@header {
        package antlr;
}


// start variable
prog: (statement)+ EOF # Program
    ;

declaration: VARIABLE ID                   	# VariableDeclaration
            | VARIABLE ID '=' ID            # VariableInitializationConstantCopy
    		| VARIABLE ID '=' expression  	# VariableInitializationConstant
    		;

statement: declaration
         | assignment
         | conditional
         | assertedConditional
         ;

assertedConditional: 'if_require' '(' expression ')' conditional 'if_ensure' '(' expression ')' # ConditionalAssertionStatement
		;

// if and else statements can't have empty bodies
conditional: 'if' '(' logicalOp ')' '{' multAssig '}' elseIf		# IfConditional
		   ;

elseIf: 'else' 'if' '(' logicalOp ')' '{' multAssig '}'	elseIf		# ElseIfConditional
	  | 'else' '{' multAssig '}'									# ElseConditional
	  |  /* epsilon	*/												# EpsilonConditional
	  ;

multAssig: (assignment)+			# MultipleAssignments
		;

assignment: expression				# AssignExpression
		  | ID '=' expression  		# AssignAssignment
		  ;

expression: arithmeticOp			# ArithmeticOperation
		  | relationalOp			# RelationalOperation
		  | logicalOp				# LogicalOpteration
		  | '(' expression ')'		# ParanthesesExpression
		  ;

arithmeticOp: arithmeticOp '*' arithmeticOp		# MultiplicationArithmetic
			| arithmeticOp '/' arithmeticOp		# DivisionArithmetic
			| arithmeticOp '%' arithmeticOp		# ModuloArithmetic
			| arithmeticOp '+' arithmeticOp 	# AdditionArithmetic
			| arithmeticOp '-' arithmeticOp		# SubtractionArithmetic
			| ID								# VariableArithmetic
			| IntConstant						# IntegerConstant
			| '-' IntConstant					# NegationIntegerConstant
			;

relationalOp: arithmeticOp '<' arithmeticOp			# LessRelational
			| arithmeticOp '<=' arithmeticOp		# LessEqualRelational
			| arithmeticOp '>' arithmeticOp			# GreaterRelational
			| arithmeticOp '>=' arithmeticOp		# GreaterEqualRelational
			| arithmeticOp '==' arithmeticOp		# EqualityRelational
			| arithmeticOp '!=' arithmeticOp		# InequivalenceRelational
			;

logicalOp: '!' logicalOp						# NegationLogical
		 | logicalOp '&&' logicalOp				# ConjunctionLogical
		 | logicalOp '||' logicalOp				# DisjunctionLogical
		 | logicalOp '=>' logicalOp				# ImplicationLogical
		 | logicalOp '<=>' logicalOp			# EquivalenceLogical
		 | relationalOp							# RelationalOpLogical
		 | ID									# VariableLogical
		 | BoolConstant							# BooleanConstant
		 ;


//VARIABLE: 'int' | 'bool';
//ID: [a-z][a-zA-Z0-9_]*;
//IntConstant : [0-9][1-9]*;
//WS : [ \t\n\r]+ -> skip;
	/* Tokens */
    VARIABLE: 'int' | 'bool';
	IntConstant : [0-9][1-9]*;
	BoolConstant: 'true' | 'false';
	ConstantValue: IntConstant | BoolConstant;
	ID: [a-z][a-zA-Z0-9_]*;
	COMMENT: '//' ~[\r\n]* -> skip;
	WS : [ \t\n\r]+ -> skip ;