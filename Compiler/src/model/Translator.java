package model;

import model.statement.MultiAssignment;
import model.statement.assignment.ExpressionAssignment;
import model.statement.assignment.expression.Logical;
import model.statement.assignment.expression.ParanthesesExpression;
import model.statement.assignment.expression.arithmetic.*;
import model.statement.assignment.expression.logical.*;
import model.statement.assignment.expression.relational.*;
import model.statement.conditional.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Translator implements Visitor {

	private int statementsTranslated;

	private List<String> result;

	private Map<String, String> resultMap;

	private Map<String, String> originalToAlloy;

	private final String fieldName = "field";

	private String finalResult;

	private String postOldSyntax;

	private String trueInAlloy;

	private String falseInAlloy;

	private boolean inEnsure;
	
	public Translator() {
		finalResult = "";
		postOldSyntax = "";
		inEnsure = false;
		falseInAlloy = "False";
		trueInAlloy = "True";
		statementsTranslated = 0;
		result = new ArrayList<>();
		resultMap = new HashMap<>();
		originalToAlloy = new HashMap<>();
	}

	public Translator(Map<String, String> originalToAlloy, String postOldSyntax, boolean inEnsure) {
		finalResult = "";
		trueInAlloy = "True";		
		falseInAlloy = "False";
		this.inEnsure = inEnsure;
		statementsTranslated = 0;
		result = new ArrayList<>();
		resultMap = new HashMap<>();
		this.originalToAlloy = originalToAlloy;
		this.postOldSyntax = postOldSyntax;
	}

	// no re-assignment in ensure
	// ((x>y) and (x_new == x+2+3+1 and y_new==y) and (z = z_old)) or ((x<=y)
	// and (x = x_old and y = y_old+1)))
	// (not(x==x_old) or (x == x_old+2+3+1 and y==y_old)) or ((x<=y) and (x =
	// x_old and y = y_old+1)))
	// ((n.arg1 > n.arg2 and
	// addOneConditionalEnsure[n.arg1,n.arg2,n.arg1.add[1],n.arg2]) or (n.arg1
	// <= n.arg2 and
	// addOneConditionalEnsure[n.arg1,n.arg2,n.arg1,n.arg2.add[1]]))

	/**
	 * @return the statementsTranslated
	 */
	public void incrementStatementsTranslated() {
		statementsTranslated++;
	}

	/**
	 * @return the statementsTranslated
	 */
	public int getStatementsTranslated() {
		return statementsTranslated;
	}

	/**
	 * @return the result
	 */
	public List<String> getResult() {
		return result;
	}

	/**
	 * @return the resultMap
	 */
	public Map<String, String> getResultMap() {
		return resultMap;
	}

	/**
	 * @return the originalToAlloy
	 */
	public Map<String, String> getOriginalToAlloy() {
		return originalToAlloy;
	}

	/**
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * @return the finalResult
	 */
	public String getFinalResult() {
		return finalResult;
	}

	/**
	 * @return the postOldSyntax
	 */
	public String getPostOldSyntax() {
		return postOldSyntax;
	}

	@Override
	public void visitConditionalAssertionStatement(AssertedConditional exp) {

		String stateName = "state";
		String funName = "funStatement";
		String assertName = "assertStatement";
		Map<String, Value> vars = exp.getVariables();

		StringBuilder sigVarialesSB = new StringBuilder();
		StringBuilder funParamSB = new StringBuilder();
		StringBuilder postOldSyntaxSB = new StringBuilder();
		
		if(this.statementsTranslated == 0){
			sigVarialesSB.append("open logicFuncs\n");	
		}		
		stateName +=this.statementsTranslated ; 
		

		
		sigVarialesSB.append("sig ").append(stateName).append("{");
		postOldSyntaxSB.append("all field: ").append(funName).append(this.getStatementsTranslated()).append(" [");

		Map<String, String> postOriginalToAlloy = new HashMap<>();
		Map<String, String> preOriginalToAlloy = new HashMap<>();

		int counter = 1;
		for (String each : vars.keySet()) {
			String alloyVar = "arg" + (counter);
			String originalVar = each;
			String varType = vars.get(originalVar).getType();

			preOriginalToAlloy.put(originalVar, "n." + alloyVar);
			postOriginalToAlloy.put(originalVar, this.getFieldName() + "." + alloyVar);
			postOriginalToAlloy.put(originalVar + "_old", "n." + alloyVar);

			sigVarialesSB.append("\n\t");
			sigVarialesSB.append(alloyVar).append(":").append(varType).append(",");
			funParamSB.append("p").append(alloyVar).append(":").append(varType).append(",");
			postOldSyntaxSB.append("n.").append(alloyVar).append(",");
			counter++;
		}

		sigVarialesSB.deleteCharAt(sigVarialesSB.lastIndexOf(","));
		sigVarialesSB.append("\n}\n\n");

		funParamSB.deleteCharAt(funParamSB.lastIndexOf(","));
		String funParamString = funParamSB.toString();

		postOldSyntaxSB.deleteCharAt(postOldSyntaxSB.lastIndexOf(","));
		postOldSyntaxSB.append("] | { ");
		postOldSyntax = postOldSyntaxSB.toString();

		Translator functionTranslator = new Translator(preOriginalToAlloy, postOldSyntax, false);
		exp.getIfStatment().accept(functionTranslator);
		List<String> functionTranslated = functionTranslator.getResult();
		StringBuilder functionSB = new StringBuilder();
		functionTranslated.forEach(functionSB::append);
		String functionTranslatedString = functionSB.toString();

		sigVarialesSB.append("fun ").append(funName).append(this.getStatementsTranslated()).append(" (")
				.append(funParamString).append(") : ").append(stateName).append(" {\n");
        sigVarialesSB.append("\t{ n : ").append(stateName).append(" | \n\t\t");
        sigVarialesSB.append(functionTranslatedString).append(" \n\t}\n}\n\n");

        Translator precondTranslator = new Translator(preOriginalToAlloy, postOldSyntax, false);
        exp.getPreCond().accept(precondTranslator);
        List<String> precondTranslated = precondTranslator.getResult();
        StringBuilder precondSB = new StringBuilder();
        precondTranslated.forEach(precondSB::append);
        String precondTranslatedString = precondSB.toString();

        Translator postcondTranslator = new Translator(preOriginalToAlloy, postOldSyntax, true);
        exp.getPostCond().accept(postcondTranslator);
        List<String> postcondTranslated = postcondTranslator.getResult();
        StringBuilder postcondSB = new StringBuilder();
        postcondTranslated.forEach(postcondSB::append);
        String postcondTranslatedString = "("+ postcondSB.append(")").toString();

        sigVarialesSB.append("assert ").append(assertName).append(this.getStatementsTranslated()).append(" {\n");
        sigVarialesSB.append("\t all n: ").append(stateName).append(" | (").append(precondTranslatedString)
                .append(")" + " in True => (").append(postcondTranslatedString).append(" in True").append(")\n}\n\n");

		sigVarialesSB.append("check ").append(assertName).append(this.getStatementsTranslated());

		this.incrementStatementsTranslated();

		this.finalResult = sigVarialesSB.toString();
	}

	@Override
	public void visitIfConditional(IfElseIfStatement exp) {
		Map<String, String> parameterNameMap = new HashMap<>();
		for (String key : originalToAlloy.keySet()) {
			parameterNameMap.put(key, originalToAlloy.get(key).replaceFirst("n.", "p"));
		}

		Translator conditionTranslator = new Translator(parameterNameMap, postOldSyntax, this.inEnsure);
		exp.getCondition().accept(conditionTranslator);

		Translator ifTranslator = new Translator(parameterNameMap, postOldSyntax, this.inEnsure);
		exp.getAssignments().accept(ifTranslator);


		// Parse ElseIf if there is any 	
		List<List<String>> elseIfConditions = new ArrayList<>();
		List<Map<String, String>> elseIfAssignments = new ArrayList<>();
		for (ElseIfStatement stmt : exp.getElseIfStatments()) {
			Translator elseIfTranslator = new Translator(parameterNameMap, postOldSyntax, this.inEnsure);
			stmt.accept(elseIfTranslator);
			elseIfConditions.add(elseIfTranslator.getResult());
			elseIfAssignments.add(elseIfTranslator.getResultMap());
		}
		assert (elseIfAssignments.size() == elseIfConditions.size());

		Translator elseTranslator = new Translator(parameterNameMap, postOldSyntax, this.inEnsure);
		if (exp.getElseStatment() != null) {
			exp.getElseStatment().getAssignments().accept(elseTranslator);
		}

//		System.out.println("getOriginalToAlloy: " + this.getOriginalToAlloy().toString());
//		System.out.println("parameterNameMap: " + parameterNameMap.toString());
//		System.out.println("If Updates: " + ifTranslator.resultMap.toString());
//		System.out.println("Else Updates: " + elseTranslator.resultMap.toString());
//		System.out.println("ElseIf conditions: " + elseIfConditions.toString());
//		System.out.println("ElseIf  Updates: " + elseIfAssignments.toString());

		for (String key : this.getOriginalToAlloy().keySet()) {
			this.result.add(this.getOriginalToAlloy().get(key));
			this.result.add(" = ((");
			this.result.addAll(conditionTranslator.getResult());
			this.result.add(") in True => ");
			String assignment = ifTranslator.resultMap.get(parameterNameMap.get(key));
			if (assignment == null) {
				assignment = parameterNameMap.get(key);
			}
			this.result.add(assignment);

			for (int i = 0; i < elseIfConditions.size(); i++) {
				if (elseIfAssignments.get(i).containsKey(parameterNameMap.get(key))) {
					this.result.add(" else");
					this.result.add(" ( ");
					this.result.addAll(elseIfConditions.get(i));
					this.result.add(" )");
					this.result.add(" in True => ");
					this.result.add(elseIfAssignments.get(i).get(parameterNameMap.get(key)));
					this.result.add(" ");
				}
			}

			this.result.add(" else ");
			String elseAssignment = parameterNameMap.get(key);
			if (exp.getElseStatment() != null) {
				if (elseTranslator.resultMap.containsKey(parameterNameMap.get(key))) {
					elseAssignment = elseTranslator.resultMap.get(parameterNameMap.get(key));
				}
			}
			this.result.add(elseAssignment);

			this.result.add(" )\n\t\t");
			this.result.add(" and ");
		}
		this.result.remove(this.result.size() - 1);
	}

	@Override
	public void visitElseIfConditional(ElseIfStatement exp) {
		Translator conditionTranslator = new Translator(originalToAlloy, postOldSyntax, this.inEnsure);
		exp.getCondition().accept(conditionTranslator);
		this.result.addAll(conditionTranslator.getResult());
		
		Translator assignmentTranslator = new Translator(originalToAlloy, postOldSyntax, this.inEnsure);
		exp.getAssignments().accept(assignmentTranslator );
		this.resultMap.putAll(assignmentTranslator.getResultMap());
		
	}
	
	@Override
	public void visitMultipleAssignments(MultiAssignment exp) {
		for (Instruction inst : exp.getAssignments()) {
			Translator instTranslator = new Translator(originalToAlloy, postOldSyntax, this.inEnsure);
			inst.accept(instTranslator);
			int size = instTranslator.getResult().size();
//			System.out.println("SIZE : "+ size);
			assert (instTranslator.getResult().size() == 2);
			String key = instTranslator.getResult().get(0);
			String value;
			if (inst instanceof IfElseIfStatement || inst instanceof AssertedConditional) {
				StringBuilder valueBuilder = new StringBuilder();
//				valueBuilder.append("(");
				instTranslator.getResult().subList(2, instTranslator.getResult().size() - 1).forEach(each -> valueBuilder.append(each));
//				valueBuilder.append(")");
				value = valueBuilder.toString();
			} else {
				value = instTranslator.getResult().get(1);
			}
			this.resultMap.put(key, value);
		}
	}

	public void visitAssignExpression(ExpressionAssignment exp) {
		String alloyVarName = this.originalToAlloy.get(exp.getID());
		this.result.add(alloyVarName);
		Translator rhsTranslator = new Translator(originalToAlloy, postOldSyntax, this.inEnsure);
		exp.getExpr().accept(rhsTranslator);
		StringBuilder rhsResult = new StringBuilder();
		for (String str: rhsTranslator.getResult()){
			rhsResult.append(str);
		}
		
		this.result.add(rhsResult.toString());
	}

	@Override
	public void visitParanthesesExpression(ParanthesesExpression exp) {
		Translator innerTranslator = new Translator(originalToAlloy, postOldSyntax, this.inEnsure);
		exp.getExpression().accept(innerTranslator);
		result = innerTranslator.getResult();
	}

	@Override
	public void visitDivisionArithmetic(Division exp) {
		Translator lhsTranslator = new Translator(originalToAlloy, postOldSyntax, this.inEnsure);
		exp.getLeftExpr().accept(lhsTranslator);

		Translator rhsTranslator = new Translator(originalToAlloy, postOldSyntax, this.inEnsure);
		exp.getRightExpr().accept(rhsTranslator);

		result.addAll(lhsTranslator.getResult());
		result.add(".div[");
		result.addAll(rhsTranslator.getResult());
		result.add("]");
	}

	@Override
	public void visitModuloArithmetic(Modulo exp) {
		Translator lhsTranslator = new Translator(originalToAlloy, postOldSyntax, this.inEnsure);
		exp.getLeftExpr().accept(lhsTranslator);

		Translator rhsTranslator = new Translator(originalToAlloy, postOldSyntax, this.inEnsure);
		exp.getRightExpr().accept(rhsTranslator);

		result.addAll(lhsTranslator.getResult());
		result.add(".rem[");
		result.addAll(rhsTranslator.getResult());
		result.add("]");
	}

	@Override
	public void visitMultiplicationArithmetic(Multiplication exp) {
		Translator lhsTranslator = new Translator(originalToAlloy, postOldSyntax, this.inEnsure);
		exp.getLeftExpr().accept(lhsTranslator);

		Translator rhsTranslator = new Translator(originalToAlloy, postOldSyntax, this.inEnsure);
		exp.getRightExpr().accept(rhsTranslator);

		result.addAll(lhsTranslator.getResult());
		result.add(".mul[");
		result.addAll(rhsTranslator.getResult());
		result.add("]");
	}

	@Override
	public void visitAdditionArithmetic(Addition exp) {
		Translator lhsTranslator = new Translator(originalToAlloy, postOldSyntax, this.inEnsure);
		exp.getLeftExpr().accept(lhsTranslator);

		Translator rhsTranslator = new Translator(originalToAlloy, postOldSyntax, this.inEnsure);
		exp.getRightExpr().accept(rhsTranslator);

		result.addAll(lhsTranslator.getResult());
		result.add(".add[");
		result.addAll(rhsTranslator.getResult());
		result.add("]");
	}

	@Override
	public void visitSubtractionArithmetic(Subtraction exp) {
		Translator lhsTranslator = new Translator(originalToAlloy, postOldSyntax, this.inEnsure);
		exp.getLeftExpr().accept(lhsTranslator);

		Translator rhsTranslator = new Translator(originalToAlloy, postOldSyntax, this.inEnsure);
		exp.getRightExpr().accept(rhsTranslator);

		result.addAll(lhsTranslator.getResult());
		result.add(".sub[");
		result.addAll(rhsTranslator.getResult());
		result.add("]");
	}

	@Override
	public void visitIntegerConstant(IntegerConstant exp) {
		String value = String.valueOf(exp.getValue());
		result.add(value);
	}

	@Override
	public void visitIntegerVariable(IntegerVariable exp) {
		String alloyVarName = this.originalToAlloy.get(exp.getID());
		result.add(alloyVarName);
	}

	@Override
	public void visitLessRelational(LessThan exp) {
		Translator lhsTrans = new Translator(originalToAlloy, postOldSyntax, this.inEnsure);
		exp.getLeftExpr().accept(lhsTrans);
		Translator rhsTrans = new Translator(originalToAlloy, postOldSyntax, this.inEnsure);
		exp.getRightExpr().accept(rhsTrans);
		boolean refersToOLD = this.inEnsure;
		
		if (refersToOLD) {
			this.result.add(this.postOldSyntax);
		}
		
		this.result.addAll(lhsTrans.getResult());
		this.result.add(" < ");
		this.result.addAll(rhsTrans.getResult());
		this.result.add(" => True else False");
		if (refersToOLD) {
			this.result.add("} ");
		}
//		// WHY NO referstoold here?
//		Translator lhsTrans = new Translator(originalToAlloy, postOldSyntax);
//		exp.getLeftExpr().accept(lhsTrans);
//		Translator rhsTrans = new Translator(originalToAlloy, postOldSyntax);
//		exp.getRightExpr().accept(rhsTrans);
//		this.result.addAll(lhsTrans.getResult());
//		this.result.add(" = ");
//		this.result.addAll(rhsTrans.getResult());
//		this.result.add(" => True else False");
	}

	@Override
	public void visitLessEqualRelational(LessThanOrEqual exp) {
		Translator lhsTrans = new Translator(originalToAlloy, postOldSyntax, this.inEnsure);
		exp.getLeftExpr().accept(lhsTrans);
		Translator rhsTrans = new Translator(originalToAlloy, postOldSyntax, this.inEnsure);
		exp.getRightExpr().accept(rhsTrans);
		boolean refersToOLD = this.inEnsure;
		if (refersToOLD) {
			this.result.add(this.postOldSyntax);
		}
		this.result.addAll(lhsTrans.getResult());
		this.result.add(" <= ");
		this.result.addAll(rhsTrans.getResult());
		this.result.add(" => True else False");
		if (refersToOLD) {
			this.result.add("} ");
		}
	}

	@Override
	public void visitGreaterRelational(GreaterThan exp) {
		Translator lhsTrans = new Translator(originalToAlloy, postOldSyntax, this.inEnsure);
		exp.getLeftExpr().accept(lhsTrans);
		Translator rhsTrans = new Translator(originalToAlloy, postOldSyntax, this.inEnsure);
		exp.getRightExpr().accept(rhsTrans);
		boolean refersToOLD = this.inEnsure;
		if (refersToOLD) {
			this.result.add(this.postOldSyntax);
		}
		this.result.addAll(lhsTrans.getResult());
		this.result.add(" > ");
		this.result.addAll(rhsTrans.getResult());
		this.result.add(" => True else False");
		if (refersToOLD) {
			this.result.add("} ");
		}
	}

	@Override
	public void visitGreaterEqualRelational(GreaterThanOrEqual exp) {
		Translator lhsTrans = new Translator(originalToAlloy, postOldSyntax, this.inEnsure);
		exp.getLeftExpr().accept(lhsTrans);
		Translator rhsTrans = new Translator(originalToAlloy, postOldSyntax, this.inEnsure);
		exp.getRightExpr().accept(rhsTrans);
		boolean refersToOLD = this.inEnsure;
		if (refersToOLD) {
			this.result.add(this.postOldSyntax);
		}
		this.result.addAll(lhsTrans.getResult());
		this.result.add(" >= ");
		this.result.addAll(rhsTrans.getResult());
		this.result.add(" => True else False");

		if (refersToOLD) {
			this.result.add("} ");
		}
	}

	@Override
	public void visitEqualityRelational(Equality exp) {
		Translator lhsTrans = new Translator(originalToAlloy, postOldSyntax, this.inEnsure);
		exp.getLeftExpr().accept(lhsTrans);
		Translator rhsTrans = new Translator(originalToAlloy, postOldSyntax, this.inEnsure);
		exp.getRightExpr().accept(rhsTrans);
		boolean refersToOLD = this.inEnsure;
		if (refersToOLD) {
			this.result.add(this.postOldSyntax);
		}
		this.result.addAll(lhsTrans.getResult());
		this.result.add(" = ");
		this.result.addAll(rhsTrans.getResult());
		this.result.add(" => True else False");

		if (refersToOLD) {
			this.result.add("} ");
		}
	}

	@Override
	public void visitInequivalenceRelational(Inequality exp) {
		Translator lhsTrans = new Translator(originalToAlloy, postOldSyntax, this.inEnsure);
		exp.getLeftExpr().accept(lhsTrans);
		Translator rhsTrans = new Translator(originalToAlloy, postOldSyntax, this.inEnsure);
		exp.getRightExpr().accept(rhsTrans);
		boolean refersToOLD = this.inEnsure;
		if (refersToOLD) {
			this.result.add(this.getPostOldSyntax());
		}
		this.result.addAll(lhsTrans.getResult());
		this.result.add(" != ");
		this.result.addAll(rhsTrans.getResult());
		this.result.add(" => True else False");
		if (refersToOLD) {
			this.result.add("} ");
		}
	}

	@Override
	public void visitDisjunctionLogical(Disjunction exp) {
		Translator lhsTrans = new Translator(originalToAlloy, postOldSyntax, this.inEnsure);
		exp.getLeftExpr().accept(lhsTrans);
		Translator rhsTrans = new Translator(originalToAlloy, postOldSyntax, this.inEnsure);
		exp.getRightExpr().accept(rhsTrans);
		this.result.add("orGate[");
		boolean refersToOLD = this.inEnsure;
		if (refersToOLD) {
			this.result.add(this.postOldSyntax);
		}
		this.result.addAll(lhsTrans.getResult());
		if (refersToOLD) {
			this.result.add("} ");
		}
		this.result.add(", ");

		if (refersToOLD) {
			this.result.add(this.postOldSyntax);
		}
		this.result.addAll(rhsTrans.getResult());
		if (refersToOLD) {
			this.result.add("} ");
		}		
		this.result.add("]");
		this.result.add(".arg3");
	}

	@Override
	public void visitImplicationLogical(Implication exp) {
		Translator lhsTrans = new Translator(originalToAlloy, postOldSyntax, this.inEnsure);
		exp.getLeftExpr().accept(lhsTrans);
		Translator rhsTrans = new Translator(originalToAlloy, postOldSyntax, this.inEnsure);
		exp.getRightExpr().accept(rhsTrans);
		boolean refersToOLD = this.inEnsure;
		if (refersToOLD) {
			this.result.add(this.postOldSyntax);
		}
		this.result.addAll(lhsTrans.getResult());
		if (refersToOLD) {
			this.result.add("} ");
		}				
		this.result.add(" => ");
		if (refersToOLD) {
			this.result.add(this.postOldSyntax);
		}
		this.result.addAll(rhsTrans.getResult());
		if (refersToOLD) {
			this.result.add("} ");
		}		
	}

	@Override
	public void visitEquivalenceLogical(Equivalence exp) {
		Translator lhsTrans = new Translator(originalToAlloy, postOldSyntax, this.inEnsure);
		exp.getLeftExpr().accept(lhsTrans);
		Translator rhsTrans = new Translator(originalToAlloy, postOldSyntax, this.inEnsure);
		exp.getRightExpr().accept(rhsTrans);
		boolean refersToOLD = this.inEnsure;
		if (refersToOLD) {
			this.result.add(this.postOldSyntax);
		}
		this.result.addAll(lhsTrans.getResult());
		this.result.add(" in ");
		this.result.addAll(rhsTrans.getResult());
		this.result.add(" => True else False");

		if (refersToOLD) {
			this.result.add("} ");
		}
	}

	@Override
	public void visitNegationLogical(Negation exp) {
		Translator lhsTrans = new Translator(originalToAlloy, postOldSyntax,this.inEnsure);
		exp.getExpression().accept(lhsTrans);
		boolean refersToOLD = this.inEnsure;
		this.result.add("notGate[");
		if (refersToOLD) {
			this.result.add(this.postOldSyntax);
		}
		this.result.addAll(lhsTrans.getResult());
		if (refersToOLD) {
			this.result.add("} ");
		}
		this.result.add("]");
		this.result.add(".arg3");
	}

	@Override
	public void visitConjunctionLogical(Conjunction exp) {
		Translator lhsTrans = new Translator(originalToAlloy, postOldSyntax, this.inEnsure);
		exp.getLeftExpr().accept(lhsTrans);
		Translator rhsTrans = new Translator(originalToAlloy, postOldSyntax, this.inEnsure);
		exp.getRightExpr().accept(rhsTrans);
		boolean refersToOLD = this.inEnsure;
		this.result.add("andGate[");
		if (refersToOLD) {
			this.result.add(this.postOldSyntax);
		}
		this.result.addAll(lhsTrans.getResult());
		if (refersToOLD) {
			this.result.add("} ");
		}
		this.result.add(", ");
		if (refersToOLD) {
			this.result.add(this.postOldSyntax);
		}
		this.result.addAll(rhsTrans.getResult());
		if (refersToOLD) {
			this.result.add("} ");
		}
		this.result.add("]");
		this.result.add(".arg3");
	}

	@Override
	public void visitBooleanConstant(BooleanConstant exp) {
		boolean value = exp.getValue();
		if (value) {
			result.add(this.trueInAlloy);
		} else {
			result.add(this.falseInAlloy);
		}

	}

	@Override
	public void visitBooleanVariable(BooleanVariable exp) {
		String alloyVarName = this.originalToAlloy.get(exp.getID());
		result.add(alloyVarName);
	}

	@Override
	public void visitPrecondStatement(PrecondStatement exp) {
		Logical precondLogical = (Logical) exp.getLogicalCondition();
		Translator tr = new Translator();
		precondLogical.accept(tr);
		result = tr.getResult();
	}

	@Override
	public void visitPostcondStatement(PostcondStatement exp) {
		Logical postcondLogical = (Logical) exp.getLogicalCondition();
		Translator tr = new Translator();
		postcondLogical.accept(tr);
		result = tr.getResult();
	}

}
