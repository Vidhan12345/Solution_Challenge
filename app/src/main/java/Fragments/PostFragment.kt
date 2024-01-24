package Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.solution_challenge.R
import com.example.solution_challenge.databinding.FragmentProfileBinding
import com.example.solution_challenge.databinding.FragmentRequestBinding


class PostFragment : Fragment() {

private lateinit var binding : FragmentRequestBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_request, container, false)
        binding = FragmentRequestBinding.inflate( inflater, container, false)
        return binding.root
    }

}