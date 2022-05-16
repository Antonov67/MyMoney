package com.example.mymoney;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MinusFragment extends Fragment {

    ListView listView;
    ListAdapter adapter;
    List<ExpenceByCategory> totalList;
    Context context;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = view.getContext();
        listView = view.findViewById(R.id.listExpence);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_minus, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        totalList = BD.allExpenceGroupByCategory(context);
        List<String> list = new ArrayList<>();

        for (ExpenceByCategory expenceByCategory : totalList){
            list.add(expenceByCategory.getName() + " " + expenceByCategory.getSumma());
        }

        adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //передадим в интент номер категории для вывода подробных расходов в новом окне
                Intent intent = new Intent(getActivity(),ExpenceListActivity.class);
                CategoryExpence categoryExpence = new CategoryExpence(totalList.get(i).getId(),totalList.get(i).getName());
                intent.putExtra("cat_id", categoryExpence);
                startActivity(intent);
            }
        });
    }
}