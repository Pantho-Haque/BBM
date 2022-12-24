package com.pantho.bbm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pantho.bbm.fragments.fragment_notAuthenticate;
import com.pantho.bbm.fragments.fragment_profile;
import com.pantho.bbm.objects.AccountInfo;

public class Register extends AppCompatActivity {

    private ProgressDialog loader;

    private EditText regName,regEmail, regPwd,regPhone,regAddress , apos , aneg,bpos,bneg,abpos,abneg,opos,oneg ;
    private Button regBtn;
    private Button gtlLayout;
    private FirebaseAuth auth= FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        loader = new ProgressDialog(this);

        regName=findViewById(R.id.nameReg);
        regEmail = findViewById(R.id.emailReg);
        regPwd = findViewById(R.id.passReg);
        regPhone=findViewById(R.id.phoneReg);
        regAddress=findViewById(R.id.addReg);

        // bloods
        apos=findViewById(R.id.aposReg);
        aneg=findViewById(R.id.anegReg);
        bpos=findViewById(R.id.bposReg);
        bneg=findViewById(R.id.bnegReg);
        abpos=findViewById(R.id.abposReg);
        abneg=findViewById(R.id.abnegReg);
        opos=findViewById(R.id.oposReg);
        oneg=findViewById(R.id.onegReg);



        regBtn = findViewById(R.id.regBtn);
        gtlLayout = findViewById(R.id.gtlLayout);

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=regName.getText().toString().trim();
                String email = regEmail.getText().toString().trim();
                String pwd = regPwd.getText().toString().trim();
                String phone=regPhone.getText().toString().trim();
                String address=regAddress.getText().toString().trim();

                int aposint=Integer.parseInt(apos.getText().toString().trim());
                int anegint=Integer.parseInt(aneg.getText().toString().trim());
                int bposint=Integer.parseInt(bpos.getText().toString().trim());
                int bnegint=Integer.parseInt(bneg.getText().toString().trim());
                int abposint=Integer.parseInt(abpos.getText().toString().trim());
                int abnegint=Integer.parseInt(abneg.getText().toString().trim());
                int oposint=Integer.parseInt(opos.getText().toString().trim());
                int onegint=Integer.parseInt(oneg.getText().toString().trim());

                if (TextUtils.isEmpty(name)) {
                    regName.setError("Name Required");
                    return;
                }else if (TextUtils.isEmpty(email)) {
                    regEmail.setError("Email Required");
                    return;
                } else if (TextUtils.isEmpty(pwd)) {
                    regPwd.setError("Password required");
                    return;
                }else if (TextUtils.isEmpty(phone)) {
                    regPhone.setError("Phone Number required");
                    return;
                }else if (TextUtils.isEmpty(address)) {
                    regAddress.setError("Address required");
                    return;
                } else
                {
                    loader.setMessage("Registration in progress");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();


                    auth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {


                                AccountInfo acc = new AccountInfo(name,email,phone , address, aposint,anegint,abposint,abnegint,bposint,bnegint,oposint,onegint);
                                String uid = auth.getCurrentUser().getUid();
                                DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Users");

                                reference.child(uid).setValue(acc).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(Register.this, "You Have Registered successfully", Toast.LENGTH_LONG).show();

                                            startActivity(new Intent(Register.this, MainActivity.class));
                                            finish();
                                        } else {
                                            String err = task.getException().toString();
                                            Toast.makeText(Register.this, "Failed: " + err, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                Log.w("sad", "createUserWithEmail:failure", task.getException());
                                String err = "Error: " + task.getException().toString();
                                Toast.makeText(Register.this, err, Toast.LENGTH_SHORT).show();
                            }
                            loader.dismiss();
                        }
                    });
                }
            }
        });

        gtlLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //startActivity(new Intent(Register.this, Login.class));
            }
        });
}
}