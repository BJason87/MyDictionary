package com.example.bonaventurajason.mydictionary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultActivity extends AppCompatActivity {

    @BindView(R.id.kata_extras)
    TextView get_kata_extras;
    @BindView(R.id.keterangan_extras) TextView get_keterangan_extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        ButterKnife.bind(this);

        get_kata_extras.setText(getIntent().getExtras().getString("kata").toUpperCase());
        get_keterangan_extras.setText(getIntent().getExtras().getString("keterangan"));
    }
}
