package lpzmrc.test.djungle.io.ui.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import lpzmrc.test.djungle.io.R
import lpzmrc.test.djungle.io.databinding.FragmentLoginBinding
import lpzmrc.test.djungle.io.ui.kxt.closeKeyboard
import lpzmrc.test.djungle.io.ui.kxt.showError

class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().finishAffinity()
        }
        bindViewModel()
    }

    override fun onStart() {
        super.onStart()
        viewModel.checkIfCanLogin()
    }

    private fun bindViewModel() {
        viewModel.loginResult.observe(viewLifecycleOwner,
            Observer { loginResult ->
                loginResult ?: return@Observer
                binding.loadingProgressBar.visibility = View.GONE
                loginResult.error?.let {
                    showError(it)
                }
                loginResult.success.let {
                    if (it) findNavController().navigate(R.id.finishLogin)
                }
            })

        viewModel.username.observe(viewLifecycleOwner, Observer { viewModel.loginDataChanged() })
        viewModel.password.observe(viewLifecycleOwner, Observer { viewModel.loginDataChanged() })

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.loginButton.setOnClickListener { view ->
            view.closeKeyboard();viewModel.login()
        }
        binding.passwordTextInputLayout.editText?.setOnEditorActionListener { view, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                view.closeKeyboard();viewModel.login()
                true
            } else
                false
        }
    }
}
