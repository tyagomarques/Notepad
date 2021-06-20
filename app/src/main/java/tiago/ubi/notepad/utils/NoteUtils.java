package tiago.ubi.notepad.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class NoteUtils {
    public static String dateFromLong(long time){
        DateFormat format = new SimpleDateFormat("EEEE, dd MMM yyy 'at' hh:mm aaa", Locale.getDefault());
        return format.format(new Date(time));
    }
    public static String hashPassword(String password){
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    public static Boolean verifyPassword(String password, String bcryptHashString){
        return BCrypt.verifyer().verify(password.toCharArray(), bcryptHashString).verified;
    }
}
