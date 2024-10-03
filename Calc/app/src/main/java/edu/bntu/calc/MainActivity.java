package edu.bntu.calc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private long timePressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getIntent().getBooleanExtra("finish", false)) finish();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onSimpleCalcButtonClick(View view) {
        Intent intent = new Intent(this, SimpleCalcActivity.class);
        startActivity(intent);
    }

    public void onAdvancedCalcButtonClick(View view) {
        Intent intent = new Intent(this, AdvancedCalcActivity.class);
        startActivity(intent);
    }

    public void onExitButtonClick(View view) {
        if (timePressed + 2000 > System.currentTimeMillis()) {
            this.finish();
        } else {
            Toast.makeText(this, getString(R.string.confirm_exit_message),
                    Toast.LENGTH_SHORT).show();
        }
        timePressed = System.currentTimeMillis();
    }
}