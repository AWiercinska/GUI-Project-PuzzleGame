package sample;

import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class scoresViewController {

    @FXML TextFlow bestScoresBoard;
    @FXML Text firstPlace;
    @FXML Text secondPlace;
    @FXML Text thirdPlace;

    private ArrayList<String> scoresListString;

    scoresViewController(){

        scoresListString = new ArrayList<>();
        checkBestScores();
        sortScoresList();
    }

    private void checkBestScores(){
        try {
            Scanner scanner = new Scanner(new File(
                    "/Users/cheap_ramen/Documents/college/Projekt_2_s18710/src/sample/cutImage/solveTimes.txt"));
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
    void sortScoresList(){
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
                    "        "+(j+1)+". "+scoresListString.get(j)+ System.lineSeparator());
            text.getStyleClass().add("Stylesheets/PuzzleMenusStyleSheet.css");
            text.setFill(Color.WHITE);
            text.setFont(Font.font(20));
            bestScoresBoard.getChildren().add(text);
            bestScoresBoard.setTextAlignment(TextAlignment.CENTER);
        }

    }


}
