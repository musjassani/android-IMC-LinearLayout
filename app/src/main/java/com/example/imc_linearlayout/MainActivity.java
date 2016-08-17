package com.example.imc_linearlayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static int defaultColor ;
    private static float defautSize ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        defautSize = ((TextView) findViewById(R.id.hintTextView)).getTextSize();
        defaultColor = ((TextView) findViewById(R.id.hintTextView)).getTextColors().getDefaultColor();
        String log = "defautSize : " + defautSize + ", defaultColor : " + String.format("0x%08X", defaultColor);
        Log.i(this.getClass().getName(), log);
        //Evénement lors du click sur le bouton [calculer IMC]
        Button calculerIMCButton = (Button) findViewById(R.id.calculerIMCButton);
        calculerIMCButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    EditText poidsEditText = (EditText) findViewById(R.id.poidsEditText);
                    String poidsTxt = poidsEditText.getText().toString();
                    Double poids = Double.parseDouble(poidsTxt);
                    EditText tailleEditText = (EditText) findViewById(R.id.tailleEditText);
                    String tailleTxt = tailleEditText.getText().toString();
                    Double taille = Double.parseDouble(tailleTxt);

                    if (poids.equals(0) || taille.equals(0)) {
                        throw new NumberFormatException();
                    }
                    RadioButton centimetre = (RadioButton) findViewById(R.id.centimetreRadioButton);
                    boolean centimetreChecked = centimetre.isChecked();
                    if (centimetreChecked) {
                        taille /=100;
                    }
                    Double imc = poids / (taille * taille);
                    TextView hintTextView = (TextView) findViewById(R.id.hintTextView);
                    hintTextView.setTextColor(0xFF9DFF00);
                    hintTextView.setTextSize(20);
                    hintTextView.setText(imc.toString());
                } catch (NumberFormatException e) {
                    String text = getResources().getString(R.string.formatNonValide);
                    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    String text = e.getMessage();
                    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
                }
            }
        });

        //Evénement lors du click sur le bouton [RAZ]
        Button razButton = (Button) findViewById(R.id.razButton);
        razButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText poidsEditText = (EditText) findViewById(R.id.poidsEditText);
                poidsEditText.setText("");
                EditText tailleEditText = (EditText) findViewById(R.id.tailleEditText);
                tailleEditText.setText("");
                RadioButton centimetre = (RadioButton) findViewById(R.id.centimetreRadioButton);
                centimetre.setChecked(true);
                TextView hintTextView = (TextView) findViewById(R.id.hintTextView);
                String hintTxt = getResources().getString(R.string.hintTxt);
                hintTextView.setText(hintTxt);
                //hintTextView.setTypeface(Typeface.DEFAULT);
                hintTextView.setTextColor(defaultColor);
                hintTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, defautSize);
            }
        });
    }
}
