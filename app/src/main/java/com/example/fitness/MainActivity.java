package com.example.fitness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerMain = findViewById(R.id.recycler_main);


        List<MainItem> mainItems = new ArrayList<>();
        mainItems.add(new MainItem(1, R.drawable.ic_baseline_fitness_center_24, R.string.label_historic, R.color.HartnSoul));
        mainItems.add(new MainItem(1, R.drawable.ic_baseline_food_bank_24, R.string.label_food, R.color.HartnSoul));
        mainItems.add(new MainItem(1, R.drawable.ic_baseline_monitor_heart_24, R.string.label_bpm, R.color.HartnSoul));
        mainItems.add(new MainItem(2, R.drawable.ic_baseline_fitbit_24, R.string.label_imc, R.color.HartnSoul));
        mainItems.add(new MainItem(3, R.drawable.ic_baseline_incomplete_circle_24, R.string.label_tmb, R.color.HartnSoul));

        // mosaic, grid ou linear(horizontal | vertical)
        recyclerMain.setLayoutManager(new GridLayoutManager(this, 2));

        MainAdapter adapter = new MainAdapter(mainItems);
        adapter.setListener( id -> {
            switch (id) {
                case 1:
                    startActivity(new Intent(getBaseContext(), HistoricActivity.class));
                    break;
                case 2:
                    startActivity(new Intent(getBaseContext(), ImcActivity.class));
                    break;
                case 3:
                    startActivity(new Intent(getBaseContext(), TmbActivity.class));
                    break;
            }
        });

        recyclerMain.setAdapter(adapter);
    }

    private class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

        private List<MainItem> mainItems;
        private OnItemClickListener listener;

        public MainAdapter(List<MainItem> mainItems) {
            this.mainItems = mainItems;
        }

        public void setListener(OnItemClickListener listener) {
            this.listener = listener;
        }

        @NonNull
        @Override
        public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MainViewHolder(getLayoutInflater().inflate(R.layout.main_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
            MainItem mainItemCurrent = mainItems.get(position);
            holder.bind(mainItemCurrent);
        }

        @Override
        public int getItemCount() {
            return mainItems.size();
        }

        private class MainViewHolder extends RecyclerView.ViewHolder {

            public MainViewHolder(@NonNull View itemView) {
                super(itemView);
            }

            public void bind(MainItem item) {
                TextView txtName = itemView.findViewById(R.id.item_txt_name);
                ImageView imgIcon = itemView.findViewById(R.id.item_img_icon);
                LinearLayout btnImc = (LinearLayout) itemView.findViewById(R.id.btn_imc);

                btnImc.setOnClickListener(view -> {
                    listener.onClick(item.getId());
                });

                txtName.setText(item.getTextStringId());
                imgIcon.setImageResource(item.getDrawableId());
                btnImc.setBackgroundColor(item.getColor());
            }
        }
    }
}