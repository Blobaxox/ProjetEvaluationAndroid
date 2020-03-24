package com.example.projetevaluationandroid.classes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.projetevaluationandroid.R;
import java.util.ArrayList;

public class FilmDAO extends SQLiteOpenHelper
{

    public FilmDAO(Context contexte)
    {
        super(contexte, Param.base, null, Param.version);
    }

    @Override
    public void onCreate(SQLiteDatabase base)
    {
        base.execSQL("CREATE TABLE Film (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nom TEXT NOT NULL," +
                "realisateur TEXT NOT NULL," +
                "duree TEXT NOT NULL," +
                "langue TEXT NOT NULL," +
                "idImage INTEGER NOT NULL);");

        base.execSQL("CREATE TABLE Seance (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "heure TEXT NOT NULL," +
                "idFilm INTEGER NOT NULL," +
                "FOREIGN KEY (idFilm) REFERENCES Film(id));");

        // Joker
        base.execSQL("INSERT INTO Film (nom, realisateur, duree, langue, idImage) VALUES (" +
                "'Joker', 'Todd Phillips', '2h02', 'VF'," + R.drawable.joker + ");");

        base.execSQL("INSERT INTO Seance (heure, idFilm) VALUES (" +
                "'11h00', (SELECT id FROM Film WHERE nom = 'Joker'));");

        base.execSQL("INSERT INTO Seance (heure, idFilm) VALUES (" +
                "'15h00', (SELECT id FROM Film WHERE nom = 'Joker'));");

        base.execSQL("INSERT INTO Seance (heure, idFilm) VALUES (" +
                "'19h45', (SELECT id FROM Film WHERE nom = 'Joker'));");

        // Countdown
        base.execSQL("INSERT INTO Film (nom, realisateur, duree, langue, idImage) VALUES (" +
                "'Countdown', 'Justin Dec', '1h30', 'VO'," + R.drawable.countdown + ");");

        base.execSQL("INSERT INTO Seance (heure, idFilm) VALUES (" +
                "'19h45', (SELECT id FROM Film WHERE nom = 'Countdown'));");

        base.execSQL("INSERT INTO Seance (heure, idFilm) VALUES (" +
                "'23h00', (SELECT id FROM Film WHERE nom = 'Countdown'));");

        // Sonic
        base.execSQL("INSERT INTO Film (nom, realisateur, duree, langue, idImage) VALUES (" +
                "'Sonic', 'Jeff Fowler', '1h40', 'VF'," + R.drawable.sonic + ");");

        base.execSQL("INSERT INTO Seance (heure, idFilm) VALUES (" +
                "'20h00', (SELECT id FROM Film WHERE nom = 'Sonic'))");

        // Endgame
        base.execSQL("INSERT INTO Film (nom, realisateur, duree, langue, idImage) VALUES (" +
                "'Avengers : Endgame', 'Joe Russo', '3h02', 'VO'," + R.drawable.endgame + ");");

        base.execSQL("INSERT INTO Seance (heure, idFilm) VALUES (" +
                "'10h00', (SELECT id FROM Film WHERE nom = 'Avengers : Endgame'));");

        base.execSQL("INSERT INTO Seance (heure, idFilm) VALUES (" +
                "'22h00', (SELECT id FROM Film WHERE nom = 'Avengers : Endgame'));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase base, int oldVersion, int newVersion)
    {
        // On est obligé de l'implémenter
    }

    public void ajouterFilm(Film unFilm)
    {
        SQLiteDatabase base = this.getWritableDatabase();

        String reqFilm = "INSERT INTO Film (nom, realisateur, duree, langue, idImage) VALUES ('" +
                unFilm.getNom() + "', '" + unFilm.getRealisateur() + "', '" + unFilm.getDuree() +
                "', '" + unFilm.getLangue() + "', " + unFilm.getIdImage() + ");";

        base.execSQL(reqFilm);

        for(String seance : unFilm.getListeSeances())
        {
            String reqSeance = "INSERT INTO Seance (heure, idFilm) VALUES (" +
                    "'" + seance + "', (SELECT id FROM Film WHERE nom = '" + unFilm.getNom() + "'));";

            base.execSQL(reqSeance);
        }

        base.close();
    }

    public void supprimerFilm(long id)
    {
        SQLiteDatabase base = this.getWritableDatabase();

        base.execSQL("DELETE FROM Seance WHERE idFilm = " + id + ";");
        base.execSQL("DELETE FROM Film WHERE id = " + id + ";");

        base.close();
    }

    public ArrayList<Film> getAllFilms()
    {
        Cursor curseur;
        String requete = "SELECT * FROM FILM;";

        curseur = this.getReadableDatabase().rawQuery(requete, null);
        ArrayList<Film> listeDesFilms = cursorToFilmArrayList(curseur);
        this.getReadableDatabase().close();

        return listeDesFilms;
    }

    public Film getFilm(long id)
    {
        Film leFilm = null;
        Cursor curseur;

        String requete = "SELECT * FROM FILM WHERE id = ?";

        curseur = this.getReadableDatabase().rawQuery(requete, new String[] {id + ""});

        if(curseur.getCount() > 0)
        {
            leFilm = cursorToFilm(curseur);
        }
        else
        {
            leFilm = new Film();
        }

        this.getReadableDatabase().close();

        return leFilm;
    }

    private ArrayList<String> getSeancesOfFilm(long idFilm)
    {
        Cursor curseur;
        ArrayList<String> listeSeances;

        String requete = "SELECT * FROM SEANCE WHERE idFilm = ?";

        curseur = this.getReadableDatabase().rawQuery(requete, new String[] {idFilm + ""});
        listeSeances = cursorToSeancesArrayList(curseur);
        this.getReadableDatabase().close();

        return listeSeances;
    }

    private ArrayList<Film> cursorToFilmArrayList(Cursor curseur)
    {
        ArrayList<Film> listeFilms = new ArrayList<Film>();

        long id;
        String nom;
        String realisateur;
        String duree;
        String langue;
        int idImage;
        ArrayList<String> listeSeances;

        curseur.moveToFirst();

        while(!curseur.isAfterLast())
        {
            id = curseur.getLong(0);
            nom = curseur.getString(1);
            realisateur = curseur.getString(2);
            duree = curseur.getString(3);
            langue = curseur.getString(4);
            idImage = curseur.getInt(5);
            listeSeances = getSeancesOfFilm(id);

            listeFilms.add(new Film(id, nom, realisateur, duree, langue, idImage, listeSeances));
            curseur.moveToNext();
        }

        return listeFilms;
    }

    private Film cursorToFilm(Cursor curseur)
    {
        Film unFilm = null;

        long id;
        String nom;
        String realisateur;
        String duree;
        String langue;
        int idImage;
        ArrayList<String> listeSeances;

        curseur.moveToFirst();

        id = curseur.getLong(0);
        nom = curseur.getString(1);
        realisateur = curseur.getString(2);
        duree = curseur.getString(3);
        langue = curseur.getString(4);
        idImage = curseur.getInt(5);
        listeSeances = getSeancesOfFilm(id);

        unFilm = new Film(id, nom, realisateur, duree, langue, idImage, listeSeances);

        return unFilm;
    }

    private ArrayList<String> cursorToSeancesArrayList(Cursor curseur)
    {
        ArrayList<String> listeSeances = new ArrayList<String>();

        String seance;

        curseur.moveToFirst();

        while(!curseur.isAfterLast())
        {
            seance = curseur.getString(1);
            listeSeances.add(seance);
            curseur.moveToNext();
        }

        return listeSeances;
    }
}
