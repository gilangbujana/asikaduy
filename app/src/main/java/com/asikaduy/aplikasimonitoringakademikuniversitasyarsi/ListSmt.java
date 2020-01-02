package com.asikaduy.aplikasimonitoringakademikuniversitasyarsi;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListSmt extends ListActivity {
    String idsmt="",npm="",id_tahunakademik="";

    ArrayList<HashMap<String, String>> arrayList,arrayListsmt;

    private String[] semester ={"Semester 1","Semester 2","Semester 3","Semester 4","Semester 5","Semester 6","Semester 7",};
    private String[] smtr = {"1","2","3","4","5","6","7",};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_list);

        arrayListsmt = new ArrayList<HashMap<String, String>>();

        Intent i = getIntent();
        npm = i.getStringExtra("pk");
        id_tahunakademik= i.getStringExtra("id_tahunakademik");

        HashMap<String, String> map = new HashMap<String, String>();
        for (int count = 0; count < semester.length; count++){
            map = new HashMap<String, String>();
            map.put("Judul", semester[count]);
            map.put("Id", smtr[count]);
            arrayListsmt.add(map);
        }

        ListAdapter adapter = new SimpleAdapter(ListSmt.this, arrayListsmt,R.layout.desain_listkrssmt,
                new String[] { "Id","Judul"},
                new int[] { R.id.kode_k, R.id.txtNamalkp});
        setListAdapter(adapter);

        ListView lv = getListView();
        lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                idsmt = ((TextView) view.findViewById(R.id.kode_k)).getText().toString();
                Log.v("Ini Id nya", idsmt);
                Intent i = new Intent(getApplicationContext(), KrsList.class);
                i.putExtra("idsmt", idsmt);
                i.putExtra("pk", npm);
                i.putExtra("id_tahunakademik", id_tahunakademik);
                startActivityForResult(i, 100);
            }});
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100) {// jika result code 100
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

