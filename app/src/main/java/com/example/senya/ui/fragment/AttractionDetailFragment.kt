package com.example.senya.ui.fragment

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.senya.R
import com.example.senya.data.Attraction
import com.example.senya.databinding.FragmentAttractionDetailBinding
import com.example.senya.databinding.FrgmentHomeBinding
import com.example.senya.ui.fragment.home.HomeFragmentAdapter
import com.example.senya.ui.fragment.home.HomeFragmentDirections
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.squareup.picasso.Picasso
import java.lang.StringBuilder

class AttractionDetailFragment: BaseFragment() {


    private var _binding: FragmentAttractionDetailBinding? = null
    private val binding
        get() = _binding!!

    private val safeArgs: AttractionDetailFragmentArgs by navArgs()

    private val attraction: Attraction by lazy{
        attractions.find { it.id == safeArgs.attractionId }!!
    }

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
                        val uri = Uri.parse("geo:${attraction.location.latitude},${attraction.location.longitude}?z=9&q=${attraction.title}")
                        val mapIntent = Intent(Intent.ACTION_VIEW, uri)
                        mapIntent.setPackage("com.google.android.apps.maps")
                        startActivity(mapIntent)
                        return true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            titleTextView.text = attraction.title
            descriptionTextView.text = attraction.description
            Picasso.get().load(attraction.image_urls[0]).into(headerImageView);
            monthsToVisitTextView.text = attraction.months_to_visit
            numberOfFactsTextView.text = "${attraction.facts.size} facts"
            binding.numberOfFactsTextView.setOnClickListener {
                val stringBuilder = StringBuilder()
                attraction.facts.forEach {
                    stringBuilder.append("\u2022 $it")
                    stringBuilder.append("\n\n")
                }
                val message = stringBuilder.toString()
                    .substring(0, stringBuilder.toString().lastIndexOf("\n\n"))
                //had to use materialdialogbuilder because the theme is a material components theme
                MaterialAlertDialogBuilder(requireContext(), R.style.MyDialog)
                    .setTitle("${attraction.title} Facts")
                    .setMessage(message)
                    .setPositiveButton("Ok"){dialog, which ->
                        dialog.dismiss()
                    }.show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}