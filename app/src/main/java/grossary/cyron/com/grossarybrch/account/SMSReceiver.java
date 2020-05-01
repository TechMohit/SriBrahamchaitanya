package grossary.cyron.com.grossarybrch.account;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;



public class SMSReceiver extends BroadcastReceiver {
    private static final String SMS_EXTRA_NAME = "pdus";
    private final OnMessageListener onMessageListener;

    public interface OnMessageListener {
        void onMessage(String from, String message);
    }

    public SMSReceiver(OnMessageListener onMessageListener) {
        this.onMessageListener = onMessageListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras == null)
            return;
        Object[] smsExtra = (Object[]) extras.get(SMS_EXTRA_NAME);

        if (smsExtra != null) {
            for (int i = 0; i < smsExtra.length; ++i) {
                SmsMessage sms = getMessage(smsExtra[i], extras);
                String body = sms.getMessageBody();
                String address = sms.getOriginatingAddress();
                onMessageListener.onMessage(address, body);
            }
        }

    }

    @SuppressWarnings("deprecation")
    private SmsMessage getMessage(Object aObject, Bundle bundle) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String format = bundle.getString("format");
            return SmsMessage.createFromPdu((byte[]) aObject, format);
        } else {
            return SmsMessage.createFromPdu((byte[]) aObject);
        }
    }
}
