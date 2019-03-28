package net.novate.reader

import android.app.Application
import android.util.SparseArray
import androidx.core.util.keyIterator
import androidx.core.util.set
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class PermissionsViewModel constructor(application: Application) : AndroidViewModel(application) {
    private val requests = SparseArray<MutableLiveData<BooleanArray>>()


    fun test() {

        requests[5] = MutableLiveData()
        requests[3] = MutableLiveData()
        requests[4] = MutableLiveData()
        requests[6] = MutableLiveData()

        requests.keyIterator().forEach {
            println(it)
        }

//        requests.contains(1)
//
//        requests.keyAt(requests.size() - 1) + 1
    }

    fun code() = if (requests.size() <= 0) 1 else requests.keyAt(requests.size() - 1) + 1
}