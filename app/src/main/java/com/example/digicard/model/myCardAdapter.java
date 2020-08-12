package com.example.digicard.model;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.digicard.Activities.Preview_Activity;
import com.example.digicard.R;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

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
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        card_attributes c=myCardsList.get(position);
        holder.cardName.setText(c.getUsername());
        holder.name.setText(myCardsList.get(position).getName());
    }

    @Override
    public int getItemCount() {
         return myCardsList.size();
    }

    @Override
    public Filter getFilter() {
        return myFilter;
    }
    private Filter myFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<card_attributes> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(cardsListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (card_attributes item : cardsListFull) {
                    if (item.getUsername().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            myCardsList.clear();
            myCardsList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class myViewHolder extends RecyclerView.ViewHolder{
        TextView cardName;
        TextView name;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            cardName=(TextView) itemView.findViewById(R.id.CardName);
            name=(TextView) itemView.findViewById(R.id.name);

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
