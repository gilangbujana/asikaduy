package com.asikaduy.aplikasimonitoringakademikuniversitasyarsi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

public class Menuutama extends AppCompatActivity {
    SessionManager session;
    String npm = "", nama_mahasiswa = "",id_tahunakademik="",foto="";
    TextView txt1,txt2;
    private static final int MY_PERMISSION_REQUEST = 1;
    JSONParser jsonParser = new JSONParser();

    String ip="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menuutama);

        ip=jsonParser.getIP();


        session = new SessionManager(getApplicationContext());
        session.checkLogin();


        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(Menuutama.this);
        Boolean Registered = sharedPref.getBoolean("Registered", false);
        if (!Registered) {
            finish();
        } else {
            npm = sharedPref.getString("npm", "");
            nama_mahasiswa = sharedPref.getString("nama_mahasiswa", "");
            foto = sharedPref.getString("foto", "");
            id_tahunakademik = sharedPref.getString("id_tahunakademik", "");
        }

        Log.v("seskod", npm);
        Log.v("sesnam", nama_mahasiswa);

        txt1 = (TextView) findViewById(R.id.txt1);
        txt2 = (TextView) findViewById(R.id.txt2);
        txt1.setText("NAMA : "+nama_mahasiswa);
        txt2.setText("NPM : "+npm);



        String arUrlFoto=ip+"ypathfile/"+foto;
        new DownloadImageTask((ImageView) findViewById(R.id.myGambar)).execute(arUrlFoto);


        CardView  pengumumanCard = (CardView) findViewById(R.id.pengumumanCard);
        pengumumanCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Menuutama.this, PengumumanList.class);
                i.putExtra("pk", npm);
                i.putExtra("id_tahunakademik", id_tahunakademik);

                startActivity(i);
            }
        });


        CardView   jadwalCard = (CardView) findViewById(R.id.jadwalCard);
        jadwalCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Menuutama.this, MatkulList.class);
                i.putExtra("pk", npm);
                i.putExtra("id_tahunakademik", id_tahunakademik);
                startActivity(i);
            }
        });


        CardView   nilaidetailCard = (CardView) findViewById(R.id.nilaidetailcard);//nilai smst aktif
        nilaidetailCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Menuutama.this,Nilai_DetailList.class);
                i.putExtra("pk", npm);
                i.putExtra("gr", "0");
                i.putExtra("id_tahunakademik", id_tahunakademik);
                startActivity(i);
            }
        });


        CardView   nilaiCard = (CardView) findViewById(R.id.nilaiCard);//GRAFIK
        nilaiCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Menuutama.this, Graph.class);//KHS MainActivity_Chart
                i.putExtra("npm", npm);
                //i.putExtra("gr", "1");

                i.putExtra("id_tahunakademik", id_tahunakademik);
                startActivity(i);
            }
        });





        CardView   krsCard = (CardView) findViewById(R.id.krsCard);//KrsList yg diambils
       krsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Menuutama.this, ListSmt.class);//KrsList
                i.putExtra("pk", npm);
                i.putExtra("id_tahunakademik", id_tahunakademik);
                startActivity(i);
            }
        });

        CardView   nilaikhs = (CardView) findViewById(R.id.nilaikhs);//KrsList yg diambils
        nilaikhs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Menuutama.this, KhsActivity.class);//KrsList
                i.putExtra("pk", npm);


                //i.putExtra("id_tahunakademik", id_tahunakademik);
                startActivity(i);
            }
        });


        CardView  mapsCard = (CardView) findViewById(R.id.profilCard);
        mapsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Menuutama.this, Profil.class);
                i.putExtra("pk", npm);
                i.putExtra("id_tahunakademik", id_tahunakademik);

                startActivity(i);
            }
        });
        CardView   LogOutCard = (CardView) findViewById(R.id.LogOutCard);
        LogOutCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               session.logout();
               finish();
                //startActivity(i);
            }
        });





    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            }
            catch (Exception e) {Log.e("Error", e.getMessage());e.printStackTrace();}
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result); }
    }

}