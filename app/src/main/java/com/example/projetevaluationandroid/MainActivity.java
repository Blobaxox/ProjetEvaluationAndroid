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

public class MainActivity extends AppCompatActivity {

    private FilmDAO unFilmDAO;
    private LinearLayout listeFilms;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listeFilms = findViewById(R.id.listeFilms);

        unFilmDAO = new FilmDAO(this);

        afficherFilms();
    }

    public void afficherFilms()
    {
        for(Film filmCourant : unFilmDAO.getAllFilms())
        {
            Space espaceEntreFilms = new Space(this);
            espaceEntreFilms.setMinimumHeight(40);
            listeFilms.addView(espaceEntreFilms);

            LinearLayout layoutFilm = new LinearLayout(this);
            layoutFilm.setId((int) filmCourant.getId());
            layoutFilm.setOrientation(LinearLayout.HORIZONTAL);
            layoutFilm.setBackgroundColor(getResources().getColor(R.color.grey));
            layoutFilm.setMinimumWidth(500);

            ImageView affiche = new ImageView(this);
            affiche.setImageResource(filmCourant.getIdImage());
            affiche.setAdjustViewBounds(true);
            affiche.setMaxWidth(300);
            layoutFilm.addView(affiche);

            Space espaceAvantInfos = new Space(this);
            espaceAvantInfos.setMinimumWidth(30);
            layoutFilm.addView(espaceAvantInfos);

            LinearLayout layoutInfos = new LinearLayout(this);
            layoutInfos.setOrientation(LinearLayout.VERTICAL);

            TextView titre = new TextView(this);
            titre.setText(filmCourant.getNom());
            titre.setTextSize(30);
            titre.setTypeface(null, Typeface.BOLD);
            layoutInfos.addView(titre);

            TextView real = new TextView(this);
            real.setText(filmCourant.getRealisateur());
            real.setTextSize(30);
            layoutInfos.addView(real);

            TextView duree = new TextView(this);
            duree.setText(filmCourant.getDuree());
            duree.setTextSize(30);
            layoutInfos.addView(duree);

            layoutFilm.addView(layoutInfos);

            TextView langue = new TextView(this);
            langue.setText(filmCourant.getLangue());
            langue.setTextSize(40);
            layoutFilm.addView(langue);

            layoutFilm.setClickable(true);
            layoutFilm.setOnClickListener(new FilmOnClickListener());

            listeFilms.addView(layoutFilm);
        }
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
