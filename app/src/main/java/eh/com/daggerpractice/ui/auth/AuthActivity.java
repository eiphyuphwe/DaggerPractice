package eh.com.daggerpractice.ui.auth;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.RequestManager;

import javax.inject.Inject;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.support.DaggerAppCompatActivity;
import eh.com.daggerpractice.R;
import eh.com.daggerpractice.model.User;
import eh.com.daggerpractice.ui.main.MainActivity;
import eh.com.daggerpractice.viewmodel.ViewModelProviderFactory;

public class AuthActivity extends DaggerAppCompatActivity {


    @Inject
    RequestManager requestManager;

    @Inject
    Drawable logo;

    @Inject
    ViewModelProviderFactory providerFactory;
    private static final String TAG = "AuthActivity";

    AuthViewModel viewModel;
    EditText edtUserId;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        progressBar = findViewById(R.id.progress_bar);
        setLogo();

        viewModel = ViewModelProviders.of(this,providerFactory).get(AuthViewModel.class);
        edtUserId = findViewById(R.id.user_id_input);
        catchEvents();
        subscribeObservers();

    }

    private void catchEvents()
    {
        findViewById(R.id.login_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
    }

    private void setLogo()
    {
        requestManager.load(logo)
                .into((ImageView) findViewById(R.id.login_logo));
    }


    private void attemptLogin() {

        viewModel.authenticateUserbyId(Integer.parseInt(edtUserId.getText().toString()));
    }

    private void subscribeObservers()
    {
        viewModel.observeAuthState().observe(this, new Observer< AuthResource< User > >() {
            @Override
            public void onChanged(AuthResource< User > userAuthResource) {

                switch (userAuthResource.status)
                {
                    case LOADING:
                    {
                        showProgressBar(true);
                        break;
                    }
                    case ERROR:
                    {
                        showProgressBar(false);
                        Toast.makeText(AuthActivity.this,userAuthResource.message+
                                "\nDid you Enter the ID during 1 to 10 ",Toast.LENGTH_LONG).show();
                        break;
                    }
                    case AUTHENTICATED:
                    {
                        showProgressBar(false);
                        Toast.makeText(AuthActivity.this,userAuthResource.data.getEmail(),Toast.LENGTH_LONG).show();
                        loginSuccess();
                        break;
                    }
                    case NOT_AUTHENTICATED:
                    {
                        showProgressBar(false);
                        break;
                    }
                }
            }
        });
    }

    private void loginSuccess()
    {
        Intent i = new Intent(AuthActivity.this, MainActivity.class);
        startActivity(i);
    }

    private void showProgressBar(boolean isVisible)
    {
        if(isVisible)
        {
            progressBar.setVisibility(View.VISIBLE);
        }
        else
        {
            progressBar.setVisibility(View.GONE);
        }
    }
}
