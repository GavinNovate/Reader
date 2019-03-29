package net.novate.reader

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import net.novate.reader.databinding.NovelActivityBinding

private const val TAG = "NovelActivity"

class NovelActivity : AppCompatActivity() {

    private lateinit var binding: NovelActivityBinding
    lateinit var viewModel: PermissionsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_novel)

//        binding.content.text = assets.open("novel.txt").source().buffer().readUtf8()

        viewModel = ViewModelProviders.of(this)[PermissionsViewModel::class.java]


//        println("code" + viewModel.code())
//
//        viewModel.test()
//
//        println("code" + viewModel.code())


        val liveData = MutableLiveData<String>()


        liveData.observe(this, Observer {
            Log.d(TAG, "observe -- $it")
        })

        liveData.observeOnce(this) {

        }

        liveData.observe(this) {

        }

        val bean = Bean()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        viewModel.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}