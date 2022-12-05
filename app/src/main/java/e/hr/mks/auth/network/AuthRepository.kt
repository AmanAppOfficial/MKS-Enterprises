package e.hr.mks.auth.network

import android.app.Activity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import e.hr.mks.auth.AuthListener
import javax.inject.Inject

class AuthRepository @Inject constructor() {

    private var auth: FirebaseAuth = Firebase.auth
    private lateinit var authListener: AuthListener


    fun setListener(listener: AuthListener){
        authListener = listener
    }
    fun getCurrUser(): FirebaseUser?{
        return auth.currentUser
    }

    fun signInWithEmail(email: String, password: String, context: Activity){
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(context){task->
                if(task.isSuccessful)
                    authListener.success()
                else
                    authListener.failed(task.exception?.message.toString())
            }
    }

    fun signUpWithEmail(email: String, password: String, context: Activity){
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(context) { task ->
                if (task.isSuccessful) {
                    authListener.success()
                    auth.signOut()
                } else {
                    authListener.failed(task.exception?.message.toString())
                }
            }
    }
}