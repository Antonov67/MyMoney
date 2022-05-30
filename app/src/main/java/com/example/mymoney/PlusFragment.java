package com.example.mymoney;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;


public class PlusFragment extends Fragment {

    ListView listView;
    ListAdapter adapter;
    List<IncomeByCategory> totalList;
    Context context;

    private PieChart chart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_plus, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = view.getContext();
        listView = view.findViewById(R.id.listIncome);
        chart = view.findViewById(R.id.chartIncome);
    }

    @Override
    public void onResume() {
        super.onResume();
        totalList = BD.allIncomeGroupByCategory(context);
        List<String> list = new ArrayList<>();

        for (IncomeByCategory incomeByCategory : totalList){
            list.add(incomeByCategory.getName() + " " + incomeByCategory.getSumma());
        }

        adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //передадим в интент номер категории для вывода подробных доходов в новом окне
                Intent intent = new Intent(getActivity(),IncomeListActivity.class);
                CategoryIncome categoryIncome = new CategoryIncome(totalList.get(i).getId(),totalList.get(i).getName());
                intent.putExtra("cat_id", categoryIncome);
                startActivity(intent);
            }
        });
        // array of graph different colors
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(ContextCompat.getColor(context, R.color.green));
        colors.add(ContextCompat.getColor(context, R.color.blue));
        colors.add(ContextCompat.getColor(context, R.color.orange));
        colors.add(ContextCompat.getColor(context, R.color.yellow));

        // Массив координат точек
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        for (int i=0; i<totalList.size(); i++) {
            entries.add(new PieEntry((float) totalList.get(i).getSumma(), totalList.get(i).getName()));
        }

        // initializing pie data set
        PieDataSet dataset = new PieDataSet(entries, "");
        // set the data
        PieData data = new PieData(dataset);        // initialize Piedata
        chart.setData(data);

        // colors according to the dataset
        dataset.setColors(colors);

        // adding legends to the desigred positions
        Legend l = chart.getLegend();
        l.setTextSize(14f);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setTextColor(Color.WHITE);
        l.setEnabled(false);
        // if no need to add description
        chart.getDescription().setEnabled(false);
        // animation and the center text color
        chart.animateY(1000);
        chart.setEntryLabelColor(Color.BLACK);
    }
}