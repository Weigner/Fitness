package com.example.fitness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // private View btnImc;
    private RecyclerView recyclerMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        btnImc = findViewById(R.id.btn_imc);
        recyclerMain = findViewById(R.id.recycler_main);


        List<MainItem> mainItems = new ArrayList<>();
        mainItems.add(new MainItem(1, R.drawable.ic_baseline_wb_sunny_24, R.string.label_imc, R.color.icarust));
        mainItems.add(new MainItem(2, R.drawable.ic_baseline_visibility_24, R.string.label_tmb, R.color.anonymuse));

        // mosaic, grid ou linear(horizontal | vertical)
        recyclerMain.setLayoutManager(new LinearLayoutManager(this));

        MainAdapter adapter = new MainAdapter(mainItems);
        recyclerMain.setAdapter(adapter);


//        btnImc.setOnClickListener(view -> {
//            Intent intent = new Intent(getBaseContext(), ImcActivity.class);
//            startActivity(intent);
//        });
    }

    private class MainAdapter extends RecyclerView.Adapter<MainViewHolder> {

        private List<MainItem> mainItems;

        public MainAdapter(List<MainItem> mainItems) {
            this.mainItems = mainItems;
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
    }

    private class MainViewHolder extends RecyclerView.ViewHolder {

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(MainItem item) {
            TextView txtName = itemView.findViewById(R.id.item_txt_name);
            ImageView imgIcon = itemView.findViewById(R.id.item_img_icon);
            LinearLayout container = (LinearLayout) itemView;

            txtName.setText(item.getTextStringId());
            imgIcon.setImageResource(item.getDrawableId());
            container.setBackgroundColor(item.getColor());
        }
    }
}