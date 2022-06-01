package be.kuleuven.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoadingActivity extends AppCompatActivity {
    List<Pokemon> list = new ArrayList<>(30);
    List<String> urls = new ArrayList<>(30);

    RequestQueue requestQueue;
    int numberOfPokemon = 31;
    int pokemonCounter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        sendRequest(1);
    }
    public void sendRequest(int i) {
        //max value is 899
        if (i < numberOfPokemon) {
            requestQueue = Volley.newRequestQueue(this);
            String requestURL2 = "https://pokeapi.co/api/v2/pokemon/" + i;

            Log.d("Database", "creating request");

            JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, requestURL2, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray stats = response.getJSONArray("stats");
                        JSONArray types = response.getJSONArray("types");
                        //JSONObject result = results.getJSONObject(i);
                        list.add(new Pokemon(
                                        response.getString("name").substring(0, 1).toUpperCase() + response.getString("name").substring(1),
                                        ("#" + response.getString("id")),
                                        response.getInt("height"),
                                        response.getInt("weight"),
                                        stats.getJSONObject(0).getInt("base_stat"),
                                        stats.getJSONObject(1).getInt("base_stat"),
                                        stats.getJSONObject(2).getInt("base_stat"),
                                        stats.getJSONObject(3).getInt("base_stat"),
                                        stats.getJSONObject(4).getInt("base_stat"),
                                        stats.getJSONObject(5).getInt("base_stat"),
                                        response.getJSONObject("sprites").getJSONObject("other").getJSONObject("official-artwork").getString("front_default")
                                )
//                                        response.getJSONObject("sprites").getJSONObject("other").getJSONObject("official-artwork").getString("front_default")

//                                      ,drawableFromUrl((response.getJSONObject("sprites").getString("back_default")))
//                                        (drawableFromUrl(response.getJSONObject("sprites").getString("back_default")))
                                //Integer.parseInt(drawableFromUrl(response.getJSONObject("sprites").getString("back_default").getTag().toString())
                        );
                        //urls[i]=response.getJSONObject("sprites").getJSONObject("other").getJSONObject("official-artwork").getString("front_default");
                        //urls.add(response.getJSONObject("sprites").getJSONObject("other").getJSONObject("official-artwork").getString("front_default"));
                        urls.add(response.getJSONObject("sprites").getString("front_default"));
                        pokemonCounter++;
                    } catch (JSONException e) {
                        e.printStackTrace();
                        //list.addAll(testList);
                    }
                    //new AsyncGettingBitmapFromUrl().doInBackground(urls.get(i-1));
                    sendRequest(i + 1);
                }


//                    recyclerView.setAdapter(adapter);

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    //Log.i("Volley Error: ", error);
                }
            });


            requestQueue.add(jsonArrayRequest);
            Log.d("Database", "request sent " + pokemonCounter);

            //}
        }
    }

}