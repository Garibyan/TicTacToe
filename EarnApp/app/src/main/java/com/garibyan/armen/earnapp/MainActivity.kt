package com.garibyan.armen.earnapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.garibyan.armen.earnapp.adapters.CategoryAdapter
import com.garibyan.armen.earnapp.adapters.ContentManager
import com.garibyan.armen.earnapp.databinding.ActivityMainBinding
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import java.util.*
import kotlin.random.Random

class MainActivity : AppCompatActivity(), CategoryAdapter.Listener, Animation.AnimationListener {
    private lateinit var binding: ActivityMainBinding
    private var interAd: InterstitialAd? = null
    private var adapter: CategoryAdapter? = null
    private var posM: Int = 0
    private lateinit var inAnimation: Animation
    private lateinit var outAnimation: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        inAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha_in)
        outAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha_out)
        outAnimation.setAnimationListener(this)
        initAdMob()
        (application as AppMainState).showAdIfAvailable(this) {}
        initRcView()
        binding.imageBg.setOnClickListener() {
        }
    }

    private fun initRcView() = with(binding) {
        adapter = CategoryAdapter(this@MainActivity)
        rcViewCat.layoutManager = LinearLayoutManager(
            this@MainActivity,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        rcViewCat.adapter = adapter
        adapter?.submitList(ContentManager.list)
    }

    override fun onResume() {
        super.onResume()
        binding.adView.resume()
        loadInternalAd()
    }

    override fun onPause() {
        super.onPause()
        binding.adView.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.adView.destroy()
    }


    private fun initAdMob() {
        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    private fun loadInternalAd() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(this,
            "ca-app-pub-3940256099942544/1033173712",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    interAd = null
                }

                override fun onAdLoaded(ad: InterstitialAd) {
                    interAd = ad
                }
            })
    }

    private fun showInterAd() {
        if (interAd != null) {
            interAd?.fullScreenContentCallback =
                object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        showContent()
                        interAd = null
                        loadInternalAd()
                    }

                    override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                        showContent()
                        interAd = null
                        loadInternalAd()
                    }

                    override fun onAdShowedFullScreenContent() {
                        interAd = null
                        loadInternalAd()
                    }
                }
            interAd?.show(this)
        } else {
            showContent()
        }
    }

    private fun showContent() {
        Toast.makeText(this, "Content started", Toast.LENGTH_SHORT).show()
    }

    fun getMessage() = with(binding) {
        tvMessage.startAnimation(inAnimation)
        tvName.startAnimation(inAnimation)
        imageBg.startAnimation(inAnimation)
        val currentArrey = resources.getStringArray(MainConst.arrayList[posM])
        val message = currentArrey[Random.nextInt(currentArrey.size)]
        val messageList = message.split("|")
        tvMessage.text = messageList[0]
        tvName.text = messageList[1]
        imageBg.setImageResource(MainConst.imageList[Random.nextInt(4)])
    }

    override fun onClick(pos: Int) {
        binding.apply {
            tvMessage.startAnimation(outAnimation)
            tvName.startAnimation(outAnimation)
            imageBg.startAnimation(outAnimation)
        }
        posM = pos
    }

    override fun onAnimationStart(p0: Animation?) {

    }

    override fun onAnimationEnd(p0: Animation?) {
        getMessage()
    }

    override fun onAnimationRepeat(p0: Animation?) {
    }
}