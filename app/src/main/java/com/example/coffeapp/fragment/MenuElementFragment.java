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

    private static MenuElementFragment menuElementFragment;

    private MenuElement menuElement;
    private List<Cart> cartList;

    private CartViewModel cartViewModel;

    private TextView menuElementNameLabel;
    private TextView menuElementDescriptionText;
    private TextView menuElementPriceText;
    private Button addToCartButton;
    private ConstraintLayout constraintLayout;

    public static MenuElementFragment newInstance(MenuElement menuElement) {
        return new MenuElementFragment(menuElement);
    }

    public MenuElementFragment() {}

    public MenuElementFragment(MenuElement menuElement) {
        this.menuElement = menuElement;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        cartList = new ArrayList<>();

        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        cartViewModel.getAllCartItems().observe(getViewLifecycleOwner(), carts -> {
            cartList.addAll(carts);
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_element, container, false);

        ActionBar supportActionBar = ((BaseActivity) getActivity()).getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);

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
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertToRoom();
            }
        });

        return view;
    }

    private void insertToRoom() {
        Cart cart = new Cart();
        cart.setMenuElementName(menuElement.getName());
        cart.setMenuElementPrice(menuElement.getPrice().doubleValue());

        final int[] quantity = {1};
        final int[] id = new int[1];

        if (!cartList.isEmpty()) {
            for (int i = 0; i < cartList.size(); i++) {
                if (cart.getMenuElementName().equals(cartList.get(i).getMenuElementName())) {
                    quantity[0] = cartList.get(i).getAmount();
                    quantity[0]++;
                    id[0] = cartList.get(i).getId();
                }
            }
        }

        if (quantity[0] == 1) {
            cart.setAmount(quantity[0]);
            cart.setTotalMenuElementPrice(quantity[0] * cart.getMenuElementPrice());
            cartViewModel.insertCartItem(cart);
        } else {
            cartViewModel.updateCartItemQuantity(id[0], quantity[0]);
            cartViewModel.updateCartItemPrice(id[0], quantity[0] * cart.getMenuElementPrice());
        }

        makeSnackBar("Добавлено в корзину");
    }

    private void makeSnackBar(String message) {
        CartFragment cartFragment = CartFragment.getInstance();

        Snackbar
                .make(constraintLayout, message, BaseTransientBottomBar.LENGTH_SHORT)
                .setAction("Коризна", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FragmentTransaction fragmentTransaction = getActivity()
                                .getSupportFragmentManager()
                                .beginTransaction();

                        fragmentTransaction.replace(R.id.fragment_container, cartFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                })
//                .setBackgroundTint(0XFF999999)
                .setTextColor(getResources().getColor(R.color.white))
                .setActionTextColor(getResources().getColor(R.color.green_default))
                .setBackgroundTint(getResources().getColor(R.color.grey_default))
                .show();
    }
}
