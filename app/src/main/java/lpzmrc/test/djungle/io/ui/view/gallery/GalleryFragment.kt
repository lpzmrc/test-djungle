package lpzmrc.test.djungle.io.ui.view.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import lpzmrc.test.djungle.io.R
import lpzmrc.test.djungle.io.data.model.AuthenticationState
import lpzmrc.test.djungle.io.databinding.FragmentGalleryBinding
import lpzmrc.test.djungle.io.ui.controller.GalleryController
import lpzmrc.test.djungle.io.ui.kxt.galleryDecoration
import lpzmrc.test.djungle.io.ui.kxt.galleryLayoutManager
import lpzmrc.test.djungle.io.ui.kxt.showError

class GalleryFragment : Fragment() {

    private val controller = GalleryController()
    private val viewModel: GalleryViewModel by activityViewModels()
    private lateinit var binding: FragmentGalleryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gallery, container, false)
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
            layoutManager = galleryLayoutManager.apply {
                setSpanSizeLookup(controller.spanSizeLookup)
            }
            itemAnimator = null
            addItemDecoration(galleryDecoration)
        }
        observeAuthenticationState()
        viewModel.galleryResult.observe(viewLifecycleOwner,
            Observer { galleryResult ->
                galleryResult ?: return@Observer
                galleryResult.error?.let {
                    showError(it)
                }
                galleryResult.data?.let {
                    controller.setData(it)
                }
            })
    }

    private fun observeAuthenticationState() {
        viewModel.authenticationState.observe(viewLifecycleOwner, Observer { authenticationState ->
            binding.refresh.isEnabled = authenticationState == AuthenticationState.AUTHENTICATED
            when (authenticationState) {
                AuthenticationState.AUTHENTICATED -> {
                    if (viewModel.hasNoData()) {
                        viewModel.fetchData()
                    }
                }
                else -> {
                    viewModel.clearData()
                }
            }
        })
    }
}
