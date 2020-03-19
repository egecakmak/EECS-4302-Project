package model.declaration;

import java.util.HashMap;
import java.util.Map;

import model.Declaration;
import model.Value;
import model.Visitor;

/*
 * VariableInitialization Class that represents initialization of a variable
 */
public class VariableInitialization extends Declaration {
	private String id;
	private Value value;
	private String type;

	
	public VariableInitialization(String id, String type) {
		this.id = id;
		this.type = type;
	}
	
	/*
	 * Constructor
	 *
	 * @param id the id of the variable
	 *
	 * @param type the type of the variable (not important in this project)
	 *
	 * @param value the assigned value of the variable
	 */
	public VariableInitialization(String id, String type, Value value) {
		this.id = id;
		this.type = type;
		this.value = value;
	}
	
    public VariableInitialization(String id, String type, VariableInitialization other) {
        this.id = other.id;
        this.type = other.type;
        this.value = other.value;
    }

	/*
	 * retrieve the id of the variable
	 * 
	 * @return retrieve the id of the variable
	 */
	public String getID() {
		return this.id;
	}

	/*
	 * retrieve the type of the variable
	 * 
	 * @return retrieve the type of the variable
	 */
	public String getType() {
		return this.type;
	}

	/*
	 * retrieve the value of the variable
	 *
	 * @return retrieve the value of the variable
	 */
	public Value getValue() {
		return this.value;
	}

	@Override
	public String toString() {
		return this.id + " " + this.type + " " + this.value.getValue();
	}

	@Override
	public void accept(Visitor visitor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, Value> getVariables() {
		Map<String,Value> result = new HashMap<>();
		result.put(id,this.value);
		return result;
	}
}
