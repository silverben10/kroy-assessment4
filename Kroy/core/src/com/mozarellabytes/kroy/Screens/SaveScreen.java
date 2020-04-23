package com.mozarellabytes.kroy.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mozarellabytes.kroy.Kroy;
import com.mozarellabytes.kroy.Utilities.GUI;
import com.mozarellabytes.kroy.Utilities.GameInputHandler;
import com.mozarellabytes.kroy.Utilities.SaveScreenInputHandler;

import static com.badlogic.gdx.utils.Align.center;

public class SaveScreen implements Screen {

    private final Kroy game;

    private final Texture backgroundImage;

    private final OrthographicCamera camera;

    private final Rectangle exitButton;

    private final Screen parent;

    /**
     * Width of the screen
     */
    private final float screenWidth;

    /**
     * Height of the screen
     */
    private final float screenHeight;

    private GlyphLayout layout = new GlyphLayout();

    private final Rectangle saveButton1;

    private final Rectangle saveButton2;

    private final Rectangle saveButton3;

    /**
     * Constructor for the Save scree.
     *
     * @param game   Reference to the Kroy LibGDX game
     * @param parent the screen that called the Save screen
     */
    public SaveScreen(Kroy game, Screen parent) {
        this.game = game;
        this.parent = parent;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        screenWidth = camera.viewportWidth;
        screenHeight = camera.viewportHeight;

        Gdx.input.setInputProcessor(new SaveScreenInputHandler(this));

        backgroundImage = new Texture(Gdx.files.internal("images/YorkMapEdit.png"), true);
        backgroundImage.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.MipMapLinearNearest);

        exitButton = new Rectangle();
        exitButton.x = (int) (screenWidth / 1.08f);
        exitButton.y = (int) (screenHeight / 1.126f);
        exitButton.width = 30;
        exitButton.height = 30;

        saveButton1 = new Rectangle();
        saveButton1.width = 500;
        saveButton1.height = 50;
        saveButton1.x = screenWidth/2 - saveButton1.width/2;
        saveButton1.y = screenHeight - 400;

        saveButton2 = new Rectangle();
        saveButton2.width = 500;
        saveButton2.height = 50;
        saveButton2.x = screenWidth/2 - saveButton2.width/2;
        saveButton2.y = screenHeight - 500;

        saveButton3 = new Rectangle();
        saveButton3.width = 500;
        saveButton3.height = 50;
        saveButton3.x = screenWidth/2 - saveButton3.width/2;
        saveButton3.y = screenHeight - 600;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        camera.update();

        drawBackgroundImage();
        drawFilledBackgroundBox();

        game.batch.begin();

        layout.setText(game.font50, "Save Game");
        game.font50.draw(game.batch, layout, screenWidth / 2 - layout.width / 2, screenHeight - 100);

        layout.setText(game.font25, "Select one of the slots below to save your current game.");
        game.font25.draw(game.batch, layout, screenWidth / 2 - layout.width / 2, screenHeight - 175);

        layout.setText(game.font25, "This will overwrite any data currently saved in that slot.");
        game.font25.draw(game.batch, layout, screenWidth / 2 - layout.width / 2, screenHeight - 225);
        game.batch.end();

        renderSaveButton(1, saveButton1.x, saveButton1.y, (int) saveButton1.width, (int) saveButton1.height);
        renderSaveButton(2, saveButton2.x, saveButton2.y, (int) saveButton2.width, (int) saveButton2.height);
        renderSaveButton(3, saveButton3.x, saveButton3.y, (int) saveButton3.width, (int) saveButton3.height);

        renderExitButton();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    public void closeScreen() {
        GUI gui = new GUI(game, (GameScreen) parent);
        Gdx.input.setInputProcessor(new GameInputHandler((GameScreen) parent, gui));
        gui.idleInfoButton();
        this.game.setScreen(parent);
    }

    /**
     * Draws the image being shown behind the controls panel
     */
    private void drawBackgroundImage() {
        game.batch.begin();
        game.batch.draw(backgroundImage, 0, 0, camera.viewportWidth, camera.viewportHeight);
        game.batch.end();
    }

    /**
     * Draws the black rectangle over which the controls are shown
     */
    private void drawFilledBackgroundBox() {
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        game.shapeRenderer.rect(screenWidth / 25.6f, screenHeight / 16, screenWidth / 1.085f, screenHeight / 1.14f, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK);
        game.shapeRenderer.end();
    }

    /**
     * Renders the exit button
     */
    private void renderExitButton() {
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        game.shapeRenderer.rect(screenWidth / 1.08f, screenHeight / 1.126f, 30, 30, Color.FIREBRICK, Color.FIREBRICK,
            Color.FIREBRICK, Color.FIREBRICK);
        game.shapeRenderer.rect(screenWidth / 1.078f, screenHeight / 1.123f, 26, 26, Color.RED, Color.RED, Color.RED,
            Color.RED);
        game.shapeRenderer.end();

        game.batch.begin();
        game.font33Red.draw(game.batch, "X", screenWidth / 1.075f, screenHeight / 1.103f);
        game.batch.end();
    }

    private void renderSaveButton(int saveSlot, float x, float y, int buttonWidth, int buttonHeight) {
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        game.shapeRenderer.rect(x, y, buttonWidth, buttonHeight, Color.LIGHT_GRAY, Color.LIGHT_GRAY,
            Color.LIGHT_GRAY, Color.LIGHT_GRAY);
        game.shapeRenderer.end();

        game.batch.begin();
        layout.setText(game.font33Red, "Slot "+ saveSlot);
        game.font33Red.draw(game.batch, layout, x+(buttonWidth/2)-(layout.width/2), y+buttonHeight-(layout.height/2));
        game.batch.end();
    }

    public Rectangle getExitButton() {
        return this.exitButton;
    }

    public Rectangle getSaveButton1() {
        return saveButton1;
    }

//    public Rectangle getSaveButton2() {
//        return saveButton2;
//    }
//
//    public Rectangle getSaveButton3() {
//        return saveButton3;
//    }

    public OrthographicCamera getCamera() {
        return this.camera;
    }

    @Override
    public void dispose() {

    }
}
