package Fragments

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

        var postList =ArrayList<Post>()!!
        var adapter = PostAdapter(requireContext(), postList )
        binding.rv.layoutManager = LinearLayoutManager(this.context)
        binding.rv.adapter
        //        Making db for Post Images
        var tempList = arrayListOf<Post>()
        Firebase.firestore.collection(POST).get().addOnSuccessListener {
            for (i in it.documents){
                var post:Post = i.toObject<Post>()!!
                tempList.add(post)
                Toast.makeText(context,"Post Done ",Toast.LENGTH_SHORT).show()
            }
            postList.addAll(tempList)
            adapter.notifyDataSetChanged()
        }




        return binding.root
    }

}