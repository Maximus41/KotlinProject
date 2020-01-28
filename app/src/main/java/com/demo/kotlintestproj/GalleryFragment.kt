package com.demo.kotlintestproj

import Repository.Model.Picture
import Util.TOTAL_ITEM_COUNT
import Util.TOTAL_PAGE
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.kotlintestprojtestapp.PictureAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_gallery.*
import kotlinx.android.synthetic.main.fragment_gallery.view.*

class GalleryFragment(disposable: CompositeDisposable) : BaseFragment() {

    private lateinit var listener: OnFragmentInteractionListener
    private lateinit var viewModel: MainViewModel
    private var subscription : CompositeDisposable = disposable
    private lateinit var gallery : RecyclerView
    private lateinit var picAdapter : PictureAdapter
    private var currentPage : Int = 0
    private var loading : Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = activity!!.run { ViewModelProviders.of(this).get(MainViewModel::class.java) }
    }

    private fun onPicsLoaded(pics : ArrayList<Picture>) {
        if((pics == null || pics.isEmpty()) && picAdapter.itemCount == 0) {
            no_internet_layout.visibility = View.VISIBLE
            galleryView.visibility = View.GONE
        } else {
            no_internet_layout.visibility = View.GONE
            galleryView.visibility = View.VISIBLE
        }
        loading = false
        picAdapter.addPics(pics)
    }

    private fun onError(err : Throwable) {
        if(picAdapter.itemCount == 0) {
            no_internet_layout.visibility = View.VISIBLE
            galleryView.visibility = View.GONE
        }
        loading = false
        currentPage--
        Toast.makeText(activity, err.message, Toast.LENGTH_SHORT).show()
    }


    private fun initListeners() {
        val layoutManager = galleryView.layoutManager as LinearLayoutManager
        galleryView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(((layoutManager.findLastVisibleItemPosition() + 1) == (picAdapter.itemCount - 1))
                    && picAdapter.itemCount < TOTAL_ITEM_COUNT && currentPage < TOTAL_PAGE && !loading) {
                    Toast.makeText(activity, "Page No ${currentPage + 1} Loading", Toast.LENGTH_LONG).show()
                    loading = true
                    subscription.add(viewModel
                        .loadImages(calculateNextPage())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ pics -> onPicsLoaded(pics) }, { err -> onError(err) })
                    )
                }
            }
        })
    }

    fun calculateNextPage() : Int {
        currentPage += 1
        return currentPage
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }


    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(pic_id : Int)
    }

    companion object {
        @JvmStatic
        fun newInstance(subscription: CompositeDisposable) =
            GalleryFragment(subscription)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gallery = view.galleryView

        picAdapter = PictureAdapter(activity!!.applicationContext, ArrayList()) { pos -> listener!!.onFragmentInteraction(pos)}
        galleryView.layoutManager = LinearLayoutManager(activity)
        galleryView.adapter = picAdapter

        initListeners()
        subscription.add(viewModel.loadImages(calculateNextPage()).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ pics -> onPicsLoaded(pics) }, { err -> onError(err) })
        )
    }
}
