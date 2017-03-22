package com.example.gzde.mobileproject;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class RegisterFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_register, container, false);
        Button register = (Button) v.findViewById(R.id.btnrgstr);
        final EditText name = (EditText) v.findViewById(R.id.txtNameSurname);
        final EditText email = (EditText) v.findViewById(R.id.txtEmail);
        final EditText age = (EditText) v.findViewById(R.id.txtAge);
        final EditText password = (EditText) v.findViewById(R.id.txtPassword);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (email.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Email cannot be empty!", Toast.LENGTH_LONG).show();
                } else {
                    SharedPreferences preference = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preference.edit();
                    String strEmail = email.getText().toString();
                    strEmail = strEmail.toLowerCase();
                    editor.putString("name", name.getText().toString());
                    editor.putString("email", strEmail);
                    editor.putString("age", age.getText().toString());
                    editor.putString("password", password.getText().toString());
                    editor.commit();


                    Toast.makeText(getActivity(),"suksesfully kayıt oldun",Toast.LENGTH_LONG).show();


                }

            }

        });

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


}
