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
		import android.widget.ArrayAdapter;
		import android.widget.Button;
import android.widget.EditText;
		import android.widget.Spinner;
		import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Mahasiswa extends Activity {
	String ip="";
	String npm;
	String npm0="";

	Spinner fakultas;
	Spinner prodi;

	String[]arPilFakultas={"Fakultas Teknik"};
	String[]arPilProdi={"Teknik Informatika","Ilmu Perpustakaan"};


	EditText txtnpm;
	EditText txtnama_mahasiswa;
	EditText txtfakultas;
	EditText txtprodi;
	EditText txttempat_lahir;
	EditText txttanggal_lahir;
	EditText txtjenis_kelamin;
	EditText txtkewarganegaraan;
	EditText txtagama;
	EditText txtalamat;
	EditText txtno_telp;
	EditText txtemail;
	EditText txtusername;
	EditText txtpassword;
	EditText txtid_dosen;


	Button btnProses;
	Button btnHapus;

	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();

	private static final String TAG_SUKSES = "sukses";
	private static final String TAG_record = "record";

	private static final String TAG_nama_mahasiswa = "nama_mahasiswa";
	private static final String TAG_fakultas= "fakultas";
	private static final String TAG_prodi = "prodi";
	private static final String TAG_tempat_lahir = "tempat_lahir";
	private static final String TAG_tanggal_lahir = "tanggal_lahir";
	private static final String TAG_jenis_kelamin = "jenis_kelamin";
	private static final String TAG_kewarganegaraan = "kewarganegaraan";
	private static final String TAG_agama = "agama";
	private static final String TAG_alamat= "alamat";
	private static final String TAG_no_telp = "no_telp";
	private static final String TAG_foto = "foto";
	private static final String TAG_email = "email";
	private static final String TAG_username= "username";
	private static final String TAG_password = "password";
	private static final String TAG_id_dosen = "id_dosen";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mahasiswa);

		ip=jsonParser.getIP();

		txtnpm = (EditText) findViewById(R.id.txtnpm);
		txtnama_mahasiswa= (EditText) findViewById(R.id.txtnama_mahasiswa);
		txtfakultas= (EditText) findViewById(R.id.txtfakultas);
		txtprodi= (EditText) findViewById(R.id.txtprodi);
		txttempat_lahir= (EditText) findViewById(R.id.txttempat_lahir);
		txttanggal_lahir= (EditText) findViewById(R.id.txttanggal_lahir);
		txtjenis_kelamin= (EditText) findViewById(R.id.txtjenis_kelamin);
		txtkewarganegaraan = (EditText) findViewById(R.id.txtkewarganegaraan);
		txtagama = (EditText) findViewById(R.id.txtagama);
		txtalamat= (EditText) findViewById(R.id.txtalamat);
		txtno_telp= (EditText) findViewById(R.id.txtno_telp);
		txtemail= (EditText) findViewById(R.id.txtemail);
		txtusername= (EditText) findViewById(R.id.txtusername);
		txtpassword= (EditText) findViewById(R.id.txtpassword);
		txtalamat= (EditText) findViewById(R.id.txtalamat);
		txtid_dosen= (EditText) findViewById(R.id.txtid_dosen);

		btnProses= (Button) findViewById(R.id.btnproses);
		btnHapus = (Button) findViewById(R.id.btnhapus);




		fakultas = (Spinner) findViewById(R.id.spinFakultas);
		prodi = (Spinner) findViewById(R.id.spinProdi);


		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,arPilFakultas);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		fakultas.setAdapter(dataAdapter);

		ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,arPilProdi);
		dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		prodi.setAdapter(dataAdapter2);



		Intent i = getIntent();
		npm0 = i.getStringExtra("pk");

		if(npm0.length()>0){
			new get().execute();
			btnProses.setText("Update Profil");
			btnHapus.setVisibility(View.GONE);
		}
		else{
			btnProses.setText("Add New Data");
			btnHapus.setVisibility(View.GONE);
		}

		btnProses.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				npm= txtnpm.getText().toString();
				String lnama_mahasiswa= txtnama_mahasiswa.getText().toString();
				String lfakultas= txtfakultas.getText().toString();
				String lprodi= txtprodi.getText().toString();
				String ltempat_lahir= txttempat_lahir.getText().toString();
				String ltanggal_lahir= txttanggal_lahir.getText().toString();
				String ljenis_kelamin= txtjenis_kelamin.getText().toString();
				String lno_telp= txtno_telp.getText().toString();
				String lemail= txtemail.getText().toString();
				String lusername= txtusername.getText().toString();
				String lpassword= txtpassword.getText().toString();
				String lkewarganegaraan= txtkewarganegaraan.getText().toString();
				String lagama= txtagama.getText().toString();
				String lalamat= txtalamat.getText().toString();
				String lid_dosen= txtid_dosen.getText().toString();

				if(npm.length()<1){lengkapi("npm");}
				else if(lnama_mahasiswa.length()<1){lengkapi("nama_mahasiswa");}
				else if(lfakultas.length()<1){lengkapi("fakultas");}
				else if(lprodi.length()<1){lengkapi("prodi");}
				else if(ltempat_lahir.length()<1){lengkapi("tempat_lahir");}
				else if(ltanggal_lahir.length()<1){lengkapi("tanggal_lahir");}
				else if(ljenis_kelamin.length()<1){lengkapi("telpon");}
				else if(lkewarganegaraan.length()<1){lengkapi("kewarganegaraan");}
				else if(lagama.length()<1){lengkapi("agama");}
				else if(lalamat.length()<1){lengkapi("alamat");}
				else if(lkewarganegaraan.length()<1){lengkapi("kewarganegaraan");}
				else if(lno_telp.length()<1){lengkapi("no_telp");}
				else if(lemail.length()<1){lengkapi("email");}
				else if(lusername.length()<1){lengkapi("username");}
				else if(lpassword.length()<1){lengkapi("password");}
				else if(lid_dosen.length()<1){lengkapi("id_dosen");}
				else{
					if(npm0.length()>0){
						new update().execute();
					}
					else{
						new save().execute();
					}
				}//else

			}});

		btnHapus.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new del().execute();
			}});
	}

	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	class get extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Mahasiswa.this);
			pDialog.setMessage("Load data detail. Silahkan tunggu...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		protected String doInBackground(String... params) {
			int sukses;
			try {
				List<NameValuePair> params1 = new ArrayList<NameValuePair>();
				params1.add(new BasicNameValuePair("npm", npm0));

				String url=ip+"mahasiswa/mahasiswa_detail.php";
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
								txtnpm.setText(npm0);
								txtnama_mahasiswa.setText(myJSON.getString(TAG_nama_mahasiswa));

								String fak=myJSON.getString(TAG_fakultas);
								int ba=getBarisFak(fak);
								fakultas.setSelection(ba);



								String pro=myJSON.getString(TAG_prodi);
								int ba2=getBarisFak(pro);
								prodi.setSelection(ba2);


								txttempat_lahir.setText(myJSON.getString(TAG_tempat_lahir));
								txttanggal_lahir.setText(myJSON.getString(TAG_tanggal_lahir));
								txtjenis_kelamin.setText(myJSON.getString(TAG_jenis_kelamin));
								txtkewarganegaraan.setText(myJSON.getString(TAG_kewarganegaraan));
								txtagama.setText(myJSON.getString(TAG_agama));
								txtalamat.setText(myJSON.getString(TAG_alamat));
								txtno_telp.setText(myJSON.getString(TAG_no_telp));
								txtemail.setText(myJSON.getString(TAG_email));
								txtusername.setText(myJSON.getString(TAG_username));
								txtpassword.setText(myJSON.getString(TAG_password));
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
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	
	int getBarisFak(String item){

		int index=0;
		int jba=arPilFakultas.length;
		for(int h=0;h<jba;h++){
			if(item.equalsIgnoreCase(arPilFakultas[h])){
				index=h;
				break;
			}
		}
		return index;
	}

	int getBarisPro(String item){

		int index=0;
		int jba=arPilProdi.length;
		for(int h=0;h<jba;h++){
			if(item.equalsIgnoreCase(arPilProdi[h])){
				index=h;
				break;
			}
		}
		return index;
	}
	class save extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Mahasiswa.this);
			pDialog.setMessage("Menyimpan data ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		protected String doInBackground(String... args) {
			npm = txtnpm.getText().toString();
			String lnama_mahasiswa= txtnama_mahasiswa.getText().toString();
			String lfakultas= txtfakultas.getText().toString();
			String lprodi= txtprodi.getText().toString();
			String ltempat_lahir= txttempat_lahir.getText().toString();
			String ltanggal_lahir= txttanggal_lahir.getText().toString();
			String ljenis_kelamin= txtjenis_kelamin.getText().toString();
			String lkewarganegaraan= txtkewarganegaraan.getText().toString();
			String lagama= txtagama.getText().toString();
			String lalamat= txtalamat.getText().toString();
			String lno_telp= txtno_telp.getText().toString();
			String lemail= txtemail.getText().toString();
			String lusername= txtusername.getText().toString();
			String lpassword= txtpassword.getText().toString();
			String lid_dosen= txtid_dosen.getText().toString();



			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("npm0", npm0));
			params.add(new BasicNameValuePair("npm", npm));
			params.add(new BasicNameValuePair("nama_mahasiswa", lnama_mahasiswa));
			params.add(new BasicNameValuePair("fakultas", lfakultas));
			params.add(new BasicNameValuePair("prodi", lprodi));
			params.add(new BasicNameValuePair("tempat_lahir", ltempat_lahir));
			params.add(new BasicNameValuePair("tanggal_lahir", ltanggal_lahir));
			params.add(new BasicNameValuePair("kewarganegaraan", lkewarganegaraan));
			params.add(new BasicNameValuePair("agama", lagama));
			params.add(new BasicNameValuePair("alamat", lalamat));params.add(new BasicNameValuePair("jenis_kelamin", ljenis_kelamin));
			params.add(new BasicNameValuePair("no_telp", lno_telp));
			params.add(new BasicNameValuePair("email", lemail));
			params.add(new BasicNameValuePair("username", lusername));
			params.add(new BasicNameValuePair("password", lpassword));
			params.add(new BasicNameValuePair("id_dosen", lid_dosen));

			String url=ip+"mahasiswa/mahasiswa_add.php";
			Log.v("add",url);
			JSONObject json = jsonParser.makeHttpRequest(url,"POST", params);
			Log.d("add", json.toString());
			try {
				int sukses = json.getInt(TAG_SUKSES);
				if (sukses == 1) {
					Intent i = getIntent();
					setResult(100, i);
					finish();
				} else {
					// gagal update data
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(String file_url) {pDialog.dismiss();}
	}
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	

	class update extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Mahasiswa.this);
			pDialog.setMessage("Mengubah data ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		protected String doInBackground(String... args) {
			npm = txtnpm.getText().toString();
			String lnama_mahasiswa= txtnama_mahasiswa.getText().toString();
			String lfakultas= txtfakultas.getText().toString();
			String lprodi= txtprodi.getText().toString();
			String ltempat_lahir= txttempat_lahir.getText().toString();
			String ltanggal_lahir= txttanggal_lahir.getText().toString();
			String ljenis_kelamin= txtjenis_kelamin.getText().toString();
			String lkewarganegaraan= txtkewarganegaraan.getText().toString();
			String lagama= txtagama.getText().toString();
			String lalamat= txtalamat.getText().toString();
			String lno_telp= txtno_telp.getText().toString();
			String lemail= txtemail.getText().toString();
			String lusername= txtusername.getText().toString();
			String lpassword= txtpassword.getText().toString();
			String lid_dosen= txtid_dosen.getText().toString();

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("npm0", npm0));
			params.add(new BasicNameValuePair("npm", npm));
			params.add(new BasicNameValuePair("nama_mahasiswa", lnama_mahasiswa));
			params.add(new BasicNameValuePair("fakultas", lfakultas));
			params.add(new BasicNameValuePair("prodi", lprodi));
			params.add(new BasicNameValuePair("tempat_lahir", ltempat_lahir));
			params.add(new BasicNameValuePair("tanggal_lahir", ltanggal_lahir));
			params.add(new BasicNameValuePair("jenis_kelamin", ljenis_kelamin));
			params.add(new BasicNameValuePair("kewarganegaraan", lkewarganegaraan));
			params.add(new BasicNameValuePair("agama", lagama));
			params.add(new BasicNameValuePair("alamat", lalamat));
			params.add(new BasicNameValuePair("no_telp", lno_telp));
			params.add(new BasicNameValuePair("email", lemail));
			params.add(new BasicNameValuePair("username", lusername));
			params.add(new BasicNameValuePair("password", lpassword));
			params.add(new BasicNameValuePair("id_dosen", lid_dosen));

			String url=ip+"mahasiswa/mahasiswa_update.php";
			Log.v("update",url);
			JSONObject json = jsonParser.makeHttpRequest(url,"POST", params);
			Log.d("add", json.toString());
			try {
				int sukses = json.getInt(TAG_SUKSES);
				if (sukses == 1) {
					Intent i = getIntent();
					setResult(100, i);
					finish();
				} else {
					// gagal update data
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(String file_url) {pDialog.dismiss();}
	}
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	

	class del extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Mahasiswa.this);
			pDialog.setMessage("Menghapus data...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		protected String doInBackground(String... args) {
			int sukses;
			try {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("npm", npm0));

				String url=ip+"mahasiswa/mahasiswa_del.php";
				Log.v("delete",url);
				JSONObject json = jsonParser.makeHttpRequest(url, "GET", params);
				Log.d("delete", json.toString());
				sukses = json.getInt(TAG_SUKSES);
				if (sukses == 1) {
					Intent i = getIntent();
					setResult(100, i);
					finish();
				}
			}
			catch (JSONException e) {e.printStackTrace();}
			return null;
		}

		protected void onPostExecute(String file_url) {pDialog.dismiss();}
	}
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	

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
