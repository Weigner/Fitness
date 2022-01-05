package com.example.fitness;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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

        btnSend.setOnClickListener(view -> {
            if (!validate()) {
                Toast.makeText(ImcActivity.this, R.string.fields_messages, Toast.LENGTH_SHORT).show();
                return;
            }

            String sHeigth = editHeigth.getText().toString();
            String sWeigth = editWeigth.getText().toString();

            int heigth = Integer.parseInt(sHeigth);
            int weigth = Integer.parseInt(sWeigth);

            double result = calculateImc(heigth, weigth);
            int imcResponseId = imcResponse(result);

            Log.d("teste", "resultado:" + result);

            AlertDialog dialog = new AlertDialog.Builder(ImcActivity.this)
                    .setTitle(getString(R.string.imc_response, result))
                    .setMessage(imcResponseId)
                    .setPositiveButton(android.R.string.ok, (dialogInterface, i) -> {
                    })
                    .setNegativeButton(R.string.save, (dialogInterface, i) -> {
                        new Thread(() -> {
                            long calcId = SqlHelper.getInstance(ImcActivity.this).addItem("imc", result);
                            runOnUiThread(() -> {
                                if (calcId > 0) {
                                    Toast.makeText(ImcActivity.this, R.string.calc_saved, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }).start();
                    })
                    .create();
            dialog.show();

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editWeigth.getWindowToken(), 0);
            imm.hideSoftInputFromWindow(editHeigth.getWindowToken(), 0);

        });
    }

    @StringRes
    private int imcResponse(double imc) {
        if (imc < 15) {
            return R.string.imc_severely_low_weight;
        } else if (imc < 16) {
            return R.string.imc_very_low_weight;
        } else if (imc < 18.5) {
            return R.string.imc_low_weight;
        } else if (imc < 25) {
            return R.string.normal;
        } else if (imc < 30) {
            return R.string.imc_high_weight;
        } else if (imc < 35) {
            return R.string.imc_so_high_weight;
        } else if (imc < 16) {
            return R.string.imc_severely_high_weight;
        }
        return R.string.imc_extreme_weight;
    }

    private double calculateImc(int heigth, int weigth) {
        return weigth / ((double) heigth / 100 * (double) heigth / 100);
    }

    private boolean validate() {
        return (!editHeigth.getText().toString().startsWith("0")
                && !editWeigth.getText().toString().startsWith("0")
                && !editHeigth.getText().toString().isEmpty()
                && !editWeigth.getText().toString().isEmpty());
    }
}