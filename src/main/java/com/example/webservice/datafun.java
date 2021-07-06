package com.example.webservice;

import tech.tablesaw.api.Table;
import static tech.tablesaw.aggregate.AggregateFunctions.*;
import java.util.*;
import java.util.stream.Collectors;

public class datafun {
    public Table csvtotable (String path) {
        Table namedata = null;
        try{
            namedata = Table.read().csv(path);
            
        } catch(Exception e){

        }
        return namedata;
    }

    public Table cleanedData (Table data){
        //create new table and copy data to it
        Table copydata = data;
        // drop duplicated rows
        copydata.dropDuplicateRows();
        // drop rows with missing values
        copydata.dropRowsWithMissingValues();
        return copydata;

    }

    public Table jobCountPerCompany(Table cdata){
        Table jobcount = cdata.summarize("Title", count).by("Company");
        return jobcount;
    }

    public Table top10DescendingOrder(Table jdata){
        Table DescendingOrder = jdata.sortDescendingOn("count [Title]");
        Table DescendingOrderTop10 = DescendingOrder.first(10);

        return DescendingOrderTop10;

    }

    public Table mostPopularJob(Table mdata){
        Table mostjob = mdata.summarize("Title", count).by("Title");
        return mostjob;

    }

    public Table top10mostPopularJob(Table top10data){
        Table mostjob = top10data.sortDescendingOn("count [Title]");
        Table top10mostjob = mostjob.first(10);
        return top10mostjob;

    }

    public Table mostPopularLocation(Table ldata){
        Table mostlocation = ldata.summarize("Location", count).by("Location");
        return mostlocation;

    }

    public Table top10mostPopularLocation(Table top10data){
        Table mostlocation = top10data.sortDescendingOn("Count [Location]");
        Table top10mostlocation = mostlocation.first(10);
        return top10mostlocation;

    }
    public List jobSkills(Table sdata){
        List<String> skills = sdata.stringColumn("skills").asList().stream().
                flatMap(row-> Arrays.stream(row.split(","))).collect(Collectors.toList());
        return skills;

    }


    public Map countofSkills(List sdata){

        Map<String ,Integer> map = new HashMap<>();

        for(  Object r  : sdata) {
            if(  map.containsKey(r)   ) {
                map.put((String) r, map.get(r) + 1);
            }//if
            else {
                map.put((String) r, 1);
            }
        }
        return map;

    }
    public LinkedHashMap orderDescendMap(Map odata){
        LinkedHashMap<String, Integer> reverseSortedMap = new LinkedHashMap<>();
        Set< Map.Entry<String ,Integer> > entrySet = odata.entrySet();
        entrySet.stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> reverseSortedMap.put(x.getKey(),x.getValue()));

        return reverseSortedMap;

    }

//




}
