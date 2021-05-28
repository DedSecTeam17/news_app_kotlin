package com.example.newsapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.newsapplication.R
import com.example.newsapplication.databinding.FragmentNewsBinding
import com.example.newsapplication.databinding.FragmentNewsDetailBinding
import com.example.newsapplication.models.Article
import com.google.gson.Gson


class NewsDetailFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val fragmentNewsDetailBinding: FragmentNewsDetailBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_news_detail, container, false)
        bindDataToView(
            fragmentNewsDetailBinding = fragmentNewsDetailBinding,
            article = getArticleFromArgument()
        );
        return fragmentNewsDetailBinding.root
    }

    private fun getArticleFromArgument(): Article {
        val args: NewsDetailFragmentArgs by navArgs()
        var articleAsJson = args.article
        val gson = Gson()
        return gson.fromJson(articleAsJson, Article::class.java)
    }


    private fun bindDataToView(
        fragmentNewsDetailBinding: FragmentNewsDetailBinding,
        article: Article
    ) {
        fragmentNewsDetailBinding.detailTitle.text = article.title
        fragmentNewsDetailBinding.authorInfo.text = article.author
        fragmentNewsDetailBinding.detailDescription.text = article.description

        article.imageUrl?.let {
            Glide.with(requireActivity())
                .load(it)
                .placeholder(R.drawable.news_place_holder)
                .thumbnail(0.1f)
                .diskCacheStrategy(DiskCacheStrategy.ALL) //3
                .transform(RoundedCorners(15))

                .into(fragmentNewsDetailBinding.newsImagePreview)
        }
    }


}