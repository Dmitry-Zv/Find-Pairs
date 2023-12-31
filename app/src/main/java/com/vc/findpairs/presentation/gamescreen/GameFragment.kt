package com.vc.findpairs.presentation.gamescreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.vc.findpairs.adapter.FieldFlipped
import com.vc.findpairs.adapter.GameFieldAdapter
import com.vc.findpairs.adapter.Victory
import com.vc.findpairs.databinding.FragmentGameBinding
import com.vc.findpairs.domain.model.GameFieldEntity
import com.vc.findpairs.presentation.MainViewModel
import com.vc.findpairs.presentation.Navigation
import com.vc.findpairs.utils.collectLatestLifecycleFlow
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GameFragment : Fragment(), FieldFlipped, Victory {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    private val viewModel: GameViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var adapter: GameFieldAdapter
    private var currentCoin = 0


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
            adapter = GameFieldAdapter(this@GameFragment, this@GameFragment)
            gameField.adapter = adapter
        }
        viewModel.onEvent(event = GameEvent.GetGameEntity)
        viewModel.onEvent(event = GameEvent.GetCoin)
        viewModel.onEvent(event = GameEvent.StartTimer)
        collectGameEntity()
        collectCoin()
        collectTimer()
        updateList()
        checkCongratulation()
    }

    private fun collectGameEntity() {
        collectLatestLifecycleFlow(viewModel.gameEntityState) { gameEntity ->
            gameEntity?.let {
                binding.gameField.layoutManager = object :
                    GridLayoutManager(requireContext(), it.countOfColumns) {
                    override fun canScrollHorizontally(): Boolean {
                        return false
                    }

                    override fun canScrollVertically(): Boolean {
                        return false
                    }
                }
                viewModel.onEvent(event = GameEvent.GetGameFieldEntity)
            }
        }
    }

    private fun collectCoin() {
        collectLatestLifecycleFlow(viewModel.coin) { coin ->
            currentCoin = coin.currentCoin
            binding.coinText.text = coin.currentCoin.toString()
        }
    }

    private fun checkCongratulation() {
        collectLatestLifecycleFlow(viewModel.congratulation) { congratulation ->
            if (congratulation) {
                mainViewModel.onEvent(event = Navigation.OnEndGameFragment)
            }
        }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onFlipped(
        gameFieldEntity: GameFieldEntity,
        position: Int,
        listOfGameFieldEntity: List<GameFieldEntity>
    ) {
        val mutableListOfGameField = listOfGameFieldEntity.toMutableList()
        mutableListOfGameField[position] =
            GameFieldEntity(
                id = gameFieldEntity.id,
                iconField = gameFieldEntity.iconField,
                isRotated = true,
                isRight = gameFieldEntity.isRight
            )
        viewModel.onEvent(
            event = GameEvent.ReverseField(
                listOfGameFieldEntity = mutableListOfGameField,
                gameFieldEntity = mutableListOfGameField[position],
                position = position
            )
        )
    }

    override fun checkTheVictory(listOfGameFieldEntity: List<GameFieldEntity>) {
        if (listOfGameFieldEntity.all { gameField -> gameField.isRight }) {
            viewModel.onEvent(event = GameEvent.Congratulation(currentCoin = currentCoin))
        }
    }
}