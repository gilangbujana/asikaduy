package com.asikaduy.aplikasimonitoringakademikuniversitasyarsi;

		import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Nilai_Detail extends Activity {
	String ip="";
	String id_nilaidetail;
	String id_nilaidetail0="";

	EditText txtid_nilai;
	EditText txtnpm;
	EditText txtabsensi;
	EditText txttugas;
	EditText txtproyek;
	EditText txtpresentasi;
	EditText txtuts;
	EditText txtuas;
	EditText txttotal;
	EditText txtgrade;
	EditText txtketerangan;


	Button btnProses;
	Button btnHapus;

	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();

	private static final String TAG_SUKSES = "sukses";
	private static final String TAG_record = "record";

	private static final String TAG_id_nilai = "id_nilai";
	private static final String TAG_npm= "npm";
	private static final String TAG_absensi = "absensi";
	private static final String TAG_tugas = "tugas";
	private static final String TAG_proyek = "proyek";
	private static final String TAG_presentasi = "presentasi";
	private static final String TAG_uts = "uts";
	private static final String TAG_uas = "uas";
	private static final String TAG_total= "total";
	private static final String TAG_grade = "grade";
	private static final String TAG_keterangan = "keterangan";


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nilaidetail);

		ip=jsonParser.getIP();
		txtid_nilai= (EditText) findViewById(R.id.txtid_nilai);txtid_nilai.setEnabled(false);
		txtnpm= (EditText) findViewById(R.id.txtnpm);txtnpm.setEnabled(false);
		txtabsensi= (EditText) findViewById(R.id.txtabsensi);txtabsensi.setEnabled(false);
		txttugas= (EditText) findViewById(R.id.txttugas);txttugas.setEnabled(false);
		txtproyek= (EditText) findViewById(R.id.txtproyek);txtproyek.setEnabled(false);
		txtpresentasi= (EditText) findViewById(R.id.txtpresentasi);txtpresentasi.setEnabled(false);
		txtuts = (EditText) findViewById(R.id.txtuts);txtuts.setEnabled(false);
		txtuas = (EditText) findViewById(R.id.txtuas);txtuas.setEnabled(false);
		txttotal= (EditText) findViewById(R.id.txttotal);txttotal.setEnabled(false);
		txtgrade= (EditText) findViewById(R.id.txtgrade);txtgrade.setEnabled(false);
		txtketerangan = (EditText) findViewById(R.id.txtketerangan);txtketerangan.setEnabled(false);

		btnProses= (Button) findViewById(R.id.btnproses);
		btnHapus = (Button) findViewById(R.id.btnhapus);

		Intent i = getIntent();
		id_nilaidetail0 = i.getStringExtra("pk");
		new get().execute();

		btnProses.setEnabled(false);
		btnProses.setVisibility(View.GONE);

		btnHapus.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
				//new del().execute();
			}});

		btnHapus.setText("Kembali");
	}

	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	class get extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Nilai_Detail.this);
			pDialog.setMessage("Load data detail. Silahkan tunggu...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		protected String doInBackground(String... params) {
			int sukses;
			try {
				List<NameValuePair> params1 = new ArrayList<NameValuePair>();
				params1.add(new BasicNameValuePair("id_nilaidetail", id_nilaidetail0));

				String url=ip+"nilaidetail/nilaidetail_detail.php";
				Log.v("detail",url);
				JSONObject json = jsonParser.makeHttpRequest(url, "GET", params1);
				Log.d("detail", json.toString());
				sukses = json.getInt(TAG_SUKSES);
				if (sukses == 1) {
					JSONArray myObj = json.getJSONArray(TAG_record); // JSON Array
					final JSONObject myJSON = myObj.getJSONObject(0);
					runOnUiThread(new Runnable() {
						public void run() {
							try {
								txtid_nilai.setText(myJSON.getString("id_matkul"));
								txtnpm.setText(myJSON.getString("dosen"));
								txtabsensi.setText(myJSON.getString(TAG_absensi));
								txttugas.setText(myJSON.getString(TAG_tugas));
								txtproyek.setText(myJSON.getString(TAG_proyek));
								txtpresentasi.setText(myJSON.getString(TAG_presentasi));
								txtuts.setText(myJSON.getString(TAG_uts));
								txtuas.setText(myJSON.getString(TAG_uas));
								txttotal.setText(myJSON.getString(TAG_total));
								txtgrade.setText(myJSON.getString(TAG_grade));
								txtketerangan.setText(myJSON.getString(TAG_keterangan));
							}
							catch (JSONException e) {e.printStackTrace();}
						}});
				}
				else{
					// jika id tidak ditemukan
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}
		protected void onPostExecute(String file_url) {pDialog.dismiss();}
	}
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	
//
//	class save extends AsyncTask<String, String, String> {
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//			pDialog = new ProgressDialog(Nilai_Detail.this);
//			pDialog.setMessage("Menyimpan data ...");
//			pDialog.setIndeterminate(false);
//			pDialog.setCancelable(true);
//			pDialog.show();
//		}
//		protected String doInBackground(String... args) {
//			id_nilaidetail = txtid_nilaidetail.getText().toString();
//			String lid_nilai= txtid_nilai.getText().toString();
//			String lnpm= txtnpm.getText().toString();
//			String labsensi= txtabsensi.getText().toString();
//			String ltugas= txttugas.getText().toString();
//			String lproyek= txtproyek.getText().toString();
//			String lpresentasi= txtpresentasi.getText().toString();
//			String luts= txtuts.getText().toString();
//			String luas= txtuas.getText().toString();
//			String ltotal= txttotal.getText().toString();
//			String lgrade= txtgrade.getText().toString();
//			String lketerangan= txtketerangan.getText().toString();
//
//
//
//			List<NameValuePair> params = new ArrayList<NameValuePair>();
//			params.add(new BasicNameValuePair("id_nilaidetail0", id_nilaidetail0));
//			params.add(new BasicNameValuePair("id_nilaidetail", id_nilaidetail));
//			params.add(new BasicNameValuePair("id_nilai", lid_nilai));
//			params.add(new BasicNameValuePair("npm", lnpm));
//			params.add(new BasicNameValuePair("absensi", labsensi));
//			params.add(new BasicNameValuePair("tugas", ltugas));
//			params.add(new BasicNameValuePair("proyek", lproyek));
//			params.add(new BasicNameValuePair("uts", luts));
//			params.add(new BasicNameValuePair("uas", luas));
//			params.add(new BasicNameValuePair("total", ltotal));params.add(new BasicNameValuePair("presentasi", lpresentasi));
//			params.add(new BasicNameValuePair("grade", lgrade));
//			params.add(new BasicNameValuePair("keterangan", lketerangan));
//
//			String url=ip+"nilaidetail/nilaidetail_add.php";
//			Log.v("add",url);
//			JSONObject json = jsonParser.makeHttpRequest(url,"POST", params);
//			Log.d("add", json.toString());
//			try {
//				int sukses = json.getInt(TAG_SUKSES);
//				if (sukses == 1) {
//					Intent i = getIntent();
//					setResult(100, i);
//					finish();
//				} else {
//					// gagal update data
//				}
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
//			return null;
//		}
//
//		protected void onPostExecute(String file_url) {pDialog.dismiss();}
//	}
////++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//
//	class update extends AsyncTask<String, String, String> {
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//			pDialog = new ProgressDialog(Nilai_Detail.this);
//			pDialog.setMessage("Mengubah data ...");
//			pDialog.setIndeterminate(false);
//			pDialog.setCancelable(true);
//			pDialog.show();
//		}
//		protected String doInBackground(String... args) {
//			id_nilaidetail = txtid_nilaidetail.getText().toString();
//			String lid_nilai= txtid_nilai.getText().toString();
//			String lnpm= txtnpm.getText().toString();
//			String labsensi= txtabsensi.getText().toString();
//			String ltugas= txttugas.getText().toString();
//			String lproyek= txtproyek.getText().toString();
//			String lpresentasi= txtpresentasi.getText().toString();
//			String luts= txtuts.getText().toString();
//			String luas= txtuas.getText().toString();
//			String ltotal= txttotal.getText().toString();
//			String lgrade= txtgrade.getText().toString();
//			String lketerangan= txtketerangan.getText().toString();
//
//
//			List<NameValuePair> params = new ArrayList<NameValuePair>();
//			params.add(new BasicNameValuePair("id_nilaidetail0", id_nilaidetail0));
//			params.add(new BasicNameValuePair("id_nilaidetail", id_nilaidetail));
//			params.add(new BasicNameValuePair("id_nilai", lid_nilai));
//			params.add(new BasicNameValuePair("npm", lnpm));
//			params.add(new BasicNameValuePair("absensi", labsensi));
//			params.add(new BasicNameValuePair("tugas", ltugas));
//			params.add(new BasicNameValuePair("proyek", lproyek));
//			params.add(new BasicNameValuePair("presentasi", lpresentasi));
//			params.add(new BasicNameValuePair("uts", luts));
//			params.add(new BasicNameValuePair("uas", luas));
//			params.add(new BasicNameValuePair("total", ltotal));
//			params.add(new BasicNameValuePair("grade", lgrade));
//			params.add(new BasicNameValuePair("keterangan", lketerangan));
//
//			String url=ip+"nilaidetail/nilaidetail_update.php";
//			Log.v("update",url);
//			JSONObject json = jsonParser.makeHttpRequest(url,"POST", params);
//			Log.d("add", json.toString());
//			try {
//				int sukses = json.getInt(TAG_SUKSES);
//				if (sukses == 1) {
//					Intent i = getIntent();
//					setResult(100, i);
//					finish();
//				} else {
//					// gagal update data
//				}
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
//			return null;
//		}
//
//		protected void onPostExecute(String file_url) {pDialog.dismiss();}
//	}
//	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//
//	class del extends AsyncTask<String, String, String> {
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//			pDialog = new ProgressDialog(Nilai_Detail.this);
//			pDialog.setMessage("Menghapus data...");
//			pDialog.setIndeterminate(false);
//			pDialog.setCancelable(true);
//			pDialog.show();
//		}
//
//		protected String doInBackground(String... args) {
//			int sukses;
//			try {
//				List<NameValuePair> params = new ArrayList<NameValuePair>();
//				params.add(new BasicNameValuePair("id_nilaidetail", id_nilaidetail0));
//
//				String url=ip+"nilaidetail/nilaidetail_del.php";
//				Log.v("delete",url);
//				JSONObject json = jsonParser.makeHttpRequest(url, "GET", params);
//				Log.d("delete", json.toString());
//				sukses = json.getInt(TAG_SUKSES);
//				if (sukses == 1) {
//					Intent i = getIntent();
//					setResult(100, i);
//					finish();
//				}
//			}
//			catch (JSONException e) {e.printStackTrace();}
//			return null;
//		}
//
//		protected void onPostExecute(String file_url) {pDialog.dismiss();}
//	}
////++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	public void lengkapi(String item){
		new AlertDialog.Builder(this)
				.setTitle("Lengkapi Data")
				.setMessage("Silakan lengkapi data "+item +" !")
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
