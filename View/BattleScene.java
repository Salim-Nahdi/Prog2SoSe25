package View;

import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
//.
import Model.Player;
import Model.Attack;
import Controller.GameController;

public class BattleScene {
    private final Scene scene;
    private final GameController controller;
    private final Label statusLabel = new Label();
//    private final Label player1HpLabel = new Label();
//    private final Label player2HpLabel = new Label();
// --
    private Rectangle p1HealthRect;
    private Rectangle p2HealthRect;

    private ImageView p1View;
    private ImageView p2View;
    private final Random random = new Random();
    private boolean isJumping = false;

    //HP bar
    private Group createHealthBar(Player player, double width, double height, Color barColor, boolean isPlayerOne){
        Rectangle rectBackground = new Rectangle(width, height);
        rectBackground.setFill(Color.GRAY);
        double healthWidth = player.getHp() / player.getMaxHp() * width;
        Rectangle rectHealth = new Rectangle(healthWidth, height);
        rectHealth.setFill(barColor);
        Label nameLabel = new Label(player.getName());
        nameLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");
        nameLabel.setTranslateY(-20);
        if (isPlayerOne){
            p1HealthRect = rectHealth;
        } else { p2HealthRect = rectHealth; }
        return new Group(rectBackground, rectHealth, nameLabel);
    }

    public BattleScene(Stage stage, String path1, String path2) {
        Player p1 = createPlayerFromPath(path1);
        Player p2 = createPlayerFromPath(path2);
        controller = new GameController(p1, p2);

        BorderPane root = new BorderPane();

        // Background
        Image bg = new Image(Paths.get("assets/bg.jpg").toUri().toString());
        BackgroundImage bgImage = new BackgroundImage(bg, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, new BackgroundSize(1.0, 1.0, true, true, false, false));
        root.setBackground(new Background(bgImage));

        // Top - HP Labels
        Group leftBar = createHealthBar(p1, 200, 20, Color.LIMEGREEN, true);
        Group rightBar = createHealthBar(p2, 200, 20, Color.RED, false);

        leftBar.setLayoutX(20);
        leftBar.setLayoutY(20);

        // Character setup
        p1View = new ImageView(new Image(Paths.get(p1.getImagePath()).toUri().toString()));
        p2View = new ImageView(new Image(Paths.get(p2.getImagePath()).toUri().toString()));
        p1View.setFitWidth(150);
        p1View.setFitHeight(150);
        p1View.setPreserveRatio(true);
        p2View.setFitWidth(150);
        p2View.setFitHeight(150);
        p2View.setPreserveRatio(true);

        Pane characterPane = new Pane();
        characterPane.setPrefSize(1000, 600);

        // Initial positions
        p1View.setLayoutX(50);       // left side
        p1View.setLayoutY(380);      // slightly above bottom
        p2View.setLayoutX(800);      // right side
        p2View.setLayoutY(355);

        characterPane.getChildren().addAll(p1View, p2View);
        root.setCenter(characterPane);

        // Bottom - Status label only
        VBox bottomBox = new VBox(15, statusLabel);
        bottomBox.setAlignment(Pos.CENTER);
        root.setBottom(bottomBox);

        this.scene = new Scene(root, 1000, 600);
        //--
        rightBar.setLayoutY(20);
        rightBar.setLayoutX(root.getWidth() - 220);
        root.getChildren().addAll(leftBar, rightBar);


        // Controls
        setupMovementControls();

        // Bot starts attacking
        startBotLoop();

     //   updateHpLabels();
    }

    private void setupMovementControls() {
        scene.setOnKeyPressed(event -> {
            double x = p1View.getLayoutX();
            double y = p1View.getLayoutY();

            if (event.getCode() == KeyCode.RIGHT && x < 850) {
                p1View.setLayoutX(x + 10);
            } else if (event.getCode() == KeyCode.LEFT && x > 0) {
                p1View.setLayoutX(x - 10);
            } else if (event.getCode() == KeyCode.UP && !isJumping) {
                jump();
            }
        });
    }

    private void jump() {
        isJumping = true;
        double startY = p1View.getLayoutY();
        double jumpHeight = 150;

        // Going up
        Timeline up = new Timeline(new KeyFrame(Duration.millis(200),
                new KeyValue(p1View.layoutYProperty(), startY - jumpHeight)));
        // Coming down
        Timeline down = new Timeline(new KeyFrame(Duration.millis(200),
                new KeyValue(p1View.layoutYProperty(), startY)));

        SequentialTransition jumpTransition = new SequentialTransition(up, down);
        jumpTransition.setOnFinished(e -> isJumping = false);
        jumpTransition.play();
    }

    private void startBotLoop() {
        Timeline botTimeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
            if (controller.getOpponent().getHp() <= 0 || controller.getCurrentPlayer().getHp() <= 0) return;

            Player bot = controller.getCurrentPlayer();//---
            List<Attack> botAttacks = bot.getAttacks();
            if (!botAttacks.isEmpty()) {
                Attack randomAttack = botAttacks.get(random.nextInt(botAttacks.size()));
                boolean defeated = controller.takeTurn(randomAttack);
                //System.out.print("\n Hp1:" + controller.getPlayer1().getHp());
                //System.out.print("\n Hp2: " + controller.getPlayer2().getHp()); //FOR DEBUG
                updateHpLabels();



                if (defeated) {
                    statusLabel.setText(controller.getOpponent().getName() + " fainted! Game Over.");
                } else {
                    statusLabel.setText(bot.getName() + " used " + randomAttack.getName() + "!");
                    //System.out.println(bot.getName() + " used " + randomAttack.getName() + "!"); //FOR DEBUG
                    //----
                    controller.switchTurn();
                }
            }
        }));
        botTimeline.setCycleCount(Timeline.INDEFINITE);
        botTimeline.play();
    }

    private void updateHpLabels() {
        double p1Hp = 200 * controller.getPlayer1().getHp() / controller.getPlayer1().getMaxHp();
        //System.out.println("\nhp11\n" + p1Hp); //FOR DEBUG
        double p2Hp = 200 * controller.getPlayer2().getHp() / controller.getPlayer2().getMaxHp();
        p1HealthRect.setWidth(p1Hp);
        p2HealthRect.setWidth(p2Hp);
        /*    player1HpLabel.setText(controller.getCurrentPlayer().getName() + " HP: " + controller.getCurrentPlayer().getHp());
        player2HpLabel.setText(controller.getOpponent().getName() + " HP: " + controller.getOpponent().getHp());*/
    }

    private Player createPlayerFromPath(String imagePath) {
        if (imagePath.contains("alien")) {
            return new Player("Alien", imagePath, 100, List.of(
                    new Attack("Slimy Punch", 15),
                    new Attack("UFO Blast", 30)));
        } else if (imagePath.contains("mummy")) {
            return new Player("Mummy", imagePath, 100, List.of(
                    new Attack("Wrap Attack", 10),
                    new Attack("Ancient Curse", 35)));
        } else if (imagePath.contains("Baum")) {
            return new Player("Tree Monster", imagePath, 110, List.of(
                    new Attack("Root Whip", 18),
                    new Attack("Earthquake", 40)));
        } else if (imagePath.contains("Stein")) {
            return new Player("Rock Golem", imagePath, 120, List.of(
                    new Attack("Stone Fist", 20),
                    new Attack("Meteor Slam", 45)));
        }
        return new Player("Unknown", imagePath, 80, new ArrayList<>());
    }

    public Scene getScene() {
        return scene;
    }
}
