package Fragments

import android.app.ActionBar
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagramclone.utils.POST
import com.example.solution_challenge.Adapter.PostAdapter
import com.example.solution_challenge.DonateActivity
import com.example.solution_challenge.PostActivity
import com.example.solution_challenge.databinding.FragmentHomeBinding
import com.example.solution_challenge.model.Post
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject


class HomeFragment : Fragment() {
private lateinit var binding:FragmentHomeBinding
private lateinit var intent: Intent

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home, container, false)
        binding = FragmentHomeBinding.inflate( inflater, container, false)
        binding.fhBtnPost.setOnClickListener {
            intent = Intent(requireContext(),PostActivity::class.java)
            startActivity(intent)
        }
        binding.donate.setOnClickListener {
            intent = Intent(requireContext(),DonateActivity::class.java)
            startActivity(intent)
        }


        var postList = ArrayList<Post>()!!
        var postadapter = PostAdapter(requireContext(), postList )
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        binding.rv.adapter = postadapter

        //        Making db for Post Images
        val tempList = arrayListOf<Post>()
        Firebase.firestore.collection(POST).get().addOnSuccessListener {
            result->
            tempList.clear()
            postList.clear()
            for (i in result){
                val post:Post = i.toObject()
                tempList.add(post)
            }
            postList.addAll(tempList)
         postadapter.notifyDataSetChanged()
        }
        return binding.root
    }

}