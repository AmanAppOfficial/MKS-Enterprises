package e.hr.mks.utils

import android.content.Context
import android.util.Log
import java.io.*
import java.nio.charset.StandardCharsets

class CacheUtility(private val context: Context){

    val salaryCacheFileName = "SalaryCacheFile"

    fun createCacheFile(fileName: String): Boolean{
        try{
            val file = getCacheFile(fileName)
            if(!file!!.exists()){
                 file.createNewFile()
                 return true
            }
        }
        catch (e: FileNotFoundException){
            Log.e("exception" , e.message.toString())
            return false
        }
        catch (e: IOException){
            Log.e("exception" , e.message.toString())
            return false
        }
        return false
    }

     fun getCacheFile(fileName: String): File?{
        return try {
                return File(context.cacheDir, fileName)
        } catch (e: FileNotFoundException){
            Log.e("exception", e.message.toString())
            null
        }
    }

    fun readFileContents(fileName: String): String{
        var  contents = ""
        val fis: FileInputStream = context.openFileInput(fileName)
        val inputStreamReader = InputStreamReader(fis, StandardCharsets.UTF_8)
        val stringBuilder = StringBuilder()
        try {
            BufferedReader(inputStreamReader).use { reader ->
                var line = reader.readLine()
                while (line != null) {
                    stringBuilder.append(line).append('\n')
                    line = reader.readLine()
                }
            }
        } catch (e: IOException) {
        } finally {
            contents = stringBuilder.toString()
            return contents
        }
    }

    fun writeFileContents(fileName: String, content: String){
        context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
            it.write(content.toByteArray())
        }
    }

    fun deleteCacheFile(fileName: String): Boolean{
        if(getCacheFile(fileName)!!.exists()) {
            return try{
                getCacheFile(fileName)!!.delete()
                true
            } catch (e: Exception){
                false
            }
        }
        return false
    }

}