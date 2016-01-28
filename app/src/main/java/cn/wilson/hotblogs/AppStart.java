package cn.wilson.hotblogs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AppStart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_start);

        Intent intent = new Intent(AppStart.this,AppMain.class);
        startActivity(intent);
        finish();
    }
}
