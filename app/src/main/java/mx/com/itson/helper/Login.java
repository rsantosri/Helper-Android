package mx.com.itson.helper;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity implements View.OnClickListener{


    EditText email, contrasena;
    Button login;
    FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.txtEmail);
        contrasena = findViewById(R.id.txtPassword);
        login = findViewById(R.id.login);
        login.setOnClickListener(this);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        findViewById(R.id.lblRegistrarse).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                userLogin();
                break;
            case R.id.lblRegistrarse:
                startActivity(new Intent(this, Signup.class));
                finish();
                break;
        }
    }

    private void userLogin(){
        String correo = email.getText().toString().trim();
        String pass = contrasena.getText().toString().trim();

        if (correo.isEmpty()){
            email.setError("Email es requerido");
            email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
            email.setError("Favor de introducir un correo valido");
            email.requestFocus();
            return;
        }

        if (pass.isEmpty()){
            contrasena.setError("Contraseña requerida");
            contrasena.requestFocus();
            return;
        }

        if (pass.length() < 6){
            contrasena.setError(" La contraseña requiere de al menos 6 caracteres");
            contrasena.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);


        mAuth.signInWithEmailAndPassword(correo, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(Login.this, Principal.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.errorLogin, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(mAuth.getCurrentUser() != null){
            startActivity(new Intent(this, Principal.class));
        }
    }
}