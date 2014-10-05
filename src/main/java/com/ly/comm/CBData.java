package com.ly.comm;


public class CBData {

    private static CBData uniqueInstance = null;

    private CBData() {
    }

    public static CBData getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new CBData();
        }
        return uniqueInstance;
    }

    public void init()
    {

    }

}
