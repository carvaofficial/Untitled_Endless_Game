package com.example.untitledendlessgame.Scenes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.untitledendlessgame.*;
import com.example.untitledendlessgame.Resources.Tools;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import static com.example.untitledendlessgame.MenuSurfaceView.*;
import static com.example.untitledendlessgame.Resources.Tools.*;

public class SettingsActivity extends AppCompatActivity {
    Button btnBack;
    ImageButton theme1, theme2, btnPlayGames;
    Switch swMusic, swEffects, swVibration, swGyroscope, swThemeAuto;
    Spinner spinnerLang;
    Typeface font;
    static Locale[] languages = {new Locale("es", "ES"), new Locale("en", "EN"),
            new Locale("fr", "FR")};
    private int langSelected;
    private boolean theme1Activated, theme2Activated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        //Configuración de decorado de la actividad
        Tools.manageDecorationView(this, false);

        settings = getSharedPreferences("Settings", Context.MODE_PRIVATE);

        //Inicialización componentes
        btnBack = findViewById(R.id.btnBackSettings);
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
        swMusic.setChecked(settings.getBoolean("Music", true));
        swEffects.setChecked(settings.getBoolean("Effects", true));
        swVibration.setChecked(settings.getBoolean("Vibration", true));
        swGyroscope.setChecked(settings.getBoolean("Gyroscope", false));
        swThemeAuto.setChecked(settings.getBoolean("ThemeAuto", false));
        theme1Activated = settings.getBoolean("Theme1", true);
        theme2Activated = settings.getBoolean("Theme2", false);
        spinnerLang.setSelection(settings.getInt("Language", 0));
        Log.i("Lang", "onCreate: " + Locale.getDefault().getDisplayLanguage());

        if (theme1Activated) {
            theme1.setImageResource(R.drawable.ic_sun_color);
        }
        if (theme2Activated) {
            theme2.setImageResource(R.drawable.ic_moon_color);
        }

        //Eventos componentes
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vibration) vibrate(10);
                onBackPressed();
            }
        });
        btnPlayGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vibration) vibrate(10);
                AlertDialog.Builder noService = new AlertDialog.Builder(SettingsActivity.this);
                noService.setTitle(getString(R.string.service_not_avaliable));
                noService.setMessage(getString(R.string.gservice_not_avaliable));
                noService.setNeutralButton(getString(R.string.accept), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (vibration) vibrate(10);
                    }
                });
                noService.show();
            }
        });
        spinnerLang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                langSelected = position;
                Configuration config = getResources().getConfiguration();
                config.setLocale(languages[position]);
                createConfigurationContext(config);
                getResources().updateConfiguration(config, metrics);
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
                music = isChecked;
                if (vibration) vibrate(10);
                if (isChecked) {
                    mediaPlayer.start();
                } else {
                    mediaPlayer.pause();
                }
            }
        });
        swEffects.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                effects = isChecked;
                if (vibration) vibrate(10);
            }
        });
        swVibration.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                vibration = isChecked;
                if (vibration) vibrate(10);
            }
        });
        swGyroscope.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (vibration) vibrate(10);
            }
        });
        swThemeAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swThemeAuto.setChecked(false);
                if (vibration) vibrate(10);
                Toast.makeText(SettingsActivity.this, getString(R.string.theme_auto_disabled), Toast.LENGTH_SHORT).show();
            }
        });
        theme1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                theme1Activated = true;
                theme2Activated = false;
                theme1.setImageResource(R.drawable.ic_sun_color);
                theme2.setImageResource(R.drawable.ic_moon_bw);
                if (vibration) vibrate(10);
            }
        });
        theme2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                theme2Activated = true;
                theme1Activated = false;
                theme2.setImageResource(R.drawable.ic_moon_color);
                theme1.setImageResource(R.drawable.ic_sun_bw);
                if (vibration) vibrate(10);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Tools.manageDecorationView(this, false);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

    @Override
    public void onBackPressed() {
        editor = settings.edit();
        editor.putBoolean("Music", swMusic.isChecked());
        editor.putBoolean("Effects", swEffects.isChecked());
        editor.putBoolean("Vibration", swVibration.isChecked());
        editor.putBoolean("Gyroscope", swGyroscope.isChecked());
        editor.putBoolean("ThemeAuto", swThemeAuto.isChecked());
        editor.putBoolean("Theme1", theme1Activated);
        editor.putBoolean("Theme2", theme2Activated);
        editor.putInt("Language", langSelected);
        editor.apply();
        Tools.establishSettings(this);
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

    //FIXME Revisar
    public void changeLanguage(String langCode) {
        Configuration conf = getResources().getConfiguration();
        conf.setLocale(new Locale(langCode.toLowerCase()));
        createConfigurationContext(conf);
        getResources().updateConfiguration(conf, metrics);
    }
}
