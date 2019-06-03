package sample;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class scoresViewController {

    @FXML TextFlow bestScoresBoard;
    @FXML Text firstPlace;
    @FXML Text secondPlace;
    @FXML Text thirdPlace;
    @FXML Button mainMenu;

    @FXML ImageView starsImgLeft;
    @FXML ImageView starsImgRight;

    private ArrayList<String> scoresListString;
    MenuController menuController;
    Stage stage;
    Timeline timeline;

    scoresViewController(Timeline timeline, MenuController menuController, Stage stage){
        this.timeline = timeline;
        this.stage = stage;
        this.menuController = menuController;

        scoresListString = new ArrayList<>();
        checkBestScores();
        sortScoresList();
    }

    private void checkBestScores(){
        try {
            Scanner scanner = new Scanner(new File(
                    "/Users/cheap_ramen/Documents/college/Projekt_2_s18710/src/sample/otherFiles/solveTimes.txt"));
            while (scanner.hasNext()){
                String line = scanner.next();
                scoresListString.add(line);
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }

    }

    // solve time is transformed from string to int
    // number of seconds, minutes and milliseconds is summed
    private int solveTimeToInt(String expr){
        StringBuilder result = new StringBuilder();

        for(int i = 0; i < expr.length(); i++)
        {
            if(expr.charAt(i) == ':'){
                result.append(" ");
            }else {
                result.append(expr.charAt(i));
            }
        }

        Scanner timeScanner = new Scanner(result.toString());
        int sumOfNumbers = 0;
        //converts minutes to seconds
        sumOfNumbers += timeScanner.nextInt()*60;
        sumOfNumbers += timeScanner.nextInt();
        //converts miliseconds to seconds
        sumOfNumbers += timeScanner.nextInt()/1000;

        return sumOfNumbers;
    }

    //sorts the arraylist with scores
    private void sortScoresList(){
        scoresListString.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int o1Int = solveTimeToInt(o1);
                int o2Int = solveTimeToInt(o2);
                if(solveTimeToInt(o1) < solveTimeToInt(o2)){
                    return -1;
                }else if( o1Int == o2Int){
                    return 0;
                }else {
                    return 1;
                }
            }
        });

    }

    //updates text fields with values from the text file
    public void setTextFields(){

        if (scoresListString.size() == 1){
            firstPlace.setText(scoresListString.get(0));
            secondPlace.setText("-");
            thirdPlace.setText("-");
        }else if(scoresListString.size() == 2){
            firstPlace.setText(scoresListString.get(0));
            secondPlace.setText(scoresListString.get(1));
            thirdPlace.setText("-");
        }else if(scoresListString.size() >=3){
            firstPlace.setText(scoresListString.get(0));
            secondPlace.setText(scoresListString.get(1));
            thirdPlace.setText(scoresListString.get(2));
        }else{
            firstPlace.setText("-");
            secondPlace.setText("-");
            thirdPlace.setText("-");
        }

        for(int i=0, j =scoresListString.size()/2; j < scoresListString.size(); j++, i++){
            Text text = new Text((i+1)+". "+scoresListString.get(i) +
                    "        "+(j+2)+". "+scoresListString.get(j)+ System.lineSeparator());
            text.getStyleClass().add("Stylesheets/PuzzleMenusStyleSheet.css");
            text.setFill(Color.WHITE);
            text.setFont(Font.font(20));
            bestScoresBoard.getChildren().add(text);
            bestScoresBoard.setTextAlignment(TextAlignment.CENTER);
        }

    }

    public void setAnimations(){
        KeyFrame keyFrame1 = new KeyFrame(Duration.seconds(4),
                new KeyValue(firstPlace.yProperty(), firstPlace.getY()-20),
                new KeyValue(firstPlace.fillProperty(), Color.GOLD)
                //new KeyValue(firstPlace.fontProperty(), Font.font(40))
                );
        KeyFrame keyFrame2 = new KeyFrame(Duration.seconds(4),
                new KeyValue(secondPlace.yProperty(), secondPlace.getY()-20),
                new KeyValue(secondPlace.fillProperty(), Color.SILVER)
                //new KeyValue(secondPlace.fontProperty(), Font.font(40))
        );
        KeyFrame keyFrame3 = new KeyFrame(Duration.seconds(4),
                new KeyValue(thirdPlace.yProperty(), thirdPlace.getY()-20),
                new KeyValue(thirdPlace.fillProperty(), Color.SANDYBROWN)
               // new KeyValue(thirdPlace.fontProperty(), Font.font(40))
        );

        ScaleTransition starLeftTransition = new ScaleTransition(Duration.seconds(4), starsImgLeft);
        ScaleTransition starRightTransition = new ScaleTransition(Duration.seconds(4), starsImgRight);

        starLeftTransition.setByX(0.2f);
        starRightTransition.setByX(0.2f);
        starLeftTransition.setByY(0.2f);
        starRightTransition.setByY(0.2f);

        starLeftTransition.setCycleCount(30);
        starRightTransition.setCycleCount(30);
        starLeftTransition.setAutoReverse(true);
        starRightTransition.setAutoReverse(true);

        this.timeline.getKeyFrames().addAll(keyFrame1,keyFrame2,keyFrame3);
        this.timeline.setCycleCount(Animation.INDEFINITE);
        this.timeline.setAutoReverse(true);
        starLeftTransition.play();
        starRightTransition.play();
        this.timeline.play();
    }

    @FXML void setMainMenu(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxmlFiles/MainMenu.fxml"));
        fxmlLoader.setController(menuController);
        try {
            Scene scene = new Scene(fxmlLoader.load(),600,400);
            stage.setScene(scene);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
