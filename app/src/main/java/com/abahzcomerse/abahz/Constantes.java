package com.abahzcomerse.abahz;

public class Constantes {


    //SQLite database

    public static final int DB_VERSION = 1;
    public static final String DB_NAME ="ABAHZ_DB";
    public static final String TABLE_NAME ="Abahz";

    //Identifients
    public static final String  C_ID ="ID";
    public static final String C_IMAGE ="IMAGE";
    public static final String C_NAME ="NAME";
    public static final String C_DATE_EXP ="DATE";

    //Quantites
    public static final String C_QTE_ACHAT="QTE_ACHAT";
     public static final String C_QTE_VENTE="QTE_VENTE";
    public static final String C_QTE_STOCK="QTE_STOCK";
   //Prix
    public static final String C_PRIX_ACHAT_U ="PRIX_ACHAT_U";
    public static final String C_PRIX_ACHAT_T = "PRIX_ACHAT_T";

    public static final String C_PRIX_VENTE_U ="PRIX_VENTE_U";
    public static final String C_PRIX_VENTE_T ="PRIX_VENTE_T";

    //Marges brutes
    public static final String C_MARGE_U ="MARGE_U";
    public static final String C_MARGE_T ="MARGE_T";
    //Autres depenses brutes
    public static final String C_DEPENSE_U ="DEPENSE_U";
     public static final String C_DEPENSE_T ="DEPENSE_T";

    //Creation Base de donnees SQLites
    public static final String CREATE_TABLE = "CREATE TABLE  " + TABLE_NAME + "("
            +  C_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +  C_IMAGE+" TEXT,"
            +  C_NAME+" TEXT,"
            +  C_DATE_EXP+" TEXT,"
            +  C_QTE_ACHAT+" TEXT,"
            +  C_QTE_VENTE+" TEXT,"
            +  C_QTE_STOCK+" TEXT,"
            +  C_PRIX_ACHAT_U+" TEXT,"
            +  C_PRIX_ACHAT_T+" TEXT,"
            +  C_PRIX_VENTE_U+" TEXT,"
            +  C_PRIX_VENTE_T+" TEXT,"
            +  C_MARGE_U+" TEXT,"
            +  C_MARGE_T+" TEXT,"
            +  C_DEPENSE_U +" TEXT,"
            +  C_DEPENSE_T +" TEXT"
            +  ")";
}
