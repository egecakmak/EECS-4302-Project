package model;

import model.statement.MultiAssignment;
import model.statement.assignment.ExpressionAssignment;
import model.statement.assignment.expression.Logical;
import model.statement.assignment.expression.ParanthesesExpression;
import model.statement.assignment.expression.Relational;
import model.statement.assignment.expression.arithmetic.*;
import model.statement.assignment.expression.logical.*;
import model.statement.assignment.expression.relational.*;
import model.statement.conditional.AssertedConditional;
import model.statement.conditional.IfElseIfStatement;
import model.statement.conditional.PostcondStatement;
import model.statement.conditional.PrecondStatement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.misc.IntSet;


public class Translator implements Visitor {

	private int statementsTranslated;

    private List<String> result;
    
    private Map<String,String> resultMap;
    
    private Map<String, String> originalToAlloy;

    private final String fieldName = "field";
    
    private String finalResult ;
    
    private String postOldSyntax;
    
    
    public Translator() {
    	finalResult = "";
    	postOldSyntax="";
    	statementsTranslated= 0;
    	result = new ArrayList<>();
    	resultMap = new HashMap<>();
    	originalToAlloy = new HashMap<>();
	}
    
    public Translator(Map<String, String> originalToAlloy) {
    	finalResult = "";
    	postOldSyntax="";
    	statementsTranslated= 0;
    	result = new ArrayList<>();
    	resultMap = new HashMap<>();
    	this.originalToAlloy=originalToAlloy;
	}
    
    public Translator(Map<String, String> originalToAlloy, String postOldSyntax) {
    	finalResult = "";
    	statementsTranslated= 0;
    	result = new ArrayList<>();
    	resultMap = new HashMap<>();
    	this.originalToAlloy=originalToAlloy;
    	this.postOldSyntax=postOldSyntax;
	}
    
//  no re-assignment in ensure
//  ((x>y) and  (x_new == x+2+3+1 and y_new==y) and (z = z_old)) or ((x<=y) and  (x = x_old and y = y_old+1)))
//  (not(x==x_old) or  (x == x_old+2+3+1 and y==y_old)) or ((x<=y) and  (x = x_old and y = y_old+1)))
//  ((n.arg1 > n.arg2 and addOneConditionalEnsure[n.arg1,n.arg2,n.arg1.add[1],n.arg2])  or (n.arg1 <= n.arg2 and addOneConditionalEnsure[n.arg1,n.arg2,n.arg1,n.arg2.add[1]]))

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
        StringBuilder sb = new StringBuilder();
        StringBuilder postOldSyntaxSB= new StringBuilder();
   
        sb.append("sig").append(stateName).append("{");
        postOldSyntaxSB.append("all field: "+funName+this.getStatementsTranslated()+" [");
        
        
        Map<String, String> postOriginalToAlloy= new HashMap<>();
        Map<String, String> preOriginalToAlloy = new HashMap<>();
        

        int counter = 1;
        for (String each : vars.keySet()) {
            String alloyVar = "arg" + (counter + 1);
            String originalVar = each;
            String varType = vars.get(originalVar).getType();
            preOriginalToAlloy.put(originalVar,alloyVar);
            
            postOriginalToAlloy.put(originalVar, this.getFieldName()+"."+alloyVar);
            postOriginalToAlloy.put(originalVar + "_old", "n."+alloyVar);
            sb.append("\n\t");
            sb.append(alloyVar).append(":").append(varType).append(",");
            postOldSyntaxSB.append(each).append(",");
        }

        sb.deleteCharAt(sb.lastIndexOf(","));        
        sb.append("\n}\n");
        
        postOldSyntaxSB.deleteCharAt(sb.lastIndexOf(","));
        postOldSyntaxSB.append("] | { ");
        postOldSyntax = postOldSyntaxSB.toString();


        Translator functionTranslator = new Translator(preOriginalToAlloy);
        exp.getIfStatment().accept(functionTranslator);
        List<String> functionTranslated = functionTranslator.getResult();
        StringBuilder functionSB = new StringBuilder();
        functionTranslated.forEach(functionSB::append);
        String functionTranslatedString = functionSB.toString();
        
        sb.append("fun ").append(funName).append(this.getStatementsTranslated()).
                append(" (").append("param : ").append(stateName).append(") : ").append(stateName).append("{\n");
        sb.append(functionTranslatedString).append(" \n}");



        Translator precondTranslator = new Translator(preOriginalToAlloy);
        exp.getPreCond().accept(precondTranslator);
        List<String> precondTranslated = precondTranslator.getResult();
        StringBuilder precondSB = new StringBuilder();
        precondTranslated.forEach(precondSB::append);
        String precondTranslatedString = precondSB.toString();

        Translator postcondTranslator = new Translator(postOriginalToAlloy,postOldSyntax);
        exp.getPreCond().accept(postcondTranslator);
        List<String> postcondTranslated = postcondTranslator.getResult();
        StringBuilder postcondSB = new StringBuilder();
        postcondTranslated.forEach(postcondSB::append);
        String postcondTranslatedString = postcondSB.toString();

        sb.append("assert ").append(assertName).append(this.getStatementsTranslated()).append(" {\n");
        sb.append("\t all n: ").append(stateName).append(" | (").append(precondTranslatedString).append(") => (").append(postcondTranslatedString);
        sb.append("check ").append(assertName).append(this.getStatementsTranslated());

        this.incrementStatementsTranslated();
        
        this.finalResult = sb.toString();
    }

    @Override
    public void visitIfConditional(IfElseIfStatement exp) { 
        Translator conditionTranslator = new Translator(originalToAlloy);
        exp.getCondition().accept(conditionTranslator);        
        
        Translator assigTranslator = new Translator(originalToAlloy);
        exp.getAssignments().accept(assigTranslator);

        Translator elseIfTranslator = new Translator(originalToAlloy);
        if(exp.getElseStatment() != null){
            exp.getElseStatment().getAssignments().accept(elseIfTranslator);        	
        }

        
        for(String key : this.getOriginalToAlloy().keySet()){
        	this.result.add(key);
        	this.result.add(" = (");
        	this.result.addAll(conditionTranslator.result);
        	this.result.add(" => ");
        	this.result.add(assigTranslator.resultMap.get(key));
        	if (exp.getElseStatment() != null){
            	this.result.add(" else ");
            	this.result.add(elseIfTranslator.resultMap.get(key));        		
        	}

        	this.result.add(" )");
        	this.result.add("and");
        }
        this.result.remove(this.result.size()-1);
    }

    @Override
    public void visitEpsilonConditional(Instruction exp) {

    }

    @Override
    public void visitMultipleAssignments(MultiAssignment exp) {
    	for (Instruction inst: exp.getAssignments()){
            Translator instTranslator = new Translator(originalToAlloy);
            inst.accept(instTranslator);
            this.resultMap.put(instTranslator.result.get(0), instTranslator.result.get(2));
    	}
    }

    public void visitAssignExpression(ExpressionAssignment exp) {
    	this.result.add(exp.getID());
        Translator rhsTranslator = new Translator(originalToAlloy);
        exp.getExpr().accept(rhsTranslator);
        this.result.addAll(rhsTranslator.getResult());
    }

    @Override
    public void visitAssignAssignment(Instruction exp) {

    }

    @Override
    public void visitArithmeticOperation(Instruction exp) {

    }

    @Override
    public void visitRelationalOperation(Relational exp) {

    }

    @Override
    public void visitLogicalOpteration(Logical exp) {
    	

    }

    @Override
    public void visitParanthesesExpression(ParanthesesExpression exp) {
        Translator innerTranslator = new Translator(originalToAlloy);
        exp.getExpression().accept(innerTranslator);
        result = innerTranslator.getResult();
    }

    @Override
    public void visitDivisionArithmetic(Division exp) {
        Translator lhsTranslator = new Translator(originalToAlloy);
        exp.getLeftExpr().accept(lhsTranslator);

        Translator rhsTranslator = new Translator(originalToAlloy);
        exp.getRightExpr().accept(rhsTranslator);

        result.addAll(lhsTranslator.getResult());
        result.add(".div[");
        result.addAll(rhsTranslator.getResult());
        result.add("]");
    }

    @Override
    public void visitVariableArithmetic(Instruction exp) {

    }

    @Override
    public void visitModuloArithmetic(Modulo exp) {
        Translator lhsTranslator = new Translator(originalToAlloy);
        exp.getLeftExpr().accept(lhsTranslator);

        Translator rhsTranslator = new Translator(originalToAlloy);
        exp.getRightExpr().accept(rhsTranslator);

        result.addAll(lhsTranslator.getResult());
        result.add(".rem[");
        result.addAll(rhsTranslator.getResult());
        result.add("]");
    }

    @Override
    public void visitMultiplicationArithmetic(Multiplication exp) {
        Translator lhsTranslator = new Translator(originalToAlloy);
        exp.getLeftExpr().accept(lhsTranslator);

        Translator rhsTranslator = new Translator(originalToAlloy);
        exp.getRightExpr().accept(rhsTranslator);

        result.addAll(lhsTranslator.getResult());
        result.add(".mul[");
        result.addAll(rhsTranslator.getResult());
        result.add("]");
    }

    @Override
    public void visitNegationIntegerConstant(IntegerConstant exp) {
        String value = String.valueOf(exp.getValue());
        result.add(value);
    }

    @Override
    public void visitAdditionArithmetic(Addition exp) {
        Translator lhsTranslator = new Translator(originalToAlloy);
        exp.getLeftExpr().accept(lhsTranslator);

        Translator rhsTranslator = new Translator(originalToAlloy);
        exp.getRightExpr().accept(rhsTranslator);

        result.addAll(lhsTranslator.getResult());
        result.add(".add[");
        result.addAll(rhsTranslator.getResult());
        result.add("]");
    }

    @Override
    public void visitSubtractionArithmetic(Subtraction exp) {
        Translator lhsTranslator = new Translator(originalToAlloy);
        exp.getLeftExpr().accept(lhsTranslator);

        Translator rhsTranslator = new Translator(originalToAlloy);
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
        Translator lhsTrans = new Translator(originalToAlloy);
        exp.getLeftExpr().accept(lhsTrans);
        Translator rhsTrans = new Translator(originalToAlloy);
        exp.getRightExpr().accept(rhsTrans);
        this.result.addAll(lhsTrans.getResult());
        this.result.add(" < ");
        this.result.addAll(rhsTrans.getResult());
    }

    @Override
    public void visitLessEqualRelational(LessThanOrEqual exp) {
        Translator lhsTrans = new Translator(originalToAlloy);
        exp.getLeftExpr().accept(lhsTrans);
        Translator rhsTrans = new Translator(originalToAlloy);
        exp.getRightExpr().accept(rhsTrans);
        boolean refersToOLD=false ;
        for (String res:lhsTrans.result){
        	if(res.contains(this.fieldName)){
        		refersToOLD=true;
        	}
        }
        for (String res:rhsTrans.result){
        	if(res.contains(this.fieldName)){
        		refersToOLD=true;
        	}
        }
        if(refersToOLD){
        	this.result.add(this.postOldSyntax);
        }
        this.result.addAll(lhsTrans.result);
        this.result.add(" <= ");
        this.result.addAll(rhsTrans.result);
        if(refersToOLD){
        	this.result.add("} ");
        }
    }

    @Override
    public void visitGreaterRelational(GreaterThan exp) {
        Translator lhsTrans = new Translator(originalToAlloy);
        exp.getLeftExpr().accept(lhsTrans);
        Translator rhsTrans = new Translator(originalToAlloy);
        exp.getRightExpr().accept(rhsTrans);
        boolean refersToOLD=false ;
        for (String res:lhsTrans.getResult()){
        	if(res.contains(this.fieldName)){
        		refersToOLD=true;
        	}
        }
        for (String res:rhsTrans.getResult()){
        	if(res.contains(this.fieldName)){
        		refersToOLD=true;
        	}
        }
        if(refersToOLD){
        	this.result.add(this.postOldSyntax);
        }
        this.result.addAll(lhsTrans.getResult());
        this.result.add(" > ");
        this.result.addAll(rhsTrans.getResult());
        if(refersToOLD){
        	this.result.add("} ");
        }
    }

    @Override
    public void visitGreaterEqualRelational(GreaterThanOrEqual exp) {
        Translator lhsTrans = new Translator(originalToAlloy);
        exp.getLeftExpr().accept(lhsTrans);
        Translator rhsTrans = new Translator(originalToAlloy);
        exp.getRightExpr().accept(rhsTrans);
        boolean refersToOLD=false ;
        for (String res:lhsTrans.getResult()){
        	if(res.contains(this.fieldName)){
        		refersToOLD=true;
        	}
        }
        for (String res:rhsTrans.getResult()){
        	if(res.contains(this.fieldName)){
        		refersToOLD=true;
        	}
        }
        if(refersToOLD){
        	this.result.add(this.postOldSyntax);
        }
        this.result.addAll(lhsTrans.getResult());
        this.result.add(" >= ");
        this.result.addAll(rhsTrans.getResult());

        if(refersToOLD){
        	this.result.add("} ");
        }
    }

    @Override
    public void visitEqualityRelational(Equality exp) {
        Translator lhsTrans = new Translator(originalToAlloy);
        exp.getLeftExpr().accept(lhsTrans);
        Translator rhsTrans = new Translator(originalToAlloy);
        exp.getRightExpr().accept(rhsTrans);
        boolean refersToOLD=false ;
        for (String res:lhsTrans.getResult()){
        	if(res.contains(this.fieldName)){
        		refersToOLD=true;
        	}
        }
        for (String res:rhsTrans.getResult()){
        	if(res.contains(this.fieldName)){
        		refersToOLD=true;
        	}
        }
        if(refersToOLD){
        	this.result.add(this.postOldSyntax);
        }
        this.result.addAll(lhsTrans.getResult());
        this.result.add(" = ");
        this.result.addAll(rhsTrans.getResult());
        
        if(refersToOLD){
        	this.result.add("} ");
        }
    }

    @Override
    public void visitInequivalenceRelational(Inequality exp) {
        Translator lhsTrans = new Translator(originalToAlloy);
        exp.getLeftExpr().accept(lhsTrans);
        Translator rhsTrans = new Translator(originalToAlloy);
        exp.getRightExpr().accept(rhsTrans);
        boolean refersToOLD=false ;
        for (String res:lhsTrans.getResult()){
        	if(res.contains(this.fieldName)){
        		refersToOLD=true;
        	}
        }
        for (String res:rhsTrans.getResult()){
        	if(res.contains(this.getFieldName())){
        		refersToOLD=true;
        	}
        }
        if(refersToOLD){
        	this.result.add(this.getPostOldSyntax());
        }
        this.result.addAll(lhsTrans.getResult());
        this.result.add(" != ");
        this.result.addAll(rhsTrans.getResult());
        if(refersToOLD){
        	this.result.add("} ");
        }
    }

    @Override
    public void visitDisjunctionLogical(Disjunction exp) {
        Translator lhsTrans = new Translator(originalToAlloy);
        exp.getLeftExpr().accept(lhsTrans);
        Translator rhsTrans = new Translator(originalToAlloy);
        exp.getRightExpr().accept(rhsTrans);
        this.result.addAll(lhsTrans.getResult());
        this.result.add(" or ");
        this.result.addAll(rhsTrans.getResult());
    }

    @Override
    public void visitImplicationLogical(Implication exp) {

    }

    @Override
    public void visitVariableLogical(Instruction exp) {

    }

    @Override
    public void visitEquivalenceLogical(Equivalence exp) {

    }

    @Override
    public void visitNegationLogical(Negation exp) {

    }

    @Override
    public void visitConjunctionLogical(Conjunction exp) {
        Translator lhsTrans = new Translator(originalToAlloy);
        exp.getLeftExpr().accept(lhsTrans);
        Translator rhsTrans = new Translator(originalToAlloy);
        exp.getRightExpr().accept(rhsTrans);
        this.result.addAll(lhsTrans.getResult());
        this.result.add(" and ");
        this.result.addAll(rhsTrans.getResult());
    }

    @Override
    public void visitBooleanConstant(BooleanConstant exp) {

    }

    @Override
    public void visitRelationalOpLogical(Instruction exp) {

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
