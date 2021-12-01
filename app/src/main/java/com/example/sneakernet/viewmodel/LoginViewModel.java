package com.example.sneakernet.viewmodel;

import androidx.lifecycle.ViewModel;


public class LoginViewModel extends ViewModel{
        private boolean mIsSigningIn;


        public LoginViewModel() {
            mIsSigningIn = false;
        }

        public boolean getIsSigningIn() {
            return mIsSigningIn;
        }

        public void setIsSigningIn(boolean mIsSigningIn) {
            this.mIsSigningIn = mIsSigningIn;
        }


}
