package com.isaquliyev.githubsearchapp.ui.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.isaquliyev.githubsearchapp.R
import com.isaquliyev.githubsearchapp.databinding.FragmentDetailBinding
import com.isaquliyev.githubsearchapp.model.Item
import com.squareup.picasso.Picasso

class DetailFragment : Fragment() {

    private lateinit var binding : FragmentDetailBinding
    private lateinit var item : Item

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle : Bundle? = this.arguments
        if(bundle != null) {
            item = bundle.getSerializable("Item") as Item
        }


        Picasso.get().load(item.owner.avatar_url).into(binding.profilePhotoInDetails)
        binding.usernameInDetail.setText("Login: ${item.owner.login}")
        binding.repoNameInDetail.text = "Repository: ${item.name}"
        binding.descriptionInDetail.text = "Description: ${item.description}"
    }

}