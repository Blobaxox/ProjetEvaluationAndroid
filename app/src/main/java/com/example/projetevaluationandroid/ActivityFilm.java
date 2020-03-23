package com.example.projetevaluationandroid;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class ActivityFilm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film);

        Bundle bundle = getIntent().getExtras();
        int idFilm = -1;
        if(bundle != null)
        {
            idFilm = bundle.getInt("idFilm");
        }
    }
}
