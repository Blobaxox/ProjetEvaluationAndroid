package com.example.projetevaluationandroid;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projetevaluationandroid.classes.Film;
import com.example.projetevaluationandroid.classes.FilmDAO;

public class ActivityRecapitulatif extends AppCompatActivity
{

    private Film filmSelectionne;
    private FilmDAO unFilmDAO;
    private ImageView affiche;
    private TextView nomFilm;
    private TextView horaire;
    private TextView cout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recapitulatif);

        affiche = findViewById(R.id.afficheFilmRecap);
        nomFilm = findViewById(R.id.nomFilmRecap);
        horaire = findViewById(R.id.horaireRecap);
        cout = findViewById(R.id.cout);

        Bundle bundle = getIntent().getExtras();
        int idFilm = bundle.getInt("idFilm");
        String seanceSelect = bundle.getString("horaire");
        double prix = bundle.getDouble("cout");

        unFilmDAO = new FilmDAO(this);
        filmSelectionne = unFilmDAO.getFilm((long) idFilm);

        affiche.setImageResource(filmSelectionne.getIdImage());
        nomFilm.setText(filmSelectionne.getNom());
        horaire.setText(seanceSelect);
        cout.setText(prix + "€");
    }
}
