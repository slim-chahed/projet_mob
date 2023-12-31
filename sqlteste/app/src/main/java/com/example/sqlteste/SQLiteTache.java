package com.example.sqlteste;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteTache extends SQLiteOpenHelper {
    // Constructeur de la classe SQLiteTache
    public SQLiteTache(Context context, String name, SQLiteDatabase.CursorFactory factory,
                       int version) {
        // Appel du constructeur de la classe mère avec les paramètres spécifiés
        super(context, name, factory, version);
    }

    // Méthode appelée lors de la création de la base de données
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Définition de la requête SQL pour créer la table "tache"
        String sql = "CREATE TABLE tache (id INTEGER PRIMARY KEY AUTOINCREMENT, nom TEXT NOT NULL, avn INTEGER);";
        // Exécution de la requête SQL pour créer la table
        db.execSQL(sql);
    }

    // Méthode appelée lors d'une mise à niveau de la base de données
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Cette méthode est vide car aucune action spécifique n'est nécessaire lors de la mise à niveau
        // Vous pourriez mettre à jour la structure de la base de données ici si nécessaire
    }
}