package com.example.coffeapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeapp.R;
import com.example.coffeapp.adapter.CartElementsAdapter;
import com.example.coffeapp.database.Cart;
import com.example.coffeapp.viewmodel.CartViewModel;

import java.math.BigDecimal;

public class CartFragment extends Fragment implements CartElementsAdapter.CartClickedListener {

    private static final String TAG = "CartFragment";
    private static CartFragment instance;

    private CartViewModel cartViewModel;

    private CartElementsAdapter cartElementsAdapter;
    private RecyclerView cartElementsRecyclerView;
    private TextView totalCartPriceTextView;

    public static CartFragment newInstance() {
        return new CartFragment();
    }

    public CartFragment() {}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        cartViewModel.getAllCartItems().observe(getViewLifecycleOwner(), carts -> {
            double price = 0;

            cartElementsAdapter.setMenuElementsCartList(carts);

            if (carts.isEmpty()) {
                totalCartPriceTextView.setText("Пусто");
            } else {
                for (int i = 0; i < carts.size(); i++) {
                    price += carts.get(i).getTotalMenuElementPrice();
                }

                totalCartPriceTextView.setText(
                        "Всего: "
                                + new BigDecimal(price).setScale(2, BigDecimal.ROUND_HALF_UP)
                                + " руб"
                );
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        totalCartPriceTextView = view.findViewById(R.id.orderTotalTextView);
        setCartElementsRecyclerView(view);

        return view;
    }

    private void setCartElementsRecyclerView(View view) {
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
        cartElementsAdapter = new CartElementsAdapter(this);
        cartElementsRecyclerView = view.findViewById(R.id.cart_recycler_view);
        cartElementsRecyclerView.setLayoutManager(layoutManager);
        cartElementsRecyclerView.setAdapter(cartElementsAdapter);
        cartElementsRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void onDeleteClick(Cart cart) {
        cartViewModel.deleteCartItem(cart);
    }

    @Override
    public void onPlusClicked(Cart cart) {
        int quantity = cart.getAmount() + 1;
        cartViewModel.updateCartItemQuantity(cart.getId(), quantity);
        cartViewModel.updateCartItemPrice(cart.getId(),
                quantity * cart.getMenuElementPrice());
        cartElementsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onMinusClicked(Cart cart) {
        int quantity = cart.getAmount() - 1;

        if (quantity != 0) {
            cartViewModel.updateCartItemQuantity(cart.getId(), quantity);
            cartViewModel.updateCartItemPrice(cart.getId(),
                    quantity * cart.getMenuElementPrice());
            cartElementsAdapter.notifyDataSetChanged();
        } else {
            cartViewModel.deleteCartItem(cart);
        }
    }
}
