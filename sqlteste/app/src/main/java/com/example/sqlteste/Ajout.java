package com.example.sqlteste;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Ajout extends AppCompatActivity {
    private EditText edNom;      // Champ de texte pour entrer le nom de la tâche
    private Button btnAjouter;   // Bouton pour ajouter une nouvelle tâche
    private Button btnAnnuler;    // Bouton pour annuler et revenir en arrière

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout);
        init();  // Initialiser les composants de l'interface utilisateur et définir les écouteurs
    }

    private void init() {
        edNom = findViewById(R.id.edNom);
        btnAjouter = findViewById(R.id.btnAjouter);
        btnAnnuler = findViewById(R.id.btnAnnuler);
        ajouterEcouteurs();  // Définir les écouteurs de clic pour les boutons
    }

    private void ajouterEcouteurs() {
        btnAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajouter();  // Appeler la méthode pour ajouter une nouvelle tâche
            }
        });
        btnAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                annuler();  // Appeler la méthode pour annuler et revenir en arrière
            }
        });
    }

    private void ajouter() {
        // Créer une instance de la classe d'aide SQLiteTache pour gérer la base de données
        SQLiteTache dbHelper = new SQLiteTache(this, "taches.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();  // Obtenir une base de données en écriture

        // Obtenir le nom de la tâche et définir l'avancement par défaut à 0
        String nomTache = edNom.getText().toString();
        int avancement = 0;

        // Créer un objet ContentValues pour stocker les paires colonne-valeur
        ContentValues values = new ContentValues();
        values.put("nom", nomTache);    // "nom" est la colonne pour le nom de la tâche
        values.put("avn", avancement);  // "avn" est la colonne pour l'avancement de la tâche

        // Insérer les valeurs dans la table "tache"
        db.insert("tache", null, values);

        setResult(RESULT_OK);  // Définir le code de résultat comme OK
        finish();  // Fermer l'activité actuelle et revenir à l'activité précédente
        // Gérer le cas où l'insertion a échoué.
    }

    private void annuler() {
        // Implémenter la logique d'annulation si nécessaire
    }
}