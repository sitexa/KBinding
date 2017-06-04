package com.sitexa.android.ui.fragment

import android.support.v4.app.Fragment
import com.benny.library.kbinding.bind.BindingDelegate
import com.benny.library.kbinding.bind.BindingDisposer
import com.benny.library.kbinding.view.BindingDisposerGenerator
import com.benny.library.kbinding.viewmodel.ViewModel

/**
 * Created by open on 04/06/2017.
 */


open class BaseFragment : Fragment(), BindingDisposerGenerator, BindingDelegate {
    override val viewModel: ViewModel = ViewModel()
    override val bindingDisposer: BindingDisposer = BindingDisposer()

    override fun onDestroy() {
        super.onDestroy()
        bindingDisposer.unbind()
    }

}