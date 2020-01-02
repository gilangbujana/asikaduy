package com.asikaduy.aplikasimonitoringakademikuniversitasyarsi;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.levitnudi.legacytableview.LegacyTableView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class KhsActivity extends AppCompatActivity {

    TextView tv_nama, tv_npm, tv_pa, tv_pp, tv_fakultas, tv_ps, tv_jmlsks,
            tv_jmlsksltl, tv_jmlmkltl, tv_jn, tv_ips, tv_ipk;
    String nama = "", npm1="", pa="", fakultas="", pp="";
    LegacyTableView legacyTableView;
    JSONParser jsonParserkhs = new JSONParser();;
    JSONArray jsonArray;
    int jmlsksl, jmlskstl, jmlmkl, jmlmktl,jmlsks;
    double totalnilai, ips, ipk;
    String ip="", npm="", pesan="";
    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khs);

        ip = jsonParserkhs.getIP();
        tv_nama = (TextView) findViewById(R.id.tv_nama_khs);
        tv_npm = (TextView) findViewById(R.id.tv_npm_khs);
        tv_pa = (TextView) findViewById(R.id.tv_pa_khs);
        tv_pp = (TextView) findViewById(R.id.tv_pp_khs);
        tv_fakultas = (TextView) findViewById(R.id.tv_fakultas_khs);
        tv_ps = (TextView) findViewById(R.id.tv_ps_khs);
        tv_jmlsks = (TextView) findViewById(R.id.tv_jmlsks_khs);
        tv_jmlsksltl = (TextView) findViewById(R.id.tv_jmlsksltl_khs);
        tv_jmlmkltl = (TextView) findViewById(R.id.tv_jmlmkltl_khs);
        tv_jn = (TextView) findViewById(R.id.tv_jn_khs);
        tv_ips = (TextView) findViewById(R.id.tv_ips_khs);
        tv_ipk = (TextView) findViewById(R.id.tv_ipk_khs);
        npm = getIntent().getStringExtra("pk");
        //Toast.makeText(this, npm, Toast.LENGTH_SHORT).show();
        LegacyTableView.insertLegacyTitle("NO", "SMT", "KODE MK", "MATA KULIAH", "SKS", "GRADE", "TOTAL BOBOT", "KETERANGAN");
        legacyTableView = (LegacyTableView) findViewById(R.id.legacy_tabel_khs);
        new LoadData().execute();

    }

    class LoadData extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(KhsActivity.this);
            pDialog.setMessage("Load data. Silahkan Tunggu...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("npm", npm.trim()));
            params.add(new BasicNameValuePair("trig", "1"));
            JSONObject jsonObjectkhs = jsonParserkhs.makeHttpRequest(ip+"kartuhasil.php", "GET", params);
            nama = jsonObjectkhs.toString();

            try {
                nama = jsonObjectkhs.getString("nama_mahasiswa");
                npm1 = jsonObjectkhs.getString("npm");
                pa = jsonObjectkhs.getString("pa");
                fakultas = jsonObjectkhs.getString("fakultas");
                pp = jsonObjectkhs.getString("pp");




                jsonArray = jsonObjectkhs.getJSONArray("khs");
                for(int i=0; i < jsonArray.length(); i++){
                    JSONObject obj = jsonArray.getJSONObject(i);
                    int smt = obj.getInt("smt");
                    String kode_mk = obj.getString("kode_mk");
                    String mata_kuliah = obj.getString("mata_kuliah");
                    int sks = obj.getInt("sks");

                    String grade = obj.getString("grade");
                    double bobot = 0;
                    if(grade.equals("A")){
                        bobot = sks * 4;
                    }else if(grade.equals("A-")){
                        bobot = sks * 3.75;
                    }else if(grade.equals("B+")){
                        bobot = sks * 3.50;
                    }else if(grade.equals("B")){
                        bobot = sks * 3;
                    }else if(grade.equals("B-")){
                        bobot = sks * 2.75;
                    }else if(grade.equals("C+")){
                        bobot = sks * 2.50;
                    }else if(grade.equals("C")){
                        bobot = sks * 2.00;
                    }else if(grade.equals("D")){
                        bobot = sks * 1.00;
                    }else if(grade.equals("E")){
                        bobot = sks * 0;
                    }else if(grade.equals("TL")){
                        bobot = sks * 0;
                    }

                    totalnilai+=bobot;
                    String keterangan = obj.getString("keterangan");
                    if(keterangan.equals("Lulus")){
                        jmlsksl+=sks;
                        jmlmkl++;
                    }else if(keterangan.equals("Tidak Lulus")){
                        jmlskstl+=sks;
                        jmlmktl++;
                    }
                    jmlsks += sks;
                    LegacyTableView.insertLegacyContent(String.valueOf(i+1), String.valueOf(smt), kode_mk, mata_kuliah, String.valueOf(sks), grade, String.valueOf(bobot), keterangan);

                }

                int i = jsonArray.length()-1;
                int smtampung = jsonArray.getJSONObject(jsonArray.length()-1).getInt("smt");
                int smtcek = 0;

                double totalnilaiips =0, jmlsksips=0;
               do{
                    JSONObject obj = jsonArray.getJSONObject(i);
                    smtcek = obj.getInt("smt");
                    int sks = obj.getInt("sks");
                    String grade = obj.getString("grade");
                    double bobot = 0;
                    if(grade.equals("A")){
                        bobot = sks * 4;
                    }else if(grade.equals("A-")){
                        bobot = sks * 3.75;
                    }else if(grade.equals("B+")){
                        bobot = sks * 3.50;
                    }else if(grade.equals("B")){
                        bobot = sks * 3;
                    }else if(grade.equals("B-")){
                        bobot = sks * 2.75;
                    }else if(grade.equals("C+")){
                        bobot = sks * 2.50;
                    }else if(grade.equals("C")){
                        bobot = sks * 2.00;
                    }else if(grade.equals("D")){
                        bobot = sks * 1.00;
                    }else if(grade.equals("E")){
                        bobot = sks * 0;
                    }else if(grade.equals("TL")){
                        bobot = sks * 0;
                    }
                    totalnilaiips+=bobot;
                    jmlsksips+=sks;
                    i--;
                }while(smtampung==smtcek);
                ipk = totalnilai/jmlsks;
                ips = totalnilaiips/jmlsksips;
                pesan = jsonObjectkhs.getString("pesan");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            //super.onPostExecute(s);
            tv_nama.setText(nama);
            tv_npm.setText(npm1);
            tv_pa.setText(pa);
            tv_fakultas.setText(fakultas);
            tv_ps.setText(pp);
            pDialog.dismiss();
           // Toast.makeText(getApplicationContext(), nama + " haha", Toast.LENGTH_SHORT).show();
            legacyTableView.setTitle(LegacyTableView.readLegacyTitle());
            legacyTableView.setContent(LegacyTableView.readLegacyContent());
            legacyTableView.build();
            tv_jmlsks.setText(String.valueOf(jmlsks));
            tv_jmlsksltl.setText(String.valueOf(jmlsksl)+"/"+String.valueOf(jmlskstl));
            tv_jmlmkltl.setText(String.valueOf(jmlmkl)+"/"+String.valueOf(jmlmktl));
            tv_jn.setText(String.valueOf(totalnilai));
            tv_ips.setText(String.format("2f", ips));
            tv_ipk.setText(String.format("2f", ipk));
            //tv_ipk.setText(pesan);
        }
    }
}
