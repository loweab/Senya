package com.example.senya.ui.fragment.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.senya.R
import com.example.senya.databinding.FragmentAttractionDetailBinding
import com.example.senya.ui.fragment.BaseFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.squareup.picasso.Picasso
import java.lang.StringBuilder

class AttractionDetailFragment: BaseFragment() {


    private var _binding: FragmentAttractionDetailBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAttractionDetailBinding.inflate(inflater, container, false)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_attraction_detail, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when(menuItem.itemId){
                    R.id.menu_item_location -> {
                        val attraction = activityViewModel.selectedAttractionLiveData.value ?: return true
                        activityViewModel.locationSelectedLiveData.postValue(attraction)
                        true //return true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return binding.root
    }

    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            activityViewModel.selectedAttractionLiveData.observe(viewLifecycleOwner) { attraction ->
                titleTextView.text = attraction.title

                headerEpoxyRecyclerView.setControllerAndBuildModels(
                    DetailHeaderEpoxyController(attraction.image_urls)
                )

                LinearSnapHelper().attachToRecyclerView(headerEpoxyRecyclerView)
                indicator.attachToRecyclerView(headerEpoxyRecyclerView)

                var isGridMode: Boolean = contentEpoxyRecyclerView.layoutManager is GridLayoutManager
                val contentEpoxyController = ContentEpoxyController(attraction)
                contentEpoxyController.isGridMode = isGridMode
                contentEpoxyController.onChangeLayoutCallback = {
                    if (isGridMode) {
                        contentEpoxyRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                    } else {
                        contentEpoxyRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
                    }

                    isGridMode = !isGridMode
                    contentEpoxyController.isGridMode = isGridMode
                    contentEpoxyController.requestModelBuild()
                }

                contentEpoxyRecyclerView.setControllerAndBuildModels(ContentEpoxyController(attraction))
            }
        }
    }*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activityViewModel.selectedAttractionLiveData.observe(viewLifecycleOwner) { attraction ->
            binding.titleTextView.text = attraction.title
            binding.headerEpoxyRecyclerView.setControllerAndBuildModels(
                DetailHeaderEpoxyController(
                    attraction.image_urls
                )
            )
            LinearSnapHelper().attachToRecyclerView(binding.headerEpoxyRecyclerView)
            binding.indicator.attachToRecyclerView(binding.headerEpoxyRecyclerView)

            var isGridMode: Boolean = binding.contentEpoxyRecyclerView.layoutManager is GridLayoutManager
            val contentEpoxyController = ContentEpoxyController(attraction)
            contentEpoxyController.isGridMode = isGridMode
            contentEpoxyController.onChangeLayoutCallback = {
                if (isGridMode) {
                    binding.contentEpoxyRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                } else {
                    binding.contentEpoxyRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
                }

                isGridMode = !isGridMode
                contentEpoxyController.isGridMode = isGridMode
                contentEpoxyController.requestModelBuild()
            }

            binding.contentEpoxyRecyclerView.setControllerAndBuildModels(contentEpoxyController)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}