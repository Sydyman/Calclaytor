package com.ex.calclaytor;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Double first;
    private Double second;
    private String currentOperation;
    private boolean isOperationClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        first = null;
        second = null;
        currentOperation = "";
        isOperationClick = false;
    }

    public void onNumberClick(View view) {
        String textButton = ((MaterialButton) view).getText().toString();
        if (textButton.equals("AC")) {
            textView.setText("0");
            first = null;
            second = null;
            currentOperation = "";
        } else if (textButton.equals(".")) {
            if (!textView.getText().toString().contains(".")) {
                textView.append(".");
            }
        } else if (textView.getText().toString().equals("0") || isOperationClick) {
            textView.setText(textButton);
        } else {
            textView.append(textButton);
        }
        isOperationClick = false;
    }

    public void onOperationClick(View view) {
        if (first == null) {
            first = Double.parseDouble(textView.getText().toString());
        } else {
            second = Double.parseDouble(textView.getText().toString());
            calculate();
        }
        currentOperation = ((MaterialButton) view).getText().toString();
        isOperationClick = true;
    }



    public void onToggleSignClick(View view) {
        String currentText = textView.getText().toString();
        if (!currentText.equals("0")) {
            if (currentText.startsWith("-")) {
                textView.setText(currentText.substring(1));
            } else {
                textView.setText("-" + currentText);
            }
        }
    }

    private void calculate() {
        switch (currentOperation) {
            case "+":
                first += second;
                break;
            case "-":
                first -= second;
                break;
            case "x":
                first *= second;
                break;
            case "/":
                if (second != 0) {
                    first /= second;
                } else {
                    first = null;
                    second = null;
                    currentOperation = "";
                    return;
                }
                break;
        }
        displayResult(first);
    }

    private void displayResult(double result) {
        if (result == (int) result) {
            textView.setText(String.valueOf((int) result));
        } else {
            textView.setText(String.valueOf(result));
        }
    }
}
