package com.app.arejaybee.character_sheet.fragments

import android.content.ActivityNotFoundException
import android.content.Intent
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.app.arejaybee.character_sheet.activity.MainActivity
import java.io.File


open class RobFragment : Fragment() {
    val activity
        get() = if (getActivity() == null) null else getActivity() as MainActivity

    companion object {
        const val TAG = "RobFragment"
    }

    open fun onClickAdd() {

    }

    open fun onClickEdit() {

    }

    open fun onClickDelete() {

    }

    open fun onClickEmail() {

        activity?.let {
            val file = tempSaveCharacter()
            if (file == null) {
                Toast.makeText(it, "Could not save character to a file.", Toast.LENGTH_LONG).show()
            } else {
                val emailIntent = Intent(Intent.ACTION_SEND)
                emailIntent.type = "message/char"
                val uri = FileProvider.getUriForFile(it, it.packageName + ".provider", file);
                emailIntent.putExtra(Intent.EXTRA_STREAM, uri)
                try {
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."))
                } catch (err: ActivityNotFoundException) {
                    Toast.makeText(it, "There is no email client installed.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    open fun onClickSave() {

    }

    open fun onClickMenu() {

    }

    open fun onClickHome() {
        activity?.onBackPressed()
    }

    private fun tempSaveCharacter() : File? {
        activity?.let {
            val outputDir = it.getExternalFilesDir(null) // context being the Activity pointer
            val outputFile = File.createTempFile("5e_"+it.rob.name, ".char", outputDir)
            outputFile.writeText(it.rob.toJson())
            return outputFile
        }
        return null
    }
}