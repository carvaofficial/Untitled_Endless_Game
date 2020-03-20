package com.carva.untitledendlessgame.Scenes.Markers;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Adaptador de las pestañas de los marcadores.
 *
 * @author carva
 * @version 1.0
 */
public class TabsAdapter extends FragmentPagerAdapter {
    /**
     * Colección de <code>Fragments</code> a visualizar.
     */
    private ArrayList<Fragment> tabsFragments = new ArrayList<>();

    /**
     * @param fm gestor de <code>Fragments</code>.
     * @see FragmentManager
     */
    public TabsAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * Añade un <code>Fragment</code> a la colección
     *
     * @param f <code>Fragment</code>
     */
    public void insert(Fragment f) {
        tabsFragments.add(f);
    }

    /**
     * @param position posición del <code>Fragment</code> dentro de la colección.
     * @return <code>Fragment</code> de la colección {@link #tabsFragments}.
     */
    @Override
    public Fragment getItem(int position) {
        return tabsFragments.get(position);
    }

    /**
     * @return tamaño de la colección de <code>Fragments</code> ({@link #tabsFragments}).
     */
    @Override
    public int getCount() {
        return tabsFragments.size();
    }
}
