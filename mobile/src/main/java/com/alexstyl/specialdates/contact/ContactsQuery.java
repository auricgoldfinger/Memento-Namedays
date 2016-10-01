package com.alexstyl.specialdates.contact;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
class ContactsQuery {
    public final static Uri CONTENT_URI = ContactsContract.Data.CONTENT_URI;

    public static String COL_DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY;

    public static String COL_LOOKUP = ContactsContract.Contacts.LOOKUP_KEY;

    @SuppressLint("InlinedApi")
    public final static String SORT_ORDER = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY;

    @SuppressLint("InlinedApi")
    public static final String[] PROJECTION = {
            ContactsContract.Data.MIMETYPE,//0
            ContactsContract.Data.CONTACT_ID,//1
            COL_LOOKUP, //2
            COL_DISPLAY_NAME,//3
            ContactsContract.CommonDataKinds.Event.START_DATE, //4
            ContactsContract.CommonDataKinds.Event.LABEL, //5
            ContactsContract.CommonDataKinds.Event.TYPE, // 6
    };

    public static final int ROW_TYPE = 0;

    public static final int ID = 1;
    public static final int LOOKUP_KEY = 2;
    public static final int DISPLAY_NAME = 3;
    public static final int BIRTHDAY = 4;
    public static final int EVENT_LABEL = 5;
    public static final int EVENT_TYPE = 6;

    public static boolean isBirthdayRow(Cursor cursor) {
        return cursor.getString(ROW_TYPE).equals(ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE)
                && cursor.getInt(EVENT_TYPE) == ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY;
    }
}
