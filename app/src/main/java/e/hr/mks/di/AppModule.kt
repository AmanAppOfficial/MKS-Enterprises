package e.hr.mks.di

import dagger.Module
import dagger.Provides
import e.hr.mks.salary.network.SalaryFirebase

@Module
class AppModule {

    @Provides
    fun getSalaryFirebase(): SalaryFirebase{
        return SalaryFirebase()
    }

}