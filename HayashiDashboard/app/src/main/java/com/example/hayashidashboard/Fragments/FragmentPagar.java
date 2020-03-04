package com.example.hayashidashboard.Fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hayashidashboard.Adapters.PagarAdapter;
import com.example.hayashidashboard.Dados.ConnectionClass;
import com.example.hayashidashboard.Activitys.MainActivity;
import com.example.hayashidashboard.Dados.ListPagar;
import com.example.hayashidashboard.Dados.PegarDatas;
import com.example.hayashidashboard.R;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPagar extends Fragment {


    private EditText etDataInicio;
    private EditText etDataFim;
    private MainActivity mainActivity;

    private String i = null;
    private String f = null;
    private Button btnPesquisaPagar;
    private PegarDatas pegarDatas;

    private TextView tvResultado;
    private TextView tvTotal;
    private TextView totalTVPagar;
    private TextView resultadoTVPagar;


    private RecyclerView rvPagar;
    private RecyclerView.LayoutManager pagarLayoutManager;
    private PagarAdapter pagarAdapter;
    private ArrayList<ListPagar> listPagar;

    private Connection connect;
    private String ConnectionResult = "";
    private Boolean isSuccess = false;
    private int resultado = 0;
    private int totalValor;


    public FragmentPagar() {
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
        View rootView = inflater.inflate(R.layout.fragment_pagar, container, false);

        rvPagar = rootView.findViewById(R.id.RV_Pagar);
        rvPagar.setHasFixedSize(true);

        pagarLayoutManager = new LinearLayoutManager(mainActivity);
        rvPagar.setLayoutManager(pagarLayoutManager);
        listPagar = new ArrayList<ListPagar>();
        pagarAdapter = new PagarAdapter(listPagar, mainActivity);

        etDataInicio = rootView.findViewById(R.id.etDataInicioPagar);
        etDataFim = rootView.findViewById(R.id.etDataFimPagar);
        etDataInicio.setInputType(InputType.TYPE_NULL);
        etDataFim.setInputType(InputType.TYPE_NULL);
        btnPesquisaPagar = rootView.findViewById(R.id.btnPesquisarPagar);
        tvResultado = rootView.findViewById(R.id.tvResultadoPagar);
        tvTotal = rootView.findViewById(R.id.tvTotalPagar);
        totalTVPagar = rootView.findViewById(R.id.totalTVPagar);
        resultadoTVPagar = rootView.findViewById(R.id.resultadoTVPagar);
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

                pegarDatas.pegarDataFim(mainActivity,etDataFim);

            }
        });

        btnPesquisaPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                i = pegarDatas.setarDataInicio(mainActivity);
                f = pegarDatas.setarDataFim(mainActivity);
                resultado = 0;

                try {

                    ConnectionClass connectionClass = new ConnectionClass();
                    connect = connectionClass.CONN();
                    if (connect == null) {

                        ConnectionResult = "Cheque seu acesso à internet";

                    } else {

                        String query = "SELECT Descricao, Emissao, Valor FROM CPagar WHERE (Emissao >= '" + i + "' and Vencimento <= '" + f + "')";
                        Statement stmt = connect.createStatement();
                        ResultSet rs = stmt.executeQuery(query);

                        if (rs != null) {

                            while (rs.next()) {

                                ++resultado;

                                try {

                                    listPagar.add(new ListPagar(rs.getString("Descricao"),
                                            String.valueOf(rs.getString("Emissao")),
                                            String.valueOf(rs.getString("Valor"))));

                                }catch (Exception e){e.printStackTrace();}

                                isSuccess = true;

                            }

                            pagarAdapter = new PagarAdapter(listPagar, mainActivity);
                            rvPagar.setAdapter(pagarAdapter);

                            String queryTotal = "SELECT SUM(Valor) FROM Cpagar WHERE (Emissao >= '" + i + "' and Vencimento <= '" + f + "')";
                            Statement stmtT = connect.createStatement();
                            ResultSet rsT = stmtT.executeQuery(queryTotal);

                            while (rsT.next()) {

                                totalValor = rsT.getInt(1);
                                totalTVPagar.setVisibility(View.VISIBLE);
                                tvTotal.setVisibility(View.VISIBLE);
                                tvTotal.setText("R$ " + totalValor);
                            }


                            tvResultado.setText(String.valueOf(resultado));
                            resultadoTVPagar.setVisibility(View.VISIBLE);
                            tvResultado.setVisibility(View.VISIBLE);

                            ConnectionResult = "Successful";
                            isSuccess = true;
                            connect.close();

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
