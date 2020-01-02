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

public class KHS extends ListActivity {
String ip="",npm="",id_tahunakademik="";
TextView txtipk;
double ipk=0;
int totsks=0;
int totnilai=0;


	private ProgressDialog pDialog;
	JSONParser jParser = new JSONParser();
	JSONArray myJSON = null;
	
	ArrayList<HashMap<String, String>> arrayList;
	private static final String TAG_SUKSES = "sukses";
	private static final String TAG_record = "record";
	
	private static final String TAG_id_nilaidetail = "id_nilaidetail";
	private static final String TAG_id_nilai = "id_nilai";
	private static final String TAG_npm = "npm";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_listipk);
		arrayList = new ArrayList<HashMap<String, String>>();
		ip=jParser.getIP();


		Intent i = getIntent();
		npm= i.getStringExtra("pk");
		id_tahunakademik= i.getStringExtra("id_tahunakademik");

		new load().execute();

		ListView lv = getListView();
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				String pk = ((TextView) view.findViewById(R.id.kode_k)).getText().toString();
				Intent i = new Intent(getApplicationContext(), MainActivity_Chart.class);
				i.putExtra("pk", pk);
				startActivityForResult(i, 100);

//				Chart chart = chartList.get(position);
//
//				Intent intent = new Intent(getApplicationContext(), chart.getActivityClass());
//				startActivity(intent);

			}});


		txtipk= (TextView) findViewById(R.id.txtipk);

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
			pDialog = new ProgressDialog(KHS.this);
			pDialog.setMessage("Load data. Silahkan Tunggu...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}
		protected String doInBackground(String... args) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("npm", npm));
			params.add(new BasicNameValuePair("id_tahunakademik", id_tahunakademik));

			//nilaidetail_showall
			JSONObject json = jParser.makeHttpRequest(ip+"nilaidetail/nilaidetail_showall.php", "GET", params);
			Log.d("show: ", json.toString());
			try {
				totsks=0;
				totnilai=0;

				sukses = json.getInt(TAG_SUKSES);
				if (sukses == 1) {
					myJSON = json.getJSONArray(TAG_record);
					for (int i = 0; i < myJSON.length(); i++) {
						JSONObject c = myJSON.getJSONObject(i);
						String id_nilaidetail= c.getString(TAG_id_nilaidetail);
						String id_matkul= c.getString("id_matkul");
						String dosen= c.getString("dosen");
						String total= c.getString("total");
						String grade= c.getString("grade");
						String lsks= c.getString("sks");

						int isks=Integer.parseInt(lsks);
						int bobot=0;
						if(grade.equalsIgnoreCase("A")){
								bobot=4;
						}
						else if(grade.equalsIgnoreCase("B")){
							bobot=3;
						}
						else if(grade.equalsIgnoreCase("C")){
							bobot=2;
						}else if(grade.equalsIgnoreCase("D")){
							bobot=1;
						}
						totnilai+=(bobot *isks);
						totsks+=isks;

						HashMap<String, String> map = new HashMap<String, String>();
							map.put(TAG_id_nilaidetail, id_nilaidetail);
							map.put(TAG_npm, id_matkul +" :"+lsks+" sks");
							map.put(TAG_id_nilai, dosen+"\nNilai:"+total +" |Grade:"+grade+"");

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
					ListAdapter adapter = new SimpleAdapter(KHS.this, arrayList,R.layout.desain_listipk, new String[] { TAG_id_nilaidetail, TAG_npm,TAG_id_nilai,},new int[] { R.id.kode_k, R.id.txtNamalkp ,R.id.txtDeskripsilkp});
					setListAdapter(adapter);
					if(sukses==0){	kosong();}
					else{
						ipk=totnilai/totsks;
						String sipk=String.valueOf(ipk);
						if(sipk.length()>5){sipk=sipk.substring(0,5);}
							txtipk.setText("=Jumlah Bobot/Jumlah sks ="+totnilai+"/"+totsks+" ="+sipk);
					}
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
