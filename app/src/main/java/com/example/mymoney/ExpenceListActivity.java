package com.example.mymoney;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class ExpenceListActivity extends AppCompatActivity {

    ListView listView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expence_list);
        getSupportActionBar().hide();

        CategoryExpence categoryExpence = (CategoryExpence) getIntent().getSerializableExtra("cat_id");

        List<String> list = BD.allExpenceIzCategory(this, categoryExpence.getId());

        listView = findViewById(R.id.listExpenceByCategory);
        textView = findViewById(R.id.textView3);
        textView.setText("Расходы в категории " + categoryExpence.getName());
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
    }
}