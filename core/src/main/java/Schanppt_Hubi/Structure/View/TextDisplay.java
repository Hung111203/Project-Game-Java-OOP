package Schanppt_Hubi.Structure.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class TextDisplay {
    private Stage stage;
    private Skin skin;

    public TextDisplay(Stage stage, Skin skin) {
        this.stage = stage;
        this.skin = skin;
    }

    public void displayMessage(String message, float displayTime, float x, float y, float width, float height) {
        // Create label style
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = skin.getFont("default-font");
        labelStyle.fontColor = Color.WHITE;

        // Create the label with wrapping enabled
        Label label = new Label(message, labelStyle);
        label.setWrap(true);
        label.setAlignment(Align.center);

        // Set label size and position
        label.setSize(width, height);
        label.setPosition(x, y);

        stage.addActor(label);

        // Add the action to the label
        label.addAction(Actions.sequence(
            Actions.alpha(1), // Set initial transparency
            Actions.delay(displayTime), // Wait for the specified time
            Actions.fadeOut(1), // Fade out over 1 second
            Actions.run(() -> label.remove()) // Remove the label from the stage
        ));

        // Adjust font size to fit text within box
        adjustFontSizeToFit(label, width, height);
    }

    private void adjustFontSizeToFit(Label label, float width, float height) {
        GlyphLayout layout = new GlyphLayout();
        BitmapFont font = label.getStyle().font;
        float fontSize = font.getData().scaleX;

        // Adjusting the font size and layout until it fits within the label size
        do {
            font.getData().setScale(fontSize);
            layout.setText(font, label.getText());
            fontSize -= 0.1f;
        } while ((layout.width > width || layout.height > height) && fontSize > 0);
    }
}
