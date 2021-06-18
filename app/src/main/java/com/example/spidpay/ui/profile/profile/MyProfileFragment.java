package com.example.spidpay.ui.profile.profile;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.spidpay.R;
import com.example.spidpay.data.repository.MyProfileRepository;
import com.example.spidpay.data.response.MyAddressResponse;
import com.example.spidpay.data.response.MyProfileResponse;
import com.example.spidpay.data.response.UpdateResponse;
import com.example.spidpay.databinding.AddressEditLayoutBinding;
import com.example.spidpay.databinding.FragmentMyProfileBinding;
import com.example.spidpay.interfaces.ChangeTitlenandIconInterface;
import com.example.spidpay.interfaces.MyProfileInterface;
import com.example.spidpay.interfaces.UpdateBottomView;
import com.example.spidpay.ui.profile.MyProfileViewModel;
import com.example.spidpay.util.Constant;
import com.example.spidpay.util.PrefManager;
import com.google.android.material.bottomsheet.BottomSheetDialog;


public class MyProfileFragment extends Fragment implements MyProfileInterface {
    private static final String ARG_PARAM1 = "param1";
    FragmentMyProfileBinding fragmentMyProfileBinding;
    ChangeTitlenandIconInterface changeTitlenandIconInterface;
    UpdateBottomView updateBottomView;
    MyProfileInterface myProfileInterface;
    MyProfileViewModel myProfileViewModel;
    MyAddressResponse myAddressResponse;
    BottomSheetDialog update_address_bottomSheetDialog;
    AddressEditLayoutBinding addressEditLayoutBinding;

    public MyProfileFragment() {
    }

    public static MyProfileFragment newInstance(String param1, String param2) {
        MyProfileFragment fragment = new MyProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        changeTitlenandIconInterface = (ChangeTitlenandIconInterface) context;
        updateBottomView = (UpdateBottomView) context;
        myProfileInterface = MyProfileFragment.this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentMyProfileBinding = FragmentMyProfileBinding.inflate(inflater, container, false);
        return fragmentMyProfileBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MyProfileRepository myProfileRepository = new MyProfileRepository(requireContext(), myProfileInterface);
        myProfileViewModel = new ViewModelProvider(requireActivity()).get(MyProfileViewModel.class);
        myProfileViewModel.myProfileRepository = myProfileRepository;
        myProfileViewModel.myProfileInterface = myProfileInterface;
        myProfileViewModel.getMyProfile(new PrefManager(requireContext()).getUserID());
        getViewLifecycleOwner();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateBottomView.bottomViewId(Constant.BOTTOM_HOME);
        changeTitlenandIconInterface.changeTitlenadIcon(getResources().getString(R.string.myprofile), true);
        fragmentMyProfileBinding.imgEditAddress.setOnClickListener(v -> update_address_BottomSheet());
    }


    @Override
    public void onProfileSuccess(LiveData<MyProfileResponse> myProfileResponseLiveData) {
        myProfileResponseLiveData.observe(this, myProfileResponse -> {
            fragmentMyProfileBinding.pbMyprofile.setVisibility(View.GONE);
            fragmentMyProfileBinding.setUserprofile(myProfileResponse);
            myProfileViewModel.getMyAddress(new PrefManager(requireContext()).getUserID());
        });
    }

    @Override
    public void onAddressSuccess(LiveData<MyAddressResponse> myProfileResponseLiveData) {
        myProfileResponseLiveData.observe(this, myAddressResponse -> {
            fragmentMyProfileBinding.pbMyprofile.setVisibility(View.GONE);
            fragmentMyProfileBinding.setUseraddress(myAddressResponse);
            this.myAddressResponse = myAddressResponse;
        });
    }

    @Override
    public void onUpdateAddress(LiveData<UpdateResponse> commonResponseLiveData) {
        commonResponseLiveData.observe(this, commonResponse -> {
            if (commonResponse.userid.equals(new PrefManager(getActivity()).getUserID())) {
                update_address_bottomSheetDialog.dismiss();
                myProfileViewModel.getMyAddress(commonResponse.userid);
            }
        });
    }


    @Override
    public void onServiceStart() {
        fragmentMyProfileBinding.pbMyprofile.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFailed(String msg) {
        Constant.showToast(getActivity(), msg);
        fragmentMyProfileBinding.pbMyprofile.setVisibility(View.GONE);
    }


    public void update_address_BottomSheet() {
        update_address_bottomSheetDialog = new BottomSheetDialog(requireContext());
        addressEditLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.address_edit_layout, null, false);
        update_address_bottomSheetDialog.setContentView(addressEditLayoutBinding.getRoot());
        addressEditLayoutBinding.setMyprofileviewmodel(myProfileViewModel);
        addressEditLayoutBinding.setAddress(myAddressResponse);
        myProfileViewModel.userid = new PrefManager(getContext()).getUserID();
        addressEditLayoutBinding.setLifecycleOwner(this);
        update_address_bottomSheetDialog.show();
        addressEditLayoutBinding.imgDismissDialog.setOnClickListener(v -> update_address_bottomSheetDialog.dismiss());
    }
}