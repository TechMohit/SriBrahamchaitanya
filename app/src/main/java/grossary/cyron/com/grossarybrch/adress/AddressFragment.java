package grossary.cyron.com.grossarybrch.adress;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import grossary.cyron.com.grossarybrch.R;
import grossary.cyron.com.grossarybrch.account.LoginModel;
import grossary.cyron.com.grossarybrch.category.CategoryActivity;
import grossary.cyron.com.grossarybrch.payment.PaymentGatewayRequestModel;
import grossary.cyron.com.grossarybrch.payment.SubmitTransactionModel;
import grossary.cyron.com.grossarybrch.profile.GetUserProfileModel;
import grossary.cyron.com.grossarybrch.utility.LoadingView;
import grossary.cyron.com.grossarybrch.utility.PreferenceManager;
import grossary.cyron.com.grossarybrch.utility.retrofit.RetrofitClient;
import grossary.cyron.com.grossarybrch.utility.retrofit.RetrofitRequest;
import grossary.cyron.com.grossarybrch.utility.retrofit.callbacks.Request;
import grossary.cyron.com.grossarybrch.utility.retrofit.callbacks.ResponseListener;
import okhttp3.Headers;
import retrofit2.Call;

import static grossary.cyron.com.grossarybrch.utility.Constant.CONSTANT.MAKE_PAYMENT;
import static grossary.cyron.com.grossarybrch.utility.Constant.CONSTANT.MAKE_PAYMENT_ONLINE;
import static grossary.cyron.com.grossarybrch.utility.Constant.URL.BASE_URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddressFragment extends Fragment {


    private LoadingView load;
    private TextInputEditText etUserName, etAddress, etCity, etState, etZip, etPhone;
    private RadioButton rdOnline, rdCash;
    private Context context;
    private CompoundButton.OnCheckedChangeListener rdOnlineListener, rdCashListener;

    public AddressFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_address, container, false);
        ((CategoryActivity) getActivity()).txtCheckout.setText(MAKE_PAYMENT);
        etUserName = view.findViewById(R.id.etUserName);
        etAddress = view.findViewById(R.id.etAddress);
        etCity = view.findViewById(R.id.etCity);
        etState = view.findViewById(R.id.etState);
        etZip = view.findViewById(R.id.etZip);
        etPhone = view.findViewById(R.id.etPhone);
        rdCash = view.findViewById(R.id.rdCash);
        rdOnline = view.findViewById(R.id.rdOnline);
        callApiProfile();

        rdCashListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                rdOnline.setOnCheckedChangeListener(null);
                if (isChecked) {
                    rdOnline.setChecked(false);
                } else {
                    rdOnline.setChecked(true);

                }
                rdOnline.setOnCheckedChangeListener(rdOnlineListener);
            }
        };

        rdOnlineListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                rdCash.removeOnLayoutChangeListener(null);
                if (isChecked) {
                    rdCash.setChecked(false);
                } else {
                    rdCash.setChecked(true);

                }
                rdCash.setOnCheckedChangeListener(rdCashListener);

            }
        };
        rdCash.setOnCheckedChangeListener(rdCashListener);
        rdOnline.setOnCheckedChangeListener(rdOnlineListener);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void callApiProfile() {
        load = new LoadingView(getActivity());
        load.setCancalabe(false);
        load.showLoading();
        String url = BASE_URL + "/Profile/GetUserProfile";
        final LoginModel res = new PreferenceManager(getActivity()).getLoginModel();

        Log.e("URl", "*** " + url);
        Call<GetUserProfileModel> call = RetrofitClient.getAPIInterface().getUserProfile(url, "" + res.getUserid());
        Request request = new RetrofitRequest<>(call, new ResponseListener<GetUserProfileModel>() {
            @Override
            public void onResponse(int code, GetUserProfileModel response, Headers headers) {
                load.dismissLoading();

                if (response.getResponse().getResponseval()) {

                    etUserName.setText("" + response.getFullname());
                    etAddress.setText("" + response.getAddress());
                    etCity.setText("" + response.getCity());
                    etState.setText("" + response.getState());
                    etZip.setText("" + response.getZipcode());
                    etPhone.setText("" + response.getMobileno());

                } else {
                    Toast.makeText(getActivity(), "" + response.getResponse().getReason(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onError(int error) {
                load.dismissLoading();

            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e("respond", "failure ---->");
                load.dismissLoading();
            }
        });
        request.enqueue();
    }

    public void callApiSubmitTransaction() {
        String fullName = etUserName.getText().toString();
        String address = etAddress.getText().toString();
        String city = etCity.getText().toString();
        String state = etState.getText().toString();
        String zipcode = etZip.getText().toString();
        String phone = etPhone.getText().toString();
        String paymode = MAKE_PAYMENT_ONLINE;
        String shippinfCharge = new PreferenceManager(getActivity()).getShippingCharges();
        String totalCharge = new PreferenceManager(getActivity()).getGrandtoal();


        if (etUserName.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(context, "Enter Full Name", Toast.LENGTH_SHORT).show();
        } else if (etAddress.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(context, "Enter Address", Toast.LENGTH_SHORT).show();
        } else if (etCity.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(context, "Enter City", Toast.LENGTH_SHORT).show();
        } else if (etState.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(context, "Enter State", Toast.LENGTH_SHORT).show();
        } else if (etZip.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(context, "Enter Zip Code", Toast.LENGTH_SHORT).show();
        } else if (etPhone.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(context, "Enter Phone", Toast.LENGTH_SHORT).show();
        }else if (rdCash.isChecked()) {

            load = new LoadingView(getActivity());
            load.setCancalabe(false);
            load.showLoading();

            String url = BASE_URL + "/Home/SubmitTransaction";

            Log.e("URl", "*** " + url);
            final LoginModel res = new PreferenceManager(getActivity()).getLoginModel();
            Call<SubmitTransactionModel> call = RetrofitClient.getAPIInterface().submitTransaction(url, fullName, address, city, state, zipcode, phone
                    , "" + res.getUserid(), paymode, shippinfCharge, totalCharge);
            Request request = new RetrofitRequest<>(call, new ResponseListener<SubmitTransactionModel>() {
                @Override
                public void onResponse(int code, SubmitTransactionModel response, Headers headers) {
                    load.dismissLoading();
                    if (response.getResponse().getResponseval()) {
                        Toast.makeText(getActivity(), "" + response.getResponse().getReason(), Toast.LENGTH_SHORT).show();
                        new PreferenceManager(getActivity()).setShippingCharges("0");
                        new PreferenceManager(getActivity()).setGrandtoal("0");
                        new PreferenceManager(getActivity()).setCount("0");
                        showDilogSucess(response);

                    } else {
                        Toast.makeText(getActivity(), "" + response.getResponse().getReason(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(int error) {
                    load.dismissLoading();

                }

                @Override
                public void onFailure(Throwable throwable) {
                    Log.e("respond", "failure ---->");
                    load.dismissLoading();
                }
            });
            request.enqueue();
        } else {

            load = new LoadingView(getActivity());
            load.setCancalabe(false);
            load.showLoading();

            String url = BASE_URL + "/Payment/PaymentGatewayRequest";

            Log.e("URl", "*** " + url);
            final LoginModel res = new PreferenceManager(getActivity()).getLoginModel();
            Call<PaymentGatewayRequestModel> call = RetrofitClient.getAPIInterface().paymentGatewayRequest(url, fullName, address, city, state, zipcode, phone
                    , "" + res.getUserid(), paymode, shippinfCharge, totalCharge);
            Request request = new RetrofitRequest<>(call, new ResponseListener<PaymentGatewayRequestModel>() {
                @Override
                public void onResponse(int code, PaymentGatewayRequestModel response, Headers headers) {
                    load.dismissLoading();
                    if (response.getResponse().getResponseVal()) {

                        String tempurl=response.getGatewayURL();
                        openDilog(tempurl, getActivity());

                    } else {
                        Toast.makeText(getActivity(), "" + response.getResponse().getReason(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(int error) {
                    load.dismissLoading();

                }

                @Override
                public void onFailure(Throwable throwable) {
                    Log.e("respond", "failure ---->");
                    load.dismissLoading();
                }
            });
            request.enqueue();
        }
    }
//    private class WebClient extends WebViewClient {
//
//        @Override
//        public void onPageStarted(WebView view, String url, Bitmap favicon) {
//            binding.progressBar.setVisibility(View.VISIBLE);
//            super.onPageStarted(view, url, favicon);
//        }
//
//        @Override
//        public void onPageFinished(WebView view, String url) {
//            binding.progressBar.setVisibility(View.GONE);
//            super.onPageFinished(view, url);
//        }
//
//        @SuppressWarnings("deprecation")
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            return isPaymentConfirm(url);
//        }
//
//        @TargetApi(Build.VERSION_CODES.N)
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//            final Uri uri = request.getUrl();
//            return isPaymentConfirm(uri.toString());
//        }
//
//    }
//
//    private boolean isPaymentConfirm(String url) {
//        Uri paymentUri = Uri.parse(url);
//        if (paymentUri.toString().contains("thank-you")) {
//            if (getActivity() != null)
//                ((AppointmentListener) getActivity()).onShowMyAppointments();
//            return true;
//        } else {
//            return false;
//        }
//    }

    private void showDilogSucess(SubmitTransactionModel response) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dilog_payment_sucess);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        dialog.setCancelable(false);
        dialog.show();

        Button btnClose=dialog.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                getActivity().finish();
            }
        });

        TextView txtTotalAmount=dialog.findViewById(R.id.txtTotalAmount);
        txtTotalAmount.setText("Total Amount : ₹"+response.getTotalamount());
        TextView txtTranDate=dialog.findViewById(R.id.txtTranDate);
        txtTranDate.setText("Transation Date : "+response.getTransactiondate());
        TextView txtTransNo=dialog.findViewById(R.id.txtTransNo);
        txtTransNo.setText("Order Number : "+response.getTranno());


    }

    public void openDilog(String url, final Context context) {

        Log.e("URl", "***Dilog " + url);

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dilog_web);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        dialog.setCancelable(false);
        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        ImageView img_close = dialog.findViewById(R.id.img_close);

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        WebView webView = dialog.findViewById(R.id.webview);
        final ProgressDialog progressDialog = ProgressDialog.show(context, "", "Loading...", true);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setAllowFileAccess(true);
        webView.loadUrl(url);

        webView.setWebChromeClient(new WebChromeClient() {
        });

        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            public void onPageFinished(WebView view, String url) {
                progressDialog.dismiss();
                dialog.show();
                //Toast.makeText(context, "Page Load Finished", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                progressDialog.dismiss();
                dialog.show();
            }
        });


    }


}
