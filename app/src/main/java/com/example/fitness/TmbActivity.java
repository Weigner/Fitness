package com.example.fitness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class TmbActivity extends AppCompatActivity {

    private EditText editHeigth;
    private EditText editWeigth;
    private EditText editAge;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tmb_actvity);

        editHeigth = findViewById(R.id.edit_tmb_heigth);
        editWeigth = findViewById(R.id.edit_tmb_weigth);
        editAge = findViewById(R.id.edit_tmb_age);
        spinner = findViewById(R.id.spinner_tmb_lifestyle);

        Button btnSend = findViewById(R.id.btn_tmb_send);

        btnSend.setOnClickListener(view -> {
            if (!validate()) {
                Toast.makeText(TmbActivity.this, R.string.fields_messages, Toast.LENGTH_SHORT).show();
                return;
            }

            String sHeigth = editHeigth.getText().toString();
            String sWeigth = editWeigth.getText().toString();
            String sAge = editAge.getText().toString();

            int heigth = Integer.parseInt(sHeigth);
            int weigth = Integer.parseInt(sWeigth);
            int age = Integer.parseInt(sAge);

            double result = calculateTmb(heigth, weigth, age);
            double tmb = tmbResponse(result);

            Log.d("teste", "resultado:" + tmb);

            AlertDialog dialog = new AlertDialog.Builder(TmbActivity.this)
                    .setTitle(getString(R.string.imc_response, result))
                    .setMessage("${result}")
                    .setPositiveButton(android.R.string.ok, (dialogInterface, i) -> {
                    })
                    .setNegativeButton(R.string.save, (dialogInterface, i) -> {
                        new Thread(() -> {
                            long calcId = SqlHelper.getInstance(TmbActivity.this).addItem("tmb", result);
                            runOnUiThread(() -> {
                                if (calcId > 0) {
                                    Toast.makeText(TmbActivity.this, R.string.calc_saved, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(TmbActivity.this, ListCalcActivity.class);
                                    intent.putExtra("type", "tmb");
                                    startActivity(intent);
                                }
                            });
                        }).start();
                    })
                    .create();
            dialog.show();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_list:
                openListCalcActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openListCalcActivity() {
        Intent intent = new Intent(TmbActivity.this, ListCalcActivity.class);
        intent.putExtra("type", "tmb");
        startActivity(intent);
    }

    private double tmbResponse(double tmb) {
        int index = spinner.getSelectedItemPosition();

        switch (index) {
            case 0:
                return tmb * 1.2;
            case 1:
                return tmb * 1.375;
            case 2:
                return tmb * 1.55;
            case 3:
                return tmb * 1.725;
            case 4:
                return tmb * 1.9;
            default:
                return 0;
        }
    }

    private double calculateTmb(int heigth, int weigth, int age) {
        return 66 + (weigth * 13.8) + (5 * heigth) - (6.8 * age);
    }

    private boolean validate() {
        return (!editHeigth.getText().toString().startsWith("0")
                && !editWeigth.getText().toString().startsWith("0")
                && !editAge.getText().toString().startsWith("0")
                && !editHeigth.getText().toString().isEmpty()
                && !editWeigth.getText().toString().isEmpty()
                && !editAge.getText().toString().isEmpty());
    }
}