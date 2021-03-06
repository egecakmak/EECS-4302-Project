package model.statement.assignment.expression.array;

import model.Instruction;
import model.Visitor;
import model.statement.assignment.Expression;
import model.statement.assignment.expression.ArrayOperations;
import model.values.Value;

import java.util.HashMap;
import java.util.Map;

public abstract class ArrayComposite extends ArrayOperations {
    protected Instruction array;
    protected Instruction inside;

    /*
     * Constructor
     *
     * @param left the left expression
     * @param right the right expression
     */
    public ArrayComposite(Instruction array, Instruction inside) {
        this.array = array;
        this.inside = inside;
    }

    /* Getter for array
     * @return Expression representing the left expression
     */
    public Instruction getArray() {
        return this.array;
    }

    /* Getter for inside
     * @return Expression representing the left expression
     */
    public Instruction getInside() {
        return this.inside;
    }

    @Override
    public void accept(Visitor visitor) {
        //No need to implement
    }

    @Override
    public Expression clone() {
        return null;
    }

    @Override
    public Map<String, Value> getVariables() {
        Map<String, Value> result = new HashMap<>();
        result.putAll(this.array.getVariables());
        result.putAll(this.inside.getVariables());
        return result;
    }


}