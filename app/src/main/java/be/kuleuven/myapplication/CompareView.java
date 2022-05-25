package be.kuleuven.myapplication;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;

import org.w3c.dom.Text;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import de.hdodenhof.circleimageview.CircleImageView;

public class CompareView extends AppCompatActivity {

    CircleImageView image1,image2;
    TextView id1,id2,height1,height2,weight1,weight2,name;
    List<RadarEntry> chartList1 = new ArrayList<>();
    List<RadarEntry> chartList2 = new ArrayList<>();
    public static float  MIN = 1f;
    public static final int NB_QUALITIES = 6;
    private RadarChart chart;
    Pokemon pokemon1,pokemon2;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compare_view);

        pokemon1 = getIntent().getParcelableExtra("pokemon1");
        pokemon2 = getIntent().getParcelableExtra("pokemon2");

        List<Integer> total = new ArrayList<>();
        total.add(pokemon1.getStat0());
        total.add(pokemon1.getStat1());
        total.add(pokemon1.getStat2());
        total.add(pokemon1.getStat3());
        total.add(pokemon1.getStat4());
        total.add(pokemon2.getStat5());
        total.add(pokemon2.getStat0());
        total.add(pokemon2.getStat1());
        total.add(pokemon2.getStat2());
        total.add(pokemon2.getStat3());
        total.add(pokemon2.getStat4());
        total.add(pokemon2.getStat5());


        Integer max = total
                .stream()
                .mapToInt(v -> v)
                .max()
                .orElseThrow(NoSuchElementException::new);

        Integer min = total
                .stream()
                .mapToInt(v -> v)
                .min()
                .orElseThrow(NoSuchElementException::new);


        image1=findViewById(R.id.image1);
        image2=findViewById(R.id.image2);
        id1=findViewById(R.id.number1);
        name=findViewById(R.id.id1);
        id2=findViewById(R.id.id2);
        height1=findViewById(R.id.height1);
        height2=findViewById(R.id.height2);
        weight1=findViewById(R.id.weight1);
        weight2=findViewById(R.id.weight2);
        chart=findViewById(R.id.chart);

        //radar configuration
        chart.getDescription().setEnabled(false);
        chart.setWebLineWidth(1f);
        chart.setWebColor(Color.WHITE);
        chart.setWebLineWidth(1f);
        chart.setWebColorInner(Color.WHITE);
        chart.setWebAlpha(100);
        chart.setBackgroundColor(Color.TRANSPARENT);

        chart.animateXY(1400,1400, Easing.EaseInOutQuad, Easing.EaseInOutQuad);

        XAxis xAxis = chart.getXAxis();
        xAxis.setTextSize(9f);
        xAxis.setYOffset(0);
        xAxis.setXOffset(0);
//        xAxis.setValueFormatter(new ValueFormatter() {
//            private final String[] qualities = new String[]{"HP","Attack", "Defense", "Special Attack", "Special Defense", "Speed"};
//
//        });
        xAxis.setValueFormatter(new IndexAxisValueFormatter() {

            private final String[] qualities = new String[]{"HP","Attack", "Defense", "Special Attack", "Special Defense", "Speed"};

            public String getFormattedValue(float value) {
                return qualities[(int) value % qualities.length];
            }
        });

        xAxis.setTextColor(Color.WHITE);

        YAxis yAxis = chart.getYAxis();
        yAxis.setLabelCount(NB_QUALITIES,false);
        yAxis.setTextSize(9f);
        yAxis.setAxisMinimum(1f);
        yAxis.setAxisMaximum(max);
        yAxis.setDrawLabels(false);

        Legend l = chart.getLegend();
        l.setTextSize(15f);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);
        l.setTextColor(Color.WHITE);




        image1.setImageBitmap(pokemon1.getBitmap());
        image2.setImageBitmap(pokemon2.getBitmap());
        id1.setText(pokemon1.getNumber());
        height1.setText(String.valueOf(pokemon1.getHeight()));
        weight1.setText(String.valueOf(pokemon1.getWeight()));
        id2.setText(pokemon2.getNumber());
        height2.setText(String.valueOf(pokemon2.getHeight()));
        weight2.setText(String.valueOf(pokemon2.getWeight()));
        name.setText(pokemon1.getName()+" vs " + pokemon2.getName());
        setData();


    }

    private void setData(){

        chartList1.add( new RadarEntry(pokemon1.getStat0()));
        chartList1.add( new RadarEntry(pokemon1.getStat1()));
        chartList1.add( new RadarEntry(pokemon1.getStat2()));
        chartList1.add( new RadarEntry(pokemon1.getStat3()));
        chartList1.add( new RadarEntry(pokemon1.getStat4()));
        chartList1.add( new RadarEntry(pokemon2.getStat5()));

        chartList2.add( new RadarEntry(pokemon2.getStat0()));
        chartList2.add( new RadarEntry(pokemon2.getStat1()));
        chartList2.add( new RadarEntry(pokemon2.getStat2()));
        chartList2.add( new RadarEntry(pokemon2.getStat3()));
        chartList2.add( new RadarEntry(pokemon2.getStat4()));
        chartList2.add( new RadarEntry(pokemon2.getStat5()));

        int max1 = pokemon1.getStat0() +pokemon1.getStat1() +pokemon1.getStat2() +pokemon1.getStat3() +pokemon1.getStat4() +pokemon1.getStat5();
        int max2 = pokemon2.getStat0() +pokemon2.getStat1() +pokemon2.getStat2() +pokemon2.getStat3() +pokemon2.getStat4() +pokemon2.getStat5();


        RadarDataSet set1 = new RadarDataSet(chartList1, null);
        set1.setColor(Color.GREEN);
        set1.setFillColor(Color.GREEN);
        set1.setDrawFilled(true);
        set1.setFillAlpha(180);
        set1.setLineWidth(2f);
        set1.setDrawHighlightIndicators(false);
        set1.setDrawHighlightCircleEnabled(true);

        RadarDataSet set2 = new RadarDataSet(chartList2, null);
        set2.setColor(Color.RED);
        set2.setFillColor(Color.RED);
        set2.setDrawFilled(true);
        set2.setFillAlpha(180);
        set2.setLineWidth(2f);
        set2.setDrawHighlightIndicators(false);
        set2.setDrawHighlightCircleEnabled(true);

        ArrayList<IRadarDataSet> sets = new ArrayList<>();
        sets.add(set1);
        sets.add(set2);

        RadarData data = new RadarData(sets);
        data.setValueTextSize(8f);
        data.setDrawValues(false);
        data.setValueTextColor(Color.WHITE);

        chart.setData(data);
        chart.invalidate();
    }
}
