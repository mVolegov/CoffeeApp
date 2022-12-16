package com.example.coffeapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import com.example.coffeapp.R;
import com.example.coffeapp.adapter.MenuCategoryAdapter;
import com.example.coffeapp.adapter.MenuElementAdapter;
import com.example.coffeapp.model.MenuCategory;
import com.example.coffeapp.model.MenuElement;
import com.example.coffeapp.viewmodel.MenuCategoriesViewModel;
import com.example.coffeapp.viewmodel.MenuElementsViewModel;

import java.util.ArrayList;
import java.util.List;

public class MenuFragment
        extends Fragment
        implements MenuElementAdapter.ItemClickListener, MenuCategoryAdapter.ItemClickListener {

    private static final String TAG = "MenuFragment";

    private MenuElementsViewModel menuElementsViewModel;
    private MenuCategoriesViewModel menuCategoriesViewModel;

    private MenuCategoryAdapter menuCategoryAdapter;
    private MenuElementAdapter menuElementAdapter;
    private RecyclerView menuElementRecycler;
    private ProgressBar progressBar;
    private TextView allMenuElementsLabel;

    private List<MenuCategory> menuCategories = new ArrayList<>();
    private List<MenuElement> menuElementsFull = new ArrayList<>();
    private List<MenuElement> menuElementsToShow = new ArrayList<>();

    public static MenuFragment newInstance() {
        return new MenuFragment();
    }

    public MenuFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        setActionBar();
        setProgressBar(view);
        setShowAllMenuElementsButton(view);
        setMenuCategoryRecyclerView(menuCategories, view);
        setMenuElementRecyclerView(menuElementsFull, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        menuCategoriesViewModel =
                new ViewModelProvider(this).get(MenuCategoriesViewModel.class);

        menuCategoriesViewModel
                .getMenuCategories()
                .observe(
                        getViewLifecycleOwner(),
                        menuCategories -> menuCategoryAdapter.setMenuCategories(menuCategories)
                );

        menuElementsViewModel =
                new ViewModelProvider(this).get(MenuElementsViewModel.class);

        menuElementsViewModel
                .getMenuElements()
                .observe(getViewLifecycleOwner(), menuElements -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Данные получены", Toast.LENGTH_SHORT).show();
                    menuElementsFull.clear();
                    menuElementsFull.addAll(menuElements);
                    menuElementAdapter.setMenuElements(menuElements);
                });
    }

    @Override
    public void onMenuElementClick(MenuElement menuElement) {
        MenuElementFragment menuElementFragment = MenuElementFragment.newInstance(menuElement);

        FragmentTransaction fragmentTransaction = getActivity()
                .getSupportFragmentManager()
                .beginTransaction();

        fragmentTransaction.replace(R.id.fragment_container, menuElementFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onMenuCategoryClick(MenuCategory menuCategory) {
        menuElementsToShow.clear();

        for (MenuElement eachMenuElement : menuElementsFull) {
            List<MenuCategory> eachMenuElementCategories = eachMenuElement.getCategories();

            for (MenuCategory eachMenuElementCategory : eachMenuElementCategories) {
                if (eachMenuElementCategory.equals(menuCategory)) {
                    menuElementsToShow.add(eachMenuElement);
                }
            }
        }

        menuElementAdapter.setMenuElements(menuElementsToShow);
    }

    private void setShowAllMenuElementsButton(View view) {
        allMenuElementsLabel = view.findViewById(R.id.all_menu_elements_label);
        allMenuElementsLabel.setOnClickListener(view1 -> {
            menuElementAdapter.setMenuElements(menuElementsFull);
        });
    }

    private void setMenuCategoryRecyclerView(List<MenuCategory> menuCategories, View view) {
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);

        menuCategoryAdapter = new MenuCategoryAdapter(menuCategories, this);

        RecyclerView menuCategoryRecycler = view.findViewById(R.id.menu_category_recycler);
        menuCategoryRecycler.setLayoutManager(layoutManager);
        menuCategoryRecycler.setAdapter(menuCategoryAdapter);
    }

    private void setMenuElementRecyclerView(List<MenuElement> menuElements, View view) {
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

        menuElementAdapter = new MenuElementAdapter(menuElements, this);

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
}
