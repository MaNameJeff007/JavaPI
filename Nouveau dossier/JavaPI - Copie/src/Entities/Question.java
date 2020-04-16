/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import GUIController.EdTech.Exercice.ExerciceParCoursFrontController;
import Services.ExerciceService;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author oucema
 */
public class Question {

    // static variables (available to any instance)
    static Random rand = new Random();
    static int score = 0;
    static int questionCount = 1;
    static int questionIndex = 0;
    static int questionsCorrect = 0;
    static ArrayList<Button> buttons;
    final static String DELIMITER = ";";

    // instance specific variables
    String question;
    String correct;
    ArrayList<String> wrongs;
    Button randButton;

    // Constructor; creates "wrongs" ArrayList which stores the wrong/decoy answers
    public Question(String question, String correct, String wrong1, String wrong2, String wrong3) {
        this.question = question;
        this.correct = correct;
        this.wrongs = new ArrayList<>();
        this.wrongs.add(wrong1);
        this.wrongs.add(wrong2);
        this.wrongs.add(wrong3);
    }

    // Loads questions from a file and returns an ArrayList of Question objects
    public static ArrayList<Question> loadQuestions(int id)  {
        System.out.println("LENNNAAAAAAAAAAAAAAAAAAAA222222222222222");

        ArrayList<Question> questions = new ArrayList<>();
        try {
            ExerciceService s = new ExerciceService();
            List<Exercice> ex = s.getAllExercices();
            System.out.println("the id is : "+ id);
            for (Exercice e : ex) {
                questions.add(new Question(e.getQuestion(), e.getReponse(), e.getOption1(), e.getOption2(), e.getOption3()));
            }
        } catch (Exception e) {

        }
        return questions;
    }

    // Groups the 4 buttons in an ArrayList for easy access
    public static void setButtons(Button... buttonsArray) {
        buttons = new ArrayList<>(Arrays.asList(buttonsArray));
    }

    // questionIndex getter
    public static int getQuestionIndex() {
        return questionIndex;
    }

    // Takes input of label and buttons and displays the instance question and answers in the GUI
    public void displayQuestion(Label lbl, Label correctLabel) {

        ArrayList<Button> buttonsCopy = new ArrayList<>(buttons);

        for (Button b : buttonsCopy) {
            // Sets default style class for all buttons
            b.getStyleClass().remove("correct");
            b.getStyleClass().remove("wrong");
        }

        // Sets question label to instance variable "question"
        lbl.setText(this.question);
        correctLabel.setText("Question " + questionCount);

        // Generates random integer from 0 to 3
        int randInt = rand.nextInt(4);
        randButton = buttonsCopy.get(randInt);

        // Sets correct button to correct answer
        buttonsCopy.get(randInt).setText(this.correct);
        buttonsCopy.remove(randInt);
        // The correct button is removed so it is easier to set other buttons to wrong answers without checking
        // whether it is the right answer button or not

        // Shuffles "wrongs" ArrayList so buttons aren't in predictable order
        Collections.shuffle(this.wrongs);
        for (Button b : buttonsCopy) {
            // Sets button to wrong of the same index (not a problem due to the shuffle)
            b.setText(this.wrongs.get(buttonsCopy.indexOf(b)));
        }
    }

    // Checks whether the clicked button is the correct answer or not
    public void checkCorrect(Button b, ArrayList<Question> questions, Label scoreLabel) {

        // Checks if the button clicked in correct
        // If correct, increments "score" and "questionsCorrect" and changes Score label
        if (this.randButton == b) {
            b.getStyleClass().add("correct"); // Makes button green
            score += 10;
            scoreLabel.setText("Score: " + score);
            questionsCorrect += 1;
        } else {
            b.getStyleClass().add("wrong"); // Makes button red
            this.randButton.getStyleClass().add("correct");
        }

        // Checks if all questions have ben used; if yes, calls "finished" to show score and exit
        if (questions.size() == questionCount) {
            ExerciceParCoursFrontController a = new ExerciceParCoursFrontController();
            try{
            a.finished(score, questionsCorrect);
            }catch(Exception e){
                
            }
            }
        // If the program has reached this far, means that there more questions
        // Increments "questionCount" and "questionIndex" to keep track of the current question
        // Changes current question label
        questionCount += 1;
        questionIndex += 1;
    }
}
