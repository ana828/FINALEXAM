package com.example.nadiri

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    lateinit var db: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        init()


        saveInfo.setOnClickListener {

            val n: String = inputFullName.text.toString()
            val a: String = inputAddress.text.toString()
            val p: String = inputPhone.text.toString()


            if (TextUtils.isEmpty(n) || TextUtils.isEmpty(a) || TextUtils.isEmpty(p)) {
                Toast.makeText(this, "FILL ALL THE FIELD!", Toast.LENGTH_LONG).show()
            } else {

                contactInfo(n, p, a)
            }

        }



        logoutBtn.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        updatePasswordBtn.setOnClickListener {
            val intent = Intent(this, UpdatePasswordActivity::class.java)
            startActivity(intent)
        }

    }

    private fun init() {

        auth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance().getReference("UserInfo")

        addUserInfoChangeListener()

    }

    private fun contactInfo(name: String, phone: String?, address: String) {
        val userInfo = UserInfo(name, phone, address)
        db.child(auth.currentUser?.uid!!).setValue(userInfo)
    }

    private fun addUserInfoChangeListener() {

        db.child(auth.currentUser?.uid!!)
            .addValueEventListener(object : ValueEventListener {

                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(snap: DataSnapshot) {

                    val userInfo: UserInfo = snap.getValue(UserInfo::class.java) ?: return

                    showFullName.text = userInfo.name
                    showAddress.text = userInfo.address
                    showPhone.text = userInfo.mobile ?: ""

                    inputFullName.setText("")
                    inputPhone.setText("")
                    inputAddress.setText("")

                }

            })

    }


}
