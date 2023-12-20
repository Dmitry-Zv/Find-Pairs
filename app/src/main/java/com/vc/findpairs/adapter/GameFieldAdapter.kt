package com.vc.findpairs.adapter

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.opengl.Visibility
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vc.findpairs.databinding.ItemGameFieldBinding
import com.vc.findpairs.domain.model.GameField
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.concurrent.Volatile

class GameFieldAdapter(
    private val fieldFlipped: FieldFlipped,
    private val victory: Victory
) : RecyclerView.Adapter<GameFieldAdapter.GameFieldViewHolder>() {

    private val scope = CoroutineScope(Dispatchers.Main)

    private val callback = object : DiffUtil.ItemCallback<GameField>() {
        override fun areItemsTheSame(oldItem: GameField, newItem: GameField): Boolean =
            oldItem.iconField == newItem.iconField


        override fun areContentsTheSame(oldItem: GameField, newItem: GameField): Boolean =
            oldItem == newItem

    }

    @Volatile
    private var isClickedEnable = AtomicBoolean(true)


    private val listDiffer = AsyncListDiffer(this, callback)

    inner class GameFieldViewHolder(private val binding: ItemGameFieldBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(gameField: GameField, position: Int, isEnable: Boolean) {
            with(binding) {

                itemFieldIcon.setImageResource(gameField.iconField)
                cardView.isEnabled = !gameField.isRotated
                cardView.rotationY = if (gameField.isRotated) 180f else 0f
                if (cardView.rotationY == 180F) itemFieldIcon.visibility = View.VISIBLE
                else itemFieldIcon.visibility = View.GONE
                synchronized(this@GameFieldAdapter) {
                    if (gameField.isRight) {
                        Log.d("IS_CLICKED", isClickedEnable.toString() + " 222")
                        isClickedEnable.set(false)
                        cardView.postDelayed(
                            {
                                cardView.visibility = View.GONE
                                checkIfWon()
                                isClickedEnable.set(true)
                            },
                            5000L
                        )
//                        scope.launch {
//                        isClickedEnable = false
//
//                            delay(5000L)
//                            cardView.visibility = View.GONE
//                            isClickedEnable = true
//                            checkIfWon()
//                        }
                    }
                }

                Log.d("IS_CLICKED", isClickedEnable.toString())
                if (isClickedEnable.get()) {
                    cardView.setOnClickListener {
                        cardView.isEnabled = false

                        fieldFlipped.onFlipped(
                            gameField = gameField,
                            position = position,
                            listOfGameField = listDiffer.currentList
                        )
                        val rotation = if (gameField.isRotated) 0f else 180f
                        val rotationAnimator =
                            ObjectAnimator.ofFloat(cardView, "rotationY", rotation)
                        rotationAnimator.duration = 300 // Adjust the duration as needed
                        rotationAnimator.interpolator = AccelerateDecelerateInterpolator()
                        rotationAnimator.start()
                    }
                }
            }
        }
    }

    private fun checkIfWon() {
        victory.checkTheVictory(listOfGameField = listDiffer.currentList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameFieldViewHolder {
        val binding =
            ItemGameFieldBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameFieldViewHolder(binding)
    }

    override fun getItemCount(): Int = listDiffer.currentList.size

    override fun onBindViewHolder(holder: GameFieldViewHolder, position: Int) {
        val gameField = listDiffer.currentList[position]
        holder.bind(gameField, position, isClickedEnable.get())
    }

    fun submitList(listOfGameField: List<GameField>) {
        listDiffer.submitList(listOfGameField)
    }
}

interface FieldFlipped {
    fun onFlipped(gameField: GameField, position: Int, listOfGameField: List<GameField>)
}

interface Victory {
    fun checkTheVictory(listOfGameField: List<GameField>)
}