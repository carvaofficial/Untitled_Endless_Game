package com.carva.untitledendlessgame.Scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.widget.Toast;

import com.carva.untitledendlessgame.R;
import com.carva.untitledendlessgame.Resources.Tools;

/**
 * Visualiza la escena Créditos.
 *
 * @author carva
 * @version 1.0
 */
public class CreditsScene extends Scene {
    /**
     * Listas de los textos a mostrar con las diferentes atribuciones.
     */
    private Text titles[], developers[], musicians[], based, graphics[], fonts[], soundeffects[], appreciations[];
    /**
     * Comprueba si se está pulsando la panatlla.
     */
    private boolean pressed;
    /**
     * Comprueba si la longitud de un <code>Text</code> ha cambiado.
     */
    private boolean lengthChanged;
    /**
     * Tamaños de separación de textos predeterminados.
     */
    private int generalX, generalY, shortSeparationY, normalSeparationY, longSeparationY;

    /**
     * Inicializa propiedades y herramientas, establece tamaños de texto según la orientación, e
     * iniciaiza los textos.
     *
     * @param sceneNumber  número de escena.
     * @param context      contexto de la aplicación.
     * @param screenWidth  ancho de la superficie del lienzo
     * @param screenHeight altura de la superficie del lienzo
     * @param orientation  orientación de la pantalla.
     */
    public CreditsScene(int sceneNumber, Context context, int screenWidth, int screenHeight, boolean orientation) {
        super(sceneNumber, context, screenWidth, screenHeight, orientation);
        pressed = false;
        lengthChanged = false;

        //Propiedades Paints:
        SVTools.pBold[0].setTextAlign(Paint.Align.CENTER);
        SVTools.pRegular[0].setTextAlign(Paint.Align.CENTER);

        //Gestión de tamaño de pinceles, posiciones generales de los texto y su separación según orientación
        generalX = screenWidth / 2;
        generalY = screenHeight;

        if (orientation) {
            shortSeparationY = (int) (0.035 * (double) screenHeight);
            normalSeparationY = (int) (0.05 * (double) screenHeight);
            longSeparationY = (int) (0.1 * (double) screenHeight);

            SVTools.pBold[0].setTextSize(screenWidth / 12);
            SVTools.pRegular[0].setTextSize(screenWidth / 18);
        } else {
            shortSeparationY = (int) (0.07 * (double) screenHeight);
            normalSeparationY = (int) (0.1 * (double) screenHeight);
            longSeparationY = (int) (0.25 * (double) screenHeight);

            SVTools.pBold[0].setTextSize(screenWidth / 24);
            SVTools.pRegular[0].setTextSize(screenWidth / 36);
        }

        titles = new Text[6];
        developers = new Text[2];
        musicians = new Text[2];
        graphics = new Text[5];
        soundeffects = new Text[4];
        fonts = new Text[2];
        appreciations = new Text[3];


        titles[0] = new Text(context.getString(R.string.game_dev_and_des), generalX, generalY, SVTools.pBold[0]);
        developers[0] = new Text(context.getString(R.string.dev_name), titles[0].getX(),
                titles[0].getY() + normalSeparationY, SVTools.pRegular[0]);

        titles[1] = new Text(context.getString(R.string.music), titles[0].getX(),
                developers[0].getY() + longSeparationY, SVTools.pBold[0]);
        musicians[0] = new Text(context.getString(R.string.kumbagua), titles[0].getX(),
                titles[1].getY() + normalSeparationY, SVTools.pRegular[0]);
        musicians[1] = new Text(context.getString(R.string.sin_project), titles[0].getX(),
                musicians[0].getY() + shortSeparationY, SVTools.pRegular[0]);

        titles[2] = new Text(context.getString(R.string.graphics), titles[0].getX(),
                musicians[1].getY() + longSeparationY, SVTools.pBold[0]);
        developers[1] = new Text(context.getString(R.string.dev_name), titles[0].getX(),
                titles[2].getY() + normalSeparationY, SVTools.pRegular[0]);
        based = new Text(context.getString(R.string.based_on), titles[0].getX(),
                developers[1].getY() + normalSeparationY, SVTools.pRegular[0]);
        graphics[0] = new Text(context.getString(R.string.surang), titles[0].getX(),
                based.getY() + shortSeparationY, SVTools.pRegular[0]);
        graphics[1] = new Text(context.getString(R.string.vectors_market), titles[0].getX(),
                graphics[0].getY() + shortSeparationY, SVTools.pRegular[0]);
        graphics[2] = new Text(context.getString(R.string.freepik), titles[0].getX(),
                graphics[1].getY() + shortSeparationY, SVTools.pRegular[0]);
        graphics[3] = new Text(context.getString(R.string.photo3idea_studio), titles[0].getX(),
                graphics[2].getY() + shortSeparationY, SVTools.pRegular[0]);
        graphics[4] = new Text(context.getString(R.string.good_ware), titles[0].getX(),
                graphics[3].getY() + shortSeparationY, SVTools.pRegular[0]);

        titles[3] = new Text(context.getString(R.string.sound_effects), titles[0].getX(),
                graphics[4].getY() + longSeparationY, SVTools.pBold[0]);
        soundeffects[0] = new Text(context.getString(R.string.mark_diangelo), titles[0].getX(),
                titles[3].getY() + normalSeparationY, SVTools.pRegular[0]);
        soundeffects[1] = new Text(context.getString(R.string.vladimir), titles[0].getX(),
                soundeffects[0].getY() + shortSeparationY, SVTools.pRegular[0]);
        soundeffects[2] = new Text(context.getString(R.string.grabskc), titles[0].getX(),
                soundeffects[1].getY() + shortSeparationY, SVTools.pRegular[0]);
        soundeffects[3] = new Text(context.getString(R.string.sergiy), titles[0].getX(),
                soundeffects[2].getY() + shortSeparationY, SVTools.pRegular[0]);

        titles[4] = new Text(context.getString(R.string.fonts), titles[0].getX(),
                soundeffects[3].getY() + longSeparationY, SVTools.pBold[0]);
        fonts[0] = new Text(context.getString(R.string.google_fonts), titles[0].getX(),
                titles[3].getY() + normalSeparationY, SVTools.pRegular[0]);
        fonts[1] = new Text(context.getString(R.string.font_awesome), titles[0].getX(),
                fonts[0].getY() + shortSeparationY, SVTools.pRegular[0]);

        titles[5] = new Text(context.getString(R.string.appreciations), titles[0].getX(),
                fonts[1].getY() + longSeparationY, SVTools.pBold[0]);
        appreciations[0] = new Text(context.getString(R.string.kumbagua), titles[0].getX(),
                titles[5].getY() + normalSeparationY, SVTools.pRegular[0]);
        appreciations[1] = new Text(context.getString(R.string.paula), titles[0].getX(),
                appreciations[0].getY() + shortSeparationY, SVTools.pRegular[0]);
        appreciations[2] = new Text(context.getString(R.string.sergiy), titles[0].getX(),
                appreciations[1].getY() + shortSeparationY, SVTools.pRegular[0]);
    }

    /**
     * Establece el eje Y de todos los textos. Se utiliza cuando se produce un cambio en el eje {@link #generalY}.
     */
    private void setAllY() {
        if (lengthChanged) {
            titles[0].setY((int) ((float) titles[0].getY() + SVTools.pBold[0].getTextSize()));
            lengthChanged = false;
        } else {
            titles[0].setY(generalY);
        }
        developers[0].setY(titles[0].getY() + normalSeparationY);

        titles[1].setY(developers[0].getY() + longSeparationY);
        musicians[0].setY(titles[1].getY() + normalSeparationY);
        musicians[1].setY(musicians[0].getY() + shortSeparationY);

        titles[2].setY(musicians[1].getY() + longSeparationY);
        developers[1].setY(titles[2].getY() + normalSeparationY);
        based.setY(developers[1].getY() + normalSeparationY);
        graphics[0].setY(based.getY() + shortSeparationY);
        graphics[1].setY(graphics[0].getY() + shortSeparationY);
        graphics[2].setY(graphics[1].getY() + shortSeparationY);
        graphics[3].setY(graphics[2].getY() + shortSeparationY);
        graphics[4].setY(graphics[3].getY() + shortSeparationY);

        titles[3].setY(graphics[4].getY() + longSeparationY);
        soundeffects[0].setY(titles[3].getY() + normalSeparationY);
        soundeffects[1].setY(soundeffects[0].getY() + shortSeparationY);
        soundeffects[2].setY(soundeffects[1].getY() + shortSeparationY);
        soundeffects[3].setY(soundeffects[2].getY() + shortSeparationY);

        titles[4].setY(soundeffects[3].getY() + longSeparationY);
        fonts[0].setY(titles[4].getY() + normalSeparationY);
        fonts[1].setY(fonts[0].getY() + shortSeparationY);

        titles[5].setY(fonts[1].getY() + longSeparationY);
        appreciations[0].setY(titles[5].getY() + normalSeparationY);
        appreciations[1].setY(appreciations[0].getY() + shortSeparationY);
        appreciations[2].setY(appreciations[1].getY() + shortSeparationY);
    }

    /**
     * Se dibujan todos los textos. Los textos se van desplazando hacia arriba lentamente. Si se pulsa
     * la pantalla, la velocidad de subida aumenta. Al terminar de mostrarse los textos, se avisa al usuario
     * de que han finalizado y se le pide que pulse la pantalla para volver al menú.
     *
     * @param canvas lienzo de dibujo.
     */
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (titles[0].getLength() > screenWidth) {
            canvas.drawText(titles[0].getText().substring(0, titles[0].getText().indexOf(' ')), titles[0].getX(),
                    titles[0].getY(), SVTools.pBold[0]);
            canvas.drawText(titles[0].getText().substring(titles[0].getText().indexOf(' ')), titles[0].getX(),
                    titles[0].getY() + SVTools.pBold[0].getTextSize(), SVTools.pBold[0]);
            lengthChanged = true;
            setAllY();
        } else {
            canvas.drawText(titles[0].getText(), titles[0].getX(), titles[0].getY(), SVTools.pBold[0]);
        }
        canvas.drawText(developers[0].getText(), developers[0].getX(), developers[0].getY(), SVTools.pRegular[0]);

        canvas.drawText(titles[1].getText(), titles[1].getX(), titles[1].getY(), SVTools.pBold[0]);
        canvas.drawText(musicians[0].getText(), musicians[0].getX(), musicians[0].getY(), SVTools.pRegular[0]);
        canvas.drawText(musicians[1].getText(), musicians[1].getX(), musicians[1].getY(), SVTools.pRegular[0]);

        canvas.drawText(titles[2].getText(), titles[2].getX(), titles[2].getY(), SVTools.pBold[0]);
        canvas.drawText(developers[1].getText(), developers[1].getX(), developers[1].getY(), SVTools.pRegular[0]);
        canvas.drawText(based.getText(), based.getX(), based.getY(), SVTools.pRegular[0]);
        canvas.drawText(graphics[0].getText(), graphics[0].getX(), graphics[0].getY(), SVTools.pRegular[0]);
        canvas.drawText(graphics[1].getText(), graphics[1].getX(), graphics[1].getY(), SVTools.pRegular[0]);
        canvas.drawText(graphics[2].getText(), graphics[2].getX(), graphics[2].getY(), SVTools.pRegular[0]);
        canvas.drawText(graphics[3].getText(), graphics[3].getX(), graphics[3].getY(), SVTools.pRegular[0]);
        canvas.drawText(graphics[4].getText(), graphics[4].getX(), graphics[4].getY(), SVTools.pRegular[0]);

        canvas.drawText(titles[3].getText(), titles[3].getX(), titles[3].getY(), SVTools.pBold[0]);
        canvas.drawText(soundeffects[0].getText(), soundeffects[0].getX(), soundeffects[0].getY(), SVTools.pRegular[0]);
        canvas.drawText(soundeffects[1].getText(), soundeffects[1].getX(), soundeffects[1].getY(), SVTools.pRegular[0]);
        canvas.drawText(soundeffects[2].getText(), soundeffects[2].getX(), soundeffects[2].getY(), SVTools.pRegular[0]);
        canvas.drawText(soundeffects[3].getText(), soundeffects[3].getX(), soundeffects[3].getY(), SVTools.pRegular[0]);

        canvas.drawText(titles[4].getText(), titles[4].getX(), titles[4].getY(), SVTools.pBold[0]);
        canvas.drawText(fonts[0].getText(), fonts[0].getX(), fonts[0].getY(), SVTools.pRegular[0]);
        canvas.drawText(fonts[1].getText(), fonts[1].getX(), fonts[1].getY(), SVTools.pRegular[0]);

        canvas.drawText(titles[5].getText(), titles[5].getX(), titles[5].getY(), SVTools.pBold[0]);
        canvas.drawText(appreciations[0].getText(), appreciations[0].getX(), appreciations[0].getY(), SVTools.pRegular[0]);
        canvas.drawText(appreciations[1].getText(), appreciations[1].getX(), appreciations[1].getY(), SVTools.pRegular[0]);
        canvas.drawText(appreciations[2].getText(), appreciations[2].getX(), appreciations[2].getY(), SVTools.pRegular[0]);

        if (pressed) {
            generalY -= 5;
        } else {
            generalY--;
        }
        setAllY();

        if (appreciations[2].getY() == 0) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, context.getString(R.string.credits_msg), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    /**
     * Se detecta el tipo de pulsación realizada sobre la pantalla. Si se pulsa la pantalla, el texto
     * comenzará a subir más rápido. Cuando el útimo texto desaparezca de la pantalla, si se pulsa
     * la pantalla se volverá al menú principal. También se producirá una vibración háptica si
     * la opción está activa en ajustes.
     *
     * @param event detector de movimiento en pantalla.
     * @return true cada vez que se detecte una puksación.
     */
    @Override
    public int onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                pressed = true;
                break;
            case MotionEvent.ACTION_UP:
                pressed = false;
                if (appreciations[2].getY() <= 0) {
                    if (Tools.vibration) Tools.vibrate(10);
                    return Scene.MENU;
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * Texto para mostrar en la escena Créditos.
     *
     * @author carva
     * @version 1.0
     */
    static class Text {
        /**
         * Cadena de texto.
         */
        private String text;
        /**
         * Coordenada de eje X del texto.
         */
        private int X;
        /**
         * Coordenada de eje Y del texto.
         */
        private int Y;
        /**
         * Longitud del texto.
         */
        private int length;
        /**
         * Rectágulo que mide las dimensiones del texto.
         */
        private Rect rLength;

        /**
         * Inicialización de propiedades y se miden las dimensiones del texto.
         *
         * @param text cadena de texto.
         * @param x    coordenada de eje X del texto.
         * @param y    coordenada de eje Y del texto.
         * @param p    pincel de referencia para medir las dimensiones del texto.
         */
        public Text(String text, int x, int y, Paint p) {
            this.text = text;
            X = x;
            Y = y;

            rLength = new Rect();
            p.getTextBounds(this.text, 0, this.text.length(), rLength);
            length = rLength.right;
        }

        /**
         * @return cadena de texto.
         */
        public String getText() {
            return text;
        }

        /**
         * @param text establece una nueva  cadena de texto.
         */
        public void setText(String text) {
            this.text = text;
        }

        /**
         * @return coordenada de eje X del texto.
         */
        public int getX() {
            return X;
        }

        /**
         * @param x establece una nueva coordenada de eje X del texto.
         */
        public void setX(int x) {
            X = x;
        }

        /**
         * @return coordenada de eje Y del texto.
         */
        public int getY() {
            return Y;
        }

        /**
         * @param y establece una nueva coordenada de eje Y del texto.
         */
        public void setY(int y) {
            Y = y;
        }

        /**
         * @return longitud del texto.
         */
        public int getLength() {
            return length;
        }
    }
}
