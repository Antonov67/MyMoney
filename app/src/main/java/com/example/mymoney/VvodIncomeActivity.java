package com.example.mymoney;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class VvodIncomeActivity extends AppCompatActivity {

    ListView listView;
    TextView textResult;
    EditText dateIncome, summaField, itemField;
    int cat_id = -1;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vvod_income);
        getSupportActionBar().hide();

        listView = findViewById(R.id.listVvodIncome);
        textResult = findViewById(R.id.textPreviewVvodIncome);
        dateIncome = findViewById(R.id.dateIncome);
        summaField = findViewById(R.id.summaFieldIncome);
        itemField = findViewById(R.id.itemIncomeField);

        //список названий категорий из общего списка категорий
        List<String> list = new ArrayList<>();

        List<CategoryIncome> listTotal = BD.allCatIncome(this);

        for (CategoryIncome categoryIncome : listTotal){
            list.add(categoryIncome.getName());
        }


        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                cat_id = listTotal.get(i).getId();
            }
        });

        dateIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(VvodIncomeActivity.this,android.R.style.Theme_Holo_Dialog_MinWidth, mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String strmonth="", strday="";
                month = month + 1;
                if (Integer.toString(month).length() == 1) strmonth = "0" + Integer.toString(month); else strmonth = Integer.toString(month);
                if (Integer.toString(day).length() == 1) strday = "0" + Integer.toString(day); else strday = Integer.toString(day);
                String date = year + "-" + strmonth + "-" + strday;
                dateIncome.setText(date);
            }
        };
    }

    public void vvodIncomeTotal(View view) {

        //если поля не пустые, то создадим объект "доходы" и добавим его в БД
        if (!summaField.getText().toString().equals("")
                && !itemField.getText().toString().equals("")
                && !dateIncome.getText().toString().equals("")
                && cat_id != -1)
        {
            Income income = new Income(
                    BD.USER_ID,dateIncome.getText().toString(),
                    Double.parseDouble(summaField.getText().toString()),
                    itemField.getText().toString(),cat_id);
            textResult.setText(income.toString());
            BD.addIncome(income, this);
        }else {
            Toast.makeText(getApplicationContext(),"Заполните все поля",Toast.LENGTH_SHORT).show();
        }
    }
}