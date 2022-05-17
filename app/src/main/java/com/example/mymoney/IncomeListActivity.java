package com.example.mymoney;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class IncomeListActivity extends AppCompatActivity {

    ListView listView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_list);

        getSupportActionBar().hide();

        CategoryIncome categoryIncome = (CategoryIncome) getIntent().getSerializableExtra("cat_id");

        List<String> list = BD.allIncomeIzCategory(this, categoryIncome.getId());

        listView = findViewById(R.id.listIncomeByCategory);
        textView = findViewById(R.id.textView4);
        textView.setText("Доходы в категории " + categoryIncome.getName());
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
    }
}