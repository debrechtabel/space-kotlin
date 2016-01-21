package com.trevordebrecht.spacekotlin

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.onClick
import java.util.*

/**
 * Created by tdebrecht on 1/18/16.
 */

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        startGame.onClick { startGameImpl() }
        highScores.onClick { showHighScores() }
        ship.onClick { editShip() }
        loadShip()

        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount == 0) {
                mainMenu.visibility = View.VISIBLE
                loadShip()
            } else {
                mainMenu.visibility = View.GONE
            }
        }
    }

    fun loadShip() {
        ShipUtil.processShipDrawable(ship.drawable)
    }

    fun startGameImpl() {
        // todo real impl
//        val name = playerName.text.toString()
//        val score = Random().nextInt(500)
//
//        ScoreKeeper.addScore(name, score)
        launchFragment(GameFragment())
    }

    private fun showHighScores() {
        launchFragment(HighScoresFragment())
    }

    private fun editShip() {
        launchFragment(EditShipFragment())
    }

    private fun launchFragment(f: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.contentContainer, f)
                .addToBackStack(null)
                .commit()
    }
}
