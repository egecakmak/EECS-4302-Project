package model.statement.assignment;

import model.statement.Assignment;


public class ExpressionAssignment extends Assignment {
	private String id;
	private Expression expr;

	/*
	 * Constructor
	 * 
	 * @param left the left expression
	 * 
	 * @param right the right expression
	 */
	public ExpressionAssignment(String id, Expression expr) {
		this.id = id;
		this.expr = expr;
	}


	public Expression getExpr() {
		return this.expr;
	}

	public String getID() {
		return this.id;
	}

}
