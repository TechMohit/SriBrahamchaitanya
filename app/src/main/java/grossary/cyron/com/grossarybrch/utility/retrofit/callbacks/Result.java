package grossary.cyron.com.grossarybrch.utility.retrofit.callbacks;

import com.google.gson.annotations.SerializedName;

/**
 * Created by subham_naik on 16-Jan-18.
 */

public class Result {

    @SerializedName("ResponseVal")
    public int responseval;

    public int getResponseval() {
        return responseval;
    }

    public String getReason() {
        return reason;
    }

    @SerializedName("Reason")

    public String reason;
}
