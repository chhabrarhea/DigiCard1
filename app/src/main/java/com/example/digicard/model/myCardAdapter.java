package com.example.digicard.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.digicard.Activities.Preview_Activity;
import com.example.digicard.R;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class myCardAdapter extends RecyclerView.Adapter<myCardAdapter.myViewHolder> implements Filterable {
    private ArrayList<card_attributes> myCardsList;
    private ArrayList<card_attributes> cardsListFull;
    private Context context;




    public myCardAdapter(Context c, ArrayList<card_attributes> s)
    {
        context=c;
        myCardsList=s;
        cardsListFull=new ArrayList<>(myCardsList);

    }
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.my_card_item,parent,false);
         return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final myViewHolder holder, int position) {
        card_attributes c=myCardsList.get(position);
        holder.cardName.setText(c.getCardname());
        holder.name.setText(myCardsList.get(position).getName());
        if(!myCardsList.get(position).getImage_path().equals(""))
        {
            Glide.with(context).load(myCardsList.get(position).getImage_path()).asBitmap().centerCrop().into(holder.dp);
        }
        else
            holder.dp.setImageResource(R.drawable.ic_baseline_person_24);
    }

    @Override
    public int getItemCount() {
         return myCardsList.size();
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    private Filter campFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<card_attributes> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(cardsListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (card_attributes item : cardsListFull) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            myCardsList.clear();
            myCardsList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };



    public class myViewHolder extends RecyclerView.ViewHolder{
        TextView cardName;
        TextView name;
        CircleImageView dp;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            cardName=(TextView) itemView.findViewById(R.id.CardName);
            name=(TextView) itemView.findViewById(R.id.name);
            dp=(CircleImageView)itemView.findViewById(R.id.dp);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos=getAdapterPosition();
                    if(pos!=RecyclerView.NO_POSITION){
                    card_attributes card=myCardsList.get(pos);
                    Intent intent=new Intent(context, Preview_Activity.class);
                    intent.putExtra("Preview_Card",card);
                    intent.putExtra("position",pos);
                    context.startActivity(intent);
                    }}
            });
        }
    }
}
