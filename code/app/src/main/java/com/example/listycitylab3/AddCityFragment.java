package com.example.listycitylab3;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {

    interface AddCityDialogListener {
        void addCity(City city);
        // Second hint - new instance method
        void editCity(int currentCity, City newCity);
    }

    private AddCityDialogListener listener;

    // so lambdas dont freak out
    private City newCity = null;
    private int newPosition = -1;

    // Example of newInstance:
//    static AddCityFragment newInstance(City city) {
//        Bundle args = new Bundle();
//        args.putSerializable("city", city);
//
//        AddCityFragment fragment = new AddCityFragment();
//        fragment.setArguments(args);
//        return fragment;
//    }
    // "Usually should not have custom constructors in your fragments?
    public AddCityFragment() {}

    // For editing a city, similar to the hint example thing above
    public static AddCityFragment newInstance(int position, City city) {
        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putSerializable("city", city);

        AddCityFragment fragment = new AddCityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement AddCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Last part of hint 2, Later on in your onCreateDialog method, you can
        // access the Bundle using getArguments() and retrieve the City object
        // there.
        View view =
                LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        // new
        Bundle args = getArguments();

        if (args != null) {
            newCity = (City) args.getSerializable("city");
            newPosition = args.getInt("position", -1);
        }

        if (newCity != null) {
            editCityName.setText(newCity.getName());
            editCityName.setSelection(editCityName.length());
            editProvinceName.setText(newCity.getProvince());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Add/edit city")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", (dialog, which) -> {
                    String cityName = editCityName.getText().toString();
                    String provinceName = editProvinceName.getText().toString();

                    if (newCity != null && newPosition >= 0) {
                        listener.editCity(newPosition, new City(cityName, provinceName));
                    } else {
                        listener.addCity(new City(cityName, provinceName));
                    }
                })
                .create();
    }
}
