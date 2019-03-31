package net.novate.reader

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.SparseArray
import androidx.core.app.ActivityCompat
import androidx.core.util.set
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

const val PERMISSION_GRANTED = 0
const val PERMISSION_DENIED = -1
const val PERMISSION_IGNORE = -2

class PermissionsViewModel : ViewModel() {
    public val requests = SparseArray<MutableLiveData<IntArray>>()


    fun requestPermissions(activity: FragmentActivity, permissions: Array<out String>, observer: (IntArray?) -> Unit) {
        val code = code()
        val request = object : MutableLiveData<IntArray>() {
            override fun onInactive() {
                requests.remove(code)
            }
        }
        request.observeOnce(activity) {

        }
        requests[code] = request
        ActivityCompat.requestPermissions(activity, permissions, code)
    }

    fun requestManifestPermissions(activity: FragmentActivity, observer: (IntArray?) -> Unit) {
        return requestPermissions(activity, getManifestPermissions(activity), observer)
    }

    fun onRequestPermissionsResult(activity: Activity, requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        requests.take(requestCode)?.value = IntArray(grantResults.size) { i ->
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) PERMISSION_GRANTED else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[i])) PERMISSION_DENIED else PERMISSION_IGNORE
            }
        }
    }

    private fun getManifestPermissions(context: Context): Array<out String> {
        return context.packageManager.getPackageInfo(context.packageName, PackageManager.GET_PERMISSIONS).requestedPermissions
    }

    private fun code() = if (requests.size() <= 0) 1 else requests.keyAt(requests.size() - 1) + 1
}

private fun <E> SparseArray<E>.take(key: Int): E? {
    val value = get(key)
    remove(key)
    return value
}