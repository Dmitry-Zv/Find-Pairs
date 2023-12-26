package com.vc.findpairs.presentation.menuscreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import com.vc.findpairs.R
import com.vc.findpairs.databinding.FragmentMenuBinding
import com.vc.findpairs.domain.model.GameEntity
import com.vc.findpairs.presentation.gamescreen.GameFragment
import com.vc.findpairs.utils.collectLatestLifecycleFlow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MenuViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onEvent(event = MenuEvent.GetCurrentCoin)
        binding.playButton.setOnClickListener {
            requireActivity().supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<GameFragment>(R.id.fragment_container)
            }
        }
        collectCurrentCoin()
    }

    private fun collectCurrentCoin() {
        collectLatestLifecycleFlow(viewModel.currentCoin) { currentCoin ->
            Log.d("CURRENT_COIN", currentCoin.toString())
            binding.coinText.text = currentCoin.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}