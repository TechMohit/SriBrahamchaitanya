package grossary.cyron.com.grossarybrch.profile;

import com.google.gson.annotations.SerializedName;

/**
 * Created by subham_naik on 08-Oct-18.
 */

public class GetUserProfileUpdateModel {

    @SerializedName("userId")
    public int userid;
    @SerializedName("MobileNumber")
    public String mobilenumber;
    @SerializedName("Response")
    public Response response;

    public  class Response {
        @SerializedName("ResponseVal")
        public boolean responseval;
        @SerializedName("Reason")
        public String reason;
    }
}
