package com.recepguzel.cryptoxcleanarchitecture.ui.home.favorite.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.recepguzel.cryptoxcleanarchitecture.R
import com.recepguzel.cryptoxcleanarchitecture.databinding.FragmentFavoriteBinding
import com.recepguzel.cryptoxcleanarchitecture.ui.home.HomeFragmentDirections
import com.recepguzel.cryptoxcleanarchitecture.ui.home.favorite.adapter.FavoriteAdapter
import com.recepguzel.cryptoxcleanarchitecture.ui.home.favorite.viewmodel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private val favoriteViewModel: FavoriteViewModel by viewModels()
    lateinit var favoriteAdapter: FavoriteAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding=FragmentFavoriteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createRecyclerView()
        observeData()
        deleteFavoriteCoin()
    }
    private fun createRecyclerView() {
        favoriteAdapter = FavoriteAdapter()
        binding.favoriteRecyclerView.adapter = favoriteAdapter
    }

    private fun observeData() {
        favoriteViewModel.getFavoriteList().observe(viewLifecycleOwner) { favoriteList ->
            if (favoriteList.isNotEmpty()) {
                binding.emptyListTextview.visibility = View.GONE
                favoriteAdapter.differ.submitList(favoriteList)
                favoriteAdapter.setOnItemClickListener {
                    val action = HomeFragmentDirections.actionHomeFragmentToCoinDetailFragment(it)
                    findNavController().navigate(action)
                }

            } else {
                binding.emptyListTextview.visibility = View.VISIBLE
            }


        }
    }

    private fun deleteFavoriteCoin() {
        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = favoriteAdapter.differ.currentList[position]
                favoriteViewModel.deleteFavoriteCoin(article)
                Snackbar.make(
                    requireView(),
                    getString(R.string.successfully),
                    Snackbar.LENGTH_SHORT
                ).apply {
                    setAction(getString(R.string.undo)) {
                        favoriteViewModel.addFavoriteCoin(article)
                    }.show()
                }
            }
        }
        ItemTouchHelper(itemTouchHelper).apply {
            attachToRecyclerView(binding.favoriteRecyclerView)
        }
    }

}

