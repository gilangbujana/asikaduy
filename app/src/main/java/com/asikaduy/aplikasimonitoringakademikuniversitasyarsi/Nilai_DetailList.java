package com.asikaduy.aplikasimonitoringakademikuniversitasyarsi;

import android.app.AlertDialog;
import android.app.ExpandableListActivity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Nilai_DetailList extends ExpandableListActivity {
String ip="",npm="",id_tahunakademik="";
TextView txtipk;
double ipk=0;
int totsks=0;
int totnilai=0;


	private ProgressDialog pDialog;
	JSONParser jParser = new JSONParser();
	JSONArray myJSON = null;
	
	HashMap<String, List<HashMap<String, String>>> listDetail;
	List<String> listJudul;
	ArrayList<HashMap<String, String>> arrayList;
	ExpandableListView elv;
	ExpandableListAdapter elvAdapter;
	private static final String TAG_SUKSES = "sukses";
	private static final String TAG_record = "record";
	
	private static final String TAG_id_nilaidetail = "id_nilaidetail";
	private static final String TAG_id_nilai = "id_nilai";
	private static final String TAG_npm = "npm";
String gr="";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_listnilai);
		listDetail = new HashMap<String, List<HashMap<String, String>>>();
		arrayList = new ArrayList<HashMap<String, String>>();
		ip=jParser.getIP();


		Intent i = getIntent();
		npm= i.getStringExtra("pk");
		id_tahunakademik= i.getStringExtra("id_tahunakademik");
		gr= i.getStringExtra("gr");

		new load().execute();

		elv = getExpandableListView();
		elv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long id) {

				//Toast.makeText(getApplicationContext(), "lah",Toast.LENGTH_SHORT).show();
				String pk = ((TextView) view.findViewById(R.id.kode_k)).getText().toString();
				Intent i = new Intent(getApplicationContext(), Nilai_Detail.class);
				i.putExtra("pk", pk);
				startActivityForResult(i, 100);
				return false;
			}
		});

		/*
		ListView lv = getListView();
		elv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				String pk = ((TextView) view.findViewById(R.id.kode_k)).getText().toString();
				Intent i = new Intent(getApplicationContext(), Nilai_Detail.class);
				i.putExtra("pk", pk);
				startActivityForResult(i, 100);
			}});
		*/

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
			pDialog = new ProgressDialog(Nilai_DetailList.this);
			pDialog.setMessage("Load data. Silahkan Tunggu...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}
		protected String doInBackground(String... args) {
			Log.v("Masuk ke","Nilai_DetailList.java");
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("npm", npm));
			//params.add(new BasicNameValuePair("id_tahunakademik", id_tahunakademik));


			JSONObject json = jParser.makeHttpRequest(ip+"nilaidetail/nilaidetail_show.php", "GET", params);
			Log.d("show: ", json.toString());
			try {
				totsks=0;
				totnilai=0;

				sukses = json.getInt(TAG_SUKSES);
				if (sukses == 1) {
					myJSON = json.getJSONArray(TAG_record);
					int smtam = myJSON.getJSONObject(0).getInt("semester");
					for (int i = 0; i < myJSON.length(); i++) {
						JSONObject c = myJSON.getJSONObject(i);
						String id_nilaidetail= c.getString(TAG_id_nilaidetail);
						String id_matkul= c.getString("id_matkul");
						String dosen= c.getString("dosen");
						String total= c.getString("total");
						String grade= c.getString("grade");
						String lsks= c.getString("sks");
						int semester = c.getInt("semester");

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

						if(semester!=smtam){
							listDetail.put("Semester " + smtam, arrayList);
							smtam = semester;
							arrayList = new ArrayList<HashMap<String, String>>();
							arrayList.add(map);
						}else {
							arrayList.add(map);
						}
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
					listJudul = new ArrayList<>(listDetail.keySet());

					elvAdapter = new CustomExpandableListView(Nilai_DetailList.this, listJudul, listDetail);
					setListAdapter(elvAdapter);


					/*ListAdapter adapter = new SimpleAdapter(Nilai_DetailList.this, arrayList,R.layout.desain_listipk,
							new String[] { TAG_id_nilaidetail, TAG_npm,TAG_id_nilai,},
							new int[] { R.id.kode_k, R.id.txtNamalkp ,R.id.txtDeskripsilkp});
					setListAdapter(adapter);
					if(sukses==0){	kosong();}
					else{
						ipk=totnilai/totsks;
						String sipk=String.valueOf(ipk);
						if(sipk.length()>5){sipk=sipk.substring(0,5);}
							//txtipk.setText("=Jumlah Bobot/Jumlah sks ="+totnilai+"/"+totsks+" ="+sipk);
					}*/
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
