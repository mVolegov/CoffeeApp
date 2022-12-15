package com.example.coffeapp.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeapp.BaseActivity;
import com.example.coffeapp.MainApp;
import com.example.coffeapp.R;
import com.example.coffeapp.adapter.MenuCategoryAdapter;
import com.example.coffeapp.adapter.MenuElementAdapter;
import com.example.coffeapp.model.MenuCategory;
import com.example.coffeapp.model.MenuElement;
import com.example.coffeapp.viewmodel.MenuElementsViewModel;

import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment implements MenuElementAdapter.ItemClickListener {

    private static final String TAG = "MenuFragment";

    private static MenuFragment menuFragmentInstance;

    private static MenuCategoryAdapter menuCategoryAdapter;
    private static MenuElementAdapter menuElementAdapter;
    private RecyclerView menuElementRecycler;

    private static List<MenuCategory> menuCategories = new ArrayList<>();
    private static List<MenuElement> menuElementsFull = new ArrayList<>();
    private static List<MenuElement> menuElementsToShow;

    private MenuElementsViewModel menuElementsViewModel;

    private ProgressBar progressBar;

    public static MenuFragment newInstance() {
        if (menuFragmentInstance == null) menuFragmentInstance = new MenuFragment();

        return menuFragmentInstance;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        menuElementsViewModel =
                new ViewModelProvider(this).get(MenuElementsViewModel.class);

        menuElementsViewModel.getMenuElements().observe(getViewLifecycleOwner(), menuElements -> {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getContext(), "Данные получены", Toast.LENGTH_SHORT).show();
            menuElementAdapter.setMenuElements(menuElements);
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        setActionBar();
        setProgressBar(view);

        menuCategories = ((MainApp) getActivity().getApplication()).getMenuCategories();
        setMenuCategoryRecyclerView(menuCategories, view);

        setMenuElementRecyclerView(menuElementsFull, view);

        return view;
    }

    @Override
    public void onItemClick(MenuElement menuElement) {
        MenuElementFragment menuElementFragment = MenuElementFragment.newInstance(menuElement);

        FragmentTransaction fragmentTransaction = getActivity()
                .getSupportFragmentManager()
                .beginTransaction();

        fragmentTransaction.replace(R.id.fragment_container, menuElementFragment);
//        fragmentTransaction.hide(getActivity().getSupportFragmentManager().findFragmentByTag("selected_fragment"));
//        fragmentTransaction.add(R.id.fragment_container, menuElementFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void setMenuCategoryRecyclerView(List<MenuCategory> menuCategories, View view) {
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);

        menuCategoryAdapter = new MenuCategoryAdapter(getContext(), menuCategories);

        RecyclerView menuCategoryRecycler = view.findViewById(R.id.menu_category_recycler);
        menuCategoryRecycler.setLayoutManager(layoutManager);
        menuCategoryRecycler.setAdapter(menuCategoryAdapter);
    }

    private void setMenuElementRecyclerView(List<MenuElement> menuElements, View view) {
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

        menuElementAdapter = new MenuElementAdapter(getContext(), menuElements, this);

        menuElementRecycler = view.findViewById(R.id.menu_element_recycler);
        menuElementRecycler.setLayoutManager(layoutManager);
        menuElementRecycler.setAdapter(menuElementAdapter);
    }

    private void setProgressBar(View view) {
        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void setActionBar() {
        ActionBar supportActionBar = ((BaseActivity) getActivity()).getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
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
