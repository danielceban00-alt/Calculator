/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculator;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 *
 * @author user
 */
public class Calculator extends Application {

    BorderPane root;
    GridPane grid;
    TextField display;

    Button[][] calc = new Button[4][4];

    String[][] buttonText = {
        {"7", "8", "9", "÷"},
        {"4", "5", "6", "×"},
        {"1", "2", "3", "−"},
        {".", "0", "=", "+"}
    };

    double firstNumber;
    double result;
    String operator;

    @Override
    public void start(Stage primaryStage) {

        root = new BorderPane();
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(15));

        display = new TextField();
        display.setFont(Font.font(
                "Consolas",
                FontWeight.BOLD,
                42
        ));
        display.setPadding(
                new Insets(10, 18, 10, 18)
        );
        display.setFocusTraversable(false);

        display.setEditable(false);
        display.setStyle(
                "-fx-background-color: #1e1e1e;"
                + "-fx-text-fill: white;"
                + "-fx-border-color: #555555;"
                + "-fx-border-width: 2;"
                + "-fx-border-radius: 8;"
                + "-fx-background-radius: 8;"
                + "-fx-focus-color: transparent;"
                + "-fx-faint-focus-color: transparent;"
        );
        display.setPrefHeight(100);

        root.setTop(display);
        root.setCenter(grid);

        Scene scene = new Scene(root, 360, 470);

        Rectangle back = new Rectangle(0, 0, scene.getWidth(), scene.getHeight());
        back.setFill(Color.BLACK);
        root.getChildren().add(back);
        back.toBack();

        for (int row = 0; row < calc.length; row++) {
            for (int column = 0; column < calc[row].length; column++) {

                Button button = new Button(
                        buttonText[row][column]
                );

                button.setOnAction(e -> {
                    String pressed = button.getText();

                    if (pressed.matches("[0-9]")
                            && display.getText().length() < 12) {

                        display.appendText(pressed);
                    }

                    if (pressed.equals(".")
                            && !display.getText().contains(".")) {

                        if (display.getText().isEmpty()) {
                            display.setText("0.");
                        } else {
                            display.appendText(".");
                        }
                    }

                    if (pressed.matches("[+−×÷]")
                            && !display.getText().isEmpty()
                            && operator == null) {

                        firstNumber = Double.parseDouble(
                                display.getText()
                        );

                        operator = pressed;

                        display.clear();
                    }

                    if (pressed.equals("=")
                            && operator != null
                            && !display.getText().isEmpty()) {

                        double secondNumber = Double.parseDouble(
                                display.getText()
                        );

                        result = 0;

                        switch (operator) {
                            case "+":
                                result = firstNumber + secondNumber;
                                break;

                            case "−":
                                result = firstNumber - secondNumber;
                                break;

                            case "×":
                                result = firstNumber * secondNumber;
                                break;

                            case "÷":
                                result = firstNumber / secondNumber;
                                break;
                        }

                        if (Double.isInfinite(result)
                                || Double.isNaN(result)) {

                            display.setFont(Font.font(
                                    "Arial",
                                    FontWeight.BOLD,
                                    19
                            ));

                            display.setAlignment(Pos.CENTER);

                            display.setText(
                                    "why u tryna calculate that gng 😭🙏"
                            );

                        } else {

                            display.setFont(Font.font(
                                    "Consolas",
                                    FontWeight.BOLD,
                                    42
                            ));

                            display.setAlignment(Pos.CENTER_RIGHT);

                            String resultText = String.valueOf(result);

                            if (resultText.endsWith(".0")) {
                                resultText = resultText.substring(
                                        0,
                                        resultText.length() - 2
                                );
                            }

                            if (resultText.length() > 12) {
                                display.setFont(Font.font(
                                        "Consolas",
                                        FontWeight.BOLD,
                                        22
                                ));
                            } else if (resultText.length() > 8) {
                                display.setFont(Font.font(
                                        "Consolas",
                                        FontWeight.BOLD,
                                        30
                                ));
                            } else {
                                display.setFont(Font.font(
                                        "Consolas",
                                        FontWeight.BOLD,
                                        42
                                ));
                            }

                            display.setAlignment(Pos.CENTER_RIGHT);
                            display.setText(resultText);
                            display.positionCaret(resultText.length());
                        }

                        operator = null;
                    }

                });

                button.setPrefSize(75, 75);

                button.setStyle(
                        "-fx-background-color: linear-gradient(#666666, #3f3f3f);"
                        + "-fx-text-fill: white;"
                        + "-fx-font-size: 24px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-background-radius: 12;"
                        + "-fx-border-radius: 12;"
                        + "-fx-border-color: #777777;"
                        + "-fx-border-width: 1;"
                        + "-fx-effect: dropshadow(gaussian, #111111, 0, 0, 0, 6);"
                        + "-fx-cursor: hand;"
                );

                button.setOnMousePressed(e -> {
                    button.setTranslateY(5);

                    button.setStyle(
                            "-fx-background-color: linear-gradient(#3f3f3f, #333333);"
                            + "-fx-text-fill: white;"
                            + "-fx-font-size: 24px;"
                            + "-fx-font-weight: bold;"
                            + "-fx-background-radius: 12;"
                            + "-fx-border-radius: 12;"
                            + "-fx-border-color: #555555;"
                            + "-fx-border-width: 1;"
                            + "-fx-effect: dropshadow(gaussian, #111111, 0, 0, 0, 1);"
                            + "-fx-cursor: hand;"
                    );
                });

                button.setOnMouseReleased(e -> {
                    button.setTranslateY(0);

                    button.setStyle(
                            "-fx-background-color: linear-gradient(#666666, #3f3f3f);"
                            + "-fx-text-fill: white;"
                            + "-fx-font-size: 24px;"
                            + "-fx-font-weight: bold;"
                            + "-fx-background-radius: 12;"
                            + "-fx-border-radius: 12;"
                            + "-fx-border-color: #777777;"
                            + "-fx-border-width: 1;"
                            + "-fx-effect: dropshadow(gaussian, #111111, 0, 0, 0, 6);"
                            + "-fx-cursor: hand;"
                    );
                });

                calc[row][column] = button;
                grid.add(button, column, row);

            }
        }

        primaryStage.setTitle("Calculator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
