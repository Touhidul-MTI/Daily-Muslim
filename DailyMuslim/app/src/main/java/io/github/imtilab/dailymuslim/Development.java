package io.github.imtilab.dailymuslim;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Development extends AppCompatActivity {

    TextView tv_highScore, tv_hsDate, tv_firstScore, tv_fsDate;
    MyDBHandler myDBHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_development);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myDBHandler = new MyDBHandler(this);

        tv_highScore = (TextView) findViewById(R.id.dev_tv22);
        tv_hsDate = (TextView) findViewById(R.id.dev_tv32);
        tv_firstScore = (TextView) findViewById(R.id.dev_tv42);
        tv_fsDate = (TextView) findViewById(R.id.dev_tv52);

        showScore();
    }

    public void showScore() {
        //get value from database
        String highScoreArray[] = myDBHandler.getSingleScore_FromDB_ToStringArray_By_ID(1);
        String firstScoreArray[] = myDBHandler.getSingleScore_FromDB_ToStringArray_By_ID(2);

        if (highScoreArray[0] == null) {//initially array value null, but want to display 0.0%
            //set value to display
            tv_highScore.setText(0.0 + "%");
            tv_hsDate.setText("");
            tv_firstScore.setText(0.0 + "%");
            tv_fsDate.setText("");
        } else {
            //set value to display
            tv_highScore.setText(highScoreArray[0] + "%");
            tv_hsDate.setText(highScoreArray[1]);
            tv_firstScore.setText(firstScoreArray[0] + "%");
            tv_fsDate.setText(firstScoreArray[1]);
        }
    }

    public void clicked_dev_b1(View view) {
        finish();
    }

    //toolbar back button clicked
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {//for toolbar back button
            onBackPressed();
            return true;
        } else if (menuItem.getItemId() == R.id.dev_menu_resetScore) {//for dev menu item
            AlertDialog.Builder myBuilder = new AlertDialog.Builder(Development.this);
            myBuilder.setTitle("সতর্কতা!");
            myBuilder.setMessage("আপনি স্কোর মুছে ফেলতে যাচ্ছেন।");
            //myBuilder.setIcon();//if you want to pu images

            myBuilder.setPositiveButton("মুছে ফেলুন", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //when reset put "-" in date box, will need to re-enter data into first score row
                    myDBHandler.updateScore(0.0 + "", "-", 1);
                    myDBHandler.updateScore(0.0 + "", "-", 2);
                    Toast.makeText(getApplicationContext(), "স্কোর মুছে ফেলা হয়েছে।", Toast.LENGTH_SHORT).show();
                    tv_highScore.setText(0.0 + "%");
                    tv_hsDate.setText("");
                    tv_firstScore.setText(0.0 + "%");
                    tv_fsDate.setText("");
                }
            });

            myBuilder.setNegativeButton("বাতিল করুন", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            AlertDialog myDialog = myBuilder.create();
            myDialog.show();

            return true;

        } else {
            return false;
        }
    }

    //for dev menu
    public boolean onCreateOptionsMenu(Menu menuType) {
        super.onCreateOptionsMenu(menuType);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.development_menu, menuType);
        return true;
    }

}
