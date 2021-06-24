package com.example.helpbuy.ui.addrequest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.helpbuy.R;
import com.example.helpbuy.databinding.FragmentAddrequestBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddRequestFragment extends Fragment {

    private AddRequestViewModel addRequestViewModel;
    private FragmentAddrequestBinding binding;
    private Button btnSubmit;
    private EditText textLocation, textItem, textQuantity,
            textEstPrice, textDeliveryDate, textDeliveryTime, textDeliveryFees, textRemarks;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        addRequestViewModel =
//                new ViewModelProvider(this).get(AddRequestViewModel.class);

        View view = inflater.inflate(R.layout.fragment_addrequest, container, false);


        btnSubmit = (Button) view.findViewById(R.id.btn_submitrequest);
        textLocation = (EditText) view.findViewById(R.id.requestlocation);
        textItem = (EditText) view.findViewById(R.id.requestitemdescription);
        textQuantity = (EditText) view.findViewById(R.id.reqquantity);
        textEstPrice = (EditText) view.findViewById(R.id.reqestprice);
        textDeliveryDate = (EditText) view.findViewById(R.id.deliverydate);
        textDeliveryTime = (EditText) view.findViewById(R.id.deliverytime);
        textDeliveryFees = (EditText) view.findViewById(R.id.deliveryfees);
        textRemarks = (EditText) view.findViewById(R.id.reqremarks);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                String location = textLocation.getText().toString();
                String item = textItem.getText().toString();
                String quantity = textQuantity.getText().toString();
                String estPrice = textEstPrice.getText().toString();
                String deliveryDate = textDeliveryDate.getText().toString();
                String deliveryTime = textDeliveryTime.getText().toString();
                String deliveryFees = textDeliveryFees.getText().toString();
                String remarks = textRemarks.getText().toString();
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                Map<String, String> newRequest = new HashMap<>();
                newRequest.put("Location", location);
                newRequest.put("Item", item);
                newRequest.put("Quantity", quantity);
                newRequest.put("Estimated Price", estPrice);
                newRequest.put("Delivery Date", deliveryDate);
                newRequest.put("Delivery Time", deliveryTime);
                newRequest.put("Delivery Fees", deliveryFees);
                newRequest.put("Remarks", remarks);
                newRequest.put("UID", uid);
                newRequest.put("aUID","");

                db.collection("Job_requests")
                        .add(newRequest);

                RequestDialogBox dialogBox = new RequestDialogBox();
                dialogBox.show(getFragmentManager(),"dialog");

                ClearAction();

            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void ClearAction() {
        textLocation.getText().clear();
        textItem.getText().clear();
        textQuantity.getText().clear();
        textEstPrice.getText().clear();
        textDeliveryDate.getText().clear();
        textDeliveryFees.getText().clear();
        textDeliveryTime.getText().clear();
        textRemarks.getText().clear();
    }
}





