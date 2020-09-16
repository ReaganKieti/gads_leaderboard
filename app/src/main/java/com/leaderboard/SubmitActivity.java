package com.leaderboard;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.leaderboard.Data.ApiUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SubmitActivity extends AppCompatActivity implements View.OnClickListener {
    private Button submitBtn;
    private ImageView backBtn;
    private EditText firstNameInput;
    private EditText lastNameInput;
    private EditText emailInput;
    private EditText githubLinkInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

        //UI
        submitBtn = findViewById(R.id.form_submit_btn);
        backBtn = findViewById(R.id.back_btn);
        firstNameInput = findViewById(R.id.first_name);
        lastNameInput = findViewById(R.id.last_name);
        emailInput = findViewById(R.id.email);
        githubLinkInput = findViewById(R.id.github_project_link);

        // SETTING listeners
        submitBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == submitBtn){
            validateData();
        }
        else if (view == backBtn){
            onBackPressed();
        }
    }

    private void validateData(){
        String email = emailInput.getText().toString();
        String firstName = firstNameInput.getText().toString();
        String lastName = lastNameInput.getText().toString();
        String githubLink = githubLinkInput.getText().toString();

        if (firstName.isEmpty()|| lastName.isEmpty()|| email.isEmpty()|| githubLink.isEmpty()|| !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() || !URLUtil.isValidUrl(githubLink)){
            if(firstName.isEmpty()){
                firstNameInput.setError("Please enter First Name");
                firstNameInput.requestFocus();
            }

            if(lastName.isEmpty()){
                lastNameInput.setError("Please enter Last Name");
                lastNameInput.requestFocus();
            }

            if(email.isEmpty()){
                emailInput.setError("Please enter Email");
                emailInput.requestFocus();
            }
            if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                emailInput.setError("Please enter valid Email");
                emailInput.requestFocus();
            }


            if(githubLink.isEmpty()){
                githubLinkInput.setError("Please enter Github Link");
                githubLinkInput.requestFocus();
            }

            if(!URLUtil.isValidUrl(githubLink)){
                githubLinkInput.setError("Please enter valid link");
                githubLinkInput.requestFocus();
            }

        }
        else {
            createConfirmAlertDialog();
        }
    }
    private void submitProject(String email,String firstName,String lastName,String githubLink){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://docs.google.com/forms/d/e/")
                .build();
        ApiUtil apiUtil = retrofit.create(ApiUtil.class);

        apiUtil.submitProject(email,firstName,lastName,githubLink).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful())
                    createSuccessAlertDialog();
                else
                    createErrorAlertDialog();
                System.out.println(response.toString());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                createErrorAlertDialog();
            }
        });
    }

    public void createConfirmAlertDialog(){

        String email = emailInput.getText().toString();
        String firstName = firstNameInput.getText().toString();
        String lastName = lastNameInput.getText().toString();
        String githubLink = githubLinkInput.getText().toString();

        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.create();
        View layout = getLayoutInflater().inflate(R.layout.alert_confirm,null);
        ImageView close = layout.findViewById(R.id.confirm_close);
        Button okBtn = layout.findViewById(R.id.confirm_btn);
        builder.setView(layout);
        AlertDialog dialog= builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                submitProject(email,firstName,lastName,githubLink);
            }
        });
    }

    public void createSuccessAlertDialog(){
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.create();
        View layout = getLayoutInflater().inflate(R.layout.alert_success,null);
        builder.setView(layout);
        AlertDialog dialog= builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
    public void createErrorAlertDialog(){
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.create();
        View layout = getLayoutInflater().inflate(R.layout.alert_error,null);
        builder.setView(layout);
        AlertDialog dialog= builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
}