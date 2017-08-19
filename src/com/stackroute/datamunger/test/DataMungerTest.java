package com.stackroute.datamunger.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.stackroute.datamunger.query.parser.AggregateFunction;
import com.stackroute.datamunger.query.parser.QueryParameter;
import com.stackroute.datamunger.query.parser.QueryParser;
import com.stackroute.datamunger.query.parser.Restriction;

public class DataMungerTest {

	
	private static QueryParser queryParser;
	private static QueryParameter queryParameter;
	private String queryString;
	
	@BeforeClass
	public static void init() throws FileNotFoundException{

		queryParser = new QueryParser();
	}
	
	@Test
	public void getFileNameTestCase() {
		queryString = "select * from ipl.csv";
		queryParameter = queryParser.parseQuery(queryString);
		assertEquals("ipl.csv", queryParameter.getFile());
		display(queryString, queryParameter);
	}
	@Test
	public void getFieldsTestCase() {
		queryString = "select city, winner, team1,team2 from ipl.csv";
		queryParameter = queryParser.parseQuery(queryString);
		List<String> expectedFields = new ArrayList<>();
		expectedFields.add("city");
		expectedFields.add("winner");
		expectedFields.add("team1");
		expectedFields.add("team2");
		assertArrayEquals(expectedFields.toArray(), queryParameter.getFields().toArray());
		display(queryString, queryParameter);
	}
	
	@Test
	public void getFieldsAndRestrictionsTestCase() {
		queryString = "select city,winner,player_match from ipl.csv where season > 2014";
		queryParameter = queryParser.parseQuery(queryString);
		List<Restriction> restrictions = queryParameter.getRestrictions();
		assertNotNull(restrictions);
		
		display(queryString, queryParameter);
	}
	
	@Test
	public void getFieldsAndMultipleRestrictionsTestCase() {
		queryString = "select city,winner,player_match from ipl.csv where season > 2014 and city ='Bangalore'";
		queryParameter = queryParser.parseQuery(queryString);
		List<Restriction> restrictions = queryParameter.getRestrictions();
		assertNotNull(restrictions);
		
		display(queryString, queryParameter);
	}
	
	@Test
	public void getFieldsAndMultipleRestrictionsTestCase2() {
		queryString = "select city,winner,player_match from ipl.csv where season > 2014 or city ='Bangalore'";
		queryParameter = queryParser.parseQuery(queryString);
		List<Restriction> restrictions = queryParameter.getRestrictions();
		assertNotNull(restrictions);
		
		display(queryString, queryParameter);
	}
	
	@Test
	public void getFieldsAndThreeRestrictionsTestCase() {
		queryString = "select city,winner,player_match from ipl.csv where season > 2014 and city ='Bangalore' or city ='Delhi'";
		queryParameter = queryParser.parseQuery(queryString);
		List<Restriction> restrictions = queryParameter.getRestrictions();
		assertNotNull(restrictions);
		
		display(queryString, queryParameter);
	}
	
	@Test
	public void getAggregateFunctionsTestCase() {
		queryString = "select count(city),avg(win_by_runs),min(season),max(win_by_wickets) from ipl.csv";
		queryParameter = queryParser.parseQuery(queryString);
		List<AggregateFunction> aggregateFunctions=queryParameter.getAggregateFunctions();
		assertNotNull(aggregateFunctions);
		display(queryString, queryParameter);
	}
	
	@Test
	public void getGroupByTestCase() {
		queryString = "select city,avg(win_by_runs) from ipl.csv group by city";
		queryParameter = queryParser.parseQuery(queryString);
		assertNotNull(queryParameter);
		
		display(queryString, queryParameter);
	}
	@Test
	public void getGroupByOrderByTestCase() {
		queryString = "select city,winner,team1,team2 from ipl.csv where season > 2016 and city='Bangalore' group by winner order by city";
		queryParameter = queryParser.parseQuery(queryString);
		List<Restriction> restrictions = queryParameter.getRestrictions();
		assertNotNull(restrictions);
		
		display(queryString, queryParameter);
	}
	@Test
	public void getOrderByAndWhereConditionTestCase() {
		queryString = "select city,winner,player_match from ipl.csv where season > 2014 and city ='Bangalore'";
		queryParameter = queryParser.parseQuery(queryString);
		List<Restriction> restrictions = queryParameter.getRestrictions();
		assertNotNull(restrictions);
		
		display(queryString, queryParameter);
	}
	
		
	@Test
	public void getOrderByTestCase() {
		queryString = "select city,winner,team1,team2,player_match from ipl.csv order by city";
		queryParameter = queryParser.parseQuery(queryString);
		List<String> orderByFields = queryParameter.getOrderByFields();
		assertNotNull(orderByFields);
		display(queryString, queryParameter);
	}
	
	@Test
	public void getGroupeByWithoutWhereTestCase() {
		queryString = "select winner,count(*) from ipl.csv where season > 2016 group by winner";
		queryParameter = queryParser.parseQuery(queryString);
		List<String> groupByFields = queryParameter.getGroupByFields();
		assertNotNull(groupByFields);
		
		display(queryString, queryParameter);
	}
	private void display(String queryString, QueryParameter queryParameter) {
		System.out.println("\nQuery : " + queryString);
		System.out.println("--------------------------------------------------");
		System.out.println("Base Query:" + queryParameter.getBaseQuery());
		System.out.println("File:" + queryParameter.getFile());
		System.out.println("Query Type:" + queryParameter.getQueryType());
		List<String> fields = queryParameter.getFields();
		System.out.println("Selected field(s):");
		if (fields == null || fields.isEmpty()) {
			System.out.println("*");
		} else {
			for (String field : fields) {
				System.out.println("\t" + field);
			}
		}
		
		List<Restriction> restrictions = queryParameter.getRestrictions();
		
		if(restrictions!=null && !restrictions.isEmpty())
		{
			System.out.println("Where Conditions : ");
			int conditionCount=1;
			for(Restriction restriction :restrictions )
			{
				System.out.println("\tCondition : " + conditionCount++);
				System.out.println("\t\tName : "+restriction.getPropertyName());
				System.out.println("\t\tCondition : "+restriction.getCondition());
				System.out.println("\t\tValue : "+restriction.getPropertyValue());
			}
		}
		List<AggregateFunction>  aggregateFunctions = queryParameter.getAggregateFunctions();
		if(aggregateFunctions!=null && !aggregateFunctions.isEmpty()){
			
			System.out.println("Aggregate Functions : ");
			int funtionCount=1;
			for(AggregateFunction aggregateFunction :aggregateFunctions )
			{
				System.out.println("\t Aggregate Function : " + funtionCount++);
				System.out.println("\t\t function : "+aggregateFunction.getFunction());
				System.out.println("\t\t  field : "+aggregateFunction.getField());
			}
			
		}
		
		List<String>  orderByFields = queryParameter.getOrderByFields();
		if(orderByFields!=null && !orderByFields.isEmpty()){
			
			System.out.println(" Order by fields : ");
			for(String orderByField :orderByFields )
			{
				System.out.println("\t "+orderByField);
				
			}
			
		}
		
		List<String>  groupByFields = queryParameter.getGroupByFields();
		if(groupByFields!=null && !groupByFields.isEmpty()){
			
			System.out.println(" Group by fields : ");
			for(String groupByField :groupByFields )
			{
				System.out.println("\t "+groupByField);
				
			}
			
		}
	}
	
	
	
	
	
	
	
	
	
}
