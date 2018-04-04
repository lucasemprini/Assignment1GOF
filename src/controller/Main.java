package controller;

import controller.concurrency.Game;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.utility.StringUtilities;
import view.InitialWindowLayoutController;
import view.LayoutController;

import java.io.IOException;

public class Main extends Application {

    private static final String LAYOUT_PATH = "/view/game.fxml";
    private static final String WINDOW_TITLE = "Game Of Life";

    private static final int TESTROWS = 1000;
    private static final int TESTCOLUMNS = 1000;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        InitialWindowLayoutController initialWindow = new InitialWindowLayoutController();
        Scene scene = initialWindow.selectDimensionScene();
        primaryStage.setTitle(WINDOW_TITLE);
        primaryStage.setScene(scene);

        this.addListenerToOkButton(initialWindow.setUpOkButton(),
                initialWindow,
                primaryStage);

        primaryStage.show();
    }

    /**
     * Metodo che aggiunge il Listener al Button della finestra iniziale.
     * @param okButton il Bottone della finestra iniziale.
     * @param initialWindow il controller della finestra iniziale.
     * @param primaryStage il primaryStage dell'applicazione.
     */
    private void addListenerToOkButton(final Button okButton, final InitialWindowLayoutController initialWindow,
                                       final Stage primaryStage) {
         okButton.setOnAction( event ->  {
             if(StringUtilities.isStringNumeric(initialWindow.getRowsField().getText()) &&
                     StringUtilities.isStringNumeric(initialWindow.getColumnsField().getText())) {
                final int numRows = Integer.parseInt(initialWindow.getRowsField().getText());
                final int numColumns = Integer.parseInt(initialWindow.getColumnsField().getText());

                 Game game;
                 FXMLLoader loader;

                 try {
                     game = initGame(getNumberOfThreads(), numRows, numColumns);
                     loader = initGui(primaryStage);
                     final LayoutController lc = loader.getController();
                     lc.setModel(game);
                 } catch (IOException | InterruptedException e) {
                     e.printStackTrace();
                 }
             }
         });
    }

    /**
     * Metodo che restituisce il numero di Thread su cui suddividere il Game in base
     * al numero di processori della macchina su cui sta eseguendo l'applicazione.
     * @return il numero di Thread, coerente con il numero di processori.
     */
    private static int getNumberOfThreads() {
        return Runtime.getRuntime().availableProcessors() + 1;
    }

    /**
     * Metodo che inizializza la classe Game.
     * @param numThreads il numero dei Thread voluti.
     * @param numRows    il numero delle righe.
     * @param numColumns il numero delle colonne.
     * @return il Game.
     * @throws InterruptedException se il Thread viene interrotto.
     */
    private Game initGame(final int numThreads, final int numRows, final int numColumns) throws InterruptedException {

        final Game game = new Game(numThreads, numRows, numColumns);
        game.setupSemaphores();
        return game;
    }

    /**
     * Metodo per inizializzare la GUI dell'applicazione.
     * @param primaryStage lo stage primario della GUI JavaFX.
     * @return il FXMLLoader.
     */
    private FXMLLoader initGui(final Stage primaryStage) throws IOException {
        final FXMLLoader loader = new FXMLLoader(getClass().getResource(LAYOUT_PATH));
        final Parent root = loader.load();
        primaryStage.setTitle(WINDOW_TITLE);
        primaryStage.setScene(new Scene(root));
        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
        return loader;
    }
}
