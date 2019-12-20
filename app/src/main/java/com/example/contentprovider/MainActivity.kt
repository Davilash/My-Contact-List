package com.example.contentprovider

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contentprovider.model.ContactModel
import com.example.contentprovider.recyclerView.ContactRecyclerAdapter
import com.example.contentprovider.recyclerView.VerticalSpaceItemDecoration
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkForPermissions()

        getAllContacts()
    }

    private fun checkForPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS), 0)
        else  ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)
    }


    private fun getAllContacts() {
        val contactVOList = arrayListOf<ContactModel>()
        var contactVO: ContactModel

        val contentResolver = contentResolver
        val cursor = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        )
            ?: return

        if (cursor.count > 0) {
            while (cursor.moveToNext()) {

                val hasPhoneNumber =
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)))
                if (hasPhoneNumber > 0) {
                    val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))

                    contactVO =
                        ContactModel("", "")
                    contactVO.contactName = name

                    val phoneCursor = contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        arrayOf(id), null
                    )
                    if (phoneCursor!!.moveToNext()) {
                        val phoneNumber =
                            phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                        contactVO.contactNumber = phoneNumber
                    }

                    phoneCursor.close()
                    contactVOList.add(contactVO)
                }
            }
            cursor.close()

            val contactAdapter =
                ContactRecyclerAdapter(
                    applicationContext,
                    contactVOList
                )
            recycler_view_con.layoutManager = LinearLayoutManager(this)
            val dividerItemDecoration = VerticalSpaceItemDecoration(20)
            recycler_view_con.addItemDecoration(dividerItemDecoration)
            recycler_view_con.adapter = contactAdapter
        }
    }
}
