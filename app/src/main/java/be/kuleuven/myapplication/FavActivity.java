package be.kuleuven.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class FavActivity extends AppCompatActivity {

    List<Pokemon> list = new ArrayList<>();
    RecyclerViewAdapter2 adapter2;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);
        recyclerView=findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        list = getIntent().getParcelableArrayListExtra("list");
        adapter2 = new RecyclerViewAdapter2(this,list);
        recyclerView.setAdapter(adapter2);
    }

}