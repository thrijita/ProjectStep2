package com.stackroute.datamunger.query.parser;

import java.util.ArrayList;

public class QueryParser {

	private QueryParameter queryParameter = new QueryParameter();
	public QueryParameter parseQuery(String queryString) {
		
		//this method will parse the queryString and will return the object of QueryParameter
		//class
		queryParameter.setFile((queryString.split("from")[1].trim()).split("where")[0].trim().split(" ")[0].trim());
		queryParameter.setAggregateFunctions(new ArrayList<>());
		queryParameter.setFields(new ArrayList<>());
		String[] fields = (queryString.split("from")[0].trim()).split("select")[1].trim().split(",");
		//checking for simple  query
		for (String eachField : fields) {
			queryParameter.setQueryType("simple");
			queryParameter.getFields().add(eachField.trim());
			if (eachField.contains("(") || eachField.contains(")")) {
				queryParameter.setQueryType("aggregate");
				AggregateFunction aggregateFunction = new AggregateFunction();
				aggregateFunction.setField(eachField.split("\\(")[1].trim().split("\\)")[0]);
				aggregateFunction.setFunction(eachField.split("\\(")[0]);
				queryParameter.getAggregateFunctions().add(aggregateFunction);
			}
		}
		//checking for where clause 
		if (queryString.contains("where")) {
			queryParameter.setRestrictions(new ArrayList<>());
			queryParameter.setBaseQuery((queryString.split("where")[0].trim()));
			queryParameter.setQueryType("where");
			String[] conditions = (((queryString.split("where")[1].trim()).split("order by|group by")[0].trim()
					.toLowerCase()).split(" and | or "));
			for (String eachCondition : conditions) {
				Restriction restriction = new Restriction();
				restriction.setPropertyName(eachCondition.split(">=|<=|!=|<|>|=")[0].trim());
				restriction.setCondition(eachCondition.split(eachCondition.split(">=|<=|!=|<|>|=")[1].trim())[0].trim()
						.split(eachCondition.split(">=|<=|!=|<|>|=")[0].trim())[1].trim());
				restriction.setPropertyValue(eachCondition.split(">=|<=|!=|<|>|=")[1].trim());
				queryParameter.getRestrictions().add(restriction);
			}
		} else if (queryString.contains("group by") || queryString.contains("order by")) {
			queryParameter.setGroupByFields(new ArrayList<>());
			queryParameter.setOrderByFields(new ArrayList<>());
			queryParameter.setBaseQuery((queryString.split("order by|group by")[0].trim()));
			if (queryString.contains("order by")) {
				queryParameter.setQueryType("order by");
				String[] orderByField = (queryString.split("order by")[1].trim()).split("group by")[0].trim()
						.split(" ");
				for (String eachOrderByField : orderByField) {
					queryParameter.getOrderByFields().add(eachOrderByField);
				}
			}
			if (queryString.contains("group by")) {
				queryParameter.setQueryType("group by");
				String[] groupByFields = (queryString.split("group by")[1].trim()).split("order by")[0].trim()
						.split(" ");
				for (String eachGroupByField : groupByFields) {
					queryParameter.getGroupByFields().add(eachGroupByField);
				}
			}
		} else {
			queryParameter.setBaseQuery(queryString);
		}
		
		return queryParameter;
	}
	
	
}
