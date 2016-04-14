package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.function.Function;

// Java 8 code
public class CartesianPlot extends Application {
    @FXML
    public boolean saveAsPNG(Plot plot) {
        WritableImage image = plot.snapshot(new SnapshotParameters(), null);

        // TODO: probably use a file chooser here
        File file = new File("plot.png");

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
            return true;
        } catch (IOException e) {
            // TODO: handle exception here
            return false;
        }
    }

    /*
    @FXML
    public boolean saveAsJPG(Plot plot) {
        WritableImage image = plot.snapshot(new SnapshotParameters(), null);

        // TODO: probably use a file chooser here
        File file = new File("plot.jpg");

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "jpg", file);
            return true;
        } catch (IOException e) {
            // TODO: handle exception here
            return false;
        }
    }
     */

    /**
     * saveAsJPG = saves plot as .jpg image, auto corrects the alpha channel (pink toning issue - solved in Java 8)
     * @param plot
     * @return
     */
    @FXML
    public boolean saveAsJPG(Plot plot) {
        WritableImage image = plot.snapshot(new SnapshotParameters(), null);

        // TODO: probably use a file chooser here
        File file = new File("plot.jpg");


        // Get buffered image:
        BufferedImage procImage =
                SwingFXUtils.fromFXImage(
                        image,
                        null);

        // Remove alpha-channel from buffered image:
        BufferedImage imageRGB =
                new BufferedImage(
                        procImage.getWidth(),
                        procImage.getHeight(),
                        BufferedImage.OPAQUE);

        Graphics2D graphics = imageRGB.createGraphics();

        graphics.drawImage(
                procImage,
                0,
                0,
                null);

        graphics.dispose();

        try {
            ImageIO.write(imageRGB, "jpg", file);
            return true;
        } catch (IOException e) {
            // TODO: handle exception here
            return false;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage stage) {
        Axes axes = new Axes(
                700, 500,
                -8, 8, 1,
                -6, 6, 1
        );

        Plot plot = new Plot(
                x -> .25 * (x + 4) * (x + 1) * (x - 2),
                -8, 8, 0.1,
                axes
        );

        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(0,20,20,10));
        layout.setStyle("-fx-background-color: rgb(35, 39, 50);");


        layout.setCenter(plot);


        /**
         * MenuBar mainMenu = meniu principal aplicatie
         * */
        MenuBar mainMenu = new MenuBar();
        // --- Menu File
        Menu menuFile = new Menu("File");

        MenuItem exitMenuFile = new MenuItem("Exit");
        exitMenuFile.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                final Stage dialogStage = new Stage();
                dialogStage.setTitle("Exit");
                dialogStage.initModality(Modality.WINDOW_MODAL);

                Label exitLabel = new Label("Are you sure you want to exit?");
                exitLabel.setAlignment(Pos.BASELINE_CENTER);

                Button yesBtn = new Button("Yes");

                yesBtn.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent arg0) {
                        //dialogStage.close();
                        Platform.exit();

                    }
                });
                Button noBtn = new Button("No");

                noBtn.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent arg0) {
                        dialogStage.close();

                    }
                });

                HBox hBox = new HBox();
                hBox.setAlignment(Pos.BASELINE_CENTER);
                hBox.setPadding(new Insets(5));
                hBox.setSpacing(40.0);
                hBox.getChildren().addAll(yesBtn, noBtn);

                VBox vBox = new VBox();
                vBox.setSpacing(40.0);
                vBox.setPadding(new Insets(10));
                vBox.getChildren().addAll(exitLabel, hBox);

                dialogStage.setScene(new Scene(vBox));
                dialogStage.show();
            }
        });


        MenuItem savePNGMenuFile = new MenuItem("Save as .png");
        savePNGMenuFile.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //stocheaza rezultatul functiei
                boolean rezultatSalvare = saveAsPNG(plot);
            }
        });


        MenuItem saveJPGMenuFile = new MenuItem("Save as .jpg");
        saveJPGMenuFile.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //stocheaza rezultatul functiei
                boolean rezultatSalvare = saveAsJPG(plot);
            }
        });



        menuFile.getItems().addAll(savePNGMenuFile, saveJPGMenuFile, new SeparatorMenuItem(), exitMenuFile);


        // --- Menu Edit
        Menu menuEdit = new Menu("Edit");

        // --- Menu View
        Menu menuView = new Menu("View");
        mainMenu.getMenus().addAll(menuFile, menuEdit, menuView);

        HBox hboxTop = new HBox();
        layout.setMargin(hboxTop, new Insets(0));
        //hboxTop.setPadding(new Insets(-15));
        //mainMenu.setPadding(new Insets(0));

        hboxTop.getChildren().addAll(mainMenu);
        //hboxTop.setMargin(mainMenu, new Insets(0));
        layout.setTop(hboxTop);


        /**
         * hboxBottom components
         */
        Button submitPointButton = new Button("Click Me");
        //submitPointButton.onMousePressedProperty();
        TextField pointInput = new TextField();

        /**
        * HBox hboxBottom = meniu introducere puncte
        * */

        HBox hboxBottom = new HBox();
        hboxBottom.setPadding(new Insets(15,5,-10,5));

        hboxBottom.getChildren().addAll(pointInput, submitPointButton);
        layout.setBottom(hboxBottom);


        /**
         * stage attributes
         */
        stage.setTitle("y = \u00BC(x+4)(x+1)(x-2)");
        stage.setScene(new Scene(layout, Color.rgb(35, 39, 50)));
        stage.show();



    }

    class Axes extends Pane {
        private NumberAxis xAxis;
        private NumberAxis yAxis;

        public Axes(
                int width, int height,
                double xLow, double xHi, double xTickUnit,
                double yLow, double yHi, double yTickUnit
        ) {
            setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
            setPrefSize(width, height);
            setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);

            xAxis = new NumberAxis(xLow, xHi, xTickUnit);
            xAxis.setSide(Side.BOTTOM);
            xAxis.setMinorTickVisible(false);
            xAxis.setPrefWidth(width);
            xAxis.setLayoutY(height / 2);

            yAxis = new NumberAxis(yLow, yHi, yTickUnit);
            yAxis.setSide(Side.LEFT);
            yAxis.setMinorTickVisible(false);
            yAxis.setPrefHeight(height);
            yAxis.layoutXProperty().bind(
                    Bindings.subtract(
                            (width / 2) + 1,
                            yAxis.widthProperty()
                    )
            );

            getChildren().setAll(xAxis, yAxis);
        }

        public NumberAxis getXAxis() {
            return xAxis;
        }

        public NumberAxis getYAxis() {
            return yAxis;
        }
    }

    class Plot extends Pane {
        public Plot(
                Function<Double, Double> f,
                double xMin, double xMax, double xInc,
                Axes axes
        ) {
            Path path = new Path();
            path.setStroke(Color.ORANGE.deriveColor(0, 1, 1, 0.6));
            path.setStrokeWidth(2);

            path.setClip(
                    new Rectangle(
                            0, 0,
                            axes.getPrefWidth(),
                            axes.getPrefHeight()
                    )
            );

            double x = xMin;
            double y = f.apply(x);

            path.getElements().add(
                    new MoveTo(
                            mapX(x, axes), mapY(y, axes)
                    )
            );

            x += xInc;
            while (x < xMax) {
                y = f.apply(x);

                path.getElements().add(
                        new LineTo(
                                mapX(x, axes), mapY(y, axes)
                        )
                );

                x += xInc;
            }

            setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
            setPrefSize(axes.getPrefWidth(), axes.getPrefHeight());
            setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);

            getChildren().setAll(axes, path);
        }

        private double mapX(double x, Axes axes) {
            double tx = axes.getPrefWidth() / 2;
            double sx = axes.getPrefWidth() /
                    (axes.getXAxis().getUpperBound() -
                            axes.getXAxis().getLowerBound());

            return x * sx + tx;
        }

        private double mapY(double y, Axes axes) {
            double ty = axes.getPrefHeight() / 2;
            double sy = axes.getPrefHeight() /
                    (axes.getYAxis().getUpperBound() -
                            axes.getYAxis().getLowerBound());

            return -y * sy + ty;
        }
    }
}
