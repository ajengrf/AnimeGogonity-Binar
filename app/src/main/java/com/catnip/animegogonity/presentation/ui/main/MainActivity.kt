package com.catnip.animegogonity.presentation.ui.main


import android.app.AlertDialog
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import com.catnip.animegogonity.R
import com.catnip.animegogonity.base.BaseActivity
import com.catnip.animegogonity.base.BaseViewModelActivity
import com.catnip.animegogonity.databinding.ActivityMainBinding
import com.catnip.animegogonity.presentation.ui.auth.AuthActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity :
    BaseViewModelActivity<ActivityMainBinding, MainViewModel>(ActivityMainBinding::inflate) {

    override val viewModel: MainViewModel by viewModel()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_logout -> {
                showDialogLogout()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showDialogLogout() {
        AlertDialog.Builder(this)
            .apply {
                setTitle(getString(R.string.text_logout_dialog))
                setPositiveButton(R.string.text_dialog_logout_task_positive) { dialog, _ ->
                    logout()
                    dialog.dismiss()
                }
                setNegativeButton(R.string.text_dialog_logout_task_negative) { dialog, _ ->
                    dialog.dismiss()
                }
            }.create().show()
    }

    private fun logout() {
        viewModel.doLogout()
        navigateToLogin()
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, AuthActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }

}