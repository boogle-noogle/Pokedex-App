package be.kuleuven.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;

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
import java.util.LinkedList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    List<Pokemon> testList = new ArrayList<>();
    List<Pokemon> list = new ArrayList<>(30);
    RequestQueue requestQueue;
    RecyclerView recyclerView,recyclerView2;
    RecyclerViewAdapter adapter;
    Bitmap bitmap = null;
    List<String> urls = new LinkedList<>();
    List<Bitmap> bitmaps = new ArrayList<>();
    List<Pokemon> favPokemon = new ArrayList<>();
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView2 = findViewById(R.id.recyclerView2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));

        String name[] = getResources().getStringArray(R.array.name);
        String number[] = getResources().getStringArray(R.array.number);
        int image[] = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e, R.drawable.f, R.drawable.g, R.drawable.h, R.drawable.i, R.drawable.j};


        for (int i = 0; i < name.length; i++) {

            Pokemon pokemon = new Pokemon(name[i],
                    number[i],
                    BitmapFactory.decodeResource(this.getResources(),
                            image[i]));
            testList.add(pokemon);
        }
//        adapter = new RecyclerViewAdapter(this,testList);
        sendRequest(1);
        //new AsyncGettingBitmapFromUrl().onPostExecute(bitmaps);

        //list.addAll(testList);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s.toString());
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


    public void sendRequest(int i) {
        if (i < 31){
            requestQueue = Volley.newRequestQueue(this);
            String requestURL2 = "https://pokeapi.co/api/v2/pokemon/" + i;

            Log.d("Database", "creating request");
            // Pokemon pokemon = new Pokemon();


            JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, requestURL2, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray stats = response.getJSONArray("stats");
                        JSONArray types = response.getJSONArray("types");
                        //JSONObject result = results.getJSONObject(i);
                        list.add(new Pokemon(
                                        response.getString("name").substring(0, 1).toUpperCase() + response.getString("name").substring(1),
                                        ("#"+response.getString("id")),
                                        response.getInt("height"),
                                        response.getInt("weight"),
                                        stats.getJSONObject(0).getInt("base_stat"),
                                        stats.getJSONObject(1).getInt("base_stat"),
                                        stats.getJSONObject(2).getInt("base_stat"),
                                        stats.getJSONObject(3).getInt("base_stat"),
                                        stats.getJSONObject(4).getInt("base_stat"),
                                        stats.getJSONObject(5).getInt("base_stat"))
//                                        response.getJSONObject("sprites").getJSONObject("other").getJSONObject("official-artwork").getString("front_default")

//                                      ,drawableFromUrl((response.getJSONObject("sprites").getString("back_default")))
//                                        (drawableFromUrl(response.getJSONObject("sprites").getString("back_default")))
                                //Integer.parseInt(drawableFromUrl(response.getJSONObject("sprites").getString("back_default").getTag().toString())
                        );
                        //urls[i]=response.getJSONObject("sprites").getJSONObject("other").getJSONObject("official-artwork").getString("front_default");
                        //urls.add(response.getJSONObject("sprites").getJSONObject("other").getJSONObject("official-artwork").getString("front_default"));
                        urls.add(response.getJSONObject("sprites").getString("front_default"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                        list.addAll(testList);
                    }
                    //new AsyncGettingBitmapFromUrl().doInBackground(urls.get(i-1));
                    sendRequest(i+1);
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
            Log.d("Database", "request sent");

            //}
        }
        else{
            adapter = new RecyclerViewAdapter(this, list);
            new AsyncGettingBitmapFromUrl().execute(urls.toArray(new String[0]));
            recyclerView.setAdapter(adapter);
        }
    }
    public void setAdapt(View view){
        List<Pokemon> favList = adapter.getFavList();
        RecyclerViewAdapter adapter2 = new RecyclerViewAdapter(this, favList);
        recyclerView2.setAdapter(adapter2);
    }

    private class AsyncGettingBitmapFromUrl extends AsyncTask<String, Void, Void> {

        int id;

        @Override
        protected Void doInBackground(String... params) {

            System.out.println("doInBackground");

            //id = Integer.parseInt(params[0].substring(params[0].get(0).length()-1));

            Bitmap bitmap = null;

            //bitmap = Pokemon.downloadImage(params[24]);
            //bitmap = Pokemon.downloadImage("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/19.png");
            for(int i =0;i<params.length;i++){
                System.out.println(params[i]);
            }


            bitmaps.add(bitmap);

            for(int i = 0;i< params.length;i++) {
                list.get(i).setBitmap(Pokemon.downloadImage(params[i]));
                System.out.println("bitmap" + bitmap);
            }
            return null;
        }

        //@Override
        protected void onPostExecute(Bitmap bitmap) {

            //list.get(5).setBitmap(bitmap);
            //adapter.notifyItemInserted(5);
            for(int i = 0;i< list.size();i++){
                list.get(i).setBitmap(bitmaps.get(i));
                System.out.println("bitmap" + bitmap);

            }
            //sendRequest();
        }

    }}