package com.gan.breakingbad.characters.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gan.breakingbad.R
import com.gan.breakingbad.characters.data.BreakingBadCharacter
import com.gan.breakingbad.databinding.FragmentSecondBinding
import kotlinx.android.synthetic.main.fragment_second.*


class BreakingBadCharacterFragment : Fragment() {

    private lateinit var binding: FragmentSecondBinding
    private var breakingBadCharacter: BreakingBadCharacter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_second, container, false
        )
        breakingBadCharacter = arguments?.getParcelable("breakingBadCharacter")
        binding.breakingBadCharacter = breakingBadCharacter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)
        (activity as? AppCompatActivity)?.supportActionBar?.title = breakingBadCharacter?.name
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }


}

