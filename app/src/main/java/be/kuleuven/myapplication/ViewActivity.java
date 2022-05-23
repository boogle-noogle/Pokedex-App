package be.kuleuven.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewActivity extends AppCompatActivity {

    CircleImageView imageView;
    TextView Name,Number;
    TextView stat00,stat10,stat20,stat30,stat40,stat50;
    TextView weight0,height0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        imageView=findViewById(R.id.otherProfileImage);
        Name=findViewById(R.id.textView3);
        stat00=findViewById(R.id.stat0);
        stat10=findViewById(R.id.stat1);
        stat20=findViewById(R.id.stat2);
        stat30=findViewById(R.id.stat3);
        stat40=findViewById(R.id.stat4);
        stat50=findViewById(R.id.stat5);
        Number = findViewById(R.id.number);
        weight0=findViewById(R.id.weight);
        height0=findViewById(R.id.height);

        int image = getIntent().getIntExtra("image", 1);

        Bitmap bitmap = (Bitmap) getIntent().getParcelableExtra("BitmapImage");
        byte[] byteArray = getIntent().getByteArrayExtra("newImage");

        String name = getIntent().getStringExtra("name");
        String number = getIntent().getStringExtra("number");
        int stat0= getIntent().getIntExtra("stat0",1);
        int stat1= getIntent().getIntExtra("stat1",1);
        int stat2= getIntent().getIntExtra("stat2",1);
        int stat3= getIntent().getIntExtra("stat3",1);
        int stat4= getIntent().getIntExtra("stat4",1);
        int stat5= getIntent().getIntExtra("stat5",1);
        int height= getIntent().getIntExtra("height",1);
        int weight= getIntent().getIntExtra("weight",1);


        imageView.setImageBitmap(BitmapFactory.decodeByteArray(byteArray,0,byteArray.length));
        Name.setText(name);
        Number.setText(number);
        stat00.setText("    " + String.valueOf(stat0)+ "    ");
        stat10.setText("    " + String.valueOf(stat1)+ "    ");
        stat20.setText("    " + String.valueOf(stat2)+ "    ");
        stat30.setText("    " + String.valueOf(stat3)+ "    ");
        stat40.setText("    " + String.valueOf(stat4)+ "    ");
        stat50.setText("    " + String.valueOf(stat5)+ "    ");
        height0.setText("    " + String.valueOf(height)+ "    ");
        weight0.setText("    " + String.valueOf(weight)+ "    ");


    }
}