package com.example.mymoney;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class PlusFragment extends Fragment {

    ListView listView;
    ListAdapter adapter;
    List<IncomeByCategory> totalList;
    Context context;

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
    }
}