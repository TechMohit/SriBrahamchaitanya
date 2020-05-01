package grossary.cyron.com.grossarybrch.utility.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Errors {
    @SerializedName("errors")
    private List<Error> errors;


    public Errors() {
    }

    public List<Error> getErrors() {
        return errors;
    }

    public Errors(List<Error> errors) {
        this.errors = errors;
    }

    public Error getError() {
        return errors.get(0);
    }

    public static class Error {
        @SerializedName("message")
        private String message;
        @SerializedName("source")
        private String source;

        private Error() {
        }

        public Error(String message, String source) {
            this.message = message;
            this.source = source;
        }

        public String getMessage() {
            return message;
        }

        public String getSource() {
            return source;
        }
    }
}
