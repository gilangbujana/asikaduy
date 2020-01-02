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

public class Matkul extends Activity {
	String ip="";
	String id_matkul;
	String id_matkul0="";

	EditText txtkurikulum;
	EditText txtkode_matkul;
	EditText txtnama_matkul;
	EditText txtsemester;
	EditText txtsks_tatapmuka;
	EditText txtsks_praktikum;
	EditText txtsks_prakteklapangan;
	EditText txtjenis_matkul;
	EditText txtminimal_skslulus;
	EditText txtnilai_minimallulus;
	EditText txtwajib;
	EditText txtmk_khusus;
	EditText txtfakultas;
	EditText txtprodi;
	EditText txtjadwal;
	EditText txtid_tahunakademik;
	EditText txtid_dosen;


	Button btnProses;
	Button btnHapus;

	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();

	private static final String TAG_SUKSES = "sukses";
	private static final String TAG_record = "record";

	private static final String TAG_kurikulum = "kurikulum";
	private static final String TAG_kode_matkul = "kode_matkul";
	private static final String TAG_nama_matkul = "nama_matkul";
	private static final String TAG_semester = "semester";
	private static final String TAG_sks_tatapmuka = "sks_tatapmuka";
	private static final String TAG_sks_praktikum = "sks_praktikum";
	private static final String TAG_sks_prakteklapangan = "sks_prakteklapangan";
	private static final String TAG_jenis_matkul = "jenis_matkul";
	private static final String TAG_minimal_skslulus = "minimal_skslulus";
	private static final String TAG_nilai_minimallulus = "nilai_minimallulus";
	private static final String TAG_wajib = "wajib";
	private static final String TAG_mk_khusus = "mk_khusus";
	private static final String TAG_fakultas = "fakultas";
	private static final String TAG_prodi = "prodi";
	private static final String TAG_jadwal = "jadwal";
	private static final String TAG_id_tahunakademik = "id_tahunakademik";
	private static final String TAG_id_dosen = "id_dosen";


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.matkul);

		ip=jsonParser.getIP();

		txtkurikulum = (EditText) findViewById(R.id.txtkurikulum);txtkurikulum.setEnabled(false);
		txtkode_matkul = (EditText) findViewById(R.id.txtkode_matkul);txtkode_matkul.setEnabled(false);
		txtnama_matkul= (EditText) findViewById(R.id.txtnama_matkul);txtnama_matkul.setEnabled(false);
		txtsemester = (EditText) findViewById(R.id.txtsemester);txtsemester.setEnabled(false);
		txtsks_tatapmuka = (EditText) findViewById(R.id.txtsks_tatapmuka);txtsks_tatapmuka.setEnabled(false);
		txtsks_praktikum = (EditText) findViewById(R.id.txtsks_praktikum);txtsks_praktikum.setEnabled(false);
		txtsks_prakteklapangan = (EditText) findViewById(R.id.txtsks_prakteklapangan);txtsks_prakteklapangan.setEnabled(false);
		txtjenis_matkul = (EditText) findViewById(R.id.txtjenis_matkul);txtjenis_matkul.setEnabled(false);
		txtminimal_skslulus = (EditText) findViewById(R.id.txtminimal_skslulus);txtminimal_skslulus.setEnabled(false);
		txtnilai_minimallulus = (EditText) findViewById(R.id.txtnilai_minimallulus);txtnilai_minimallulus.setEnabled(false);
		txtwajib = (EditText) findViewById(R.id.txtwajib);txtwajib.setEnabled(false);
		txtmk_khusus = (EditText) findViewById(R.id.txtmk_khusus);txtmk_khusus.setEnabled(false);
		txtfakultas = (EditText) findViewById(R.id.txtfakultas);txtfakultas.setEnabled(false);
		txtprodi = (EditText) findViewById(R.id.txtprodi);txtprodi.setEnabled(false);
		txtjadwal = (EditText) findViewById(R.id.txtjadwal);txtjadwal.setEnabled(false);
		txtid_tahunakademik = (EditText) findViewById(R.id.txtid_tahunakademik);txtid_tahunakademik.setEnabled(false);
		txtid_dosen = (EditText) findViewById(R.id.txtid_dosen);txtid_dosen.setEnabled(false);

		btnProses= (Button) findViewById(R.id.btnproses);
		btnHapus = (Button) findViewById(R.id.btnhapus);

		Intent i = getIntent();
		id_matkul0 = i.getStringExtra("pk");
		new get().execute();

		btnProses.setVisibility(View.GONE);
		btnHapus.setText("Kembali");

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
			pDialog = new ProgressDialog(Matkul.this);
			pDialog.setMessage("Load data detail. Silahkan tunggu...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		protected String doInBackground(String... params) {
			int sukses;
			try {
				List<NameValuePair> params1 = new ArrayList<NameValuePair>();
				params1.add(new BasicNameValuePair("id_matkul", id_matkul0));

				String url=ip+"matkul/matkul_detail.php";
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
								txtkurikulum.setText(myJSON.getString(TAG_kurikulum));
								txtkode_matkul.setText(myJSON.getString(TAG_kode_matkul));
								txtnama_matkul.setText(myJSON.getString(TAG_nama_matkul));
								txtsemester.setText(myJSON.getString(TAG_semester));
								txtsks_tatapmuka.setText(myJSON.getString(TAG_sks_tatapmuka));
								txtsks_praktikum.setText(myJSON.getString(TAG_sks_praktikum));
								txtsks_prakteklapangan.setText(myJSON.getString(TAG_sks_prakteklapangan));
								txtjenis_matkul.setText(myJSON.getString(TAG_jenis_matkul));
								txtminimal_skslulus.setText(myJSON.getString(TAG_nama_matkul));
								txtnilai_minimallulus.setText(myJSON.getString(TAG_nilai_minimallulus));
								txtwajib.setText(myJSON.getString(TAG_wajib));
								txtmk_khusus.setText(myJSON.getString(TAG_mk_khusus));
								txtfakultas.setText(myJSON.getString(TAG_fakultas));
								txtprodi.setText(myJSON.getString(TAG_prodi));
								txtjadwal.setText(myJSON.getString(TAG_jadwal));
								txtid_tahunakademik.setText(myJSON.getString(TAG_id_tahunakademik));
								txtid_dosen.setText(myJSON.getString(TAG_id_dosen));

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
//	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//
//	class save extends AsyncTask<String, String, String> {
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//			pDialog = new ProgressDialog(Matkul.this);
//			pDialog.setMessage("Menyimpan data ...");
//			pDialog.setIndeterminate(false);
//			pDialog.setCancelable(true);
//			pDialog.show();
//		}
//		protected String doInBackground(String... args) {
//			id_matkul = txtid_matkul.getText().toString();
//			String lkurikulum= txtkurikulum.getText().toString();
//			String lkode_matkul= txtkode_matkul.getText().toString();
//			String lnama_matkul= txtnama_matkul.getText().toString();
//			String lsemester= txtsemester.getText().toString();
//			String lsks_tatapmuka= txtsks_tatapmuka.getText().toString();
//			String lsks_praktikum= txtsks_praktikum.getText().toString();
//			String lsks_prakteklapangan= txtsks_prakteklapangan.getText().toString();
//			String ljenis_matkul= txtjenis_matkul.getText().toString();
//			String lminimal_skslulus= txtminimal_skslulus.getText().toString();
//			String lnilai_minimallulus= txtnilai_minimallulus.getText().toString();
//			String lwajib= txtwajib.getText().toString();
//			String lmk_khusus= txtmk_khusus.getText().toString();
//			String lprodi= txtprodi.getText().toString();
//			String ljadwal= txtjadwal.getText().toString();
//			String lid_tahunakademik= txtid_tahunakademik.getText().toString();
//			String lid_dosen= txtid_dosen.getText().toString();
//
//			List<NameValuePair> params = new ArrayList<NameValuePair>();
//			params.add(new BasicNameValuePair("id_matkul0", id_matkul0));
//			params.add(new BasicNameValuePair("id_matkul", id_matkul));
//			params.add(new BasicNameValuePair("kurikulum", lkurikulum));
//			params.add(new BasicNameValuePair("kode_matkul", lkode_matkul));
//			params.add(new BasicNameValuePair("nama_matkul", lnama_matkul));
//			params.add(new BasicNameValuePair("semester", lsemester));
//			params.add(new BasicNameValuePair("sks_tatapmuka", lsks_tatapmuka));
//			params.add(new BasicNameValuePair("sks_praktikum", lsks_praktikum));
//			params.add(new BasicNameValuePair("sks_prakteklapangan", lsks_prakteklapangan));
//			params.add(new BasicNameValuePair("jenis_matkul", ljenis_matkul));
//			params.add(new BasicNameValuePair("minimal_skslulus", lminimal_skslulus));
//			params.add(new BasicNameValuePair("nilai_minimallulus", lnilai_minimallulus));
//			params.add(new BasicNameValuePair("wajib", lwajib));
//			params.add(new BasicNameValuePair("mk_khusus", lmk_khusus));
//			params.add(new BasicNameValuePair("prodi", lprodi));
//			params.add(new BasicNameValuePair("jadwal", ljadwal));
//			params.add(new BasicNameValuePair("id_tahunakademik", lid_tahunakademik));
//			params.add(new BasicNameValuePair("id_dosen", lid_dosen));
//
//			String url=ip+"matkul/matkul_add.php";
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
//			pDialog = new ProgressDialog(Matkul.this);
//			pDialog.setMessage("Mengubah data ...");
//			pDialog.setIndeterminate(false);
//			pDialog.setCancelable(true);
//			pDialog.show();
//		}
//		protected String doInBackground(String... args) {
//			id_matkul = txtid_matkul.getText().toString();
//			String lkurikulum= txtkurikulum.getText().toString();
//			String lkode_matkul= txtkode_matkul.getText().toString();
//			String lnama_matkul= txtnama_matkul.getText().toString();
//			String lsemester= txtsemester.getText().toString();
//			String lsks_tatapmuka= txtsks_tatapmuka.getText().toString();
//			String lsks_praktikum= txtsks_praktikum.getText().toString();
//			String lsks_prakteklapangan= txtsks_prakteklapangan.getText().toString();
//			String ljenis_matkul= txtjenis_matkul.getText().toString();
//			String lminimal_skslulus= txtminimal_skslulus.getText().toString();
//			String lnilai_minimallulus= txtnilai_minimallulus.getText().toString();
//			String lwajib= txtwajib.getText().toString();
//			String lmk_khusus= txtmk_khusus.getText().toString();
//			String lprodi= txtprodi.getText().toString();
//			String ljadwal= txtjadwal.getText().toString();
//			String lid_tahunakademik= txtid_tahunakademik.getText().toString();
//			String lid_dosen= txtid_dosen.getText().toString();
//
//			List<NameValuePair> params = new ArrayList<NameValuePair>();
//			params.add(new BasicNameValuePair("id_matkul0", id_matkul0));
//			params.add(new BasicNameValuePair("id_matkul", id_matkul));
//			params.add(new BasicNameValuePair("id_matkul", id_matkul));
//			params.add(new BasicNameValuePair("kurikulum", lkurikulum));
//			params.add(new BasicNameValuePair("kode_matkul", lkode_matkul));
//			params.add(new BasicNameValuePair("nama_matkul", lnama_matkul));
//			params.add(new BasicNameValuePair("semester", lsemester));
//			params.add(new BasicNameValuePair("sks_tatapmuka", lsks_tatapmuka));
//			params.add(new BasicNameValuePair("sks_praktikum", lsks_praktikum));
//			params.add(new BasicNameValuePair("sks_prakteklapangan", lsks_prakteklapangan));
//			params.add(new BasicNameValuePair("jenis_matkul", ljenis_matkul));
//			params.add(new BasicNameValuePair("minimal_skslulus", lminimal_skslulus));
//			params.add(new BasicNameValuePair("nilai_minimallulus", lnilai_minimallulus));
//			params.add(new BasicNameValuePair("wajib", lwajib));
//			params.add(new BasicNameValuePair("mk_khusus", lmk_khusus));
//			params.add(new BasicNameValuePair("prodi", lprodi));
//			params.add(new BasicNameValuePair("jadwal", ljadwal));
//			params.add(new BasicNameValuePair("id_tahunakademik", lid_tahunakademik));
//			params.add(new BasicNameValuePair("id_dosen", lid_dosen));
//
//			String url=ip+"matkul/matkul_update.php";
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
//			pDialog = new ProgressDialog(Matkul.this);
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
//				params.add(new BasicNameValuePair("id_matkul", id_matkul0));
//
//				String url=ip+"matkul/matkul_del.php";
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


	void callMarquee(){
		Calendar cal = Calendar.getInstance();
		int jam = cal.get(Calendar.HOUR);
		int menit= cal.get(Calendar.MINUTE);
		int detik= cal.get(Calendar.SECOND);

		int tgl= cal.get(Calendar.DATE);
		int bln= cal.get(Calendar.MONTH)+1;
		int thn= cal.get(Calendar.YEAR);

		String stgl=String.valueOf(tgl)+"-"+String.valueOf(bln)+"-"+String.valueOf(thn);
		String sjam=String.valueOf(jam)+":"+String.valueOf(menit)+":"+String.valueOf(detik);

		TextView  txtMarquee=(TextView)findViewById(R.id.txtMarquee);
		txtMarquee.setSelected(true);
		String kata="Aplikasi Akademik "+stgl+"/"+sjam+" #";
		String kalimat=String.format("%1$s",TextUtils.htmlEncode(kata));
		txtMarquee.setText(Html.fromHtml(kalimat+kalimat+kalimat));
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
