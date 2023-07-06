package com.example.senya.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.senya.data.Attraction
import com.example.senya.databinding.FragmentAttractionDetailBinding
import com.example.senya.databinding.FrgmentHomeBinding
import com.example.senya.ui.fragment.home.HomeFragmentAdapter
import com.example.senya.ui.fragment.home.HomeFragmentDirections
import com.squareup.picasso.Picasso

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
                //todo
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}