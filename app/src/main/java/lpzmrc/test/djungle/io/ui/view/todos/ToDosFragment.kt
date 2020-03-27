package lpzmrc.test.djungle.io.ui.view.todos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import lpzmrc.test.djungle.io.R
import lpzmrc.test.djungle.io.data.model.AuthenticationState
import lpzmrc.test.djungle.io.databinding.FragmentTodosBinding
import lpzmrc.test.djungle.io.ui.controller.ToDosController
import lpzmrc.test.djungle.io.ui.kxt.showError
import lpzmrc.test.djungle.io.ui.kxt.todoDecoration
import lpzmrc.test.djungle.io.ui.kxt.todoLayoutManager


class ToDosFragment : Fragment() {

    private val controller = ToDosController()
    private val viewModel: ToDosViewModel by viewModels()
    private lateinit var binding: FragmentTodosBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_todos, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bindViewModel()
    }

    private fun bindViewModel() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.refresh.setOnRefreshListener { viewModel.fetchData() }
        binding.content.apply {
            setController(controller)
            setHasFixedSize(false)
            layoutManager = todoLayoutManager
            addItemDecoration(todoDecoration)
            itemAnimator = null
        }

        observeAuthenticationState()
        viewModel.toDosResult.observe(viewLifecycleOwner,
            Observer { toDosResult ->
                toDosResult ?: return@Observer
                toDosResult.error?.let {
                    showError(it)
                }
                toDosResult.data?.let {
                    controller.setData(it)
                }
            })
    }

    private fun observeAuthenticationState() {
        viewModel.authenticationState.observe(viewLifecycleOwner, Observer { authenticationState ->
            binding.refresh.isEnabled = authenticationState == AuthenticationState.AUTHENTICATED
            when (authenticationState) {
                AuthenticationState.AUTHENTICATED -> {
                    viewModel.fetchData()
                }
                else -> {
                    viewModel.clearData()
                }
            }
        })
    }
}
