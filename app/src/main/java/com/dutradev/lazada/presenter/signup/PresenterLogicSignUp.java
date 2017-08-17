package com.dutradev.lazada.presenter.signup;

import com.dutradev.lazada.model.objectclass.Customer;
import com.dutradev.lazada.model.signinsignup.ModelSignUp;
import com.dutradev.lazada.view.signinsignup.ISignUpActivity;

/**
 * Created by dutradev on 16/08/2017.
 */

public class PresenterLogicSignUp implements IPresenterSignUp {

    ISignUpActivity mISignUpActivity;
    ModelSignUp mModelSignUp;

    public PresenterLogicSignUp(ISignUpActivity mISignUpActivity){
        this.mISignUpActivity = mISignUpActivity;
        mModelSignUp = new ModelSignUp();

    }

    @Override
    public void registerAccount(Customer mCustomer) {
        boolean isCheck = mModelSignUp.registerMember(mCustomer);
        if(isCheck){
            mISignUpActivity.registerSuccess();
        }else{
            mISignUpActivity.registerFail();
        }
    }
}
