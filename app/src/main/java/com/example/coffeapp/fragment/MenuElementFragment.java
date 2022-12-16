package com.example.coffeapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.coffeapp.BaseActivity;
import com.example.coffeapp.R;
import com.example.coffeapp.database.Cart;
import com.example.coffeapp.model.MenuElement;
import com.example.coffeapp.viewmodel.CartViewModel;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MenuElementFragment extends Fragment {

    private static final String TAG = "MenuElementFragment";

    private CartViewModel cartViewModel;

    private TextView menuElementNameLabel;
    private TextView menuElementDescriptionText;
    private TextView menuElementPriceText;
    private Button addToCartButton;
    private ConstraintLayout constraintLayout;

    private MenuElement menuElement;
    private List<Cart> cartList;

    public static MenuElementFragment newInstance(MenuElement menuElement) {
        return new MenuElementFragment(menuElement);
    }

    public MenuElementFragment() {}

    public MenuElementFragment(MenuElement menuElement) {
        this.menuElement = menuElement;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_element, container, false);

        setActionBar();
        setViews(view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cartList = new ArrayList<>();

        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        cartViewModel
                .getAllCartItems()
                .observe(getViewLifecycleOwner(), carts -> cartList.addAll(carts));
    }

    private void setViews(View view) {
        menuElementNameLabel = view.findViewById(R.id.menu_element_page_name_label);
        menuElementDescriptionText = view.findViewById(R.id.menu_element_page_description_label);
        menuElementPriceText = view.findViewById(R.id.menu_element_page_price_label);
        constraintLayout = view.findViewById(R.id.constraint_layout);

        menuElementNameLabel.setText(menuElement.getName());
        menuElementDescriptionText.setText(menuElement.getDescription());
        menuElementPriceText.setText(
                String.format("%.2f .руб", menuElement.getPrice().doubleValue())
        );

        addToCartButton = view.findViewById(R.id.add_to_cart_button);
        addToCartButton.setOnClickListener(view1 -> insertToRoom());
    }

    private void insertToRoom() {
        Cart cart = new Cart();
        cart.setMenuElementName(menuElement.getName());
        cart.setMenuElementPrice(menuElement.getPrice().doubleValue());

        int amount = 1;
        int id = 0;

        if (!cartList.isEmpty()) {


            for (Cart eachCart : cartList) {
                if (cart.getMenuElementName().equals(eachCart.getMenuElementName())) {
                    amount = eachCart.getAmount();
                    amount++;
                    id = eachCart.getId();
                }
            }
        }

        if (amount == 1) {
            cart.setAmount(amount);
            cart.setTotalMenuElementPrice(amount * cart.getMenuElementPrice());
            cartViewModel.insertCartItem(cart);
        } else {
            cartViewModel.updateCartItemQuantity(id, amount);
            cartViewModel.updateCartItemPrice(id, amount * cart.getMenuElementPrice());
        }

        makeSnackBar("Добавлено в корзину");
    }

    private void makeSnackBar(String message) {
        CartFragment cartFragment = CartFragment.newInstance();

        Snackbar
                .make(constraintLayout, message, BaseTransientBottomBar.LENGTH_SHORT)
                .setAction("Коризна", view -> {
                    FragmentTransaction fragmentTransaction = getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction();

                    fragmentTransaction.replace(R.id.fragment_container, cartFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                })
                .setTextColor(getResources().getColor(R.color.white))
                .setActionTextColor(getResources().getColor(R.color.green_default))
                .setBackgroundTint(getResources().getColor(R.color.grey_default))
                .show();
    }

    private void setActionBar() {
        ActionBar supportActionBar = ((BaseActivity) getActivity()).getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
    }
}
