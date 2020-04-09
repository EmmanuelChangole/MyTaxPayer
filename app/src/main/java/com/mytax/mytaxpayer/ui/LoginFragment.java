package com.mytax.mytaxpayer.ui;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.mytax.mytaxpayer.R;
import com.mytax.mytaxpayer.utils.FirebaseMethods;


public class LoginFragment extends Fragment {
    /*Login log*/
    private String tag = "LoginFragment";
    /*Widget  declaration*/
    private View view;
    private EditText loginEmail;
    private EditText loginPassword;
    private Button loginButton;
    private FirebaseMethods  firebaseMethods;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);
        initWidgets();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validatesData();
            }
        });
        return view;

    }

    /*Method used to initialize the widgets*/
    private void initWidgets()
    {
        loginEmail=view.findViewById(R.id.ed_login_Email);
        loginPassword=view.findViewById(R.id.ed_login_Password);
        loginButton=view.findViewById(R.id.but_login);

        firebaseMethods=new FirebaseMethods(getContext());

    }
    /*Method that's gets and validates data provided by the user*/
    private void validatesData()
    {
        String email=loginEmail.getText().toString().trim();
        String password=loginPassword.getText().toString().trim();

        if(isEmpty(email))
        {
            loginEmail.setError(getActivity().getString(R.string.email_error));
            loginEmail.requestFocus();
            loginEmail.setBackgroundResource(R.drawable.edit_text_error_background);
            return;
        }
        loginEmail.setBackgroundResource(R.drawable.edit_text_background);
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            loginEmail.setError(getActivity().getString(R.string.error_invalid_mail));
            loginEmail.requestFocus();
            loginEmail.setBackgroundResource(R.drawable.edit_text_error_background);
            return;

        }
        loginEmail.setBackgroundResource(R.drawable.edit_text_background);

        if(isEmpty(password))
        {
            loginPassword.setError(getActivity().getString(R.string.password_error));
            loginPassword.requestFocus();
            loginPassword.setBackgroundResource(R.drawable.edit_text_error_background);
            return;
        }
        loginPassword.setBackgroundResource(R.drawable.edit_text_background);
        if(isLengthMin("name",password,"PhoneNumber"))
        {
            loginPassword.setError(getActivity().getString(R.string.password_length_error));
            loginPassword.requestFocus();
            loginPassword.setBackgroundResource(R.drawable.edit_text_error_background);
            return;
        }
        loginPassword.setBackgroundResource(R.drawable.edit_text_background);
        loginUser(email,password);





    }

    private void loginUser(String email, String password)
    {
        firebaseMethods.login(email,password);


    }


    /*Method that validates the length*/
    private boolean isLengthMin(String string, String pass, String phone)
    {
        if(string.length()<4)
            return  true;
        if(pass.length()<6)
            return true;
        if(phone.length()<10)
            return true;
        return false;
    }
    /*Method that check if data provided is empty*/
    private boolean isEmpty(String string)
    {
        if(string.isEmpty())
            return true;
        return false;

    }





}
