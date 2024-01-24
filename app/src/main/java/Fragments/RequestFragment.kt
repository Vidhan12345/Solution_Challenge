package Fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.solution_challenge.databinding.FragmentRequestBinding


class RequestFragment : Fragment() {

private lateinit var binding : FragmentRequestBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_request, container, false)
        binding = FragmentRequestBinding.inflate( inflater, container, false)

        binding.dir.setOnClickListener {

            val source = "21.105510529892424, 79.00322354747368"
            val destination = "21.095100665790703, 78.9783755653517"

            val uri = Uri.parse("https://www.google.com/maps/dir/$source/$destination")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.`package` = "com.google.android.apps.maps"
            startActivity(intent)

        }
        return binding.root
    }

}