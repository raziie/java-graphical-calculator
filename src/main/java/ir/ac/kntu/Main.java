package ir.ac.kntu;

import ir.ac.kntu.gui.CalculatorGui;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author Sina Rostami
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("calculator");
        CalculatorGui calculatorGui = new CalculatorGui();
        calculatorGui.setEventsHandlers();
        VBox root = new VBox();
        TilePane pane = new TilePane();
        pane.setAlignment(Pos.CENTER);
        calculatorGui.addNodesToPane(root, pane);
        Scene scene = new Scene(root, 300,350);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}