package com.example.mymoney;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private double plus, minus, ballans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        minus = BD.totalExpence(this);
        plus = BD.totalIncome(this);
        ballans = plus + -minus;

        BD.allMoney(this);
        Log.d("money777", "общие траты");
        Log.d("money777", minus + "");
        Log.d("money777", "общие доходы");
        Log.d("money777", plus + "");
        Log.d("money777", ",балланс");
        Log.d("money777", ballans + "");

    }
}