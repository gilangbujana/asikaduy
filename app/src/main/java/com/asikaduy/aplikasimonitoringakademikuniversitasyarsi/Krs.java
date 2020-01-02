package com.asikaduy.aplikasimonitoringakademikuniversitasyarsi;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Krs extends Activity {
	String ip="";
	String id_krs;
	String id_krs0="";

	EditText txtfakultas,txtdosen;
	EditText txtid_tahunakademik;
	EditText txtprodi;
	EditText txtsemester;
	EditText txtstatus_pa;
	EditText txtid_matkul;



	Button btnProses;
	Button btnHapus;

	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();

	private static final String TAG_SUKSES = "sukses";
	private static final String TAG_record = "record";

	private static final String TAG_id_tahunakademik = "id_tahunakademik";
	private static final String TAG_prodi = "prodi";
	private static final String TAG_semester = "semester";
	private static final String TAG_status_pa = "status_pa";
	private static final String TAG_npm = "npm";
	private static final String TAG_id_matkul = "id_matkul";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.krs);

		ip=jsonParser.getIP();

		txtfakultas = (EditText) findViewById(R.id.txtfakultas);txtfakultas.setEnabled(false);
		txtid_tahunakademik= (EditText) findViewById(R.id.txtid_tahunakademik);txtid_tahunakademik.setEnabled(false);
		txtprodi= (EditText) findViewById(R.id.txtprodi);txtprodi.setEnabled(false);
		txtsemester= (EditText) findViewById(R.id.txtsemester);txtsemester.setEnabled(false);
		txtstatus_pa= (EditText) findViewById(R.id.txtstatus_pa);txtstatus_pa.setEnabled(false);
		txtid_matkul = (EditText) findViewById(R.id.txtid_matkul);txtid_matkul.setEnabled(false);
		txtdosen= (EditText) findViewById(R.id.txtdosen);txtdosen.setEnabled(false);

		txtfakultas.setText("Teknik Informatika");

		btnProses= (Button) findViewById(R.id.btnproses);
		btnHapus = (Button) findViewById(R.id.btnhapus);

		Intent i = getIntent();
		id_krs0 = i.getStringExtra("pk");

		btnProses.setVisibility(View.GONE);
		btnHapus.setText("Kembali");
		new get().execute();



		btnHapus.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}});
	}

	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	class get extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Krs.this);
			pDialog.setMessage("Load data detail. Silahkan tunggu...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		protected String doInBackground(String... params) {
			int sukses;
			try {
				List<NameValuePair> params1 = new ArrayList<NameValuePair>();
				params1.add(new BasicNameValuePair("id_krs", id_krs0));

				String url=ip+"krs/krs_detail.php";
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
								txtid_tahunakademik.setText(myJSON.getString(TAG_id_tahunakademik));
								txtprodi.setText(myJSON.getString(TAG_prodi));
								txtsemester.setText(myJSON.getString(TAG_semester));
								txtstatus_pa.setText(myJSON.getString(TAG_status_pa));
								txtid_matkul.setText(myJSON.getString(TAG_id_matkul));
								txtdosen.setText(myJSON.getString("dosen"));
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
//			pDialog = new ProgressDialog(Krs.this);
//			pDialog.setMessage("Menyimpan data ...");
//			pDialog.setIndeterminate(false);
//			pDialog.setCancelable(true);
//			pDialog.show();
//		}
//		protected String doInBackground(String... args) {
//			id_krs = txtfakultas.getText().toString();
//			String lid_tahunakademik= txtid_tahunakademik.getText().toString();
//			String lprodi= txtprodi.getText().toString();
//			String lsemester= txtsemester.getText().toString();
//			String lstatus_pa= txtstatus_pa.getText().toString();
//			String lnpm= txtnpm.getText().toString();
//			String lid_matkul= txtid_matkul.getText().toString();
//
//
//
//			List<NameValuePair> params = new ArrayList<NameValuePair>();
//			params.add(new BasicNameValuePair("id_krs0", id_krs0));
//			params.add(new BasicNameValuePair("id_krs", id_krs));
//			params.add(new BasicNameValuePair("id_tahunakademik", lid_tahunakademik));
//			params.add(new BasicNameValuePair("prodi", lprodi));
//			params.add(new BasicNameValuePair("semester", lsemester));
//			params.add(new BasicNameValuePair("status_pa", lstatus_pa));
//			params.add(new BasicNameValuePair("id_matkul", lid_matkul));
//
//
//			String url=ip+"krs/krs_add.php";
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
//			pDialog = new ProgressDialog(Krs.this);
//			pDialog.setMessage("Mengubah data ...");
//			pDialog.setIndeterminate(false);
//			pDialog.setCancelable(true);
//			pDialog.show();
//		}
//		protected String doInBackground(String... args) {
//			id_krs = txtfakultas.getText().toString();
//			String lid_tahunakademik= txtid_tahunakademik.getText().toString();
//			String lprodi= txtprodi.getText().toString();
//			String lsemester= txtsemester.getText().toString();
//			String lstatus_pa= txtstatus_pa.getText().toString();
//			String lnpm= txtnpm.getText().toString();
//			String lid_matkul= txtid_matkul.getText().toString();
//
//			List<NameValuePair> params = new ArrayList<NameValuePair>();
//			params.add(new BasicNameValuePair("id_krs0", id_krs0));
//			params.add(new BasicNameValuePair("id_krs", id_krs));
//			params.add(new BasicNameValuePair("id_tahunakademik", lid_tahunakademik));
//			params.add(new BasicNameValuePair("prodi", lprodi));
//			params.add(new BasicNameValuePair("semester", lsemester));
//			params.add(new BasicNameValuePair("status_pa", lstatus_pa));
//			params.add(new BasicNameValuePair("npm", lnpm));
//			params.add(new BasicNameValuePair("id_matkul", lid_matkul));
//
//			String url=ip+"krs/krs_update.php";
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
//			pDialog = new ProgressDialog(Krs.this);
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
//				params.add(new BasicNameValuePair("id_krs", id_krs0));
//
//				String url=ip+"krs/krs_del.php";
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


}
