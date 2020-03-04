package com.ore.contactapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ore.loginsignupui.R
import kotlinx.android.synthetic.main.contact_scrolling.*


class ContactActivity : AppCompatActivity() {
    lateinit var adapter: ContactsAdapter
    val contacts = ArrayList<Contact>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contact_scrolling)
        setSupportActionBar(toolbarAll)
        toolbarAll.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_24dp)
        toolbarAll.setNavigationOnClickListener {
            super.onBackPressed()
        }

        contacts.sortBy {
            it.name
        }

        addNewContactButton.setOnClickListener {
            val intent = Intent(this, MainBabyBlissLoginUIActivity::class.java)
//            startActivityForResult(intent, 1)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        showStuff()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val item = menu.findItem(R.id.menu1)
        item.isVisible = false
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.preferences) {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showStuff() {

        adapter = ContactsAdapter(contacts, this)
        recyclerView.adapter = adapter


        var a = recyclerView.adapter
        contacts.clear()

        /** *************START OF FIREBASE************* **/
        val fireBaseDB = FirebaseDatabase.getInstance()
        val dBRef = fireBaseDB.getReference("Contacts")
        // READ FROM DB

        dBRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (snap in snapshot.children) {
                    val value = snap.getValue(Contact::class.java)
                    contacts.add(value!!)
                    adapter.notifyDataSetChanged()
                    // value?.let { contacts.add(it) }
                    Log.e("TAG", contacts.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("VALUE", error.toException())
            }
        })
        /** *************END OF FIREBASE************* **/
    }
}

