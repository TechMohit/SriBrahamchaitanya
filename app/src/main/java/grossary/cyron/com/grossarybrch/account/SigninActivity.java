package grossary.cyron.com.grossarybrch.account;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;

import grossary.cyron.com.grossarybrch.HomeActivity;
import grossary.cyron.com.grossarybrch.R;
import grossary.cyron.com.grossarybrch.utility.LoadingView;
import grossary.cyron.com.grossarybrch.utility.PreferenceManager;
import grossary.cyron.com.grossarybrch.utility.retrofit.RetrofitClient;
import grossary.cyron.com.grossarybrch.utility.retrofit.RetrofitRequest;
import grossary.cyron.com.grossarybrch.utility.retrofit.callbacks.Request;
import grossary.cyron.com.grossarybrch.utility.retrofit.callbacks.ResponseListener;
import okhttp3.Headers;
import retrofit2.Call;

import static grossary.cyron.com.grossarybrch.utility.Constant.URL.BASE_URL;

public class SigninActivity extends AppCompatActivity {

    private static final String ACTION_SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private final static int PERMISSION_REQUEST_READ_SMS = 100;
    private SMSReceiver smsReceiver;
    private static final String SMS_ORIGIN_GROSSARY = "PLATRD";
    public static final String OTP_DELIMITER = "OTP is";
    private TextView txt_register;
    private Button btn_login;
    private LoadingView load;
    private EditText etPhone;
    private Dialog dialog;
    private LoginModel response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        txt_register = findViewById(R.id.txt_register);
        etPhone = findViewById(R.id.etPhone);
        btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    callApiAuthenticate();
                }
            }
        });
        txt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etPhone.setText("");
                startActivity(new Intent(SigninActivity.this, SignupActivity.class));
            }
        });

        if (new PreferenceManager(SigninActivity.this).getAutoLogin()) {
            startActivity(new Intent(SigninActivity.this, HomeActivity.class));
            finish();
        }else {
            chaeckPermission();
        }
    }


    private void unregisterSMSReceiver() {
        if (smsReceiver == null)
            return;
        SigninActivity.this.unregisterReceiver(smsReceiver);
    }


    private void registerSMSReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_SMS_RECEIVED);
        intentFilter.setPriority(999);
        SigninActivity.this.registerReceiver(smsReceiver = createReceiver(), intentFilter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_READ_SMS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    registerSMSReceiver();
                } else {
                    // permission denied, boo! Disable the functionality that depends on this permission.
                }
                return;
            }

        }
    }

    private SMSReceiver createReceiver() {
        return new SMSReceiver(new SMSReceiver.OnMessageListener() {
            @Override
            public void onMessage(String from, String text) {
                if (!from.toLowerCase().contains(SMS_ORIGIN_GROSSARY.toLowerCase()))
                    return;
                String code = null;
                int index = text.indexOf(OTP_DELIMITER) + 1;
                if (index != -1 && ((index + 7) < text.length())) {
                    int start = index + OTP_DELIMITER.length();
                    int length = text.length();
                    code = text.substring(start, length);
                    callApiOtpVerify(code, response);

                }
            }
        });
    }


    private void callApiAuthenticate() {
        load = new LoadingView(this);
        load.setCancalabe(false);
        load.showLoading();
        String url = BASE_URL + "/Login/Authenticate";

        Log.e("URl", "*** " + url);
        Call<LoginModel> call = RetrofitClient.getAPIInterface().authenticate(url,
                etPhone.getText().toString());
        Request request = new RetrofitRequest<>(call, new ResponseListener<LoginModel>() {
            @Override
            public void onResponse(int code, LoginModel response, Headers headers) {
                load.dismissLoading();

                if (response.getResponse().getResponseval()) {

                    openDilogotp(response);
                } else {
                    Toast.makeText(SigninActivity.this, "" + response.getResponse().getReason(), Toast.LENGTH_SHORT).show();
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

    private void openDilogotp(final LoginModel response) {

        dialog = new Dialog(SigninActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dilog_otp);
        dialog.setCancelable(false);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        dialog.show();
        final PinEntryEditText pin = dialog.findViewById(R.id.pin);

        this.response = response;

        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        Button btn_retry = dialog.findViewById(R.id.btn_retry);
        btn_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pin.setText("");
                callApiRetry();
            }
        });
        pin.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
            @Override
            public void onPinEntered(CharSequence str) {
                if (str != null && str.length() == 6) {
                    callApiOtpVerify(str.toString(), response);
                }
            }
        });
    }

    private void callApiRetry() {

        load = new LoadingView(this);
        load.setCancalabe(false);
        load.showLoading();
        String url = BASE_URL + "/Login/ResendOTP";

        Log.e("URl", "*** " + url);
        Call<ResendOTPModel> call = RetrofitClient.getAPIInterface().resendOTP(url, etPhone.getText().toString());
        Request request = new RetrofitRequest<>(call, new ResponseListener<ResendOTPModel>() {
            @Override
            public void onResponse(int code, ResendOTPModel response, Headers headers) {
                load.dismissLoading();
                if (response.response.responseval) {
                    Toast.makeText(SigninActivity.this, "OTP send", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SigninActivity.this, "" + response.response.reason, Toast.LENGTH_SHORT).show();
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

    private void callApiOtpVerify(String otp, final LoginModel responseTemp) {
        load = new LoadingView(this);
        load.setCancalabe(false);
        load.showLoading();
        String url = BASE_URL + "/Login/ValidateOTP";

        Log.e("URl", "*** " + url);
        Call<VerifyRegisterOTPModel> call = RetrofitClient.getAPIInterface().verifyRegisterOTP(url, etPhone.getText().toString(), otp);
        Request request = new RetrofitRequest<>(call, new ResponseListener<VerifyRegisterOTPModel>() {
            @Override
            public void onResponse(int code, VerifyRegisterOTPModel response, Headers headers) {
                load.dismissLoading();
                if (response.getResponse().isResponseval()) {
                    dialog.dismiss();
                    new PreferenceManager(SigninActivity.this).setLoginModel(responseTemp);
                    new PreferenceManager(SigninActivity.this).setAutoLogin(true);
                    startActivity(new Intent(SigninActivity.this, HomeActivity.class));
                    finish();

                } else {
                    Toast.makeText(SigninActivity.this, "" + response.response.reason, Toast.LENGTH_SHORT).show();
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

    private boolean validate() {

        if (TextUtils.isEmpty(etPhone.getText().toString())) {
            Toast.makeText(this, "Please Enter Mobile Number", Toast.LENGTH_SHORT).show();
            etPhone.setError("Please Enter Mobile Number");
            return false;
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
//        chaeckPermission();

    }

    private void chaeckPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_SMS)) {


            } else {


                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_SMS},
                        PERMISSION_REQUEST_READ_SMS);

            }
        } else {
            registerSMSReceiver();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterSMSReceiver();

    }

    public  void testgit(){

    }

}
