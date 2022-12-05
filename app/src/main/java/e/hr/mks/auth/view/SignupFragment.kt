package e.hr.mks.auth.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import dagger.android.support.AndroidSupportInjection
import e.hr.mks.R
import e.hr.mks.auth.AuthListener
import e.hr.mks.auth.network.AuthRepository
import e.hr.mks.databinding.FragmentSignupBinding
import javax.inject.Inject

class SignupFragment : Fragment(),AuthListener {

    private lateinit var binding: FragmentSignupBinding

    @Inject
    lateinit var authRepository: AuthRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        AndroidSupportInjection.inject(this)
        binding=  FragmentSignupBinding.inflate(layoutInflater, container, false)

        setUpListeners()

        return binding.root
    }

    private fun setUpListeners() {

        authRepository.setListener(this)

        binding.loginText.setOnClickListener{
            findNavController().navigateUp()
        }

        binding.signupBtn.setOnClickListener{
            signUp()
        }

    }

    private fun signUp() {
        if(binding.emailInputEdittext.text.isNullOrBlank() || binding.passwordInputEdittext.text.isNullOrBlank()){
            Toast.makeText(context, R.string.error_empty_fields, Toast.LENGTH_SHORT).show()
            return
        }
        authRepository.signUpWithEmail(binding.emailInputEdittext.text.toString(),binding.passwordInputEdittext.text.toString(),requireActivity())
    }

    override fun success() {
        Toast.makeText(context,R.string.success_msg,Toast.LENGTH_SHORT).show()
        findNavController().navigateUp()
    }

    override fun failed(msg: String) {
        Toast.makeText(context,"${R.string.failed_msg} $msg",Toast.LENGTH_LONG).show()
    }

}