package com.github.jeffersonrolino;

import com.github.jeffersonrolino.entities.BaseGame;
import com.github.jeffersonrolino.screens.MenuScreen;

public class StarfishGame extends BaseGame {
    @Override
    public void create() {
        setActiveScreen(new MenuScreen());
    }
}
