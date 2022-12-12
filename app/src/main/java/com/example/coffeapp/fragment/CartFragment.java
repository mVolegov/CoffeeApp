package com.example.coffeapp.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeapp.R;
import com.example.coffeapp.adapter.CartElementsAdapter;
import com.example.coffeapp.model.CartItem;
import com.example.coffeapp.viewmodel.CartViewModel;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    private static final String TAG = "CartFragment";
    private static CartFragment cartFragmentInstance;

    private CartViewModel cartViewModel;
    private CartElementsAdapter cartElementsAdapter;
    private RecyclerView cartRecyclerView;
    private List<CartItem> cart = new ArrayList<>();

    public static CartFragment getInstance() {
        if (cartFragmentInstance ==  null) {
            cartFragmentInstance = new CartFragment();
        }

        return cartFragmentInstance;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        cartViewModel = new ViewModelProvider(requireActivity()).get(CartViewModel.class);
        cartViewModel.getCart().observe(getViewLifecycleOwner(), new Observer<List<CartItem>>() {
            @Override
            public void onChanged(List<CartItem> cartItems) {
                cart.clear();
                cart.addAll(cartItems);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
//        cartElementsAdapter = new CartElementsAdapter(cart);
        cartRecyclerView = view.findViewById(R.id.cart_recycler_view);
        cartRecyclerView.setLayoutManager(layoutManager);
        cartRecyclerView.setAdapter(cartElementsAdapter);

        return view;
    }
}
