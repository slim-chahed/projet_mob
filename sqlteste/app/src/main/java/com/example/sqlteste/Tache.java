package com.example.sqlteste;

// Classe représentant une tâche
public class Tache {
    private int id;         // Identifiant de la tâche
    private String nom;     // Nom de la tâche
    private int avn;        // Avancement de la tâche

    // Constructeur de la classe Tache
    public Tache(int id, String nom, int avn) {
        // Initialiser les attributs de la tâche avec les valeurs passées en paramètres
        this.id = id;
        this.nom = nom;
        this.avn = avn;
    }

    // Méthode pour obtenir l'identifiant de la tâche
    public int getId() {
        return id;
    }

    // Méthode pour obtenir le nom de la tâche
    public String getNom() {
        return nom;
    }

    // Méthode pour obtenir l'avancement de la tâche
    public int getAvn() {
        return avn;
    }

    // Méthode pour obtenir une représentation textuelle de la tâche
    @Override
    public String toString() {
        // Formatage de la chaîne avec l'identifiant, le nom et l'avancement de la tâche
        return id + " - " + nom + " - " + avn + "%";
    }
}