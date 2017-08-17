package com.dutradev.lazada.view.signinsignup.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.dutradev.lazada.R;
import com.dutradev.lazada.model.objectclass.Customer;
import com.dutradev.lazada.presenter.signup.PresenterLogicSignUp;
import com.dutradev.lazada.view.signinsignup.ISignUpActivity;

/**
 * Created by dutradev on 15/08/2017.
 */

public class FragmentISignUp extends Fragment implements ISignUpActivity,View.OnClickListener,View.OnFocusChangeListener{

    PresenterLogicSignUp mPresenterLogicSignUp;
    Button mButtonSignUp;
    EditText mEditTextName, mEditTextPassword, mEditTextRePassword, mEditTextEmail;
    SwitchCompat mReciveNotification;
    TextInputLayout mTextInputName;
    TextInputLayout mInputTextPassword;
    TextInputLayout mInputTextRePassword;
    TextInputLayout mInputTextEmail;
    Boolean isValid = false;
    String mReciveEmail = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_signup,container,false);

        mButtonSignUp = (Button) view.findViewById(R.id.btnDangKy);
        mEditTextName = (EditText) view.findViewById(R.id.edHoTenDK);
        mEditTextPassword = (EditText) view.findViewById(R.id.edMatKhauDK);
        mEditTextRePassword = (EditText) view.findViewById(R.id.edNhapLaiMatKhauDK);
        mEditTextEmail = (EditText) view.findViewById(R.id.edDiaChiEmailDK);
        mReciveNotification = (SwitchCompat) view.findViewById(R.id.sEmailDocQuyen);
        mTextInputName = (TextInputLayout) view.findViewById(R.id.input_edHoTenDK);
        mInputTextPassword = (TextInputLayout) view.findViewById(R.id.input_edMatKhauDK);
        mInputTextRePassword = (TextInputLayout) view.findViewById(R.id.input_edNhapLaiMatKhauDK);
        mInputTextEmail = (TextInputLayout)view.findViewById(R.id.input_edDiaChiEmailDK);

        mPresenterLogicSignUp = new PresenterLogicSignUp(this);

        mButtonSignUp.setOnClickListener(this);
        mEditTextName.setOnFocusChangeListener(this);
        mEditTextRePassword.setOnFocusChangeListener(this);
        mEditTextEmail.setOnFocusChangeListener(this);

        return view;
    }

    @Override
    public void registerSuccess() {
        Toast.makeText(getActivity(),"Đăng ký thành công !",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void registerFail() {
        Toast.makeText(getActivity(),"Đăng ký thất bại !",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){

            case R.id.btnDangKy:
                RegisterAccount();
                ;break;
        }
    }


    private void RegisterAccount(){
        String hoten = mEditTextName.getText().toString();
        String email = mEditTextEmail.getText().toString();
        String matkhau = mEditTextPassword.getText().toString();
        String nhaplaimatkhau = mEditTextRePassword.getText().toString();

        mReciveNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mReciveEmail = b + "";
            }
        });

        if(isValid) {
            Customer customer = new Customer();
            customer.setmName(hoten);
            customer.setmUserName(email);
            customer.setmPassword(matkhau);
            customer.setmReciveNotification(mReciveEmail);
            customer.setmCustomerType(2);

            mPresenterLogicSignUp.registerAccount(customer);
        }else{
            Log.d("kiemtra","Dang ky that bai ");
        }


    }

    @Override
    public void onFocusChange(View view, boolean b) {
        int id = view.getId();
        switch (id){
            case R.id.edHoTenDK:
                if(!b){
                    String chuoi = ((EditText)view).getText().toString();
                    if(chuoi.trim().equals("") || chuoi.equals(null)){
                        mTextInputName.setErrorEnabled(true);
                        mTextInputName.setError("Bạn chưa nhận mục này !");
                        isValid = false;
                    }else{
                        mTextInputName.setErrorEnabled(false);
                        mTextInputName.setError("");
                        isValid = true;
                    }
                }
                ;break;

            case R.id.edDiaChiEmailDK:
                if(!b){

                    String chuoi = ((EditText)view).getText().toString();

                    if(chuoi.trim().equals("") || chuoi.equals(null)){
                        mInputTextEmail.setErrorEnabled(true);
                        mInputTextEmail.setError("Bạn chưa nhận mục này !");
                        isValid = false;
                    }else{

                        Boolean isEmail = Patterns.EMAIL_ADDRESS.matcher(chuoi).matches();
                        if(!isEmail){
                            mInputTextEmail.setErrorEnabled(true);
                            mInputTextEmail.setError("Đây không phải là địa chỉ Email !");
                            isValid = false;
                        }else{
                            mInputTextEmail.setErrorEnabled(false);
                            mInputTextEmail.setError("");
                            isValid = true;
                        }
                    }
                }
                ;break;

            case R.id.edMatKhauDK:
                ;break;

            case R.id.edNhapLaiMatKhauDK:
                if(!b){
                    String chuoi = ((EditText)view).getText().toString();
                    String matkhau = mEditTextPassword.getText().toString();
                    if(!chuoi.equals(matkhau)){
                        mInputTextRePassword.setErrorEnabled(true);
                        mInputTextRePassword.setError("Mật khẩu không trùng khớp !");
                        isValid = false;
                    }else{
                        mInputTextRePassword.setErrorEnabled(false);
                        mInputTextRePassword.setError("");
                        isValid = true;
                    }
                }

                ;break;

        }
    }
}
