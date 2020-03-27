package lpzmrc.test.djungle.io.ui.view.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import lpzmrc.test.djungle.io.R
import lpzmrc.test.djungle.io.databinding.FragmentSettingsBinding
import lpzmrc.test.djungle.io.ui.kxt.showError

class SettingsFragment : Fragment() {

    private val viewModel: SettingsViewModel by viewModels()
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bindViewModel()
    }

    private fun bindViewModel() {
        viewModel.logoutResult.observe(viewLifecycleOwner,
            Observer { loginResult ->
                loginResult ?: return@Observer
                binding.loadingProgressBar.visibility = View.GONE
                loginResult.error?.let {
                    showError(it)
                }
                loginResult.success?.let {
                    Unit
                }
            })
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.logoutButton.setOnClickListener {
            binding.loadingProgressBar.visibility = View.VISIBLE
            viewModel.logout()
        }
    }
}
