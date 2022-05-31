package be.kuleuven.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.beardedhen.androidbootstrap.BootstrapProgressBar;
import com.beardedhen.androidbootstrap.api.attributes.BootstrapBrand;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewActivity extends AppCompatActivity {

    CircleImageView imageView;
    TextView Name, Number;
    TextView weight0, height0;
    TextView text0,text2,text1,text3,text4,text5;
    byte[] byteArray;
    float[] stats;
    BootstrapProgressBar bar0, bar1, bar2, bar3, bar4, bar5;
    RequestQueue requestQueue;

    HorizontalBarChart chart1;
    private SeekBar seekBarX, seekBarY;
    private TextView tvX, tvY;
    String name, number;
    int stat0, stat1, stat2, stat3, stat4, stat5, height, weight;
    int color;
    ConstraintLayout constraintLayout;
    String colorString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        number = getIntent().getStringExtra("number");
        getColor();

        imageView = findViewById(R.id.image1);
        constraintLayout=findViewById(R.id.constraint);
        Name = findViewById(R.id.id1);
        bar0 = findViewById(R.id.bar0);
        bar1 = findViewById(R.id.bar1);
        bar2 = findViewById(R.id.bar2);
        bar3 = findViewById(R.id.bar3);
        bar4 = findViewById(R.id.bar4);
        bar5 = findViewById(R.id.bar5);
        text0=findViewById(R.id.text0);
        text1=findViewById(R.id.text1);
        text2=findViewById(R.id.text20);
        text3=findViewById(R.id.text3);
        text4=findViewById(R.id.text4);
        text5=findViewById(R.id.text5);
        Number = findViewById(R.id.number1);
        weight0 = findViewById(R.id.weight1);
        height0 = findViewById(R.id.height1);


        int image = getIntent().getIntExtra("image", 1);

        Bitmap bitmap = (Bitmap) getIntent().getParcelableExtra("BitmapImage");
        byteArray = getIntent().getByteArrayExtra("newImage");

        name = getIntent().getStringExtra("name");
        stat0 = getIntent().getIntExtra("stat0", 1);
        stat1 = getIntent().getIntExtra("stat1", 1);
        stat2 = getIntent().getIntExtra("stat2", 1);
        stat3 = getIntent().getIntExtra("stat3", 1);
        stat4 = getIntent().getIntExtra("stat4", 1);
        stat5 = getIntent().getIntExtra("stat5", 1);
        height = getIntent().getIntExtra("height", 1);
        weight = getIntent().getIntExtra("weight", 1);
        String url = getIntent().getStringExtra("url");

        bar0.setProgress(stat0);
        bar1.setProgress(stat1);
        bar2.setProgress(stat2);
        bar3.setProgress(stat3);
        bar4.setProgress(stat4);
        bar5.setProgress(stat5);

        text0.setText(String.valueOf(stat0));
        text1.setText(String.valueOf(stat1));
        text2.setText(String.valueOf(stat2));
        text3.setText(String.valueOf(stat3));
        text4.setText(String.valueOf(stat4));
        text5.setText(String.valueOf(stat5));

        //imageView.setImageBitmap(BitmapFactory.decodeByteArray(byteArray,0,byteArray.length));
        new AsyncGettingBitmapFromUrl().execute(url);
        Name.setText(name);
        Number.setText(number);
        height0.setText(String.valueOf(height));
        weight0.setText(String.valueOf(weight));
    }

    public void getColor() {
        //max value is 899
        requestQueue = Volley.newRequestQueue(this);
        String requestURL2 = "https://pokeapi.co/api/v2/pokemon-species/" + Integer.parseInt(number.substring(1));

        Log.d("Database", "creating request");

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, requestURL2, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    colorString = response.getJSONObject("color").getString("name");


                    switch (colorString) {
                        case "black":
                            color = Color.BLACK;
                            break;
                        case "white":
                            color = Color.WHITE;
                            break;
                        case "red":
                            color = Color.parseColor("#8B0000");
                            break;
                        case "green":
                            color = Color.parseColor("#006400");
                            break;
                        case "yellow":
                            color = Color.parseColor("#999900");
                            break;
                        case "blue":
                            color = Color.parseColor("#00008B");
                            break;
                        case "orange":
                            color = Color.parseColor("#FFA500");
                            break;
                        case "purple":
                            color = Color.parseColor("#6A0DAD");
                            break;
                        case "brown":
                            color = Color.parseColor("#8B4513");
                            break;
                        case "gray":
                            color = Color.parseColor("#696969");
                            break;
                        case "pink":
                            color = Color.parseColor("#FF69B4");
                            break;
                    }
                    constraintLayout.setBackgroundColor(color);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private class AsyncGettingBitmapFromUrl extends AsyncTask<String, Void, Bitmap> {
        int id;

        @Override
        protected Bitmap doInBackground(String... params) {

            System.out.println("doInBackground");
            Bitmap bitmap = null;
            bitmap = Pokemon.downloadImage(params[0]);
            return bitmap;
        }

        //@Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
            //imageView.setImageBitmap(BitmapFactory.decodeByteArray(byteArray,0,byteArray.length));


        }

    }
}