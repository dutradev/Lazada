package com.dutradev.lazada.view.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import com.dutradev.lazada.adapter.ExpandAdapter;
import com.dutradev.lazada.adapter.ViewPagerAdapter;
import com.dutradev.lazada.model.signin.ModelSignIn;
import com.dutradev.lazada.model.objectclass.ProductType;
import com.dutradev.lazada.presenter.home.menuhandling.PresenterLogicMenuHandling;
import com.dutradev.lazada.R;
import com.dutradev.lazada.view.signin.SignInActivity;
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

public class HomeActivity extends AppCompatActivity implements ViewMenuHandling, GoogleApiClient.OnConnectionFailedListener, AppBarLayout.OnOffsetChangedListener {

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
    ModelSignIn mModelSignIn;
    GoogleApiClient mGoogleApiClient;
    GoogleSignInResult mGoogleSignInResult;
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    AppBarLayout mAppBarLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.home_layout);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        expandableListView = (ExpandableListView) findViewById(R.id.epMenu);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

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
        mModelSignIn = new ModelSignIn();

        logicMenuHandling.LayDanhSachMenu();

        mGoogleApiClient = mModelSignIn.LayGoogleApiClient(this,this);

        mAppBarLayout.addOnOffsetChangedListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuhome, menu);
        this.menu = menu;

        itemLogIn = menu.findItem(R.id.itDangNhap);
        itemLogOut = menu.findItem(R.id.itDangXuat);

        accessToken = logicMenuHandling.LayTokenDungFacebook();
        mGoogleSignInResult = mModelSignIn.LayThongDangNhapGoogle(mGoogleApiClient);

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

        if(mGoogleSignInResult != null){
            itemLogIn.setTitle(mGoogleSignInResult.getSignInAccount().getDisplayName());
            Log.d("goo", mGoogleSignInResult.getSignInAccount().getDisplayName());
        }

        if(accessToken != null || mGoogleSignInResult != null){
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
                if(accessToken == null && mGoogleSignInResult == null) {
                    Intent iDangNhap = new Intent(this, SignInActivity.class);
                    startActivity(iDangNhap);
                };
                break;

            case R.id.itDangXuat:
                if (accessToken != null) {
                    LoginManager.getInstance().logOut();
                    this.menu.clear();
                    this.onCreateOptionsMenu(this.menu);
                }
                if(mGoogleSignInResult != null){
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

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if(mCollapsingToolbarLayout.getHeight() + verticalOffset <=  1.5 * ViewCompat.getMinimumHeight(mCollapsingToolbarLayout)){
            LinearLayout linearLayout = (LinearLayout) appBarLayout.findViewById(R.id.linear_search);
            linearLayout.animate().alpha(0).setDuration(200);

            MenuItem itSearch = menu.findItem(R.id.itSearch);
            itSearch.setVisible(true);

        }else{
            LinearLayout linearLayout = (LinearLayout) appBarLayout.findViewById(R.id.linear_search);
            linearLayout.animate().alpha(1).setDuration(200);
            try{
                MenuItem itSearch = menu.findItem(R.id.itSearch);
                itSearch.setVisible(false);
            }catch (Exception e){

            }

        }
    }
}
