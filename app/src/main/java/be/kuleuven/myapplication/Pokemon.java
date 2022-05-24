package be.kuleuven.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class Pokemon {

    private String name;
    private String id;
    private String type1, type2;
    public String xp;
    private int height;
    private int weight;
    private int stat0, stat1, stat2, stat3, stat4, stat5;
    private String ability;
    private String generation;
    private String strain;
    private Drawable drawable;
    List<String> sprites = new ArrayList<>();
    Bitmap bitmap = null;
    private int image;
    private String url;
    private boolean fav;

    public boolean isFav() {
        return fav;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
    }

    private ByteArrayOutputStream stream = new ByteArrayOutputStream();

    public byte[] getStream() {
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        return stream.toByteArray();
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getUrl() {
        return url;
    }

    public Drawable getDrawable() {
        return drawable;
    }


    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Pokemon(String name, String id, int height, int weight, int stat0, int stat1, int stat2, int stat3, int stat4, int stat5,
                   Bitmap bitmap) {
        this.name = name;
        this.id = id;
        this.height = height;
        this.weight = weight;
        this.stat0 = stat0;
        this.stat1 = stat1;
        this.stat2 = stat2;
        this.stat3 = stat3;
        this.stat4 = stat4;
        this.stat5 = stat5;
        this.bitmap = bitmap;
    }
//        this.drawable = drawable;
//        image =  new DownloadImage(image).execute(url);

    public Pokemon(String name, String id, int height, int weight, int stat0, int stat1, int stat2, int stat3, int stat4, int stat5) {
        this.name = name;
        this.id = id;
        this.height = height;
        this.weight = weight;
        this.stat0 = stat0;
        this.stat1 = stat1;
        this.stat2 = stat2;
        this.stat3 = stat3;
        this.stat4 = stat4;
        this.stat5 = stat5;
//        this.drawable = drawable;
//        image =  new DownloadImage(image).execute(url);
    }

    public Pokemon(String name, String id, int height, int weight, int stat0, int stat1, int stat2, int stat3, int stat4, int stat5,
                   String url, Drawable drawable) {
        this.name = name;
        this.id = id;
        this.height = height;
        this.weight = weight;
        this.stat0 = stat0;
        this.stat1 = stat1;
        this.stat2 = stat2;
        this.stat3 = stat3;
        this.stat4 = stat4;
        this.stat5 = stat5;
        this.url = url;
        this.drawable = drawable;
//        image =  new DownloadImage(image).execute(url);
    }

    public Pokemon(String name, String id, int image) {
        this.name = name;
        this.id = id;
        this.image = image;
    }

    public Pokemon(String name, String id, Bitmap bitmap) {
        this.name = name;
        this.id = id;
        this.bitmap = bitmap;
    }

    public Pokemon(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public static Bitmap downloadImage(String url) {
        Bitmap bitmap = null;
        InputStream stream = null;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inSampleSize = 1;

        try {
            stream = getHttpConnection(url);
            bitmap = BitmapFactory.decodeStream(stream, null, bmOptions);
            stream.close();
        } catch (IOException e1) {
            e1.printStackTrace();
            System.out.println("downloadImage" + e1.toString());
        }
        return bitmap;
    }

    public static InputStream getHttpConnection(String urlString) throws IOException {

        InputStream stream = null;
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();

        try {
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            httpConnection.setRequestMethod("GET");
            httpConnection.connect();

            if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                stream = httpConnection.getInputStream();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("downloadImage" + ex.toString());
        }
        return stream;
    }


    public int getImage() {
        return image;
    }


    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setXp(String xp) {
        this.xp = xp;
    }

    public String getXp() {
        return xp;
    }

    public String getNumber() {
        return id;
    }

    public String getType1() {
        return type1;
    }

    public String getType2() {
        return type2;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public int getStat0() {
        return stat0;
    }

    public int getStat1() {
        return stat1;
    }

    public int getStat2() {
        return stat2;
    }

    public int getStat3() {
        return stat3;
    }

    public int getStat4() {
        return stat4;
    }

    public int getStat5() {
        return stat5;
    }


    public String getAbility() {
        return ability;
    }

    public String getGeneration() {
        return generation;
    }

    public String getStrain() {
        return strain;
    }

    public List<String> getSprites() {
        return sprites;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.id = number;
    }

    public void setType2(String type2) {
        this.type2 = type2;
    }

    public void setType1(String type1) {
        this.type1 = type1;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setStat0(int stat0) {
        this.stat0 = stat0;
    }

    public void setStat1(int stat1) {
        this.stat1 = stat1;
    }

    public void setStat2(int stat2) {
        this.stat2 = stat2;
    }

    public void setStat3(int stat3) {
        this.stat3 = stat3;
    }

    public void setStat4(int stat4) {
        this.stat4 = stat4;
    }

    public void setStat5(int stat5) {
        this.stat5 = stat5;
    }

    public void setAbility(String ability) {
        this.ability = ability;
    }

    public void setGeneration(String generation) {
        this.generation = generation;
    }

    public void setStrain(String strain) {
        this.strain = strain;
    }

    public void setSprites(List<String> sprites) {
        this.sprites = sprites;
    }

    public Object getAverageColor() {
        return null;
    }
}

class DownloadImage extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    public DownloadImage(ImageView bmImage) {
        this.bmImage = (ImageView) bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.d("Error", e.getStackTrace().toString());

        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}