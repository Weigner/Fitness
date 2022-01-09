package com.example.fitness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ListCalcActivity extends AppCompatActivity {

    private RecyclerView recyclerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_calc);

        Bundle extras = getIntent().getExtras();

        recyclerList = findViewById(R.id.recycler_list);


        if (extras != null) {
            String type = extras.getString("type");
            List<RegisterModel> registers = SqlHelper.getInstance(this).getRegister(type);

            new Thread(() -> {
                runOnUiThread(() -> {
                    CalcItemAdapter adapter = new CalcItemAdapter(registers);
                    recyclerList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
                    recyclerList.setAdapter(adapter);
                });
            }).start();
        }
    }

    private class CalcItemAdapter extends RecyclerView.Adapter<ListCalcActivity.CalcItemAdapter.CalcItemViewHolder> {

        private List<RegisterModel> calcItems;

        public CalcItemAdapter(List<RegisterModel> calcItems) {
            this.calcItems = calcItems;
        }

        @NonNull
        @Override
        public CalcItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ListCalcActivity.CalcItemAdapter.CalcItemViewHolder(getLayoutInflater().inflate(android.R.layout.simple_list_item_1, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull CalcItemAdapter.CalcItemViewHolder holder, int position) {
            RegisterModel calcItemCurrent = calcItems.get(position);
            holder.bind(calcItemCurrent);
        }

        @Override
        public int getItemCount() {
            return calcItems.size();
        }

        private class CalcItemViewHolder extends RecyclerView.ViewHolder {

            public CalcItemViewHolder(@NonNull View itemView) {
                super(itemView);
            }

            public void bind(RegisterModel item) {
                String formatted = "";
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("pt", "BR"));
                    Date dateSaved = dateFormat.parse(item.createdDate);
                    SimpleDateFormat dateFormatResult = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", new Locale("pt", "BR"));
                    formatted = dateFormatResult.format(dateSaved);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                ((TextView) itemView).setText(getString(R.string.list_reponse, item.response, formatted));
            }
        }
    }
}