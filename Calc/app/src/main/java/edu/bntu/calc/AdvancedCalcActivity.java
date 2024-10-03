package edu.bntu.calc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class AdvancedCalcActivity extends AppCompatActivity {
    private long timePressed;
    private StringBuilder input = new StringBuilder();
    private TextView tvResult;
    private double operand1 = 0.0;
    private double operand2 = 0.0;
    private String operator = null;
    private boolean calculated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_calc);

        tvResult = findViewById(R.id.tvResult);
        setEqualButtonAnimation();
    }

    private void setEqualButtonAnimation() {
        Button btnEqual = findViewById(R.id.btnEqual);
        btnEqual.setOnClickListener(v -> {
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.button_equal_animation);
            btnEqual.startAnimation(animation);
            onEqualButtonClick(v);
        });
    }

    public void onBackButtonClick(View view) {
        getOnBackPressedDispatcher().onBackPressed();
    }

    public void onSimpleCalcButtonClick(View view) {
        Intent intent = new Intent(this, SimpleCalcActivity.class);
        startActivity(intent);
    }

    public void onClearButtonClick(View view) {
        input.setLength(0);
        tvResult.setText("");
    }

    public void onEraseButtonClick(View view) {
        if (input.length() > 0) {
            input.deleteCharAt(input.length() - 1);
            tvResult.setText(input.toString());
        }
    }

    public void onBinaryOperatorButtonClick(View view) {
        Button button = (Button) view;
        operator = button.getText().toString();
        if (input.toString().isEmpty()) {
            if (Objects.equals(operator, "-")) {
                input.append("-");
                tvResult.setText("-");
            }
            else this.showInputValueMassage();
        } else {
            operand1 = Double.parseDouble(input.toString());
            input.setLength(0);
        }
    }

    public void onDigitButtonClick(View view) {
        if (calculated) {
            tvResult.setText("");
            input.setLength(0);
            calculated = false;
        }

        Button button = (Button) view;
        int digit = Integer.parseInt((String) button.getText());
        input.append(digit);
        tvResult.setText(input.toString());
    }

    public void onDecimalButtonClick(View view) {
        if (!input.toString().isEmpty() && !input.toString().contains(".")) {
            input.append(".");
            tvResult.setText(input.toString());
        }
    }

    public void onEqualButtonClick(View view) {
        if (operator == null) {
            Toast.makeText(this,
                    getString(R.string.choose_operator_message), Toast.LENGTH_SHORT).show();
            return;
        }
        if (!input.toString().isEmpty()) {
            operand2 = Double.parseDouble(input.toString());
            double result = 0.0;
            switch (operator) {
                case "+":
                    result = operand1 + operand2;
                    break;
                case "-":
                    result = operand1 - operand2;
                    break;
                case "*":
                    result = operand1 * operand2;
                    break;
                case "/":
                    result = operand1 / operand2;
                    break;
                case "x^y":
                    result = Math.pow(operand1, operand2);
                    break;
                default:
                    break;
            }
            input.setLength(0);
            input.append(result);
            tvResult.setText(input.toString());
            calculated = true;
            operator = null;
        }
    }

    private double factorial(int n) {
        if (n == 0 || n == 1) {
            return 1;
        } else {
            return n * factorial(n - 1);
        }
    }

    public void onPiButtonClick(View view) {
        if (calculated) {
            tvResult.setText("");
            input.setLength(0);
            calculated = false;
        }

        input.append(Math.PI);
        tvResult.setText(input.toString());
    }

    public void onEButtonClick(View view) {
        if (calculated) {
            tvResult.setText("");
            input.setLength(0);
            calculated = false;
        }

        input.append(Math.E);
        tvResult.setText(input.toString());
    }

    public void onUnaryOperatorClick(View view) {
        if (input.toString().isEmpty()) {
            this.showInputValueMassage();
        } else {
            Button button = (Button) view;
            String unaryOperator = button.getText().toString();
            double operand = Double.parseDouble(input.toString());
            double result = 0.0;

            switch (unaryOperator) {
                case "|x|":
                    result = Math.abs(operand);
                    break;
                case "exp":
                    result = Math.exp(operand);
                    break;
                case "ln":
                    result = Math.log(operand);
                    break;
                case "lg":
                    result = Math.log10(operand);
                    break;
                case "1/x":
                    result = 1 / operand;
                    break;
                case "%":
                    result = operand / 100.0;
                    break;
                case "√":
                    if (operand >= 0) {
                        result = Math.sqrt(operand);
                    } else {
                        Toast.makeText(this,
                                getString(R.string.negative_square_root_message),
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
                case "x!":
                    try {
                        int n = Integer.parseInt(input.toString());
                        result = factorial(n);
                    } catch (Exception e) {
                        Toast.makeText(this,
                                getString(R.string.factorial_integer_message),
                                Toast.LENGTH_SHORT).show();
                    }
                default:
                    break;
            }
            calculated = true;
            input.setLength(0);
            input.append(result);
            tvResult.setText(input.toString());
        }
    }

    private void showInputValueMassage() {
        Toast.makeText(this,
                getString(R.string.input_value_message), Toast.LENGTH_SHORT).show();
    }

    public void onExitButtonClick(View view) {
        if (timePressed + 2000 > System.currentTimeMillis()) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("finish", true);
            startActivity(intent);
        } else {
            Toast.makeText(this, getString(R.string.confirm_exit_message),
                    Toast.LENGTH_SHORT).show();
        }
        timePressed = System.currentTimeMillis();
    }
}
