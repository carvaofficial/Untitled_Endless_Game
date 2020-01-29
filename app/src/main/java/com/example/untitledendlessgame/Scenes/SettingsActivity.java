package com.example.untitledendlessgame.Scenes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.untitledendlessgame.*;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
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
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import static com.example.untitledendlessgame.Utilities.editor;
import static com.example.untitledendlessgame.Utilities.preferences;

//TODO pendiente hacer clicables los ImageView, crear sus apartados en SharedPreferences e insertar
// sus respectivas imágenes
public class SettingsActivity extends AppCompatActivity {
    View decorationView;
    Intent intent;
    Button btnBack;
    ImageButton btnPlayGames;
    Switch swMusic, swEffects, swVibration, swGyroscope, swThemeAuto;
    Spinner spinnerLang;
    ImageView theme1, theme2;
    Utilities util;
    Typeface font;
    Locale language;
    int langSelected;

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

        //Inicialización componentes
        btnBack = findViewById(R.id.btnBack);
        btnPlayGames = findViewById(R.id.btnPlayGames);
        swMusic = findViewById(R.id.swMusic);
        swEffects = findViewById(R.id.swEffects);
        swVibration = findViewById(R.id.swVibration);
        swGyroscope = findViewById(R.id.swGyroscope);
        swThemeAuto = findViewById(R.id.swThemeAuto);
        theme1 = findViewById(R.id.theme1);
        theme2 = findViewById(R.id.theme2);
        typefacedSpinner();

        //Propiedades componentes
        swMusic.setChecked(preferences.getBoolean("Music", true));
        swEffects.setChecked(preferences.getBoolean("Effects", true));
        swVibration.setChecked(preferences.getBoolean("Vibration", true));
        swGyroscope.setChecked(preferences.getBoolean("Gyroscope", false));
        swThemeAuto.setChecked(preferences.getBoolean("ThemeAuto", false));
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
                noService.setTitle(getString(R.string.service_not_avaliable));
                noService.setMessage(getString(R.string.gservice_not_avaliable));
                noService.setNeutralButton(getString(R.string.accept), null);
                noService.show();
            }
        });
        spinnerLang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        language = new Locale("es", "ES");
                        break;
                    case 1:
                        language = new Locale("en", "EN");
                        break;
                    case 2:
                        language = new Locale("fr", "FR");
                        break;
                }
                langSelected = position;
                Locale.setDefault(language);
                Configuration config = new Configuration();
                config.setLocale(language);
                getBaseContext().getResources().updateConfiguration(config, getBaseContext().
                        getResources().getDisplayMetrics());
                //TODO preguntar a Javi como refrescar la actividad después del cambio de idioma.
                // la función de abajo refresca, pero se ejecuta constantemente.
//                recreate();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        swMusic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    util.gameMusic.start();
                } else {
                    if (util.gameMusic.isPlaying()) util.gameMusic.stop();
                }
            }
        });
        swEffects.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
        swVibration.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
        swGyroscope.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
        swThemeAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swThemeAuto.setChecked(false);
                Toast.makeText(SettingsActivity.this, getString(R.string.theme_auto_disabled), Toast.LENGTH_SHORT).show();
            }
        });
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

    public void typefacedSpinner() {
        spinnerLang = findViewById(R.id.spinnerLang);
        final String[] languages = getResources().getStringArray(R.array.languages);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, languages) {
            public View getView(int position, View convertView, android.view.ViewGroup parent) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    font = getResources().getFont(R.font.comfortaa_regular);
                } else {
                    font = Typeface.createFromAsset(getAssets(), "fonts/comfortaa-regular.ttf");
                }

                TextView v = (TextView) super.getView(position, convertView, parent);
                v.setTypeface(font);
                v.setTextColor(Color.WHITE);
                v.setTextSize(18);
                return v;
            }

            public View getDropDownView(int position, View convertView, android.view.ViewGroup parent) {
                TextView v = (TextView) super.getView(position, convertView, parent);
                v.setTypeface(font);
                v.setTextColor(Color.WHITE);
                v.setTextSize(18);
                return v;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLang.setAdapter(adapter);
    }
}
