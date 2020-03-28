package com.example.calculator;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView display;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initButton();
        initDisplay();
    }

    private void initDisplay() {
        display = (TextView) findViewById(R.id.display);
    }

    private void initButton() {
        List<View> views = new ArrayList<>();
        getAllButton(this.getWindow().getDecorView(), views);
        views.stream().filter(view -> view instanceof Button).forEach(button -> button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v instanceof Button) {
                    click(((Button) v).getText().toString());
                }
            }
        }));
    }


    public void getAllButton(View root, List<View> result) {
        if (!result.contains(root)) result.add(root);
        if (root instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) root;
            for (int i = 0; i < group.getChildCount(); i++) {
                View child = group.getChildAt(i);
                getAllButton(child, result);
            }
        } else {
            return;
        }
    }


    public void click(String button) {
        Log.i("click button: ", button);
        String expression = display.getText().toString();
        if ("CE".equals(button)) {
            display.setText(null);
            return;
        }
        if ("ERROR".equals(expression)) {
            return;
        }
        if ("=".equals(button)) {
            try {
                String result = Expression.calculate(expression);
                display.setText(result);
            } catch (Exception e) {
                display.setText("ERROR");
            }
            return;
        }
        if (!expression.isEmpty()) {
            if (isOperator(button) && isOperator(expression.charAt(expression.length() - 1))) {
                display.setText(expression.substring(0, expression.length() - 1));
            }
            if (isDigit(button) && expression.charAt(expression.length() - 1) == '0') {
                display.setText(expression.substring(0, expression.length() - 1));
            }
        } else if (isOperator(button)) {
            return;
        }
        display.append(button);

    }



    private boolean isDigit(String button) {
        return Character.isDigit(button.charAt(0));
    }

    private boolean isOperator(String button) {
        return isOperator(button.charAt(0));
    }

    private boolean isOperator(Character button) {
        return Expression.LEVEL_MAP.keySet().contains(button);
    }
}
