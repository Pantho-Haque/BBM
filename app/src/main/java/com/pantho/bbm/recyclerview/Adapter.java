package com.pantho.bbm.recyclerview;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pantho.bbm.R;
import com.pantho.bbm.objects.AccountInfo;

import java.util.ArrayList;


public class Adapter extends RecyclerView.Adapter<Adapter.Viewholder> {
    Context context;
    ArrayList<AccountInfo> list;
    public Adapter(Context context, ArrayList<AccountInfo> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public Adapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(context).inflate(R.layout.activity_recycler_item, parent, false);
        return new Viewholder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        AccountInfo a=list.get(position);
        holder.name.setText(a.getName());
        holder.email.setText(a.getEmail());
        holder.phone.setText(a.getPhone());
        holder.address.setText(a.getAddress());

        holder.apos.setText(String.valueOf(a.getApos()));
        holder.aneg.setText(String.valueOf(a.getAneg()));
        holder.bpos.setText(String.valueOf(a.getBpos()));
        holder.bneg.setText(String.valueOf(a.getBneg()));
        holder.abpos.setText(String.valueOf(a.getAbpos()));
        holder.abneg.setText(String.valueOf(a.getAbneg()));
        holder.opos.setText(String.valueOf(a.getOpos()));
        holder.oneg.setText(String.valueOf(a.getOneg()));

        holder.phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+a.getPhone()));
                context.startActivity(intent);
            }
        });

        holder.email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + a.getEmail()));
                context.startActivity(Intent.createChooser(emailIntent, "Chooser Title"));
            }
        });


        holder.copyEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager)  view.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("email",a.getEmail());
                clipboard.setPrimaryClip(clip);

                Toast.makeText(context, "Email Copied", Toast.LENGTH_SHORT).show();
            }
        });

        holder.copyPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager)  view.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("phone",a.getPhone());
                clipboard.setPrimaryClip(clip);

                Toast.makeText(context, "Phone Number Copied", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }




    public class Viewholder extends RecyclerView.ViewHolder {
        private TextView name,email,phone , address, apos,aneg,abpos,abneg,bpos,bneg,opos,oneg ;
        private Button copyEmail,copyPhone;

        public Viewholder(final View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.nameRI);
            email = (TextView) itemView.findViewById(R.id.emailRI);
            phone =  (TextView) itemView.findViewById(R.id.phoneRI);
            address =  (TextView) itemView.findViewById(R.id.addressRI);

            apos =  (TextView) itemView.findViewById(R.id.aposRI);
            aneg=  (TextView) itemView.findViewById(R.id.anegRI);
            bpos =  (TextView) itemView.findViewById(R.id.bposRI);
            bneg=  (TextView) itemView.findViewById(R.id.bnegRI);
            abpos =  (TextView) itemView.findViewById(R.id.abposRI);
            abneg=  (TextView) itemView.findViewById(R.id.abnegRI);
            opos =  (TextView) itemView.findViewById(R.id.oposRI);
            oneg=  (TextView) itemView.findViewById(R.id.onegRI);

            copyEmail=(Button) itemView.findViewById(R.id.copyEmail);
            copyPhone=(Button) itemView.findViewById(R.id.copyPhone);
        }

        public void bindData(@NonNull final AccountInfo a) {
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

        }
    }
}