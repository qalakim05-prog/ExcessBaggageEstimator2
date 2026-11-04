package com.haiqal.excessbaggageestimator;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setTitle(R.string.about_title);
            bar.setDisplayHomeAsUpEnabled(true); // back arrow
        }

        TextView tvUrl = findViewById(R.id.tvUrl);
        tvUrl.setText(MainActivity.APP_URL);
        // Clickable URL: opens the browser
        tvUrl.setOnClickListener(v ->
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(MainActivity.APP_URL))));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}