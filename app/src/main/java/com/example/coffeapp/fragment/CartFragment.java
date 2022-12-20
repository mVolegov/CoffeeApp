package com.example.coffeapp.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeapp.R;
import com.example.coffeapp.adapter.CartElementsAdapter;
import com.example.coffeapp.database.Cart;
import com.example.coffeapp.network.CoffeeAPI;
import com.example.coffeapp.network.DTO.OrderDTO;
import com.example.coffeapp.network.RetrofitInstance;
import com.example.coffeapp.viewmodel.CartViewModel;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartFragment extends Fragment implements CartElementsAdapter.CartClickedListener {

    private static final String TAG = "CartFragment";

    private CartViewModel cartViewModel;

    private CartElementsAdapter cartElementsAdapter;
    private RecyclerView cartElementsRecyclerView;
    private TextView totalCartPriceTextView;
    private Button makeOrderButton;

    public static CartFragment newInstance() {
        return new CartFragment();
    }

    public CartFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        setViews(view);
        setCartElementsRecyclerView(view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        cartViewModel
                .getAllCartItems()
                .observe(getViewLifecycleOwner(), carts -> {
                    cartElementsAdapter.setMenuElementsCartList(carts);

                    if (carts.isEmpty()) {
                        totalCartPriceTextView.setText("Пусто");
                    } else {
                        double price = carts.stream().mapToDouble(Cart::getTotalMenuElementPrice).sum();

                        totalCartPriceTextView.setText(
                            "Всего: "
                            + new BigDecimal(price).setScale(2, BigDecimal.ROUND_HALF_UP)
                            + " руб"
                        );
                    }
                });
    }

    private void setViews(View view) {
        totalCartPriceTextView = view.findViewById(R.id.orderTotalTextView);

        makeOrderButton = view.findViewById(R.id.loginButton);
        makeOrderButton.setOnClickListener(view1 -> makeOrder());
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
        cartViewModel.updateCartItemPrice(
                cart.getId(), quantity * cart.getMenuElementPrice()
        );
        cartElementsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onMinusClicked(Cart cart) {
        int quantity = cart.getAmount() - 1;

        if (quantity != 0) {
            cartViewModel.updateCartItemQuantity(cart.getId(), quantity);
            cartViewModel.updateCartItemPrice(
                    cart.getId(), quantity * cart.getMenuElementPrice()
            );
            cartElementsAdapter.notifyDataSetChanged();
        } else {
            cartViewModel.deleteCartItem(cart);
        }
    }

    private void makeOrder() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        String accessToken = prefs.getString("accessTokenJWT", "");
        String username = prefs.getString("username", "");

        if (!accessToken.equals("") && !username.equals("")) {
            trySendOrder(accessToken, username);
            return;
        }

        String refreshToken = prefs.getString("refreshTokenJWT", "");
        if (!refreshToken.equals("") && !username.equals("")) {
            CoffeeAPI api = RetrofitInstance.getRetrofitClient().create(CoffeeAPI.class);
            Call<Map<String, String>> call = api.refreshJwtToken(refreshToken);
            call.enqueue(new Callback<Map<String, String>>() {
                @Override
                public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                    if (response.body() != null) {
                        SharedPreferences.Editor editor = prefs.edit();
                        if (response.body().containsKey("accessTokenJWT")) {
                            editor.putString("accessTokenJWT", response.body().get("accessTokenJWT"));
                        }
                        if (response.body().containsKey("refreshTokenJWT")) {
                            editor.putString("refreshTokenJWT", response.body().get("refreshTokenJWT"));
                        }
                        editor.apply();
                    }
                }

                @Override
                public void onFailure(Call<Map<String, String>> call, Throwable t) {
                    Snackbar
                            .make(getView().findViewById(R.id.cartConstraintLayout), "Ошибка при обновлении токена", BaseTransientBottomBar.LENGTH_SHORT)
                            .setTextColor(getResources().getColor(R.color.white))
                            .setBackgroundTint(getResources().getColor(R.color.grey_default))
                            .show();
                }
            });

            trySendOrder(accessToken, username);
            return;
        }

        GoToAccount();
    }

    private void trySendOrder(String accessToken, String username) {
        CoffeeAPI api = RetrofitInstance.getAuthorizedRetrofitClient(accessToken).create(CoffeeAPI.class);
        // TODO: OrderDTO orderDTO = new OrderDTO(username, cartViewModel.getAllCartItems().getValue() - ???)
        //  вроде long - int словарь принимаешь, но тут в коде же нет такого
        Map<Long, Integer> mockCart = new HashMap<>();
        mockCart.put(1L,1);
        OrderDTO orderDTO = new OrderDTO(username, mockCart);
        Call<Integer> call = api.sendOrder(orderDTO);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.code() == 403){
                    GoToAccount();
                } else {
                    Snackbar
                            .make(getView().findViewById(R.id.cartConstraintLayout), "Заказ отправлен", BaseTransientBottomBar.LENGTH_SHORT)
                            .setTextColor(getResources().getColor(R.color.white))
                            .setBackgroundTint(getResources().getColor(R.color.grey_default))
                            .show();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Snackbar
                        .make(getView().findViewById(R.id.cartConstraintLayout), "Не получилось создать заказ", BaseTransientBottomBar.LENGTH_SHORT)
                        .setTextColor(getResources().getColor(R.color.white))
                        .setBackgroundTint(getResources().getColor(R.color.grey_default))
                        .show();
            }
        });
    }

    private void GoToAccount(){
        AccountLoginFragment accountLoginFragment = AccountLoginFragment.newInstance();

        FragmentTransaction fragmentTransaction = getActivity()
                .getSupportFragmentManager()
                .beginTransaction();

        fragmentTransaction.replace(R.id.fragment_container, accountLoginFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
