package grossary.cyron.com.grossarybrch.utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import grossary.cyron.com.grossarybrch.account.LoginModel;


public class PreferenceManager {

    private final static String FORTIS_PREFERENCES = "cyron_glossary";
    private static final String COUNT = "count";
    private static final String LOGIN_MODEL = "login_model";
    private static final String AUTO_LOGIN = "auto_login";
    private static final String GRANT_TOTAL = "GRANT_TOTAL";
    private static final String SHIPPING_CHARGE = "SHIPPING_CHARGE";



    private SharedPreferences sharedPreferences;
    private String shippingCharges;

    private PreferenceManager() {

    }

    public PreferenceManager(Context context) {
        this();
        sharedPreferences = context.getApplicationContext().getSharedPreferences(FORTIS_PREFERENCES, Context.MODE_PRIVATE);
    }


    public void setCount( String city) {
        sharedPreferences.edit().putString(COUNT, city).apply();
    }
    public String getCount() {
        String city = sharedPreferences.getString(COUNT, "0");
        return city;
    }
    public void setLoginModel( LoginModel loginModel) {
        sharedPreferences.edit().putString(LOGIN_MODEL, new Gson().toJson(loginModel)).apply();
    }
    public LoginModel getLoginModel() {
        String loginModel = sharedPreferences.getString(LOGIN_MODEL, null);
        return new Gson().fromJson(loginModel,LoginModel.class);
    }
    public void setAutoLogin( boolean login) {
        sharedPreferences.edit().putBoolean(AUTO_LOGIN, login).apply();
    }
    public boolean getAutoLogin() {
        boolean loginModel = sharedPreferences.getBoolean(AUTO_LOGIN, false);
        return loginModel;
    }

    public void setShippingCharges(String shippingCharges) {

        sharedPreferences.edit().putString(SHIPPING_CHARGE, shippingCharges).apply();
        }

    public String getShippingCharges() {
        String shippingCharges = sharedPreferences.getString(SHIPPING_CHARGE, "0");

        return shippingCharges;
    }

    public void setGrandtoal(String grandtoal) {
        sharedPreferences.edit().putString(GRANT_TOTAL, grandtoal).apply();
    }

    public String getGrandtoal() {
        String city = sharedPreferences.getString(GRANT_TOTAL, "0");
        return city;
    }
}
