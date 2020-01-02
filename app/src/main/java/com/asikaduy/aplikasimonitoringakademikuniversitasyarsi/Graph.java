package com.asikaduy.aplikasimonitoringakademikuniversitasyarsi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.BarGraphSeries;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Graph extends AppCompatActivity {
    String ip="";
    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();
    JSONArray myJSON = null;
    JSONArray myJSON1 = null;

    private static final String TAG_SUKSES = "sukses";
    private static final String TAG_record = "record";
    String npm="",id_tahunakademik="";

    int jd=0;
    int jd1=0;
    double ipk=0;
    int totsks=0;
    int totnilai=0;
    String arTA[];
    String arNA[];
    String arIPK[];
    String arIPS[];

    private BarChart mBarChart;
    List<BarEntry> dataIPK = new ArrayList<BarEntry>();
    List<BarEntry> dataIPS = new ArrayList<BarEntry>();

    String IPK="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.graph);
        setContentView(R.layout.activity_chart_common);
        mBarChart = findViewById(R.id.chart);
        ip=jParser.getIP();
        Intent i = getIntent();
        npm = i.getStringExtra("npm");
        id_tahunakademik = i.getStringExtra("id_tahunakademik");
        new load().execute();
        //call();
    }

    class load extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Graph.this);
            pDialog.setMessage("Load data. Silahkan Tunggu...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("npm", npm));
            JSONObject json = jParser.makeHttpRequest(ip+"tahunakademik/grafik.php", "GET", params);

            List<NameValuePair> params1 = new ArrayList<NameValuePair>();
            params1.add(new BasicNameValuePair("npm", npm));
            params1.add(new BasicNameValuePair("id_tahunakademik", id_tahunakademik));
            JSONObject json1 = jParser.makeHttpRequest(ip+"nilaidetail/nilaidetail_show.php", "GET", params1);

            Log.d("show: ", json.toString());
            try {
                int sukses = json.getInt(TAG_SUKSES);
                if (sukses == 1) {
                    myJSON = json.getJSONArray(TAG_record);

                   jd= myJSON.length();
                   Log.v("Panjang json nya", String.valueOf(jd));
                    arTA  = new String[jd];
                    arNA  = new String[jd];
                    arIPK = new String[jd];

                    for (int i = 0; i < myJSON.length(); i++) {
                        JSONObject c = myJSON.getJSONObject(i);
                        String id_tahunajaran= c.getString("id_tahunakademik");
                        String nama_tahunajaran = c.getString("nama_tahunakademik");
                        String IPK = c.getString("IPK");

                        arTA[i]=id_tahunajaran;
                        arNA[i]=nama_tahunajaran;
                        arIPK[i]=IPK;
                    }
                } else {

                }

                totsks=0;
                totnilai=0;
                int sukses1 = json1.getInt(TAG_SUKSES);
                if(sukses1 == 1){
                    myJSON1 = json1.getJSONArray(TAG_record);
                    jd1     = myJSON1.length();
                    arIPS   = new String[jd1];
                    for (int i = 0; i < myJSON1.length(); i++) {
                        JSONObject c1 = myJSON1.getJSONObject(i);
                        String grade= c1.getString("grade");
                        String lsks= c1.getString("sks");

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
                        ipk=totnilai/totsks;
                        String sipk=String.valueOf(ipk);
                        arIPS[i]=sipk;
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
                    grafik();
                }
            });}
    }

void grafik(){

//    DataPoint []ar=null;
//    for(int i=0;i<jd;i++) {
//        ar[i] = new DataPoint(0, Double.parseDouble(arIPK[i]));
//    }
//    BarGraphSeries<DataPoint> line_series =new BarGraphSeries<DataPoint>(ar);

//    GraphView line_graph = (GraphView) findViewById(R.id.graph);
//    GraphView line_graph1 = (GraphView) findViewById(R.id.graph1);

//    BarGraphSeries<DataPoint> line_series =new BarGraphSeries<DataPoint>(new DataPoint[] {
//            new DataPoint(0, Double.parseDouble(arIPK[0])),
//            new DataPoint(1, Double.parseDouble(arIPK[1]))
//    });

//    BarGraphSeries<DataPoint> line_series  = null;
//    BarGraphSeries<DataPoint> line_series1 = null;
//    if(jd==1) {
//        line_series = new BarGraphSeries<DataPoint>(new DataPoint[]{
//                new DataPoint(0, Double.parseDouble(arIPK[0]))
//        });
//    }
//    else if(jd==2) {
//            line_series = new BarGraphSeries<DataPoint>(new DataPoint[]{
//                    new DataPoint(0, Double.parseDouble(arIPK[0])),
//                    new DataPoint(1, Double.parseDouble(arIPK[1]))
//            });
//            line_series1 = new BarGraphSeries<DataPoint>(new DataPoint[]{
//                    new DataPoint(0, Double.parseDouble(arIPS[0])),
//                    new DataPoint(0, Double.parseDouble(arIPS[1]))
//            });
//        }
//    else if(jd==3) {
//        line_series = new BarGraphSeries<DataPoint>(new DataPoint[]{
//                new DataPoint(0, Double.parseDouble(arIPK[0])),
//                new DataPoint(1, Double.parseDouble(arIPK[1])),
//                new DataPoint(1, Double.parseDouble(arIPK[2]))
//        });
//    }
//    else if(jd==4) {
//        line_series = new BarGraphSeries<DataPoint>(new DataPoint[]{
//                new DataPoint(0, Double.parseDouble(arIPK[0])),
//                new DataPoint(1, Double.parseDouble(arIPK[1])),
//                new DataPoint(0, Double.parseDouble(arIPK[2])),
//                new DataPoint(1, Double.parseDouble(arIPK[3]))
//
//        });
//    }
//    else if(jd==5) {
//        line_series = new BarGraphSeries<DataPoint>(new DataPoint[]{
//                new DataPoint(0, Double.parseDouble(arIPK[0])),
//                new DataPoint(1, Double.parseDouble(arIPK[1])),
//                new DataPoint(1, Double.parseDouble(arIPK[2])),
//                new DataPoint(1, Double.parseDouble(arIPK[3])),
//                new DataPoint(1, Double.parseDouble(arIPK[4]))
//        });
//    }
//    else if(jd==6) {
//        line_series = new BarGraphSeries<DataPoint>(new DataPoint[]{
//                new DataPoint(0, Double.parseDouble(arIPK[0])),
//                new DataPoint(1, Double.parseDouble(arIPK[1])),
//                new DataPoint(1, Double.parseDouble(arIPK[2])),
//                new DataPoint(1, Double.parseDouble(arIPK[3])),
//                new DataPoint(1, Double.parseDouble(arIPK[4])),
//                new DataPoint(1, Double.parseDouble(arIPK[5]))
//        });
//    }
//    else if(jd==7) {
//        line_series = new BarGraphSeries<DataPoint>(new DataPoint[]{
//                new DataPoint(0, Double.parseDouble(arIPK[0])),
//                new DataPoint(1, Double.parseDouble(arIPK[1])),
//                new DataPoint(1, Double.parseDouble(arIPK[2])),
//                new DataPoint(1, Double.parseDouble(arIPK[3])),
//                new DataPoint(1, Double.parseDouble(arIPK[4])),
//                new DataPoint(1, Double.parseDouble(arIPK[5])),
//                new DataPoint(1, Double.parseDouble(arIPK[6]))
//        });
//    }
//    else if(jd==8) {
//        line_series = new BarGraphSeries<DataPoint>(new DataPoint[]{
//                new DataPoint(0, Double.parseDouble(arIPK[0])),
//                new DataPoint(1, Double.parseDouble(arIPK[1])),
//                new DataPoint(1, Double.parseDouble(arIPK[2])),
//                new DataPoint(1, Double.parseDouble(arIPK[3])),
//                new DataPoint(1, Double.parseDouble(arIPK[4])),
//                new DataPoint(1, Double.parseDouble(arIPK[5])),
//                new DataPoint(1, Double.parseDouble(arIPK[6])),
//                new DataPoint(1, Double.parseDouble(arIPK[7]))
//
//        });
//    }
//    else if(jd==9) {
//        line_series = new BarGraphSeries<DataPoint>(new DataPoint[]{
//                new DataPoint(0, Double.parseDouble(arIPK[0])),
//                new DataPoint(1, Double.parseDouble(arIPK[1])),
//                new DataPoint(1, Double.parseDouble(arIPK[2])),
//                new DataPoint(1, Double.parseDouble(arIPK[3])),
//                new DataPoint(1, Double.parseDouble(arIPK[4])),
//                new DataPoint(1, Double.parseDouble(arIPK[5])),
//                new DataPoint(1, Double.parseDouble(arIPK[6])),
//                new DataPoint(1, Double.parseDouble(arIPK[7])),
//                new DataPoint(1, Double.parseDouble(arIPK[8]))
//
//        });
//    }
//    else if(jd==10) {
//        line_series = new BarGraphSeries<DataPoint>(new DataPoint[]{
//                new DataPoint(0, Double.parseDouble(arIPK[0])),
//                new DataPoint(1, Double.parseDouble(arIPK[1])),
//                new DataPoint(1, Double.parseDouble(arIPK[2])),
//                new DataPoint(1, Double.parseDouble(arIPK[3])),
//                new DataPoint(1, Double.parseDouble(arIPK[4])),
//                new DataPoint(1, Double.parseDouble(arIPK[5])),
//                new DataPoint(1, Double.parseDouble(arIPK[6])),
//                new DataPoint(1, Double.parseDouble(arIPK[7])),
//                new DataPoint(1, Double.parseDouble(arIPK[8])),
//                new DataPoint(1, Double.parseDouble(arIPK[9]))
//        });
//    }
//    else if(jd==11) {
//        line_series = new BarGraphSeries<DataPoint>(new DataPoint[]{
//                new DataPoint(0, Double.parseDouble(arIPK[0])),
//                new DataPoint(1, Double.parseDouble(arIPK[1])),
//                new DataPoint(1, Double.parseDouble(arIPK[2])),
//                new DataPoint(1, Double.parseDouble(arIPK[3])),
//                new DataPoint(1, Double.parseDouble(arIPK[4])),
//                new DataPoint(1, Double.parseDouble(arIPK[5])),
//                new DataPoint(1, Double.parseDouble(arIPK[6])),
//                new DataPoint(1, Double.parseDouble(arIPK[7])),
//                new DataPoint(1, Double.parseDouble(arIPK[8])),
//                new DataPoint(1, Double.parseDouble(arIPK[9])),
//                new DataPoint(1, Double.parseDouble(arIPK[10]))
//
//        });
//    }
//    else if(jd==12) {
//        line_series = new BarGraphSeries<DataPoint>(new DataPoint[]{
//                new DataPoint(0, Double.parseDouble(arIPK[0])),
//                new DataPoint(1, Double.parseDouble(arIPK[1])),
//                new DataPoint(1, Double.parseDouble(arIPK[2])),
//                new DataPoint(1, Double.parseDouble(arIPK[3])),
//                new DataPoint(1, Double.parseDouble(arIPK[4])),
//                new DataPoint(1, Double.parseDouble(arIPK[5])),
//                new DataPoint(1, Double.parseDouble(arIPK[6])),
//                new DataPoint(1, Double.parseDouble(arIPK[7])),
//                new DataPoint(1, Double.parseDouble(arIPK[8])),
//                new DataPoint(1, Double.parseDouble(arIPK[9])),
//                new DataPoint(1, Double.parseDouble(arIPK[10])),
//                new DataPoint(1, Double.parseDouble(arIPK[11]))
//
//        });
//    }

//    //nilai ipk get dari database
//    line_graph.addSeries(line_series);
//    //line_graph.getViewport().setXAxisBoundsManual(true);
//    //line_graph.getViewport().setMinX(0);
//    //line_graph.getViewport().setMaxX(jd);//8
//
//    //Untuk Range IPK nya
//    line_graph.setTitle("Grafik IPK");
//    line_graph.getViewport().setYAxisBoundsManual(true);
//    line_graph.getViewport().setMinY(0);
//    line_graph.getViewport().setMaxY(4);
//
//    line_graph.getViewport().setScrollable(true);
//
//    //graph 2
//    //nilai ipk get dari database
//    line_graph1.addSeries(line_series1);
//    //line_graph.getViewport().setXAxisBoundsManual(true);
//    //line_graph.getViewport().setMinX(0);
//    //line_graph.getViewport().setMaxX(jd);//8
//
//    //Untuk Range IPK nya
//    line_graph1.setTitle("Grafik IPS");
//    line_graph1.getViewport().setYAxisBoundsManual(true);
//    line_graph1.getViewport().setMinY(0);
//    line_graph1.getViewport().setMaxY(4);
//    line_graph.getViewport().setScrollable(true);

/*====================================================================*/
    float groupSpace = 0.08f;
    float barSpace = 0.02f;
    float barWidth = 0.45f;
    float tahunAwal = 1f;

    if(jd==1) {
        // Data-data yang akan ditampilkan di Chart
        dataIPK.add(new BarEntry(1, 1500000));
        dataIPS.add(new BarEntry(1, 500000));
    }
    else if(jd==2) {
        // Data-data yang akan ditampilkan di Chart
        dataIPK.add(new BarEntry(1, Float.parseFloat(arIPK[0])));
        dataIPK.add(new BarEntry(2, Float.parseFloat(arIPK[1])));
        dataIPS.add(new BarEntry(1, Float.parseFloat(arIPS[0])));
        dataIPS.add(new BarEntry(2, Float.parseFloat(arIPS[1])));
    }
    else if(jd==3) {

    }
    else if(jd==4) {

    }
    else if(jd==5) {

    }
    else if(jd==6) {

    }
    else if(jd==7) {

    }
    else if(jd==8) {

    }
    else if(jd==9) {

    }
    else if(jd==10) {

    }
    else if(jd==11) {

    }
    else if(jd==12) {

    }


    YAxis y = mBarChart.getAxisLeft();
    y.setAxisMaxValue(4);
    y.setAxisMinValue(0);

    // Pengaturan atribut bar, seperti warna dan lain-lain
    BarDataSet dataSet1 = new BarDataSet(dataIPK, "IPK");
    dataSet1.setColor(ColorTemplate.JOYFUL_COLORS[0]);

    BarDataSet dataSet2 = new BarDataSet(dataIPS, "IPS");
    dataSet2.setColor(ColorTemplate.JOYFUL_COLORS[1]);

    // Membuat Bar data yang akan di set ke Chart
    BarData barData = new BarData(dataSet1, dataSet2);

    // Pengaturan sumbu X
    XAxis xAxis = mBarChart.getXAxis();
    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM.BOTTOM);
    xAxis.setCenterAxisLabels(true);

    // Agar ketika di zoom tidak menjadi pecahan
    xAxis.setGranularity(1f);

    // Diubah menjadi integer, kemudian dijadikan String
    // Ini berfungsi untuk menghilankan koma, dan tanda ribuah pada tahun
    xAxis.setValueFormatter(new IAxisValueFormatter() {
        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return String.valueOf((int) value);
        }
    });

    //Menghilangkan sumbu Y yang ada di sebelah kanan
    mBarChart.getAxisRight().setEnabled(false);

    // Menghilankan deskripsi pada Chart
    mBarChart.getDescription().setEnabled(false);

    // Set data ke Chart
    // Tambahkan invalidate setiap kali mengubah data chart
    mBarChart.setData(barData);
    mBarChart.getBarData().setBarWidth(barWidth);
    mBarChart.getXAxis().setAxisMinimum(tahunAwal);
    mBarChart.getXAxis().setAxisMaximum(tahunAwal + mBarChart.getBarData().getGroupWidth(groupSpace, barSpace) * 7);
    mBarChart.groupBars(tahunAwal, groupSpace, barSpace);
    mBarChart.setDragEnabled(true);
    mBarChart.invalidate();
}


    void call(){

        // set the dynamically label

        /*line_graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    // show normal x values
                    return super.formatLabel(value, isValueX);
                } else {
                    // show currency for y values
                    return super.formatLabel(value, isValueX) + " $";
                }
            }
        });*/

        //set the static label
       /* StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(line_graph);
        staticLabelsFormatter.setHorizontalLabels(new String[] {"Jan", "Feb", "March"});
        staticLabelsFormatter.setVerticalLabels(new String[] {"Sun", "Mon", "Tue"});
        line_graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);*/


        // custom paint to make a dotted line
       /* Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setPathEffect(new DashPathEffect(new float[]{8, 5}, 0));
        line_series.setCustomPaint(paint);*/


        // set the radius of data point
       /* line_series.setDrawDataPoints(true);
        line_series.setDataPointsRadius(10);*/


        // set the background color below the line
        /*line_series.setDrawBackground(true);
        line_series.setBackgroundColor(Color.BLUE);*/


        // set the thickness of line
        // line_series.setThickness(20);

    }
}
