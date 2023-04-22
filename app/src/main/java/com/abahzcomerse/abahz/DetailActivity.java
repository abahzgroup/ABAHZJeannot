package com.abahzcomerse.abahz;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    ImageView detail_image;
    Button recordBtn;
    TextView detail_name,detail_qteS,detail_prixAu,detail_prixAt,detail_prixVu,detail_prixVt,detail_margeU,detail_margT,detail_date;

    private String prodId;
    MyDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        recordBtn = findViewById(R.id.recordBtn);

        Intent intent = getIntent();
        prodId = intent.getStringExtra("PROD_ID");

        detail_image = findViewById(R.id.detail_image);
        detail_name = findViewById(R.id.detail_name);
        detail_qteS = findViewById(R.id.detail_qteS);
        detail_prixAu = findViewById(R.id.detail_prixAu);
        detail_prixAt = findViewById(R.id.detail_priAt);
        detail_prixVu = findViewById(R.id.detail_prixVu);
        detail_prixVt = findViewById(R.id.detail_priVt);
        detail_margeU = findViewById(R.id.detail_margeU);
        detail_margT = findViewById(R.id.detail_margeT);
        detail_date = findViewById(R.id.detail_date);

        dbHelper = new MyDbHelper(this);

        showDetailProduct();

        recordBtn.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(),FactureActivity.class));
        });
    }

    private void showDetailProduct() {

        String selectQuery = "SELECT * FROM " + Constantes.TABLE_NAME + " WHERE " + Constantes.C_ID +" =\"" + prodId +"\"";

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()){
            do {
                @SuppressLint("Range") String id = ""+ cursor.getInt(cursor.getColumnIndex(Constantes.C_ID));
                @SuppressLint("Range") String name = ""+ cursor.getString(cursor.getColumnIndex(Constantes.C_NAME));
                @SuppressLint("Range") String image = ""+ cursor.getString(cursor.getColumnIndex(Constantes.C_IMAGE));
                @SuppressLint("Range") String qteS = ""+ cursor.getString(cursor.getColumnIndex(Constantes.C_QTE_STOCK));
                @SuppressLint("Range") String prixAu = ""+ cursor.getString(cursor.getColumnIndex(Constantes.C_PRIX_ACHAT_U));
                @SuppressLint("Range") String prixAt = ""+ cursor.getString(cursor.getColumnIndex(Constantes.C_PRIX_ACHAT_T));
                @SuppressLint("Range") String prixVu = ""+ cursor.getString(cursor.getColumnIndex(Constantes.C_PRIX_VENTE_U));
                @SuppressLint("Range") String prixVt = ""+ cursor.getString(cursor.getColumnIndex(Constantes.C_PRIX_VENTE_T));
                @SuppressLint("Range") String margeu = ""+ cursor.getString(cursor.getColumnIndex(Constantes.C_MARGE_U));
                @SuppressLint("Range") String marget = ""+ cursor.getString(cursor.getColumnIndex(Constantes.C_MARGE_T));
                @SuppressLint("Range") String date = ""+ cursor.getString(cursor.getColumnIndex(Constantes.C_DATE_EXP));

                detail_name.setText(name);
                detail_qteS.setText(qteS);
                detail_prixAu.setText(prixAu);
                detail_prixAt.setText(prixAt);
                detail_prixVu.setText(prixVu);
                detail_prixVt.setText(prixVt);
                detail_margeU.setText(margeu);
                detail_margT.setText(marget);
                detail_date.setText(date);
                if (image.equals("null")){
                    detail_image.setImageResource(R.drawable.baseline_add_photo_alternate_24);
                }else {
                    detail_image.setImageURI(Uri.parse(image));
                }
            }while (cursor.moveToNext());
        }
        db.close();
    }
}