package Injections

import Repository.PictureRepo
import com.kotlin.testapp.MainViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [WebserviceModule :: class])
interface WebserviceComponent {

    fun inject(viewModel: MainViewModel)
}