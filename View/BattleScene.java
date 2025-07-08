package View;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Button;

import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Map;
import java.util.HashMap;

import Model.Player;
import Model.Attack;
import Controller.GameController;

public class BattleScene {
    private final Scene scene;
    private GameController controller = null;
    private final Label statusLabel = new Label();
    private boolean playerWon;

    private Rectangle p1HealthRect1;
    private Rectangle p1HealthRect2;
    private Rectangle p2HealthRect1;
    private Rectangle p2HealthRect2;
    private Player[] p1;
    private Player[] p2;
    private Button ultimateBtn;
    private Button attackBtn;
    private Button changeBtn;
    private Stage sstage;

    private ImageView p1View;
    private ImageView p2View;
    private final Random random = new Random();
    private Rectangle textBoardBackground;
    private Label messageLabel;


    private VBox createHealthBar(Player[] player, double width, double height, Color barColor, boolean isPlayerOne) {
        VBox vbox = new VBox(5);
        Rectangle rectBackground1 = new Rectangle(width, height);
        Rectangle rectBackground2 = new Rectangle(width, height);
        rectBackground1.setFill(Color.GRAY);
        rectBackground2.setFill(Color.GRAY);
        double healthWidth1 = player[0].getHp() / player[0].getMaxHp() * width;
        double healthWidth2 = player[1].getHp() / player[1].getMaxHp() * width;
        Rectangle rectHealth1 = new Rectangle(healthWidth1, height);
        Rectangle rectHealth2 = new Rectangle(healthWidth2, height);
        rectHealth1.setFill(barColor);
        rectHealth2.setFill(barColor);
        Label nameLabel1 = new Label(player[0].getName());
        Label nameLabel2 = new Label(player[1].getName());
        nameLabel1.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");
        nameLabel2.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");
        if (isPlayerOne) {
            p1HealthRect1 = rectHealth1;
            p1HealthRect2 = rectHealth2;
        } else {
            p2HealthRect1 = rectHealth1;
            p2HealthRect2 = rectHealth2;
        }
        VBox singleHealthDisplay1 = new VBox(2);
        VBox singleHealthDisplay2 = new VBox(2);
        StackPane stack1 = new StackPane(rectBackground1, rectHealth1);
        StackPane.setAlignment(rectHealth1, Pos.CENTER_LEFT);
        StackPane stack2 = new StackPane(rectBackground2, rectHealth2);
        StackPane.setAlignment(rectHealth2, Pos.CENTER_LEFT);
        singleHealthDisplay1.getChildren().addAll(nameLabel1, stack1);
        singleHealthDisplay2.getChildren().addAll(nameLabel2, stack2);
        vbox.getChildren().addAll(singleHealthDisplay1, singleHealthDisplay2);
        return vbox;
    }

    private void damageTransition(BorderPane root, Attack attack, boolean isPlayerOne) {
        Image image = new Image(Paths.get(attack.getPath()).toUri().toString());
        ImageView imageView = new ImageView(image);
        imageView.setY(400);
        double x1 = isPlayerOne ? 120 : 810;
        double x2 = isPlayerOne ? 810 : 120;
        imageView.setX(x1);
        if (!isPlayerOne) imageView.setScaleX(-1);
        root.getChildren().add(imageView);
        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.7), imageView);
        transition.setFromX(0);
        transition.setToX(x2 - x1);
        transition.setCycleCount(1);
        transition.setOnFinished(event -> root.getChildren().remove(imageView));
        transition.play();
    }

    public BattleScene(Stage stage, Player[] pp1, Player[] pp2) {
        p1 = pp1;
        p2 = pp2;
        sstage = stage;
        controller = new GameController(p1, p2);

        BorderPane root = new BorderPane();

        Image bg = new Image(Paths.get("assets/bg.jpg").toUri().toString());
        BackgroundImage bgImage = new BackgroundImage(bg, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, new BackgroundSize(1.0, 1.0, true, true, false, false));
        root.setBackground(new Background(bgImage));

        VBox leftBar = createHealthBar(p1, 200, 20, Color.LIMEGREEN, true);
        VBox rightBar = createHealthBar(p2, 200, 20, Color.RED, false);

        leftBar.setLayoutX(20);
        leftBar.setLayoutY(20);

        p1View = new ImageView(new Image(Paths.get(p1[controller.getSelectedPlayer()[0]].getImagePath()).toUri().toString()));
        p2View = new ImageView(new Image(Paths.get(p2[controller.getSelectedPlayer()[1]].getImagePath()).toUri().toString()));

        p1View.setFitWidth(150);
        p1View.setFitHeight(150);
        p1View.setPreserveRatio(true);
        p1View.setScaleX(-1);
        p2View.setFitWidth(150);
        p2View.setFitHeight(150);
        p2View.setPreserveRatio(true);

        Pane characterPane = new Pane();
        characterPane.setPrefSize(1000, 600);

        p1View.setLayoutX(50);
        p1View.setLayoutY(380);
        p2View.setLayoutX(800);
        p2View.setLayoutY(355);

        characterPane.getChildren().addAll(p1View, p2View);
        root.setCenter(characterPane);

        VBox bottomBox = new VBox(15, statusLabel);
        bottomBox.setAlignment(Pos.CENTER);
        root.setBottom(bottomBox);

        this.scene = new Scene(root, 1000, 600);

        rightBar.setLayoutY(20);
        rightBar.setLayoutX(root.getWidth() - 220);
        root.getChildren().addAll(leftBar, rightBar);

        Button menuBtn = new Button("☰");
        menuBtn.setStyle("-fx-font-size: 20px; -fx-background-color: transparent; -fx-text-fill: white;");
        menuBtn.setLayoutX(475);
        menuBtn.setLayoutY(20);

        ContextMenu contextMenu = new ContextMenu();
        MenuItem restartItem = new MenuItem("Restart Game");
        MenuItem changePlayerItem = new MenuItem("Change Players");
        MenuItem quitItem = new MenuItem("Quit Game");

        contextMenu.getItems().addAll(restartItem, changePlayerItem, quitItem);

        menuBtn.setOnAction(e -> contextMenu.show(menuBtn, javafx.geometry.Side.BOTTOM, 0, 0));

        restartItem.setOnAction(e -> {
            Player[] freshP1 = new Player[] {
                new Player(
                    p1[0].getName(),
                    p1[0].getImagePath(),
                    p1[0].getMaxHp(),
                    new Attack(p1[0].getNrmlAtk().getPath(), p1[0].getNrmlAtk().getName(), p1[0].getNrmlAtk().getDamage(), false),
                    new Attack(p1[0].getUltAtk().getPath(), p1[0].getUltAtk().getName(), p1[0].getUltAtk().getDamage(), true)
                ),
                new Player(
                    p1[1].getName(),
                    p1[1].getImagePath(),
                    p1[1].getMaxHp(),
                    new Attack(p1[1].getNrmlAtk().getPath(), p1[1].getNrmlAtk().getName(), p1[1].getNrmlAtk().getDamage(), false),
                    new Attack(p1[1].getUltAtk().getPath(), p1[1].getUltAtk().getName(), p1[1].getUltAtk().getDamage(), true)
                )
            };

            Player[] freshP2 = new Player[] {
                new Player(
                    p2[0].getName(),
                    p2[0].getImagePath(),
                    p2[0].getMaxHp(),
                    new Attack(p2[0].getNrmlAtk().getPath(), p2[0].getNrmlAtk().getName(), p2[0].getNrmlAtk().getDamage(), false),
                    new Attack(p2[0].getUltAtk().getPath(), p2[0].getUltAtk().getName(), p2[0].getUltAtk().getDamage(), true)
                ),
                new Player(
                    p2[1].getName(),
                    p2[1].getImagePath(),
                    p2[1].getMaxHp(),
                    new Attack(p2[1].getNrmlAtk().getPath(), p2[1].getNrmlAtk().getName(), p2[1].getNrmlAtk().getDamage(), false),
                    new Attack(p2[1].getUltAtk().getPath(), p2[1].getUltAtk().getName(), p2[1].getUltAtk().getDamage(), true)
                )
            };

            BattleScene newScene = new BattleScene(stage, freshP1, freshP2);
            stage.setScene(newScene.getScene());
        });


        changePlayerItem.setOnAction(e -> {
            PlayerSelectionScene selectionScene = new PlayerSelectionScene(stage);
            stage.setScene(selectionScene.getScene());
        });

        quitItem.setOnAction(e -> stage.close());

        characterPane.getChildren().add(menuBtn);

        //text Box
        StackPane messageBox = new StackPane();
        messageBox.setLayoutX(300);
        messageBox.setLayoutY(60);
        messageBox.setPrefSize(400, 100);

        textBoardBackground = new Rectangle(400, 100);
        textBoardBackground.setFill(Color.BLACK);
        textBoardBackground.setOpacity(0.7);
        textBoardBackground.setArcWidth(20);   // rounded corners
        textBoardBackground.setArcHeight(20);

        messageLabel = new Label("Welcome to the battle!");
        messageLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");
        messageLabel.setWrapText(true);
        messageLabel.setAlignment(Pos.CENTER);
        messageLabel.setMaxWidth(380);
        messageLabel.setMaxHeight(90);
        messageLabel.setPrefWidth(380);
        messageLabel.setPrefHeight(90);


        // Set the position of the text box and label
        textBoardBackground.setLayoutX(300);
        textBoardBackground.setLayoutY(60);  // Place it below the health bars

        messageLabel.setLayoutX(320);
        messageLabel.setLayoutY(80);  // Align it inside the text box

        // Add the background and label to the root layout
        characterPane.getChildren().addAll(textBoardBackground, messageLabel);


        Font font = new Font(13);

        // === Change Button ===
        changeBtn = new Button();
        changeBtn.setText("Change");
        changeBtn.setFont(font);
        changeBtn.setMinWidth(70);
        //changeBtn.setMinSize(50, 100);
        //changeBtn.labelPaddingProperty();
        changeBtn.setOnAction(e -> {
            controller.switchPlayer(true);
            p1View.setImage(new Image(Paths.get(p1[controller.getSelectedPlayer()[0]].getImagePath()).toUri().toString()));
            updateHpLabels(p1, p2);
            updateButtonsForTurn();
        });
        changeBtn.setPrefWidth(100);



        // === Attack Button ===
        attackBtn = new Button();
        attackBtn.setText("Attack");
        attackBtn.setFont(font);
        attackBtn.setMinWidth(70);
        attackBtn.setOnAction(e -> {
            if(controller.isPlayer1()) {
                damageTransition(root, p1[controller.getSelectedPlayer()[0]].getNrmlAtk(), controller.isPlayer1());
                attacking(p1[controller.getSelectedPlayer()[0]], p2[controller.getSelectedPlayer()[1]], p1[controller.getSelectedPlayer()[0]].getNrmlAtk());
                updateHpLabels(p1, p2);
                controller.switchTurn();
                updateButtonsForTurn();
                showEndScreen(root);
                p1[controller.getSelectedPlayer()[0]].getUltAtk().updateCooldown(false);
                startBotLoop(root, p1, p2);
            }
        });

        // === Ultimate Attack Button ===
        ultimateBtn = new Button();
        ultimateBtn.setText("ULTIMATE");
        ultimateBtn.setFont(font);
        ultimateBtn.setMinWidth(85);
        ultimateBtn.setDisable(true);
        //controller.isPlayer1() &&
        ultimateBtn.setOnAction(e -> {
            if(controller.isPlayer1()){
                damageTransition(root, p1[controller.getSelectedPlayer()[0]].getUltAtk() , controller.isPlayer1());
                attacking(p1[controller.getSelectedPlayer()[0]], p2[controller.getSelectedPlayer()[1]], p1[controller.getSelectedPlayer()[0]].getUltAtk());
                updateHpLabels(p1, p2);
                controller.switchTurn();
                updateButtonsForTurn();
                showEndScreen(root);
                p1[controller.getSelectedPlayer()[0]].getUltAtk().updateCooldown(true);
                startBotLoop(root, p1, p2);
        }});

        HBox layout = new HBox(7); // spacing between banner and buttons
        //layout.setAlignment(Pos.CENTER);
        layout.setLayoutY(530);
        layout.setLayoutX(10);
        layout.getChildren().addAll(changeBtn, attackBtn, ultimateBtn);


        root.getChildren().add(layout);

 //       startBotLoop(root, p1, p2);

    }
    private void showMessage(String message) {
        messageLabel.setText(message);
    }

    private void attacking(Player attacker, Player defender, Attack atk){
    String attackerElement = getElementFromPlayer(attacker);
    String defenderElement = getElementFromPlayer(defender);
    double multiplier = getEffectiveness(attackerElement, defenderElement);
    int damage = (int) Math.round(atk.getDamage() * multiplier);

    // Deal the damage
    controller.takeTurn(damage);

    String effMsg = multiplier == 1.1 ? " It's super effective!" :
                    multiplier == 0.9 ? " It's not very effective." : "";
    String message = attacker.getName() + " used " + atk.getName() + " and dealt " + damage + " damage!" + effMsg;

    // Show attack message first
    showMessage(message);

    // Then check if defender fainted and show another message
    if (defender.isFainted()) {
        PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
        pause.setOnFinished(e -> {
            showMessage(defender.getName() + " is fainted!");
        });
        pause.play();
    }
}

    private void startBotLoop(BorderPane root, Player[] p1, Player[] p2) {
        Random rand = new Random();
        int x = rand.nextInt(2);
        if (x==0){
            controller.switchPlayer(false);
            PauseTransition pauseSwi = new PauseTransition(Duration.millis(1200));
            pauseSwi.setOnFinished(evS -> {
            p2View.setImage(new Image(Paths.get(p2[controller.getSelectedPlayer()[1]].getImagePath()).toUri().toString()));
            });
            pauseSwi.play();
        }
        if(!(controller.isPlayer1())){
            if(controller.getPlayer2().isFainted()) {
                controller.switchPlayer(false);
                PauseTransition pauseSw = new PauseTransition(Duration.millis(700));
                pauseSw.setOnFinished(evS -> {
                    p2View.setImage(new Image(Paths.get(p2[controller.getSelectedPlayer()[1]].getImagePath()).toUri().toString()));
                });
                pauseSw.play();
            }
            if(controller.getPlayer2().isFainted()) return;
            Timeline botTimeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> {
                //if (controller.getOpponent().getHp() <= 0 || controller.getCurrentPlayer().getHp() <= 0) return;

                Player bot = controller.getPlayer2();
                List<Attack> botAttacks = bot.getAttacks();
                Player player = controller.getPlayer1();
                Attack ultAtk = bot.getUltAtk();

                String attackerElement = getElementFromPlayer(bot);
                String defenderElement = getElementFromPlayer(player);

                List<Attack> availableAttacks = bot.getAvailabeAttacks();

                Attack chosenAttack = pickBestAttack(availableAttacks, attackerElement, defenderElement, player.getHp());
                if (chosenAttack == ultAtk) ultAtk.updateCooldown(true);
                else ultAtk.updateCooldown(false);

                damageTransition(root, chosenAttack, controller.isPlayer1());

                PauseTransition pause = new PauseTransition(Duration.millis(700));
                pause.setOnFinished(ev -> {
                    updateHpLabels(p1, p2);
                });
                pause.play();
                attacking(bot, player, chosenAttack);
                showEndScreen(root);
                controller.switchTurn();
                if (controller.getPlayer1().isFainted()){
                    controller.switchPlayer(true);
                    PauseTransition pauseSwi = new PauseTransition(Duration.millis(700));
                    pauseSwi.setOnFinished(evS -> {
                        p1View.setImage(new Image(Paths.get(p1[controller.getSelectedPlayer()[0]].getImagePath()).toUri().toString()));
                    });
                    pauseSwi.play();
                }
                updateButtonsForTurn();
        }));
        botTimeline.setCycleCount(1);
        botTimeline.play();
    }}

    private Attack pickBestAttack(List<Attack> attacks, String attackerElement, String defenderElement, int opponentHp) {
        Attack bestAttack = attacks.get(0);
        double bestScore = 0;

        for (Attack atk : attacks) {
            double multiplier = getEffectiveness(attackerElement, defenderElement);
            double potentialDamage = atk.getDamage() * multiplier;
            double score = potentialDamage;

            if (potentialDamage >= opponentHp) {
                score += 10;
            }

            score *= 0.9 + (Math.random() * 0.2);

            if (score > bestScore) {
                bestScore = score;
                bestAttack = atk;
            }
        }

        return bestAttack;
    }

    private void updateButtonsForTurn() {
        boolean isHumanTurn = controller.isPlayer1();
        boolean isSwitchNotPossible = p1[0].isFainted() || p1[1].isFainted();
        boolean GameOver = ((p1[0].isFainted() && p1[1].isFainted()) || p2[0].isFainted() && p2[1].isFainted());
        changeBtn.setDisable(!isHumanTurn || isSwitchNotPossible || GameOver);
        attackBtn.setDisable(!isHumanTurn || GameOver);
        ultimateBtn.setDisable(!isHumanTurn || !p1[controller.getSelectedPlayer()[0]].ultIsAvailable() || GameOver);
    }

    private void updateHpLabels(Player[] p1, Player[] p2) {
        double p1Hp1 = 200 * p1[0].getHp() / p1[0].getMaxHp();
        double p1Hp2 = 200 * p1[1].getHp() / p1[1].getMaxHp();
        double p2Hp1 = 200 * p2[0].getHp() / p2[0].getMaxHp();
        double p2Hp2 = 200 * p2[1].getHp() / p2[1].getMaxHp();
        p1HealthRect1.setWidth(p1Hp1);
        p1HealthRect2.setWidth(p1Hp2);
        p2HealthRect1.setWidth(p2Hp1);
        p2HealthRect2.setWidth(p2Hp2);
    }

    private String getElementFromPlayer(Player player) {
        String path = player.getImagePath().toLowerCase();
        if (path.contains("alien")) return "Water";
        if (path.contains("mummy")) return "Rock";
        if (path.contains("baum")) return "Grass";
        if (path.contains("stein")) return "Fire";
        return "Normal";
    }

    private double getEffectiveness(String attackerElement, String defenderElement) {
        return switch (attackerElement) {
            case "Fire" -> defenderElement.equals("Grass") ? 1.1 :
                    (defenderElement.equals("Water") || defenderElement.equals("Rock")) ? 0.9 : 1.0;
            case "Water" -> (defenderElement.equals("Fire") || defenderElement.equals("Rock")) ? 1.1 :
                    defenderElement.equals("Grass") ? 0.9 : 1.0;
            case "Grass" -> (defenderElement.equals("Water") || defenderElement.equals("Rock")) ? 1.1 :
                    defenderElement.equals("Fire") ? 0.9 : 1.0;
            case "Rock" -> defenderElement.equals("Fire") ? 1.1 :
                    (defenderElement.equals("Water") || defenderElement.equals("Grass")) ? 0.9 : 1.0;
            default -> 1.0;
        };
    }

    private void showEndScreen(BorderPane root) {
    if ((controller.getAllPlayers1()[0].isFainted()) && (controller.getAllPlayers1()[1].isFainted())) playerWon = false;
    else if ((controller.getAllPlayers2()[0].isFainted()) && (controller.getAllPlayers2()[1].isFainted())) playerWon = true;
    else return;

    String imagePath = playerWon
            ? "assets/victory.png"
            : "assets/defeat.png";

    ImageView endImage = new ImageView(new Image(Paths.get(imagePath).toUri().toString()));
    endImage.setPreserveRatio(true);
    endImage.setFitWidth(400);
    endImage.setFitHeight(400);

    // Restart Button
    Button newGameBtn = new Button("New Game");
    newGameBtn.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
    newGameBtn.setOnAction(e -> {
            PlayerSelectionScene selectionScene = new PlayerSelectionScene(sstage);
            sstage.setScene(selectionScene.getScene());
        });

    VBox endBox = new VBox(30, endImage, newGameBtn);
    endBox.setAlignment(Pos.CENTER);
    endBox.setPrefSize(1000, 600);
    endBox.setStyle("-fx-background-color: rgba(0,0,0,0.6);");

    PauseTransition pause = new PauseTransition(Duration.millis(700));
    pause.setOnFinished(ev -> {
        p1View.setVisible(false);
        p2View.setVisible(false);
        root.setCenter(endBox);
    });
    pause.play();
}

    public Scene getScene() {
        return scene;
    }
}
