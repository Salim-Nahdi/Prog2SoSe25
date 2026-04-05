Requirements

Java JDK 21 or newer
OpenJFX 21 or newer
Make sure JavaFX is downloaded and extracted on your system.
Make sure the JavaFX version matches your Java version.


How to compile

javac --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml,javafx.media Main.java Model/*.java View/*.java Controller/*.java


How to run

javac --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml,javafx.media Main.java Model/*.java View/*.java Controller/*.java