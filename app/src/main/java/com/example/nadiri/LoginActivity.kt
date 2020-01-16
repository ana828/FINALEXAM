package com.example.nadiri

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
                Toast.makeText(this, "Fill all the field!", Toast.LENGTH_LONG).show()
            } else {

                contactInfo(n, p, a)
            }

        }



        logoutBtn.setOnClickListener {

            val builder = AlertDialog.Builder(this@LoginActivity)
            builder.setTitle("Are you sure!")
            builder.setMessage("Do you want to sign out?")
            builder.setPositiveButton("YES"){dialog, which ->

                auth.signOut()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()

            }


            builder.setNegativeButton("No"){dialog,which ->


            }

            val dialog: AlertDialog = builder.create()

            dialog.show()




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

    private fun contactInfo(name: String, phone: String?, city: String) {
        val userInfo = Information(name, phone, city)
        db.child(auth.currentUser?.uid!!).setValue(userInfo)
    }



    private fun addUserInfoChangeListener() {

        db.child(auth.currentUser?.uid!!)
            .addValueEventListener(object : ValueEventListener {

                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(snap: DataSnapshot) {

                    val userInfo: Information = snap.getValue(Information::class.java) ?: return

                    showFullName.text = "  FULLNAME : " + userInfo.name
                    showAddress.text = "  CITY : " + userInfo.city
                    showPhone.text = "  PHONE : " + userInfo.mobile ?: ""

                    inputFullName.setText("")
                    inputPhone.setText("")
                    inputAddress.setText("")

                }

            })
        deleteBtn.setOnClickListener{

            val db = FirebaseDatabase.getInstance().getReference("UserInfo")
            db.child(auth.currentUser?.uid!!).removeValue()

            showFullName.setText("  FULLNAME : ")
            showAddress.setText("  CITY : ")
            showPhone.setText("  PHONE : ")


        }



    }


}
