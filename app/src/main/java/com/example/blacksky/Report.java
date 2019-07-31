package com.example.blacksky;
import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ajts.androidmads.library.ExcelToSQLite;
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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Report extends AppCompatActivity {

    Toolbar toolbar;
    ChartDataModel chartDataModel;
    //DatabaseHelper myDb;

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
            case R.id.retrieveExcel:
                performFileSearch();
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
//                try {
//                    Uri uri = Uri.parse(filePath);
//                    Intent sendFile = new Intent();
//                    sendFile.setAction(Intent.ACTION_SEND);
//                    sendFile.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file.getAbsoluteFile()));
//                    sendFile.setType("application/excel");
//                    startActivity(Intent.createChooser(sendFile, getResources().getText(R.string.app_name)));
//                }catch (Exception e) {
//                    Toast.makeText(getApplicationContext(), "Can't Share", Toast.LENGTH_SHORT).show();
//                }
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                Log.e(Report.class.getName(), e.getMessage());
            }
        });
    }

    private static final int READ_REQUEST_CODE = 42;
    /**
     * Fires an intent to spin up the "file chooser" UI and select an image.
     */
    public void performFileSearch() {

        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers,
        // it would be "*/*".
        intent.setType("*/*");

        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode != READ_REQUEST_CODE || resultCode != RESULT_OK){
            return;
        }

        importExcel(data.getData());
    }

    private String getFileName(Uri uri) throws IllegalArgumentException{
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor.getCount() <= 0){
            cursor.close();
            throw  new IllegalArgumentException("Can't obtain file name, cursor is empty");
        }

        cursor.moveToFirst();

        String filename = cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME));
        cursor.close();

        return filename;
    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


        private void importExcel(Uri uri) {
        String file = getRealPathFromURI(this, uri);

//        File file = null;
//        try {
//            file = new File(new URI(path));
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }

        Toast.makeText(this, file, Toast.LENGTH_SHORT).show();

//        if (!DatabaseHelper.deleteDatabase(this)){
//            Toast.makeText(this, "Failed to Delete", Toast.LENGTH_SHORT).show();
//            return;
//        }

        ExcelToSQLite excelToSQLite = new ExcelToSQLite(this, DatabaseHelper.DATABASE_NAME, true);
       // String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath();

        excelToSQLite.importFromFile(file, new ExcelToSQLite.ImportListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onCompleted(String dbName) {
                Toast.makeText(getApplicationContext(), "Imported", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
//                new AlertDialog.Builder(getApplicationContext())
//                        .setTitle("Delete entry")
//                        .setMessage( "Error: " + e.getMessage())
//
//                        // Specifying a listener allows you to take an action before dismissing the dialog.
//                        // The dialog is automatically dismissed when a dialog button is clicked.
//                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                // Continue with delete operation
//                            }
//                        })

//                        // A null listener allows the button to dismiss the dialog and take no further action.
//                        .setNegativeButton(android.R.string.no, null)
//                        .setIcon(android.R.drawable.ic_dialog_alert)
//                        .show();
            }
        });
    }

    /**
     * Copies a uri reference to a temporary file
     *
     * @param uri the uri used as the input stream
     * @param tempFile the file used as an output stream
     * @return the input tempFile for convenience
     * @throws IOException if an error occurs
     */
    private File copyToTempFile(Uri uri, File tempFile) throws IOException {
        // Obtain an input stream from the uri
        InputStream inputStream = getContentResolver().openInputStream(uri);

        if (inputStream == null){
            throw new IOException("Unable to obtain input stream from URI");
        }

        // Copy the stream to the temp file
        // FileUtils.copyInputStreamToFile(inputStream, tempFile);
        return tempFile;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
}
























