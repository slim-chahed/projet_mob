package com.example.sqlteste;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnAjouter;   // Bouton pour ajouter une nouvelle tâche
    private Button btnQuitter;    // Bouton pour quitter l'application
    private ListView lstT;        // Liste des tâches
    private ArrayAdapter<Tache> adpT; // Adaptateur pour la liste de tâches
    private static final int ACTION_AJOUT = 1;           // Code d'action pour l'ajout de tâche
    private static final int ACTION_MODIFICATION = 2;    // Code d'action pour la modification de tâche
    private static final int ACTION_SUPPRESSION = 3;     // Code d'action pour la suppression de tâche
    // Déclaration du lanceur d'activité pour la gestion des résultats
    private ActivityResultLauncher<Intent> ActivityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        remplir(); // Mettre à jour la liste après l'ajout, la modification, ou la suppression
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init(); // Initialiser les composants et écouteurs
    }

    // Initialisation des composants
    private void init() {
        btnAjouter=findViewById(R.id.btnAjout);
        btnQuitter=findViewById(R.id.btnQuitter);
        lstT=findViewById(R.id.lstTache);
        adpT=new ArrayAdapter<Tache>(this,android.R.layout.simple_list_item_1);
        lstT.setAdapter(adpT);
        remplir(); // Remplir la liste avec les tâches existantes
        ajouterEcouteurs(); // Ajouter les écouteurs pour les boutons et la liste
    }

    // Remplir la liste avec les tâches existantes
    private void remplir() {
        SQLiteTache dbHelper = new SQLiteTache(this, "taches.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "SELECT * FROM tache WHERE avn < 100;";
        Cursor cursor = db.rawQuery(sql, null);
        adpT.clear();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String nom = cursor.getString(1);
            int avn = cursor.getInt(2);

            Tache tache = new Tache(id, nom, avn);
            adpT.add(tache);
        }

        cursor.close();
    }

    // Ajouter les écouteurs pour les boutons et la liste
    private void ajouterEcouteurs() {
        btnAjouter.setOnClickListener(this);
        btnQuitter.setOnClickListener(this);
        lstT.setOnItemClickListener((parent, view, position, id) -> modifier(position)); // Modifier la tâche sélectionnée
    }

    // Modifier la tâche sélectionnée
    private void modifier(int position) {
        Tache tache = adpT.getItem(position);
        Intent intent = new Intent(this, Modification.class);
        intent.putExtra("id", tache.getId());
        intent.putExtra("nom", tache.getNom());
        intent.putExtra("avn", tache.getAvn());
        ActivityLauncher.launch(intent); // Lancer l'activité de modification avec le lanceur d'activité pour les résultats
    }

    // Gérer les clics sur les boutons
    @Override
    public void onClick(View v) {
        Intent intent = null;
        int id = v.getId();
        if (id == R.id.btnAjout) {
            intent = new Intent(MainActivity.this, Ajout.class);
        } else if (id == R.id.btnQuitter) {
            finish();
        }
        ActivityLauncher.launch(intent); // Lancer l'activité correspondante avec le lanceur d'activité pour les résultats
    }
}