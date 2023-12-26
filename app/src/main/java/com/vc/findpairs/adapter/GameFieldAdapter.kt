package com.vc.findpairs.adapter

import android.animation.ObjectAnimator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vc.findpairs.databinding.ItemGameFieldBinding
import com.vc.findpairs.domain.model.GameFieldEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class GameFieldAdapter(
    private val fieldFlipped: FieldFlipped,
    private val victory: Victory
) : RecyclerView.Adapter<GameFieldAdapter.GameFieldViewHolder>() {

    private val scope = CoroutineScope(Dispatchers.Main)

    private val callback = object : DiffUtil.ItemCallback<GameFieldEntity>() {
        override fun areItemsTheSame(oldItem: GameFieldEntity, newItem: GameFieldEntity): Boolean =
            oldItem.iconField == newItem.iconField


        override fun areContentsTheSame(
            oldItem: GameFieldEntity,
            newItem: GameFieldEntity
        ): Boolean =
            oldItem == newItem

    }

    private var isClickedEnable = true
    private var isFirstBind = true


    private val listDiffer = AsyncListDiffer(this, callback)

    inner class GameFieldViewHolder(private val binding: ItemGameFieldBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(gameFieldEntity: GameFieldEntity, position: Int) {
            with(binding) {

                itemFieldIcon.setImageResource(gameFieldEntity.iconField)
                cardView.isEnabled = !gameFieldEntity.isRotated
                cardView.rotationY = if (gameFieldEntity.isRotated) 180f else 0f
                val rotation = if (gameFieldEntity.isRotated) 0f else 180f
                val rotationAnimator =
                    ObjectAnimator.ofFloat(cardView, "rotationY", rotation)
                rotationAnimator.duration = 300
                rotationAnimator.interpolator = AccelerateDecelerateInterpolator()
                rotationAnimator.start()
                if (cardView.rotationY == 180f) itemFieldIcon.visibility = View.VISIBLE
                else itemFieldIcon.visibility = View.GONE
                if (gameFieldEntity.isRight) {
                    Log.d("IS_CLICKED", isClickedEnable.toString() + " 222")
                    isClickedEnable = false
                    cardView.visibility = View.GONE
                    checkIfWon()
                    isClickedEnable = true
                }
                Log.d("IS_CLICKED", isClickedEnable.toString())

                cardView.setOnClickListener {
                    if (isClickedEnable) {
                        cardView.isEnabled = false

                        fieldFlipped.onFlipped(
                            gameFieldEntity = gameFieldEntity,
                            position = position,
                            listOfGameFieldEntity = listDiffer.currentList
                        )

                    }
                }
            }
        }
    }

    private fun checkIfWon() {
        victory.checkTheVictory(listOfGameFieldEntity = listDiffer.currentList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameFieldViewHolder {
        val binding =
            ItemGameFieldBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameFieldViewHolder(binding)
    }

    override fun getItemCount(): Int = listDiffer.currentList.size

    override fun onBindViewHolder(holder: GameFieldViewHolder, position: Int) {
        val gameField = listDiffer.currentList[position]
        holder.bind(gameField, position)
        isFirstBind = false
    }

    fun submitList(listOfGameFieldEntity: List<GameFieldEntity>) {
        listDiffer.submitList(listOfGameFieldEntity)
    }
}

interface FieldFlipped {
    fun onFlipped(
        gameFieldEntity: GameFieldEntity,
        position: Int,
        listOfGameFieldEntity: List<GameFieldEntity>
    )
}

interface Victory {
    fun checkTheVictory(listOfGameFieldEntity: List<GameFieldEntity>)
}