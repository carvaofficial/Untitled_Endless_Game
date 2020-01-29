package com.example.untitledendlessgame.Scenes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.untitledendlessgame.*;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Locale;

import static com.example.untitledendlessgame.Utilities.editor;
import static com.example.untitledendlessgame.Utilities.preferences;

//TODO setOnChecked de los switches para ajustar todos los valores al momento (sonido. musica, etc.)
public class SettingsActivity extends AppCompatActivity {
    View decorationView;
    Intent intent;
    Button btnBack;
    ImageButton btnPlayGames;
    Switch swMusic, swEffects, swVibration, swGyroscope, swThemeAuto;
    Spinner spinnerLang;
    int langSelected;
    Utilities util;
    Typeface font;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //Configuración de decorado de la actividad
        decorationView = getWindow().getDecorView();
        decorationView.setSystemUiVisibility(Utilities.viewOptions);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        util = new Utilities(this);
        intent = new Intent();
        preferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        final String[] language = getResources().getStringArray(R.array.language);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, language);

        //Inicialización componentes
        btnBack = findViewById(R.id.btnBack);
        btnPlayGames = findViewById(R.id.btnPlayGames);
        swMusic = findViewById(R.id.swMusic);
        swEffects = findViewById(R.id.swEffects);
        swVibration = findViewById(R.id.swVibration);
        swGyroscope = findViewById(R.id.swGyroscope);
        swThemeAuto = findViewById(R.id.swThemeAuto);
        spinnerLang = findViewById(R.id.spinnerLang);

        //Propiedades componentes
        swMusic.setChecked(preferences.getBoolean("Music", true));
        swEffects.setChecked(preferences.getBoolean("Effects", true));
        swVibration.setChecked(preferences.getBoolean("Vibration", true));
        swGyroscope.setChecked(preferences.getBoolean("Gyroscope", false));
        swThemeAuto.setChecked(preferences.getBoolean("ThemeAuto", false));
        spinnerLang.setAdapter(adapter);
        spinner2meth();
        spinnerLang.setSelection(preferences.getInt("Language", 0));
        Log.i("Lang", "onCreate: " + Locale.getDefault().getDisplayLanguage());

        //Eventos componentes
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnPlayGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder noService = new AlertDialog.Builder(SettingsActivity.this);
                noService.setTitle("Servicio no disponible");
                noService.setMessage("Servicios de Google no disponible temporalmente");
                noService.setNeutralButton("Aceptar", null);
                noService.show();
            }
        });
        spinnerLang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                langSelected = position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    //TODO mejorar esta función del Spinner (permite personalizar la fuente)
    public void spinner2meth() {
        Spinner mySpinner = findViewById(R.id.spinnerLang);
        final String[] language = getResources().getStringArray(R.array.language);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, language) {
            public View getView(int position, View convertView, android.view.ViewGroup parent) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    font = getResources().getFont(R.font.comfortaa_bold);
                } else {
                    font = Typeface.createFromAsset(getAssets(), "fonts/comfortaa-regular.ttf");
                }

                TextView v = (TextView) super.getView(position, convertView, parent);
                v.setTypeface(font);
                v.setTextColor(Color.WHITE);
                v.setTextSize(20);
                return v;
            }

            public View getDropDownView(int position, View convertView, android.view.ViewGroup parent) {
                TextView v = (TextView) super.getView(position, convertView, parent);
                v.setTypeface(font);
                v.setTextColor(Color.WHITE);
                v.setTextSize(20);
                return v;
            }
        };
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(adapter1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        decorationView = getWindow().getDecorView();
        decorationView.setSystemUiVisibility(Utilities.viewOptions);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void onBackPressed() {
        editor = preferences.edit();
        editor.putBoolean("Music", swMusic.isChecked());
        editor.putBoolean("Effects", swEffects.isChecked());
        editor.putBoolean("Vibration", swVibration.isChecked());
        editor.putBoolean("Gyroscope", swGyroscope.isChecked());
        editor.putBoolean("ThemeAuto", swThemeAuto.isChecked());
        editor.putInt("Language", langSelected);
        editor.commit();
        finish();
        overridePendingTransition(0, 0);
    }
}
