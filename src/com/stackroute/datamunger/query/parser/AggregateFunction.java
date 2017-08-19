package com.stackroute.datamunger.query.parser;

//this class is used to store Aggregate Function
public class AggregateFunction {
	//creating fields to store the function name and field name
	private String Function,Field;

	public String getFunction() {
		return Function;
	}

	public void setFunction(String function) {
		Function = function;
	}

	public String getField() {
		return Field;
	}

	public void setField(String field) {
		Field = field;
	}
	
}
