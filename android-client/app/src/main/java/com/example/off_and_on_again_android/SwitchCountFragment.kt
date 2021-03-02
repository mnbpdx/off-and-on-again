package com.example.off_and_on_again_android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.off_and_on_again_android.databinding.FragmentSwitchCountBinding
import io.socket.client.Socket

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class SwitchCountFragment : Fragment() {

    private lateinit var viewModel: SwitchCountViewModel

    private var _binding: FragmentSwitchCountBinding? = null

    private lateinit var socket: Socket

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSwitchCountBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(SwitchCountViewModel::class.java)
        viewModel.setupSocketConnection()?.let { socket = it }
        socket.connect()
        activity?.let { viewModel.getSwitchCountRealTime(it) }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.flipCount.observe(viewLifecycleOwner) {
            binding.flipCountValue.text = viewModel.flipCount.value.toString()
        }

//        binding.buttonFirst.setOnClickListener {
//            viewModel.getSwitchCount()
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
