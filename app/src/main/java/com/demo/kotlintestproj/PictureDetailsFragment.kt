package com.demo.kotlintestproj


import Repository.Model.Picture
import Util.APPLICATION_TAG
import Util.isInternetAvailable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_picture_details.view.*

 const val PIC_ID = "picId"

class PictureDetailsFragment(private val disposable : CompositeDisposable) : BaseFragment() {
    private var picId: Int = 0
    private lateinit var viewModel : MainViewModel

    private lateinit var title : TextView
    private lateinit var desc : TextView
    private lateinit var uploader : TextView
    private lateinit var loc : TextView
    private lateinit var image : ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        picId = arguments!!.getInt(PIC_ID)
        viewModel = activity!!.run { ViewModelProviders.of(this).get(MainViewModel::class.java) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        title = view.imageTitle
        desc = view.imageDesc
        uploader = view.imageUploader
        loc = view.geoTag
        image = view.image
        disposable.add(
            viewModel!!.loadImageById(picId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ pic -> onPictureLoaded(pic) }, { err -> onError(err) })!!
        )
    }


    private fun onPictureLoaded(pic : Picture) {
        title.text = pic.title
        desc.text = pic.desc
        uploader.text = pic.uploader
        loc.text = pic.loc
        Picasso.get().load(pic.avatar)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.error)
            .fit()
            .centerInside()
            .into(image)
    }

    private fun onError(err : Throwable) {
        Log.i(APPLICATION_TAG, "onError(PictureDetailsFragment)")
        Toast.makeText(activity, err.message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        @JvmStatic
        fun newInstance(disposable: CompositeDisposable) =
            PictureDetailsFragment(disposable)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_picture_details, container, false)
    }
}
