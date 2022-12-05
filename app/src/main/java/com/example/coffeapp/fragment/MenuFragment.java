package com.example.coffeapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeapp.BaseActivity;
import com.example.coffeapp.MainApp;
import com.example.coffeapp.R;
import com.example.coffeapp.adapter.MenuCategoryAdapter;
import com.example.coffeapp.adapter.MenuElementAdapter;
import com.example.coffeapp.api.ApiMenuCategories;
import com.example.coffeapp.api.ApiMenuElements;
import com.example.coffeapp.api.CoffeeAPI;
import com.example.coffeapp.mocks.MockedDataInitializer;
import com.example.coffeapp.model.MenuCategory;
import com.example.coffeapp.model.MenuElement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MenuFragment extends Fragment {

    private static MenuCategoryAdapter menuCategoryAdapter;
    private static MenuElementAdapter menuElementAdapter;

    private static List<MenuCategory> menuCategories = new ArrayList<>();
    private static List<MenuElement> menuElementsFull;
    private static List<MenuElement> menuElementsToShow;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

//        MockedDataInitializer mockedData = new MockedDataInitializer();

        menuCategories = ((MainApp) getActivity().getApplication()).getMenuCategories();
        setMenuCategoryRecycler(menuCategories, view);

        menuElementsFull = ((MainApp) getActivity().getApplication()).getMenuElements();
        setMenuElementRecycler(menuElementsFull, view);

//        ApiMenuElements apiMenuElements = new ApiMenuElements();

//        menuElementsFull = mockedData.getMockedMenuElements();

//        menuElementsFull = apiMenuElements.getAllMenuElements();
//        System.out.println("Элементы меню: " + menuElementsFull.toString());
//        menuElementsToShow = new ArrayList<MenuElement>() {{
//            addAll(menuElementsFull);
//        }};
//        setMenuElementRecycler(menuElementsToShow, view);

        return view;
    }

    private void setMenuCategoryRecycler(List<MenuCategory> menuCategories, View view) {
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);

        menuCategoryAdapter = new MenuCategoryAdapter(getContext(), menuCategories);

        RecyclerView menuCategoryRecycler = view.findViewById(R.id.menu_category_recycler);
        menuCategoryRecycler.setLayoutManager(layoutManager);
        menuCategoryRecycler.setAdapter(menuCategoryAdapter);
    }

    private void setMenuElementRecycler(List<MenuElement> menuElements, View view) {
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

        menuElementAdapter = new MenuElementAdapter(getContext(), menuElements);

        RecyclerView menuElementRecycler = view.findViewById(R.id.menu_element_recycler);
        menuElementRecycler.setLayoutManager(layoutManager);
        menuElementRecycler.setAdapter(menuElementAdapter);
    }

    //    public static void showMenuElementsByCategory(int category) {
    //        menuElementsToShow.clear();
    //
    //        List<MenuElement> filterMenuElements = menuElementsFull
    //                .stream()
    //                .filter(el -> el.getCategory().getId() == category)
    //                .collect(Collectors.toList());
    //
    //        menuElementsToShow.clear();
    //        menuElementsToShow.addAll(filterMenuElements);
    //
    //        menuElementAdapter.notifyDataSetChanged();
    //    }
}
