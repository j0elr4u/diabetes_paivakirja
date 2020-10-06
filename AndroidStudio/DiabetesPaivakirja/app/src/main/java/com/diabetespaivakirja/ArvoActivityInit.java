package com.diabetespaivakirja;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ArvoActivityInit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arvo_init);

        main();
    }

    private void main() {

    }

    // Buttons

    public void onButtonPressed_Back (View view) {
        super.onBackPressed();
    }

    public void onButtonPressed_ListView(View view) {
        Intent nextActivity = new Intent(ArvoActivityInit.this, ArvoActivity.class);
        nextActivity.putExtra("boolean", true);
        startActivity(nextActivity);
    }

    public void onButtonPressed_AnyChart(View view) {
        Intent nextActivity = new Intent(ArvoActivityInit.this, ArvoActivity.class);
        nextActivity.putExtra("boolean", false);
        startActivity(nextActivity);
    }
}