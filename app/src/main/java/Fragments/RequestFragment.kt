package Fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagramclone.utils.DONATE
import com.example.instagramclone.utils.POST
import com.example.solution_challenge.Adapter.DonateAdapter
import com.example.solution_challenge.databinding.FragmentRequestBinding
import com.example.solution_challenge.model.Donate
import com.example.solution_challenge.model.Post
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject


class RequestFragment : Fragment() {

private lateinit var binding : FragmentRequestBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_request, container, false)
        binding = FragmentRequestBinding.inflate( inflater, container, false)

         var donateList = ArrayList<Donate>()!!
         var donateAdapter = DonateAdapter(requireContext(),donateList)
         binding.donateRv.layoutManager = LinearLayoutManager(requireContext())
        binding.donateRv.adapter = donateAdapter

        val tempList = arrayListOf<Donate>()
        Firebase.firestore.collection(DONATE).get().addOnSuccessListener {
                result->
            tempList.clear()
            for (i in result){
                val donateItem :Donate = i.toObject()
                tempList.add(donateItem)
            }
            donateList.clear()
            donateList.addAll(tempList)
           donateAdapter.notifyDataSetChanged()
        }


//        binding.dir.setOnClickListener {
//
//            val source = "21.105510529892424, 79.00322354747368"
//            val destination = "21.095100665790703, 78.9783755653517"
//
//            val uri = Uri.parse("https://www.google.com/maps/dir/$source/$destination")
//            val intent = Intent(Intent.ACTION_VIEW, uri)
//            intent.`package` = "com.google.android.apps.maps"
//            startActivity(intent)
//
//        }





        return binding.root
    }

}