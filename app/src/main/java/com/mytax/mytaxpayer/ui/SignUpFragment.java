package com.mytax.mytaxpayer.ui;

import android.os.Bundle;
import android.util.Log;
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


public class SignUpFragment extends Fragment
{
          /*Log*/
     private String Tag="SignUpFragment";
        /*Widgets Decalaraations*/
        private EditText signUpEmail;
        private EditText signUpPassword;
        private EditText signUpConfrimPassword;
        private Button signUpButton;
        private View view;
        private FirebaseMethods firebaseMethods;


    @Nullable
    @Override
              /*Creates the sign up view*/
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        Log.d(Tag,"Starting Sign up fragment");
        view=inflater.inflate(R.layout.fragment_sign_up,container,false);
        initWigets();
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validatesData();
            }
        });

        return view;
    }

         /*Method used to initialize the widgets*/
    private void initWigets()
    {

        signUpEmail=view.findViewById(R.id.ed_sign_up_Email);
        signUpPassword=view.findViewById(R.id.ed_sign_up_Password);
        signUpConfrimPassword=view.findViewById(R.id.ed_sign_up_ConfrimPassword);
        signUpButton=view.findViewById(R.id.ed_sign_up_button);
        firebaseMethods=new FirebaseMethods(getContext());



    }

          /*Method that's gets and validates data provided by the user*/
    private void validatesData()
    {
        String email=signUpEmail.getText().toString().trim();
        String password=signUpPassword.getText().toString().trim();
        String confirmPassword=signUpConfrimPassword.getText().toString();

        if(isEmpty(email))
        {
            signUpEmail.setError(getActivity().getString(R.string.email_error));
            signUpEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            signUpEmail.setError(getActivity().getString(R.string.error_invalid_mail));
            signUpEmail.requestFocus();
            return;

        }



        if(isEmpty(password))
        {
            signUpPassword.setError(getActivity().getString(R.string.password_error));
            signUpPassword.requestFocus();
            return;
        }
        if(isLengthMin("name",password,"PhoneNumber"))
        {
            signUpPassword.setError(getActivity().getString(R.string.password_length_error));
            signUpPassword.requestFocus();
            return;
        }

        if(!confirmPassword.matches(password))
        {
            signUpConfrimPassword.setError(getActivity().getString(R.string.confrim_password_error));
            signUpConfrimPassword.requestFocus();
            return;
        }


        registerUser(email,password);



    }

          /*Method that register user with new account*/

    private void registerUser( String email, String password)
    {
        firebaseMethods.createAccount(email,password);







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
