package com.example.spidpay.ui.profile.kyc;


import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.spidpay.R;
import com.example.spidpay.data.repository.MyProfileRepository;
import com.example.spidpay.data.repository.StaticRepository;
import com.example.spidpay.data.response.CompanyReponse;
import com.example.spidpay.data.response.KYCResponse;
import com.example.spidpay.data.response.UpdateResponse;
import com.example.spidpay.databinding.ChooseoptionBinding;
import com.example.spidpay.databinding.FragmentKYCBinding;
import com.example.spidpay.databinding.UpdateCompanyDetailBinding;
import com.example.spidpay.databinding.UpdateKycLayoutBinding;
import com.example.spidpay.interfaces.KYCInterface;
import com.example.spidpay.interfaces.OnStaticClickIterface;
import com.example.spidpay.interfaces.StaticInterface;
import com.example.spidpay.ui.profile.MyProfileViewModel;
import com.example.spidpay.ui.signup.InterrestedforAdapter;
import com.example.spidpay.util.Constant;
import com.example.spidpay.util.ItemOffsetDecoration;
import com.example.spidpay.util.PrefManager;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class KYCFragment extends Fragment implements KYCInterface, OnStaticClickIterface, StaticInterface {
    private static final int REQUEST_CAPTURE_IMAGE = 100;
    private static final int REQUEST_PICK_IMAGE = 101;
    FragmentKYCBinding fragmentKYCBinding;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    KYCInterface kycInterface;
    MyProfileViewModel myProfileViewModel;
    MyProfileRepository myProfileRepository;
    BottomSheetDialog bottomSheetDialog_kyc, bottomSheetDialog_company, bottomSheetDialog_chooseOption;
    UpdateKycLayoutBinding updateKycLayoutBinding;
    ChooseoptionBinding chooseoptionBinding;
    UpdateCompanyDetailBinding updateBankDetailBinding;
    KYCResponse kycResponse;
    CompanyReponse companyReponse;
    BottomSheetDialog interrestedfor_bottomsheet;
    OnStaticClickIterface onStaticClickIterface;
    StaticInterface staticInterface;
    StaticRepository staticRepository;
    ProgressBar progressBar_for_bottom_sheet;
    private boolean is_pb_for_bottom_sheet = false;
    private boolean is_kyc_data_selection = true;
    File photoFile = null, finalpath = null;
    Uri photoURI;

    public KYCFragment() {

    }

    public static KYCFragment newInstance(String param1, String param2) {
        KYCFragment fragment = new KYCFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        kycInterface = KYCFragment.this;
        onStaticClickIterface = KYCFragment.this;
        staticInterface = (StaticInterface) KYCFragment.this;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentKYCBinding = FragmentKYCBinding.inflate(inflater, container, false);
        return fragmentKYCBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myProfileViewModel = new ViewModelProvider(requireActivity()).get(MyProfileViewModel.class);
        staticRepository = new StaticRepository(requireActivity(), staticInterface);
        myProfileRepository = new MyProfileRepository(requireActivity(), kycInterface);
        myProfileViewModel.myProfileRepository = myProfileRepository;
        myProfileViewModel.kycInterface = kycInterface;
        myProfileViewModel.staticInterface = staticInterface;
        myProfileViewModel.staticRepository = staticRepository;
        myProfileViewModel.getKYCInfo(new PrefManager(requireContext()).getUserID());
        fragmentKYCBinding.setIsKycDone(new PrefManager(getContext()).getKYCPending());
        getViewLifecycleOwner();

    }

    @Override
    public void onResume() {
        super.onResume();
        fragmentKYCBinding.imgEditKyc.setOnClickListener(v -> update_KYC());
        fragmentKYCBinding.imgEditCompanyInfo.setOnClickListener(v -> updateCompanyDetail());
    }

    @Override
    public void onKYCSuccess(LiveData<KYCResponse> kycResponseLiveData) {
        kycResponseLiveData.observe(this, kycResponse -> {
            if (kycResponse.panNo != null && !kycResponse.panNo.equals("")) {
                fragmentKYCBinding.pbKyc.setVisibility(View.GONE);
                Constant.START_TOUCH(requireActivity());
                fragmentKYCBinding.setKycinfo(kycResponse);
                this.kycResponse = kycResponse;
                myProfileViewModel.getCompanyInfo(new PrefManager(getContext()).getUserID());
            } else {
                Constant.START_TOUCH(requireActivity());
                fragmentKYCBinding.pbKyc.setVisibility(View.GONE);
                this.kycResponse = new KYCResponse();
                myProfileViewModel.getCompanyInfo(new PrefManager(getContext()).getUserID());
            }
        });
    }

    @Override
    public void onUpdateSucess(LiveData<UpdateResponse> updateResponseLiveData) {
        updateResponseLiveData.observe(this, updateResponse -> {
            if (updateResponse.userid.equals(new PrefManager(getContext()).getUserID())) {
                Constant.START_TOUCH(requireActivity());
                fragmentKYCBinding.pbKyc.setVisibility(View.GONE);
                bottomSheetDialog_kyc.dismiss();
                myProfileViewModel.getKYCInfo(updateResponse.userid);
            }
        });
    }


    @Override
    public void onCompanySuccess(LiveData<CompanyReponse> companyReponseLiveData) {
        companyReponseLiveData.observe(this, companyReponse -> {
            if (companyReponse.companyName != null && !companyReponse.companyName.equals("")) {
                fragmentKYCBinding.pbKyc.setVisibility(View.GONE);
                Constant.START_TOUCH(requireActivity());
                fragmentKYCBinding.setCompanyinfo(companyReponse);
                this.companyReponse = companyReponse;
                myProfileViewModel.code = companyReponse.companyType.code;
            } else {
                fragmentKYCBinding.pbKyc.setVisibility(View.GONE);
                Constant.START_TOUCH(requireActivity());
                this.companyReponse = new CompanyReponse();
            }
        });
    }

    @Override
    public void onCompanyUpdateSucess(LiveData<UpdateResponse> updateResponseLiveData) {
        updateResponseLiveData.observe(this, updateResponse -> {
            if (updateResponse.userid.equals(new PrefManager(getContext()).getUserID())) {
                bottomSheetDialog_company.dismiss();
                myProfileViewModel.getCompanyInfo(updateResponse.userid);
                Constant.START_TOUCH(requireActivity());
                fragmentKYCBinding.pbKyc.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onServiceStart() {
        Constant.STOP_TOUCH(requireActivity());
        fragmentKYCBinding.pbKyc.setVisibility(View.VISIBLE);
        is_pb_for_bottom_sheet = false;
    }

    @Override
    public void onFailed(String msg) {
        Constant.START_TOUCH(requireActivity());
        Constant.showToast(getContext(), msg);
        fragmentKYCBinding.pbKyc.setVisibility(View.GONE);
    }

    public void update_KYC() {
        bottomSheetDialog_kyc = new BottomSheetDialog(requireContext());
        updateKycLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.update_kyc_layout, null, false);
        bottomSheetDialog_kyc.setContentView(updateKycLayoutBinding.getRoot());
        updateKycLayoutBinding.setMyprofileviewmodel(myProfileViewModel);
        updateKycLayoutBinding.setKyc(kycResponse);
        myProfileViewModel.userid = new PrefManager(getContext()).getUserID();
        updateKycLayoutBinding.setLifecycleOwner(this);
        bottomSheetDialog_kyc.show();
        updateKycLayoutBinding.imgDismissDialog.setOnClickListener(v -> bottomSheetDialog_kyc.dismiss());
        updateKycLayoutBinding.edtAdditionalId.setOnClickListener(v -> {
            is_pb_for_bottom_sheet = true;
            getAdditionalIds();
        });

        updateKycLayoutBinding.btnPanImage.setOnClickListener(v -> {
            chooseOption();
        });


    }

    private void chooseOption() {
        bottomSheetDialog_chooseOption = new BottomSheetDialog(requireContext());
        chooseoptionBinding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.chooseoption, null, false);
        bottomSheetDialog_chooseOption.setContentView(chooseoptionBinding.getRoot());
        bottomSheetDialog_chooseOption.show();
        chooseoptionBinding.relativeCancel.setOnClickListener(v -> bottomSheetDialog_chooseOption.dismiss());
        chooseoptionBinding.relativeCamera.setOnClickListener(v -> openCameraIntent());
        chooseoptionBinding.relativeGallery.setOnClickListener(v -> {
            bottomSheetDialog_chooseOption.dismiss();
            openGallery();
        });
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        gallery.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        gallery.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        startActivityForResult(gallery, REQUEST_PICK_IMAGE);

    }


    private void openCameraIntent() {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(requireContext().getPackageManager()) != null) {
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Log.e("ok", ex.toString());
            }
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(requireContext(), Constant.MY_CONTENT_PROVIDER, photoFile);
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(pictureIntent, REQUEST_CAPTURE_IMAGE);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = new File(requireContext().getApplicationContext().getExternalFilesDir("") + Constant.FILE_DIR);
        return File.createTempFile(imageFileName,  /* prefix */".jpg",         /* suffix */storageDir      /* directory */);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_PICK_IMAGE) {

                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    String path = getRealPathFromURI(selectedImageUri);
                    Log.e("ok", path.toString());
                }
            }
        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = requireContext().getContentResolver().query(contentURI, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        return cursor.getString(columnIndex);
    }

    private void getAdditionalIds() {
        is_kyc_data_selection = true;
        View view = LayoutInflater.from(requireActivity()).inflate(R.layout.interrestedfor_bottomsheet, null);
        interrestedfor_bottomsheet = new BottomSheetDialog(requireActivity());
        interrestedfor_bottomsheet.setContentView(view);
        interrestedfor_bottomsheet.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressBar_for_bottom_sheet = view.findViewById(R.id.pb_for_bottomsheet);
        progressBar_for_bottom_sheet.setVisibility(View.VISIBLE);
        RecyclerView rv_interested_for = view.findViewById(R.id.rv_interreseted_for);
        rv_interested_for.setLayoutManager(new LinearLayoutManager(requireActivity()));
        ItemOffsetDecoration itemOffsetDecoration = new ItemOffsetDecoration(requireActivity(), R.dimen.margin10dp);
        rv_interested_for.addItemDecoration(itemOffsetDecoration);

        myProfileViewModel.getStaticDataForAdditionalIds(requireContext()).observe(this, interrestedforResponses -> {
            if (!interrestedforResponses.isEmpty()) {
                Constant.START_TOUCH(requireActivity());
                progressBar_for_bottom_sheet.setVisibility(View.GONE);
                InterrestedforAdapter interrestedforAdapter = new InterrestedforAdapter(interrestedforResponses, onStaticClickIterface);
                rv_interested_for.setAdapter(interrestedforAdapter);
                interrestedfor_bottomsheet.show();
            } else {
                InterrestedforAdapter interrestedforAdapter = new InterrestedforAdapter(interrestedforResponses, onStaticClickIterface);
                rv_interested_for.setAdapter(interrestedforAdapter);
                interrestedfor_bottomsheet.show();
            }
        });

    }

    public void updateCompanyDetail() {
        bottomSheetDialog_company = new BottomSheetDialog(requireContext());
        updateBankDetailBinding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.update_company_detail, null, false);
        bottomSheetDialog_company.setContentView(updateBankDetailBinding.getRoot());
        updateBankDetailBinding.setMyprofileviewmodel(myProfileViewModel);
        updateBankDetailBinding.setCompany(companyReponse);
        myProfileViewModel.userid = new PrefManager(getContext()).getUserID();
        updateBankDetailBinding.setLifecycleOwner(this);
        bottomSheetDialog_company.show();
        updateBankDetailBinding.imgDismissDialog.setOnClickListener(v -> bottomSheetDialog_company.dismiss());
        updateBankDetailBinding.edtEditComType.setOnClickListener(v -> {
            is_pb_for_bottom_sheet = true;
            myProfileViewModel.static_value = Constant.COMPANY;
            getCompanyType();
        });
    }

    public void getCompanyType() {
        is_kyc_data_selection = false;
        View view = LayoutInflater.from(requireActivity()).inflate(R.layout.interrestedfor_bottomsheet, null);
        interrestedfor_bottomsheet = new BottomSheetDialog(requireActivity());
        interrestedfor_bottomsheet.setContentView(view);
        interrestedfor_bottomsheet.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressBar_for_bottom_sheet = view.findViewById(R.id.pb_for_bottomsheet);
        progressBar_for_bottom_sheet.setVisibility(View.VISIBLE);
        RecyclerView rv_interested_for = view.findViewById(R.id.rv_interreseted_for);
        rv_interested_for.setLayoutManager(new LinearLayoutManager(requireActivity()));
        ItemOffsetDecoration itemOffsetDecoration = new ItemOffsetDecoration(requireActivity(), R.dimen.margin10dp);
        rv_interested_for.addItemDecoration(itemOffsetDecoration);

        myProfileViewModel.getStaticData().observe(this, interrestedforResponses -> {
            if (!interrestedforResponses.isEmpty()) {
                Constant.START_TOUCH(requireActivity());
                progressBar_for_bottom_sheet.setVisibility(View.GONE);
                InterrestedforAdapter interrestedforAdapter = new InterrestedforAdapter(interrestedforResponses, onStaticClickIterface);
                rv_interested_for.setAdapter(interrestedforAdapter);
                interrestedfor_bottomsheet.show();
            } else {
                progressBar_for_bottom_sheet.setVisibility(View.GONE);
                InterrestedforAdapter interrestedforAdapter = new InterrestedforAdapter(interrestedforResponses, onStaticClickIterface);
                rv_interested_for.setAdapter(interrestedforAdapter);
                interrestedfor_bottomsheet.show();
            }
        });

    }

    @Override
    public void onItemClick(String code, String description) {
        if (!description.equals(getResources().getString(R.string.select))) {
            myProfileViewModel.code = code;
            myProfileViewModel.companytype_description = description;

            if (!is_kyc_data_selection) {
                updateBankDetailBinding.edtEditComType.setText(description);
                if (Constant.PARTNERSHIP.equals(description)) {
                    updateBankDetailBinding.edtEditPartnership.setVisibility(View.VISIBLE);

                    updateBankDetailBinding.edtEditCoi.setVisibility(View.GONE);
                    updateBankDetailBinding.edtEditMoa.setVisibility(View.GONE);
                    updateBankDetailBinding.edtEditDeclration.setVisibility(View.GONE);

                    updateBankDetailBinding.edtEditUdyog.setVisibility(View.GONE);

                } else if (Constant.SOLE_OWNER.equals(description)) {
                    updateBankDetailBinding.edtEditUdyog.setVisibility(View.VISIBLE);

                    updateBankDetailBinding.edtEditPartnership.setVisibility(View.GONE);

                    updateBankDetailBinding.edtEditCoi.setVisibility(View.GONE);
                    updateBankDetailBinding.edtEditMoa.setVisibility(View.GONE);
                    updateBankDetailBinding.edtEditDeclration.setVisibility(View.GONE);

                } else if (Constant.PRIVATE_LIMITED.equals(description)) {
                    updateBankDetailBinding.edtEditCoi.setVisibility(View.VISIBLE);
                    updateBankDetailBinding.edtEditMoa.setVisibility(View.VISIBLE);
                    updateBankDetailBinding.edtEditDeclration.setVisibility(View.VISIBLE);


                    updateBankDetailBinding.edtEditPartnership.setVisibility(View.GONE);
                    updateBankDetailBinding.edtEditUdyog.setVisibility(View.GONE);
                }
            } else {
                updateKycLayoutBinding.edtAdditionalId.setText(description);
            }

            interrestedfor_bottomsheet.dismiss();
        } else {
            Constant.showToast(requireContext(), "Select one option");
        }

    }

    @Override
    public void onStaticStart() {
        Constant.STOP_TOUCH(requireActivity());
        if (is_pb_for_bottom_sheet) {
            progressBar_for_bottom_sheet.setVisibility(View.VISIBLE);
        } else {
            fragmentKYCBinding.pbKyc.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onStaticFailed(String msg) {
        Constant.START_TOUCH(requireActivity());
        Constant.showToast(requireActivity(), msg);
        if (is_pb_for_bottom_sheet) {
            progressBar_for_bottom_sheet.setVisibility(View.GONE);
        } else {
            fragmentKYCBinding.pbKyc.setVisibility(View.GONE);
        }
    }
}