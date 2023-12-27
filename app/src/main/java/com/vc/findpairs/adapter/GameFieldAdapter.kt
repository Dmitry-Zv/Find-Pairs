package com.vc.findpairs.adapter

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vc.findpairs.R
import com.vc.findpairs.databinding.ItemGameFieldBinding
import com.vc.findpairs.domain.model.GameFieldEntity

class GameFieldAdapter(
    private val fieldFlipped: FieldFlipped,
    private val victory: Victory,
) : RecyclerView.Adapter<GameFieldAdapter.GameFieldViewHolder>() {


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
                if (gameFieldEntity.isRotated != null) {
                    cardView.isEnabled = !gameFieldEntity.isRotated
                    cardView.rotationY = if (gameFieldEntity.isRotated) 0f else 180f
                    if (!gameFieldEntity.isRotated) {
                        flipAnimatorReverse(binding.root, itemFieldIcon = itemFieldIcon)
                    } else {
                        flipAnimator(binding.root, itemFieldIcon)
                    }
                } else {
                    cardView.isEnabled = true
                    itemFieldIcon.visibility = View.GONE
                }

                if (cardView.rotationY == 180f) itemFieldIcon.visibility = View.VISIBLE
                else itemFieldIcon.visibility = View.GONE
                if (gameFieldEntity.isRight) {
                    isClickedEnable = false
                    cardView.visibility = View.GONE
                    checkIfWon()
                    isClickedEnable = true
                }

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

    private fun flipAnimator(view: View, itemFieldIcon: ImageView) {
        val flipAnimatorReverse = AnimatorInflater.loadAnimator(
            view.context,
            R.animator.flip_animator
        ) as AnimatorSet
        flipAnimatorReverse.setTarget(view)
        flipAnimatorReverse.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                isClickedEnable = false
                itemFieldIcon.visibility = View.GONE
            }

            override fun onAnimationEnd(animation: Animator) {
                isClickedEnable = true
                itemFieldIcon.visibility = View.VISIBLE
            }

            override fun onAnimationCancel(animation: Animator) {
            }

            override fun onAnimationRepeat(animation: Animator) {
            }

        })
        flipAnimatorReverse.start()
    }

    private fun flipAnimatorReverse(view: View, itemFieldIcon: ImageView) {
        val flipAnimator = AnimatorInflater.loadAnimator(
            view.context,
            R.animator.flip_animator_reverse
        ) as AnimatorSet
        flipAnimator.setTarget(view)
        flipAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                isClickedEnable = false
                itemFieldIcon.visibility = View.VISIBLE
            }

            override fun onAnimationEnd(animation: Animator) {
                isClickedEnable = true
                itemFieldIcon.visibility = View.GONE
            }

            override fun onAnimationCancel(animation: Animator) {
            }

            override fun onAnimationRepeat(animation: Animator) {
            }

        })
        flipAnimator.start()
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
