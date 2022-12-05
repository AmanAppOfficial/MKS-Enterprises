package e.hr.mks.salary

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import e.hr.mks.salary.network.SalaryFirebase
import e.hr.mks.utils.CacheUtility
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.lang.reflect.Type
import javax.inject.Inject


class SalaryViewModel @Inject constructor(var salaryFirebase: SalaryFirebase): ViewModel(), SalaryEventListener {


    val salaryLiveData = MutableLiveData<ArrayList<SalaryModel>>()
    private lateinit var cacheUtility: CacheUtility
    private val gson = Gson()

    init {
        salaryFirebase.setListener(this)
    }

    fun getSalaryData(context: Context, uid: String){
        CoroutineScope(IO).launch {
            var salary: ArrayList<SalaryModel>
            val gson =  Gson()

            /** check if cache exists load from cache
             * else load from network
             */
            cacheUtility = CacheUtility(context)
            val file = cacheUtility.getCacheFile(cacheUtility.salaryCacheFileName)
            if(file!!.exists()){
                val cachedData = cacheUtility.readFileContents(cacheUtility.salaryCacheFileName)
                val type: Type = object : TypeToken<ArrayList<SalaryModel?>?>() {}.type
                val list: ArrayList<SalaryModel> = gson.fromJson(cachedData, type)
                salary = list
                salaryLiveData.postValue(salary)
            }
            else{
                salaryFirebase.getSalary(uid)
            }
        }
    }

    fun downloadSlip(context: Context, uid: String, date: String){
        CoroutineScope(IO).launch{
            salaryFirebase.downloadSalarySlip(uid, context, date)
        }
    }

    override fun success(list: ArrayList<SalaryModel>) {
        cacheUtility.createCacheFile(cacheUtility.salaryCacheFileName)
        cacheUtility.writeFileContents(cacheUtility.salaryCacheFileName, gson.toJson(list))
        salaryLiveData.postValue(list)

    }

    override fun failed(msg: String) {
        Log.e("error",  msg)
        salaryLiveData.postValue(ArrayList())
    }


}