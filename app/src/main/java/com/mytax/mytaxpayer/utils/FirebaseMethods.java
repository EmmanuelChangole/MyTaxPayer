package com.mytax.mytaxpayer.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mytax.mytaxpayer.R;
import com.mytax.mytaxpayer.model.Users;
import com.mytax.mytaxpayer.ui.ActivityCompleteReg;
import com.mytax.mytaxpayer.ui.HomeActivity;
import com.mytax.mytaxpayer.ui.MainActivity;

public class FirebaseMethods
{
  private Context context;
  private FirebaseAuth mAuth;
  private FirebaseAuth.AuthStateListener mAuthListener;
  private FirebaseDatabase myDataBase;
  private DatabaseReference myRefDataBase;
  private ProgressDialog mProgress;
  private String tag="FireBaseMethod";
  private static Users user;


  public FirebaseMethods(Context context) {
    this.context = context;
    mAuth=FirebaseAuth.getInstance();
    myDataBase=FirebaseDatabase.getInstance();
    myRefDataBase=myDataBase.getReference();
    mProgress=new ProgressDialog(context);
    mProgress.setTitle("Loading..");
    mProgress.setMessage("Please wait...");
    mProgress.setCancelable(false);

  }

  public void onChangeState()
  {
    mAuth.addAuthStateListener(mAuthListener);
    checkifLoggedIn(mAuth.getCurrentUser());
  }


  private void sendVerificationLink()
  {
    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
    if(user!=null)
    {
      user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
          if(task.isSuccessful())
          {
            Toast.makeText(context, context.getString(R.string.verification_email), Toast.LENGTH_SHORT).show();
          }
          else
          {
            Toast.makeText(context, context.getString(R.string.failed_email), Toast.LENGTH_SHORT).show();
          }
        }
      });
    }


  }


  public void clearState()
  {
    if(mAuthListener!=null)
    {
      mAuth.removeAuthStateListener(mAuthListener);
    }

  }

  public void initFirbase()
  {

    mAuthListener=new FirebaseAuth.AuthStateListener()
    {
      @Override
      public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
      {
        FirebaseUser user=firebaseAuth.getCurrentUser();
        checkifLoggedIn(user);

      }
    };

  }


  private void checkifLoggedIn(FirebaseUser currentUser)
  {
    if(currentUser==null)
    {
            Intent login=new Intent(context, MainActivity.class);
            login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(login);

    }

  }

  public void login(String email, String password)
  {
    mProgress.show();
    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
      @Override
      public void onComplete(@NonNull Task<AuthResult> task)
      {
        final FirebaseUser user=mAuth.getCurrentUser();

        if(task.isSuccessful())
        {
          try {
                if(user.isEmailVerified())
                {
                  DatabaseReference mRef=FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
                   ValueEventListener valueEventListener=new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                      if(!dataSnapshot.exists())
                      {
                        mProgress.dismiss();
                        Intent intent =new Intent(context,ActivityCompleteReg.class);
                        context.startActivity(intent);
                      }

                      else
                        {
                          mProgress.dismiss();
                          Intent intent=new Intent(context.getApplicationContext(),HomeActivity.class);
                          intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                          context.startActivity(intent);



                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                  };
                   mRef.addListenerForSingleValueEvent(valueEventListener);


                }
                else
              {
                mProgress.dismiss();
                Toast.makeText(context, "Email is not verified check your mail inbox", Toast.LENGTH_SHORT).show();
                mAuth.signOut();

            }


          }

          catch (NullPointerException e)
          {
            Log.e(tag,"On complete null pointer exception"+e);

          }

        }


      }
    }).addOnFailureListener(new OnFailureListener() {
      @Override
      public void onFailure(@NonNull Exception e) {
      mProgress.dismiss();
        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();

      }
    });


  }

  public void createAccount(String email, String password)
  {
    mProgress.show();

    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
      @Override
      public void onComplete(@NonNull Task<AuthResult> task) {

        if(task.isSuccessful())
        {
          mProgress.dismiss();
          sendVerificationLink();
          Toast.makeText(context,context.getString(R.string.toast_success),Toast.LENGTH_LONG);
          mAuth.signOut();
          Intent intent=new Intent(context,MainActivity.class);
          context.startActivity(intent);
        }



      }
    }).addOnFailureListener(new OnFailureListener() {
      @Override
      public void onFailure(@NonNull Exception e)
      {
        mProgress.dismiss();
        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
      }
    });

  }


    public void addUser(String name, String phone, String id, String type, String businessName, String location, String pin)
    {
        String userId=mAuth.getCurrentUser().getUid();
        Users user=new Users(userId,name,phone,id,type,businessName,location,pin);
        myRefDataBase.child(context.getString(R.string.databaseName)).child(mAuth.getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(context, "Profile added successfully", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(context,HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(intent);

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });





    }







}
