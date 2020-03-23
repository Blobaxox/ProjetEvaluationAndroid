package com.example.projetevaluationandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.projetevaluationandroid.classes.FilmDAO;

public class MainActivity extends AppCompatActivity {

    private FilmDAO unFilmDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unFilmDAO = new FilmDAO(this);
    }

    public void afficherFilms()
    {

    }

    private class FilmOnClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            Intent intent = new Intent(MainActivity.this, ActivityFilm.class);
            Bundle bundle = new Bundle();
            bundle.putInt("idFilm", v.getId());
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }
    }
}
