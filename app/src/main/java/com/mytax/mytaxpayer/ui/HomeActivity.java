package com.mytax.mytaxpayer.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mytax.mytaxpayer.R;
import com.mytax.mytaxpayer.utils.FirebaseMethods;

public class HomeActivity extends AppCompatActivity {
  FirebaseMethods firebaseMethods;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initWidgets();
        initFirebase();

    }

    private void initFirebase()
    {
        firebaseMethods=new FirebaseMethods(this);
        firebaseMethods.initFirbase();


    }

    private void initWidgets() {
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        firebaseMethods.onChangeState();
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseMethods.clearState();
    }
}
