package es.uah.matcomp.mp.ed.proyectofinal.extraordinariapecl.Age_Of_Threads;

import es.uah.matcomp.mp.ed.proyectofinal.extraordinariapecl.InterfazRemota;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.rmi.Naming;
import java.util.Map;

public class ClienteRemotoController {
    @FXML private Label labelCentroUrbano;
    @FXML private Label labelAldeanosRecursos;
    @FXML private Label labelGuerrerosRecursos;
    @FXML private Label labelBarbaros;
    @FXML private Label labelRecursos;

    private InterfazRemota servidor;

    @FXML
    public void initialize() {
        try {
            servidor = (InterfazRemota) Naming.lookup("//localhost/SimulacionRMI");
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> actualizarDatos()));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        } catch (Exception e) {
            labelCentroUrbano.setText("Error conectando al servidor");
        }
    }

    private void actualizarDatos() {
        try {
            labelCentroUrbano.setText("Centro urbano: " + servidor.getAldeanosCentroUrbano());

            Map<String, Integer> aldeanos = servidor.getAldeanosPorZona();
            labelAldeanosRecursos.setText("Aldeanos recursos: " + aldeanos);

            Map<String, Integer> guerreros = servidor.getGuerrerosPorZona();
            labelGuerrerosRecursos.setText("Guerreros recursos: " + guerreros);

            labelBarbaros.setText("Bárbaros: Campamento " + servidor.getBarbarosCampamento() +
                    " / Preparación " + servidor.getBarbarosPreparacion());

            Map<String, String> recursos = servidor.getEstadoRecursos();
            labelRecursos.setText("Recursos: " + recursos);
        } catch (Exception e) {
            labelCentroUrbano.setText("Fallo en actualización");
        }
    }

    @FXML
    private void activarEmergencia() {
        try {
            servidor.activarEmergencia();
        } catch (Exception ignored) {}
    }

    @FXML
    private void pausarReanudar() {
        try {
            servidor.pausarReanudarSimulacion();
        } catch (Exception ignored) {}
    }
}