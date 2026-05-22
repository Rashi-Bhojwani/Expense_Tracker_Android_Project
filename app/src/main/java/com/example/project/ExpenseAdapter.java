package com.example.project;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder> {

    Context context;
    ArrayList<Expense> list;
    DatabaseHelper dbHelper;

    public ExpenseAdapter(Context context, ArrayList<Expense> list) {
        this.context = context;
        this.list = list;
        this.dbHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.expense_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Expense expense = list.get(position);

        holder.title.setText(expense.getTitle());
        holder.amount.setText("₹ " + expense.getAmount());
        holder.date.setText(expense.getDate());

        holder.itemView.setOnLongClickListener(v -> {

            new AlertDialog.Builder(context)
                    .setTitle("Delete Expense")
                    .setMessage("Are you sure you want to delete this item?")
                    .setPositiveButton("Yes", (dialog, which) -> {

                        int adapterPosition = holder.getAdapterPosition();
                        Expense item = list.get(adapterPosition);

                        // 1. DELETE FROM DATABASE (IMPORTANT FIX)
                        dbHelper.deleteExpense(item.getId());

                        // 2. REMOVE FROM LIST
                        list.remove(adapterPosition);
                        notifyItemRemoved(adapterPosition);

                        // 3. UPDATE TOTAL IN REAL TIME (optional callback approach)
                        Toast.makeText(context, "Expense Deleted", Toast.LENGTH_SHORT).show();

                    })
                    .setNegativeButton("No", null)
                    .show();

            return true;
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, amount, date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tvTitle);
            amount = itemView.findViewById(R.id.tvAmount);
            date = itemView.findViewById(R.id.tvDate);
        }
    }
}