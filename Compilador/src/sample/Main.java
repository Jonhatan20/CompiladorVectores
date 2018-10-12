package sample;

import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.controller.Splash;


public class Main extends Application {
    public static int duracion = 50000;
    public static int steps = 1;

    @Override
    public void init() throws Exception {
        for (int i = 0; i < duracion; i++) {
            double progreso = (100 * i) / duracion;
            LauncherImpl.notifyPreloader(this, new Splash.ProgressNotification(progreso));

        }//LLAVE FOR
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("vistas/Principal.fxml"));
        primaryStage.setTitle("Compilador");
        primaryStage.setScene(new Scene(root, 970, 700));
        primaryStage.show();
    }

    public static void main(String[] args) {

        LauncherImpl.launchApplication(Main.class, Splash.class, args);
    }
}