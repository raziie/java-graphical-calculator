package ir.ac.kntu.gui;

import ir.ac.kntu.logic.Calculator;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.HashMap;

/**
 * @author sina rostami
 */
public class CalculatorGui {
    private static final String[][] TEMPLATE = {
            { "7", "8", "9", "+" },
            { "4", "5", "6", "-" },
            { "1", "2", "3", "/" },
            { "!", "0", "=", "*" }
    };

    private static final String[][] PICTURES = {
            { "file:src/main/resources/buttons/7.jpg", "file:src/main/resources/buttons/8.jpg",
                "file:src/main/resources/buttons/9.jpg", "file:src/main/resources/buttons/plus.jpg" },
            { "file:src/main/resources/buttons/4.jpg", "file:src/main/resources/buttons/5.jpg",
                "file:src/main/resources/buttons/6.jpg", "file:src/main/resources/buttons/minus.jpg" },
            { "file:src/main/resources/buttons/1.jpg", "file:src/main/resources/buttons/2.jpg",
                "file:src/main/resources/buttons/3.jpg", "file:src/main/resources/buttons/divide.jpg" },
            { "file:src/main/resources/buttons/clear.jpg", "file:src/main/resources/buttons/0.jpg",
                "file:src/main/resources/buttons/equal.jpg", "file:src/main/resources/buttons/multiply.jpg" }
    };

    private HashMap<String, Button> buttons = new HashMap<>();

    private TextField textField;

    private String operand1 = "";

    private String operand2 = "";

    private String operator = "";

    public CalculatorGui() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                buttons.put(TEMPLATE[i][j], new Button());
            }
        }
        textField = new TextField();
        textField.setFont(Font.font(40));
        textField.setPromptText("0");
        textField.setFocusTraversable(false);
        textField.setEditable(false);
        textField.setPrefHeight(100);
        adjustButtons();
    }

    public void setEventsHandlers() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (TEMPLATE[i][j].matches("[0-9]")) {
                    int finalI = i;
                    int finalJ = j;
                    buttons.get(TEMPLATE[i][j]).setOnMouseClicked(e -> addNumber(TEMPLATE[finalI][finalJ]));
                } else if (TEMPLATE[i][j].equals("!")) {
                    buttons.get(TEMPLATE[i][j]).setOnMouseClicked(e -> clear());
                } else if (TEMPLATE[i][j].equals("=")) {
                    buttons.get(TEMPLATE[i][j]).setOnMouseClicked(e -> equal());
                } else {
                    int finalI1 = i;
                    int finalJ1 = j;
                    buttons.get(TEMPLATE[i][j]).setOnMouseClicked(e -> addOperator(TEMPLATE[finalI1][finalJ1]));
                }
            }
        }
    }

    public void addNodesToPane(VBox root, TilePane pane) {
        root.getChildren().add(textField);
        pane.setPrefRows(4);
        pane.setPrefColumns(4);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                buttons.get(TEMPLATE[i][j]).setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                VBox.setVgrow(buttons.get(TEMPLATE[i][j]), Priority.ALWAYS);
                pane.getChildren().add(buttons.get(TEMPLATE[i][j]));
            }
        }
        VBox.setVgrow(pane, Priority.ALWAYS);
        root.getChildren().add(pane);
    }

    private void adjustButtons() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Image img = new Image(PICTURES[i][j]);
                ImageView view = new ImageView(img);
                view.setFitHeight(55);
                view.setPreserveRatio(true);
                buttons.get(TEMPLATE[i][j]).setGraphic(view);
            }
        }
    }

    public void addNumber(String number) {
        if (operator.equals("")) {
            operand1 += number;
            textField.setText(operand1);
        } else {
            operand2 += number;
            textField.setText(operand1 + " " + nameToSign(operator) + " " + operand2);
        }
    }

    public void clear() {
        operand1 = "";
        operand2 = "";
        operator = "";
        textField.setText("0");
    }

    public void equal() {
        double result = Calculator.calculate(new StringBuilder(operand1), new StringBuilder(operand2),
                new StringBuilder(operator));
        int intResult = (int) result;
        if (result - intResult == 0) {
            textField.setText(String.valueOf(intResult));
            operand1 = String.valueOf(intResult);
        } else {
            textField.setText(String.valueOf(result));
            operand1 = String.valueOf(result);
        }
        operator = "";
        operand2 = "";
    }

    public void addOperator(String theOperator) {
        if (operator.equals("")) {
            switch (theOperator) {
                case "+" -> operator += "PLUS";
                case "-" -> operator += "MINUS";
                case "*" -> operator += "MULTIPLY";
                case "/" -> operator += "DIVIDE";
                default -> operator += "UNKNOWN";
            }
            textField.setText(operand1 + " " + nameToSign(operator));
        }
    }

    public String nameToSign(String theOperator) {
        String result = "";
        switch (theOperator) {
            case "PLUS" -> result = "+";
            case "MINUS" -> result = "-";
            case "MULTIPLY" -> result = "x";
            case "DIVIDE" -> result = "÷";
            default -> {
            }
        }
        return result;
    }
}
