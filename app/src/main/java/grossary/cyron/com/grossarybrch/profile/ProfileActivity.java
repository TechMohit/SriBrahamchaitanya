package grossary.cyron.com.grossarybrch.profile;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import grossary.cyron.com.grossarybrch.R;
import grossary.cyron.com.grossarybrch.account.LoginModel;
import grossary.cyron.com.grossarybrch.utility.LoadingView;
import grossary.cyron.com.grossarybrch.utility.PreferenceManager;
import grossary.cyron.com.grossarybrch.utility.retrofit.RetrofitClient;
import grossary.cyron.com.grossarybrch.utility.retrofit.RetrofitRequest;
import grossary.cyron.com.grossarybrch.utility.retrofit.callbacks.Request;
import grossary.cyron.com.grossarybrch.utility.retrofit.callbacks.ResponseListener;
import okhttp3.Headers;
import retrofit2.Call;

import static grossary.cyron.com.grossarybrch.utility.Constant.URL.BASE_URL;

public class ProfileActivity extends AppCompatActivity {

    private Button btnUpdate;
    private LoadingView load;
    private TextInputEditText etName, etMobile, etEmail, etAddress, etGst,etZip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        etName = findViewById(R.id.etName);
        etMobile = findViewById(R.id.etMobile);
        etEmail = findViewById(R.id.etEmail);
        etAddress = findViewById(R.id.etAddress);
        etGst = findViewById(R.id.etGst);
        etZip = findViewById(R.id.etZip);
        btnUpdate = findViewById(R.id.btnUpdate);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validation()) {
                    callApiProfileUpdate();
                }
            }
        });
        callApiProfile();
    }

    private void callApiProfileUpdate() {
        load = new LoadingView(this);
        load.setCancalabe(false);
        load.showLoading();
        String url = BASE_URL + "/Profile/UpdateProfile";
        LoginModel res = new PreferenceManager(ProfileActivity.this).getLoginModel();

        Log.e("URl", "*** " + url);

        Call<GetUserProfileUpdateModel> call = RetrofitClient.getAPIInterface().getUserProfileUpdate(url,
                "" + res.getUserid(), "" + res.getLoginid(), ""+etName.getText().toString(),
                ""+etMobile.getText().toString(),""+etEmail.getText().toString(), ""+etAddress.getText().toString(),
                ""+etGst.getText().toString(),""+etZip.getText().toString());
        Request request = new RetrofitRequest<>(call, new ResponseListener<GetUserProfileUpdateModel>() {
            @Override
            public void onResponse(int code, GetUserProfileUpdateModel response, Headers headers) {
                load.dismissLoading();

                if (response.response.responseval) {
                    Toast.makeText(ProfileActivity.this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                    callApiProfile();

                } else {
                    Toast.makeText(ProfileActivity.this, "" + response.response.reason, Toast.LENGTH_SHORT).show();
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

    private void callApiProfile() {
        load = new LoadingView(this);
        load.setCancalabe(false);
        load.showLoading();
        String url = BASE_URL + "/Profile/GetUserProfile";
        LoginModel res = new PreferenceManager(ProfileActivity.this).getLoginModel();

        Log.e("URl", "*** " + url);
        Call<GetUserProfileModel> call = RetrofitClient.getAPIInterface().getUserProfile(url, "" + res.getUserid());
        Request request = new RetrofitRequest<>(call, new ResponseListener<GetUserProfileModel>() {
            @Override
            public void onResponse(int code, GetUserProfileModel response, Headers headers) {
                load.dismissLoading();

                if (response.getResponse().getResponseval()) {

                    etName.setText("" + response.getFullname());
                    etMobile.setText("" + response.getMobileno());
                    etEmail.setText("" + response.getEmail());
                    etAddress.setText("" + response.getAddress());
                    etGst.setText("" + response.getGstnumber());
                    etZip.setText(""+response.getZipcode());

                } else {
                    Toast.makeText(ProfileActivity.this, "" + response.getResponse().getReason(), Toast.LENGTH_SHORT).show();
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

    private boolean validation() {

        if (TextUtils.isEmpty(etName.getText().toString())) {
            Toast.makeText(this, "Please Enter User Name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(etEmail.getText().toString())) {
            Toast.makeText(this, "Please Enter Emal-id", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(etAddress.getText().toString())) {
            Toast.makeText(this, "Please Enter address", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(etMobile.getText().toString())) {
            Toast.makeText(this, "Please Enter Mobile Number", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etMobile.getText().toString().length() < 10) {
            Toast.makeText(this, "Please Enter Valid Mobile Number", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString().trim()).matches()) {
            Toast.makeText(this, "Invalid email Id", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(etZip.getText().toString())) {
            Toast.makeText(this, "Please Enter ZipCode", Toast.LENGTH_SHORT).show();
            return false;
        }
       /* else if (TextUtils.isEmpty(etGst.getText().toString())) {
            Toast.makeText(this, "Please Enter GST Number", Toast.LENGTH_SHORT).show();
            return false;
        }
*/
        return true;
    }

}
