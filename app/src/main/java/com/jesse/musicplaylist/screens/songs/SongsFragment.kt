package com.jesse.musicplaylist.screens.songs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.jesse.musicplaylist.data.Song
import com.jesse.musicplaylist.databinding.FragmentSongsBinding
import com.jesse.musicplaylist.obtainViewModel


class SongsFragment : Fragment() {
    private var _binding: FragmentSongsBinding? = null
    private val binding : FragmentSongsBinding get() = _binding!!
    private val songsViewModel: SongsViewModel by lazy {
        requireActivity().obtainViewModel(SongsViewModel::class.java)
    }
    private val songsAdapter = SongsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSongsBinding.inflate(layoutInflater, container, false)
        songsAdapter.setDeleteLambda{song: Song -> songsViewModel.deleteSong(song) }
        setupRecyclerView()
        return binding.root
    }

    private fun setupRecyclerView() {
        binding.songRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false)
            adapter = songsAdapter
        }
        songsViewModel.getAllSongs().observe(viewLifecycleOwner, {
                songs -> songsAdapter.setSongs(songs)
        })
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}