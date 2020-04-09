package com.mytax.mytaxpayer.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mytax.mytaxpayer.R;
import com.mytax.mytaxpayer.utils.FirebaseMethods;

public class ActivityCompleteReg extends AppCompatActivity
{
    private EditText edName,edPhone,edId,edBusinessType,edBusinessName,edBusinessLocation,edKRA;
    private Button butSubmit;
    private FirebaseMethods firebaseMethods;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_reg);
        intwidgets();
        butSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });

    }

    private void validateData() {

        String name = edName.getText().toString();
        String phone = edPhone.getText().toString();
        String id = edId.getText().toString();
        String type = edBusinessType.getText().toString();
        String businessName = edBusinessName.getText().toString();
        String location = edBusinessLocation.getText().toString();
        String pin = edKRA.getText().toString();

        if (name.isEmpty())
          {
              edName.setError("Name cannot be empty");
              edName.requestFocus();
              return;
          }
        if(phone.isEmpty()||phone.length()<10)
        {

            edPhone.setError("Please Enter your phone number");
            edPhone.requestFocus();
            return;
        }
        if(id.isEmpty()||id.length()<8)
        {
            edId.setError("Enter your national id");
            edId.requestFocus();
            return;
        }
        if(type.isEmpty())
        {
            edBusinessType.setError("Enter your business type");
            edBusinessType.requestFocus();
            return;
        }

        if(businessName.isEmpty())
        {
            edBusinessName.setError("Enter your business name");
            edBusinessName.requestFocus();
            return;
        }
        if(location.isEmpty())
        {
           edBusinessLocation.setError("Enter your business location");
           edBusinessLocation.requestFocus();
           return;
        }
        if(pin.isEmpty())
        {
            edKRA.setError("Enter your pin ");
            edKRA.requestFocus();
            return;

        }

        firebaseMethods.addUser(name,phone,id,type,businessName,location,pin);

    }



    private void intwidgets()
    {
        edName=(EditText)findViewById(R.id.ed_com_name);
        edPhone=(EditText)findViewById(R.id.ed_com_phone);
        edId=(EditText)findViewById(R.id.ed_com_national_id);
        edBusinessType=(EditText)findViewById(R.id.ed_com_type_business);
        edBusinessName=(EditText)findViewById(R.id.ed_com_Name_business);
        edBusinessLocation=(EditText)findViewById(R.id.ed_com_location_business);
        edKRA=(EditText)findViewById(R.id.ed_com_kra_pin);
        butSubmit=(Button)findViewById(R.id.but_submit);
        firebaseMethods=new FirebaseMethods(getApplicationContext());
    }
}
