import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.util.Duration;
import View.MenuScene;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        MusicPlayer.playBackgroundMusic();
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

class MusicPlayer {

    private static MediaPlayer mediaPlayer;

    public static void playBackgroundMusic() {
        String musicPath = MusicPlayer.class.getResource("/assets/bcgMusik.mp3").toExternalForm();
        Media media = new Media(musicPath);
        mediaPlayer = new MediaPlayer(media);

        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop the music
        mediaPlayer.setVolume(0.5); // 0.0 to 1.0
        mediaPlayer.play();
    }

    public static void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }
}
