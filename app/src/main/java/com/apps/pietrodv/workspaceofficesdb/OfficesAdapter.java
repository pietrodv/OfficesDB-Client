package com.apps.pietrodv.workspaceofficesdb;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;

public class OfficesAdapter extends RecyclerView.Adapter<OfficesAdapter.OfficeViewHolder> {

    Context context;
    private List<Office> officesList;

    //Set Context and Object for the Custom Adapter
    public OfficesAdapter(Context context, List<Office> officesList) {

        this.context = context;
        this.officesList = officesList;
    }

    //Inflate the office layout
    @NonNull
    @Override
    public OfficeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate the item Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.office_layout, parent, false);
        // set the view's size, margins, padding and layout parameters
        OfficeViewHolder oVh = new OfficeViewHolder(view);
        return oVh;
    }

    //According to the position of each office in the List<> set their details to the dedicated views
    @Override
    public void onBindViewHolder(@NonNull final OfficeViewHolder holder, final int position) {

        // set the data in items
        holder.titleTextView.setText((officesList.get(position).getTitle()));
        holder.priceTextView.setText((officesList.get(position).getPrice()));
        Picasso.get().load(officesList.get(position).getPhoto()).resize(180,120).into(holder.photoImageView);

        // implement setOnClickListener event on item view.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Start a new Activity carrying the single item data
                Intent goToEditOffice = new Intent(context, EditOfficeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("OFFICE", officesList.get(position));
                goToEditOffice.putExtras(bundle);
                context.startActivity(goToEditOffice);

            }
        });

    }

    //Repeat as many times as the size of the List<Office>
    @Override
    public int getItemCount() {
        return officesList.size();
    }

    //PUBLIC CLASS which set the ViewHolder for the single office row
    public class OfficeViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        TextView priceTextView;
        ImageView photoImageView;

        //Set the views variables
        public OfficeViewHolder(View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.title);
            priceTextView = itemView.findViewById(R.id.price);
            photoImageView = itemView.findViewById(R.id.photo);

        }

    }

}
