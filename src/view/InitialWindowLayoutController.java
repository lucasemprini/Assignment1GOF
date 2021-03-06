package view;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

public class InitialWindowLayoutController {

    private static final String PRESENTATION_STRING ="Select your preferred dimensions for the Game Matrix: ";
    private static final String ROWS_LABEL ="Rows: ";
    private static final String COLUMNS_LABEL ="Columns: ";
    private static final String OK_TEXT ="OK";


    private final BorderPane rootBorder = new BorderPane();
    private final FlowPane flowPane = new FlowPane();

    private final TextField rowsField = new TextField();
    private final TextField columnsField = new TextField();
    private final Label presentationLabel = new Label(PRESENTATION_STRING);
    private final Label rowsLabel = new Label(ROWS_LABEL);
    private final Label columnsLabel = new Label(COLUMNS_LABEL);
    private final Button dimensionsChosen = new Button(OK_TEXT);


    /**
     * Metodo che setta la finestra iniziale di scelta dimensioni.
     * @return la finestra in questione.
     */
    public Scene selectDimensionScene(){
        this.setUpFlowPane();
        this.addPaddingInsets();
        this.setUpBorderPane();
        return new Scene(rootBorder);
    }

    /**
     * Metodo che setta i vari parametri grafici del bottone della finestra iniziale.
     */
    public Button setUpOkButton() {
        dimensionsChosen.prefWidthProperty().bind(rootBorder.widthProperty());
        return dimensionsChosen;
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

    /**
     * Getter method per il TextField delle righe.
     * @return il TextField delle righe.
     */
    public TextField getRowsField() {
        return rowsField;
    }

    /**
     * Getter method per il TextField delle colonne.
     * @return il TextField delle colonne.
     */
    public TextField getColumnsField() {
        return columnsField;
    }
}
