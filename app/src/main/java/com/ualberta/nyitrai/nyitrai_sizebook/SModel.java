package com.ualberta.nyitrai.nyitrai_sizebook;

import java.util.ArrayList;

/**
 * Created by nyitrai on 2/4/2017.
 */

public class SModel<V extends SView> {
    private ArrayList<V> views;

    public SModel() {
        views = new ArrayList<V>();
    }

    public void addView(V view) {
        if (!views.contains(view)) {
            views.add(view);
        }
    }

    public void deleteView(V view) {
        views.remove(view);
    }

    public void notifyViews() {
        for (V view : views) {
            view.update(this);
        }
    }
}
