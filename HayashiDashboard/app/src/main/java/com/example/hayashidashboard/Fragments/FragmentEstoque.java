package com.example.hayashidashboard.Fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.hayashidashboard.Adapters.EstoqueAdapter;
import com.example.hayashidashboard.Dados.ConnectionClass;
import com.example.hayashidashboard.Activitys.MainActivity;
import com.example.hayashidashboard.Dados.ListProdutos;
import com.example.hayashidashboard.R;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentEstoque extends Fragment {

    private Button btnPesquisar;
    private EstoqueAdapter estoqueAdapter;
    private Spinner spnProdutos;
    RecyclerView rvProdutos;
    RecyclerView.LayoutManager produtosLayoutManager;
    private Connection connect;
    private String ConnectionResult = "";
    private Boolean isSuccess = false;
    private ArrayList<ListProdutos> listProdutos;
    MainActivity mainActivity;


    public FragmentEstoque() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) getActivity();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_estoque, container, false);

        ArrayList<String> produtos;
        rvProdutos = rootView.findViewById(R.id.RV_Estoque);
        rvProdutos.setHasFixedSize(true);

        produtosLayoutManager = new LinearLayoutManager(mainActivity);
        rvProdutos.setLayoutManager(produtosLayoutManager);
        listProdutos = new ArrayList<ListProdutos>();
        estoqueAdapter = new EstoqueAdapter(listProdutos, mainActivity);

        spnProdutos = rootView.findViewById(R.id.spnProdutos);
        btnPesquisar = rootView.findViewById(R.id.btnPesquisarEstoque);
        mainActivity = new MainActivity();
        produtos = new ArrayList<>();
        produtos.add("TODOS");

        try {

            ConnectionClass connectionClass = new ConnectionClass();
            connect = connectionClass.CONN();
            if (connect == null) {

                ConnectionResult = "Cheque seu acesso à internet";

            } else {

                String queryProduto = "SELECT Descricao FROM GrupoNivel";
                Statement stmtP = connect.createStatement();
                ResultSet rsP = stmtP.executeQuery(queryProduto);

                while (rsP.next()) {


                    produtos.add(rsP.getString("Descricao"));

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                            android.R.layout.simple_spinner_item, produtos);
                    adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                    spnProdutos.setAdapter(adapter);

                }


                ConnectionResult = "Successful";
                isSuccess = true;
                connect.close();

            }

        } catch (Exception ex) {

            isSuccess = false;
            ConnectionResult = ex.getMessage();

        }

        btnPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    listProdutos.removeAll(listProdutos);
                    String produtoSelecionado = spnProdutos.getSelectedItem().toString();
                    String grupoProduto = " WHERE GrupoNivel.Descricao = '" + produtoSelecionado + "'";
                    if (produtoSelecionado.equals("TODOS")) {
                        grupoProduto = "";
                    }

                    try {

                        ConnectionClass connectionClass = new ConnectionClass();
                        connect = connectionClass.CONN();
                        if (connect == null) {

                            ConnectionResult = "Cheque seu acesso à internet";

                        } else {
                            String queryEstoque = "SELECT" +
                                    " Produto_Servico.ID, Produto_Servico.Descricao, EstoqueAtual, EstoqueIdeal, EstoqueMinimo" +
                                    " FROM" +
                                    " Produto_Estoque" +
                                    " INNER JOIN" +
                                    " GrupoNivel" +
                                    " ON" +
                                    " GrupoNivel.ID = Produto_Estoque.ID_Produto" +
                                    " INNER JOIN Produto_Servico" +
                                    " ON" +
                                    " Produto_Servico.ID_Grupo = GrupoNivel.ID" + grupoProduto;

                            Statement stmtE = connect.createStatement();
                            ResultSet rsE = stmtE.executeQuery(queryEstoque);

                            if (rsE != null) {

                                while (rsE.next()) {

                                    try {

                                        listProdutos.add(new ListProdutos(String.valueOf(rsE.getString("ID")),
                                                rsE.getString("Descricao"),
                                                String.valueOf(rsE.getString("EstoqueMinimo")),
                                                String.valueOf(rsE.getString("EstoqueAtual")),
                                                String.valueOf(rsE.getString("EstoqueIdeal"))));

                                    } catch (Exception ex) {

                                        ex.printStackTrace();

                                    }

                                    isSuccess = true;


                                }

                                estoqueAdapter = new EstoqueAdapter(listProdutos, mainActivity);
                                rvProdutos.setAdapter(estoqueAdapter);

                            } else {

                                isSuccess = false;

                            }

                        }

                    } catch (Exception ex) {

                        isSuccess = false;
                        ConnectionResult = ex.getMessage();

                    }

                }

        });


        return rootView;
    }

}
