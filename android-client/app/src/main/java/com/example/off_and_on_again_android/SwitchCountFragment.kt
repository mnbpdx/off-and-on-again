package com.example.off_and_on_again_android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        viewModel.setupSocketConnection()?.let { socket = it }
        socket.on("switch-response", onSwitchResponse)
        socket.connect()
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

        socket.disconnect()
        socket.off("switch-response", onSwitchResponse)
        _binding = null
    }
}
