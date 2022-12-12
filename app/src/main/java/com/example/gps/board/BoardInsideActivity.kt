package com.example.gps.board

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.gps.R
import com.example.gps.SplashActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage


class BoardInsideActivity : AppCompatActivity() {

    lateinit var imgIn: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_inside)

        //게시글 상세페이지

        //id값
        val tvInTitle = findViewById<TextView>(R.id.tvInTitle)
        val tvInTime = findViewById<TextView>(R.id.tvInTime)
        val tvInContent = findViewById<TextView>(R.id.tvInContent)

        val btnEdit = findViewById<Button>(R.id.btnEdit)
        val btnRemove = findViewById<Button>(R.id.btnRemove)

        imgIn = findViewById(R.id.imgIn)

        //해당 게시물의 상세내용을 가져와서 set해주자!
        val title = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")
        val time = intent.getStringExtra("time")

        //이미지를 Firebase에서 꺼내올 때 사용할 거임
        val key = intent.getStringExtra("key")

        tvInTitle.text = title.toString()
        tvInContent.text = content.toString()
        tvInTime.text = time.toString()



        //이미지 가져오기(게시물의 uid 값으로 이름을 지정했음)
        //받아온 이미지 key값을 넘겨주기!
        getImageData(key.toString())

        btnEdit.setOnClickListener {

            val intent = Intent(this@BoardInsideActivity, BoardWriteActivity::class.java)
            intent.putExtra("title",title)
            intent.putExtra("content",content)
//            intent.putExtra("image",content)
            startActivity(intent)
        }

        btnRemove.setOnClickListener {
            val mDatabase = FirebaseDatabase.getInstance();
            val dataRef = mDatabase.getReference("board");

            dataRef.removeValue();


        }




    }




    // Image를 가져오는 함수 만들기
    fun getImageData(key : String){
        val storageReference = Firebase.storage.reference.child("$key.png")

        storageReference.downloadUrl.addOnCompleteListener { task->
            //task: 데이터를 가져오는데 성공했는지 여부와 데이터 정보를 가지고 있음
            if (task.isSuccessful){
                Glide.with(this)
                    .load(task.result)
                    //into : imgIn에 업로드 하라는 것!
                    .into(imgIn)

            }
        }


    }



}