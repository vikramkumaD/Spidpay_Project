package com.example.spidpay.interfaces;

import androidx.lifecycle.LiveData;
import com.example.spidpay.data.response.UserData;

public interface RegisterInterface extends CommonInterface {

    void onSuccess(LiveData<UserData> responseLiveData);

}
