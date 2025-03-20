# IPTV Sample App

This is a **Kotlin-based IPTV streaming app** that allows users to watch live TV channels from an **M3U8 playlist**. The app fetches channels dynamically from an **IPTV playlist URL** and plays them using **ExoPlayer**.

## 📌 Features
- 📺 **Stream Live TV Channels** using ExoPlayer.
- 🔍 **Search Functionality** to find channels easily.
- 📜 **Loads Channels from M3U8 Playlist**.
- 🎛 **Dynamically Updates the Channel List**.
- 🏎 **Fast & Lightweight** IPTV player.

## 🛠️ Tech Stack
- **Language:** Kotlin
- **Media Player:** ExoPlayer
- **Networking:** OkHttp
- **UI Components:** ListView, SearchView, PlayerView

## 🚀 How to Run the App
### 1️⃣ **Clone the Repository**
```sh
 git clone https://github.com/your-repo/iptv-sample-app.git
 cd iptv-sample-app
```

### 2️⃣ **Open in Android Studio**
- Open **Android Studio** and select **Open an Existing Project**.
- Choose the `iptv-sample-app` folder.

### 3️⃣ **Add Dependencies**
Ensure you have the following dependencies in `build.gradle`:
```gradle
dependencies {
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'com.google.android.exoplayer:exoplayer:2.19.1'
    implementation 'com.squareup.okhttp3:okhttp:4.9.3'
}
```

### 4️⃣ **Run the App**
- Click **Run** ▶️ in Android Studio or use `Shift + F10`.
- The IPTV channels will load from **https://iptv-org.github.io/iptv/index.category.m3u**.
- Search and play any available channel.

## 📜 How It Works
1. The app fetches an **M3U8 playlist** from `https://iptv-org.github.io/iptv/index.category.m3u`.
2. It **parses the playlist** to extract **channel names** and **stream URLs**.
3. The **channel list** is displayed using a **ListView**.
4. Users can **search for channels** dynamically.
5. Selecting a channel starts streaming via **ExoPlayer**.

## 🎯 Future Enhancements
- ⭐ **Favorite Channels** list
- 📅 **EPG (Electronic Program Guide)** support
- 🎭 **Channel Categories (Sports, Movies, News, etc.)**

## 📜 License
This project is **open-source** and free to use. Modify and improve as needed!

---
**Developed with ❤️ using Kotlin & ExoPlayer**

