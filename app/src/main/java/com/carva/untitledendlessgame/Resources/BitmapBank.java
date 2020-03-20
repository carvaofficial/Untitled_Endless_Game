package com.carva.untitledendlessgame.Resources;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.carva.untitledendlessgame.R;
//TODO mejorar estructura dél código de la imágenes de las cajas

/**
 * Banco de imágenes utilizadas en el juego.
 */
public class BitmapBank {
    /**
     * imagen de fondo del juego.
     */
    private Bitmap background;
    /**
     * imagen de la carretera.
     */
    private Bitmap highway;
    /**
     * imágenes del personaje.
     */
    private Bitmap[] character;
    /**
     * imagen de la caja superior.
     */
    private Bitmap boxTop;
    /**
     * imagen de la caja inferior.
     */
    private Bitmap boxBottom;
    /**
     * imagen de la caja izquierda.
     */
    private Bitmap boxLeft;
    /**
     * imagen de la caja derecha.
     */
    private Bitmap boxRight;
    /**
     * imagen de la caja blanca superior.
     */
    private Bitmap whiteBoxTop;
    /**
     * imagen de la caja blanca inferior.
     */
    private Bitmap whiteBoxBottom;
    /**
     * imagen de la caja blanca izquierda.
     */
    private Bitmap whiteBoxLeft;
    /**
     * imagen de la caja blanca derecha.
     */
    private Bitmap whiteBoxRight;
    /**
     * Ancho de la superficie de dibujo.
     */
    private int screenWidth;
    /**
     * Alto de la superficie de dibujo.
     */
    private int screenHeight;
    /**
     * Recurso de imagen de fondo.
     */
    private int img;

    /**
     * Se establece el fondo del juego según el ajuste establecido en ajustes (fondo de dia/fondo de noche).
     * Se asignan las imágenes de cada recurso y se gestiona el tamaño mediante el rescalado de las mismas.
     *
     * @param resource     recursos de la aplicación.
     * @param screenWidth  ancho de la superficie de dibujo.
     * @param screenHeight alto de la superficie de dibujo.
     */
    public BitmapBank(Resources resource, int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        if (Tools.theme1) {
            img = R.drawable.background_day;
        }
        if (Tools.theme2) {
            img = R.drawable.background_night;
        }

        background = AppConstants.SVTools.scaleHeight(img, screenHeight);
        highway = AppConstants.SVTools.scaleHeight(R.drawable.highway, screenHeight);

        character = new Bitmap[6];
        for (int i = 0; i < character.length; i++) {
            character[i] = BitmapFactory.decodeResource(resource, R.drawable.character);
            character[i] = AppConstants.SVTools.scale(character[i], character[i].getWidth() / 5,
                    character[i].getHeight() / 5);
        }

        //Inicialización de cajas:
        //Cajas marrones
        boxTop = BitmapFactory.decodeResource(resource, R.drawable.box_b_top);
        boxBottom = BitmapFactory.decodeResource(resource, R.drawable.box_b_bottom);

        //Cajas blancas
        whiteBoxTop = BitmapFactory.decodeResource(resource, R.drawable.box_w_top);
        whiteBoxBottom = BitmapFactory.decodeResource(resource, R.drawable.box_w_bottom);

        //Reescalado de cajas
        boxTop = AppConstants.SVTools.scale(boxTop, boxTop.getWidth() / 4,
                boxTop.getHeight() / 4);
        boxBottom = AppConstants.SVTools.scale(boxBottom, boxBottom.getWidth() / 4,
                boxBottom.getHeight() / 4);
        whiteBoxTop = AppConstants.SVTools.scale(whiteBoxTop, whiteBoxTop.getWidth() / 4,
                whiteBoxTop.getHeight() / 4);
        whiteBoxBottom = AppConstants.SVTools.scale(whiteBoxBottom, whiteBoxBottom.getWidth() / 4,
                whiteBoxBottom.getHeight() / 4);
    }

    /**
     * @return imagen de fondo del juego ({@link #background}).
     */
    public Bitmap getBackground() {
        return background;
    }

    /**
     * @param background establece una nueva imagen de fondo.
     */
    public void setBackground(Bitmap background) {
        this.background = background;
    }

    /**
     * @return ancho de la imagen de fondo del juego ({@link #background}).
     */
    public int getBackgroundWidth() {
        return background.getWidth();
    }

    /**
     * @return alto de la imagen de fondo del juego ({@link #background}).
     */
    public int getBackgroundHeight() {
        return background.getHeight();
    }

    /**
     * @return imagen de la carretera ({@link #highway}).
     */
    public Bitmap getHighway() {
        return highway;
    }

    /**
     * @param highway establece una nueva imagen de la carretera.
     */
    public void setHighway(Bitmap highway) {
        this.highway = highway;
    }

    /**
     * @return ancho de la imagen de la carretera ({@link #highway}).
     */
    public int getHighwayWidth() {
        return highway.getWidth();
    }

    /**
     * @return alto de la imagen de la carretera ({@link #highway}).
     */
    public int getHighwayHeight() {
        return highway.getHeight();
    }

    /**
     * Escoge un fotograma del personaje ({@link #character}).
     *
     * @param frame fotograma del conjunto de imágenes del personaje.
     * @return fotograma especificado en el parámetro.
     */
    public Bitmap getCharacter(int frame) {
        return character[frame];
    }

    /**
     * @return ancho de las imágenes del personaje ({@link #character}).
     */
    public int getCharacterWidth() {
        return character[0].getWidth();
    }

    /**
     * @return alto de las imágenes del personaje ({@link #character}).
     */
    public int getCharacterHeight() {
        return character[0].getHeight();
    }

    /**
     * @return tamaño del conjunto de imágenes del personaje ({@link #character}).
     */
    public int getCharacterLength() {
        return character.length;
    }

    /**
     * @return imagen de la caja superior ({@link #boxTop}).
     */
    public Bitmap getBoxTop() {
        return boxTop;
    }

    /**
     * @return imagen de la caja inferior ({@link #boxBottom}).
     */
    public Bitmap getBoxBottom() {
        return boxBottom;
    }

    /**
     * @return imagen de la caja izquierda ({@link #boxLeft}).
     */
    public Bitmap getBoxLeft() {
        return boxLeft;
    }

    /**
     * @return imagen de la caja derecha ({@link #boxRight}).
     */
    public Bitmap getBoxRight() {
        return boxRight;
    }

    /**
     * @return imagen de la caja blanca superior ({@link #whiteBoxTop}).
     */
    public Bitmap getWhiteBoxTop() {
        return whiteBoxTop;
    }

    /**
     * @return imagen de la caja blanca inferior ({@link #whiteBoxBottom}).
     */
    public Bitmap getWhiteBoxBottom() {
        return whiteBoxBottom;
    }

    /**
     * @return imagen de la caja blanca izquierda ({@link #whiteBoxLeft}).
     */
    public Bitmap getWhiteBoxLeft() {
        return whiteBoxLeft;
    }

    /**
     * @return imagen de la caja blanca derecha ({@link #whiteBoxRight}).
     */
    public Bitmap getWhiteBoxRight() {
        return whiteBoxRight;
    }

    /**
     * @return ancho de la imagen de las cajas.
     */
    public int getBoxWidth() {
        return boxTop.getWidth();
    }

    /**
     * @return alto de la imagen de las cajas.
     */
    public int getBoxHeight() {
        return boxTop.getHeight();
    }

}
