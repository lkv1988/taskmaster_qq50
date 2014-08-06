package com.github.taskmaster.qq50.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import com.github.taskmaster.qq50.fragment.HomeFragment;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new HomeFragment())
                .commit();
    }
}
