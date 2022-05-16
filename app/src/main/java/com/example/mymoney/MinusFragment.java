package com.example.mymoney;

import android.content.Context;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;


public class MinusFragment extends Fragment {

    ListView listView;
    ListAdapter adapter;
    List<String> list;
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
        list = BD.allExpence(context);
        adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
    }
}