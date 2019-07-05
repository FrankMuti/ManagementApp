package com.example.blacksky.datamodels;

public class ChartDataModel {

    private String[] C_ServiceName;
    private int[] C_ServiceEarning;
    private int[] S_ServicePaid;

    public ChartDataModel(String[] C_ServiceName, int[] C_ServiceEarning, int[] S_ServicePaid) {
        this.C_ServiceName = C_ServiceName;
        this.C_ServiceEarning = C_ServiceEarning;
        this.S_ServicePaid = S_ServicePaid;
    }

    private Object getObject(Object[] arr, int i){
        return arr[i];
    }

    private int totalServiceEarning() {
        int total = 0;
        for (int se : C_ServiceEarning){
            total += se;
        }
        return total;
    }

    private int totalServicePaid() {
        int total = 0;
        for (int sp : S_ServicePaid) {
            total += sp;
        }
        return total;
    }




}
