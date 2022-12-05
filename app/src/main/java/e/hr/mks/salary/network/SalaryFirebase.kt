package e.hr.mks.salary.network

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import e.hr.mks.R
import e.hr.mks.auth.UserModel
import e.hr.mks.salary.SalaryEventListener
import e.hr.mks.salary.SalaryModel
import javax.inject.Inject


class SalaryFirebase @Inject constructor(){

    companion object{
        private const val USER_REF = "Users"
        private const val SALARY_REF = "Salary"
        private const val AADHAAR_REF = "adhaar"
        private const val EMAIL_REF = "email"
        private const val JOINING_REF = "joining"
        private const val NAME_REF = "name"
        private const val BASIC_REF = "basic"
        private const val INCENTIVE_REF = "incentive"
        private const val SALARY_STORAGE_REF = "salary_slip"
        private const val SALARY_SLIP_EXTENSION = ".pdf"
    }

    private lateinit var salaryEventListener: SalaryEventListener
    private val storageReference  = FirebaseStorage.getInstance()
    private val database = Firebase.database
    private val userRef = database.getReference(USER_REF)
    private val salaryRef = database.getReference(SALARY_REF)
    private val salaryStorageRef = storageReference.getReference(SALARY_STORAGE_REF)
    private val list = ArrayList<SalaryModel>()

    fun setListener(listener: SalaryEventListener){
        salaryEventListener = listener
    }


    fun getSalary(uid: String){
        salaryRef.child(uid).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(cSnapshot in snapshot.children){
                        val userModel = UserModel()
                        userModel.setUid(uid)

                        userRef.child(uid).addListenerForSingleValueEvent(object : ValueEventListener{
                            override fun onDataChange(snapshot: DataSnapshot) {
                                if(snapshot.exists()){
                                    userModel.setAdhaar(snapshot.child(AADHAAR_REF).value.toString())
                                    userModel.setEmail(snapshot.child(EMAIL_REF).value.toString())
                                    userModel.setJoining(snapshot.child(JOINING_REF).value.toString())
                                    userModel.setName(snapshot.child(NAME_REF).value.toString())

                                    userModel.setBasicSalary(cSnapshot.child(BASIC_REF).value.toString())
                                    userModel.setIncentive(cSnapshot.child(INCENTIVE_REF).value.toString())
                                    val salaryModel = SalaryModel(cSnapshot.key.toString(), userModel)
                                    list.add(salaryModel)
                                    salaryEventListener.success(list)
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                salaryEventListener.failed(error.message)
                            }

                        })
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                salaryEventListener.failed(error.message)
            }

        })
    }

    fun downloadSalarySlip(uid: String, context: Context, date: String){
        salaryStorageRef.child(uid).child(SALARY_STORAGE_REF + date +  SALARY_SLIP_EXTENSION).downloadUrl.addOnSuccessListener {
            if(it!=null) {
                val url = it.toString()
                downloadFile(context = context, SALARY_STORAGE_REF + date , SALARY_SLIP_EXTENSION, DIRECTORY_DOWNLOADS, url = url)
            }
            else
                Toast.makeText(context, R.string.error_not_found, Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            Toast.makeText(context, R.string.error_not_found, Toast.LENGTH_SHORT).show()
        }
    }

    private fun downloadFile(
        context: Context,
        fileName: String,
        fileExtension: String,
        destinationDirectory: String,
        url: String
    ) {
        val downloadmanager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val uri = Uri.parse(url)
        val request = DownloadManager.Request(uri)
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalFilesDir(
            context,
            destinationDirectory,
            fileName + fileExtension
        )
        downloadmanager.enqueue(request)
    }
}