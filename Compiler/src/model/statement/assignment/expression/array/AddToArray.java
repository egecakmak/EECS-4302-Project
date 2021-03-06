package model.statement.assignment.expression.array;

import model.Instruction;
import model.Visitor;
import model.statement.assignment.Expression;
import model.values.Value;

import java.util.HashMap;
import java.util.Map;

public class AddToArray extends ArrayComposite {
    /**
     * Constructor
     *
     * @param array
     * @param inside
     */
    public AddToArray(Instruction array, Instruction inside) {
        super(array, inside);
    }

    @Override
    public Expression clone() {
        return new AddToArray(((Expression) array).clone(), ((Expression) inside).clone());
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitAddToArray(this);
    }

    @Override
    public Map<String, Value> getVariables() {
        Map<String, Value> result = new HashMap<>();
        result.putAll(this.array.getVariables());
        result.putAll(this.inside.getVariables());
        return result;
    }
}