package model.statement.assignment.expression.arithmetic;

import model.Value;
import model.Visitor;
import model.statement.assignment.Expression;
import model.statement.assignment.expression.Arithmetic;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class IntegerConstant extends Arithmetic {
	private int value;

	/*
	 * Constructor
	 * 
	 * @param value the assigned value of the variable in the expression
	 */
	public IntegerConstant(int value) {
		this.value = value;
	}


    /*
     * retrieve the value of the variable expression
     *
     * @return retrieve the value of the variable expression
     */
    public int getValue() {
        return this.value;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitIntegerConstant(this);
    }


    @Override
    public Map<String, Value> getVariables() {
        Map<String, Value> result = new HashMap<>();
        return result;
    }

    @Override
    public Expression clone() {
        return new IntegerConstant(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntegerConstant that = (IntegerConstant) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

