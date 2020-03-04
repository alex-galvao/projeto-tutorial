package com.example.testerecycler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnPesquiasr;
    private ArrayList<ListProdutos> listProdutos;
    private Myadapter myAdapter;
    private RecyclerView rvProdutos;
    private RecyclerView.LayoutManager produtosLayoutManager;
    private boolean success = false;
    private ConnectionClass connectionClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvProdutos = findViewById(R.id.rvProdutos);
        rvProdutos.setHasFixedSize(true);

        produtosLayoutManager = new LinearLayoutManager(this);
        rvProdutos.setLayoutManager(produtosLayoutManager);

        connectionClass = new ConnectionClass();
        listProdutos = new ArrayList<ListProdutos>();
        btnPesquiasr = findViewById(R.id.btnPesquisar);

        btnPesquiasr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    Connection conn = connectionClass.CONN();
                    if (conn == null){

                        success = false;

                    } else {

                        String queryProdutos = "SELECT Produto_Servico.ID, Produto_Servico.Descricao, EstoqueAtual, EstoqueIdeal, EstoqueMinimo FROM Produto_Estoque INNER JOIN GrupoNivel ON GrupoNivel.ID = Produto_Estoque.ID_Produto INNER JOIN Produto_Servico ON Produto_Servico.ID_Grupo = GrupoNivel.ID";
                        Statement stmtP = conn.createStatement();
                        ResultSet rsP = stmtP.executeQuery(queryProdutos);

                        if (rsP != null ) {

                            while (rsP.next()){

                                try {

                                    listProdutos.add(new ListProdutos(rsP.getString("Descricao"),
                                            String.valueOf(rsP.getString("ID")),
                                                    String.valueOf(rsP.getString("EstoqueMinimo")),
                                                            String.valueOf(rsP.getString("EstoqueAtual")),
                                                                    String.valueOf(rsP.getString("EstoqueIdeal"))));

                                }catch (Exception ex){

                                    ex.printStackTrace();

                                }

                                success = true;

                            }

                            myAdapter = new Myadapter(listProdutos, MainActivity.this);
                            rvProdutos.setAdapter(myAdapter);

                        } else {

                            success = false;

                        }

                    }


                } catch (Exception e){

                    e.printStackTrace();
                    Writer writer = new StringWriter();
                    e.printStackTrace(new PrintWriter(writer));
                    success = false;

                }

            }
        });

    }
}

