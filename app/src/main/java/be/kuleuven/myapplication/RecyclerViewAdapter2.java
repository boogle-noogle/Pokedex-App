package be.kuleuven.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter2 extends RecyclerViewAdapter{
    public RecyclerViewAdapter2(Context context, List<Pokemon> list) {
        super(context, list);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, int position) {
        //holder.profileImage.setImageResource(list.get(holder.getAdapterPosition()).getImage());
        holder.profileImage.setImageBitmap(list.get(holder.getAdapterPosition()).getBitmap());
        holder.name.setText(list.get(holder.getAdapterPosition()).getName());
        holder.number.setText(list.get(holder.getAdapterPosition()).getNumber());
        holder.fav.setBackgroundResource(R.drawable.ic_favorite);

        holder.fav.setOnClickListener(view -> {
            list.remove(holder.getAdapterPosition());
            this.notifyItemRemoved(holder.getAdapterPosition());
        });
        holder.compare.setOnClickListener(view -> {
            Pokemon current = list.get(holder.getAdapterPosition());

            if(compList.size()==1 && !compList.contains(current)){
                compList.add(current);
                Pokemon first = compList.get(0);
                Pokemon second = compList.get(1);
                Intent intent = new Intent(context, CompareView.class);
                intent.putExtra("pokemon1", first);
                intent.putExtra("pokemon2", second);
                compList.clear();
                context.startActivity(intent);
            }else{
                if(compList.contains(current)){
                    compList.remove(current);
                    holder.compare.setBackgroundResource(R.drawable.ic_compare);

                }else{
                    compList.add(current);
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
