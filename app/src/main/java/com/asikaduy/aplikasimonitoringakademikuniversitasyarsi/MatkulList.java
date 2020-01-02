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

public class MatkulList extends ListActivity {
String ip="",id_tahunakademik="";

	private ProgressDialog pDialog;
	JSONParser jParser = new JSONParser();
	JSONArray myJSON = null;
	String npm="";
	ArrayList<HashMap<String, String>> arrayList;
	private static final String TAG_SUKSES = "sukses";
	private static final String TAG_record = "record";
	
	private static final String TAG_id_matkul = "id_matkul";
	private static final String TAG_nama_matkul = "nama_matkul";
	private static final String TAG_id_dosen = "id_dosen";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_list);
		arrayList = new ArrayList<HashMap<String, String>>();
		ip=jParser.getIP();

		Intent i = getIntent();
		id_tahunakademik= i.getStringExtra("id_tahunakademik");
		npm= i.getStringExtra("pk");

		new load().execute();
		
		ListView lv = getListView();
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				String pk = ((TextView) view.findViewById(R.id.kode_k)).getText().toString();
				Intent i = new Intent(getApplicationContext(), Matkul.class);
				i.putExtra("pk", pk);
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
int sukses=0;
	class load extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(MatkulList.this);
			pDialog.setMessage("Load data. Silahkan Tunggu...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}
		protected String doInBackground(String... args) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("id_tahunakademik", id_tahunakademik));
			params.add(new BasicNameValuePair("npm", npm));
			JSONObject json = jParser.makeHttpRequest(ip+"matkul/matkul_show.php", "GET", params);
			Log.d("show: ", json.toString());
			try {
				sukses = json.getInt(TAG_SUKSES);
				if (sukses == 1) {
					myJSON = json.getJSONArray(TAG_record);
					for (int i = 0; i < myJSON.length(); i++) {
						JSONObject c = myJSON.getJSONObject(i);
						String id_matkul= c.getString(TAG_id_matkul);
						String nama_matkul = c.getString(TAG_nama_matkul);
						String id_dosen = c.getString(TAG_id_dosen);

						String sks_tatapmuka= c.getString("sks_tatapmuka");
						String jenis_matkul = c.getString("jenis_matkul");
						String jadwal= c.getString("jadwal");

						HashMap<String, String> map = new HashMap<String, String>();
							map.put(TAG_id_matkul, id_matkul);
							map.put(TAG_nama_matkul, nama_matkul);
							map.put(TAG_id_dosen, jenis_matkul+" SKS:"+sks_tatapmuka+"\n"+jadwal);

						arrayList.add(map);
					}
				} else {

				}
			} 
			catch (JSONException e) {e.printStackTrace();}
			return null;
		}

		protected void onPostExecute(String file_url) {
			pDialog.dismiss();
			runOnUiThread(new Runnable() {
				public void run() {
					ListAdapter adapter = new SimpleAdapter(MatkulList.this, arrayList,R.layout.desain_list, new String[] { TAG_id_matkul,TAG_nama_matkul, TAG_id_dosen,},new int[] { R.id.kode_k, R.id.txtNamalkp ,R.id.txtDeskripsilkp});
					setListAdapter(adapter);
					if(sukses==0){	kosong();}
				}
			});}
	}

    
public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
		finish();
		return true;
		}
		return super.onKeyDown(keyCode, event);
		}
	public void kosong(){
		new AlertDialog.Builder(this)
				.setTitle("Kosong")
				.setMessage("Maaf data belum tersedia")
				.setNeutralButton("Tutup", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dlg, int sumthin) {
						finish();
					}}).show();
	}
}
