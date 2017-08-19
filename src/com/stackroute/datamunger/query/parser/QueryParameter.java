package com.stackroute.datamunger.query.parser;

import java.util.List;
import java.util.Map;
//this class contains the parameters and accessor/mutator methods of QueryParameter
public class QueryParameter {

	private String file, queryType, baseQuery;
	private List<String> orderByFields,
			groupByFields, fields;
	private List<Restriction> restrictions;
	private List<AggregateFunction> aggregateFunctions;
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	public String getBaseQuery() {
		return baseQuery;
	}
	public void setBaseQuery(String baseQuery) {
		this.baseQuery = baseQuery;
	}
	public List<String> getOrderByFields() {
		return orderByFields;
	}
	public void setOrderByFields(List<String> orderByFields) {
		this.orderByFields = orderByFields;
	}
	public List<String> getGroupByFields() {
		return groupByFields;
	}
	public void setGroupByFields(List<String> groupByFields) {
		this.groupByFields = groupByFields;
	}
	public List<String> getFields() {
		return fields;
	}
	public void setFields(List<String> fields) {
		this.fields = fields;
	}
	public List<Restriction> getRestrictions() {
		return restrictions;
	}
	public void setRestrictions(List<Restriction> restrictions) {
		this.restrictions = restrictions;
	}
	public List<AggregateFunction> getAggregateFunctions() {
		return aggregateFunctions;
	}
	public void setAggregateFunctions(List<AggregateFunction> aggregateFunctions) {
		this.aggregateFunctions = aggregateFunctions;
	}
	

			
}
