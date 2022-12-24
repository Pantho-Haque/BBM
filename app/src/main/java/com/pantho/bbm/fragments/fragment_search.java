package com.pantho.bbm.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pantho.bbm.R;
import com.pantho.bbm.objects.AccountInfo;
import com.pantho.bbm.recyclerview.Search_Adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class fragment_search extends Fragment {


    String bloodType;
    int numOfBlood;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<AccountInfo> list;
        list = new ArrayList<>();

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setReverseLayout(true);
        llm.setStackFromEnd(true);
        RecyclerView recyclerView = view.findViewById(R.id.searchRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(llm);


        Spinner dynamicSpinner = (Spinner) view.findViewById(R.id.searchspinner);
        String[] items = new String[] { "A+", "A-", "B+","B-", "AB+","AB-", "O+","O-" };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.search_spinner_item, R.id.spinnerText , items);
        dynamicSpinner.setAdapter(adapter);
        dynamicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                switch (position){
                    case 0:
                        bloodType="apos";
                        break;
                    case 1:
                        bloodType="aneg";
                        break;
                    case 2:
                        bloodType="bpos";
                        break;
                    case 3:
                        bloodType="bneg";
                        break;
                    case 4:
                        bloodType="abpos";
                        break;
                    case 5:
                        bloodType="abneg";
                        break;
                    case 6:
                        bloodType="opos";
                        break;
                    case 7:
                        bloodType="oneg";
                        break;
                    default:
                        break;
                }

                Log.d("tag",bloodType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        EditText minBags=view.findViewById(R.id.minBags);
        Button searchbtn=view.findViewById(R.id.searchBtn);


        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String minBag=minBags.getText().toString().trim();


                if(minBag.isEmpty()) numOfBlood=0;
                else numOfBlood= Integer.parseInt(minBag);
                Log.d("num",String.valueOf(numOfBlood));

                Search_Adapter myAdapter=new Search_Adapter(getActivity(),list,bloodType);
                recyclerView.setAdapter(myAdapter);


                DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Users");
                // read from database
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
                            AccountInfo model=dataSnapshot.getValue(AccountInfo.class);
                            if(Integer.parseInt(getTheNumber(model,bloodType))>=numOfBlood){
                                list.add(model);
                            }
                        }
                        class SortByHead implements Comparator<AccountInfo> {
                            // Used for sorting in ascending order of task head
                            public int compare(@NonNull AccountInfo a, @NonNull AccountInfo b)
                            {
                                return getTheNumber(a,bloodType).compareTo(getTheNumber(b,bloodType));

                            }
                        }

                        Collections.sort(list, new SortByHead());
                        myAdapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(DatabaseError error) {
                        Log.w("failed", "Failed to read value.", error.toException());
                    }
                });

            }
        });


    }
}