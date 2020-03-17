package com.example.untitledendlessgame.Resources;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.CountDownTimer;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameEngine {
    ScrollManager background;
    public Character character;
    private boolean gameRunning, running, crossed;
    public boolean collision;
    private ArrayList<Box> boxes;
    private Random random;
    CountDownTimer decreaseGapCountDown;
    public Timer timer;
    public TimerTask time;
    public int score, minutes, seconds, timeLength;
    int scoringBox, gapDecrease;

    public GameEngine(int backgroundVelocity) {
        background = new ScrollManager(backgroundVelocity);
        character = new Character(5);
        gameRunning = false;
        running = true;
        boxes = new ArrayList<>();
        random = new Random();

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
                gapDecrease = (int) (0.05 * (double) AppConstants.gapBetweenTopAndBottomBoxes);
            }
        };

        //Timer de tiempo de juego
        Rect rTime = new Rect();
        minutes = 0;
        seconds = -1;
        String sTime = "00:00";
        AppConstants.SVTools.pRegular[0].getTextBounds(sTime, 0, sTime.length(), rTime);
        timeLength = rTime.right;
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

    public boolean isGameRunning() {
        return gameRunning;
    }

    public void setGameRunning(boolean gameRunning) {
        this.gameRunning = gameRunning;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void updateAndDrawBackground(Canvas canvas) {
        //Dibujo de background
        if (isRunning()) {
            background.setX(background.getX() - background.getVelocity());
            if (background.getX() < -AppConstants.getBitmapBank().getBackgroundWidth()) {
                background.setX(0);
            }
        }
        canvas.drawBitmap(AppConstants.getBitmapBank().getBackground(), background.getX(), background.getY(), null);
        if (background.getX() < -(AppConstants.getBitmapBank().getBackgroundWidth() - Tools.SCREEN_WIDTH)) {
            canvas.drawBitmap(AppConstants.getBitmapBank().getBackground(), background.getX() +
                    AppConstants.getBitmapBank().getBackgroundWidth(), background.getY(), null);
        }
    }

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
        if (isRunning()) {
            currentFrame++;
            if (currentFrame > Character.maxFrame) {
                currentFrame = 0;
            }
            character.setCurrentFrame(currentFrame);
        }
    }

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

    public void updateAndDrawCollisions() {
        //Si las colisiones de las cajas interceptan a la colisión del personaje el juego finaliza
        Log.i("Coll", "updateAndDrawCollisions: " + boxes.get(0).getCollisions()[0]);
        Log.i("Coll", "updateAndDrawCollisions: " + character.getCollision());
        if ((boxes.get(0).getCollisions()[0].intersect(character.getCollision()) ||
                boxes.get(0).getCollisions()[1].intersect(character.getCollision())) ||
                (boxes.get(1).getCollisions()[0].intersect(character.getCollision()) ||
                        boxes.get(1).getCollisions()[1].intersect(character.getCollision()))) {
            setGameRunning(false);
            setRunning(false);
            collision = true;
        }
    }

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
                if (AppConstants.gapBetweenTopAndBottomBoxes <= AppConstants.getBitmapBank().getCharacterWidth() * 2) {
                    AppConstants.gapBetweenTopAndBottomBoxes = AppConstants.getBitmapBank().getCharacterWidth() * 2;
                } else {
                    if (score % 5 == 0 && score != 0) {
                        decreaseGapCountDown.start();
                    }
                }
            } else {
                if (crossed && gapDecrease != 0) {
                    AppConstants.gapBetweenTopAndBottomBoxes--;
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
