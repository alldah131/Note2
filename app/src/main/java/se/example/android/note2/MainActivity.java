package se.example.android.note2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {

    TextView textView;
    RadioButton radioButton;
    ListView listView;
    EditText editText;
    ArrayList<String> items = new ArrayList<String>();
    Button add;
    Button update;
    Button delete;
    CustomAdapter customAdapter = new CustomAdapter(this, items);
    MyDBHandler dbHandler;

    public MainActivity(){

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView1);
        editText = (EditText) findViewById(R.id.editTxt);
        textView = (TextView) findViewById(R.id.textView1);
        radioButton = (RadioButton) findViewById(R.id.radioButton);
        add = (Button) findViewById(R.id.addButton);
        update = (Button) findViewById(R.id.updateButton);
        delete = (Button) findViewById(R.id.deleteButton);
        dbHandler = new MyDBHandler(this, null, null, 1);


        // Adapter
        customAdapter.printDataBase();
        listView.setAdapter(customAdapter);

        //Set selected item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String object = String.valueOf(parent.getItemAtPosition(position));
                Toast.makeText(MainActivity.this, object, Toast.LENGTH_SHORT).show();

                editText.setText(items.get(position));
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                add();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                update();
            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                delete();
            }
        });


    }       private void add() {
        String name = editText.getText().toString();
        if (!name.isEmpty() && name.length() > 0) {
            customAdapter.add(name);
            editText.setText("");

            Toast.makeText(getApplicationContext(), "Added" + name, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Nothing to add", Toast.LENGTH_SHORT).show();

        }
    }

        private void update(){

            String name = editText.getText().toString();

            int pos = listView.getCheckedItemPosition();

            if(!name.isEmpty() && name.length()>0){
                customAdapter.remove(items.get(pos));

                customAdapter.insert(name, pos);


                customAdapter.notifyDataSetChanged();

                Toast.makeText(getApplicationContext(), "Updated " + name, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Nothing to Update ", Toast.LENGTH_SHORT).show();
            }
    }

        private void delete(){
            int pos = listView.getCheckedItemPosition();
            if(pos > -1){
                customAdapter.remove(items.get(pos));

                customAdapter.notifyDataSetChanged();

                editText.setText("");
                Toast.makeText(getApplicationContext(), "Deleted " , Toast.LENGTH_SHORT).show();
            }

            else{
                Toast.makeText(getApplicationContext(), "!! Nothing to delete ", Toast.LENGTH_SHORT).show();
            }
        }


            //Add a product to the database
        public void addButtonClicked(View view){
            Products products = new Products(editText.getText().toString());
            dbHandler.addProduct(products);
            customAdapter.printDataBase();
        }

        public void deleteButtonClicked(View view){
            String inputText = editText.getText().toString();
            dbHandler.deleteProduct(inputText);
            customAdapter.printDataBase();
        }


}
