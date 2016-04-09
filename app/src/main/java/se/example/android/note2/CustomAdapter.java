package se.example.android.note2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;


public class CustomAdapter extends ArrayAdapter<String> {


    TextView textView;
    RadioButton radioButton;
    MainActivity main = new MainActivity();

    public CustomAdapter(Context context, String[] items) {
            super(context, R.layout.custom_layout, items);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View customView = inflater.inflate(R.layout.custom_layout, parent, false);

            String singleItem = getItem(position);

            textView = (TextView) customView.findViewById(R.id.textView1);
            radioButton = (RadioButton) customView.findViewById(R.id.radioButton);

            printDataBase();
            return customView;

        }

    public void printDataBase() {
        String dbString = main.dbHandler.databaseToString();
        textView.setText(dbString);
        main.editText.setText("");
    }
}
