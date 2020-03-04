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

import com.example.hayashidashboard.Adapters.ReceberAdapter;
import com.example.hayashidashboard.Dados.ConnectionClass;
import com.example.hayashidashboard.Activitys.MainActivity;
import com.example.hayashidashboard.Dados.ListReceber;
import com.example.hayashidashboard.Dados.PegarDatas;
import com.example.hayashidashboard.R;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentReceber extends Fragment {

    private EditText etDataInicio;
    private EditText etDataFim;
    private MainActivity mainActivity;

    private Button btnPesquisarReceber;

    private TextView tvResultado;
    private TextView tvTotal;
    private TextView resultadoTV;
    private TextView totalTV;

    private RecyclerView rvReceber;
    private RecyclerView.LayoutManager receberLayoutManager;
    private ArrayList<ListReceber> listReceber;
    private ReceberAdapter receberAdapter;

    private PegarDatas pegarDatas;

    private String i = null;
    private String f = null;

    private Connection connect;
    private String ConnectionResult = "";
    private Boolean isSuccess = false;
    private int resultado = 0;
    private int totalValor;


    public FragmentReceber() {
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
        View rootView = inflater.inflate(R.layout.fragment_receber, container, false);

        rvReceber = rootView.findViewById(R.id.RV_Receber);
        rvReceber.setHasFixedSize(true);

        receberLayoutManager = new LinearLayoutManager(mainActivity);
        rvReceber.setLayoutManager(receberLayoutManager);
        listReceber = new ArrayList<ListReceber>();
        receberAdapter = new ReceberAdapter(listReceber, mainActivity);

        etDataInicio = rootView.findViewById(R.id.etDataInicioReceber);
        etDataFim = rootView.findViewById(R.id.etDataFimReceber);
        etDataInicio.setInputType(InputType.TYPE_NULL);
        etDataFim.setInputType(InputType.TYPE_NULL);
        btnPesquisarReceber = rootView.findViewById(R.id.btnPesquisarReceber);
        tvResultado = rootView.findViewById(R.id.tvResultadosReceber);
        tvTotal = rootView.findViewById(R.id.tvTotalReceber);
        resultadoTV = rootView.findViewById(R.id.resultadoTV);
        totalTV = rootView.findViewById(R.id.totalTV);
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



        btnPesquisarReceber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resultado = 0;
                i = pegarDatas.setarDataInicio(mainActivity);
                f = pegarDatas.setarDataFim(mainActivity);

                try {

                    ConnectionClass connectionClass = new ConnectionClass();
                    connect = connectionClass.CONN();
                    if (connect == null) {

                        ConnectionResult = "Cheque seu acesso à internet";

                    } else {

                        String query = "SELECT Descricao, Emissao, Valor FROM CReceber WHERE (Emissao >= '" + i + "' and Vencimento <= '" + f + "')";
                        Statement stmt = connect.createStatement();
                        ResultSet rs = stmt.executeQuery(query);

                        if (rs != null) {

                            while (rs.next()) {

                                ++resultado;

                                try {

                                    listReceber.add(new ListReceber(rs.getString("Descricao"),
                                            String.valueOf(rs.getString("Emissao")),
                                            String.valueOf(rs.getString("Valor"))));

                                }catch (Exception e){e.printStackTrace();}

                                isSuccess = true;

                            }

                            receberAdapter = new ReceberAdapter(listReceber, mainActivity);
                            rvReceber.setAdapter(receberAdapter);

                            String queryTotal = "SELECT SUM(Valor) FROM CReceber WHERE (Emissao >= '" + i + "' and Vencimento <= '" + f + "')";
                            Statement stmtT = connect.createStatement();
                            ResultSet rsT = stmtT.executeQuery(queryTotal);


                            while (rsT.next()) {

                                totalValor = rsT.getInt(1);
                                totalTV.setVisibility(View.VISIBLE);
                                tvTotal.setVisibility(View.VISIBLE);
                                tvTotal.setText("R$ " + totalValor);
                            }

                            tvResultado.setText(String.valueOf(resultado));
                            resultadoTV.setVisibility(View.VISIBLE);
                            tvResultado.setVisibility(View.VISIBLE);

                            ConnectionResult = "Successful";
                            isSuccess = true;
                            connect.close();

                        } else {isSuccess = false;}

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
