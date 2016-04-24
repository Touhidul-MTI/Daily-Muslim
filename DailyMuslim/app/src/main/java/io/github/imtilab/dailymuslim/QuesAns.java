package io.github.imtilab.dailymuslim;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class QuesAns extends AppCompatActivity {

    String[] quesPartArray1, quesPartArray2;
    TextView tv_quesPart1, tv_quesPart2, tv_quesNo, tv_score;
    int countPart1 = 0, alreadySetPart2 = 0, quesNo = 1, yesCount = 0;
    double score = 0.0;
    String highScore = "0";
    MyDialog myDialog;

    MyDBHandler myDBHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ques_ans);
        getSupportActionBar().hide();

        myDBHandler = new MyDBHandler(this);

        //textView reference
        tv_quesPart1 = (TextView) findViewById(R.id.quesans_tv3);
        tv_quesPart2 = (TextView) findViewById(R.id.quesans_tv4);
        tv_quesNo = (TextView) findViewById(R.id.quesans_tv11);
        tv_score = (TextView) findViewById(R.id.quesans_tv22);

        //get array from resource
        quesPartArray1 = getResources().getStringArray(R.array.questionArrayPart1);
        quesPartArray2 = getResources().getStringArray(R.array.questionArrayPart2);
        setQuestions();
    }

    //ques ans setup
    public void setQuestions() {
        tv_quesPart1.setText(quesPartArray1[0]);
        tv_quesPart2.setText("");
        countPart1++;

        tv_quesNo.setText("1");
        tv_score.setText("0.0%");
    }

    //when clicked on no button
    public void clicked_quesans_b1_no(View view) {
        if (countPart1 >= quesPartArray1.length) {//check all ques are finished in array

            //database check, update and other stuffs
            databaseAndHighScoreActivity();

            //score to ScorePassToDialog
            ScorePassToDialog.highScore = highScore;
            ScorePassToDialog.todayScore = score;

            //score dialog popup
            myDialog = new MyDialog();
            myDialog.show(getFragmentManager(), "Alert Dialog");

        } else {
            tv_quesPart1.setText(quesPartArray1[countPart1]);
            tv_quesPart2.setText("");
            countPart1++;
            alreadySetPart2 = 0;//0 means part2 not set

            //set ques number
            quesNo++;
            tv_quesNo.setText(quesNo + "");

            //calc and set score
            setScore(yesCount);
        }
    }

    //when clicked on yes button
    public void clicked_quesans_b1_yes(View view) {

        if (countPart1 >= quesPartArray1.length) {//check all ques are finished in array
            yesCount++;//last q yes
            setScore(yesCount);

            //database check, update and other stuffs
            databaseAndHighScoreActivity();

            //score to ScorePassToDialog
            ScorePassToDialog.highScore = highScore;
            ScorePassToDialog.todayScore = score;
            //score dialog popup
            myDialog = new MyDialog();
            myDialog.show(getFragmentManager(), "Alert Dialog");

        } else {//if not finished the do
            doYesButtonStuffs();
        }
    }

    //clicked on yes button and still there is questions left
    public void doYesButtonStuffs() {
        if (alreadySetPart2 > 0) {//first check if part2 already set once, if so just go next q
            tv_quesPart1.setText(quesPartArray1[countPart1]);
            tv_quesPart2.setText("");
            countPart1++;
            alreadySetPart2 = 0;//0 means part2 not set

            //calc and set score
            yesCount++;//part 2 also yes
            setScore(yesCount);

        } else {//if part2 not set then do check if eligible to set
            int part2OrNextQuesPosition = checkQuesSequence();

            if (part2OrNextQuesPosition < 0) {//this q does not have part 2
                tv_quesPart1.setText(quesPartArray1[countPart1]);
                tv_quesPart2.setText("");
                countPart1++;
                alreadySetPart2 = 0;//0 means part2 not set

                //calc and set score
                yesCount++;//single part ques yes
                setScore(yesCount);
            } else {//does have part 2
                tv_quesPart2.setText(quesPartArray2[part2OrNextQuesPosition]);
                alreadySetPart2 = 1;//1 means part2 is set

                if(isScoreEligibleForFirstPart()){
                    //calc and set score
                    yesCount++;//part 1 yes
                }
                setScore(yesCount);
            }
        }
        //set ques number
        quesNo++;
        tv_quesNo.setText(quesNo + "");
    }
    //check if the 1st first ques is eligible to get score or not
    public boolean isScoreEligibleForFirstPart(){
        //only 5 times namaj are eligible to get score for first part q
        if(countPart1 == 3 || countPart1 == 4 || countPart1 == 5 || countPart1 == 6 || countPart1 == 7){
            return true;
        }return false;
    }
    //check the ques which are eligible to set as 2nd part of ques
    public int checkQuesSequence() {
        switch (countPart1) {
            case 3:
                return 0;
            case 4:
                return 1;
            case 5:
                return 2;
            case 6:
                return 3;
            case 7:
                return 4;
            case 9:
                return 5;
            case 13:
                return 6;
            case 14:
                return 7;
            case 18:
                return 8;
            case 19:
                return 9;
            default:
                return -1;
        }
    }

    public void setScore(int yesTotal) {
        score = Math.ceil((yesTotal * 100) / 28.0);
        tv_score.setText(score + "%");
    }

    public void databaseAndHighScoreActivity() {
        int rowNumber = myDBHandler.getNumberOfInsertedRow();
        if (rowNumber == 0) {//means first time makes a score
            highScore = score+"";
            String currentDate = getCurrentDate();
            myDBHandler.addScore(highScore, currentDate);
            myDBHandler.addScore(highScore, currentDate);

            //check if the data has been reset before by "-", need for first score row
        }else if(myDBHandler.getSingleScore_FromDB_ToStringArray_By_ID(2)[1].equals("-")){
            highScore = score+"";
            String currentDate = getCurrentDate();
            myDBHandler.updateScore(highScore, currentDate, 1);//1 is row number of high score
            myDBHandler.updateScore(highScore, currentDate, 2);//2 is row number of first score
        }else{
            String preHighScoreArray[] = myDBHandler.getSingleScore_FromDB_ToStringArray_By_ID(1);
            double preHighScore = Double.parseDouble(preHighScoreArray[0]);
            if(score > preHighScore){
                highScore = score+"";
                myDBHandler.updateScore(highScore, getCurrentDate(), 1);//1 is row number of high score
            }else{
                highScore = preHighScoreArray[0];
            }
        }
    }
    //setup and return current date as string
    public String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");//date format set
        Calendar cal = Calendar.getInstance();//get calendar date time
        Date today_Date = cal.getTime();//current date
        //fit format and auto date as string format and then return string
        return dateFormat.format(today_Date.getTime());
    }
}