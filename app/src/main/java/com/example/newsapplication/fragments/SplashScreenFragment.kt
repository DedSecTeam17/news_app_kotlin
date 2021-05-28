package com.example.newsapplication.fragments

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.newsapplication.R
import com.example.newsapplication.core.actions.WaitAction
import com.example.newsapplication.core.utils.AppTimer
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashScreenFragment : Fragment() {


    @Inject
    lateinit var appTimer: AppTimer
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        appTimer.waitFor(seconds = 3, waitAction = object : WaitAction {
            override fun onFinish() {
                goToNewsPage();
            }

        })
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }


    private fun goToNewsPage() {
        findNavController().navigate(R.id.action_splashScreenFragment_to_newsFragment)


    }

}