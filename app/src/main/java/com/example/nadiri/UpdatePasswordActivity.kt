package com.example.nadiri

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_update_password.*

class UpdatePasswordActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_password)

        auth = FirebaseAuth.getInstance()


    backBtn.setOnClickListener {
        finish()
    }

    changePassword.setOnClickListener {

        val password: String = password.text.toString()

        if (TextUtils.isEmpty(password)) {

            Toast.makeText(this, "Please enter the password", Toast.LENGTH_LONG).show()

        } else {

            auth.currentUser?.updatePassword(password)
                ?.addOnCompleteListener(this, OnCompleteListener { task ->
                    if (task.isSuccessful) {

                        Toast.makeText(this, "Successfully changed", Toast.LENGTH_LONG)
                            .show()
                        finish()

                    } else {

                        Toast.makeText(this, "Unable to change", Toast.LENGTH_LONG)
                            .show()

                    }
                })

        }

    }

}


}
