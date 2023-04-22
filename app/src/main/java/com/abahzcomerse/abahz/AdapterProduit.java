package com.abahzcomerse.abahz;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterProduit extends RecyclerView.Adapter<AdapterProduit.MyViewHolder>{

    Context context;
    ArrayList<ModelProduct> list;
    MyDbHelper dbHelper;

    public AdapterProduit(Context context, ArrayList<ModelProduct> list) {
        this.context = context;
        this.list = list;
        dbHelper = new MyDbHelper(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_produit,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {



        ModelProduct model = list.get(position);
        String id = model.getId();
        String name = model.getName();
        String image = model.getImage();
        String qte = model.getQte();
        String prixA = model.getPrixA();
        String prixV = model.getPrixV();
        String date = model.getDate();

        holder.name_item.setText(name);
        holder.qte_item.setText(qte);
        holder.prixA_item.setText(prixA);
        holder.prixV_item.setText(prixV);
        holder.date_item.setText(date);

        if (image.equals("null")){
            holder.image_item.setImageResource(R.drawable.baseline_add_photo_alternate_24);
        }else {
            holder.image_item.setImageURI(Uri.parse(image));
        }


        holder.itemView.setOnClickListener(v->{
            Intent intent = new Intent(context,DetailActivity.class);
            intent.putExtra("PROD_ID",id);
            context.startActivity(intent);
        });

        holder.more_item.setOnClickListener(v->{
           showMoreDialog(
                   ""+position,
                   ""+ id,
                   ""+name,
                   ""+qte,
                   ""+prixA,
                   ""+prixV,
                   ""+date,
                   ""+image
           );
        });
    }

    private void showMoreDialog(String position, String id, String name, String qte, String prixA, String prixV, String date, String image) {
        
        String [] options = {"Modifier", "Supprimer"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which==0){
                    Intent intent = new Intent(context,AddActivity.class);
                    intent.putExtra("ID",id);
                    intent.putExtra("NAME",name);
                    intent.putExtra("QTE",qte);
                    intent.putExtra("PRIXA",prixA);
                    intent.putExtra("PRIXV",prixV);
                    intent.putExtra("DATE",date);
                    intent.putExtra("IMAGE",image);
                    intent.putExtra("isEditMode",true);
                    context.startActivity(intent);

                } else if (which==1) {
                    dbHelper.deleteData(id);
                    ((MainActivity)context).onResume();
                }
            }
        });
        builder.create().show();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image_item;
        ImageButton more_item;
        TextView name_item;
        TextView qte_item;
        TextView prixA_item;
        TextView prixV_item;
        TextView date_item;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            image_item= itemView.findViewById(R.id.imge_item);
            more_item= itemView.findViewById(R.id.more_item);
            name_item= itemView.findViewById(R.id.name_item);
            qte_item= itemView.findViewById(R.id.qte_item);
            prixA_item= itemView.findViewById(R.id.prixA_item);
            prixV_item= itemView.findViewById(R.id.prixV_item);
            date_item= itemView.findViewById(R.id.date_item);
        }
    }
}
