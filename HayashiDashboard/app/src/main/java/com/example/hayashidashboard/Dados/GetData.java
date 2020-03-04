package com.example.hayashidashboard.Dados;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetData {

    Connection connect;
    String ConnectionResult = "";
    Boolean isSuccess = false;

    public List<Map<String,String>> getData(){

        List<Map<String,String>> data=null;
        data = new ArrayList<>();

        try {

            ConnectionClass connectionClass = new ConnectionClass();
            connect = connectionClass.CONN();
            if (connect == null) {

                ConnectionResult = "Cheque seu acesso à internet";

            }else {

                String query="SELECT Descricao, Emissao, Valor FROM CReceber WHERE (Emissao >= '2019-09-01 00:00:00.000' and Vencimento <= '2020-02-29 23:59:59.999')";
                Statement stmt = connect.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()){

                    Map<String, String> dataNum = new HashMap<String, String>();
                    dataNum.put("ID_Produto",rs.getString("ID_Produto"));
                    dataNum.put("ID_Venda",rs.getString("ID_Venda"));
                    dataNum.put("Qtd",rs.getString("Quantidade"));

                    data.add(dataNum);

                }

                ConnectionResult = "Successful";
                isSuccess = true;
                connect.close();

            }

        }catch (Exception ex){

            isSuccess = false;
            ConnectionResult = ex.getMessage();

        }
        return data;

    }

}
