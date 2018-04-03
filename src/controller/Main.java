package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static final String PRESENTATION_STRING ="Select your preferred dimensions for the Game Matrix: ";
    private static final String ROWSLABEL ="Rows: ";
    private static final String COLUMNSLABEL ="Columns: ";
    private static final String OKTEXT ="OK: ";
    private static final String WINDOWTITLE = "Game Of Life";

    private final BorderPane rootBorder = new BorderPane();
    private final FlowPane flowPane = new FlowPane();
    private final TextField rowsField = new TextField();
    private final TextField columnsField = new TextField();
    private final Label presentationLabel = new Label(PRESENTATION_STRING);
    private final Label rowsLabel = new Label(ROWSLABEL);
    private final Label columnsLabel = new Label(COLUMNSLABEL);
    private final Button dimensionsChosen = new Button(OKTEXT);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Scene scene = selectDimensionScene(primaryStage);
        //Parent rootBorder = FXMLLoader.load(getClass().getResource("game.fxml"));

        primaryStage.setTitle(WINDOWTITLE);
        //primaryStage.setScene(new Scene(rootBorder));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Metodo che setta la finestra iniziale di scelta dimensioni.
     * @param primaryStage il primary stage.
     * @return la finestra in questione.
     */
    private Scene selectDimensionScene(final Stage primaryStage){
        this.setUpOkButton(primaryStage);
        this.setUpFlowPane();
        this.addPaddingInsets();
        this.setUpBorderPane();
        return new Scene(rootBorder);
    }

    /**
     * Metodo che setta i vari parametri grafici del bottone della finestra iniziale.
     * @param primaryStage il primary stage.
     */
    private void setUpOkButton(final Stage primaryStage) {
        dimensionsChosen.prefWidthProperty().bind(rootBorder.widthProperty());
        dimensionsChosen.setOnAction( event ->  {
            try {
                primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("game.fxml"))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Metodo che setta un BorderPane della finestra iniziale.
     */
    private void setUpBorderPane() {
        rootBorder.setTop(presentationLabel);
        rootBorder.setLeft(flowPane);
        rootBorder.setBottom(dimensionsChosen);
    }

    /**
     * Metodo che setta un FlowPane della finestra iniziale.
     */
    private void setUpFlowPane() {
        flowPane.setOrientation(Orientation.HORIZONTAL);
        flowPane.getChildren().add(rowsLabel);
        flowPane.getChildren().add(rowsField);
        flowPane.getChildren().add(columnsLabel);
        flowPane.getChildren().add(columnsField);
    }

    /**
     * Metodo che aggiusta i padding delle componenti grafiche della finestra iniziale.
     */
    private void addPaddingInsets() {
        rowsLabel.setPadding(new Insets(0, 5, 0, 5));
        rowsField.setPadding(new Insets(0, 5, 0, 5));
        columnsField.setPadding(new Insets(0, 5, 0, 5));
        columnsLabel.setPadding(new Insets(0, 5, 0, 5));
        flowPane.setPadding(new Insets (10, 0, 10, 0));
        rootBorder.setPadding(new Insets(10, 20, 10, 20));
    }
}
