package calculator;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Calculator extends Application {

    BorderPane root;
    GridPane grid;
    TextField display;

    Button[][] calc = new Button[5][4];

    String[][] buttonText = {
        {"√", "C", "⌫", "÷"},
        {"7", "8", "9", "×"},
        {"4", "5", "6", "−"},
        {"1", "2", "3", "+"},
        {".", "0", "=", "±"}
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
        display.setAlignment(Pos.CENTER_RIGHT);

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

        Scene scene = new Scene(
                root,
                360,
                560
        );

        Rectangle back = new Rectangle(
                0,
                0,
                scene.getWidth(),
                scene.getHeight()
        );

        calc();

        back.setFill(Color.BLACK);
        root.getChildren().add(back);
        back.toBack();

        primaryStage.setTitle("Calculator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    void calc() {
        for (int row = 0;
                row < calc.length;
                row++) {

            for (int column = 0;
                    column < calc[row].length;
                    column++) {

                Button button = new Button(
                        buttonText[row][column]
                );
                calcLogic(button);
                calcStyle(button);

                calc[row][column] = button;

                grid.add(
                        button,
                        column,
                        row
                );
            }
        }
    }

    void calcLogic(Button button) {
        button.setOnAction(e -> {
            String pressed
                    = button.getText();

            Logic09(pressed);

            LogicDot(pressed);

            addsubdivmul(pressed);

            sqrtLogic(pressed);

            switchSign(pressed);

            equalLogic(pressed);

            clear(pressed);

            backspace(pressed);

        });
    }

    void calcStyle(Button button) {
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
    }

    void Logic09(String pressed) {
        if (pressed.matches("[0-9]")
                && display.getText().length() < 12) {

            display.setFont(Font.font(
                    "Consolas",
                    FontWeight.BOLD,
                    42
            ));

            display.appendText(pressed);
        }
    }

    void LogicDot(String pressed) {
        if (pressed.equals(".")
                && !display.getText().contains(".")) {
            display.setFont(Font.font(
                    "Consolas",
                    FontWeight.BOLD,
                    42
            ));

            if (display.getText().isEmpty()) {
                display.setText("0.");
            } else {
                display.appendText(".");
            }
        }
    }

    void addsubdivmul(String pressed) {
        if (pressed.matches("[+−×÷]")
                && !display.getText().isEmpty()
                && operator == null) {

            firstNumber
                    = Double.parseDouble(
                            display.getText()
                    );

            operator = pressed;
            display.clear();
        }
    }

    void sqrtLogic(String pressed) {
        if (pressed.equals("√")
                && !display.getText().isEmpty()) {

            double number
                    = Double.parseDouble(
                            display.getText()
                    );

            if (number < 0) {
                display.setFont(Font.font(
                        "Arial",
                        FontWeight.BOLD,
                        19
                ));

                display.setAlignment(
                        Pos.CENTER
                );

                display.setText(
                        "Can't √ a negative number"
                );

            } else {
                result = Math.sqrt(number);

                display.setAlignment(
                        Pos.CENTER_RIGHT
                );

                String resultText
                        = String.valueOf(result);

                if (resultText.endsWith(".0")) {
                    resultText
                            = resultText.substring(
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

                display.setText(resultText);

                display.positionCaret(
                        resultText.length()
                );
            }
        }
    }

    void switchSign(String pressed) {
        if (pressed.equals("±")
                && !display.getText().isEmpty()) {
            double curent = Double.parseDouble(display.getText());

            result = -curent;

            String resultText
                    = String.valueOf(result);

            if (resultText.endsWith(".0")) {
                resultText
                        = resultText.substring(
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

            display.setText(resultText);

            display.positionCaret(
                    resultText.length()
            );

        }
    }

    void equalLogic(String pressed) {
        if (pressed.equals("=")
                && operator != null
                && !display.getText().isEmpty()) {

            double secondNumber
                    = Double.parseDouble(
                            display.getText()
                    );

            result = 0;

            switch (operator) {
                case "+":
                    result
                            = firstNumber
                            + secondNumber;
                    break;

                case "−":
                    result
                            = firstNumber
                            - secondNumber;
                    break;

                case "×":
                    result
                            = firstNumber
                            * secondNumber;
                    break;

                case "÷":
                    result
                            = firstNumber
                            / secondNumber;
                    break;
            }

            if (Double.isInfinite(result)
                    || Double.isNaN(result)) {

                display.setFont(Font.font(
                        "Arial",
                        FontWeight.BOLD,
                        19
                ));

                display.setAlignment(
                        Pos.CENTER
                );

                display.setText(
                        "why u tryna calculate that gng 😭🙏"
                );

            } else {
                display.setAlignment(
                        Pos.CENTER_RIGHT
                );

                String resultText
                        = String.valueOf(result);

                if (resultText.endsWith(".0")) {
                    resultText
                            = resultText.substring(
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

                display.setText(resultText);

                display.positionCaret(
                        resultText.length()
                );
            }

            operator = null;
        }
    }

    void clear(String pressed) {
        if (pressed.contains("C")) {
            display.clear();
            operator = null;
            result = 0;
        }

    }

    void backspace(String pressed) {
        if (pressed.contains("⌫")
                && !display.getText().isEmpty()) {
            String text = display.getText();

            text = text.substring(0, text.length() - 1);
            display.setText(text);
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
