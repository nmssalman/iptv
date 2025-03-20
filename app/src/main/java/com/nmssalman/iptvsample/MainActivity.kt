package com.nmssalman.iptvsample

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerView
import com.nmssalman.iptvsample.ui.theme.IPTVSampleTheme
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class MainActivity : ComponentActivity() {
    private var player: ExoPlayer? = null
    private lateinit var playerView: PlayerView
    private lateinit var channelListView: ListView
    private val channels = mutableListOf<Pair<String, String>>() // (Channel Name, M3U8 URL)

    private val iptvPlaylistUrl = "https://iptv-org.github.io/iptv/index.category.m3u"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         setContentView(R.layout.activity_main)

        playerView = findViewById(R.id.videoView)
        channelListView = findViewById(R.id.channelListView)

        // Load IPTV playlist
        loadM3UPlaylist()

        channelListView.setOnItemClickListener { _, _, position, _ ->
            val selectedChannel = channels[position]
            playChannel(selectedChannel.second)
        }

    }

    private fun loadM3UPlaylist() {
        val client = OkHttpClient()
        val request = Request.Builder().url(iptvPlaylistUrl).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
               Log.i("ExceptionIO", e.message.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val m3uData = response.body?.string()
                if (m3uData != null) {
                    parseM3UPlaylist(m3uData)
                }
            }
        })
    }

    private fun parseM3UPlaylist(m3uData: String) {
        val lines = m3uData.lines()
        var channelName = ""

        for (line in lines) {
            if (line.startsWith("#EXTINF")) {
                channelName = line.substringAfter(",").trim()
            } else if (line.startsWith("http") && channelName.isNotEmpty()) {
                channels.add(Pair(channelName, line.trim()))
            }
        }

        runOnUiThread {
            val adapter = ChannelAdapter(this, channels)
            channelListView.adapter = adapter
        }
    }

    private fun playChannel(url: String) {
        player?.release()

        player = ExoPlayer.Builder(this).build()
        playerView.player = player

        val mediaItem = MediaItem.fromUri(Uri.parse(url))
        player?.setMediaItem(mediaItem)
        player?.prepare()
        player?.playWhenReady = true
    }

    override fun onStop() {
        super.onStop()
        player?.release()
        player = null
    }

    inner class ChannelAdapter(context: Context, private val channels: List<Pair<String, String>>) :
        ArrayAdapter<Pair<String, String>>(context, R.layout.list_item, channels) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
            val textView = view.findViewById<TextView>(R.id.channelName)
            textView.text = channels[position].first
            return view
        }
    }
}


