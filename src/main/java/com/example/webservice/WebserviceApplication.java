package com.example.webservice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Multiset.Entry;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tech.tablesaw.api.IntColumn;
import tech.tablesaw.api.Row;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;

@SpringBootApplication
@RestController

public class WebserviceApplication{

	public static datafun df = new datafun();
	public static chartsclass cs = new chartsclass();
	public static Table csvFile = df.csvtotable("src/main/resources/Wuzzuf_Jobs.csv");
	public static Table cleanData = df.cleanedData(csvFile);	// Requirement number 3 - cleaning the data
	

	public static void main(String[] args) {
		SpringApplication.run(WebserviceApplication.class, args);
	}

	// Function that converts a tablesaw Table object into a list of hashmaps
	List<HashMap> dfToJson(Table t){
        List<HashMap> jsonList = new ArrayList();
        List<String> keys = t.columnNames();
        int size = t.columnCount();
        Iterator<Row> rows = t.stream().iterator();
        while (rows.hasNext()){
            Row values = rows.next();
            HashMap row = new HashMap<>();
            for (int i=0; i<size; i++){
                row.put(keys.get(i), values.getObject(i));
            }
            jsonList.add(row);

        }
        return jsonList;
    }

   @RequestMapping("/display")
   public HashMap<String, List<HashMap>> display(){
	   Table head = csvFile.first(10);

	   HashMap<String, List<HashMap>> result = new HashMap();
	   result.put("jobs", dfToJson(head));
	   return result;
   }

   @RequestMapping("/description")
   public HashMap<String, List<HashMap>> description(){
	   	Table summ = csvFile.summary().transpose(true, true);
		Table struct = csvFile.structure();

	   	HashMap<String, List<HashMap>> result = new HashMap();
	   	result.put("summary", dfToJson(summ));
		result.put("structure", dfToJson(struct));
	   	return result;
   }

   @RequestMapping("/jobs_company")
   public HashMap<String, List<HashMap>> jobsByCompany(){
	   Table jobs = df.top10DescendingOrder(df.jobCountPerCompany(cleanData));

	   HashMap<String, List<HashMap>> result = new HashMap();
	   result.put("top jobs by company", dfToJson(jobs));
	   return result;
   }

   @RequestMapping("/show_company")
   public String jobsByComapnyChart() throws IOException{
	PieChart chart = cs.getChart(df.top10DescendingOrder(df.jobCountPerCompany(cleanData)));
	BitmapEncoder.saveBitmap(chart, "target/classes/static/chart", BitmapFormat.PNG);	   
	return "<img src=\"chart.png\" alt = \"Pie Chart of Jobs By Company\">";
   }

   @RequestMapping("/jobs_title")
   public HashMap<String, List<HashMap>> jobsByTitle(){
	   Table jobs = df.top10mostPopularJob(df.mostPopularJob(cleanData));

	   HashMap<String, List<HashMap>> result = new HashMap();
	   result.put("jobs", dfToJson(jobs));
	   return result;
   }

   @RequestMapping("/show_title")
   public String jobsByTitleChart() throws IOException{
	CategoryChart chart = cs.getChart2(df.top10mostPopularJob(df.mostPopularJob(cleanData)));
	BitmapEncoder.saveBitmap(chart, "target/classes/static/chart", BitmapFormat.PNG);	   
	return "<img src=\"chart.png\" alt = \"Bar Chart of Jobs By Title\">";
   }

   @RequestMapping("/jobs_area")
   public HashMap<String, List<HashMap>> jobsByArea(){
	   Table jobs = df.top10mostPopularLocation(df.mostPopularLocation(cleanData));

	   HashMap<String, List<HashMap>> result = new HashMap();
	   result.put("jobs", dfToJson(jobs));
	   return result;
   }

   @RequestMapping("/show_area")
   public String jobsByAreaChart() throws IOException{
		CategoryChart chart = cs.getChart2(df.top10mostPopularLocation(df.mostPopularLocation(cleanData)));
		BitmapEncoder.saveBitmap(chart, "target/classes/static/chart", BitmapFormat.PNG);	   
		return "<img src=\"chart.png\" alt = \"Bar Chart of Jobs By Title\">";
   }

   @RequestMapping("/skills")
   public HashMap<String, LinkedHashMap> skills(){
	   LinkedHashMap skls = df.orderDescendMap(df.countofSkills(df.jobSkills(cleanData)));

	   HashMap<String, LinkedHashMap> result = new HashMap();
	   result.put("skills", skls);
	   return result;
   }

   @RequestMapping("/factorize")
   public HashMap<String, List<HashMap>> factorize(){
	   Table jobs = cleanData;
	   HashMap<String, Integer> map = new HashMap();
	   int counter = 0;
	   StringColumn yearsExp = jobs.stringColumn("YearsExp");
	   IntColumn YearsFactorized = IntColumn.create("FactorizedYearsExp");
		for(String s : yearsExp){
			if (!map.containsKey(s)){
				map.put(s, counter);
				counter++;
			}
			YearsFactorized.append(map.get(s));
		}

		jobs.addColumns(YearsFactorized);

	   HashMap<String, List<HashMap>> result = new HashMap();
	   result.put("factorized data", dfToJson(jobs));
	   return result;
   }

}
