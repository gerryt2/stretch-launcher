package com.StretchSense.DemoMultiple;


import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;

import android.app.Activity;
import android.widget.TextView;

/**
 * TableItem Class
 *
 * Define the adapter of the view list
 *
 * @author StretchSense
 * @version 1.0
 * @since 07/2016
 * @see ' www.stretchsense.com
 */
public class TableItem {
    private String menuText;
    private int value;
    private int number;

    public TableItem(String menuText, int value, int number) {
        this.menuText = menuText;
        this.value = value;
        this.number = number;
    }


    public String getNumberText() {
        return intToString(number);
    }
    public int getNumber() {
        return number;
    }

    public String getValueText() {
        return intToString(value);
    }

    public String getMenuText() {
        return menuText;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setMenuText(String menuText) {
        this.menuText = menuText;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }


    private String intToString(int integer){
        return Integer.toString(integer);
    }

    @Override
    public String toString() {
        return menuText + " " + value;
    }
}

