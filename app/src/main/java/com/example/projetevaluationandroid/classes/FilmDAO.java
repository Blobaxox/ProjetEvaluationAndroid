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

        base.execSQL("INSERT INTO Film (nom, realisateur, duree, langue, idImage) VALUES (" +
                "'film1', 'real1', '2h30', 'VF'," + R.drawable.film1 + ")");

        base.execSQL("INSERT INTO Seance (heure, idFilm) VALUES (" +
                "'11h00', (SELECT id FROM Film WHERE nom = 'film1'))");

        base.execSQL("INSERT INTO Film (nom, realisateur, duree, langue, idImage) VALUES (" +
                "'film2', 'real2', '1h30', 'VO'," + R.drawable.film2 + ")");

        base.execSQL("INSERT INTO Seance (heure, idFilm) VALUES (" +
                "'19h45', (SELECT id FROM Film WHERE nom = 'film2'))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase base, int oldVersion, int newVersion)
    {
        // On est obligé de l'implémenter
    }

    public ArrayList<Film> getAllFilms()
    {
        Cursor curseur;
        String requete = "SELECT * FROM FILM;";

        curseur = this.getReadableDatabase().rawQuery(requete, null);
        this.getReadableDatabase().close();

        return cursorToFilmArrayList(curseur);
    }

    public Film getFilm(long id)
    {
        Film unFilm = null;
        Cursor curseur;

        String requete = "SELECT * FROM FILM WHERE id = ?";

        curseur = this.getReadableDatabase().rawQuery(requete, new String[] {id + ""});
        this.getReadableDatabase().close();

        return cursorToFilm(curseur);
    }

    public Film getFilm(String nom)
    {
        Film unFilm = null;
        Cursor curseur;

        String requete = "SELECT * FROM FILM WHERE nom = ?";

        curseur = this.getReadableDatabase().rawQuery(requete, new String[] {nom + ""});

        if(curseur.getCount() > 0)
        {
            unFilm = cursorToFilm(curseur);
        }
        else
        {
            unFilm = new Film();
        }

        this.getReadableDatabase().close();

        return unFilm;
    }

    public ArrayList<String> getSeancesOfFilm(long idFilm)
    {
        Cursor curseur;

        String requete = "SELECT * FROM SEANCE WHERE idFilm = ?";

        curseur = this.getReadableDatabase().rawQuery(requete, new String[] {idFilm + ""});
        this.getReadableDatabase().close();

        return cursorToSeancesArrayList(curseur);
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

        curseur.moveToFirst();

        while(!curseur.isAfterLast())
        {
            id = curseur.getLong(0);
            nom = curseur.getString(1);
            realisateur = curseur.getString(2);
            duree = curseur.getString(3);
            langue = curseur.getString(4);
            idImage = curseur.getInt(5);

            listeFilms.add(new Film(id, nom, realisateur, duree, langue, idImage));
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

        curseur.moveToFirst();

        id = curseur.getLong(0);
        nom = curseur.getString(1);
        realisateur = curseur.getString(2);
        duree = curseur.getString(3);
        langue = curseur.getString(4);
        idImage = curseur.getInt(5);

        unFilm = new Film(id, nom, realisateur, duree, langue, idImage);

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
