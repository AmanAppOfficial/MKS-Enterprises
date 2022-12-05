package e.hr.mks.auth.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import dagger.android.support.AndroidSupportInjection
import e.hr.mks.R
import e.hr.mks.auth.AuthListener
import e.hr.mks.auth.network.AuthRepository
import e.hr.mks.databinding.FragmentSigninBinding
import e.hr.mks.utils.AppConstants
import e.hr.mks.utils.NavigationHelper
import javax.inject.Inject

class SigninFragment : Fragment(), AuthListener {

    private lateinit var binding: FragmentSigninBinding

    @Inject
    lateinit var authRepository: AuthRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AndroidSupportInjection.inject(this)
        binding =  FragmentSigninBinding.inflate(inflater, container, false)
        setUpListeners()
        return binding.root
    }


    private fun setUpListeners() {

        authRepository.setListener(this)

        binding.createEmailText.setOnClickListener{
            NavigationHelper.navigateToDest(context = requireActivity(),
                navFragId = R.id.auth_nav_host_fragment,
                to = R.id.action_signinFragment_to_signupFragment)
        }

        binding.loginBtn.setOnClickListener {
            login()
        }
    }

    private fun login() {
        if(binding.emailInputEdittext.text.isNullOrBlank() || binding.passwordInputEdittext.text.isNullOrBlank()){
            Toast.makeText(context, R.string.error_empty_fields, Toast.LENGTH_SHORT).show()
            return
        }
        authRepository.signInWithEmail(binding.emailInputEdittext.text.toString(),binding.passwordInputEdittext.text.toString(),requireActivity())
    }

    override fun success() {
        NavigationHelper.moveToNextScreen(AppConstants.AUTH_SCREEN, AppConstants.HOME_SCREEN, requireActivity())
        requireActivity().finish()
    }

    override fun failed(msg: String) {
        Toast.makeText(context,"${R.string.failed_msg} $msg",Toast.LENGTH_LONG).show()
    }

}