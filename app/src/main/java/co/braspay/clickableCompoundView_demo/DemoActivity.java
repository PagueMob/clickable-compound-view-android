package co.braspay.clickableCompoundView_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import co.braspay.clickableCompoundView.OnLeftCompoundClickListener;
import co.braspay.clickableCompoundView.OnRightCompoundClickListener;
import co.braspay.clickableCompoundView.TextView;

public class DemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        ((TextView) findViewById(R.id.text)).setOnCompoundClickListener(
                new OnRightCompoundClickListener() {
                    @Override
                    public void onRightCompoundClick() {
                        Toast.makeText(DemoActivity.this, "Right", Toast.LENGTH_SHORT).show();
                    }
                },
                new OnLeftCompoundClickListener() {
                    @Override
                    public void onLeftCompoundClick() {
                        Toast.makeText(DemoActivity.this, "Left", Toast.LENGTH_SHORT).show();
                    }
                }
        );


        ((TextView) findViewById(R.id.edit)).setOnCompoundClickListener(
                new OnRightCompoundClickListener() {
                    @Override
                    public void onRightCompoundClick() {
                        Toast.makeText(DemoActivity.this, "Righ 1t", Toast.LENGTH_SHORT).show();
                    }
                },
                new OnLeftCompoundClickListener() {
                    @Override
                    public void onLeftCompoundClick() {
                        Toast.makeText(DemoActivity.this, "Left 1", Toast.LENGTH_SHORT).show();
                        ((TextView) findViewById(R.id.edit)).getItemView().setError("Error test");
                    }
                }
        );
    }
}
