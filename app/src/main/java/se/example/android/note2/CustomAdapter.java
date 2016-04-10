package se.example.android.note2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;


public class CustomAdapter extends ArrayAdapter<String> {


    MainActivity main = new MainActivity();

    public CustomAdapter(Context context, ArrayList<String> items) {
            super(context, R.layout.custom_layout, items);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            String singleItem = getItem(position);
            if(convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_layout, parent,false);

            }

            String dbString = main.dbHandler.databaseToString();
            main.textView.setText(dbString);
            main.editText.setText("");

            return convertView;

        }

    public void printDataBase() {
        String dbString = main.dbHandler.databaseToString();
        main.textView.setText(dbString);
        main.editText.setText("");
    }
}
