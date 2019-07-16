package com.example.blacksky;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ajts.androidmads.library.SQLiteToExcel;
import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.example.blacksky.databases.DatabaseHelper;
import com.example.blacksky.datamodels.ChartDataModel;
import com.example.blacksky.properties.Properties;

import org.apache.poi.ss.formula.functions.T;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
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

        pie.labels().position("outside");
        pie.legend().title().enabled(true);


        pie.legend().title().text("Key").padding(2d, 2d, 2d, 2d);
        pie.legend().position("bottom")
                .itemsLayout(LegendLayout.HORIZONTAL_EXPANDABLE)
                .align(Align.LEFT);

        totalEarningsChart.setChart(pie);
        isStoragePermissionGranted();
    }

    private void setJobEarningsChart() {
        AnyChartView jobEarningsChart = findViewById(R.id.serviceEarningsChart);
        APIlib.getInstance().setActiveAnyChartView(jobEarningsChart);

        chartDataModel = new ChartDataModel(this);
        List<Integer> jobs = chartDataModel.getJobEarnings();

        Pie pie = AnyChart.pie();

        List<DataEntry> data = new ArrayList<>();

        for (int i = 0; i < jobs.size(); i++){
            data.add(new ValueDataEntry(Properties.SERVICES[i], jobs.get(i)));
        }

        pie.data(data);

        pie.title("Earnings by service (Ksh)") ;

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
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.exportExcel:
                generateExcel();
               // Toast.makeText(this, "Exported Excel", Toast.LENGTH_SHORT).show();
                break;

        }
        return true;
    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(Report.class.getName(),"Permission is granted");
                return true;
            } else {

                Log.v(Report.class.getName(),"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(Report.class.getName(),"Permission is granted");
            return true;
        }
    }

    private void generateExcel() {
       // File excelFile = new File(Environment.getExternalStorageDirectory() + "/BlackSkyLenses/bsl_excel.xls");

        String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/BlackSkyLenses";
       // File myFolder = Environment.getExternalStorageDirectory();

        File dir = new File(fullPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }


        File file = new File(fullPath, "black_sky_lenses_excel.xls");
        if (file.exists()) {
            file.delete();
        }

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }


        String fName = file.getAbsolutePath();
        Toast.makeText(this, fName, Toast.LENGTH_LONG).show();

        SQLiteToExcel sqLiteToExcel = new SQLiteToExcel(this, DatabaseHelper.DATABASE_NAME, fullPath);
        sqLiteToExcel.exportAllTables("black_sky_lenses_excel.xls", new SQLiteToExcel.ExportListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onCompleted(String filePath) {
                Toast.makeText(getApplicationContext(), "Successfully Generated " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
                Uri uri = Uri.parse(filePath);
                Intent sendFile = new Intent();
                sendFile.setAction(Intent.ACTION_SEND);
                sendFile.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file.getAbsoluteFile()));
                sendFile.setType("spreadsheet");
                startActivity(Intent.createChooser(sendFile, getResources().getText(R.string.app_name)));
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                Log.e(Report.class.getName(), e.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
}
























