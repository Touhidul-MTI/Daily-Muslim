package io.github.imtilab.dailymuslim;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Boolean exit = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //for the very first time, application is opened and make MyDBHandler instance
        //and database is created, from the second time it wont create we know that
        MyDBHandler myDBHandler = new MyDBHandler(this);
    }

    public void clicked_main_b1(View view) {
        Intent qa = new Intent(this, QuesAns.class);
        startActivity(qa);
    }

    public void clicked_main_b2(View view) {
        Intent dev = new Intent(this, Development.class);
        startActivity(dev);
    }
    public void clicked_main_b3(View view) {
        Intent ug = new Intent(this, UserGuide.class);
        startActivity(ug);
    }

    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }
    }
    //for about developer menu
    public boolean onCreateOptionsMenu(Menu menuType) {
        super.onCreateOptionsMenu(menuType);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_about_developer_menu, menuType);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem itemType){
        if(itemType.getItemId()==R.id.main_about_developer){
            DeveloperDialog developerDialog = new DeveloperDialog();
            developerDialog.show(getFragmentManager(), "Developer Alert Dialog");

            return true;

        }else{
            return false;
        }
    }

}
