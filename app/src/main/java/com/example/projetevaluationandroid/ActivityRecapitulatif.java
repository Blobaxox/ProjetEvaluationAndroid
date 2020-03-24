package com.example.projetevaluationandroid;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.projetevaluationandroid.classes.Film;
import com.example.projetevaluationandroid.classes.FilmDAO;
import java.math.RoundingMode;
import java.text.DecimalFormat;

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
        long idFilm = bundle.getLong("idFilm");
        String seanceSelect = bundle.getString("horaire");
        double prix = bundle.getDouble("cout");

        unFilmDAO = new FilmDAO(this);
        filmSelectionne = unFilmDAO.getFilm(idFilm);

        affiche.setImageResource(filmSelectionne.getIdImage());
        nomFilm.setText(filmSelectionne.getNom());
        horaire.setText(seanceSelect);

        /* Nous avons besoin d'arrondir car lorsque l'on achète 3 places à 9.6€ l'unité
        le prix renvoyé est 28.7999999999999999997 */
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.HALF_EVEN);

        cout.setText(df.format(prix) + "€");
    }
}
