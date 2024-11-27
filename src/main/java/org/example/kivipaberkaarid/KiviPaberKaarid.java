package org.example.kivipaberkaarid;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KiviPaberKaarid extends Application {


    private int sinuVastus = 0;
    private int vastaseVastus = 0;

    private Text text = new Text("Sina: " + sinuVastus);
    private Text text2 = new Text("Vastane: " + vastaseVastus);

    private List<Stage> stages = new ArrayList<>();

    @Override
    public void start(Stage lava) {
        stages.add(lava);

        VBox juur = new VBox(10);
        juur.setSpacing(20);
        juur.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Text tervitus = new Text("sup!");
        tervitus.setFont(Font.font("Stencil", 20));

        Label newlabel = new Label("Mitme võiduni tahad mängida: ");
        Spinner<Integer> newSpinner = new Spinner<>();
        newSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50, 3));

        HBox spinnerBox = new HBox(10, newlabel, newSpinner);
        spinnerBox.setStyle("-fx-alignment: center;");

        Button nupp = new Button("Alustame!");
        nupp.setOnMouseClicked(event -> {
            int spinneriVastus = newSpinner.getValue();
            System.out.println("Valitud võiduarv: " + spinneriVastus);
            try {
                edasi(spinneriVastus);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            lava.close();
        });

        juur.getChildren().addAll(tervitus, spinnerBox, nupp);

        Scene stseen = new Scene(juur, 500, 300);

        lava.setScene(stseen);
        lava.setTitle("Tervitus!");
        lava.show();
    }

    private void edasi(int spinneriVastus) throws FileNotFoundException {
        Stage lava = new Stage();
        stages.add(lava);

        if (sinuVastus >= spinneriVastus) {
            lopp("Youu are the champion!");
            return;
        }
        if (vastaseVastus >= spinneriVastus) {
            lopp("Bohooo, oled luuser");
            return;
        }

        text.setFont(Font.font("Stencil", 20));
        VBox topLeftBox = new VBox(text);
        topLeftBox.setAlignment(Pos.TOP_LEFT);

        text2.setFont(Font.font("Stencil", 20));
        VBox topRightBox = new VBox(text2);
        topRightBox.setAlignment(Pos.TOP_RIGHT);

        HBox topBox = new HBox(20, topLeftBox, topRightBox);
        topBox.setAlignment(Pos.TOP_CENTER);

        Image pilt1 = new Image(getClass().getResource("/kivi.jpg").toExternalForm());
        ImageView pilt1View = new ImageView(pilt1);
        pilt1View.setFitWidth(100);
        pilt1View.setFitHeight(100);
        pilt1View.setPreserveRatio(true);

        pilt1View.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            System.out.println("KIVI");
            kivi(spinneriVastus);
        });

        Image pilt2 = new Image(getClass().getResource("/paber.jpg").toExternalForm());
        ImageView pilt2View = new ImageView(pilt2);
        pilt2View.setFitWidth(100);
        pilt2View.setFitHeight(100);
        pilt2View.setPreserveRatio(true);

        pilt2View.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            System.out.println("PABER");
            paber(spinneriVastus);
        });

        Image pilt3 = new Image(getClass().getResource("/käärid.jpg").toExternalForm());
        ImageView pilt3View = new ImageView(pilt3);
        pilt3View.setFitWidth(100);
        pilt3View.setFitHeight(100);
        pilt3View.setPreserveRatio(true);

        pilt3View.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            System.out.println("KÄÄRID");
            kaarid(spinneriVastus);
        });

        HBox piltBox = new HBox(10, pilt1View, pilt2View, pilt3View);
        piltBox.setAlignment(Pos.CENTER);

        BorderPane root = new BorderPane();
        root.setTop(topBox);
        BorderPane.setAlignment(topBox, Pos.TOP_CENTER);
        root.setBottom(piltBox);
        BorderPane.setAlignment(piltBox, Pos.CENTER);

        Scene stseen = new Scene(root, 400, 200);
        lava.setScene(stseen);
        lava.show();
    }

    private void kivi(int spinneriVastus) {
        processSelection("kivi", spinneriVastus);
    }

    private void paber(int spinneriVastus) {
        processSelection("paber", spinneriVastus);
    }

    private void kaarid(int spinneriVastus) {
        processSelection("käärid", spinneriVastus);
    }

    private void lopp(String tekst) {
        for (Stage stage : stages) {
            if (stage.isShowing()) {
                stage.close();
            }
        }

        Text tekstFondiga = new Text(tekst);
        tekstFondiga.setFont(Font.font("Stencil", 20));
        Text tekst2 = new Text(sinuVastus + " - " + vastaseVastus);
        tekst2.setFont(Font.font("Stencil", 20));

        Stage lava = new Stage();

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        Text lopetame = new Text(tekst);
        vBox.getChildren().addAll(tekstFondiga, tekst2);

        Scene stseen = new Scene(vBox, 600, 400);
        lava.setScene(stseen);
        lava.show();

        stages.add(lava);
    }

    private int getRandomNumber() {
        Random random = new Random();
        return random.nextInt(3);
    }

    private void processSelection(String playerChoice, int spinneriVastus) {
        Stage lava = new Stage();
        stages.add(lava);

        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);

        HBox hbox = new HBox(20);
        hbox.setAlignment(Pos.CENTER);

        // Player section
        VBox playerBox = new VBox(10);
        playerBox.setAlignment(Pos.CENTER);

        Text playerText = new Text("Sina valisid " + playerChoice);
        playerText.setFont(Font.font("Stencil", 20));
        Image playerImage = new Image(getClass().getResource("/" + playerChoice + ".jpg").toExternalForm());
        ImageView playerImageView = new ImageView(playerImage);
        playerImageView.setFitWidth(100);
        playerImageView.setFitHeight(100);
        playerImageView.setPreserveRatio(true);

        playerBox.getChildren().addAll(playerText, playerImageView);

        // VS text
        Text vsText = new Text("VS");
        vsText.setFont(Font.font("Stencil", 20));
        //vsText.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        // Opponent section
        VBox opponentBox = new VBox(10);
        opponentBox.setAlignment(Pos.CENTER);

        String[] choices = {"kivi", "paber", "käärid"};
        int randomNum = getRandomNumber();
        String opponentChoice = choices[randomNum];

        Text opponentText = new Text("Vastane valis " + opponentChoice);
        opponentText.setFont(Font.font("Stencil", 20));
        Image opponentImage = new Image(getClass().getResource("/" + opponentChoice + ".jpg").toExternalForm());
        ImageView opponentImageView = new ImageView(opponentImage);
        opponentImageView.setFitWidth(100);
        opponentImageView.setFitHeight(100);
        opponentImageView.setPreserveRatio(true);

        opponentBox.getChildren().addAll(opponentText, opponentImageView);


        Button nupp = new Button("OK, lämme mängu tagasi");
        nupp.setOnAction(actionEvent -> lava.close());


        hbox.getChildren().addAll(playerBox, vsText, opponentBox);
        root.getChildren().addAll(hbox, nupp);

        if (playerChoice.equals("kivi") && opponentChoice.equals("käärid") ||
                playerChoice.equals("paber") && opponentChoice.equals("kivi") ||
                playerChoice.equals("käärid") && opponentChoice.equals("paber")) {
            sinuVastus++;
        } else if (!playerChoice.equals(opponentChoice)) {
            vastaseVastus++;
        }

        updateScores();

        Scene stseen = new Scene(root, 600, 400);
        lava.setScene(stseen);
        lava.show();

        Timeline spinTimeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(playerImageView.rotateProperty(), 0), new KeyValue(opponentImageView.rotateProperty(), 0)),
                new KeyFrame(Duration.seconds(1), new KeyValue(playerImageView.rotateProperty(), 360), new KeyValue(opponentImageView.rotateProperty(), 360))
        );

        spinTimeline.setCycleCount(1);
        spinTimeline.setOnFinished(event -> {
            PauseTransition paus = new PauseTransition(Duration.seconds(1));
            paus.setOnFinished(event2 -> checkGameEnd(spinneriVastus));
            paus.play();
        });
        spinTimeline.play();
    }


    private void checkGameEnd(int spinneriVastus) {
        if (sinuVastus >= spinneriVastus) {
            lopp("Youu are the champion!");
        } else if (vastaseVastus >= spinneriVastus) {
            lopp("Bohooo, oled luuser");
        }
    }

    private void updateScores() {
        text.setText("Sina: " + sinuVastus);
        text2.setText("Vastane: " + vastaseVastus);
    }

    public static void main(String[] args) {
        launch();
    }
}
