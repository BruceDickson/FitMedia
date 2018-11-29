package com.fitmedia.fitmedia;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.joda.time.LocalDate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private String key;

    private TextView data;
    private TextView dataview;
    private TextView txt_data;

    private EditText edt_login;
    private EditText edt_password;
    private EditText edt_fullname;
    private EditText edt_email;

    private RadioGroup rd_type;

    private Button btn_register;

    private String login;
    private String password;
    private String fullname;
    private String email;
    private Integer type;
    private String date;

    private Usuario user;
    private long datelong;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_register = (Button) findViewById(R.id.btn_register);

        edt_login = (EditText) findViewById(R.id.edt_user);
        edt_password = (EditText) findViewById(R.id.edt_password);
        edt_fullname = (EditText) findViewById(R.id.edt_name);
        edt_email = (EditText) findViewById(R.id.edt_email);

        rd_type = (RadioGroup) findViewById(R.id.rd_type);

        txt_data = (TextView) findViewById(R.id.txt_data);


        dataview = findViewById(R.id.txt_data);
        data = findViewById(R.id.txt_data);

        data.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                final int day = c.get(Calendar.DAY_OF_MONTH);
                final SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

                DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        LocalDate localDate = new LocalDate(year, month + 1, dayOfMonth);
                        Date date = localDate.toDate();

                        datelong = date.getTime();
                        dataview.setText(sdfDate.format(date));

                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        calendar.add(Calendar.HOUR, +24);

                    }
                };

                DatePickerDialog dateDialog = new DatePickerDialog(RegisterActivity.this, datePickerListener, year, month, day);
                dateDialog.show();
            }
        });

        rd_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.e("ID GROUP:", checkedId+"");

                switch (checkedId){
                    case R.id.rd_atleta:
                        type = 0;
                        break;
                    case R.id.rd_nutricionista:
                        type = 1;
                        break;
                    case R.id.rd_educador:
                        type = 2;
                        break;

                }
            }
        });

        btn_register.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {



                password = edt_password.getText().toString();
                fullname = edt_fullname.getText().toString();
                email = edt_email.getText().toString();
                date = txt_data.getText().toString();

                user = new Usuario(fullname, type, datelong, new ArrayMap<String, Integer>());

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            key = task.getResult().getUser().getUid();
                            mDatabase.child("users").child(key).setValue(user);

                            mAuth.signInWithEmailAndPassword(email, password)
                                    .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                // Sign in success, update UI with the signed-in user's information
                                                //Log.d(TAG, "signInWithEmail:success");
                                                FirebaseUser user = mAuth.getCurrentUser();

                                            } else {
                                                // If sign in fails, display a message to the user
                                                //Log.w(TAG, "signInWithEmail:failure", task.getException());
                                                Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                            finish();
                        }else{
                            try {
                                throw task.getException();
                            } catch(FirebaseAuthWeakPasswordException e) {
                                Toast.makeText(RegisterActivity.this,"Senha Inválida." ,Toast.LENGTH_SHORT).show();
                            } catch(FirebaseAuthInvalidCredentialsException e) {
                                Toast.makeText(RegisterActivity.this,"Email Inválido." ,Toast.LENGTH_SHORT).show();
                            } catch(FirebaseAuthUserCollisionException e) {
                                Toast.makeText(RegisterActivity.this,"Usuário já cadastrado!" ,Toast.LENGTH_SHORT).show();
                            } catch(Exception e) {
                                Toast.makeText(RegisterActivity.this,"Erro Geral!." ,Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });




    }
}
