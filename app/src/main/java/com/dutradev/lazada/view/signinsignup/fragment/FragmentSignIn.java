package com.dutradev.lazada.view.signinsignup.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dutradev.lazada.model.signinsignup.ModelSignIn;
import com.dutradev.lazada.R;
import com.dutradev.lazada.view.home.HomeActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Arrays;

/**
 * Created by dutradev on 15/08/2017.
 */

public class FragmentSignIn extends Fragment implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener{

    Button btnDangNhapFacebook,btnDangNhapGoogle,btnDangNhap;
    CallbackManager callbackManager;
    GoogleApiClient mGoogleApiClient;
    public static int SIGN_IN_GOOGLE_PLUS = 111;
    ModelSignIn mModelSignIn;
    ProgressDialog progressDialog;
    EditText edTenDangNhap,edMatKhau;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_signin,container,false);
        //FacebookSdk.sdkInitialize(getContext().getApplicationContext());

        mModelSignIn = new ModelSignIn();
        mGoogleApiClient = mModelSignIn.doGoogleApiClient(getContext(),this);

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
        btnDangNhap = (Button) view.findViewById(R.id.btnDangNhap);
        edTenDangNhap = (EditText) view.findViewById(R.id.edDiaChiEmailDangNhap);
        edMatKhau = (EditText) view.findViewById(R.id.edMatKhauDangNhap);

        btnDangNhapFacebook.setOnClickListener(this);
        btnDangNhapGoogle.setOnClickListener(this);
        btnDangNhap.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnDangNhapFacebook:
                LoginManager.getInstance().logInWithReadPermissions(FragmentSignIn.this, Arrays.asList("public_profile"));
                break;
            case R.id.btnDangNhapGoogle:
                Intent iGooglePlus = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(iGooglePlus,SIGN_IN_GOOGLE_PLUS);
                showProcessDialog();
                ;break;
            case R.id.btnDangNhap:
                String mUserName = edTenDangNhap.getText().toString();
                String mPassword = edMatKhau.getText().toString();
                boolean isValid = mModelSignIn.checkSignIn(getActivity(),mUserName,mPassword);
                if(isValid){
                    Intent iTrangChu = new Intent(getActivity(), HomeActivity.class);
                    startActivity(iTrangChu);
                }else{
                    Toast.makeText(getActivity(),"Tên đăng nhập và mật khẩu không đúng !",Toast.LENGTH_SHORT).show();
                }
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
