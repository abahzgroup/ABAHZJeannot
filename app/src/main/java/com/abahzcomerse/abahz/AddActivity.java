package com.abahzcomerse.abahz;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class AddActivity extends AppCompatActivity {

    TextInputLayout ed_name, ed_qte, ed_pau,ed_pvu, ed_date;
    MaterialButton addBtn;
    ImageView addImage;
    MyDbHelper myDbHelper;
    private Uri imageUri;
    ProgressBar progressBar;

    public static final int CAMERA_REQUEST_CODE = 100;
    public static final int STORAGE_REQUEST_CODE = 101;


    public static final int IMAGE_PICK_CAMERA_CODE = 102;
    public static final int IMAGE_PICK_GALLERY_CODE = 103;

    private String[] cameraPermissions;
    private String[] storagePermissions;
    private String id,name, date,qte,pvu,pau;
    private boolean isEditMode = false;

    ActivityResultLauncher<Intent> activityResultLauncher= registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode()== Activity.RESULT_OK){

                        Intent data = result.getData();
                        imageUri = data.getData();
                        addImage.setImageURI(imageUri);


                    }else {
                        Toast.makeText(AddActivity.this, "Pas d'image", Toast.LENGTH_SHORT).show();
                    }
                }
            });



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        ed_name = findViewById(R.id.ed_name);
        ed_date = findViewById(R.id.date_exp);
        ed_qte = findViewById(R.id.ed_qte);
        ed_pau = findViewById(R.id.ed_prix_achat);
        ed_pvu = findViewById(R.id.ed_prix_vente);
        addImage = findViewById(R.id.add_image);
        progressBar = findViewById(R.id.progrssBar);
        addBtn = findViewById(R.id.btnAdd);
        myDbHelper = new MyDbHelper(this);

        Intent intent = getIntent();
        isEditMode = intent.getBooleanExtra("isEditMode",false);

        if (isEditMode){
            id = intent.getStringExtra("ID");
            name = intent.getStringExtra("NAME");
            qte = intent.getStringExtra("QTE");
            pau = intent.getStringExtra("PRIXA");
            pvu = intent.getStringExtra("PRIXV");
            date = intent.getStringExtra("DATE");
            imageUri = Uri.parse(intent.getStringExtra("IMAGE"));

            ed_name.setHelperText(name);
            ed_qte.setHelperText(qte);
            ed_pau.setHelperText(pau);
            ed_pvu.setHelperText(pvu);
            ed_date.setHelperText(date);

            if (imageUri.toString().equals("null")){
                addImage.setImageResource(R.drawable.baseline_add_photo_alternate_24);
            }else {
                addImage.setImageURI(imageUri);
            }


        }else {

        }

        cameraPermissions = new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        addImage.setOnClickListener(v->{
            imageDialog();
        });
        addBtn.setOnClickListener(v->{
            progressBar.setVisibility(View.VISIBLE);
            if (imageUri!=null){
                insertDatainDB();
            }else {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(this, "Pas d'image", Toast.LENGTH_SHORT).show();
            }

        });
    }


    private void copyFileDirectory(String srcDir, String desDir){
        try {
            File src = new File(srcDir);
            File des = new File(desDir, src.getName());
            if (src.isDirectory()){
                String[] files =src.list();
                int fileslength = files.length;
                for (String file :files){
                    String src1 = new File(src, file).getPath();
                    String des1 = des.getPath();

                    copyFileDirectory(src1,des1);
                }
            }else {
                copyFile(src,des);
            }
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void copyFile(File srcDir, File desDir) throws IOException {
        if (!desDir.getParentFile().exists()){
            desDir.mkdir();
        }
        if (!desDir.exists()) {
            desDir.createNewFile();
        }
        FileChannel source = null;
        FileChannel destination = null;

        try {
            source = new FileInputStream(srcDir).getChannel();
            destination = new FileOutputStream(desDir).getChannel();
            destination.transferFrom(source,0,source.size());

            imageUri = Uri.parse(desDir.getPath());
            Log.d("ImagePath","copyFile:"+imageUri);
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally {
            if (source!=null){
                source.close();
            }
            if (destination!= null){
                destination.close();
            }
        }
    }

    private void insertDatainDB() {
        name = ""+ed_name.getEditText().getText().toString().trim();
        date = ""+ed_date.getEditText().getText().toString().trim();
        qte = ""+ed_qte.getEditText().getText().toString().trim();
        pvu = ""+ed_pvu.getEditText().getText().toString().trim();
        pau = ""+ed_pau.getEditText().getText().toString().trim();

        if (isEditMode){
            myDbHelper.updateData(
                    ""+id,
                    ""+name,
                    ""+imageUri,
                    ""+date,
                    ""+qte,
                    "",
                    "",
                    ""+pau,
                    "",
                    ""+pvu,
                    "",
                    "",
                    "",
                    "",
                    "");
            Toast.makeText(this, "Bien en jour !", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            startActivity(new Intent(AddActivity.this,MainActivity.class));
            finish();
        }else {

            long id = myDbHelper.insertData(
                    ""+name,
                    ""+imageUri,
                    ""+date,
                    ""+qte,
                    "",
                    "",
                    ""+pau,
                    "",
                    ""+pvu,
                    "",
                    "",
                    "",
                    "",
                    ""
            );
            Toast.makeText(this, "Bien jouer !"+id, Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            startActivity(new Intent(AddActivity.this,MainActivity.class));
            finish();
        }

    }

    private void imageDialog() {
        String[] option = {"Stockage", "Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choisir l'image dans:");
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which==0){
                    if (!checkCameraPermission()){
                        requestCameraPermission();
                    }else {
                       // pickFromCamera();
                        pickFromGallery();
                    }
                }else if (which==1){
                    if (!checkStoragePermission()){
                        requestStoragePermission();
                    }else {
                        pickFromGallery();

                    }
                }
            }
        });
        builder.create().show();
    }

    private void pickFromGallery() {
        Intent gallery = new Intent();
        gallery.setAction(Intent.ACTION_GET_CONTENT);
        gallery.setType("image*/");
        activityResultLauncher.launch(gallery);
       // startActivityForResult(gallery,IMAGE_PICK_GALLERY_CODE);
    }

    private void pickFromCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "image Title");
        values.put(MediaStore.Images.Media.DESCRIPTION, "image Description");

        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        camera.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        activityResultLauncher.launch(camera);
        startActivityForResult(camera,IMAGE_PICK_CAMERA_CODE);
    }

    private boolean checkStoragePermission(){

        boolean result = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result;
    }

    private void requestStoragePermission(){
        ActivityCompat.requestPermissions(this,storagePermissions, STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermission(){
        boolean result = ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }
    private void requestCameraPermission(){
        ActivityCompat.requestPermissions(this,cameraPermissions, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case CAMERA_REQUEST_CODE:{
                if (grantResults.length>0){
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (cameraAccepted && storageAccepted){
                        pickFromCamera();
                    }else {
                        Toast.makeText(this, "Autoriser la camera ou le stockage", Toast.LENGTH_SHORT).show();
                    }
                }

            }break;
            case STORAGE_REQUEST_CODE:{
                if (grantResults.length>0){
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (storageAccepted){
                        pickFromGallery();
                    }else {
                        Toast.makeText(this, "Autoriser la gallerie", Toast.LENGTH_SHORT).show();
                    }
                }

            }break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode==RESULT_OK){

            if (requestCode==IMAGE_PICK_GALLERY_CODE){
                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(this);

            }
            else if (requestCode==IMAGE_PICK_CAMERA_CODE){
                CropImage.activity(imageUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(this);


            } else if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (requestCode == RESULT_OK) {
                    Uri resultUri = result.getUri();
                    imageUri = resultUri;
                    addImage.setImageURI(resultUri);

                    copyFileDirectory(""+imageUri.getPath(),""+getDir("SQLiteRecordImage",MODE_PRIVATE));

                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                    Toast.makeText(this, "Erreur de chargement !" + error, Toast.LENGTH_SHORT).show();
                }

            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}