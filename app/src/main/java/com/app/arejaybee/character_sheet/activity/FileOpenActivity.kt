package com.app.arejaybee.character_sheet.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.arejaybee.character_sheet.utils.FileReader

class FileOpenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val data = intent.data
        val intent = Intent(this, MainActivity::class.java)
        try {
            data?.let {
                val file = FileReader.fileFromContentUri(this, it)
                val robJson = file.readText()
                intent.putExtra("characterJson", robJson)
            }
        } catch(err: Exception) {
            Toast.makeText(this, "File is not the right type.", Toast.LENGTH_LONG).show()
        }
        startActivity(intent)
    }
}