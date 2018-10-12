package sample.controller;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.reactfx.Subscription;
import sample.Constantes.Configs;
import java.io.File;
import java.time.Duration;
import static sample.Constantes.Configs.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller extends Application {
    private Stage stage;
    @FXML
    private HBox paneSote;
    @FXML
    private TextArea txtConsola;

    CodeArea codeArea = new CodeArea();

    @FXML
    protected void initialize() {
        // add line numbers to the left of area
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
        codeArea.replaceText(0, 0, sampleCode);
        codeArea.setPrefSize(800, 500);
        Subscription cleanupWhenNoLongerNeedIt = codeArea
                .multiPlainChanges()
                .successionEnds(Duration.ofMillis(500))
                .subscribe(ignore -> codeArea.setStyleSpans(0, computeHighlighting(codeArea.getText())));
        HBox.setHgrow(codeArea, Priority.ALWAYS);
        paneSote.getChildren().add(codeArea);
    }

    public void evtSalir(ActionEvent evento) {
        System.exit(0);
    }

    public void evtAbrir(ActionEvent evento) {
        FileChooser of = new FileChooser();
        of.setTitle("Abrir Archivos Compilador");
        FileChooser.ExtensionFilter filtro = new FileChooser.ExtensionFilter("Archivos.compilador", "*.compilador");
        of.getExtensionFilters().add(filtro);
        File file = of.showOpenDialog(stage);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
    }

    public void evtEjecutar(ActionEvent evento) {
        compilar();
    }//LLAVE EJECUTAR

    public void compilar() {
        txtConsola.setText("");
        long tInicial=System.currentTimeMillis();

        String texto = codeArea.getText();
        String[] renglones = texto.split("\\n");

        for (int x = 0; x < renglones.length; x++) {
            boolean bandera=false;
            if(!renglones[x].trim().equals("")) {
                for (int y = 0; y < Configs.EXPRESIONES.length && bandera == false; y++) {
                    Pattern patron = Pattern.compile(Configs.EXPRESIONES[y]);
                    Matcher matcher = patron.matcher(renglones[x]);
                    if (matcher.matches()) {
                        bandera = true;

                    }//LLAVE IF
                }//LLAVE FOR Y
                if (bandera == false) {
                    txtConsola.setText(txtConsola.getText() + " \n" + "Error de sintaxis en la línea" + (x + 1));
                }//LLAVE IF BANDERA
            }//LLAVE IF RENGLONES
            }//LLAVE FOR X
        long tFinal=System.currentTimeMillis()-tInicial;
        txtConsola.setText(txtConsola.getText()+"\n"+
                "Compilado en : "+tFinal+" Milisegundos");
    }
}