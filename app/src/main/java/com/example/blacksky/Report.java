package com.example.blacksky;

import android.graphics.Color;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.example.blacksky.datamodels.ChartDataModel;
import com.example.blacksky.properties.Properties;

import java.util.ArrayList;
import java.util.List;

public class Report extends AppCompatActivity {

    Toolbar toolbar;
    ChartDataModel chartDataModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        toolbar = findViewById(R.id.rp_toolbar);
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccentBlue));
        }

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        //chartDataModel = new ChartDataModel(this);

        setTotalEarningsChart();
        setJobEarningsChart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setTotalEarningsChart();
        setJobEarningsChart();

    }



    private void setTotalEarningsChart() {
        AnyChartView totalEarningsChart = findViewById(R.id.totalEarningsChart);
        APIlib.getInstance().setActiveAnyChartView(totalEarningsChart);

        chartDataModel = new ChartDataModel(this);

        Pie pie = AnyChart.pie();

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Expenditure", chartDataModel.getTotalSpending()));
        data.add(new ValueDataEntry("Income",chartDataModel.getTotalEarnings()));

        pie.data(data);
        pie.title("Individual Services Earnings (Ksh)");

      //  pie.radius(70d);

        pie.labels().position("outside");
        pie.legend().title().enabled(true);


        pie.legend().title().text("Key").padding(2d, 2d, 2d, 2d);
        pie.legend().position("bottom")
                .itemsLayout(LegendLayout.HORIZONTAL_EXPANDABLE)
                .align(Align.LEFT);

      //  findViewById(R.id.totalEarningsChart).setMinimumHeight(400);
        totalEarningsChart.setChart(pie);
    }

    private void setJobEarningsChart() {
        AnyChartView jobEarningsChart = findViewById(R.id.serviceEarningsChart);
        APIlib.getInstance().setActiveAnyChartView(jobEarningsChart);

        chartDataModel = new ChartDataModel(this);
        List<Integer> jobs = chartDataModel.getJobEarnings();

        Pie pie = AnyChart.pie();

        List<DataEntry> data = new ArrayList<>();
//
        for (int i = 0; i < jobs.size(); i++){
            data.add(new ValueDataEntry(Properties.SERVICES[i], jobs.get(i)));
        }

        pie.data(data);

        pie.title("Earnings by service (Ksh)") ;

        //  pie.radius(70d);

        pie.labels().position("outside");
        pie.legend().title().enabled(true);

        pie.legend().title().text("Key").padding(2d, 2d, 2d, 2d);
        pie.legend().position("bottom")
                .itemsLayout(LegendLayout.VERTICAL_EXPANDABLE)
                .align(Align.LEFT);

        findViewById(R.id.totalEarningsChart).setMinimumHeight(400);
        jobEarningsChart.setChart(pie);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

}
























