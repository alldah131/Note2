package se.example.android.note2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {

    ListView listView;
    EditText editText;
    TextView textView;

    Button add;
    Button update;
    Button delete;

    ArrayList<String> names = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    MyDBHandler dbHandler;

    public MainActivity(){

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView1);
        textView = (TextView) findViewById(R.id.textView1);
        editText = (EditText) findViewById(R.id.editTxt);
        add = (Button) findViewById(R.id.addButton);
        update = (Button) findViewById(R.id.updateButton);
        delete = (Button) findViewById(R.id.deleteButton);
        dbHandler = new MyDBHandler(this, null, null, 1);
        printDataBase();

        // Adapter
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, names);
        listView.setAdapter(adapter);

        //Set selected item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String object = String.valueOf(parent.getItemAtPosition(position));
                Toast.makeText(MainActivity.this, object, Toast.LENGTH_SHORT).show();

                editText.setText(names.get(position));
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
            adapter.add(name);
            editText.setText("");

            Toast.makeText(getApplicationContext(), "Added" + name, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "!! Nothing to add", Toast.LENGTH_SHORT).show();

        }
    }

        private void update(){

            String name = editText.getText().toString();

            int pos = listView.getCheckedItemPosition();

            if(!name.isEmpty() && name.length()>0){
                adapter.remove(names.get(pos));

                adapter.insert(name, pos);


                adapter.notifyDataSetChanged();

                Toast.makeText(getApplicationContext(), "Updated " + name, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "!! Nothing to Update ", Toast.LENGTH_SHORT).show();
            }
    }

        private void delete(){
            int pos = listView.getCheckedItemPosition();
            if(pos > -1){
                adapter.remove(names.get(pos));

                adapter.notifyDataSetChanged();

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
            printDataBase();
        }

        public void deleteButtonClicked(View view){
            String inputText = editText.getText().toString();
            dbHandler.deleteProduct(inputText);
            printDataBase();
        }

        public void printDataBase() {
            String dbString = dbHandler.databaseToString();
            textView.setText(dbString);
            editText.setText("");

        }
}
