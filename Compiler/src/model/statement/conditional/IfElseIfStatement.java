package model.statement.conditional;

import model.Evaluator;
import model.Instruction;
import model.Statement;
import model.Visitor;
import model.statement.MultiAssignment;

import java.util.List;

public class IfElseIfStatement extends Statement {
	private Instruction logicalCondition;
	private Instruction assignments;
	private List<IfElseIfStatement> elseIfStatments;
	private IfElseIfStatement elseStatment;


	public IfElseIfStatement(Instruction condition, Instruction assignments, List<IfElseIfStatement> elseIfStatments) {
		this.logicalCondition = condition;
		this.assignments = assignments;
		this.elseIfStatments = elseIfStatments;
	}

	/**
	 * @return the logicalCondition
	 */
	public Instruction getCondition() {
		return logicalCondition;
	}

	/**
	 * @return the assignments
	 */
	public Instruction getAssignments() {
		return assignments;
	}

	/**
	 * @return the elseIfStatments
	 */
	public List<IfElseIfStatement> getElseIfStatments() {
		return elseIfStatments;
    }


    /**
     * @return the elseStatment
     */
    public IfElseIfStatement getElseStatment() {
        return elseStatment;
    }

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		MultiAssignment assignments = (MultiAssignment) this.assignments;
		assignments.getAssignments().forEach(each -> sb.append("\n\t").append(each));
		sb.append("else");
		sb.append(elseStatment);
		return sb.toString();
	}

	@Override
	public void accept(Visitor visitor) {
		Evaluator ev = new Evaluator();
		this.logicalCondition.accept(ev);
		boolean ifConditionMeets = Evaluator.getBoolVal(ev);
		if (ifConditionMeets) {
			ev = new Evaluator();
		}
	}
}
