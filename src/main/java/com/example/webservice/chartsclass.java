package com.example.webservice;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.style.Styler;
import tech.tablesaw.api.Table;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class chartsclass {
    public PieChart getChart(Table top10data) {

        // Create Chart
        PieChart chart = new PieChartBuilder().width(1200).height(600).title("the top 10").build();

        // Customize Chart
        chart.getStyler().setCircular(false);

        // Series
        int rowcount= top10data.rowCount();
        for (int i = 0; i < rowcount; i++) {
                chart.addSeries(String.valueOf(top10data.get(i, 0)), (Number) (top10data.get(i, 1)));
        }

        return chart;
    }

    public CategoryChart getChart2(Table top10data) {

        // Create Chart
        CategoryChart chart = new CategoryChartBuilder().width(1200).height(600).title("Bar Chart").xAxisTitle("Titles").yAxisTitle("Counts").build();

        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        chart.getStyler().setHasAnnotations(true);

        int rowcount= top10data.rowCount();

        ArrayList<String> names = new ArrayList<String>();
        for (int i = 0; i < 8; i++){
            names.add(String.valueOf(top10data.get(i, 0)));

        }

        ArrayList<Double> counts = new ArrayList<Double>();
        for (int i = 0; i < 8; i++){
            counts.add((Double) top10data.get(i, 1));

        }



        // Series
        chart.addSeries("counts", names, counts);

        return chart;
    }
}
