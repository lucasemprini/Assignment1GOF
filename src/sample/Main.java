package sample;

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

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Scene scene = selectDimensionScene(primaryStage);
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        primaryStage.setTitle("GameOfLife");
        //primaryStage.setScene(new Scene(root));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Metodo che setta la finestra iniziale di scelta dimensioni.
     * @param primaryStage il primary stage.
     * @return la finestra in questione.
     */
    private Scene selectDimensionScene(Stage primaryStage){
        BorderPane root = new BorderPane();
        FlowPane pane = new FlowPane();
        TextField rowsField = new TextField();
        TextField columnsField = new TextField();
        Label presentationLabel = new Label("Select your preferred dimensions for the Game Matrix: ");
        Label rowsLabel = new Label("Rows: ");
        Label columnsLabel = new Label("Columns: ");

        Button dimensionsChosen = new Button("OK");
        dimensionsChosen.prefWidthProperty().bind(root.widthProperty());
        dimensionsChosen.setOnAction( event ->  {
                try {
                    primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("sample.fxml"))));
                } catch (IOException e) {
                    e.printStackTrace();
                }
        });
        pane.setOrientation(Orientation.HORIZONTAL);
        pane.getChildren().add(rowsLabel);
        pane.getChildren().add(rowsField);
        pane.getChildren().add(columnsLabel);
        pane.getChildren().add(columnsField);
        rowsLabel.setPadding(new Insets(0, 5, 0, 5));
        rowsField.setPadding(new Insets(0, 5, 0, 5));
        columnsField.setPadding(new Insets(0, 5, 0, 5));
        columnsLabel.setPadding(new Insets(0, 5, 0, 5));
        pane.setPadding(new Insets (10, 0, 10, 0));
        root.setPadding(new Insets(10, 20, 10, 20));
        root.setTop(presentationLabel);
        root.setLeft(pane);
        root.setBottom(dimensionsChosen);
        return new Scene(root);
    }
}
