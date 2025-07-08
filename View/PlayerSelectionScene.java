package View;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import Model.Player;

public class PlayerSelectionScene {
    private final Scene scene;

    // Button state images
    private final Image selectImage   = new Image(Paths.get("assets/button/Select.png").toUri().toString());
    private final Image selectedImage = new Image(Paths.get("assets/button/Selected.png").toUri().toString());
    private final Image redImage      = new Image(Paths.get("assets/button/Selected_red.png").toUri().toString());

    private final List<Button> allButtons = new ArrayList<>();
    private final List<Button> selectedButtons = new ArrayList<>();

    // Next button
    private final Button nextBtn = new Button();
    private final Image nextEnabledImg  = new Image(Paths.get("assets/button/Next.png").toUri().toString());
    private final Image nextDisabledImg = new Image(Paths.get("assets/button/Next.png").toUri().toString());
    private final ImageView nextImgView = new ImageView();

    public PlayerSelectionScene(Stage stage) {
        StackPane root = new StackPane();

        // Background
        Image bg = new Image(Paths.get("assets/bg.jpg").toUri().toString());
        BackgroundImage bgImage = new BackgroundImage(
                bg, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(1.0, 1.0, true, true, false, false)
        );
        root.setBackground(new Background(bgImage));

        // Player Boxes
        VBox player1 = createPlayerBox("assets/alien/alien1.png");
        VBox player2 = createPlayerBox("assets/mummy/mummy1.png");
        VBox player3 = createPlayerBox("assets/Baum/player1.png");
        VBox player4 = createPlayerBox("assets/Stein/rock.png");

        HBox allPlayers = new HBox(60, player1, player2, player3, player4);
        allPlayers.setAlignment(Pos.CENTER);

        // NEXT Button (Initially disabled)
        nextImgView.setImage(nextDisabledImg);
        nextImgView.setFitWidth(180);
        nextImgView.setPreserveRatio(true);
        nextBtn.setGraphic(nextImgView);
        nextBtn.setStyle("-fx-background-color: transparent;");
        nextBtn.setDisable(true);
        nextBtn.setOnAction(e -> proceedToBattle(stage));

        // QUIT Button
        ImageView quitImg = new ImageView(new Image(Paths.get("assets/button/Quit.png").toUri().toString()));
        quitImg.setFitWidth(180);
        quitImg.setFitHeight(40);
        Button quitBtn = new Button("", quitImg);
        quitBtn.setBackground(Background.EMPTY);
        quitBtn.setOnAction(e -> stage.close());

        VBox layout = new VBox(40, allPlayers, nextBtn, quitBtn);
        layout.setAlignment(Pos.CENTER);
        root.getChildren().add(layout);

        this.scene = new Scene(root, 1000, 600);
    }

    private VBox createPlayerBox(String characterImagePath) {
        // Character image
        Image characterImage = new Image(Paths.get(characterImagePath).toUri().toString());
        ImageView characterView = new ImageView(characterImage);
        characterView.setFitWidth(180);
        characterView.setFitHeight(180);

        // Select button
        Button btn = new Button();
        btn.setId(characterImagePath);
        btn.setBackground(Background.EMPTY);
        btn.setGraphic(makeImageView(selectImage));

        btn.setOnAction(e -> {
            if (selectedButtons.remove(btn)) {
                // deselected
            } else if (selectedButtons.size() < 2) {
                selectedButtons.add(btn);
            }
            refreshAllButtonGraphics();
            updateNextButtonState();
        });

        allButtons.add(btn);
        return new VBox(15, characterView, btn);
    }

    private ImageView makeImageView(Image img) {
        ImageView iv = new ImageView(img);
        iv.setFitWidth(140);
        iv.setFitHeight(40);
        return iv;
    }

    private void refreshAllButtonGraphics() {
        boolean twoPicked = (selectedButtons.size() == 2);
        for (Button btn : allButtons) {
            ImageView iv = (ImageView) btn.getGraphic();
            if (selectedButtons.contains(btn)) {
                iv.setImage(selectedImage);
            } else if (twoPicked) {
                iv.setImage(redImage);
            } else {
                iv.setImage(selectImage);
            }
        }
    }

    private void updateNextButtonState() {
        boolean twoSelected = (selectedButtons.size() == 2);
        nextBtn.setDisable(!twoSelected);
        nextImgView.setImage(twoSelected ? nextEnabledImg : nextDisabledImg);
    }

    private void proceedToBattle(Stage stage) {
        // collect selected characters
        String char1 = selectedButtons.get(0).getId();
        String char2 = selectedButtons.get(1).getId();
        Player[] p1 = createPlayers(char1, char2);

        // auto-assign remaining
        String char3 = getRemainingCharacter(char1, char2, "");
        String char4 = getRemainingCharacter(char1, char2, char3);
        Player[] p2 = createPlayers(char3, char4);

        BattleScene battleScene = new BattleScene(stage, p1, p2);
        stage.setScene(battleScene.getScene());
    }

    private String getRemainingCharacter(String s1, String s2, String s3) {
        if (!s1.equals("assets/alien/alien1.png") && !s2.equals("assets/alien/alien1.png") && !s3.equals("assets/alien/alien1.png"))
            return "assets/alien/alien1.png";
        else if (!s1.equals("assets/mummy/mummy1.png") && !s2.equals("assets/mummy/mummy1.png") && !s3.equals("assets/mummy/mummy1.png"))
            return "assets/mummy/mummy1.png";
        else if (!s1.equals("assets/Baum/player1.png") && !s2.equals("assets/Baum/player1.png") && !s3.equals("assets/Baum/player1.png"))
            return "assets/Baum/player1.png";
        else
            return "assets/Stein/rock.png";
    }

    private Player[] createPlayers(String c1, String c2) {
        return new Player[]{Player.createPlayerFromPath(c1), Player.createPlayerFromPath(c2)};
    }

    public Scene getScene() {
        return scene;
    }
}
