package com.example.projetevaluationandroid.classes;

import com.example.projetevaluationandroid.R;

public class Film
{

    private long id;
    private String nom;
    private String realisateur;
    private String duree;
    private String langue;
    private int idImage;

    public Film(long id, String nom, String realisateur, String duree, String langue, int idImage)
    {
        this.id = id;
        this.nom = nom;
        this.realisateur = realisateur;
        this.duree = duree;
        this.langue = langue;
        this.idImage = idImage;
    }

    // Constructeur à employer en cas d'erreur
    public Film()
    {
        this.id = -1;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getNom()
    {
        return nom;
    }

    public void setNom(String nom)
    {
        this.nom = nom;
    }

    public String getRealisateur()
    {
        return realisateur;
    }

    public void setRealisateur(String realisateur)
    {
        this.realisateur = realisateur;
    }

    public String getDuree()
    {
        return duree;
    }

    public void setDuree(String duree)
    {
        this.duree = duree;
    }

    public String getLangue()
    {
        return langue;
    }

    public void setLangue(String langue)
    {
        this.langue = langue;
    }

    public int getIdImage()
    {
        return this.idImage;
    }

    public void setIdImage(int idImage)
    {
        this.idImage = idImage;
    }
}
