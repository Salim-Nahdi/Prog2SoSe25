package View;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.nio.file.Paths;

public class MenuScene {
    private final Scene scene;

    public MenuScene(Stage stage) {
        // === Load Background Image ===
        Image bg = new Image(Paths.get("assets/bg.jpg").toUri().toString());
        BackgroundImage bgImage = new BackgroundImage(
                bg,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(1.0, 1.0, true, true, false, false) // Responsive
        );

        // === Root Layout ===
        StackPane root = new StackPane();
        root.setBackground(new Background(bgImage));

        // === Logo / Title Banner ===
        ImageView banner = new ImageView(new Image(Paths.get("./assets/banner.png").toUri().toString()));
        banner.setFitWidth(300);
        banner.setPreserveRatio(true);

        // === Start Button ===
        ImageView startImg = new ImageView(new Image(Paths.get("assets/button/Start_Game.png").toUri().toString()));
        startImg.setFitWidth(250);
        startImg.setFitHeight(60);

        Button startBtn = new Button("", startImg);
        startBtn.setBackground(Background.EMPTY);
        startBtn.setBorder(Border.EMPTY);
        startBtn.setOnAction(e -> {
            PlayerSelectionScene playerScene = new PlayerSelectionScene(stage);
            stage.setScene(playerScene.getScene());
        });

        // === Quit Button ===
        ImageView quitImg = new ImageView(new Image(Paths.get("assets/button/Quit.png").toUri().toString()));
        quitImg.setFitWidth(250);
        quitImg.setFitHeight(60);

        Button quitBtn = new Button("", quitImg);
        quitBtn.setBackground(Background.EMPTY);
        quitBtn.setBorder(Border.EMPTY);
        quitBtn.setOnAction(e -> stage.close());

        // === Vertical Box Layout ===
        VBox layout = new VBox(20); // spacing between banner and buttons
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(banner, startBtn, quitBtn);

        // === Add to Scene ===
        root.getChildren().add(layout);
        this.scene = new Scene(root, 900, 500); // fixed size
    }

    public Scene getScene() {
        return scene;
    }
}
