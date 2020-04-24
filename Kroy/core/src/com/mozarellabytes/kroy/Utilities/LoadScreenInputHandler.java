package com.mozarellabytes.kroy.Utilities;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mozarellabytes.kroy.Screens.LoadScreen;

public class LoadScreenInputHandler implements InputProcessor {
    private final LoadScreen loadScreen;

    /**
     * Constructs the load screen input handler
     *
     * @param loadScreen the control screen that this input handler controls
     */
    public LoadScreenInputHandler(LoadScreen loadScreen) {
        this.loadScreen = loadScreen;
    }

    /**
     * Called when a key was pressed
     * <p>
     * Closes the load screen when the ESC key is pressed.
     *
     * @param keycode one of the constants in {@link Input.Keys}
     * @return whether the input was processed
     */
    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ESCAPE) {
            loadScreen.closeScreen();
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector2 clickCoordinates = new Vector2(screenX, screenY);
        Vector3 position = loadScreen.getCamera().unproject(new Vector3(clickCoordinates.x, clickCoordinates.y, 0));
        if (loadScreen.getExitButton().contains(position.x, position.y)) {
            loadScreen.closeScreen();
        } else if (loadScreen.getLoadButton1().contains(position.x, position.y)) {
            loadScreen.toGameScreen(0, 1);
        } else if (loadScreen.getLoadButton2().contains(position.x, position.y)) {
            loadScreen.toGameScreen(0, 2);
        } else if (loadScreen.getLoadButton3().contains(position.x, position.y)) {
            loadScreen.toGameScreen(0, 3);
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
