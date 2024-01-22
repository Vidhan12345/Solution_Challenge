package Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.solution_challenge.User
import com.example.solution_challenge.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ProfileFragment : Fragment() {

    private lateinit var binding : FragmentProfileBinding
    private lateinit var mAuth : FirebaseAuth
    private lateinit var mDbRef : DatabaseReference
    private lateinit var userDataList : ArrayList<User>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate( inflater, container, false)

        userDataList =  ArrayList()
        mAuth = FirebaseAuth.getInstance()
        val uid = mAuth.currentUser?.uid
        mDbRef = FirebaseDatabase.getInstance().reference


////        Setting name and email
        mDbRef.child("user").addValueEventListener(object  : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userDataList.clear()
                for (postSnapshot in snapshot.children) {
                    val userData = postSnapshot.getValue(User::class.java)
                    if (uid == userData?.uid) {
                        binding.pfName.text = userData?.name.toString()
                        binding.pfEmail.text = userData?.email.toString()
//                 userDataList.add(userData!!)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

            return  binding.root
    }
}