import javafx.application.Application;
import javafx.stage.Stage;
import View.MenuScene;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        MenuScene menuScene = new MenuScene(primaryStage);
        primaryStage.setScene(menuScene.getScene());
        primaryStage.setTitle("Main Menu");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
