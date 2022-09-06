package rk.aksa

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.transition.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.transition.MaterialContainerTransform
import rk.aksa.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val image = requireArguments().getParcelable<Image>(Const.KEY_IMAGE)
        setupMenu(image?.displayName)
        postponeEnterTransition()
        ViewCompat.setTransitionName(binding.imgDetail, image?.id)
        Glide.with(requireContext())
            .load(image?.uri)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }

            })
            .dontTransform()
            .into(binding.imgDetail)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        (requireActivity() as MainActivity).hideUpButton()
    }

    private fun setupMenu(title: String?) {
        (requireActivity() as MainActivity).apply {
            showUpButton()
            setTitle(title)
        }
        (requireActivity() as MenuHost)
            .addMenuProvider(object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    return when (menuItem.itemId) {
                        android.R.id.home -> {
                            (requireActivity() as MainActivity).onBackPressed()
                            true
                        }
                        else -> false
                    }
                }

            }, viewLifecycleOwner)
    }


    private fun getTransition(): Transition {
        val set = TransitionSet()
        set.ordering = TransitionSet.ORDERING_TOGETHER
        set.addTransition(ChangeBounds())
        set.addTransition(ChangeImageTransform())
        set.addTransition(ChangeTransform())
        return set
    }
}