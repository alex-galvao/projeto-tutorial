package com.example.hayashidashboard.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.hayashidashboard.Dados.ConnectionClass;
import com.example.hayashidashboard.R;

import java.sql.Connection;

public class LoginActivity extends AppCompatActivity {

    ProgressBar pbLogin;
    EditText etServidor;
    EditText etBanco;
    EditText etUsuario;
    EditText etSenha;
    Button btnConectar;
    Connection connect;
    ConnectionClass connectionClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        pbLogin = findViewById(R.id.pbLogin);
        etServidor = findViewById(R.id.etServidor);
        etBanco = findViewById(R.id.etBD);
        etUsuario = findViewById(R.id.etUsuario);
        etSenha = findViewById(R.id.etSenha);
        btnConectar = findViewById(R.id.btnConectar);

        btnConectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                entrar();

            }
        });

    }

    private void entrar(){

        String servidor = etServidor.getText().toString();
        String banco = etBanco.getText().toString();
        String usuario = etUsuario.getText().toString();
        String senha = etSenha.getText().toString();
        connectionClass = new ConnectionClass();
        connect = connectionClass.CONN();

        if (connect == null){

            Toast.makeText(LoginActivity.this, "Verifique se você esta conectado à internet", Toast.LENGTH_SHORT).show();
        } else {

            if (!servidor.equals(connectionClass.IP)) {

                Toast.makeText(LoginActivity.this, "Servidor Incorreto", Toast.LENGTH_SHORT).show();

            } else if (!banco.equals(connectionClass.DB)) {

                Toast.makeText(LoginActivity.this, "Banco de Dados Incorreto", Toast.LENGTH_SHORT).show();
            } else if (!usuario.equals(connectionClass.DBuser)) {

                Toast.makeText(LoginActivity.this, "Usuario Incorreto", Toast.LENGTH_SHORT).show();
            } else if (!senha.equals(connectionClass.DBpassword)) {

                Toast.makeText(LoginActivity.this, "Senha Incorreta", Toast.LENGTH_SHORT).show();
            } else {

                pbLogin.setVisibility(View.VISIBLE);
                pbLogin.setIndeterminate(true);
                Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(mainIntent);
                this.finish();

            }

        }

    }
}
