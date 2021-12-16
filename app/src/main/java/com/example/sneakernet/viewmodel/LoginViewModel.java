package com.example.sneakernet.viewmodel;

import androidx.lifecycle.ViewModel;

/**
 * view model for login. Checks login in status
 */

public class LoginViewModel extends ViewModel{
    /**
     * boolean to see sign in status
     */
        private boolean mIsSigningIn;

    /**
     * constructor
     */
        public LoginViewModel() {
            mIsSigningIn = false;
        }

    /**
     *
     * @return return sign in
     */
        public boolean getIsSigningIn() {
            return mIsSigningIn;
        }

    /**
     * setting sign in
     * @param mIsSigningIn
     */
        public void setIsSigningIn(boolean mIsSigningIn) {
            this.mIsSigningIn = mIsSigningIn;
        }


}
