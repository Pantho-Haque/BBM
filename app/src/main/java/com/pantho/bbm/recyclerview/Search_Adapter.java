package com.pantho.bbm.recyclerview;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pantho.bbm.R;
import com.pantho.bbm.objects.AccountInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Search_Adapter  extends RecyclerView.Adapter<Search_Adapter.Viewholder> {
    Context context;
    String typename;
    ArrayList<AccountInfo> list;
    public Search_Adapter(Context context, ArrayList<AccountInfo> list,String typename) {
        this.context = context;
        this.list = list;
        this.typename=typename;
    }
    String getTheNumber(AccountInfo a, String tn){
        int thenum = 0;
        if(tn=="apos") thenum=a.getApos();
        else if(tn=="aneg") thenum=a.getAneg();
        else if(tn=="bpos") thenum=a.getBpos();
        else if(tn=="bneg") thenum=a.getBneg();
        else if(tn=="abpos") thenum=a.getAbpos();
        else if(tn=="abneg") thenum=a.getAbneg();
        else if(tn=="opos") thenum=a.getOpos();
        else if(tn=="oneg") thenum=a.getOneg();

        return String.valueOf(thenum);
    };

    @NonNull
    @Override
    public Search_Adapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(context).inflate(R.layout.search_item, parent, false);
        return new Search_Adapter.Viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        AccountInfo a=list.get(position);
        holder.SIname.setText(a.getName());
        holder.SInumber.setText(getTheNumber(a,typename));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // creating view
                View myView = LayoutInflater.from(context).inflate(R.layout.activity_recycler_item, null);
                final AlertDialog dialog = new AlertDialog.Builder(context).setView(myView).create();
                myView.setBackground(Drawable.createFromPath("#fff"));
                dialog.setCancelable(true);


                TextView name,email,phone , address, apos,aneg,abpos,abneg,bpos,bneg,opos,oneg ;
                Button copyEmail,copyPhone;

                name = (TextView) myView.findViewById(R.id.nameRI);
                email = (TextView) myView.findViewById(R.id.emailRI);
                phone =  (TextView) myView.findViewById(R.id.phoneRI);
                address =  (TextView) myView.findViewById(R.id.addressRI);

                apos =  (TextView) myView.findViewById(R.id.aposRI);
                aneg=  (TextView) myView.findViewById(R.id.anegRI);
                bpos =  (TextView) myView.findViewById(R.id.bposRI);
                bneg=  (TextView) myView.findViewById(R.id.bnegRI);
                abpos =  (TextView) myView.findViewById(R.id.abposRI);
                abneg=  (TextView) myView.findViewById(R.id.abnegRI);
                opos =  (TextView) myView.findViewById(R.id.oposRI);
                oneg=  (TextView) myView.findViewById(R.id.onegRI);

                copyEmail=(Button) myView.findViewById(R.id.copyEmail);
                copyPhone=(Button) myView.findViewById(R.id.copyPhone);


                // data setting
                name.setText(a.getName());
                email.setText(a.getEmail());
                phone.setText(a.getPhone());
                address.setText(a.getAddress());

                apos.setText(String.valueOf(a.getApos()));
                aneg.setText(String.valueOf(a.getAneg()));
                bpos.setText(String.valueOf(a.getBpos()));
                bneg.setText(String.valueOf(a.getBneg()));
                abpos.setText(String.valueOf(a.getAbpos()));
                abneg.setText(String.valueOf(a.getAbneg()));
                opos.setText(String.valueOf(a.getOpos()));
                oneg.setText(String.valueOf(a.getOneg()));

                phone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:"+a.getPhone()));
                        context.startActivity(intent);
                    }
                });

                email.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + a.getEmail()));
                        context.startActivity(Intent.createChooser(emailIntent, "Chooser Title"));
                    }
                });


                copyEmail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ClipboardManager clipboard = (ClipboardManager)  view.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("email",a.getEmail());
                        clipboard.setPrimaryClip(clip);

                        Toast.makeText(context, "Email Copied", Toast.LENGTH_SHORT).show();
                    }
                });

                copyPhone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ClipboardManager clipboard = (ClipboardManager)  view.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("phone",a.getPhone());
                        clipboard.setPrimaryClip(clip);

                        Toast.makeText(context, "Phone Number Copied", Toast.LENGTH_SHORT).show();
                    }
                });

                dialog.show();

            }
        });



    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public class Viewholder extends RecyclerView.ViewHolder {
        TextView SIname,SInumber;

        public Viewholder(final View itemView) {
            super(itemView);
            SIname = (TextView) itemView.findViewById(R.id.SIname);
            SInumber = (TextView) itemView.findViewById(R.id.SInumber);
        }

        public void bindData(@NonNull final AccountInfo a) {
            SIname.setText(a.getName());
            SInumber.setText(getTheNumber(a,typename));
        }
    }
}
