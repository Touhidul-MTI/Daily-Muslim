package io.github.imtilab.dailymuslim;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class UserGuide extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_guide);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void clicked_userguide_b1(View view) {
        Intent qa = new Intent(this, QuesAns.class);
        startActivity(qa);
        finish();
    }
    //toolbar back button clicked
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }
}
