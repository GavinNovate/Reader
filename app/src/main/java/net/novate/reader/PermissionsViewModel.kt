package net.novate.reader

import android.app.Activity
import android.app.Application
import android.content.pm.PackageManager
import android.util.SparseArray
import androidx.annotation.IntDef
import androidx.core.app.ActivityCompat
import androidx.core.util.keyIterator
import androidx.core.util.set
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

const val PERMISSION_GRANTED = 0
const val PERMISSION_DENIED = -1
const val PERMISSION_IGNORE = -2

@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
@IntDef(PERMISSION_GRANTED, PERMISSION_DENIED, PERMISSION_IGNORE)
annotation class Permission

class PermissionsViewModel constructor(application: Application) : AndroidViewModel(application) {
    private val requests = SparseArray<MutableLiveData<IntArray>>()


    fun test() {
        requests[5] = MutableLiveData()
        requests[3] = MutableLiveData()
        requests[4] = MutableLiveData()
        requests[6] = MutableLiveData()
        requests.keyIterator().forEach {
            println(it)
        }
    }

    fun onRequestPermissionsResult(activity: Activity, requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        requests.remove(requestCode) { it?.value = IntArray(grantResults.size) { i -> if (grantResults[i] == PackageManager.PERMISSION_GRANTED) PERMISSION_GRANTED else (if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[i])) PERMISSION_DENIED else PERMISSION_IGNORE) } }
    }

    private fun code() = if (requests.size() <= 0) 1 else requests.keyAt(requests.size() - 1) + 1
}

private fun <E> SparseArray<E>.remove(key: Int, block: (E?) -> Unit) {
    block(get(key))
    remove(key)
}