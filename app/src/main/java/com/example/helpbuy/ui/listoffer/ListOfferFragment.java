package com.example.helpbuy.ui.listoffer;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helpbuy.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;


public class ListOfferFragment extends Fragment {
    private RecyclerView offersList;
    private FirebaseFirestore db;
    private FirestoreRecyclerAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listoffer_list, container, false);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        offersList = view.findViewById(R.id.offers_list);
        Query query = db.collection("Job_offers").whereNotEqualTo("UID",false);
        FirestoreRecyclerOptions<Offers> options = new FirestoreRecyclerOptions.Builder<Offers>()
                .setQuery(query, Offers.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<Offers, OffersViewHolder>(options) {

            @NonNull
            @Override
            public OffersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_listoffer, parent, false);
                return new OffersViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull OffersViewHolder holder, int position, @NonNull Offers model) {
                holder.offerlist_location.setText(model.getLocation());
                holder.offerlist_dateofpurchase.setText(model.getDateOfPurchase());
                holder.offerlist_duration.setText(model.getDuration());
                holder.offerlist_minfeesrequest.setText(model.getMinFeesRequest());
            }
        };

        Context context = view.getContext();
        offersList.setHasFixedSize(true);
        offersList.setLayoutManager(new LinearLayoutManager(context));
        offersList.setAdapter(adapter);
        return view;
    }

    public void onCreate(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private class OffersViewHolder extends RecyclerView.ViewHolder {
        private TextView offerlist_location;
        private TextView offerlist_dateofpurchase;
        private TextView offerlist_duration;
        private TextView offerlist_minfeesrequest;

        public OffersViewHolder(@NonNull View itemView) {
            super(itemView);
            offerlist_location= itemView.findViewById(R.id.offerlist_location);
            offerlist_dateofpurchase = itemView.findViewById(R.id.offerlist_dateofpurchase);
            offerlist_duration = itemView.findViewById(R.id.offerlist_duration);
            offerlist_minfeesrequest = itemView.findViewById(R.id.offerlist_minfeesrequest);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }
}