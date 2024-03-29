package com.example.assignment.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.example.assignment.domain.model.Article
import com.example.assignment.ui.util.ImageLoader
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_article.view.*
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit.DAYS
import java.time.format.FormatStyle.MEDIUM

class NewsRecyclerAdapter(val callback: RecyclerViewClickListener) :
    ListAdapter<Article, NewsRecyclerAdapter.ArticleViewHolder>(UserDataAdapterListDiff()) {

    interface RecyclerViewClickListener {
        fun onItemClicked(view: View, article: Article)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return ArticleViewHolder(view)
    }


    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class UserDataAdapterListDiff : DiffUtil.ItemCallback<Article>() {

        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {

            //The json contains no unique ID, hence using the unique url
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    inner class ArticleViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bind(article: Article) {

            with(containerView) {

              val timestampInstant = Instant.parse(article.publishedAt)
              val articlePublishedZonedTime = ZonedDateTime.ofInstant(timestampInstant, ZoneId.systemDefault())
              val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(MEDIUM)
              val currentTimestamp = Instant.now()
              //Get current Instant
              val currentZonedTime = ZonedDateTime.ofInstant(currentTimestamp, ZoneId.systemDefault())
              //Convert current Instant to local time zone
              val gapInDays = articlePublishedZonedTime.toLocalDate().until(currentZonedTime, DAYS)
                val finalDate = when(gapInDays){
                    0L -> context.getString(R.string.today)
                    1L -> context.getString(R.string.yesterday)
                    else -> articlePublishedZonedTime.format(dateFormatter)
                }
              textview_date_time.text = finalDate

              textview_description.text = article.description
                article.urlToImage?.let { it1 ->
                    ImageLoader.loadImage(context, it1, image_thumbnail)
                } ?: kotlin.run {
                    ImageLoader.loadImage(context, R.drawable.placeholder, image_thumbnail)
                }
                setOnClickListener { callback.onItemClicked(itemView, article) }
            }
        }
    }
}