package be.kuleuven.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Parcelable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RecyclerViewAdapter2 extends RecyclerViewAdapter{
    public RecyclerViewAdapter2(Context context, List<Pokemon> list) {
        super(context, list);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, int position) {
        //holder.profileImage.setImageResource(list.get(holder.getAdapterPosition()).getImage());
        if (compList.contains(list.get(holder.getAdapterPosition()))) {
            holder.compare.setBackgroundResource(R.drawable.ic_check);
        } else{
            holder.compare.setBackgroundResource(R.drawable.ic_compare);
        }
        holder.profileImage.setImageBitmap(list.get(holder.getAdapterPosition()).getBitmap());
        holder.name.setText(list.get(holder.getAdapterPosition()).getName());
        holder.number.setText(list.get(holder.getAdapterPosition()).getNumber());
        if (list.get(holder.getAdapterPosition()).isFav()) {
            holder.fav.setBackgroundResource(R.drawable.ic_favorite);
        } else {
            holder.fav.setBackgroundResource(R.drawable.ic_unfavorite);
        }
        holder.fav.setOnClickListener(view -> {
            Pokemon current = list.get(holder.getAdapterPosition());
            if (current.isFav()) {
                current.setFav(false);
                if (favList.contains(current) && current != null) ;
                {
                    list.remove(holder.getAdapterPosition());
                    this.notifyItemRemoved(holder.getAdapterPosition());
                    favList.remove(current);
                    System.out.println(current.getName() + " removed");
                    holder.fav.setBackgroundResource(R.drawable.ic_unfavorite);
                }
            } else {
                if (current != null) {
                    current.setFav(true);
                }
                favList.add(current);
                //System.out.println(current.getName() + " added");
                holder.fav.setBackgroundResource(R.drawable.ic_favorite);
            }

        });
        holder.compare.setOnClickListener(view -> {
            Pokemon current = list.get(holder.getAdapterPosition());

            if (compList.size() == 1 && !compList.contains(current)) {
                holder.compare.setBackgroundResource(R.drawable.ic_compare);
                compList.add(current);
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
                compList.sort(comparator);
                Pokemon first = compList.get(0);
                Pokemon second = compList.get(1);
                Intent intent = new Intent(context, CompareView.class);
                intent.putExtra("pokemon1", first);
                intent.putExtra("pokemon2", second);
                compList.clear();
                context.startActivity(intent);
                this.notifyItemChanged(pos);
            } else {
                if (compList.contains(current)) {
                    compList.remove(current);
                    holder.compare.setBackgroundResource(R.drawable.ic_compare);

                } else {
                    compList.add(current);
                    pos = holder.getAdapterPosition();
                    holder.compare.setBackgroundResource(R.drawable.ic_check);
                }
            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewActivity.class);
                intent.putExtra("BitmapImage", list.get(holder.getAdapterPosition()).getBitmap());
                intent.putExtra("newImage", list.get(holder.getAdapterPosition()).getStream());
                //intent.putExtra("image",list.get(holder.getAdapterPosition()).getImage());
                intent.putExtra("name", list.get(holder.getAdapterPosition()).getName());
                intent.putExtra("number", list.get(holder.getAdapterPosition()).getNumber());
                intent.putExtra("stat0", list.get(holder.getAdapterPosition()).getStat0());
                intent.putExtra("stat1", list.get(holder.getAdapterPosition()).getStat1());
                intent.putExtra("stat2", list.get(holder.getAdapterPosition()).getStat2());
                intent.putExtra("stat3", list.get(holder.getAdapterPosition()).getStat3());
                intent.putExtra("stat4", list.get(holder.getAdapterPosition()).getStat4());
                intent.putExtra("stat5", list.get(holder.getAdapterPosition()).getStat5());
                intent.putExtra("weight", list.get(holder.getAdapterPosition()).getWeight());
                intent.putExtra("height", list.get(holder.getAdapterPosition()).getHeight());
                intent.putExtra("color", (Parcelable) list.get(holder.getAdapterPosition()).getAverageColor());


                context.startActivity(intent);
            }
        });


    }

}
