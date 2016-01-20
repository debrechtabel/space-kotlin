package com.trevordebrecht.spacekotlin

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.button
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.margin
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.onClick
import org.jetbrains.anko.padding
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.dip
import org.jetbrains.anko.textView
import org.jetbrains.anko.verticalLayout


/**
 * Created by tdebrecht on 1/19/16.
 */

class HighScoresFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val scores = ScoreKeeper.scores

        return with(ctx) {
            verticalLayout {
                backgroundColor = Color.WHITE

                textView(R.string.high_scores) {
                    textSize = 32f // 32sp
                }.lparams {
                    gravity = Gravity.CENTER
                }

                // show top 5 scores (if available)
                for (i in 0..(Math.min(scores.size - 1, 5))) {
                    linearLayout {
                        orientation = LinearLayout.HORIZONTAL

                        // alternate background color to more easily read scores
                        backgroundColor = if (i % 2 == 0) Color.WHITE else Color.LTGRAY

                        textView("${scores[i].first}").lparams(width = dip(0)) {
                            weight = 1f
                            gravity = Gravity.CENTER_VERTICAL
                            leftMargin = dip(10)
                        }
                        textView("${scores[i].second}").lparams {
                            gravity = Gravity.CENTER_VERTICAL
                            rightMargin = dip(10)
                        }

                    }.lparams(width = matchParent, height = dip(40))
                }

                button("Main Menu") {
                    onClick { popFragment() }
                }.lparams(width = matchParent) {
                    margin = dip(10)
                }
            }
        }
    }
}

object ScoreKeeper {

    val scores: MutableList<Pair<String, Int>> = arrayListOf()
        get() {
            field.sortBy { it.second }
            return field
        }

    fun addScore(player: String, score: Int) {
        scores.add(player to score)
    }

}
