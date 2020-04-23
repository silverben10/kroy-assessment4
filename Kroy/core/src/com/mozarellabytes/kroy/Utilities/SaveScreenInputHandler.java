package com.mozarellabytes.kroy.Utilities;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mozarellabytes.kroy.Screens.ControlsScreen;
import com.mozarellabytes.kroy.Screens.SaveScreen;

public class SaveScreenInputHandler implements InputProcessor {
    private final SaveScreen saveScreen;

    /**
     *  Constructs the Save screen input handler
     *
     * @param saveScreen the control screen that this input handler controls
     */
    public SaveScreenInputHandler(SaveScreen saveScreen) {
        this.saveScreen = saveScreen;
    }

    /** Called when a key was pressed
     *
     * Closes the save screen when the ESC key is pressed.
     *
     * @param keycode one of the constants in {@link Input.Keys}
     * @return whether the input was processed */
    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ESCAPE) {
            saveScreen.closeScreen();
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
        Vector3 position = saveScreen.getCamera().unproject(new Vector3(clickCoordinates.x, clickCoordinates.y, 0));
        if(saveScreen.getExitButton().contains(position.x, position.y)){
            saveScreen.closeScreen();
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
