package com.bishant.restaurantfinder.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.bishant.restaurantfinder.R;
import com.bishant.restaurantfinder.helper.RestaurantActivity;
import com.bishant.restaurantfinder.utilities.UtilitiesFunctions;
import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends RestaurantActivity implements View.OnClickListener {

    private Button loginButton;
    private Dialog dialog;
    private Dialog dialogSignUp;
    private TextView forgotPasswordTV;
    private TextView signUpTV;
    private LinearLayout mainLayoutContianer;
    private CardView facebookLayout;
    private CardView googleLayout;
    private EditText emailEditText;
    private EditText passwordEditText;
    private ProgressBar progressBarLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        initialiseViews();
        initialiseListeners();

    }

    @Override
    protected void initialiseViews() {
        loginButton = findViewById(R.id.button_login);
        forgotPasswordTV = findViewById(R.id.forgot_password);
        signUpTV = findViewById(R.id.sign_up_text_view);
        emailEditText = findViewById(R.id.email_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        mainLayoutContianer = findViewById(R.id.main_content);
        facebookLayout = findViewById(R.id.facebook_layout);
        googleLayout = findViewById(R.id.google_layout);
        progressBarLogin = findViewById(R.id.progress_bar_login);
        progressBarLogin.setVisibility(View.GONE);
    }

    @Override
    protected void initialiseListeners() {
        loginButton.setOnClickListener(this);
        forgotPasswordTV.setOnClickListener(this);
        signUpTV.setOnClickListener(this);
        facebookLayout.setOnClickListener(this);
        googleLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_login:
                if (!emailEditText.getText().toString().trim().isEmpty() && !passwordEditText.getText().toString().trim().isEmpty()) {
                    if (UtilitiesFunctions.isNetworkAvailable(LoginActivity.this)) {
                        showLoadingDialog();
                        //loginPresenter.loginApi(emailEditText.getText().toString().trim(), passwordEditText.getText().toString().trim());
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Email and Password cannot be empty", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.forgot_password:
                showForgotPasswordDialog();
                break;
            case R.id.sign_up_text_view:
                showSignUpDialog();
                break;
        }
    }

    public void showLoadingDialog() {
        progressBarLogin.setVisibility(View.VISIBLE);
    }

    public void hideLoadingDialog() {
        progressBarLogin.setVisibility(View.GONE);
    }

    private void showSignUpDialog() {
        dialogSignUp = new Dialog(LoginActivity.this, R.style.AppTheme);
        dialogSignUp.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSignUp.setContentView(R.layout.dialog_create_account);

        //initialize
        EditText nameEditText = dialogSignUp.findViewById(R.id.name);
        EditText emailEditText = dialogSignUp.findViewById(R.id.email);
        EditText phoneNumberEditText = dialogSignUp.findViewById(R.id.phone);
        EditText passwordEditText = dialogSignUp.findViewById(R.id.password);
        EditText confirmPassEditText = dialogSignUp.findViewById(R.id.confirm_password);

        final ImageView nameIcon = dialogSignUp.findViewById(R.id.name_icon);
        final ImageView emailIcon = dialogSignUp.findViewById(R.id.email_icon);
        final ImageView phoneIcon = dialogSignUp.findViewById(R.id.phone_icon);
        final ImageView passwordIcon = dialogSignUp.findViewById(R.id.password_icon);
        final ImageView confirmPasswordIcon = dialogSignUp.findViewById(R.id.confirm_password_icon);

        ImageView cross = dialogSignUp.findViewById(R.id.cross);
        Button signUpButton = dialogSignUp.findViewById(R.id.sign_up);

        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogSignUp.dismiss();
            }
        });

        signUpButton.setOnClickListener(new UserRegisterListener(nameEditText, emailEditText, phoneNumberEditText, passwordEditText, confirmPassEditText));

        nameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    if (dialogSignUp.getWindow() != null)
                        dialogSignUp.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                    setFocussedBackground(nameIcon);
                } else {
                    setUnFocussedBackground(nameIcon);
                }
            }
        });

        phoneNumberEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    if (dialogSignUp.getWindow() != null)
                        dialogSignUp.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                    setFocussedBackground(phoneIcon);
                } else {
                    setUnFocussedBackground(phoneIcon);
                }
            }
        });

        emailEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    if (dialogSignUp.getWindow() != null)
                        dialogSignUp.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                    setFocussedBackground(emailIcon);
                } else {
                    setUnFocussedBackground(emailIcon);
                }
            }
        });

        passwordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    if (dialogSignUp.getWindow() != null)
                        dialogSignUp.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                    setFocussedBackground(passwordIcon);
                } else {
                    setUnFocussedBackground(passwordIcon);
                }
            }
        });

        confirmPassEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    if (dialogSignUp.getWindow() != null)
                        dialogSignUp.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                    setFocussedBackground(confirmPasswordIcon);
                } else {
                    setUnFocussedBackground(confirmPasswordIcon);
                }
            }
        });


        if (dialogSignUp.getWindow() != null)
            dialogSignUp.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        dialogSignUp.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {

            }
        });
        dialogSignUp.show();
    }

    public void hideSignUpDialog() {

        if (dialogSignUp != null) {
            dialogSignUp.dismiss();
        }
    }

    private void setFocussedBackground(ImageView icon) {
        icon.setColorFilter(ContextCompat.getColor(LoginActivity.this, R.color.colorAccent));
    }

    private void setUnFocussedBackground(ImageView icon) {
        icon.setColorFilter(ContextCompat.getColor(LoginActivity.this, R.color.colorLightGrey));
    }

    private void showForgotPasswordDialog() {
        dialog = new Dialog(LoginActivity.this, R.style.AppTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_forgot_password);

        //initialize
        final ImageView emailIcon = dialog.findViewById(R.id.email_icon);
        final TextView headerTitle = dialog.findViewById(R.id.header_title);
        final TextView headerSubTitle = dialog.findViewById(R.id.header_sub_title);
        final TextView termAndConditions = dialog.findViewById(R.id.terms_and_condition);
        EditText emailEditText = dialog.findViewById(R.id.email);
        ImageView cross = dialog.findViewById(R.id.cross);
        Button resetPasswordButton = dialog.findViewById(R.id.reset_password);

        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        emailEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    if (dialog.getWindow() != null)
                        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                    setFocussedBackground(emailIcon);
                } else {
                    setUnFocussedBackground(emailIcon);
                }
            }
        });

        if (dialog.getWindow() != null)
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {

            }
        });
        dialog.show();
    }

    private void showSignupSuccessSnackBar() {

        final Snackbar snackbar = Snackbar
                .make(mainLayoutContianer, "Successfully Registered!", Snackbar.LENGTH_LONG);

        snackbar.setAction("Okay", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar.dismiss();

            }
        });
        snackbar.setActionTextColor(Color.WHITE);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(getResources().getColor(R.color.colorBlack));
        snackbar.setDuration(10000);
        TextView textView = sbView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
    }

    private class UserRegisterListener implements View.OnClickListener {
        ProgressBar progressBarSignUp;
        private EditText nameET, emailET, passwordET, confirmPasswordET, phoneNumberET;

        private UserRegisterListener(EditText name, EditText email, EditText phoneNumber, EditText password, EditText confirmPassword) {
            this.nameET = name;
            this.emailET = email;
            this.passwordET = password;
            this.confirmPasswordET = confirmPassword;
            this.phoneNumberET = phoneNumber;
        }

        @Override
        public void onClick(View view) {
            String name = this.nameET.getText().toString();
            String email = this.emailET.getText().toString();
            String password = this.passwordET.getText().toString();
            String phone = this.phoneNumberET.getText().toString();
            String confirmPassword = this.confirmPasswordET.getText().toString();
            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) ||
                    TextUtils.isEmpty(confirmPassword)) {

                Toast.makeText(LoginActivity.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();

                return;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailET.setError("Valid email required");
                return;
            }

            if (!password.equals(confirmPassword)) {
                Toast.makeText(LoginActivity.this, "Password and confirm password do not match", Toast.LENGTH_SHORT).show();
                passwordET.setError("Password and confirm password should match");
                confirmPasswordET.setError("Password and confirm password should match");

                return;
            }
            showLoadingDialog();
//            loginPresenter.signUpApi(name, email, phone, password, confirmPassword);
        }

    }
}
