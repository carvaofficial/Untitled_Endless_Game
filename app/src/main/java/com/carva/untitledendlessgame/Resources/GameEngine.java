package com.carva.untitledendlessgame.Resources;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.CountDownTimer;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Motor gráfico: gestor de físicas y dibujo de recursos en el lienzo.
 *
 * @author carva
 * @version 1.0
 */
public class GameEngine {
    /**
     * Gestor de movimiento del fondo del juego.
     */
    private ScrollManager background;
    /**
     * Gestor de movimiento de la carretera del juego.
     */
    private ScrollManager highway;
    /**
     * Gestor del personaje.
     */
    public Character character;
    /**
     * Establece el funcionamiento de la jugabilidad del juego, es decir, detecta si se ha comenzado
     * a jugar.
     */
    private boolean gameRunning;
    /**
     * Establece el funcionamiento del juego (movimientos, dibujado del lienzo, etc.).
     */
    private boolean running;
    /**
     * Indica si se ha pasado por el hueco de entre las cajas.
     */
    private boolean crossed;
    /**
     * Indica si el personaje ha colisionado.
     */
    public boolean collision;
    /**
     * Conjunto de cajas del juego.
     */
    private ArrayList<Box> boxes;
    /**
     * Generador de números aleatorios.
     */
    private Random random;
    /**
     * Cuenta atrás para reducir el hueco de las cajas.
     */
    private CountDownTimer decreaseGapCountDown;
    /**
     * Temporizador de la partida.
     */
    public Timer timer;
    /**
     * Tarea de actualización del reloj del temporizador.
     */
    public TimerTask time;
    /**
     * Puntuación de la partida.
     */
    public int score;
    /**
     * Minutos del reloj.
     */
    public int minutes;
    /**
     * Segundos del reloj.
     */
    public int seconds;
    /**
     * Longitud del texto del temporizador.
     */
    public int timeLength;
    /**
     * Caja por la que el personaje está pasando.
     */
    private int scoringBox;
    /**
     * Cantidad a restar del hueco de las cajas.
     */
    private int gapDecrease;

    /**
     * Inicializa los gestores de movimiento del fondo y de la carretera, el gestor del personaje,
     * inicialización de propiedades del juego (booleanas, conjunto de cajas, generador de números aleatorios,
     * temporizador y tarea). También inicializa la colisión de la carretera.
     *
     * @param backgroundVelocity velocidad de movimiento del fondo del juego.
     */
    public GameEngine(int backgroundVelocity) {
        background = new ScrollManager(backgroundVelocity);
        highway = new ScrollManager(backgroundVelocity * 2);
        character = new Character(AppConstants.getBitmapBank().getCharacterLength() - 1);
        gameRunning = false;
        running = true;
        boxes = new ArrayList<>();
        random = new Random();

        //Inicialización colisión de la carretera
        highway.setCollision(new Rect(0, AppConstants.getBitmapBank().getHighwayHeight() -
                (AppConstants.getBitmapBank().getHighwayHeight() / 8),
                AppConstants.getBitmapBank().getHighwayWidth(), AppConstants.getBitmapBank().getHighwayHeight())
        );

        //Inicialización cajas
        for (int i = 0; i < AppConstants.numberOfBoxes; i++) {
            int boxX = Tools.SCREEN_WIDTH + i * AppConstants.distanceBetweenBoxes;
            int topBoxOffsetY = AppConstants.minBoxOffsetY + random.nextInt(AppConstants.maxBoxOffsetY
                    - AppConstants.minBoxOffsetY + 1);
            Box box = new Box(boxX, topBoxOffsetY);
            boxes.add(box);
        }

        //Inicialización marcador para puntuación
        score = 0;
        scoringBox = 0;
        decreaseGapCountDown = new CountDownTimer(300, 300) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                crossed = true;
                gapDecrease = (int) (0.05 * (double) AppConstants.actualGapBetweenTopAndBottomBoxes);
            }
        };

        //Timer de tiempo de juego
        Rect rTime = new Rect();
        minutes = 0;
        seconds = -1;
        String sTime = "00:00";
        AppConstants.SVTools.pRegular[0].getTextBounds(sTime, 0, sTime.length(), rTime);
        timeLength = rTime.right;
        initializeTimers();
    }

    /**
     * @return funcionamiento de la jugabilidad del juego ({@link #gameRunning}).
     */
    public boolean isGameRunning() {
        return gameRunning;
    }

    /**
     * @param gameRunning establece el funcionamiento de la jugabilidad del juego.
     */
    public void setGameRunning(boolean gameRunning) {
        this.gameRunning = gameRunning;
    }

    /**
     * @return funcionamiento del juego ({@link #running}).
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * @param running establece el funcionamiento del juego.
     */
    public void setRunning(boolean running) {
        this.running = running;
    }

    /**
     * Inicializa el temporizador y la tarea de actualización del reloj {@link #timer}, {@link #time},
     * {@link #minutes}, {@link #seconds}).
     */
    public void initializeTimers() {
        time = new TimerTask() {
            @Override
            public void run() {
                seconds++;
                if (seconds == 60) {
                    minutes++;
                    seconds = 0;
                }
            }
        };
        timer = new Timer();
    }

    /**
     * Pausa el funcionamiento de la jugabilidad del juego, y pausa el temporizador.
     */
    public void pauseGame() {
        setGameRunning(false);
        timer.cancel();
    }

    /**
     * Reanuda el funcionamiento de la jugabilidad del juego, y reanuda el temporizador.
     */
    public void resumeGame() {
        setGameRunning(true);
        initializeTimers();
        AppConstants.getGameEngine().timer.scheduleAtFixedRate(
                AppConstants.getGameEngine().time, 0, 1000);
    }

    //TODO implementar array de fondos

    /**
     * Dibuja y actualiza la posición del fondo del juego ({@link #background}).
     *
     * @param canvas lienzo de dibujo
     */
    public void updateAndDrawBackground(Canvas canvas) {
        //Dibujo de background
        if (isRunning()) {
            background.setX(background.getX() - background.getVelocity());
            if (background.getX() < -AppConstants.getBitmapBank().getBackgroundWidth()) {
                background.setX(0);
                //Aqui se cambia el fondo seleccionando uno aleatorio del array
            }
        }
        canvas.drawBitmap(AppConstants.getBitmapBank().getBackground(), background.getX(), background.getY(), null);
        if (background.getX() < -(AppConstants.getBitmapBank().getBackgroundWidth() - Tools.SCREEN_WIDTH)) {
            //Aqui se asigna el siguiente fondo a mostrar mientras se termina de mostrar el fondo anterior
            canvas.drawBitmap(AppConstants.getBitmapBank().getBackground(), background.getX() +
                    AppConstants.getBitmapBank().getBackgroundWidth(), background.getY(), null);
        }
    }

    /**
     * Dibuja la carretera del juego y su colisión, y actualiza su posición en el lienzo ({@link #highway}).
     *
     * @param canvas lienzo de dibujo
     */
    public void updateAndDrawHighway(Canvas canvas) {
        //Dibujo de carretera
        if (isRunning()) {
            highway.setX(highway.getX() - highway.getVelocity());
            if (highway.getX() < -AppConstants.getBitmapBank().getHighwayWidth()) {
                highway.setX(0);
            }
        }
        canvas.drawBitmap(AppConstants.getBitmapBank().getHighway(), highway.getX(), highway.getY(), null);
        if (highway.getX() < -(AppConstants.getBitmapBank().getHighwayWidth() - Tools.SCREEN_WIDTH)) {
            canvas.drawBitmap(AppConstants.getBitmapBank().getHighway(), highway.getX() +
                    AppConstants.getBitmapBank().getHighwayWidth(), highway.getY(), null);
        }

        canvas.drawRect(highway.getCollision(), AppConstants.SVTools.pRects);
    }

    /**
     * Comprueba si la colisión del personaje intercepta con la colisión de la carretera ({@link #highway}).
     */
    public void updateHighwayCollision() {
        //Si la colisión de la carretera intercepta a la colisión del personaje el juego finaliza
        if (isRunning() && highway.getCollision().intersect(character.getCollision())) {
            setGameRunning(false);
            setRunning(false);
            collision = true;
        }
    }

    /**
     * Dibuja al personaje y su colisión, y actualiza su posición en el lienzo. También dibuja el cambio
     * de fotogramas del personaje si se ha comenzado a jugar ({@link #character}).
     *
     * @param canvas lienzo de dibujo
     */
    public void updateAndDrawCharacter(Canvas canvas) {
        int currentFrame = character.getCurrentFrame();
        if (isGameRunning()) {
            //Establece una velocidad de salto y una gravedad, siempre que la posición Y del personaje
            //sea menor que la altura de la pantalla - su altura*2 (para que no llegue al borde de abajo de la pantalla)
            if ((character.getY() < Tools.SCREEN_HEIGHT - AppConstants.getBitmapBank().getCharacterHeight() * 2) ||
                    character.getVelocity() < 0) {
                character.setVelocity(character.getVelocity() + AppConstants.gravity);
                character.setY(character.getY() + character.getVelocity());
            }
            //Si la posición Y del personaje es menor a 0 (pantalla) - su altura/2 no puede subir más allá
            if (character.getY() < (-AppConstants.getBitmapBank().getCharacterHeight() / 2) && character.getVelocity() < 0) {
                character.setVelocity(0);
                character.setY(character.getY() + character.getVelocity());
            }
        }
        //Colisión personaje
        character.setCollision(new Rect(character.getX(), character.getY(),
                character.getX() + AppConstants.getBitmapBank().getCharacterWidth(),
                character.getY() + AppConstants.getBitmapBank().getCharacterHeight()));

        canvas.drawBitmap(AppConstants.getBitmapBank().getCharacter(currentFrame), character.getX(),
                character.getY(), null);
        canvas.drawRect(character.getCollision(), AppConstants.SVTools.pRects);

        //Cambio de fotograma del personaje
        if (isGameRunning()) {
            currentFrame++;
            if (currentFrame > Character.maxFrame) {
                currentFrame = 0;
            }
            character.setCurrentFrame(currentFrame);
        }
    }

    /**
     * Si se ha comenzado a jugar, se dibuja el conjunto de cajas y sus respectivas colisiones, y se actualizan
     * sus posiciones en el lienzo ({@link #boxes}).
     *
     * @param canvas lienzo de dibujo
     */
    public void updateAndDrawBoxes(Canvas canvas) {
        //Si el juego está en funcionamiento la puntuación se activa y las cajas se mueven
        if (isGameRunning()) {
            //Movimiento de cajas
            for (int i = 0; i < AppConstants.numberOfBoxes; i++) {
                if (boxes.get(i).getX() < -AppConstants.getBitmapBank().getBoxWidth()) {
                    boxes.get(i).setX(boxes.get(i).getX() + AppConstants.numberOfBoxes * AppConstants.distanceBetweenBoxes);
                    int topBoxOffsetY = AppConstants.minBoxOffsetY + random.nextInt(AppConstants.maxBoxOffsetY - AppConstants.minBoxOffsetY + 1);
                    boxes.get(i).setOffsetY(topBoxOffsetY);
                    boxes.get(i).setBoxColor();
                }
                boxes.get(i).setX(boxes.get(i).getX() - AppConstants.boxVelocity);
            }
        }

        //Dibujo de cajas de diferente color
        for (int i = 0; i < AppConstants.numberOfBoxes; i++) {
            switch (boxes.get(i).getBoxColor()) {
                case 0:
                    canvas.drawBitmap(AppConstants.getBitmapBank().getBoxTop(), boxes.get(i).getX(), boxes.get(i).getTopBoxY(), null);
                    canvas.drawBitmap(AppConstants.getBitmapBank().getBoxBottom(), boxes.get(i).getX(), boxes.get(i).getBottomBoxY(), null);
                    break;
                case 1:
                    canvas.drawBitmap(AppConstants.getBitmapBank().getWhiteBoxTop(), boxes.get(i).getX(), boxes.get(i).getTopBoxY(), null);
                    canvas.drawBitmap(AppConstants.getBitmapBank().getWhiteBoxBottom(), boxes.get(i).getX(), boxes.get(i).getBottomBoxY(), null);
                    break;
            }

            //Colisiones cajas
            for (int j = 0; j < boxes.get(i).getCollisions().length; j++) {
                if (j == 0) {
                    boxes.get(i).setCollision(new Rect(boxes.get(i).getX(), boxes.get(i).getBottomBoxY(),
                            boxes.get(i).getX() + AppConstants.getBitmapBank().getBoxWidth(),
                            boxes.get(i).getBottomBoxY() + AppConstants.getBitmapBank().getBoxHeight()), j);
                } else {
                    boxes.get(i).setCollision(new Rect(boxes.get(i).getX(), boxes.get(i).getTopBoxY(),
                            boxes.get(i).getX() + AppConstants.getBitmapBank().getBoxWidth(),
                            boxes.get(i).getTopBoxY() + AppConstants.getBitmapBank().getBoxHeight()), j);
                }
                canvas.drawRect(boxes.get(i).getCollisions()[j], AppConstants.SVTools.pRects);
            }
        }
    }

    /**
     * Comprueba si la colisión del personaje intercepta con alguna colisión del conjunto de cajas ({@link #boxes}).
     */
    public void updateBoxesCollisions() {
        //Si las colisiones de las cajas interceptan a la colisión del personaje el juego finaliza
        if (isRunning() && (boxes.get(0).getCollisions()[0].intersect(character.getCollision()) ||
                boxes.get(0).getCollisions()[1].intersect(character.getCollision()))) {
            setGameRunning(false);
            setRunning(false);
            collision = true;
        }
        if (isRunning() && (boxes.get(1).getCollisions()[0].intersect(character.getCollision()) ||
                boxes.get(1).getCollisions()[1].intersect(character.getCollision()))) {
            setGameRunning(false);
            setRunning(false);
            collision = true;
        }
    }

    /**
     * Si se ha comenzado a jugar, se dibuja el temporizador y se actualiza el texto a mostrar.
     * También se dibuja y actualiza el texto de la puntuación de la partida ({@link #score}, {@link #timer},
     * {@link #minutes},{@link #seconds}).
     *
     * @param canvas lienzo de dibujo
     */
    public void updateAndDrawScoreAndTime(Canvas canvas) {
        //Puntuación: si el personaje pasa por el medio de las cajas, se suma puntuación
        if (isGameRunning()) {
            if (boxes.get(scoringBox).getX() < character.getX() + AppConstants.getBitmapBank().getBoxWidth() / 2) {
                if (Tools.effects) AppConstants.getSoundsBank().playBoxCrossed();
                score++;
                scoringBox++;
                if (scoringBox > AppConstants.numberOfBoxes - 1) {
                    scoringBox = 0;
                }
                if (AppConstants.actualGapBetweenTopAndBottomBoxes <= AppConstants.getBitmapBank().getCharacterWidth() * 2) {
                    AppConstants.actualGapBetweenTopAndBottomBoxes = AppConstants.getBitmapBank().getCharacterWidth() * 2;
                } else {
                    if (score % 5 == 0 && score != 0) {
                        decreaseGapCountDown.start();
                    }
                }
            } else {
                if (crossed && gapDecrease != 0) {
                    AppConstants.actualGapBetweenTopAndBottomBoxes--;
                    gapDecrease--;
                    if (gapDecrease == 0) crossed = false;
                }
            }

            //Dibujo de tiempo
            canvas.drawText(String.format("%02d:%02d", minutes, seconds),
                    Tools.SCREEN_WIDTH - timeLength + AppConstants.SVTools.getPixels(1),
                    AppConstants.SVTools.pRegular[0].getTextSize() + AppConstants.SVTools.getPixels(1)
                    , AppConstants.SVTools.pRegular[0]);
            canvas.drawText(String.format("%02d:%02d", minutes, seconds), Tools.SCREEN_WIDTH - timeLength,
                    AppConstants.SVTools.pRegular[1].getTextSize(), AppConstants.SVTools.pRegular[1]);
        }
        //Dibujo de puntuación
        canvas.drawText(String.valueOf(score), Tools.SCREEN_WIDTH / 2 + AppConstants.SVTools.getPixels(5),
                Tools.SCREEN_HEIGHT / 4 + AppConstants.SVTools.getPixels(5), AppConstants.SVTools.pBold[0]);
        canvas.drawText(String.valueOf(score), Tools.SCREEN_WIDTH / 2, Tools.SCREEN_HEIGHT / 4, AppConstants.SVTools.pBold[1]);
    }
}
