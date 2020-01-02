package com.asikaduy.aplikasimonitoringakademikuniversitasyarsi;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Login extends AppCompatActivity {

    SessionManager session;
    EditText txtusername,txtpassword;
    String ip="";
    int sukses;
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();

    private static final String TAG_SUKSES = "sukses";
    private static final String TAG_record = "record";

    String npm="",nama_mahasiswa,email,id_tahunakademik="",foto="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ip=jsonParser.getIP();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        session = new SessionManager(getApplicationContext());
        if(session.isLoggedIn()){
            startActivity(new Intent(this, Menuutama.class));
            finish();
        }
        txtusername=(EditText)findViewById(R.id.txtusername);
        txtpassword=(EditText)findViewById(R.id.txtpassword);


        TextView btnLogin= (TextView) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user=txtusername.getText().toString();
                String pass=txtpassword.getText().toString();
                if(user.length()<1){lengkapi("Username");}
                else if(pass.length()<1){lengkapi("Password");}
                else{
                    new ceklogin().execute();
                }

            }

        });
}

    public void gagal(){
        new AlertDialog.Builder(this)
                .setTitle("Gagal Login")
                .setMessage("Silakan Cek Account Anda Kembali")
                .setNeutralButton("Tutup", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dlg, int sumthin) {

                    }})
                .show();
    }


    class ceklogin extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Login.this);
            pDialog.setMessage("Proses Login...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        protected String doInBackground(String... params) {

            try {
                String	username=txtusername.getText().toString().trim();
                String	password=txtpassword.getText().toString().trim();

                List<NameValuePair> myparams = new ArrayList<NameValuePair>();
                myparams.add(new BasicNameValuePair("username", username));
                myparams.add(new BasicNameValuePair("password", password));

                String url=ip+"mahasiswa/mahasiswa_login.php";
                Log.v("detail",url);
                JSONObject json = jsonParser.makeHttpRequest(url, "GET", myparams);
//                Log.d("detail", json.toString());
                sukses = json.getInt(TAG_SUKSES);
                if (sukses == 1) {
                    JSONArray myObj = json.getJSONArray(TAG_record); // JSON Array
                    final JSONObject myJSON = myObj.getJSONObject(0);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            try {
                                npm=myJSON.getString("npm");
                                nama_mahasiswa=myJSON.getString("nama_mahasiswa");
                                email=myJSON.getString("email");
                                foto=myJSON.getString("foto");
                                id_tahunakademik=myJSON.getString("id_tahunakademik");
                            }
                            catch (JSONException e) {e.printStackTrace();}
                        }});
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        @SuppressLint("NewApi")
        protected void onPostExecute(String file_url) {

            pDialog.dismiss();
            Log.v("SUKSES",npm);

            if(sukses==1){
                session.createLoginSession(npm,nama_mahasiswa);
                final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(Login.this);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean("Registered", true);
                editor.putString("npm", npm);
                editor.putString("nama_mahasiswa", nama_mahasiswa);
                editor.putString("foto", foto);
                editor.putString("id_tahunakademik", id_tahunakademik);


                editor.apply();

                Intent i = new Intent(getApplicationContext(),Menuutama.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
            else{
                gagal("Login");
            }
        }
    }



    public void lengkapi(String item){
        new AlertDialog.Builder(this)
                .setTitle("Lengkapi Data")
                .setMessage("Silakan lengkapi data "+item)
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dlg, int sumthin) {
                    }})
                .show();
    }



    public void gagal(String item){
        new AlertDialog.Builder(this)
                .setTitle("Gagal Login")
                .setMessage("Login "+item+" ,, Gagal")
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dlg, int sumthin) {
                    }})
                .show();
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
