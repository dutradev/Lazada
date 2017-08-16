package com.dutradev.lazada.View.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import com.dutradev.lazada.Adapter.ExpandAdapter;
import com.dutradev.lazada.Adapter.ViewPagerAdapter;
import com.dutradev.lazada.Model.LogIn.ModelLogIn;
import com.dutradev.lazada.Model.ObjectClass.ProductType;
import com.dutradev.lazada.Presenter.Home.MenuHandling.PresenterLogicMenuHandling;
import com.dutradev.lazada.R;
import com.dutradev.lazada.View.LogIn.LogInActivity;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


/**
 * Created by dutradev on 15/08/2017.
 */

public class HomeActivity extends AppCompatActivity implements ViewMenuHandling, GoogleApiClient.OnConnectionFailedListener {

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    ExpandableListView expandableListView;
    PresenterLogicMenuHandling logicMenuHandling;
    String userName = "";
    AccessToken accessToken;
    Menu menu;
    MenuItem itemLogIn, itemLogOut;
    ModelLogIn modelLogIn;
    GoogleApiClient mGoogleApiClient;
    GoogleSignInResult googleSignInResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.home_layout);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        expandableListView = (ExpandableListView) findViewById(R.id.epMenu);

        toolbar.setTitle("");

        setSupportActionBar(toolbar);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle.syncState();


        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        logicMenuHandling = new PresenterLogicMenuHandling(this);
        modelLogIn = new ModelLogIn();

        logicMenuHandling.LayDanhSachMenu();

        mGoogleApiClient = modelLogIn.LayGoogleApiClient(this,this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuhome, menu);
        this.menu = menu;

        itemLogIn = menu.findItem(R.id.itDangNhap);
        itemLogOut = menu.findItem(R.id.itDangXuat);

        accessToken = logicMenuHandling.LayTokenDungFacebook();
        googleSignInResult = modelLogIn.LayThongDangNhapGoogle(mGoogleApiClient);

        if(accessToken != null){
            GraphRequest graphRequest = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    try {
                        userName = object.getString("name");

                        itemLogIn.setTitle(userName);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            Bundle parameter = new Bundle();
            parameter.putString("fields","name");

            graphRequest.setParameters(parameter);
            graphRequest.executeAsync();
        }

        if(googleSignInResult != null){
            itemLogIn.setTitle(googleSignInResult.getSignInAccount().getDisplayName());
            Log.d("goo",googleSignInResult.getSignInAccount().getDisplayName());
        }

        if(accessToken != null || googleSignInResult != null){
            itemLogOut.setVisible(true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        int id = item.getItemId();
        switch (id) {
            case R.id.itDangNhap:
                if(accessToken == null && googleSignInResult == null) {
                    Intent iDangNhap = new Intent(this, LogInActivity.class);
                    startActivity(iDangNhap);
                };
                break;

            case R.id.itDangXuat:
                if (accessToken != null) {
                    LoginManager.getInstance().logOut();
                    this.menu.clear();
                    this.onCreateOptionsMenu(this.menu);
                }
                if(googleSignInResult != null){
                    Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                    this.menu.clear();
                    this.onCreateOptionsMenu(this.menu);

                }
                ;break;
        }

        return true;
    }

    @Override
    public void HienThiDanhSachMenu(List<ProductType> productTypes) {
        ExpandAdapter adapter = new ExpandAdapter(this, productTypes);
        expandableListView.setAdapter(adapter);
        expandableListView.deferNotifyDataSetChanged();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
