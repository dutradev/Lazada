package com.dutradev.lazada.View.LogIn.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dutradev.lazada.Model.LogIn.ModelLogIn;
import com.dutradev.lazada.R;
import com.dutradev.lazada.View.Home.HomeActivity;
import com.dutradev.lazada.View.LogIn.LogInActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by dutradev on 15/08/2017.
 */

public class FragmentLogIn extends Fragment implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener{

    Button btnDangNhapFacebook,btnDangNhapGoogle;
    CallbackManager callbackManager;
    GoogleApiClient mGoogleApiClient;
    public static int SIGN_IN_GOOGLE_PLUS = 111;
    ModelLogIn modelLogIn;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_login,container,false);
        //FacebookSdk.sdkInitialize(getContext().getApplicationContext());

        modelLogIn = new ModelLogIn();
        mGoogleApiClient = modelLogIn.LayGoogleApiClient(getContext(),this);

        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Intent iTrangChu = new Intent(getActivity(), HomeActivity.class);
                startActivity(iTrangChu);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        btnDangNhapFacebook = (Button) view.findViewById(R.id.btnDangNhapFacebook);
        btnDangNhapGoogle = (Button) view.findViewById(R.id.btnDangNhapGoogle);
        btnDangNhapFacebook.setOnClickListener(this);
        btnDangNhapGoogle.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnDangNhapFacebook:
                LoginManager.getInstance().logInWithReadPermissions(FragmentLogIn.this, Arrays.asList("public_profile"));
                break;
            case R.id.btnDangNhapGoogle:
                Intent iGooglePlus = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(iGooglePlus,SIGN_IN_GOOGLE_PLUS);
                showProcessDialog();
                ;break;
        }

    }

    private void showProcessDialog(){
        if(progressDialog == null){
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.setIndeterminate(true);
        }
        progressDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
        if(requestCode == SIGN_IN_GOOGLE_PLUS){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()){
                progressDialog.cancel();
                Intent iTrangChu = new Intent(getActivity(), HomeActivity.class);
                startActivity(iTrangChu);
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        progressDialog.cancel();
    }
}
