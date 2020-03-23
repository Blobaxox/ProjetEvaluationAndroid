package com.example.projetevaluationandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
import com.example.projetevaluationandroid.classes.Film;
import com.example.projetevaluationandroid.classes.FilmDAO;

public class ActivityFilm extends AppCompatActivity {

    private FilmDAO unFilmDAO;
    private ImageView affiche;
    private TextView nomFilm;
    private Film filmSelectionne;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film);

        Bundle bundle = getIntent().getExtras();
        int idFilm = bundle.getInt("idFilm");

        unFilmDAO = new FilmDAO(this);

        affiche = findViewById(R.id.afficheFilm);
        nomFilm = findViewById(R.id.nomFilm);

        filmSelectionne = unFilmDAO.getFilm((long) idFilm);

        affiche.setImageResource(filmSelectionne.getIdImage());
        nomFilm.setText(filmSelectionne.getNom());

        afficherSeances();
    }

    public void afficherSeances()
    {
        for(String seance : filmSelectionne.getListeSeances())
        {

        }
    }

    private class SeanceOnClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            Intent intent = new Intent(ActivityFilm.this, ActivityReserver.class);
            Bundle bundle = new Bundle();
            bundle.putInt("idFilm", (int) filmSelectionne.getId());
            bundle.putString("horaire", null);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }
    }
}
