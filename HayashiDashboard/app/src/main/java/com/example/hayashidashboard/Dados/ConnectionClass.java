package com.example.hayashidashboard.Dados;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionClass {

    public String IP = "hayashicloud.ddns.net";
    public String classs = "net.sourceforge.jtds.jdbc.Driver";
    public String DB = "BD_Hayashi_Teste";
    public String DBuser = "SA";
    public String DBpassword = "123456*mudar";

    @SuppressLint("NewApi")
    public Connection CONN() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String ConnURL;
        try {
            Class.forName(classs);
            ConnURL = "jdbc:jtds:sqlserver://" + IP + ";"
                    + "databaseName=" + DB + ";user=" + DBuser + ";password="
                    + DBpassword + ";";

            try {
                conn = DriverManager.getConnection(ConnURL);
            }catch (SQLException se)
            {
                Log.e("SALVO", se.getMessage());
            }
            catch (Exception e) {
                Log.e("SALVADO", e.getMessage());
            }

        }
        catch (ClassNotFoundException ce) {

            Log.e("SALVADISSIMO",ce.getMessage() );

        }
        catch (Exception e) {
            Log.e("SALVADO", e.getMessage());
        }
        return conn;
    }
}