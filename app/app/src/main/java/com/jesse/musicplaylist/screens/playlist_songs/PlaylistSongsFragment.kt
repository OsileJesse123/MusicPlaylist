package com.jesse.musicplaylist.screens.playlist_songs

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.jesse.musicplaylist.R
import com.jesse.musicplaylist.data.Song
import com.jesse.musicplaylist.databinding.PlaylistSongsFragmentBinding
import com.jesse.musicplaylist.screens.songs.SongsAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PlaylistSongsFragment : Fragment() {

    private val args: PlaylistSongsFragmentArgs by navArgs()
    private var binding_: PlaylistSongsFragmentBinding? = null
    private val binding: PlaylistSongsFragmentBinding get() = binding_!!
    @Inject
    lateinit var playlistSongsAdapter: SongsAdapter
    private val playlistSongsViewModel: PlaylistSongsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding_ = DataBindingUtil.inflate(inflater, R.layout.playlist_songs_fragment,
            container, false)
        //Fragment arguments is being used here for test purposes as fragments will be tested in
        //isolation
        val playlistUid = if (args.playlistUid == 0)
            arguments?.getInt("playlistUid") else args.playlistUid

        playlistSongsViewModel.getAllSongs().observe(viewLifecycleOwner){
           songs -> val currentSongs = songs.filter { it.songStates?.contains(playlistUid)
            ?:  listOf<Int>().contains(playlistUid)}
            Log.i("PlaylistSongsSongEmpty", "listOfSongs: $currentSongs")
            playlistSongsViewModel.setSongs(currentSongs)

        }

        setupAdapter()

        setupRecyclerView()

        return binding.root
    }

    private fun setupAdapter() {
        playlistSongsAdapter.setDeleteLambda { song -> playlistSongsViewModel.deleteSong(song) }
    }
    
    override fun onStart() {
        super.onStart()

        playlistSongsViewModel.songs.observe(viewLifecycleOwner){
            songs -> setupFragmentView(songs)
        }
        binding.playlist = args.playlistName ?: arguments?.getString("playlistName") ?: "Nothing"
    }

    private fun setupFragmentView(songs: List<Song>) {
        if(songs.isNotEmpty()){
            playlistSongsAdapter.setSongs(songs)
            binding.addSongBtn.visibility = View.GONE
            binding.playlistSongsRecycler.visibility = View.VISIBLE
        } else {
            binding.playlistSongsRecycler.visibility = View.GONE
            binding.addSongBtn.visibility = View.VISIBLE

            initializeAddButtonClickedListener()
        }
    }

    private fun initializeAddButtonClickedListener() {
        binding.addSongBtn.setOnClickListener {
            //Fragment arguments is being used here for test purposes as fragments will be tested in
            //isolation
            val playlistUid = if (args.playlistUid == 0)
                arguments?.getInt("playlistUid") ?: 0 else args.playlistUid
            val action = PlaylistSongsFragmentDirections
                .actionPlaylistSongsFragmentToAddSongActivity(playlistUid)
            findNavController().navigate(action)
        }
    }

    private fun setupRecyclerView() {
        binding.playlistSongsRecycler.apply{
            layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false)
            adapter = playlistSongsAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding_ = null
    }

}