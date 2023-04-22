package com.abahzcomerse.abahz;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MyDbHelper extends SQLiteOpenHelper {
    public MyDbHelper(Context context) {
        super(context, Constantes.DB_NAME, null, Constantes.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constantes.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ Constantes.TABLE_NAME);
        onCreate(db);
    }
    public long insertData(String name,String image,String date,String qachat,String qvente, String qstock,
                           String prixAu,String prixAt,String prixVu, String prixVt,String margeu,
                           String marget,String depenseu,String depenset){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constantes.C_NAME,name);
        values.put(Constantes.C_IMAGE,image);
        values.put(Constantes.C_DATE_EXP,date);
        values.put(Constantes.C_QTE_ACHAT,qachat);
        values.put(Constantes.C_QTE_VENTE,qvente);
        values.put(Constantes.C_QTE_STOCK,qstock);
        values.put(Constantes.C_PRIX_ACHAT_U,prixAu);
        values.put(Constantes.C_PRIX_ACHAT_T,prixAt);
        values.put(Constantes.C_PRIX_VENTE_U,prixVu);
        values.put(Constantes.C_PRIX_VENTE_T,prixVt);
        values.put(Constantes.C_MARGE_U,margeu);
        values.put(Constantes.C_MARGE_T,marget);
        values.put(Constantes.C_DEPENSE_U,depenseu);
        values.put(Constantes.C_DEPENSE_T,depenset);

        long id = db.insert(Constantes.TABLE_NAME,null,values);
        db.close();

        return id;
    }


    public void updateData(String id, String name,String image,String date,String qachat,String qvente, String qstock,
                           String prixAu,String prixAt,String prixVu, String prixVt,String margeu,
                           String marget,String depenseu,String depenset){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constantes.C_NAME,name);
        values.put(Constantes.C_IMAGE,image);
        values.put(Constantes.C_DATE_EXP,date);
        values.put(Constantes.C_QTE_ACHAT,qachat);
        values.put(Constantes.C_QTE_VENTE,qvente);
        values.put(Constantes.C_QTE_STOCK,qstock);
        values.put(Constantes.C_PRIX_ACHAT_U,prixAu);
        values.put(Constantes.C_PRIX_ACHAT_T,prixAt);
        values.put(Constantes.C_PRIX_VENTE_U,prixVu);
        values.put(Constantes.C_PRIX_VENTE_T,prixVt);
        values.put(Constantes.C_MARGE_U,margeu);
        values.put(Constantes.C_MARGE_T,marget);
        values.put(Constantes.C_DEPENSE_U,depenseu);
        values.put(Constantes.C_DEPENSE_T,depenset);

        db.update(Constantes.TABLE_NAME,values,Constantes.C_ID  +" =? ", new String[]{id});
        db.close();

    }



    public ArrayList<ModelProduct> getAllProducts(String orderBy){
        ArrayList<ModelProduct> productList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + Constantes.TABLE_NAME + " ORDER BY " + orderBy;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()){
            do {
                @SuppressLint("Range") ModelProduct modelProduct = new ModelProduct(
                       ""+cursor.getInt(cursor.getColumnIndex(Constantes.C_ID)),
                       ""+cursor.getString(cursor.getColumnIndex(Constantes.C_NAME)),
                       ""+cursor.getString(cursor.getColumnIndex(Constantes.C_IMAGE)),
                       ""+cursor.getString(cursor.getColumnIndex(Constantes.C_PRIX_ACHAT_U)),
                       ""+cursor.getString(cursor.getColumnIndex(Constantes.C_PRIX_VENTE_U)),
                       ""+cursor.getString(cursor.getColumnIndex(Constantes.C_QTE_STOCK)),
                       ""+cursor.getString(cursor.getColumnIndex(Constantes.C_DATE_EXP))
               );
              productList.add(modelProduct) ;
            }while (cursor.moveToNext());
        }
        db.close();
        return productList;
    }


    public ArrayList<ModelProduct> searchProducts(String query){
        ArrayList<ModelProduct> productList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + Constantes.TABLE_NAME + " WHERE " + Constantes.C_NAME + " LIKE '%" + query +"%'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()){
            do {
                @SuppressLint("Range") ModelProduct modelProduct = new ModelProduct(
                        ""+cursor.getInt(cursor.getColumnIndex(Constantes.C_ID)),
                        ""+cursor.getString(cursor.getColumnIndex(Constantes.C_NAME)),
                        ""+cursor.getString(cursor.getColumnIndex(Constantes.C_IMAGE)),
                        ""+cursor.getString(cursor.getColumnIndex(Constantes.C_PRIX_ACHAT_U)),
                        ""+cursor.getString(cursor.getColumnIndex(Constantes.C_PRIX_VENTE_U)),
                        ""+cursor.getString(cursor.getColumnIndex(Constantes.C_QTE_STOCK)),
                        ""+cursor.getString(cursor.getColumnIndex(Constantes.C_DATE_EXP))
                );
                productList.add(modelProduct) ;
            }while (cursor.moveToNext());
        }
        db.close();
        return productList;
    }


    public int getProductCount(){
        String countQuery = "SELECT * FROM " + Constantes.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery,null);

        int count = cursor.getCount();

        cursor.close();

        return count;
    }

    public void deleteData(String id) {
       SQLiteDatabase db = this.getWritableDatabase();
       db.delete(Constantes.TABLE_NAME,Constantes.C_ID + " = ?",new String[]{id});
       db.close();
    }

    public void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + Constantes.TABLE_NAME);
        db.close();
    }
}
