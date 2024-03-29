package im.gian.tr.login.signup

import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignupViewModel : ViewModel() {
    private val auth = Firebase.auth
    private val db = Firebase.firestore

    fun signupUser(email: String, password: String, onCompleteListener: OnCompleteListener<AuthResult>) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(onCompleteListener)
    }

    fun setupUser(onCompleteListener: OnCompleteListener<Void>) {
        val userData = hashMapOf(
            "type" to "user",
            "saved" to arrayListOf<String>()
        )

        db.collection("users").document(auth.uid.toString()).set(userData).addOnCompleteListener(onCompleteListener)
    }
}