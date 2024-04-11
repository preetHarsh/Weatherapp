import android.app.ProgressDialog
import android.content.Context

}

    private fun signInUser() {
        val email = editTextEmail.text.toString().trim()
        val password = editTextPassword.text.toString().trim()

        progressDialog.show()

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                progressDialog.dismiss()
                if (task.isSuccessful) {
                    val user = mAuth.currentUserpackage com.weather

                            import android.app.ProgressDialog
                            import android.content.Context
                            import android.content.Intent
                            import android.content.SharedPreferences
                            import android.os.Bundle
                            import android.view.View
                            import android.view.inputmethod.InputMethodManager
                            import android.widget.Button
                            import android.widget.EditText
                            import android.widget.Toast
                            import androidx.appcompat.app.AppCompatActivity
                            import com.google.firebase.auth.FirebaseAuth

                    class LogIn : AppCompatActivity() {
                        private lateinit var mAuth: FirebaseAuth
                        private lateinit var editTextEmail: EditText
                        private lateinit var editTextPassword: EditText
                        private lateinit var signInButton: Button
                        private lateinit var progressDialog: ProgressDialog
                        private lateinit var sharedPreferences: SharedPreferences

                        override fun onCreate(savedInstanceState: Bundle?) {
                            super.onCreate(savedInstanceState)
                            setContentView(R.layout.activity_log_in)

                            mAuth = FirebaseAuth.getInstance()
                            editTextEmail = findViewById(R.id.editTextEmail)
                            editTextPassword = findViewById(R.id.editTextPassword)
                            signInButton = findViewById(R.id.buttonSignIn)
                            sharedPreferences = getSharedPreferences("user_pref", Context.MODE_PRIVATE)

                            progressDialog = ProgressDialog(this)
                            progressDialog.setMessage("Logging in...")

                            signInButton.setOnClickListener { signInUser() }
                    if (user != null) {
                        val userId = user.uid
                        saveUserId(userId)
                        startActivity(Intent(this@LogIn, Home::class.java))
                        finish()
                    }
                } else {
                    // Dismiss the keyboard
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(editTextPassword.windowToken, 0)

                    Toast.makeText(this@LogIn, "Authentication failed.",
                        Toast.LENGTH_LONG).show()
                }
            }
    }

    fun register(view: View) {
        startActivity(Intent(this@LogIn, Register::class.java))
        finish()
    }

    private fun saveUserId(userId: String) {
        val editor = sharedPreferences.edit()
        editor.putString("user_id", userId)
        editor.apply()
    }
}
