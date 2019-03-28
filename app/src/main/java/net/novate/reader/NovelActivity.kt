package net.novate.reader

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import net.novate.reader.databinding.NovelActivityBinding
import okio.buffer
import okio.source

class NovelActivity : AppCompatActivity() {

    private lateinit var binding: NovelActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_novel)

        binding.content.text = assets.open("novel.txt").source().buffer().readUtf8()
    }
}
