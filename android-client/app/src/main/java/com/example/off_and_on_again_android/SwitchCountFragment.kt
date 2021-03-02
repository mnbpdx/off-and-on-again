package com.example.off_and_on_again_android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.off_and_on_again_android.databinding.FragmentSwitchCountBinding
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONException
import org.json.JSONObject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class SwitchCountFragment : Fragment() {

    private lateinit var viewModel: SwitchCountViewModel

    private var _binding: FragmentSwitchCountBinding? = null

    private lateinit var socket: Socket

    private val binding get() = _binding!!

    private val onSwitchResponse = Emitter.Listener { args ->
        activity?.let {
            it.runOnUiThread(
                Runnable {
                    val data = args[0] as JSONObject
                    try {
                        viewModel.updateFlipCount(data)
                    } catch (e: JSONException) {
                        return@Runnable
                    }
                }
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSwitchCountBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(SwitchCountViewModel::class.java)
        setUpSocket()
        return binding.root
    }

    private fun setUpSocket() {
        viewModel.setupSocketConnection()?.let { socket = it }
        socket.on("switch-response", onSwitchResponse)
        socket.connect()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeFlipCount()
        observeIsOn()

//        binding.buttonFirst.setOnClickListener {
//            viewModel.getSwitchCount()
//        }
    }

    private fun observeIsOn() {
        viewModel.isOn.observe(viewLifecycleOwner) { isOn ->
            when (isOn) {
                true -> {
                    binding.offIndicator.isVisible = false
                    binding.onIndicator.isVisible = true
                }
                false -> {
                    binding.onIndicator.isVisible = false
                    binding.offIndicator.isVisible = true
                }
            }
        }
    }

    private fun observeFlipCount() {
        viewModel.flipCount.observe(viewLifecycleOwner) {
            binding.flipCountValue.text = viewModel.flipCount.value.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disconnectSocket()
        _binding = null
    }

    private fun disconnectSocket() {
        socket.disconnect()
        socket.off("switch-response", onSwitchResponse)
    }
}
