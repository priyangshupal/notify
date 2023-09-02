package com.example.geofence.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geofence.Models.PersonalizedOffer;
import com.example.geofence.R;

import java.util.List;

public class PersonalizedOfferAdapter extends RecyclerView.Adapter<PersonalizedOfferAdapter.ViewHolder> {

    private Context mContext;
    private List<PersonalizedOffer> mOffers;

    public PersonalizedOfferAdapter(Context mContext, List<PersonalizedOffer> mOffers) {
        this.mContext = mContext;
        this.mOffers = mOffers;
    }

    @NonNull
    @Override
    public PersonalizedOfferAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.personalized_offer, parent, false);
        return new PersonalizedOfferAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonalizedOfferAdapter.ViewHolder holder, int position) {
        final PersonalizedOffer personalizedOffer = mOffers.get(position);

        holder.category.setText(personalizedOffer.getCategory());
        holder.discount_percent.setText("Upto " + personalizedOffer.getDiscount_percent() + "off");
    }

    @Override
    public int getItemCount() {
        return mOffers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView categoryImage;
        TextView category, discount_percent;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryImage = itemView.findViewById(R.id.categoryImage);
            category = itemView.findViewById(R.id.category);
            discount_percent = itemView.findViewById(R.id.discount_percent);
        }
    }
}
