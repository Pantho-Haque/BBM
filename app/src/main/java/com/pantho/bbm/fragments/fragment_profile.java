package com.pantho.bbm.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pantho.bbm.MainActivity;
import com.pantho.bbm.R;
import com.pantho.bbm.Register;
import com.pantho.bbm.objects.AccountInfo;


public class fragment_profile extends Fragment {


    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private String id = auth.getCurrentUser().getUid();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FloatingActionButton logoutBtn = view.findViewById(R.id.logout);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View myView = LayoutInflater.from(view.getContext()).inflate(R.layout.logout_alert, null);
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(view.getContext()).setView(myView);
                alertDialog.setCancelable(false);

                final AlertDialog dialog = alertDialog.show();

                Button posLogout = myView.findViewById(R.id.posLogout),
                        negLogout = myView.findViewById(R.id.negLogout);

                posLogout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        auth.signOut();
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.frame_layout, new fragment_notAuthenticate());
                        ft.commit();
                        getActivity().getFragmentManager().popBackStack();

                        dialog.dismiss();
                    }
                });


                negLogout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });


            }
        });

        ProgressDialog loader = new ProgressDialog(view.getContext());

        TextView profName = view.findViewById(R.id.nameProf);
        EditText profEmail = view.findViewById(R.id.emailProf),
                profPhone = view.findViewById(R.id.phoneProf),
                profAddress = view.findViewById(R.id.addProf),
                aposProf = view.findViewById(R.id.aposProf),
                anegProf = view.findViewById(R.id.anegProf),
                bposProf = view.findViewById(R.id.bposProf),
                bnegProf = view.findViewById(R.id.bnegProf),
                abposProf = view.findViewById(R.id.abposProf),
                abnegProf = view.findViewById(R.id.abnegProf),
                oposProf = view.findViewById(R.id.oposProf),
                onegProf = view.findViewById(R.id.onegProf);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");

        // read from database
        reference.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AccountInfo acc = (AccountInfo) snapshot.getValue(AccountInfo.class);
                profName.setText(acc.getName());
                profEmail.setText(acc.getEmail());
                profPhone.setText(acc.getPhone());
                profAddress.setText(acc.getAddress());
                aposProf.setText(String.valueOf(acc.getApos()));
                anegProf.setText(String.valueOf(acc.getAneg()));
                bposProf.setText(String.valueOf(acc.getBpos()));
                bnegProf.setText(String.valueOf(acc.getBneg()));
                abposProf.setText(String.valueOf(acc.getAbpos()));
                abnegProf.setText(String.valueOf(acc.getAbneg()));
                oposProf.setText(String.valueOf(acc.getOpos()));
                onegProf.setText(String.valueOf(acc.getOneg()));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("failed", "Failed to read value.", error.toException());

            }
        });

        Button updateBtn=view.findViewById(R.id.updateBtn);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=profName.getText().toString().trim();
                String email = profEmail.getText().toString().trim();
                String phone=profPhone.getText().toString().trim();
                String address=profAddress.getText().toString().trim();

                int aposint=Integer.parseInt(aposProf.getText().toString().trim());
                int anegint=Integer.parseInt(anegProf.getText().toString().trim());
                int bposint=Integer.parseInt(bposProf.getText().toString().trim());
                int bnegint=Integer.parseInt(bnegProf.getText().toString().trim());
                int abposint=Integer.parseInt(abposProf.getText().toString().trim());
                int abnegint=Integer.parseInt(abnegProf.getText().toString().trim());
                int oposint=Integer.parseInt(oposProf.getText().toString().trim());
                int onegint=Integer.parseInt(onegProf.getText().toString().trim());

                if (TextUtils.isEmpty(email)) {
                    profEmail.setError("Email Required");
                    return;
                }else if (TextUtils.isEmpty(phone)) {
                    profPhone.setError("Phone Number required");
                    return;
                }else if (TextUtils.isEmpty(address)) {
                    profAddress.setError("Address required");
                    return;
                } else
                {
                    loader.setMessage("Update in progress");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    AccountInfo acc = new AccountInfo(name,email,phone , address, aposint,anegint,abposint,abnegint,bposint,bnegint,oposint,onegint);
                    DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Users");

                    reference.child(id).setValue(acc).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(view.getContext(), "Data Has been updated successfully", Toast.LENGTH_LONG).show();
                            } else {
                                String err = task.getException().toString();
                                Toast.makeText(view.getContext(), "Failed: " + err, Toast.LENGTH_SHORT).show();
                            }
                            loader.dismiss();
                        }
                    });
                }


            }
        });


    }
}