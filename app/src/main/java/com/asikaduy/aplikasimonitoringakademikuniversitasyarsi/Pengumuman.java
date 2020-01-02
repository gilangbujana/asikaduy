package com.asikaduy.aplikasimonitoringakademikuniversitasyarsi;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Pengumuman extends Activity {
	String ip="";
	String id_pengumuman;
	String id_pengumuman0="";

	EditText txttanggal;
	EditText txtjudul;
	EditText txtisi;
	EditText txtketerangan;

String gambar="";
	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();

	private static final String TAG_SUKSES = "sukses";
	private static final String TAG_record = "record";

	private static final String TAG_tanggal = "tanggal";
	private static final String TAG_judul = "judul";
	private static final String TAG_isi = "isi";
	private static final String TAG_gambar = "gambar";
	private static final String TAG_status = "status";
	private static final String TAG_keterangan = "keterangan";

	private DatePickerDialog datePickerDialog;
	private SimpleDateFormat dateFormatter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pengumuman);

		ip=jsonParser.getIP();

		txttanggal= (EditText) findViewById(R.id.txttanggal);txttanggal.setEnabled(false);
		txtisi= (EditText) findViewById(R.id.txtisi);txtisi.setEnabled(false);
		txtjudul= (EditText) findViewById(R.id.txtjudul);txtjudul.setEnabled(false);
		txtketerangan= (EditText) findViewById(R.id.txtketerangan);txtketerangan.setEnabled(false);

		Intent i = getIntent();
		id_pengumuman0 = i.getStringExtra("pk");

		if(id_pengumuman0.length()>0){
			new get().execute();
		}

		dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

		txttanggal.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				showDateDialog();
			}
		});



	}


	private void showDateDialog(){

		/**
		 * Calendar untuk mendapatkan tanggal sekarang
		 */
		Calendar newCalendar = Calendar.getInstance();

		/**
		 * Initiate DatePicker dialog
		 */
		datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

				/**
				 * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
				 */

				/**
				 * Set Calendar untuk menampung tanggal yang dipilih
				 */
				Calendar newDate = Calendar.getInstance();
				newDate.set(year, monthOfYear, dayOfMonth);

				/**
				 * Update TextView dengan tanggal yang kita pilih
				 */
				txttanggal.setText(""+dateFormatter.format(newDate.getTime()));
			}

		},newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

		/**
		 * Tampilkan DatePicker dialog
		 */
		datePickerDialog.show();
	}

	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	class get extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Pengumuman.this);
			pDialog.setMessage("Load data detail. Silahkan tunggu...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		protected String doInBackground(String... params) {
			int sukses;
			try {
				List<NameValuePair> params1 = new ArrayList<NameValuePair>();
				params1.add(new BasicNameValuePair("id_pengumuman", id_pengumuman0));

				String url=ip+"pengumuman/pengumuman_detail.php";
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
								txttanggal.setText(myJSON.getString(TAG_tanggal));
								txtjudul.setText(myJSON.getString(TAG_judul));
								txtisi.setText(myJSON.getString(TAG_isi));
								txtketerangan.setText(myJSON.getString(TAG_keterangan));
								gambar=myJSON.getString("gambar");
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
			String arUrlFoto=ip+"ypathfile/"+gambar;
			new DownloadImageTask((ImageView) findViewById(R.id.myGambar)).execute(arUrlFoto);
		}
	}


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

	//++++
	//
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//
//	class save extends AsyncTask<String, String, String> {
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//			pDialog = new ProgressDialog(Pengumuman.this);
//			pDialog.setMessage("Menyimpan data ...");
//			pDialog.setIndeterminate(false);
//			pDialog.setCancelable(true);
//			pDialog.show();
//		}
//		protected String doInBackground(String... args) {
//			id_pengumuman = txtid_pengumuman.getText().toString();
//			String ltanggal= txttanggal.getText().toString();
//			String lisi= txtisi.getText().toString();
//			String ljudul= txtjudul.getText().toString();
//			String lgambar= txtgambar.getText().toString();
//			String lstatus= txtstatus.getText().toString();
//			String lketerangan= txtketerangan.getText().toString();
//
//			List<NameValuePair> params = new ArrayList<NameValuePair>();
//			params.add(new BasicNameValuePair("id_pengumuman0", id_pengumuman0));
//			params.add(new BasicNameValuePair("id_pengumuman", id_pengumuman));
//			params.add(new BasicNameValuePair("tanggal", ltanggal));
//			params.add(new BasicNameValuePair("isi", lisi));
//			params.add(new BasicNameValuePair("judul", ljudul));
//			params.add(new BasicNameValuePair("gambar", lgambar));
//			params.add(new BasicNameValuePair("status", lstatus));
//			params.add(new BasicNameValuePair("keterangan", lketerangan));
//
//			String url=ip+"pengumuman/pengumuman_add.php";
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
//			pDialog = new ProgressDialog(Pengumuman.this);
//			pDialog.setMessage("Mengubah data ...");
//			pDialog.setIndeterminate(false);
//			pDialog.setCancelable(true);
//			pDialog.show();
//		}
//		protected String doInBackground(String... args) {
//			id_pengumuman = txtid_pengumuman.getText().toString();
//			String ltanggal= txttanggal.getText().toString();
//			String lisi= txtisi.getText().toString();
//			String ljudul= txtjudul.getText().toString();
//			String lgambar= txtgambar.getText().toString();
//			String lstatus= txtstatus.getText().toString();
//			String lketerangan= txtketerangan.getText().toString();
//
//			List<NameValuePair> params = new ArrayList<NameValuePair>();
//			params.add(new BasicNameValuePair("id_pengumuman0", id_pengumuman0));
//			params.add(new BasicNameValuePair("id_pengumuman", id_pengumuman));
//			params.add(new BasicNameValuePair("tanggal", ltanggal));
//			params.add(new BasicNameValuePair("isi", lisi));
//			params.add(new BasicNameValuePair("judul", ljudul));
//			params.add(new BasicNameValuePair("gambar", lgambar));
//			params.add(new BasicNameValuePair("status", lstatus));
//			params.add(new BasicNameValuePair("keterangan", lketerangan));
//
//			String url=ip+"pengumuman/pengumuman_update.php";
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
//			pDialog = new ProgressDialog(Pengumuman.this);
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
//				params.add(new BasicNameValuePair("id_pengumuman", id_pengumuman0));
//
//				String url=ip+"pengumuman/pengumuman_del.php";
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
		int bln= cal.get(Calendar.MONTH);
		int thn= cal.get(Calendar.YEAR);

		String stgl=String.valueOf(tgl)+"-"+String.valueOf(bln)+"-"+String.valueOf(thn);
		String sjam=String.valueOf(jam)+":"+String.valueOf(menit)+":"+String.valueOf(detik);

		TextView  txtMarquee=(TextView)findViewById(R.id.txtMarquee);
		txtMarquee.setSelected(true);
		String kata="Selamat Datang @lp2maray.com Aplikasi Android  "+stgl+"/"+sjam+" #";
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
