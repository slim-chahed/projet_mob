package com.example.sqlteste;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

public class Modification extends AppCompatActivity {
    private EditText edId;          // Champ de texte pour afficher l'identifiant de la tâche
    private EditText edNom;         // Champ de texte pour saisir/modifier le nom de la tâche
    private SeekBar seekAvn;        // Barre de progression pour définir/modifier l'avancement de la tâche
    private Button btnModifier, btnSupprimer;  // Boutons pour modifier et supprimer la tâche
    private Button btnAnnuler;      // Bouton pour annuler et revenir en arrière
    private SQLiteTache dbHelper;   // Assistant SQLite pour la gestion de la base de données
    private int id;                 // Identifiant de la tâche à modifier/supprimer

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modification);
        init();  // Initialiser les composants et écouteurs
    }

    private void init() {
        btnSupprimer = findViewById(R.id.btnSupprimer);
        edId = findViewById(R.id.edId);
        edNom = findViewById(R.id.edNom);
        seekAvn = findViewById(R.id.seekAvn);
        btnModifier = findViewById(R.id.btnModifier);
        btnAnnuler = findViewById(R.id.btnAnnuler);
        dbHelper = new SQLiteTache(this, "taches.db", null, 1); // Initialiser l'assistant SQLite pour la base de données
        id = getIntent().getIntExtra("id", 0); // Initialiser l'identifiant à partir de l'Intent
        remplirChamps(); // Remplir les champs avec les données de la tâche à modifier
        ajouterEcouteurs(); // Ajouter les écouteurs pour les boutons
    }

    private void remplirChamps() {
        edId.setText(String.valueOf(id)); // Afficher l'identifiant en tant que texte
        edNom.setText(getIntent().getStringExtra("nom")); // Afficher le nom de la tâche
        seekAvn.setProgress(getIntent().getIntExtra("avn", 0)); // Afficher l'avancement de la tâche
    }

    private void ajouterEcouteurs() {
        btnModifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifierTache(); // Appeler la méthode pour modifier la tâche
            }
        });

        btnAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Fermer l'activité en cas d'annulation
            }
        });
        btnSupprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supprimerTache(); // Appeler la méthode pour supprimer la tâche
            }
        });
    }

    private void supprimerTache() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("tache", "id=?", new String[]{String.valueOf(id)}); // Supprimer la tâche de la base de données
        setResult(RESULT_OK); // Définir le code de résultat comme OK
        finish(); // Fermer l'activité
    }

    private void modifierTache() {
        ContentValues values = new ContentValues();

        String nom = edNom.getText().toString();
        int avn = seekAvn.getProgress();
        values.put("nom", nom);
        values.put("avn", avn);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.update("tache", values, "id=?", new String[]{String.valueOf(id)}); // Mettre à jour la tâche dans la base de données
        setResult(RESULT_OK); // Définir le code de résultat comme OK
        finish(); // Fermer l'activité
    }
}