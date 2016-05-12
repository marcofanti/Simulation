package demo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
 
public class BarChartSample extends Application {
    final static String austria = "Austria";
    final static String brazil = "Brazil";
    final static String france = "France";
    final static String italy = "Italy";
    final static String usa = "USA";
 
    @Override public void start(Stage stage) {
        stage.setTitle("Bar Chart Sample");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc = 
            new BarChart<String,Number>(xAxis,yAxis);
        bc.setTitle("Demand");
        xAxis.setLabel("Country");       
        yAxis.setLabel("Value");
        
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("2003");       

        try (BufferedReader br = new BufferedReader(new 
        		FileReader("/Users/mfanti/Documents/UF/2016_2_Summer/InternationalLogistics/demand.txt"))) {
            String line;
            int day = 1;
            while ((line = br.readLine()) != null) {
                long longValue = Long.parseLong(line);
                System.out.println(day + "" + " " + longValue);
		        series1.getData().add(new XYChart.Data(day++ + "", longValue));
            }
        
        } catch (IOException ioe) {
        	ioe.printStackTrace();
        }
        

        
        Scene scene  = new Scene(bc,800,600);
        bc.getData().addAll(series1);
        stage.setScene(scene);
        stage.show();
    }
 
    public static void main(String[] args) {
        launch(args);
    }
}