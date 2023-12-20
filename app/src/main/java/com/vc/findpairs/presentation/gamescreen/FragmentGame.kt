package com.vc.findpairs.presentation.gamescreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.vc.findpairs.R
import com.vc.findpairs.adapter.FieldFlipped
import com.vc.findpairs.adapter.GameFieldAdapter
import com.vc.findpairs.adapter.Victory
import com.vc.findpairs.databinding.FragmentGameBinding
import com.vc.findpairs.domain.model.GameField
import com.vc.findpairs.utils.CenterGridItemDecoration
import com.vc.findpairs.utils.collectLatestLifecycleFlow
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FragmentGame : Fragment(), FieldFlipped, Victory {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    private val viewModel: GameViewModel by viewModels()
    private lateinit var adapter: GameFieldAdapter
    private var list = mutableListOf(
        GameField(
            R.drawable.timer_24,
            isRotated = false,
            isRight = false
        ),
        GameField(
            R.drawable.timer_24,
            isRotated = false,
            isRight = false
        ),
        GameField(
            R.drawable.timer_24,
            false,
            isRight = false
        ),
        GameField(
            R.drawable.timer_24,
            false,
            isRight = false
        ),
        GameField(
            R.drawable.timer_24,
            false,
            isRight = false
        ),
        GameField(
            R.drawable.timer_24,
            false,
            isRight = false
        ),
        GameField(
            R.drawable.timer_24,
            false,
            isRight = false
        ),
        GameField(
            R.drawable.timer_24,
            false,
            isRight = false
        ),
        GameField(
            R.drawable.timer_24,
            false,
            isRight = false
        ),
        GameField(
            R.drawable.timer_24,
            false,
            isRight = false
        ),
        GameField(
            R.drawable.timer_24,
            false,
            isRight = false
        ),
        GameField(
            R.drawable.timer_24,
            false,
            isRight = false
        )
    )


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            gameField.layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = GameFieldAdapter(this@FragmentGame, this@FragmentGame)
            gameField.adapter = adapter
            val spacing = calculateSpacing()
            gameField.addItemDecoration(CenterGridItemDecoration(spacing = spacing))
        }
        adapter.submitList(
            listOfGameField = list.toList()

        )
        viewModel.onEvent(event = GameEvent.StartTimer)
        collectTimer()
        updateList()
    }

    private fun updateList() {
        collectLatestLifecycleFlow(viewModel.listOfGameField) { listOfGameField ->
            if (listOfGameField.isNotEmpty()) {
                adapter.submitList(listOfGameField)
            }
        }
    }

    private fun collectTimer() {
        collectLatestLifecycleFlow(viewModel.time) { time ->
            binding.timerText.text = time
        }
    }

    private fun calculateSpacing(): Int {
        val displayMetrics = resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels

        // Set the number of columns in GridLayoutManager
        val spanCount = 3 // For example, 3 columns

        // Set the desired spacing between items
        val spacing = 16 // For example, 16dp

        // Calculate the item width based on the number of columns and spacing
        val itemWidth = (screenWidth - (spanCount - 1) * spacing) / spanCount

        // Calculate the actual spacing between items
        return (screenWidth - itemWidth * spanCount) / (spanCount + 1)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onFlipped(gameField: GameField, position: Int, listOfGameField: List<GameField>) {
        val mutableListOfGameField = listOfGameField.toMutableList()
        mutableListOfGameField[position] =
            GameField(
                iconField = gameField.iconField,
                isRotated = !gameField.isRotated,
                isRight = gameField.isRight
            )
        viewModel.onEvent(
            event = GameEvent.CheckTwoField(
                listOfGameField = mutableListOfGameField,
                gameField = mutableListOfGameField[position],
                position = position
            )
        )
    }

    override fun checkTheVictory(listOfGameField: List<GameField>) {
        if (listOfGameField.all { gameField -> gameField.isRight }) {
            Toast.makeText(
                requireContext(),
                "Congratulation, you are won",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}