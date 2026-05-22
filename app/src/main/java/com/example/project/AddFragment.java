package com.example.project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class AddFragment extends Fragment {
Button btn;
EditText title, amount, category, date;
DatabaseHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add,container,false);
        title = view.findViewById(R.id.etTitle);
        amount = view.findViewById(R.id.etAmount);
        category = view.findViewById(R.id.etCategory);
        date = view.findViewById(R.id.etDate);
        btn=view.findViewById(R.id.save);
        db =  new DatabaseHelper(getContext());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.insertExpenese(
                        title.getText().toString(),
                        amount.getText().toString(),
                        category.getText().toString(),
                        date.getText().toString()
                );
                Toast.makeText(getContext(),"Data Saved" , Toast.LENGTH_LONG).show();
            }
        });


        return view;
    }
}