package com.asikaduy.aplikasimonitoringakademikuniversitasyarsi;

import android.content.res.Resources;


import com.asikaduy.aplikasimonitoringakademikuniversitasyarsi.charts.Area3DChartActivity;
import com.asikaduy.aplikasimonitoringakademikuniversitasyarsi.charts.AreaChartActivity;
import com.asikaduy.aplikasimonitoringakademikuniversitasyarsi.charts.Bar3DChartActivity;
import com.asikaduy.aplikasimonitoringakademikuniversitasyarsi.charts.BarChartActivity;
import com.asikaduy.aplikasimonitoringakademikuniversitasyarsi.charts.BoxChartActivity;
import com.asikaduy.aplikasimonitoringakademikuniversitasyarsi.charts.BubbleChartActivity;
import com.asikaduy.aplikasimonitoringakademikuniversitasyarsi.charts.BubbleMapActivity;
import com.asikaduy.aplikasimonitoringakademikuniversitasyarsi.charts.ChoroplethMapActivity;
import com.asikaduy.aplikasimonitoringakademikuniversitasyarsi.charts.CircularGaugeActivity;
import com.asikaduy.aplikasimonitoringakademikuniversitasyarsi.charts.Column3DChartActivity;
import com.asikaduy.aplikasimonitoringakademikuniversitasyarsi.charts.ColumnChartActivity;
import com.asikaduy.aplikasimonitoringakademikuniversitasyarsi.charts.CombinedChartActivity;
import com.asikaduy.aplikasimonitoringakademikuniversitasyarsi.charts.ConnectorMapActivity;
import com.asikaduy.aplikasimonitoringakademikuniversitasyarsi.charts.FunnelChartActivity;
import com.asikaduy.aplikasimonitoringakademikuniversitasyarsi.charts.HeatMapChartActivity;
import com.asikaduy.aplikasimonitoringakademikuniversitasyarsi.charts.HiloChartActivity;
import com.asikaduy.aplikasimonitoringakademikuniversitasyarsi.charts.LineChartActivity;
import com.asikaduy.aplikasimonitoringakademikuniversitasyarsi.charts.LinearColorScaleActivity;
import com.asikaduy.aplikasimonitoringakademikuniversitasyarsi.charts.MekkoChartActivity;
import com.asikaduy.aplikasimonitoringakademikuniversitasyarsi.charts.MosaicChartActivity;
import com.asikaduy.aplikasimonitoringakademikuniversitasyarsi.charts.OHLCChartActivity;
import com.asikaduy.aplikasimonitoringakademikuniversitasyarsi.charts.ParetoChartActivity;
import com.asikaduy.aplikasimonitoringakademikuniversitasyarsi.charts.PertChartActivity;
import com.asikaduy.aplikasimonitoringakademikuniversitasyarsi.charts.PieChartActivity;
import com.asikaduy.aplikasimonitoringakademikuniversitasyarsi.charts.PointMapActivity;
import com.asikaduy.aplikasimonitoringakademikuniversitasyarsi.charts.PolarChartActivity;
import com.asikaduy.aplikasimonitoringakademikuniversitasyarsi.charts.PyramidActivity;
import com.asikaduy.aplikasimonitoringakademikuniversitasyarsi.charts.QuadrantChartActivity;
import com.asikaduy.aplikasimonitoringakademikuniversitasyarsi.charts.RadarChartActivity;
import com.asikaduy.aplikasimonitoringakademikuniversitasyarsi.charts.RangeChartActivity;
import com.asikaduy.aplikasimonitoringakademikuniversitasyarsi.charts.ResourceChartActivity;
import com.asikaduy.aplikasimonitoringakademikuniversitasyarsi.charts.ScatterChartActivity;
import com.asikaduy.aplikasimonitoringakademikuniversitasyarsi.charts.SunburstChartActivity;
import com.asikaduy.aplikasimonitoringakademikuniversitasyarsi.charts.TagCloudActivity;
import com.asikaduy.aplikasimonitoringakademikuniversitasyarsi.charts.ThermometerActivity;
import com.asikaduy.aplikasimonitoringakademikuniversitasyarsi.charts.TreeMapChartActivity;
import com.asikaduy.aplikasimonitoringakademikuniversitasyarsi.charts.TwoPiesOneColumnActivity;
import com.asikaduy.aplikasimonitoringakademikuniversitasyarsi.charts.VennDiagramActivity;
import com.asikaduy.aplikasimonitoringakademikuniversitasyarsi.charts.VerticalChartActivity;
import com.asikaduy.aplikasimonitoringakademikuniversitasyarsi.charts.WaterfallChartActivity;
import com.asikaduy.aplikasimonitoringakademikuniversitasyarsi.charts.WindDirectionActivity;
import com.asikaduy.aplikasimonitoringakademikuniversitasyarsi.charts.WindSpeedActivity;

import java.util.ArrayList;

public class Chart {

    private String name;
    private Class activityClass;

    private Chart(String name, Class activityClass) {
        this.name = name;
        this.activityClass = activityClass;
    }

    public String getName() {
        return name;
    }

    Class getActivityClass() {
        return activityClass;
    }

    static ArrayList<Chart> createChartList(Resources resources) {
        ArrayList<Chart> chartList = new ArrayList<>();

        chartList.add(new Chart(resources.getString(R.string.pie_chart), PieChartActivity.class));
        chartList.add(new Chart(resources.getString(R.string.column_chart), ColumnChartActivity.class));
        chartList.add(new Chart(resources.getString(R.string.line_chart), LineChartActivity.class));
//        chartList.add(new Chart(resources.getString(R.string.area_chart), AreaChartActivity.class));
//        chartList.add(new Chart(resources.getString(R.string.bar_chart), BarChartActivity.class));
//        chartList.add(new Chart(resources.getString(R.string.venn_diagram), VennDiagramActivity.class));
//        chartList.add(new Chart(resources.getString(R.string.heat_map_chart), HeatMapChartActivity.class));
//        chartList.add(new Chart(resources.getString(R.string.tag_cloud), TagCloudActivity.class));
//        chartList.add(new Chart(resources.getString(R.string.waterfall_chart), WaterfallChartActivity.class));
//        chartList.add(new Chart(resources.getString(R.string.tree_map_chart), TreeMapChartActivity.class));
//        chartList.add(new Chart(resources.getString(R.string.circular_gauge), CircularGaugeActivity.class));
//        chartList.add(new Chart(resources.getString(R.string.thermometer), ThermometerActivity.class));
//        chartList.add(new Chart(resources.getString(R.string.linear_color_scale), LinearColorScaleActivity.class));
//        chartList.add(new Chart(resources.getString(R.string.wind_speed), WindSpeedActivity.class));
//        chartList.add(new Chart(resources.getString(R.string.wind_direction), WindDirectionActivity.class));
//        chartList.add(new Chart(resources.getString(R.string.scatter_chart), ScatterChartActivity.class));
//        chartList.add(new Chart(resources.getString(R.string.resource_chart), ResourceChartActivity.class));
//        chartList.add(new Chart(resources.getString(R.string.radar_chart), RadarChartActivity.class));
//        chartList.add(new Chart(resources.getString(R.string.polar_chart), PolarChartActivity.class));
//        chartList.add(new Chart(resources.getString(R.string.range_chart), RangeChartActivity.class));
//        chartList.add(new Chart(resources.getString(R.string.vertical_chart), VerticalChartActivity.class));
//        chartList.add(new Chart(resources.getString(R.string.funnel_chart), FunnelChartActivity.class));
//        chartList.add(new Chart(resources.getString(R.string.pert_chart), PertChartActivity.class));
//        chartList.add(new Chart(resources.getString(R.string.combined_chart), CombinedChartActivity.class));
//        chartList.add(new Chart(resources.getString(R.string.bubble_chart), BubbleChartActivity.class));
//        chartList.add(new Chart(resources.getString(R.string.pareto_chart), ParetoChartActivity.class));
//        chartList.add(new Chart(resources.getString(R.string.pyramid_chart), PyramidActivity.class));
//        chartList.add(new Chart(resources.getString(R.string.box_chart), BoxChartActivity.class));
//        chartList.add(new Chart(resources.getString(R.string.mosaic_chart), MosaicChartActivity.class));
//        chartList.add(new Chart(resources.getString(R.string.mekko_chart), MekkoChartActivity.class));
//        chartList.add(new Chart(resources.getString(R.string.bar3d_chart), Bar3DChartActivity.class));
//        chartList.add(new Chart(resources.getString(R.string.column3d_chart), Column3DChartActivity.class));
//        chartList.add(new Chart(resources.getString(R.string.area3d_chart), Area3DChartActivity.class));
//        chartList.add(new Chart(resources.getString(R.string.hilo_chart), HiloChartActivity.class));
//        chartList.add(new Chart(resources.getString(R.string.ohlc_chart), OHLCChartActivity.class));
//        chartList.add(new Chart(resources.getString(R.string.quadrant_chart), QuadrantChartActivity.class));
//        chartList.add(new Chart(resources.getString(R.string.sunburst_chart), SunburstChartActivity.class));
//        chartList.add(new Chart(resources.getString(R.string.bubble_map), BubbleMapActivity.class));
//        chartList.add(new Chart(resources.getString(R.string.choropleth_map), ChoroplethMapActivity.class));
//        chartList.add(new Chart(resources.getString(R.string.point_map), PointMapActivity.class));
//        chartList.add(new Chart(resources.getString(R.string.connector_map), ConnectorMapActivity.class));
//        chartList.add(new Chart(resources.getString(R.string.two_pies_one_column), TwoPiesOneColumnActivity.class));
////        chartList.add(new Chart(resources.getString(R.string.gantt_chart), GanttChartActivity.class));

        return chartList;
    }

}
