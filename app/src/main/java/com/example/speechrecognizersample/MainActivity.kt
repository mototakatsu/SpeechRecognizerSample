package com.example.speechrecognizersample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.speechrecognizersample.databinding.ActivityMainBinding
import android.speech.RecognizerIntent
import android.content.Intent
import android.widget.ArrayAdapter
import java.util.*


class MainActivity : AppCompatActivity() {
    private val REQUEST_CODE = 1

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.presenter = this

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1)
        binding.listResult.adapter = adapter
    }

    fun onTapStartMic() {
        // 前回の結果をクリア
        adapter.clear()

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        // 認識する言語を指定
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH.toString());
//        // 認識する候補数の指定
//        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 10);
//        // 音声認識時に表示する案内を設定
//        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Please talk");
        // 音声認識を開始
        startActivityForResult(intent, REQUEST_CODE);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {

            // data から音声認識の結果を取り出す（リスト形式で）
            val extraResults = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)

            // 認識結果が一つ以上ある場合はテキストビューに結果を表示する
            if (extraResults.size > 0) {
                // 認識結果候補を全て表示する
                adapter.addAll(extraResults)
                adapter.notifyDataSetChanged()
            }
        }
    }
}
