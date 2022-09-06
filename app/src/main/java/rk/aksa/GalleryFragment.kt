package rk.aksa

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import rk.aksa.databinding.FragmentGalleryBinding

class GalleryFragment : Fragment(), ImageClickListener {
    private val viewModel by viewModels<GalleryFragmentViewModel> {
        GalleryFragmentViewModelFactory(requireContext())
    }
    lateinit var imageAdapter: ImageAdapter
    private var _binding: FragmentGalleryBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().title = Const.FRAGMENT_GALLERY_TITLE
        postponeEnterTransition()
        imageAdapter = ImageAdapter()
        binding.recyclerView.apply {
            adapter = imageAdapter
            layoutManager = GridLayoutManager(requireContext(), 4)
            doOnPreDraw { startPostponedEnterTransition() }
        }
        viewModel.images.observe(viewLifecycleOwner) {
            imageAdapter.submitList(it)
        }

        imageAdapter.setOnImageClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateToDetail(image: Image, view: View) {
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            val extras = bundleOf(Const.KEY_IMAGE to image)
            addSharedElement(view, view.transitionName)
            replace<DetailFragment>(R.id.fragmentContainerView, args = extras)
            addToBackStack(null)
        }
    }

    override fun onImageClicked(view: View, image: Image) {
        navigateToDetail(image, view)
    }

}