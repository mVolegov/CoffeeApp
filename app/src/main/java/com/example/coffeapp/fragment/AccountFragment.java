package com.example.coffeapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.coffeapp.R;

public class AccountFragment extends Fragment {

    private static AccountFragment accountFragmentInstance;

    public static AccountFragment getInstance() {
        if (accountFragmentInstance == null) {
            accountFragmentInstance = new AccountFragment();
        }

        return accountFragmentInstance;
    }

    public AccountFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);
    }
}
