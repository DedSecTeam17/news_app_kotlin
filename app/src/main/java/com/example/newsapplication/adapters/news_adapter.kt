package com.example.newsapplication.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.newsapplication.R
import com.example.newsapplication.core.actions.NewsAction
import com.example.newsapplication.models.Article


class NewsListHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(
    inflater.inflate(
        R.layout.news_item, parent, false
    )
) {
    lateinit var newsItem: LinearLayout;
    private lateinit var title: TextView;
    private lateinit var author: TextView;
    private lateinit var newsImage: ImageView;


    init {
        newsItem = itemView.findViewById(R.id.news_design_row)
        title = itemView.findViewById(R.id.title)
        author = itemView.findViewById(R.id.author)
        newsImage = itemView.findViewById(R.id.newsImage)
    }

    fun bindData(article: Article) {
        title.text = article.title
        author.text = article.author



        article.imageUrl?.let {
            Glide.with(itemView.context)
                .load(it)
                .placeholder(R.drawable.news_place_holder)
                .thumbnail(0.1f)
                .diskCacheStrategy(DiskCacheStrategy.ALL) //3
                .transform( RoundedCorners(15))

                .into(newsImage)
        }

    }

}

class NewsListAdapter(
    private  var ctx: Context,
   private var newsAction: NewsAction,
   private var articles: List<Article>
) : RecyclerView.Adapter<NewsListHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsListHolder {
        val inflater = LayoutInflater.from(ctx)
        return NewsListHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: NewsListHolder, position: Int) {
        holder.bindData(article = this.articles[position])

        holder.newsItem.setOnClickListener {
            newsAction.onNewsClicked(article = this.articles[position]);
        }

    }

    override fun getItemCount(): Int = articles.size

    internal fun setList(articles: List<Article>) {
        this.articles = articles
        notifyDataSetChanged()
    }



}