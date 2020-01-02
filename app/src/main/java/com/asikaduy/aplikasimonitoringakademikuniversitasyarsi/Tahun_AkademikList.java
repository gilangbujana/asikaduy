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
import android.widget.Button;
import android.widget.EditText;
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

public class Tahun_AkademikList extends ListActivity {

TextView txt0,txt;
Button btnChart;
	String ip="",gr="";
	private ProgressDialog pDialog;
	JSONParser jParser = new JSONParser();
	JSONArray myJSON = null;
	
	ArrayList<HashMap<String, String>> arrayList;
	private static final String TAG_SUKSES = "sukses";
	private static final String TAG_record = "record";
	String npm="",id_tahunakademik="";
	
	private static final String TAG_id_tahunajaran = "id_tahunajaran";
	private static final String TAG_nama_tahunajaran = "nama_tahunajaran";
	private static final String TAG_status = "status";

	String IPK="";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_listipk);
		arrayList = new ArrayList<HashMap<String, String>>();
		ip=jParser.getIP();

		Intent i = getIntent();
		npm = i.getStringExtra("pk");
		id_tahunakademik = i.getStringExtra("id_tahunakademik");

		txt0 = (TextView) findViewById(R.id.txtipk0);
		txt = (TextView) findViewById(R.id.txtipk);
		btnChart = (Button) findViewById(R.id.btnchart);

		btnChart.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i = new Intent(Tahun_AkademikList.this, Graph.class);
				i.putExtra("npm",npm);
				i.putExtra("id_tahunakademik", id_tahunakademik);
				startActivity(i);

			}
		});

		txt0.setText("I P K :");

		new load().execute();
		
		ListView lv = getListView();
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				String pk = ((TextView) view.findViewById(R.id.kode_k)).getText().toString();
				Intent i = new Intent(getApplicationContext(), Nilai_DetailList.class);//Tahun_Akademik
				i.putExtra("id_tahunakademik", pk);
				i.putExtra("pk", npm);
				i.putExtra("gr", "1");

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

	class load extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Tahun_AkademikList.this);
			pDialog.setMessage("Load data. Silahkan Tunggu...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}
		protected String doInBackground(String... args) {
			Log.v("Class", "Tahun_AkademikList.java");
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("npm", npm));
			JSONObject json = jParser.makeHttpRequest(ip+"tahunakademik/tahunakademik_show.php", "GET", params);
			Log.d("show: ", json.toString());
			try {
				int sukses = json.getInt(TAG_SUKSES);
				IPK = json.getString("IPK");

				if (sukses == 1) {
					myJSON = json.getJSONArray(TAG_record);
					for (int i = 0; i < myJSON.length(); i++) {
						JSONObject c = myJSON.getJSONObject(i);
						String id_tahunajaran= c.getString("id_tahunakademik");
						String nama_tahunajaran = c.getString("nama_tahunakademik");
						String status = c.getString("status");

						HashMap<String, String> map = new HashMap<String, String>();
							map.put(TAG_id_tahunajaran, id_tahunajaran);
							map.put(TAG_nama_tahunajaran, nama_tahunajaran);
							map.put(TAG_status, "Status PA: "+status);
						
						arrayList.add(map);
					}
				} else {
				kosong();
				}
			} 
			catch (JSONException e) {e.printStackTrace();}
			return null;
		}

		protected void onPostExecute(String file_url) {
			pDialog.dismiss();
			runOnUiThread(new Runnable() {
				public void run() {
					ListAdapter adapter = new SimpleAdapter(Tahun_AkademikList.this, arrayList,R.layout.desain_list,
							new String[] { TAG_id_tahunajaran,TAG_nama_tahunajaran, TAG_status,},
							new int[] { R.id.kode_k, R.id.txtNamalkp ,R.id.txtDeskripsilkp});
					setListAdapter(adapter);
					txt.setText(IPK);
				}
			});
		}
	}
//
//	public boolean onCreateOptionsMenu(Menu menu) {
//        menu.add(0, 1, 0, "Add New").setIcon(R.drawable.add);
//        return true;
//    }
//
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//        case 1:
//        	Intent i = new Intent(getApplicationContext(), Tahun_Akademik.class);
//			i.putExtra("pk", "");
//			startActivityForResult(i, 100);
//            return true;
//        }
//        return false;
//    }

    
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
