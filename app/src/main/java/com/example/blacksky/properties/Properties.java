package com.example.blacksky.properties;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;

public class Properties {

    Context context;

    public Properties(Context context){
        this.context = context;
    }

    public Typeface tfRobotoBold = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Bold.ttf");
    public Typeface tfRobotoLight = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf");

    public String months[] = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
    public String services[] = {"NONE","WEDDING PHOTOGRAPHY", "WEDDING VIDEOGRAPHY", "WEDDING PHOTOGRAPHY & VIDEOGRAPHY", "FAMILY PHOTOSHOOT", "BIRTHDAY SHOOT",
            "STUDIO SHOOT", "COUPLE SHOOT", "FASHION SHOOT", "PRODUCT PHOTOGRAPHY", "GRADUATION SHOOT", "BABY SHOWERS", "BRIDAL SHOWERS",
            "BRIDAL SHOWERS", "ENGAGEMENT SHOOT", "CORP AND COMMERCIAL COVERAGE", "INTERIOR/ REAL ESTATE PHOTOGRAPHY"};

}
