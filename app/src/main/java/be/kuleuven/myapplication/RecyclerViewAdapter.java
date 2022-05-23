package be.kuleuven.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> implements Filterable {

    Context context;
    List<Pokemon> list;
    List<Pokemon> listFull;

    public RecyclerViewAdapter(Context context, List<Pokemon> list) {
        this.context = context;
        this.list = list;
        listFull=new ArrayList<>(list);
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view_item,parent,false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder,  int position) {
        //holder.profileImage.setImageResource(list.get(holder.getAdapterPosition()).getImage());
        holder.profileImage.setImageBitmap(list.get(holder.getAdapterPosition()).getBitmap());
        holder.name.setText(list.get(holder.getAdapterPosition()).getName());
        holder.number.setText(list.get(holder.getAdapterPosition()).getNumber());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ViewActivity.class);
                intent.putExtra("BitmapImage", list.get(holder.getAdapterPosition()).getBitmap());
                intent.putExtra("newImage",list.get(holder.getAdapterPosition()).getStream());
                //intent.putExtra("image",list.get(holder.getAdapterPosition()).getImage());
                intent.putExtra("name",list.get(holder.getAdapterPosition()).getName());
                intent.putExtra("number",list.get(holder.getAdapterPosition()).getNumber());
                intent.putExtra("stat0",list.get(holder.getAdapterPosition()).getStat0());
                intent.putExtra("stat1",list.get(holder.getAdapterPosition()).getStat1());
                intent.putExtra("stat2",list.get(holder.getAdapterPosition()).getStat2());
                intent.putExtra("stat3",list.get(holder.getAdapterPosition()).getStat3());
                intent.putExtra("stat4",list.get(holder.getAdapterPosition()).getStat4());
                intent.putExtra("stat5",list.get(holder.getAdapterPosition()).getStat5());
                intent.putExtra("weight",list.get(holder.getAdapterPosition()).getWeight());
                intent.putExtra("height",list.get(holder.getAdapterPosition()).getHeight());

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        return FilterUser;
    }
    private Filter FilterUser = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String searchText = charSequence.toString().toLowerCase();
            List<Pokemon> tempList= new ArrayList<>();
            if(searchText.length() == 0){

                tempList.addAll(listFull);
            }else{
                for (Pokemon pokemon : listFull){
                    if(pokemon.getName().toLowerCase().contains(searchText) || pokemon.getNumber().contains(searchText)){
                        tempList.add(pokemon);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values=tempList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            list.clear();
            list.addAll((Collection<? extends Pokemon>) filterResults.values);
            notifyDataSetChanged();

        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView profileImage;
        TextView name, number;
        TextView stat0,stat1,stat2,stat3,stat4,stat5;
        TextView height, weight;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage=itemView.findViewById(R.id.profileImage);
            name=itemView.findViewById(R.id.textView);
            number=itemView.findViewById(R.id.textView2);
            stat0=itemView.findViewById(R.id.stat0);
            stat1=itemView.findViewById(R.id.stat1);
            stat2=itemView.findViewById(R.id.stat2);
            stat3=itemView.findViewById(R.id.stat3);
            stat4=itemView.findViewById(R.id.stat4);
            stat5=itemView.findViewById(R.id.stat5);
            weight=itemView.findViewById(R.id.weight);
            height=itemView.findViewById(R.id.height);
        }
    }
}
