package com.spoorthi.tasklogin;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Spoorthi on 1/12/2018.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder>
{

    private List<ItemDataModel> listItems;
    private Context context;

    public RecyclerAdapter(List<ItemDataModel> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_item_card,null);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position)
    {
        ItemDataModel itemDataModel = listItems.get(position);

        holder.headTV.setText(itemDataModel.getHead());
        holder.descTV.setText(itemDataModel.getLat()+" "+itemDataModel.getLang());
        Picasso.with(context).load(itemDataModel.getImage())
                .into(holder.img);

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }



    public class ItemViewHolder extends RecyclerView.ViewHolder{

        public TextView headTV,descTV;
        ImageView img;

        public ItemViewHolder(View itemView) {
            super(itemView);

            headTV = (TextView)itemView.findViewById(R.id.projectTitle);
            descTV = (TextView)itemView.findViewById(R.id.noOfBackers);
            img = (ImageView)itemView.findViewById(R.id.projectImg);

            descTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context,MapsActivity.class);
                    i.putExtra("lat",""+descTV.getText().toString());
                    context.startActivity(i);
                }
            });

        }
    }

}
