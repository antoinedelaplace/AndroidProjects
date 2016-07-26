package com.delaplace.antoine.rebounce;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {
    Button bNouvellePartie;
    RadioGroup rg;
    RadioButton rbFacile;
    RadioButton rbNormal;
    RadioButton rbDifficile;
    Button bReprendre;
    Button bScores;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bNouvellePartie = (Button)findViewById(R.id.buttonNouvellePartie);
        rg = (RadioGroup)findViewById(R.id.radioGroupDifficulte);
        rbFacile = (RadioButton)findViewById(R.id.radioButtonFacile);
        rbNormal = (RadioButton)findViewById(R.id.radioButtonNormal);
        rbDifficile = (RadioButton)findViewById(R.id.radioButtonDifficile);
        bReprendre = (Button)findViewById(R.id.buttonReprendre);

        bNouvellePartie.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent t = new Intent(MainActivity.this, GameActivity.class);
                startActivity(t);
            }
        });
    }
}
