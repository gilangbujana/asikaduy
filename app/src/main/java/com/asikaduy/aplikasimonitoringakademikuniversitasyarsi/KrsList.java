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

public class KrsList extends ListActivity {
String idsmt="", ip="",npm="",id_tahunakademik="";

	private ProgressDialog pDialog;
	JSONParser jParser = new JSONParser();
	JSONArray myJSON = null;
	
	ArrayList<HashMap<String, String>> arrayList,arrayListsmt;
	private static final String TAG_SUKSES = "sukses";
	private static final String TAG_record = "record";
	
	private static final String TAG_id_krs = "id_krs";
	private static final String TAG_semester = "semester";
	private static final String TAG_id_matkul = "id_matkul";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_list);
		arrayList = new ArrayList<HashMap<String, String>>();
		arrayListsmt = new ArrayList<HashMap<String, String>>();
		ip=jParser.getIP();

		Intent i = getIntent();
		idsmt = i.getStringExtra("idsmt");
		npm = i.getStringExtra("pk");
		id_tahunakademik= i.getStringExtra("id_tahunakademik");

		new load().execute();
		
		ListView lv = getListView();
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				String pk = ((TextView) view.findViewById(R.id.kode_k)).getText().toString();
				Intent i = new Intent(getApplicationContext(), Krs.class);
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
			pDialog = new ProgressDialog(KrsList.this);
			pDialog.setMessage("Load data. Silahkan Tunggu...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}
		protected String doInBackground(String... args) {
			Log.v("Masuk ke","KrsList.java");
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("idsmt", idsmt));
			params.add(new BasicNameValuePair("npm", npm));
			params.add(new BasicNameValuePair("id_tahunakademik", id_tahunakademik));

			JSONObject json = jParser.makeHttpRequest(ip+"krs/krs_show.php", "GET", params);
			Log.d("show: ", json.toString());
			try {
				 sukses = json.getInt(TAG_SUKSES);
				if (sukses == 1) {
					myJSON = json.getJSONArray(TAG_record);
					for (int i = 0; i < myJSON.length(); i++) {
						JSONObject c = myJSON.getJSONObject(i);
						String id_krs= c.getString(TAG_id_krs);
						String id_matkul = c.getString("id_matkul");
						String status_pa= c.getString("status_pa");
						String semester= c.getString("semester");
						String dosen= c.getString("dosen");

//						$record["id_tahunakademik"] = getTAM($conn,$d["id_tahunakademik"]);
//						$record["prodi"] = $d["prodi"];
//						$record["semester"] = $d["semester"];
//						$record["status_pa"] = $d["status_pa"];
//						$record["npm"] = getMahasiswaM($conn,$d["npm"]);
//						$record["id_matkul"] = getMatkulM($conn,$d["id_matkul"]);

						HashMap<String, String> map = new HashMap<String, String>();
							map.put(TAG_id_krs, id_krs);
							map.put(TAG_semester, id_matkul);
							map.put(TAG_id_matkul, status_pa+" #"+"Semester "+semester+"\nDosen:"+dosen);

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
					ListAdapter adapter = new SimpleAdapter(KrsList.this, arrayList,R.layout.desain_listkrsdetail,
							new String[] { TAG_id_krs,TAG_semester, TAG_id_matkul,},
							new int[] { R.id.kode_k, R.id.txtNamalkp ,R.id.txtDeskripsilkp});
					setListAdapter(adapter);

					if(sukses==0){	kosong();}

				}
			});}
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
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
		finish();
		return true;
		}
		return super.onKeyDown(keyCode, event);
		}

	}
