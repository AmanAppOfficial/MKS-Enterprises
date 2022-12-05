package e.hr.mks.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import e.hr.mks.auth.view.SigninFragment
import e.hr.mks.auth.view.SignupFragment
import e.hr.mks.salary.view.SalaryFragment

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeSignInFragmentInjector(): SigninFragment

    @ContributesAndroidInjector
    abstract fun contributeSignUpFragmentInjector(): SignupFragment

    @ContributesAndroidInjector
    abstract fun contributeSalaryFragmentInjector(): SalaryFragment

}