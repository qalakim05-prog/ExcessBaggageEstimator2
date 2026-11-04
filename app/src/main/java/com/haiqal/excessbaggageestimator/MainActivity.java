package com.haiqal.excessbaggageestimator;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static final String APP_URL = "https://github.com/qalakim05-prog/ExcessBaggageEstimator2";

    private TextInputLayout layoutWeight, layoutRate;
    private TextInputEditText etWeight, etRate;
    private RadioButton rbBusiness;
    private TextView tvExcessWeight, tvExcessCharge, tvTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutWeight = findViewById(R.id.layoutWeight);
        layoutRate = findViewById(R.id.layoutRate);
        etWeight = findViewById(R.id.etWeight);
        etRate = findViewById(R.id.etRate);
        rbBusiness = findViewById(R.id.rbBusiness);
        tvExcessWeight = findViewById(R.id.tvExcessWeight);
        tvExcessCharge = findViewById(R.id.tvExcessCharge);
        tvTotal = findViewById(R.id.tvTotal);

        Button btnCalculate = findViewById(R.id.btnCalculate);
        Button btnReset = findViewById(R.id.btnReset);

        btnCalculate.setOnClickListener(v -> calculate());
        btnReset.setOnClickListener(v -> reset());
    }

    private void calculate() {
        layoutWeight.setError(null);
        layoutRate.setError(null);

        Double weight = parse(etWeight, layoutWeight, getString(R.string.error_weight));
        Double rate = parse(etRate, layoutRate, getString(R.string.error_rate));
        if (weight == null || rate == null) {
            return; // error messages already shown
        }

        boolean business = rbBusiness.isChecked();
        double excess = BaggageCalculator.excessWeight(weight, business);
        double charge = BaggageCalculator.excessCharge(excess, rate);
        double total = BaggageCalculator.totalPayable(charge);

        tvExcessWeight.setText(String.format(Locale.US, "%.2f kg", excess));
        tvExcessCharge.setText(String.format(Locale.US, "RM %.2f", charge));
        tvTotal.setText(String.format(Locale.US, "RM %.2f", total));
    }

    /** Returns the number typed, or null (and shows an error) if empty/invalid/negative. */
    private Double parse(TextInputEditText field, TextInputLayout layout, String errorMsg) {
        String text = field.getText() == null ? "" : field.getText().toString().trim();
        if (text.isEmpty()) {
            layout.setError(getString(R.string.error_empty));
            return null;
        }
        try {
            double value = Double.parseDouble(text);
            if (value < 0) {
                layout.setError(errorMsg);
                return null;
            }
            return value;
        } catch (NumberFormatException e) {
            layout.setError(errorMsg);
            return null;
        }
    }

    private void reset() {
        etWeight.setText("");
        etRate.setText("");
        layoutWeight.setError(null);
        layoutRate.setError(null);
        ((RadioButton) findViewById(R.id.rbEconomy)).setChecked(true);
        tvExcessWeight.setText(R.string.placeholder_kg);
        tvExcessCharge.setText(R.string.placeholder_rm);
        tvTotal.setText(R.string.placeholder_rm);
        etWeight.requestFocus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_share) {
            Intent send = new Intent(Intent.ACTION_SEND);
            send.setType("text/plain");
            send.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
            send.putExtra(Intent.EXTRA_TEXT,
                    getString(R.string.share_text) + "\n" + APP_URL);
            startActivity(Intent.createChooser(send, getString(R.string.share_via)));
            return true;
        } else if (id == R.id.action_about) {
            startActivity(new Intent(this, AboutActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}