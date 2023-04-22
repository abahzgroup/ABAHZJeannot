package com.abahzcomerse.abahz;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    RecyclerView rec;
    FloatingActionButton fab;
    MyDbHelper dbHelper;

    TextView totalProduit;
    ImageView search, delete;
    SearchView searchView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new MyDbHelper(this);
        rec = findViewById(R.id.recMain);
        totalProduit = findViewById(R.id.totalProduit);
        search = findViewById(R.id.search);
        delete = findViewById(R.id.filter);
        searchView = findViewById(R.id.searchView);
        toolbar = findViewById(R.id.toolbar);
        fab= findViewById(R.id.fab);

        loadProducts();

        delete.setOnClickListener(v -> {
            AlertDeleteAll();
        });
        search.setOnClickListener(v -> {
           toolbar.setVisibility(View.GONE);
           searchView.setVisibility(View.VISIBLE);
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchProducts(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchProducts(newText);
                return true;
            }
        });
        fab.setOnClickListener(v -> {

            Intent intent = new Intent(getApplicationContext(),AddActivity.class);
            intent.putExtra("isEditMode",false);
            startActivity(intent);

        });
    }

    private void AlertDeleteAll() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Suppression");
        builder.setMessage("Etes-vous sur de tous supprimer ?");
        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dbHelper.deleteAllData();
            }
        }).setNegativeButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Okey Merci !", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this,MainActivity.class));
                onResume();
            }
        });
        builder.create().show();
    }

    private void searchProducts(String query) {
        AdapterProduit adapterProduit = new AdapterProduit(this,
                dbHelper.searchProducts(query));

        rec.setAdapter(adapterProduit);
    }

    private void loadProducts() {
       AdapterProduit adapterProduit = new AdapterProduit(this,
               dbHelper.getAllProducts(Constantes.C_DATE_EXP + ""));

       rec.setAdapter(adapterProduit);

        totalProduit.setText("Total: "+ dbHelper.getProductCount());

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProducts();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        toolbar.setVisibility(View.VISIBLE);
        searchView.setVisibility(View.GONE);
        startActivity(new Intent(MainActivity.this,MainActivity.class));
    }
}