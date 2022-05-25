package be.kuleuven.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
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
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    List<Pokemon> testList = new ArrayList<>();
    List<Pokemon> list = new ArrayList<>(30);
    RequestQueue requestQueue;
    RecyclerView recyclerView, recyclerView2;
    RecyclerViewAdapter adapter;
    Bitmap bitmap = null;
    List<String> urls = new LinkedList<>();
    List<Bitmap> bitmaps = new ArrayList<>();
    List<Pokemon> favPokemon = new ArrayList<>();
    Comparator<Pokemon> comparator;
    int pokemonCounter = 0;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        sendRequest(1);
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sortByNumber:
                comparator = new Comparator<Pokemon>() {
                    @Override
                    public int compare(Pokemon pokemon, Pokemon t1) {
                        if (Integer.parseInt(pokemon.getNumber().substring(1)) > Integer.parseInt(t1.getNumber().substring(1))) {
                            return 1;
                        } else if (Integer.parseInt(pokemon.getNumber().substring(1)) < Integer.parseInt(t1.getNumber().substring(1))) {
                            return -1;
                        }
                        return 0;
                    }
                };
                list.sort(comparator);
                adapter.notifyDataSetChanged();
                break;


            case R.id.sortAlphabetically:
                comparator = new Comparator<Pokemon>() {
                    @Override
                    public int compare(Pokemon pokemon, Pokemon t1) {
                        return pokemon.getName().compareToIgnoreCase(t1.getName());
                    }
                };
                list.sort(comparator);
                adapter.notifyDataSetChanged();
                break;

            case R.id.sortByHP:
                comparator = new Comparator<Pokemon>() {
                    @Override
                    public int compare(Pokemon pokemon, Pokemon t1) {
                        if (pokemon.getStat0() > t1.getStat0()) {
                            return -1;
                        } else if (pokemon.getStat0() < t1.getStat0()) {
                            return 1;
                        }
                        return 0;
                    }
                };
                list.sort(comparator);
                adapter.notifyDataSetChanged();
                break;

            case R.id.sortByAttack:
                comparator = new Comparator<Pokemon>() {
                    @Override
                    public int compare(Pokemon pokemon, Pokemon t1) {
                        if (pokemon.getStat1() > t1.getStat1()) {
                            return -1;
                        } else if (pokemon.getStat1() < t1.getStat1()) {
                            return 1;
                        }
                        return 0;
                    }
                };
                list.sort(comparator);
                adapter.notifyDataSetChanged();
                break;
            case R.id.sortByDefense:
                comparator = new Comparator<Pokemon>() {
                    @Override
                    public int compare(Pokemon pokemon, Pokemon t1) {
                        if (pokemon.getStat2() > t1.getStat2()) {
                            return -1;
                        } else if (pokemon.getStat2() < t1.getStat2()) {
                            return 1;
                        }
                        return 0;
                    }
                };
                list.sort(comparator);
                adapter.notifyDataSetChanged();
                break;
            case R.id.sortByTotal:
                comparator = new Comparator<Pokemon>() {
                    @Override
                    public int compare(Pokemon pokemon, Pokemon t1) {
                        if (pokemon.getStatTotal() > t1.getStatTotal()) {
                            return -1;
                        } else if (pokemon.getStatTotal() < t1.getStatTotal()) {
                            return 1;
                        }
                        return 0;
                    }
                };
                list.sort(comparator);
                adapter.notifyDataSetChanged();
                break;
            case R.id.sortBySpeed:
                comparator = new Comparator<Pokemon>() {
                    @Override
                    public int compare(Pokemon pokemon, Pokemon t1) {
                        if (pokemon.getStat5() > t1.getStat5()) {
                            return -1;
                        } else if (pokemon.getStat5() < t1.getStat5()) {
                            return 1;
                        }
                        return 0;
                    }
                };
                list.sort(comparator);
                adapter.notifyDataSetChanged();
                break;
            case R.id.invert:
                Collections.reverse(list);
                adapter.notifyDataSetChanged();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void sendRequest(int i) {
        //max value is 899
        if (i < 21) {
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
                        list.addAll(testList);
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
            Log.d("Database", "request sent "+ pokemonCounter);

            //}
        } else {
            adapter = new RecyclerViewAdapter(this, list);
            new AsyncGettingBitmapFromUrl().execute(urls.toArray(new String[0]));
            recyclerView.setAdapter(adapter);
        }
    }

    public void toFav(View view) {
        favPokemon = adapter.getFavList();

        Intent intent = new Intent(this, FavActivity.class);

        intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) favPokemon);
        this.startActivity(intent);
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
            for (int i = 0; i < params.length; i++) {
                System.out.println(params[i]);
            }


            bitmaps.add(bitmap);

            for (int i = 0; i < params.length; i++) {
                list.get(i).setBitmap(Pokemon.downloadImage(params[i]));
                System.out.println("bitmap" + bitmap);
            }
            return null;
        }

        //@Override
        protected void onPostExecute(Bitmap bitmap) {

            //list.get(5).setBitmap(bitmap);
            //adapter.notifyItemInserted(5);
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setBitmap(bitmaps.get(i));
                System.out.println("bitmap" + bitmap);

            }
            //sendRequest();
        }

    }
}