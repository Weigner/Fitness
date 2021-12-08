package com.example.fitness;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ImcActivity extends AppCompatActivity {

    private EditText editHeigth;
    private EditText editWeigth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imc);

        editHeigth = findViewById(R.id.edit_imc_heigth);
        editWeigth = findViewById(R.id.edit_imc_weigth);

        Button btnSend = findViewById(R.id.btn_imc_send);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validate()){
                    Toast.makeText(ImcActivity.this, R.string.filds_messages, Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });

    }

    private boolean validate() {
        return (!editHeigth.getText().toString().startsWith("0")
                && !editWeigth.getText().toString().startsWith("0")
                && !editHeigth.getText().toString().isEmpty()
                && !editWeigth.getText().toString().isEmpty());
    }
}