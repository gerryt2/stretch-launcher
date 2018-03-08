package com.StretchSense.DemoMultiple;

import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;

import android.app.Activity;
import android.widget.TextView;
/**
 * MyMenuListAdapter Class
 *
 * Define the adapter of the view list
 *
 * @author StretchSense
 * @version 1.0
 * @since 07/2016
 * @see ' www.stretchsense.com
 */
class MyMenuListAdapter extends ArrayAdapter<TableItem> {

    private Context context;
    private int ressource;
    private TableItem[] objects;

    public MyMenuListAdapter(Context context, int ressource, TableItem[] objects) {
        super(context, ressource, objects);
        this.context = context;
        this.ressource = ressource;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            LayoutInflater inflater = ((Activity) this.context).getLayoutInflater();
            convertView = inflater.inflate(this.ressource, parent, false);
        }

        TextView menuTextViewName = (TextView) convertView.findViewById(R.id.nameTextView);
        TextView menuTextViewValue = (TextView) convertView.findViewById(R.id.valueTextView);
        TextView menuTextViewNumber = (TextView) convertView.findViewById(R.id.numberOfTheSensor);

        TableItem item = objects[position];
        if (item != null){
            menuTextViewName.setText(item.getMenuText());
            menuTextViewValue.setText(item.getValueText() + " pF");
            menuTextViewNumber.setText(item.getNumberText());

        }

        String nameOfTheColor = "color"+item.getNumber();

        convertView.setBackgroundResource(resourceNameToId(getContext(), nameOfTheColor, "color"));


        return convertView;
    }

    public static int resourceNameToId(Context context, String name, String resType) {
        if (name != null && name.length() > 0) {
            return context.getResources().getIdentifier(name, resType, context.getPackageName());
        }

        return 0;
    }
}
