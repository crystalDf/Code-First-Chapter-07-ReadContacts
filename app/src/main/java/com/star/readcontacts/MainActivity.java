package com.star.readcontacts;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ListView mContactsView;
    private ArrayAdapter<String> mAdapter;
    private List<String> mContactsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContactsView = (ListView) findViewById(R.id.contacts_view);

        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mContactsList);

        mContactsView.setAdapter(mAdapter);

        readContacts();

    }

    private void readContacts() {

        Cursor cursor = null;

        try {
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, null, null, null);

            while (cursor.moveToNext()) {
                String displayName = cursor.getString(cursor.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                ));

                String number = cursor.getString(cursor.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER
                ));

                mContactsList.add(displayName + "\n" + number);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }

}
