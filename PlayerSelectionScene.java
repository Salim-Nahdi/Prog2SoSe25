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

public class PlayerSelectionScene {
    private final Scene scene;
    private final List<Button> selectedButtons = new ArrayList<>();
    private final Button nextBtn = new Button();
    private final Image nextEnabledImg = new Image(Paths.get("assets/button/Next.png").toUri().toString());
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

        // NEXT Button (Initially disabled and gray)
        nextImgView.setImage(nextDisabledImg);
        nextImgView.setFitWidth(180);
        nextImgView.setPreserveRatio(true);
        nextBtn.setGraphic(nextImgView);
        nextBtn.setStyle("-fx-background-color: transparent;");
        nextBtn.setDisable(true);
        nextBtn.setOnAction(e -> {
            System.out.println("Proceeding with 2 selected characters...");
            // Proceed to game logic
        });

        VBox layout = new VBox(40, allPlayers, nextBtn);
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

        // Select and Selected buttons
        Image selectImage = new Image(Paths.get("assets/button/Select.png").toUri().toString());
        Image selectedImage = new Image(Paths.get("assets/button/Selected.png").toUri().toString());
        ImageView buttonImageView = new ImageView(selectImage);
        buttonImageView.setFitWidth(140);
        buttonImageView.setPreserveRatio(true);

        Button selectButton = new Button("", buttonImageView);
        selectButton.setStyle("-fx-background-color: transparent;");
        selectButton.setPrefSize(buttonImageView.getFitWidth(), buttonImageView.getFitHeight());

        selectButton.setOnAction(e -> {
            if (selectedButtons.contains(selectButton)) {
                selectedButtons.remove(selectButton);
                buttonImageView.setImage(selectImage);
            } else if (selectedButtons.size() < 2) {
                selectedButtons.add(selectButton);
                buttonImageView.setImage(selectedImage);
            }
            updateNextButtonState();
        });

        VBox vbox = new VBox(15, characterView, selectButton);
        vbox.setAlignment(Pos.CENTER);
        return vbox;
    }

    private void updateNextButtonState() {
        if (selectedButtons.size() == 2) {
            nextBtn.setDisable(false);
            nextImgView.setImage(nextEnabledImg);
        } else {
            nextBtn.setDisable(true);
            nextImgView.setImage(nextDisabledImg);
        }
    }

    public Scene getScene() {
        return scene;
    }
}
