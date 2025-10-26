package com.example.gallery_dp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.gallery_dp.Permissions.RequestPermissions
import com.example.gallery_dp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var requestPermissions: RequestPermissions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        requestPermissions = RequestPermissions(this)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        mainContext = this

        requestPermissions.checkPermissions()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        requestPermissions.handlePermissionsResult(
            requestCode,
            grantResults,
            onPermissionsGranted = {
                restartActivity()
            },
            onPermissionsDenied = { deniedPermissions ->
                showPermissionDeniedMessage()
            },
        )
    }

    private fun restartActivity() {
        val intent = Intent(this, this::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        finish()
        startActivity(intent)
    }

    private fun showPermissionDeniedMessage() {
        AlertDialog
            .Builder(this)
            .setTitle(getString(R.string.require_permissions_title))
            .setMessage(getString(R.string.require_permissions_message))
            .setPositiveButton(getString(R.string.pos_b)) { dialog, which ->
                requestPermissions.checkPermissions()
            }.setNegativeButton(getString(R.string.neg_b)) { dialog, which ->
                finish()
            }.show()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) ||
            super.onSupportNavigateUp()
    }

//    override fun onActivityResult(
//        requestCode: Int,
//        resultCode: Int,
//        data: Intent?,
//    ) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == OPEN_DOCUMENT_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
//            // Handle the selected images here
//            data?.data?.let { uri ->
//                // Process the selected image URI
//            }
//        }

//        if (resultCode == RESULT_OK) {
//            listpicture.removeAt(positionDeleteMedia.last())
//            viewpagerForDeleteAction.setCurrentItem(positionDeleteMedia.last())
//            Snackbar.make(binding.root, "Файл удален", Snackbar.LENGTH_SHORT).show()
//        }
//        else {
//            Snackbar.make(binding.root, "Удаление отменено", Snackbar.LENGTH_SHORT).show()
//        }
//    }
}
