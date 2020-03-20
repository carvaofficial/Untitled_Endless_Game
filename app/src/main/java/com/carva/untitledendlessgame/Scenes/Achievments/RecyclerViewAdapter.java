package com.carva.untitledendlessgame.Scenes.Achievments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.carva.untitledendlessgame.R;
import com.carva.untitledendlessgame.Resources.Tools;

import java.util.ArrayList;

/**
 * Adaptador del <code>RecyclerView</code> {@link AchievementsActivity#rvAchievments}.
 *
 * @author carva
 * @version 1.0
 * @see RecyclerView.Adapter
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    /**
     * Contexto de la aplicación.
     *
     * @see Context
     */
    Context context;
    /**
     * Colección de logros.
     */
    private ArrayList<Achievement> achievements;
    /**
     * Lista de metas de saltos.
     */
    private int[] jumps = {100, 500, 1000};
    /**
     * Lista de metas de puntuaciones.
     */
    private int[] scores = {50, 100, 200};
    /**
     * Lista de metas de colisiones.
     */
    private int[] crashes = {10, 50, 100};
    /**
     * Lista de metas de tiempos.
     */
    private int[] times = {60, 120, 180};

    /**
     * Establece los valores de los marcadores, inicializa la colección de logros y la rellena.
     *
     * @param context contexto de la aplicación.
     */
    public RecyclerViewAdapter(Context context) {
        this.context = context;
        Tools.establishMarkers(this.context);
        this.achievements = new ArrayList<>();
        fillAchievements();
    }

    /**
     * Rellena la colección de logros ({@link #achievements}).
     */
    private void fillAchievements() {
        achievements.add(new Achievement(context.getResources().getString(R.string.ach1_jumps),
                "", R.drawable.ach1_jumps, Tools.totalJumps, jumps[0], Achievement.JUMPS));
        achievements.add(new Achievement(context.getResources().getString(R.string.ach2_jumps),
                "", R.drawable.ach2_jumps, Tools.totalJumps, jumps[1], Achievement.JUMPS));
        achievements.add(new Achievement(context.getResources().getString(R.string.ach3_jumps),
                "", R.drawable.ach3_jumps, Tools.totalJumps, jumps[2], Achievement.JUMPS));

        achievements.add(new Achievement(context.getResources().getString(R.string.ach1_scores),
                "", R.drawable.ach1_scores, Tools.maxCrossedBoxes, scores[0], Achievement.SCORE));
        achievements.add(new Achievement(context.getResources().getString(R.string.ach2_scores),
                "", R.drawable.ach2_scores, Tools.maxCrossedBoxes, scores[1], Achievement.SCORE));
        achievements.add(new Achievement(context.getResources().getString(R.string.ach3_scores),
                "", R.drawable.ach3_scores, Tools.maxCrossedBoxes, scores[2], Achievement.SCORE));

        achievements.add(new Achievement(context.getResources().getString(R.string.ach1_crashes),
                "", R.drawable.ach1_crashes, Tools.totalCrashedBoxes, crashes[0], Achievement.CRASHES));
        achievements.add(new Achievement(context.getResources().getString(R.string.ach2_crashes),
                "", R.drawable.ach2_crashes, Tools.totalCrashedBoxes, crashes[1], Achievement.CRASHES));
        achievements.add(new Achievement(context.getResources().getString(R.string.ach3_crashes),
                "", R.drawable.ach3_crashes, Tools.totalCrashedBoxes, crashes[2], Achievement.CRASHES));

        achievements.add(new Achievement(context.getResources().getString(R.string.ach1_times),
                "", R.drawable.ach1_times, Tools.bestTimeInSeconds, times[0], Achievement.TIME));
        achievements.add(new Achievement(context.getResources().getString(R.string.ach2_times),
                "", R.drawable.ach2_times, Tools.bestTimeInSeconds, times[1], Achievement.TIME));
        achievements.add(new Achievement(context.getResources().getString(R.string.ach3_times),
                "", R.drawable.ach3_times, Tools.bestTimeInSeconds, times[2], Achievement.TIME));
    }

    /**
     * Establece el progreso de la barra de progreso. Según la versión de Android, introduce una animación.
     *
     * @param holder   vista
     * @param progress progreso actual del logro.
     * @see ViewHolder
     */
    private void setProgressBarProgress(ViewHolder holder, int progress) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.currentProgress.setProgress(progress, true);
        } else {
            holder.currentProgress.setProgress(progress);
        }
    }

    /**
     * Crea y devuelve nuevos elementos para el <code>RecyclerView</code>, expandiendo el layout definido.
     *
     * @param parent   grupo de vistas padre
     * @param viewType tipo de vista
     * @return vista
     * @see ViewGroup
     * @see ViewHolder
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.achievement,
                parent, false));
    }

    /**
     * Sustituye el contenido del elemento definido por <code>holder</code> por los valores de la
     * colección de datos que están en la posición <code>position</code>. Dependiendo del tipo de logro
     * se mostrará en el <code>TextView progress</code> un dato formateado referido al tipo de logro.
     * Por ejemplo, los logros del tipo <code>Achievement.TIME</code> mostrarán el tiempo
     * de la siguiente manera: '00:00 / 00:00'; pero si el logro es del tipo <code>Achievement.SCORE</code>
     * mostrará: '0 / 0'.
     *
     * @param holder   vista
     * @param position posición del elemento dentro del conjunto de datos del adaptador.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String format = "";
        Achievement achievement = this.achievements.get(position);
        holder.title.setText(achievement.getTitle());
//        holder.description.setText(achievement.getDescription());
        holder.icon.setImageResource(achievement.getIcon());
        switch (achievement.getType()) {
            case Achievement.JUMPS:
            case Achievement.TOUCHES:
            case Achievement.SCORE:
            case Achievement.CRASHES:
                format = String.format("%3d / %3d", achievement.getCurrentProgress(), achievement.getGoal());
                break;
            case Achievement.TIME:
                format = String.format("%02d:%02d / %02d:%02d", achievement.getCurrentProgress() / 60,
                        achievement.getCurrentProgress() % 60, achievement.getGoal() / 60, achievement.getGoal() % 60);
                break;
        }
        holder.progress.setText(format);
        holder.currentProgress.setMax(achievement.getGoal());
        holder.currentProgress.getProgressDrawable().setColorFilter(Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN);
        setProgressBarProgress(holder, achievement.getCurrentProgress());

        //Si no se ha alcanzado la meta, establece el color del icono a blanco y negro, y del resto
        // de componentes a tonalidad gris
        if (achievement.getCurrentProgress() < achievement.getGoal()) {
            holder.title.setTextColor(context.getResources().getColor(R.color.grey));
//            holder.description.setTextColor(context.getResources().getColor(R.color.grey));
            holder.progress.setTextColor(context.getResources().getColor(R.color.grey));
            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(0);
            holder.icon.setColorFilter(new ColorMatrixColorFilter(matrix));
        } else {
            holder.title.setTextColor(context.getResources().getColor(R.color.white));
            holder.progress.setTextColor(context.getResources().getColor(R.color.white));
        }
    }

    /**
     * @return tamaño de la colección de logros ({@link #achievements}).
     */
    @Override
    public int getItemCount() {
        return achievements.size();
    }

    /**
     * <code>RecyclerView.ViewHolder</code> para el adaptador {@link RecyclerViewAdapter}.
     *
     * @author carva
     * @version 1.0
     * @see RecyclerView.ViewHolder
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * Título del logro.
         */
        TextView title;
        /**
         * Descripción breve del logro.
         */
        TextView description;
        /**
         * Progreso actual del logro (en texto).
         */
        TextView progress;
        /**
         * Icono/Imagen del logro.
         */
        ImageView icon;
        /**
         * Progreso actual del logro (visual).
         */
        ProgressBar currentProgress;

        /**
         * Inicializa las propiedades con su respectivo componente del diseño.
         *
         * @param itemView items de la vista
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.tvAchievementTitle);
//            this.description = itemView.findViewById(R.id.tvAchievementTitle);
            this.progress = itemView.findViewById(R.id.tvAchievementProgress);
            this.icon = itemView.findViewById(R.id.ivAchievement);
            this.currentProgress = itemView.findViewById(R.id.currentProgress);
        }
    }
}
