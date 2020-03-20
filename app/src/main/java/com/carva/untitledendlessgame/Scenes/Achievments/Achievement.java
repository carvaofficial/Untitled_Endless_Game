package com.carva.untitledendlessgame.Scenes.Achievments;

/**
 * Creador de logros para el apartado de Logros del juego.
 *
 * @author carva
 * @version 1.0
 */
public class Achievement {
    /**
     * Tipos de logros que existen:
     * 0 = NOPE (ninguno)
     * 1 = JUMPS (referido a los saltos realizados en el juego 1)
     * 2 = TOUCHES (referido a los toques realizados en el juego 2)
     * 3 = SCORE (referido a la puntuación obtenida en el juego)
     * 4 = CRASHES (referido a las colisiones realizadas en el juego)
     * 5 = TIME (referido al tiempo de duración una partida)
     */
    public static final int NOPE = 0, JUMPS = 1, TOUCHES = 2, SCORE = 3, CRASHES = 4, TIME = 5;
    /**
     * Título del logro
     */
    private String title;
    /**
     * Descripción breve del logro.
     */
    private String description;
    /**
     * Icono/Imagen del log.
     */
    private int icon;
    /**
     * Progreso actual del logro.
     */
    private int currentProgress;
    /**
     * Meta del logro.
     */
    private int goal;
    /**
     * Tipo de logro
     */
    private int type;


    /**
     * Inicialización de logro con propiedades vacías.
     */
    public Achievement() {
        this.title = "";
        this.description = "";
        this.icon = 0;
        this.currentProgress = 0;
        this.goal = 0;
        this.type = Achievement.NOPE;
    }

    /**
     * Inicialización de logro. Si {@link #currentProgress} es mayor o igual a {@link #goal},
     * se iguala el valor de {@link #currentProgress} con el de {@link #goal}.
     *
     * @param title           título del logro.
     * @param description     descripción breve del logro.
     * @param icon            icono/imagen del logro.
     * @param currentProgress progreso actual del logro.
     * @param goal            meta del logro.
     * @param type            tipo dew logro.
     */
    public Achievement(String title, String description, int icon, int currentProgress, int goal, int type) {
        this.title = title;
        this.description = description;
        this.icon = icon;
        this.currentProgress = currentProgress;
        this.goal = goal;
        this.type = type;

        if (this.currentProgress >= this.goal) {
            this.currentProgress = this.goal;
        }
    }

    /**
     * @return título del logro.
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title establece un nuevo título para el logro.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return descripción breve del logro.
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description establece una nueva descripción para el logro.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return icono/imagen del logro.
     */
    public int getIcon() {
        return icon;
    }

    /**
     * @param icon establece una nueva imagen/icono para el logro.
     */
    public void setIcon(int icon) {
        this.icon = icon;
    }

    /**
     * @return progreso actual del logro.
     */
    public int getCurrentProgress() {
        return currentProgress;
    }

    /**
     * @param currentProgress establece un nuevo progreso actual para el logro.
     */
    public void setCurrentProgress(int currentProgress) {
        this.currentProgress = currentProgress;
    }

    /**
     * @return meta del logro.
     */
    public int getGoal() {
        return goal;
    }

    /**
     * @param goal establece una nueva meta para el logro.
     */
    public void setGoal(int goal) {
        this.goal = goal;
    }

    /**
     * @return tipo de logro.
     */
    public int getType() {
        return type;
    }

    /**
     * @param type establece un nuevo tipo de logro.
     */
    public void setType(int type) {
        this.type = type;
    }
}
