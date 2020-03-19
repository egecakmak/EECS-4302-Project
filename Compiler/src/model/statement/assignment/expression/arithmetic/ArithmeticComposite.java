package model.statement.assignment.expression.arithmetic;

import java.util.HashMap;
import java.util.Map;

import model.Instruction;
import model.Value;
import model.statement.assignment.expression.Arithmetic;


public abstract class ArithmeticComposite extends Arithmetic {
    protected Instruction left;
    protected Instruction right;

    /*
     * Constructor
     *
     * @param left the left expression
     *
     * @param right the right expression
     */
    public ArithmeticComposite(Instruction left, Instruction right) {
        this.left = left;
        this.right = right;
    }

    /*
     * getter for the left expression
     *
     * @return Expression representing the left expression
     */
    public Instruction getLeftExpr() {
        return this.left;
    }

    /*
     * getter for the left expression
     *
     * @return Expression representing the left expression
     */
    public Instruction getRightExpr() {
        return this.right;
    }
    
	@Override
	public Map<String, Value> getVariables() {
		Map<String,Value> result = new HashMap<>();
		result.putAll(this.left.getVariables());
		result.putAll(this.right.getVariables());
		return result;
	}

}
