package com.example.readcontacts

import android.os.Bundle
import android.provider.ContactsContract
import android.provider.ContactsContract.Contacts
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.readcontacts.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestContacts()
    }

    private fun requestContacts() {
        scope.launch {
            val cursor = contentResolver.query(
                Contacts.CONTENT_URI,
                null,
                null,
                null,
                null,
            )

            while (cursor?.moveToNext() == true) {
                val id = cursor.getInt(
                    cursor.getColumnIndexOrThrow(Contacts._ID)
                )
                val name = cursor.getString(
                    cursor.getColumnIndexOrThrow(Contacts.DISPLAY_NAME)
                )
                val contact = Contact(id, name)
                Log.d("MainActivity", contact.toString())
            }
            cursor?.close()
        }
    }

}