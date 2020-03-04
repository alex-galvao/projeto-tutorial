package com.example.hayashidashboard.Fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hayashidashboard.Dados.ConnectionClass;
import com.example.hayashidashboard.Activitys.MainActivity;
import com.example.hayashidashboard.Dados.PegarDatas;
import com.example.hayashidashboard.R;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFinancas extends Fragment {



    private EditText etDataInicio;
    private EditText etDataFim;
    private TextView tvReceber;
    private TextView tvPagar;
    private TextView tvTotal;
    private Button btnPesquisar;
    private MainActivity mainActivity;
    private Fragment selectedFragment = null;

    private Connection connect;
    private String ConnectionResult = "";
    private Boolean isSuccess = false;
    private Button btnReceber;
    private Button btnCPagar;

    private String i = null;
    private String f = null;
    private PegarDatas pegarDatas;

    private int valorReceberInt;
    private int valorPagarInt;
    private int valorTotal;

    public FragmentFinancas() {
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


        View rootView = inflater.inflate(R.layout.fragment_financas, container, false);

        etDataInicio = rootView.findViewById(R.id.etDataInicio);
        btnReceber = rootView.findViewById(R.id.btnCReceber);
        etDataFim = rootView.findViewById(R.id.etDataFim);
        tvReceber = rootView.findViewById(R.id.tvReceber);
        tvPagar = rootView.findViewById(R.id.tvPagar);
        tvTotal = rootView.findViewById(R.id.tvTotal);
        btnPesquisar = rootView.findViewById(R.id.btnPesquisar);
        btnCPagar = rootView.findViewById(R.id.btnCPagar);
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

        etDataInicio.setInputType(InputType.TYPE_NULL);
        etDataFim.setInputType(InputType.TYPE_NULL);

        btnReceber = rootView.findViewById(R.id.btnCReceber);


        btnReceber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedFragment = new FragmentReceber();
                assert getFragmentManager() != null;
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

            }
        });

        btnCPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedFragment = new FragmentPagar();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

            }
        });


        btnPesquisar.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                i = pegarDatas.setarDataInicio(mainActivity);
                f = pegarDatas.setarDataFim(mainActivity);

                try {

                    ConnectionClass connectionClass = new ConnectionClass();
                    connect = connectionClass.CONN();
                    if (connect == null) {

                        ConnectionResult = "Cheque seu acesso à internet";

                    } else {


                        String queryPesquisaReceber = "SELECT SUM(Valor) FROM CReceber WHERE (Emissao >= '" + i + "' and Vencimento <= '" + f + "')";
                        Statement stmtR = connect.createStatement();
                        ResultSet rsR = stmtR.executeQuery(queryPesquisaReceber);

                        String queryPesquisaPagar = "SELECT SUM(Valor) FROM CPagar WHERE (Emissao >= '" + i + "' and Vencimento <= '" + f + "')";
                        Statement stmtP = connect.createStatement();
                        ResultSet rsP = stmtP.executeQuery(queryPesquisaPagar);

                        while (rsR.next() && rsP.next()) {

                            valorReceberInt = rsR.getInt(1);
                            tvReceber.setVisibility(View.VISIBLE);
                            tvReceber.setText("R$ " + valorReceberInt);

                            valorPagarInt = rsP.getInt(1);
                            tvPagar.setVisibility(View.VISIBLE);
                            tvPagar.setText("R$ " + valorPagarInt);
                        }

                        valorTotal = valorReceberInt - valorPagarInt;

                        if (valorTotal < 0) {

                            tvTotal.setTextColor(Color.parseColor("#E2332E"));

                        } else {
                            tvTotal.setTextColor(R.color.verde);
                        }

                        tvTotal.setText("R$ " + valorTotal);
                        tvTotal.setVisibility(View.VISIBLE);

                        ConnectionResult = "Successful";
                        isSuccess = true;
                        connect.close();

                    }

                } catch (Exception ex) {

                    isSuccess = false;
                    ConnectionResult = ex.getMessage();

                }

            }

        });
        // Inflate the layout for this fragment

        return rootView;

    }

}
