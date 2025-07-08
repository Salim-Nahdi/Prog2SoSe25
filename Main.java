import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import View.MenuScene;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        Image icon = new Image(getClass().getResourceAsStream("/assets/banner.png"));
        primaryStage.getIcons().add(icon);
        MenuScene menuScene = new MenuScene(primaryStage);
        primaryStage.setScene(menuScene.getScene());
        primaryStage.setTitle("Main Menu");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
