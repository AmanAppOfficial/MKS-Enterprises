package e.hr.mks.utils.Workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import e.hr.mks.utils.CacheUtility

class SalaryCacheExpirationWorker(appContext: Context, workerParams: WorkerParameters): Worker(appContext, workerParams) {
    override fun doWork(): Result {
        val cacheUtility = CacheUtility(applicationContext)
        cacheUtility.deleteCacheFile(cacheUtility.salaryCacheFileName)
        return Result.success()
    }
}