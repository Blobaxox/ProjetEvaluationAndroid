package com.example.projetevaluationandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    private LinearLayout listeSeances;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film);

        affiche = findViewById(R.id.afficheFilm);
        nomFilm = findViewById(R.id.nomFilm);
        listeSeances = findViewById(R.id.listeSeances);

        Bundle bundle = getIntent().getExtras();
        int idFilm = bundle.getInt("idFilm");

        unFilmDAO = new FilmDAO(this);
        filmSelectionne = unFilmDAO.getFilm((long) idFilm);

        affiche.setImageResource(filmSelectionne.getIdImage());
        nomFilm.setText(filmSelectionne.getNom());

        afficherSeances();
    }

    public void afficherSeances()
    {
        int i = 0;
        for(String seance : filmSelectionne.getListeSeances())
        {
            Button bout = new Button(this);
            bout.setId(i);
            bout.setText(getString(R.string.labelSeance) + " " + seance);
            bout.setPadding(50,0,50,0);
            bout.setOnClickListener(new SeanceOnClickListener());

            listeSeances.addView(bout);
            i++;
        }
    }

    private class SeanceOnClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            Intent intent = new Intent(ActivityFilm.this, ActivityReserver.class);
            Bundle bundle = new Bundle();
            bundle.putLong("idFilm", filmSelectionne.getId());
            bundle.putString("horaire", filmSelectionne.getListeSeances().get(v.getId()));
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }
    }
}
