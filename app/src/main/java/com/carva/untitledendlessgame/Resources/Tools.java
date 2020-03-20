package com.carva.untitledendlessgame.Resources;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Point;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.carva.untitledendlessgame.R;

import java.util.Locale;

import jonathanfinerty.once.Once;

/**
 * Conjunto de herramientas del sistema: harware del dispositivo, propiedades de la pantalla del dispositivo,
 * persistencia de datos, propiedades del juego, creador de <code>Intent</code>, etc.
 *
 * @author carva
 * @version 1.0
 */
public class Tools {
    //Metrics and Display tools
    /**
     * The interface that apps use to talk to the window manager.
     *
     * @see WindowManager
     */
    public static WindowManager windowManager;
    /**
     * Gestor del área de pantalla de la aplicación y del área de la pantalla real.
     *
     * @see Display
     */
    public static Display display;
    /**
     * Describe el tamaño de la pantalla, densidad, y escalado de fuente.
     *
     * @see DisplayMetrics
     */
    public static DisplayMetrics metrics;
    /**
     * Coordenadas del tamaño de la pantalla del dispositivo.
     */
    public static Point screenSize;
    /**
     * Ancho y altura de la pantalla del dispositivo.
     */
    public static int SCREEN_WIDTH, SCREEN_HEIGHT;

    //Hardware tools
    /**
     * @see Vibrator
     */
    public static Vibrator vibrator;
    /**
     * Gestor del volumen de audio del dispositivo.
     *
     * @see AudioManager
     */
    public static AudioManager audioManager;
    /**
     * Reproductor de audio.
     *
     * @see MediaPlayer
     */
    public static MediaPlayer mediaPlayer;
    /**
     * Reproductor de efectos de sonido del menú principal.
     *
     * @see SoundPool
     */
    public static SoundPool menuEffects;

    //Shared Preferences
    /**
     * Preferencias del menú de ajustes.
     *
     * @see SharedPreferences
     */
    public static SharedPreferences settings;
    /**
     * Valores de los marcadores del juego.
     *
     * @see SharedPreferences
     */
    public static SharedPreferences markers;
    /**
     * Editor de preferencias para modificar valores.
     *
     * @see SharedPreferences.Editor
     */
    public static SharedPreferences.Editor editor;
    //Variables for SharedPreferences
    /**
     * Comprueban si en el menú de ajustes están activados los diferentes valores: música, efectos de sonido,
     * vibración, giroscopio, fondo de dia, fondo de noche, fondo automático.
     */
    public static boolean music, effects, vibration, gyroscope, theme1, theme2, themeAuto;
    /**
     * Indica el idioma establecido dentro del juego.
     */
    public static int language;
    /**
     * Idioma por defecto en caso de que el idioma por defecto del sistema no se encuentre en la lista de idiomas.
     */
    private static int defaultLanguage = 1;
    /**
     * Lista de idiomas disponibles en el juego.
     */
    public static Locale[] languages = {new Locale("es", "ES"), new Locale("en", "EN"),
            new Locale("fr", "FR")};
    /**
     * Valores del juego utilizados en los marcadores.
     */
    public static int totalJumps, maxJumpsInAMatch, totalCrossedBoxes, maxCrossedBoxes, totalCrashedBoxes, bestTimeInSeconds;
    /**
     * Recursos de audio para reproducir como música en el juego.
     */
    public static int MENU_MUSIC = R.raw.main_music, GAME_MUSIC = R.raw.game_music;
    /**
     * <code>Intent</code> general, para cambiar de actividades.
     */
    public static Intent intent;
    /**
     * Tags para {@link Once}.
     *
     * @see Once
     */
    public static final String DISPLAY = "Display", TIMER = "Timer";

    /**
     * Inicialización de las medidas de la pantalla del dispositivo.
     *
     * @param context contexto de la aplicación
     * @see Context
     */
    public static void initializeMetrics(Context context) {
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager != null ? windowManager.getDefaultDisplay() : null;
        screenSize = new Point();
        display.getSize(Tools.screenSize);
        metrics = new DisplayMetrics();
        display.getRealMetrics(metrics);
    }

    /**
     * Muestra información detallada acerca de la pantalla del dispositivo.
     */
    public static void getScreenInfo() {
        Log.i(DISPLAY, "Nombre: " + display.getName());
        Log.i(DISPLAY, String.format("Width: %d\nHeight: %d", screenSize.x, screenSize.y));
        Log.i(DISPLAY, String.format("Width (px): %d\nHeight (px): %d", metrics.widthPixels, metrics.heightPixels));
        Log.i(DISPLAY, "Density: " + metrics.densityDpi);
        Log.i(DISPLAY, String.format("DPI X: %f\nDPI Y: %f", metrics.xdpi, metrics.ydpi));
        //Deprecated
        Log.i(DISPLAY, String.format("Screen Width: %d\nScreen Height: %d", display.getWidth(), display.getHeight()));
    }

    /**
     * Establece valores por defecto para el menú de ajustes.
     *
     * @param context contexto de la aplicación
     * @see Context
     */
    public static void defaultSettings(Context context) {
        settings = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putBoolean("Music", true);
        editor.putBoolean("Effects", true);
        editor.putBoolean("Vibration", true);
        editor.putBoolean("Gyroscope", false);
        editor.putBoolean("ThemeAuto", false);
        editor.putBoolean("Theme1", true);
        editor.putBoolean("Theme2", false);

        for (int i = 0; i < languages.length; i++) {
            if (languages[i].equals(Locale.getDefault())) {
                defaultLanguage = i;
            }
        }
        editor.putInt("Language", defaultLanguage);
        editor.apply();
    }

    /**
     * Establece los valores cuando se modifican en el menú de ajustes.
     *
     * @param context contexto de la aplicación
     * @see Context
     */
    public static void establishSettings(Context context) {
        settings = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        music = settings.getBoolean("Music", true);
        effects = settings.getBoolean("Effects", true);
        vibration = settings.getBoolean("Vibration", true);
        gyroscope = settings.getBoolean("Gyroscope", false);
        themeAuto = settings.getBoolean("ThemeAuto", false);
        theme1 = settings.getBoolean("Theme1", true);
        theme2 = settings.getBoolean("Theme2", false);
        language = settings.getInt("Language", defaultLanguage);
    }

    /**
     * Establece valores por defecto para los marcadores.
     *
     * @param context contexto de la aplicación
     * @see Context
     */
    public static void defaultMarkers(Context context) {
        markers = context.getSharedPreferences("Markers", Context.MODE_PRIVATE);
        editor = markers.edit();
        editor.putInt("totalJumps", 0);
        editor.putInt("maxJumpsInAMatch", 0);
        editor.putInt("totalCrossedBoxes", 0);
        editor.putInt("maxCrossedBoxes", 0);
        editor.putInt("totalCrashedBoxes", 0);
        editor.putInt("bestTimeInSeconds", 0);
        editor.apply();
    }

    /**
     * Establece los valores para modificar los marcadores cuando se juega una partida.
     *
     * @param context contexto de la aplicación
     * @see Context
     */
    public static void establishMarkers(Context context) {
        markers = context.getSharedPreferences("Markers", Context.MODE_PRIVATE);
        totalJumps = markers.getInt("totalJumps", 0);
        maxJumpsInAMatch = markers.getInt("maxJumpsInAMatch", 0);
        totalCrossedBoxes = markers.getInt("totalCrossedBoxes", 0);
        maxCrossedBoxes = markers.getInt("maxCrossedBoxes", 0);
        totalCrashedBoxes = markers.getInt("totalCrashedBoxes", 0);
        bestTimeInSeconds = markers.getInt("bestTimeInSeconds", 0);
    }

    /**
     * Inicialización del reproductor de audio y del reproductor de efectos de sonido.
     * También se inicializa el vibrador del dispositivo.
     *
     * @param context contexto de la aplicación.
     * @param music   recurso de audio.
     * @param loop    repetición en bucle del audio.
     */
    public static void initializeHardware(Context context, int music, boolean loop) {
        //Inicialización música y efectos
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        float volume = audioManager != null ? audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) : 0;
        mediaPlayer = MediaPlayer.create(context, music);
        mediaPlayer.setVolume(volume, volume);
        mediaPlayer.setLooping(loop);

        SoundPool.Builder builder = new SoundPool.Builder();
        builder.setAudioAttributes(new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build());
        builder.setMaxStreams(10);
        menuEffects = builder.build();

        //Inicialización vibración
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    /**
     * Si el dispositivo tiene vibrador, vibra durante X milisegundos.
     *
     * @param miliseconds tiempo de vibración.
     */
    public static void vibrate(int miliseconds) {
        if (vibrator.hasVibrator()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                vibrator.vibrate(VibrationEffect.createOneShot(miliseconds, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                vibrator.vibrate(miliseconds);
            }
        }
    }

    /**
     * Modifica el diseño de la aplicación. Elimina la barra de navegación, la barra de notificaciones,
     * Toolbar por defecto de las actividades, pone el area de la aplicación en pantalla completa.
     *
     * @param context  contexto de la aplicación.
     * @param onCreate si se ejecuta en <code>AppCompatActivity.onCreate()</code>, elimina el título del Toolbar.
     */
    public static void manageDecorationView(Context context, boolean onCreate) {
        final int viewOptions = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        View decorationView = ((Activity) context).getWindow().getDecorView();
        decorationView.setSystemUiVisibility(viewOptions);
        //Desde API 16 oculta la Status Bar (barra de estado/notificaciones)
        ((Activity) context).getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (onCreate) {
            //En onResume no se coloca, salta excepción
            ((Activity) context).getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
    }

    /**
     * Crea un <code>Intent</code> personalizable para utilizar dependiendo de lo que se desee.
     *
     * @param context        contexto de la aplicación.
     * @param cl4ss          clase de la actividad que se desea lanzar.
     * @param animations     habilita/deshabilita animaciones de transición.
     * @param finishActivity finaliza la actividad actual.
     */
    public static void createIntent(Context context, Class<?> cl4ss, boolean animations, boolean finishActivity) {
        intent = new Intent(context, cl4ss);
        context.startActivity(intent);

        if (!animations) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            ((Activity) context).overridePendingTransition(0, 0);
        }
        if (finishActivity) {
            ((Activity) context).finish();
        }
    }

    /**
     * Desmarca una tarea como 'realizada' y la anota en la lista de 'to do' o por hacer.
     *
     * @param tag Tag de la tarea a rehacer.
     * @see Once
     */
    public static void reDoOnce(String tag) {
        Once.clearDone(tag);
        Once.toDo(tag);
    }
}
