package com.demo.kotlintestproj

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() , GalleryFragment.OnFragmentInteractionListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var fragmentGallery : GalleryFragment
    private lateinit var fragmentPictureDetails : PictureDetailsFragment
    private lateinit var subscription: CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel :: class.java)
    }

    override fun onStart() {
        super.onStart()
        subscription = CompositeDisposable()
        initializeUi()
    }

    private fun initializeUi() {
        fragmentGallery = GalleryFragment.newInstance(subscription)
        fragmentPictureDetails = PictureDetailsFragment.newInstance(subscription)
        supportFragmentManager.beginTransaction().add(R.id.container, fragmentGallery, "Gallery_Fragment").commit()
    }

    override fun onFragmentInteraction(pic_id: Int) {
        var args = Bundle()
        args.putInt(PIC_ID, pic_id)
        fragmentPictureDetails.arguments = args
        supportFragmentManager.beginTransaction().add(R.id.container, fragmentPictureDetails, "Picture Deatils Fragment")
            .addToBackStack("Picture Details")
            .commit()
    }

    override fun onStop() {
        super.onStop()
        subscription.dispose()
    }
}
