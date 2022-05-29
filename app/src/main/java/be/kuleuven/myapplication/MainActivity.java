package be.kuleuven.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;


public class MainActivity extends AppCompatActivity {

    List<Pokemon> testList = new ArrayList<>();
    List<Pokemon> list = new ArrayList<>(30);
    RequestQueue requestQueue;
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    List<String> urls = new LinkedList<>();
    List<Bitmap> bitmaps = new ArrayList<>();
    List<Pokemon> favPokemon = new ArrayList<>();
    List<Pokemon> dbList = new ArrayList<>();
    Comparator<Pokemon> comparator;
    int pokemonCounter = 0;
    String likedRaw;
    String localID;
    int numberOfPokemon = 31;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        localID = id(this);
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

                System.out.println(favPokemon.size());

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

            case R.id.jumpToStart:
                recyclerView.setAdapter(adapter);
                break;
            case R.id.save:
                String liked = "";
                favPokemon = adapter.getFavList();
                //favPokemon.addAll(tempList);
                for (Pokemon pokemon : dbList) {
                    if (!favPokemon.contains(pokemon) && pokemon.isFav()) {
                        favPokemon.add(pokemon);
                    }
                }
                for (Pokemon pokemon : favPokemon) {
                    liked += Integer.parseInt(pokemon.getNumber().substring(1)) - 1 + "_";
                }
                System.out.println(liked);
                requestQueue = Volley.newRequestQueue(this);

                String requestURL = "https://studev.groept.be/api/a21pt106/updateData/" + liked + "/" + localID;
                StringRequest submitRequest = new StringRequest(Request.Method.GET, requestURL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("Database", "response received");
                                System.out.println(requestURL);
                                Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.Success), Toast.LENGTH_LONG);
                                toast.show();
                            }
                        },

                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.Error), Toast.LENGTH_LONG);
                                toast.show();


                            }
                        }

                );
                requestQueue.add(submitRequest);
        }
        return super.onOptionsItemSelected(item);
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
        } else {
            getLiked();
            adapter = new RecyclerViewAdapter(this, list);
            new AsyncGettingBitmapFromUrl().execute(urls.toArray(new String[0]));
            recyclerView.setAdapter(adapter);
        }
    }

    public void setFavPokemon(List<Pokemon> list) {
        favPokemon = list;
    }

    public void toFav(View view) {
        favPokemon = adapter.getFavList();
        //favPokemon.addAll(tempList);
        for (Pokemon pokemon : dbList) {
            if (!favPokemon.contains(pokemon) && pokemon.isFav()) {
                favPokemon.add(pokemon);
            }
        }

        Intent intent = new Intent(this, FavActivity.class);

        intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) favPokemon);
        this.startActivity(intent);
    }

    public synchronized static String id(Context context) {
        String uniqueID = null;
        String PREF_UNIQUE_ID = "PREF_UNIQUE_ID";
        SharedPreferences sharedPrefs = context.getSharedPreferences(PREF_UNIQUE_ID, Context.MODE_PRIVATE);
        uniqueID = sharedPrefs.getString(PREF_UNIQUE_ID, null);
        if (uniqueID == null) {
            uniqueID = UUID.randomUUID().toString();
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString(PREF_UNIQUE_ID, uniqueID);
            editor.commit();
        }
        return uniqueID;
    }

    public void getLiked() {
        requestQueue = Volley.newRequestQueue(this);
        String url = "https://studev.groept.be/api/a21pt106/service1";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        if (localID.equals(jsonObject.getString("deviceID"))) {
                            likedRaw = jsonObject.getString("list");
                            String[] splitArray = likedRaw.split("_");
                            int[] likedNew = new int[splitArray.length];

                            for (int var = 0; var < likedNew.length; var++) {
                                likedNew[var] = Integer.parseInt(splitArray[var]);
                            }
                            for (int k : likedNew) {

                                for (int j = 0; j < list.size(); j++) {
                                    if (list.get(j).getID() == k && !favPokemon.contains(list.get(j))) {
                                        dbList.add(list.get(j));
                                        list.get(j).setFav(true);
                                    }
                                }


                                for (Pokemon pokemon : list) {
                                    if (pokemon.getID() == k) {
                                        favPokemon.add(pokemon);
                                    }
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Log.i("Volley Error: ", error);
            }
        });

        requestQueue.add(jsonArrayRequest);

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
                System.out.println("bitmap" + i);
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