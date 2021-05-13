package im.gian.tr.login.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import im.gian.tr.R
import im.gian.tr.databinding.FragmentSignupBinding

class SignupFragment : Fragment() {
    val viewModel :SignupViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentSignupBinding>(
            inflater, R.layout.fragment_signup, container, false)

        val onLoginCompleteListener = OnCompleteListener<AuthResult>() {
            binding.buttonRegister.revertAnimation()
        }

        binding.signup = this

        binding.buttonRegister.setOnClickListener {
            binding.buttonRegister.startAnimation()
            viewModel.registerUser(email = binding.textInputEmail.text.toString(), password = binding.textInputPassword.toString(), onCompleteListener = onLoginCompleteListener)
        }

        binding.viewModel = viewModel

        return binding.root
    }
}