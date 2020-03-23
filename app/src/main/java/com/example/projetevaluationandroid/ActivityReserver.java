package com.example.projetevaluationandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projetevaluationandroid.classes.Film;
import com.example.projetevaluationandroid.classes.FilmDAO;

import org.w3c.dom.Text;

public class ActivityReserver extends AppCompatActivity {

    private Film filmSelectionne;
    private FilmDAO unFilmDAO;
    private ImageView affiche;
    private TextView nomFilm;
    private TextView horaire;
    private TextView nbPlacesTarifNormal, nbPlacesTarifEtud, nbPlacesTarifJeune;
    private TextView tarifNormal, tarifEtud, tarifJeune;
    private Button boutPlusNormal, boutPlusEtud, boutPlusJeune;
    private Button boutMoinsNormal, boutMoinsEtud, boutMoinsJeune;
    private Button boutReserver;

    private final double TARIF_NORMAL = 9.60;
    private final double TARIF_ETUD = 7.00;
    private final double TARIF_JEUNE = 5.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserver);

        affiche = findViewById(R.id.afficheFilmReserv);
        nomFilm = findViewById(R.id.nomFilmReserv);
        horaire = findViewById(R.id.horaireReserv);
        nbPlacesTarifNormal = findViewById(R.id.nbPlacesTarifNormal);
        nbPlacesTarifEtud = findViewById(R.id.nbPlacesTarifEtud);
        nbPlacesTarifJeune = findViewById(R.id.nbPlacesTarifJeune);
        tarifNormal = findViewById(R.id.tarifNormal);
        tarifEtud = findViewById(R.id.tarifEtud);
        tarifJeune = findViewById(R.id.tarifJeune);
        boutPlusNormal = findViewById(R.id.boutPlusTarifNormal);
        boutPlusEtud = findViewById(R.id.boutPlusTarifEtud);
        boutPlusJeune = findViewById(R.id.boutPlusTarifJeune);
        boutMoinsNormal = findViewById(R.id.boutMoinsTarifNormal);
        boutMoinsEtud = findViewById(R.id.boutMoinsTarifEtud);
        boutMoinsJeune = findViewById(R.id.boutMoinsTarifJeune);
        boutReserver = findViewById(R.id.boutReserver);

        Bundle bundle = getIntent().getExtras();
        int idFilm = bundle.getInt("idFilm");
        String seanceSelect = bundle.getString("horaire");

        unFilmDAO = new FilmDAO(this);
        filmSelectionne = unFilmDAO.getFilm((long) idFilm);

        affiche.setImageResource(filmSelectionne.getIdImage());
        nomFilm.setText(filmSelectionne.getNom());
        horaire.setText(seanceSelect);

        tarifNormal.setText(
                tarifNormal.getText().toString() + TARIF_NORMAL + "€");

        tarifEtud.setText(
                tarifEtud.getText().toString() + TARIF_ETUD + "€");

        tarifJeune.setText(
                tarifJeune.getText().toString() + TARIF_JEUNE + "€");

        boutPlusNormal.setOnClickListener(new IncrementerNbPlaces());
        boutPlusEtud.setOnClickListener(new IncrementerNbPlaces());
        boutPlusJeune.setOnClickListener(new IncrementerNbPlaces());

        boutMoinsNormal.setOnClickListener(new DecrementerNbPlaces());
        boutMoinsEtud.setOnClickListener(new DecrementerNbPlaces());
        boutMoinsJeune.setOnClickListener(new DecrementerNbPlaces());

        boutReserver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(ActivityReserver.this, ActivityRecapitulatif.class);
                Bundle bundle = new Bundle();
                bundle.putInt("idFilm", (int) filmSelectionne.getId());
                bundle.putString("horaire", horaire.getText().toString());
                bundle.putDouble("cout", calculerCout());
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
    }

    public double calculerCout()
    {
        double coutTotal = Integer.parseInt(nbPlacesTarifNormal.getText().toString()) * TARIF_NORMAL
                    + Integer.parseInt(nbPlacesTarifEtud.getText().toString()) * TARIF_ETUD
                    + Integer.parseInt(nbPlacesTarifJeune.getText().toString()) * TARIF_JEUNE;

        return coutTotal;
    }

    private class IncrementerNbPlaces implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            switch(v.getId())
            {
                case R.id.boutPlusTarifNormal:
                    nbPlacesTarifNormal.setText(
                            Integer.parseInt(nbPlacesTarifNormal.getText().toString()) + 1 + "");
                    break;

                case R.id.boutPlusTarifEtud:
                    nbPlacesTarifEtud.setText(
                            Integer.parseInt(nbPlacesTarifEtud.getText().toString()) + 1 + "");
                    break;

                case R.id.boutPlusTarifJeune:
                    nbPlacesTarifJeune.setText(
                            Integer.parseInt(nbPlacesTarifJeune.getText().toString()) + 1 + "");
                    break;
            }
        }
    }

    private class DecrementerNbPlaces implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            switch(v.getId())
            {
                case R.id.boutMoinsTarifNormal:

                    if(Integer.parseInt(nbPlacesTarifNormal.getText().toString()) > 0)
                    {
                        nbPlacesTarifNormal.setText(
                                Integer.parseInt(nbPlacesTarifNormal.getText().toString()) - 1 + "");
                    }

                    break;

                case R.id.boutMoinsTarifEtud:

                    if(Integer.parseInt(nbPlacesTarifEtud.getText().toString()) > 0)
                    {
                        nbPlacesTarifEtud.setText(
                                Integer.parseInt(nbPlacesTarifEtud.getText().toString()) - 1 + "");
                    }

                    break;

                case R.id.boutMoinsTarifJeune:

                    if(Integer.parseInt(nbPlacesTarifJeune.getText().toString()) > 0)
                    {
                        nbPlacesTarifJeune.setText(
                                Integer.parseInt(nbPlacesTarifJeune.getText().toString()) - 1 + "");
                    }
                    break;
            }
        }
    }
}
