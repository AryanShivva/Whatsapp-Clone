package com.example.whatsapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whatsapp.R
import com.example.whatsapp.adapter.MessageAdapter
import com.example.whatsapp.databinding.ActivityChatBinding
import com.example.whatsapp.databinding.ActivityNumberBinding
import com.example.whatsapp.model.MessageModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Date

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var database: FirebaseDatabase

    private lateinit var senderUid : String
    private lateinit var recieverUid : String

    private lateinit var senderRoom : String
    private lateinit var recieverRoom : String

    private lateinit var List : ArrayList<MessageModel>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)


        senderUid = FirebaseAuth.getInstance().uid.toString()
        recieverUid = intent.getStringExtra("uid")!!

        senderRoom = senderUid + recieverUid
        recieverRoom = recieverUid + senderUid

        List = ArrayList()

        database = FirebaseDatabase.getInstance()

        val messageAdapter = MessageAdapter(this, List)
        binding.recyclerview.adapter = messageAdapter
        // Don't forget to set a LayoutManager
        binding.recyclerview.layoutManager = LinearLayoutManager(this)



        binding.imageView2.setOnClickListener{
            if(binding.messagebox.text.isEmpty()){
                Toast.makeText(this@ChatActivity,"please enter your message",Toast.LENGTH_SHORT).show()
            }
            else{

                val message = MessageModel(binding.messagebox.text.toString(),senderUid,Date().time)

                val randomKey = database.reference.push().key

                database.reference.child("chats")
                    .child(senderRoom).child("message").child(randomKey!!).setValue(message)
                    .addOnSuccessListener {


                        database.reference.child("chats")
                            .child(recieverRoom).child("message").child(randomKey!!).setValue(message)
                            .addOnSuccessListener{
                                binding.messagebox.text = null
                                Toast.makeText(this,"message sent!!",Toast.LENGTH_SHORT).show()
                            }


                    }



            }
        }

        database.reference.child("chats").child(senderRoom).child("message")
            .addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    List.clear()

                    for (snapshot1 in snapshot.children){
                        val data = snapshot1.getValue(MessageModel::class.java)
                        List.add(data!!)
                    }

                    binding.recyclerview.adapter = MessageAdapter(this@ChatActivity,List)
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@ChatActivity,"Error : $error",Toast.LENGTH_SHORT).show()
                }


            })






    }
}