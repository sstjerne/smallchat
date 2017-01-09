package chat.small.demo.org.smallchat;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

public class UserEditActivity extends AppCompatActivity {


    public static final int REQUEST_CODE_BY_REGISTER = 2;

    private AutoCompleteTextView mEmailView, mNameView, mSurnameView;
    private EditText mPasswordView,mConfirmPasswordView;

    private UserRegisterTask userRegisterTask = null;

    private View mProgressView;
    private View mUserEditFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.user_edit_email);
        mNameView = (AutoCompleteTextView) findViewById(R.id.user_edit_name);
        mSurnameView = (AutoCompleteTextView) findViewById(R.id.user_edit_surname);

        mPasswordView = (EditText) findViewById(R.id.user_edit_password);
        mConfirmPasswordView = (EditText) findViewById(R.id.user_edit_confirm);

        Button mRegisterButton = (Button) findViewById(R.id.user_edit_register_button);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean response  = attemptRegister();
                if (response) {
                    onBackPressed();
                }
            }
        });


        Button mCancelButton = (Button) findViewById(R.id.user_edit_cancel_button);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mUserEditFormView = findViewById(R.id.user_edit_form);
        mProgressView = findViewById(R.id.user_edit_bar_progress);
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private boolean attemptRegister() {
        if (userRegisterTask != null) {
            return false;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String confirmPassword = mConfirmPasswordView.getText().toString();

        String name = mNameView.getText().toString();
        String surname = mSurnameView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }
        if (!TextUtils.isEmpty(confirmPassword) && !isPasswordValid(confirmPassword)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mConfirmPasswordView;
            cancel = true;
        }

        if (!confirmPassword.equals(password)) {
            mPasswordView.setError(getString(R.string.error_mismatch_password));
            focusView = mConfirmPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (TextUtils.isEmpty(name)) {
            mNameView.setError(getString(R.string.error_field_required));
            focusView = mNameView;
            cancel = true;
        }


        if (TextUtils.isEmpty(surname)) { 
            mSurnameView.setError(getString(R.string.error_field_required));
            focusView = mSurnameView;
            cancel = true;
        }


        if (cancel) {
            // There was an error;
            focusView.requestFocus();
            return false;
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            userRegisterTask = new UserRegisterTask(email, password, name, surname);
            userRegisterTask.execute((Void) null);
            return true;
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        //return email.contains("@");
        return true;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mUserEditFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mUserEditFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mUserEditFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mUserEditFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private final String mName;
        private final String mSurname;


        UserRegisterTask(String email, String password, String name, String surname) {
            mEmail = email;
            mPassword = password;
            mName = name;
            mSurname = surname;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            String username = null;
            try {
                username = RESTClientWrapper.createUser(mEmail,mPassword,mName,mSurname);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return username != null;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            userRegisterTask = null;
            showProgress(false);

            if (success) {
                //Notify with Toast ?

                Intent resultIntent = new Intent();
                resultIntent.putExtra("username", mEmail);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            } else {
                //TODO:Handle message Error = > Focus the field is mistakes
                mEmailView.setError(getString(R.string.error_generic));
                mEmailView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            userRegisterTask = null;
            showProgress(false);
        }
    }
}
