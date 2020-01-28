package Injections

import com.demo.kotlintestproj.MainViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [WebserviceModule :: class])
interface WebserviceComponent {

    fun inject(viewModel: MainViewModel)
}