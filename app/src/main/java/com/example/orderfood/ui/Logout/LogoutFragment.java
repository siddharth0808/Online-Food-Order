package com.example.orderfood.ui.Logout;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;


import com.example.orderfood.MainActivity;

import com.example.orderfood.R;
import com.example.orderfood.ui.Menu.MenuViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class LogoutFragment extends Fragment {

    private LogoutViewModel logoutViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        logoutViewModel =
                ViewModelProviders.of(this).get(LogoutViewModel.class);

        View root = inflater.inflate(R.layout.fragment_menu, container, false);
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(getActivity(), "Logout", Toast.LENGTH_SHORT).show();
        Intent i= new Intent(getActivity(), MainActivity.class);
        startActivity(i);

        return null;
    }


}