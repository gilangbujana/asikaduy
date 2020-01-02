package com.asikaduy.aplikasimonitoringakademikuniversitasyarsi;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Profil extends Activity {
	String ip="";
	String npm,nama_mahasiswa;
	String npm0="";

	Spinner spinJK;
	Spinner spinAgama;

	String[]arPilAgama={"Islam","Kristen","Hindu","Budha"};
	String[]arPilJK={"Laki-Laki","Perempuan"};


	EditText txtnpm;
	EditText txtnama_mahasiswa;
	EditText txtfakultas;
	EditText txtprodi;

	EditText txttempat_lahir;
	EditText txttanggal_lahir;
	EditText txtkewarganegaraan;
	EditText txtalamat;
	EditText txtno_telp;
	EditText txtemail;
	EditText txtusername;
	EditText txtpassword;
	EditText txtid_dosen;

	Button btnProses;
	Button btnHapus;

	String foto="";
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
		setContentView(R.layout.profil);

		ip=jsonParser.getIP();

		txtnpm = (EditText) findViewById(R.id.txtnpm);txtnpm.setEnabled(false);
		txtnama_mahasiswa= (EditText) findViewById(R.id.txtnama_mahasiswa);txtnama_mahasiswa.setEnabled(false);
		txtfakultas= (EditText) findViewById(R.id.txtfakultas);txtfakultas.setEnabled(false);
		txtprodi= (EditText) findViewById(R.id.txtprodi);txtprodi.setEnabled(false);
		txttempat_lahir= (EditText) findViewById(R.id.txttempat_lahir);txttempat_lahir.setEnabled(false);
		txttanggal_lahir= (EditText) findViewById(R.id.txttanggal_lahir);txttanggal_lahir.setEnabled(false);
		txtkewarganegaraan = (EditText) findViewById(R.id.txtkewarganegaraan);txtkewarganegaraan.setEnabled(false);
		txtalamat= (EditText) findViewById(R.id.txtalamat);txtalamat.setEnabled(false);
		txtno_telp= (EditText) findViewById(R.id.txtno_telp);txtno_telp.setEnabled(false);
		txtemail= (EditText) findViewById(R.id.txtemail);txtemail.setEnabled(false);
		txtusername= (EditText) findViewById(R.id.txtusername);txtusername.setEnabled(false);
		txtpassword= (EditText) findViewById(R.id.txtpassword);
		txtalamat= (EditText) findViewById(R.id.txtalamat);txtalamat.setEnabled(false);
		txtid_dosen= (EditText) findViewById(R.id.txtid_dosen);txtid_dosen.setEnabled(false);


		btnProses= (Button) findViewById(R.id.btnproses);
		btnHapus = (Button) findViewById(R.id.btnhapus);




		spinAgama= (Spinner) findViewById(R.id.spinagama);spinAgama.setEnabled(false);
		spinJK = (Spinner) findViewById(R.id.spinjk);spinJK.setEnabled(false);


		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,arPilAgama);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinAgama.setAdapter(dataAdapter);

		ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,arPilJK);
		dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinJK.setAdapter(dataAdapter2);

		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(Profil.this);
		Boolean Registered = sharedPref.getBoolean("Registered", false);
		if (!Registered) {
			finish();
		} else {
			npm = sharedPref.getString("npm", "");
			npm0=npm;
			nama_mahasiswa = sharedPref.getString("nama_mahasiswa", "");
		}

		new get().execute();

		btnProses.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				npm= txtnpm.getText().toString();
				String lnama_mahasiswa= txtnama_mahasiswa.getText().toString();
				String ltempat_lahir= txttempat_lahir.getText().toString();
				String ltanggal_lahir= txttanggal_lahir.getText().toString();
				String ljenis_kelamin= spinJK.getSelectedItem().toString();
				String lno_telp= txtno_telp.getText().toString();
				String lemail= txtemail.getText().toString();
				String lusername= txtusername.getText().toString();
				String lpassword= txtpassword.getText().toString();
				String lkewarganegaraan= txtkewarganegaraan.getText().toString();
				String lagama= spinAgama.getSelectedItem().toString();
				String lalamat= txtalamat.getText().toString();

				if(lpassword.length()<1){lengkapi("Password");}
			else{
					new update().execute();
				}
			}});

		btnHapus.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
			finish();
			}});

		btnProses.setText("Ubah Password");
		btnHapus.setText("Kembali");

	}

	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;

		public DownloadImageTask(ImageView bmImage) {
			this.bmImage = bmImage;
		}

		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];
			Bitmap mIcon11 = null;
			try {
				InputStream in = new java.net.URL(urldisplay).openStream();
				mIcon11 = BitmapFactory.decodeStream(in);
			}
			catch (Exception e) {Log.e("Error", e.getMessage());e.printStackTrace();}
			return mIcon11;
		}

		protected void onPostExecute(Bitmap result) {
			bmImage.setImageBitmap(result); }
	}
	class get extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Profil.this);
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

								String ag=myJSON.getString("agama");
								int ba=getBarisAgama(ag);
								spinAgama.setSelection(ba);



								String jk=myJSON.getString(TAG_jenis_kelamin);
								int ba2=getBarisJK(jk);
								spinJK.setSelection(ba2);


								txttempat_lahir.setText(myJSON.getString(TAG_tempat_lahir));
								txttanggal_lahir.setText(myJSON.getString(TAG_tanggal_lahir));
								txtkewarganegaraan.setText(myJSON.getString(TAG_kewarganegaraan));
								txtalamat.setText(myJSON.getString(TAG_alamat));
								txtno_telp.setText(myJSON.getString(TAG_no_telp));
								txtemail.setText(myJSON.getString(TAG_email));
								txtusername.setText(myJSON.getString(TAG_username));
								txtpassword.setText(myJSON.getString(TAG_password));

foto=myJSON.getString("foto");
								txtid_dosen.setText(myJSON.getString(TAG_id_dosen));
								txtprodi.setText(myJSON.getString(TAG_prodi));
								txtfakultas.setText(myJSON.getString(TAG_fakultas));

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
		protected void onPostExecute(String file_url) {pDialog.dismiss();
			String arUrlFoto=ip+"ypathfile/"+foto;
			new DownloadImageTask((ImageView) findViewById(R.id.myGambar)).execute(arUrlFoto); }
	}
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	
	int getBarisAgama(String item){

		int index=0;
		int jba=arPilAgama.length;
		for(int h=0;h<jba;h++){
			if(item.equalsIgnoreCase(arPilAgama[h])){
				index=h;
				break;
			}
		}
		return index;
	}

	int getBarisJK(String item){

		int index=0;
		int jba=arPilJK.length;
		for(int h=0;h<jba;h++){
			if(item.equalsIgnoreCase(arPilJK[h])){
				index=h;
				break;
			}
		}
		return index;
	}


	class update extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Profil.this);
			pDialog.setMessage("Mengubah data ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		protected String doInBackground(String... args) {
			npm = txtnpm.getText().toString();
			String lpassword= txtpassword.getText().toString();
			List<NameValuePair> params = new ArrayList<NameValuePair>();

			params.add(new BasicNameValuePair("npm", npm));
			params.add(new BasicNameValuePair("password", lpassword));

			String url=ip+"mahasiswa/mahasiswa_update2.php";
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

		protected void onPostExecute(String file_url) {pDialog.dismiss();
			sukses();
		}
	}
	class update2 extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Profil.this);
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
			String ljenis_kelamin= spinJK.getSelectedItem().toString();
			String lkewarganegaraan= txtkewarganegaraan.getText().toString();
			String lagama= spinAgama.getSelectedItem().toString();
			String lalamat= txtalamat.getText().toString();
			String lno_telp= txtno_telp.getText().toString();
			String lemail= txtemail.getText().toString();
			String lusername= txtusername.getText().toString();
			String lpassword= txtpassword.getText().toString();
			String lid_dosen= txtid_dosen.getText().toString();

			List<NameValuePair> params = new ArrayList<NameValuePair>();

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

		protected void onPostExecute(String file_url) {pDialog.dismiss();
			sukses();
		}
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

	public void sukses(){
		new AlertDialog.Builder(this)
				.setTitle("Update Berhasil")
				.setMessage("Update Password Berhasil")
				.setNeutralButton("Tutup", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dlg, int sumthin) {
						finish();
					}}).show();
	}

}
