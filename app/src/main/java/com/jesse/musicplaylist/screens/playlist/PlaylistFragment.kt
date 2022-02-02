package com.jesse.musicplaylist.screens.playlist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jesse.musicplaylist.data.Playlist
import com.jesse.musicplaylist.databinding.FragmentPlaylistBinding
import com.jesse.musicplaylist.obtainViewModel



class PlaylistFragment : Fragment(), PlaylistDialogFragment.OnSetOnButtonsClickedListener,
    PlaylistAdapter.OnPlaylistCardClickedListener {

    private var _binding: FragmentPlaylistBinding? = null
    private val binding: FragmentPlaylistBinding get() = _binding!!
    private val playlistAdapter by lazy {
        PlaylistAdapter(requireContext(), this)
    }
    private val playlistViewModel by lazy {
        requireActivity().obtainViewModel(PlaylistViewModel::class.java)
    }

    private val itemTouchHelper = ItemTouchHelper(object: ItemTouchHelper
    .SimpleCallback(ItemTouchHelper.UP or  ItemTouchHelper.DOWN,
        ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            TODO("Not yet implemented")
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val viewPosition = viewHolder.adapterPosition
            val currentPlaylist = playlistAdapter.playlists[viewPosition]
            deletePlaylist(currentPlaylist)
            updatePlaylistSongsAfterDelete(currentPlaylist.uid)
        }

    })

    private fun updatePlaylistSongsAfterDelete(uid: Int) {
        playlistViewModel.getAllSongs().observe(viewLifecycleOwner){songs ->
            val allSongs = songs.filter { it.songStates?.contains(uid)
            ?:  listOf<Int>().contains(uid)}
            playlistViewModel.updatePlaylistSongsAfterDelete(allSongs, uid)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlaylistBinding.inflate(layoutInflater, container, false)

        setupRecyclerView()
        binding.createPlaylistFab.setOnClickListener {
            createAlertDialog()
        }

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.playlistRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false)
            adapter = playlistAdapter
            itemTouchHelper.attachToRecyclerView(this)
        }
    }

    private fun createAlertDialog(){
       val alertDialog = PlaylistDialogFragment(this)
        alertDialog.show(requireActivity().supportFragmentManager, "Create New Playlist")
    }

    override fun onResume() {
        super.onResume()
        updatePlaylist()
    }

    private fun updatePlaylist() {
        playlistViewModel.getAllPlaylists().observe(viewLifecycleOwner) { playlists ->
            playlistAdapter.playlists = playlists
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onPositiveButtonClicked(playlistName: String) {
            savePlaylist(createPlaylist(playlistName))
    }

    private fun savePlaylist(playlist: Playlist) {
        playlistViewModel.insertPlaylist(playlist)
    }

    private fun createPlaylist(playlistName: String): Playlist {
        return playlistViewModel.createPlaylist(playlistName)
    }

    private fun deletePlaylist(currentPlaylist: Playlist) {
        playlistViewModel.deletePlaylist(currentPlaylist)
        updatePlaylist()
    }

    override fun onNegativeButtonClicked() {
        Log.v("Dismiss", "Dialog dismissed")
    }

    override fun onPlaylistCardClicked(playlistUid: Int, playlistName: String) {
        val action = PlaylistFragmentDirections
            .actionPlaylistFragmentToPlaylistSongsFragment(playlistUid = playlistUid,
                playlistName = playlistName)
        findNavController().navigate(action)
        Log.i("PlaylistObjectDetails", "The Playlist Name: $playlistName," +
                " The Playlist Uid: $playlistUid ")
    }

}