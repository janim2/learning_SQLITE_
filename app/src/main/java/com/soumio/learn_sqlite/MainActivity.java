package com.soumio.learn_sqlite;

import android.database.Cursor;
import android.icu.text.UnicodeSetSpanner;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.soumio.learn_sqlite.Adapters.DBAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText name_editText, posititon_editText;
    private Button save_button, retrieve_button;
    private ListView results_listView;

    private DBAdapter db;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> players = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name_editText = findViewById(R.id.name);
        posititon_editText = findViewById(R.id.position);
        save_button = findViewById(R.id.save_button);
        retrieve_button = findViewById(R.id.retrieve_button);
        results_listView = findViewById(R.id.list_view);

        db = new DBAdapter(this);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, players);

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //OPEN DB
                db.openDB();

                //INSERT DB
                long result = add.db(name_editText.getText().toString(),posititon_editText.getText().toString());

                if(result > 0){
                    Toast.makeText(MainActivity.this, "Added", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_LONG).show();
                }

                //CLOSE DB
                db.CloseDB();
            }
        });

        retrieve_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CLEAR ARRAY LIST
                players.clear();

                //OPEN DB
                db.openDB();

                //RETRIEVE
                Cursor c = db.getAllNames();

                while(c.moveToNext()){
                    String name = c.getString(1);
                    String pos = c.getString(2);
                    players.add(name + " : " + pos);
                }

                //
                results_listView.setAdapter(adapter);
                db.CloseDB();
            }
        });
    }
}
