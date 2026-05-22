package com.example.project;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Expense> list;
    ExpenseAdapter adapter;
    DatabaseHelper db;
    TextView total;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_view, container, false);

        recyclerView = view.findViewById(R.id.recycleView);
        total = view.findViewById(R.id.total);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        db = new DatabaseHelper(getContext());

        loadData(view);

        return view;
    }

    private void loadData(View view) {

        list = new ArrayList<>();

        Cursor cursor = db.getAllExpense();

        if (cursor != null && cursor.moveToFirst()) {
            do {

                list.add(new Expense(
                        cursor.getInt(0),      // id (IMPORTANT FIX)
                        cursor.getString(1),   // title
                        cursor.getString(2),   // amount
                        cursor.getString(3),   // category
                        cursor.getString(4)    // date
                ));

                //list.add(expense);

            } while (cursor.moveToNext());

            cursor.close();
        }

        adapter = new ExpenseAdapter(getContext(), list);
        recyclerView.setAdapter(adapter);

        updateTotal();
    }

    private void updateTotal() {

        Cursor totalCursor = db.getTotalAmount();

        if (totalCursor != null && totalCursor.moveToFirst()) {

            String amount = totalCursor.getString(0);

            if (amount == null) amount = "0";

            total.setText("Total: ₹ " + amount);

            totalCursor.close();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // 🔥 IMPORTANT: refresh data every time fragment opens
        if (db != null) {
            loadData(getView());
        }
    }
}