package com.pantho.bbm.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pantho.bbm.R;
import com.pantho.bbm.objects.AccountInfo;
import com.pantho.bbm.recyclerview.Adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class fragment_home extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<AccountInfo> list;
        list = new ArrayList<>();
        Adapter myAdapter=new Adapter(getActivity(),list);

        ProgressDialog loader=new ProgressDialog(getActivity());

        loader.setMessage("Fetcing Data ... ");
        loader.setCanceledOnTouchOutside(false);
        loader.show();

        // modal
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setReverseLayout(true);
        llm.setStackFromEnd(true);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(myAdapter);


        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Users");

        // read from database
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    AccountInfo model=dataSnapshot.getValue(AccountInfo.class);
                    list.add(model);
                }
                myAdapter.notifyDataSetChanged();
                loader.dismiss();
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("failed", "Failed to read value.", error.toException());
                loader.dismiss();
            }
        });

    }
}