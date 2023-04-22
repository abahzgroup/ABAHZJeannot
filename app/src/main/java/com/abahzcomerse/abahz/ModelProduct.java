package com.abahzcomerse.abahz;

public class ModelProduct {
    String id, name, image, prixA, prixV,qte,date;

    public ModelProduct(String id, String name, String image, String prixA, String prixV, String qte, String date) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.prixA = prixA;
        this.prixV = prixV;
        this.qte = qte;
        this.date = date;

    }

    public ModelProduct(String name, String image, String prixA, String prixV, String qte, String date) {
        this.name = name;
        this.image = image;
        this.prixA = prixA;
        this.prixV = prixV;
        this.qte = qte;
        this.date = date;
    }

    public ModelProduct() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrixA() {
        return prixA;
    }

    public void setPrixA(String prixA) {
        this.prixA = prixA;
    }

    public String getPrixV() {
        return prixV;
    }

    public void setPrixV(String prixV) {
        this.prixV = prixV;
    }

    public String getQte() {
        return qte;
    }

    public void setQte(String qte) {
        this.qte = qte;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
