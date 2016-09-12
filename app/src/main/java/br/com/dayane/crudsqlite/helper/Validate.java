package br.com.dayane.crudsqlite.helper;

import android.widget.EditText;

/**
 * Created by Dayane on 10/09/2016.
 */
public class Validate {
    private static final String REGEX_PHONE = "\\d{3}-\\d{7}";
    private static final String MSG_REQUIRED = "obrigatório";

    public static boolean required(EditText editText) {
        String text = editText.getText().toString().trim();
        editText.setError(null);
        // length 0 means there is no text
        if (text.length() == 0) {
            editText.setError(MSG_REQUIRED);
            return false;
        }
        return true;
    }
}
