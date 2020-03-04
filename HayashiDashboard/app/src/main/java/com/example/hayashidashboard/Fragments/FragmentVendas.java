package com.example.hayashidashboard.Fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.example.hayashidashboard.Adapters.VendasAdapter;
import com.example.hayashidashboard.Dados.ConnectionClass;
import com.example.hayashidashboard.Activitys.MainActivity;
import com.example.hayashidashboard.Dados.ListVendas;
import com.example.hayashidashboard.Dados.PegarDatas;
import com.example.hayashidashboard.R;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentVendas extends Fragment {


    private PegarDatas pegarDatas;
    private EditText etDataInicio;
    private EditText etDataFim;
    private MainActivity mainActivity;
    private String i = null;
    private String f = null;
    private Button btnPesquisarVendas;

    private TextView tvResultadoVendas;
    private TextView tvTotalVendas;
    private TextView tvPDVVendas;
    private TextView tvNFEVendas;
    private CardView cdvDados;

    private HorizontalScrollView horizontalScrollView;


    private RecyclerView rvVendas;
    private VendasAdapter vendasAdapter;
    private RecyclerView.LayoutManager vendasLayoutManager;
    private ArrayList<ListVendas> listVendas;

    private Connection connect;
    private String ConnectionResult = "";
    private Boolean isSuccess = false;

    private int resultado = 0;
    private int totalVendas;
    private int totalNFE;
    private int totalPDV;


    public FragmentVendas() {
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
        View rootView = inflater.inflate(R.layout.fragment_vendas, container, false);

        rvVendas = rootView.findViewById(R.id.RV_Vendas);
        rvVendas.setHasFixedSize(true);

        vendasLayoutManager = new LinearLayoutManager(mainActivity);
        rvVendas.setLayoutManager(vendasLayoutManager);
        listVendas = new ArrayList<ListVendas>();
        vendasAdapter = new VendasAdapter(listVendas, mainActivity);

        tvResultadoVendas = rootView.findViewById(R.id.tvResultadosVendas);
        tvTotalVendas = rootView.findViewById(R.id.tvTotalVendas);
        tvNFEVendas = rootView.findViewById(R.id.tvNFE);
        tvPDVVendas = rootView.findViewById(R.id.tvPDV);
        cdvDados = rootView.findViewById(R.id.cdvDados);

        horizontalScrollView = rootView.findViewById(R.id.hsvID);

        btnPesquisarVendas = rootView.findViewById(R.id.btnPesquisarVendas);

        btnPesquisarVendas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listVendas.removeAll(listVendas);
                i = pegarDatas.setarDataInicio(mainActivity);
                f = pegarDatas.setarDataFim(mainActivity);
                cdvDados.setVisibility(View.VISIBLE);
                resultado = 0;

                try {

                    ConnectionClass connectionClass = new ConnectionClass();
                    connect = connectionClass.CONN();
                    if (connect == null) {

                        ConnectionResult = "Cheque seu acesso à internet";

                    } else {

                        String query = "SELECT NaturezaOperacao, DataEmissao, ValorTotal FROM NotaFiscal WHERE (DataEmissao >= '" + i + "' and DataSaida <= '" + f + "') AND (NaturezaOperacao != 'DEVOLUÇÃO DE MERCADORIA')";
                        Statement stmt = connect.createStatement();
                        ResultSet rs = stmt.executeQuery(query);

                        if (rs != null) {

                            while (rs.next()) {

                                ++resultado;

                                try {

                                    listVendas.add(new ListVendas(rs.getString("NaturezaOperacao"),
                                            String.valueOf(rs.getString("DataEmissao")),
                                                    String.valueOf(rs.getString("ValorTotal"))));

                                }catch (Exception e){ e.printStackTrace();}

                                isSuccess = true;

                            }

                            vendasAdapter = new VendasAdapter(listVendas, mainActivity);
                            rvVendas.setAdapter(vendasAdapter);

                            String queryTotal = "SELECT SUM(ValorTotal) FROM NotaFiscal WHERE (DataEmissao >= '" + i + "' and DataSaida <= '" + f + "')";
                            Statement stmtT = connect.createStatement();
                            ResultSet rsT = stmtT.executeQuery(queryTotal);

                            while (rsT.next()) {

                                totalVendas = rsT.getInt(1);
                                tvTotalVendas.setText("R$ " + totalVendas);
                            }

                            String queryNFE = "SELECT SUM(ValorTotal) FROM NotaFiscal WHERE (DataEmissao >= '" + i + "' and DataSaida <= '" + f + "' and NaturezaOperacao = 'VENDA DE MERCADORIA')";
                            Statement stmtN = connect.createStatement();
                            ResultSet rsN = stmtN.executeQuery(queryNFE);

                            while (rsN.next()) {

                                totalNFE = rsN.getInt(1);
                                tvNFEVendas.setText("R$ " + totalNFE);
                            }

                            String queryPDV = "SELECT SUM(ValorTotal) FROM NotaFiscal WHERE (DataEmissao >= '" + i + "' and DataSaida <= '" + f + "' and NaturezaOperacao = 'VENDA CONSUMIDOR')";
                            Statement stmtP = connect.createStatement();
                            ResultSet rsP = stmtP.executeQuery(queryPDV);

                            while (rsP.next()) {

                                totalPDV = rsP.getInt(1);
                                tvPDVVendas.setText("R$ " + totalPDV);
                            }

                            tvResultadoVendas.setText(String.valueOf(resultado));


                            ConnectionResult = "Successful";
                            isSuccess = true;
                            connect.close();
                            horizontalScrollView.setVisibility(View.VISIBLE);

                        } else {

                            isSuccess = false;

                        }

                    }

                } catch (Exception ex) {

                    horizontalScrollView.setVisibility(View.GONE);
                    isSuccess = false;
                    ConnectionResult = ex.getMessage();

                }

            }
        });


        etDataInicio = rootView.findViewById(R.id.etDataInicioVendas);
        etDataFim = rootView.findViewById(R.id.etDataFimVendas);
        etDataInicio.setInputType(InputType.TYPE_NULL);
        etDataFim.setInputType(InputType.TYPE_NULL);
        pegarDatas = new PegarDatas();

        etDataInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pegarDatas.pegarDataInicio(mainActivity, etDataInicio);
            }
        });

        etDataFim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pegarDatas.pegarDataFim(mainActivity, etDataFim);
            }
        });


        return rootView;
    }

}
