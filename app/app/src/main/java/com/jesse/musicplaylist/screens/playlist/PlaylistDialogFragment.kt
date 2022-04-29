package com.jesse.musicplaylist.screens.playlist

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.jesse.musicplaylist.R
import com.jesse.musicplaylist.databinding.CustomAlertDialogBinding
import java.lang.IllegalStateException

class PlaylistDialogFragment(val listener: OnSetOnButtonsClickedListener): DialogFragment() {

    interface OnSetOnButtonsClickedListener{
        fun onPositiveButtonClicked(playlistName: String)
        fun onNegativeButtonClicked()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let{
            it.setTheme(R.style.Theme_MaterialComponents)
            //ad here stands for Alert Dialog
            val adBinding = CustomAlertDialogBinding.inflate(layoutInflater)


            val alertDialog = MaterialAlertDialogBuilder(it)
                .setView(adBinding.root)
                .setBackground(ResourcesCompat.getDrawable(resources, R.drawable.alert_dialog_bg,
                    null))
                .show()

            adBinding.cancelBtn.setOnClickListener {
                listener.onNegativeButtonClicked()
                alertDialog.dismiss()
            }
            adBinding.saveBtn.setOnClickListener {
                if (adBinding.playlistNameEt.text.toString().isEmpty()){
                    Toast.makeText(requireContext(), getString(R.string.playlist_must_have_name), Toast.LENGTH_SHORT)
                        .show()
                } else {
                    listener.onPositiveButtonClicked(adBinding.playlistNameEt.text.toString())
                    alertDialog.dismiss()
                }

            }

            alertDialog
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}