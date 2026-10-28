package hotelapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage escenario) throws Exception {
        FXMLLoader cargador = new FXMLLoader(
                Main.class.getResource("/hotelapp/vista.fxml"));

        Scene escena = new Scene(cargador.load(), 650, 500);

        escenario.setTitle("HotelApp - Sistema de reservas");
        escenario.setScene(escena);
        escenario.setMinWidth(500);
        escenario.setMinHeight(400);
        escenario.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
