package com.example.coffeapp.fragment;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.coffeapp.BaseActivity;
import com.example.coffeapp.R;
import com.example.coffeapp.model.MenuElement;
import com.example.coffeapp.viewmodel.CartViewModel;

public class MenuElementFragment extends Fragment {

    private static final String TAG = "MenuElementFragment";

    private static MenuElementFragment menuElementFragment;

    private MenuElement menuElement;

    private CartViewModel cartViewModel;

    private TextView menuElementNameLabel;
    private TextView menuElementDescriptionText;
    private TextView menuElementPriceText;
    private Button addToCartButton;

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

        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
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

        menuElementNameLabel.setText(menuElement.getName());
        menuElementDescriptionText.setText(menuElement.getDescription());
        menuElementPriceText.setText(
                String.format("%.2f .руб", menuElement.getPrice().doubleValue())
        );


        addToCartButton = view.findViewById(R.id.add_to_cart_button);
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: " + menuElement.getName());
//                cartViewModel.setNewMenuElementToCart(menuElement);
                boolean isAdded = cartViewModel.addMenuElementToCart(menuElement);
                Log.d(TAG, "onClick: " + menuElement.getName() + isAdded);
            }
        });

        return view;
    }
}
