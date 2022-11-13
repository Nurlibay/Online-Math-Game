package uz.nurlibaydev.onlinemathgame.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.nurlibaydev.onlinemathgame.NavMainDirections
import uz.nurlibaydev.onlinemathgame.R
import uz.nurlibaydev.onlinemathgame.data.source.pref.SharedPref
import uz.nurlibaydev.onlinemathgame.databinding.ActivityMainBinding
import uz.nurlibaydev.onlinemathgame.domain.MainRepository
import uz.nurlibaydev.onlinemathgame.domain.MainRepositoryImpl
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    private val pref: SharedPref by inject()

    private val viewModel:MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.findNavController()

        setLocale()

        viewModel.invitationLiveData.observe(this) { data ->

            AlertDialog.Builder(this)
                .setTitle("Confirm")
                .setMessage("Are you really play game ${data.userName}")
                .setPositiveButton("Ok") { p0, _ ->
                    viewModel.confirmInvitationStatus(1, data.gameId, {

                    }) {

                    }
                    viewModel.confirmGameStatus(1, data.gameId, { data ->
                        p0.dismiss()
                        findNavController(R.id.fragment_container_view).navigate(
                            NavMainDirections.actionGlobalGameScreen(data)
                        )
                    }) {

                    }
                }.create().show()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        navController.popBackStack()
    }

    private fun setLocale() {
        val localeName = pref.language
        val locale = Locale(localeName)
        val res = resources
        val dm = res.displayMetrics
        val conf = res.configuration
        conf.setLocale(locale)
        res.updateConfiguration(conf, dm)
    }

    fun setNewLocale() {
        val refresh = Intent(this, MainActivity::class.java)
        refresh.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        this.intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        this.intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(refresh)
    }

}