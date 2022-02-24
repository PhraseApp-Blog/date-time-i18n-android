package com.example.assignment.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
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
import java.time.format.FormatStyle
import java.time.temporal.ChronoUnit.DAYS

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
                ViewCompat.setTransitionName(item_container, article.urlToImage.orEmpty())

                val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                val utcString = article.publishedAt
                //Obtains an instance of Instant from a text string such as 2007-12-03T10:15:30.00Z.
                val timestamp = Instant.parse(utcString)
                val articlePublishedZonedTime = ZonedDateTime.ofInstant(timestamp, ZoneId.systemDefault())
                // Current moment in UTC.
                val currentTimestamp = Instant.now()
                val currentZonedTime = ZonedDateTime.ofInstant(currentTimestamp, ZoneId.systemDefault())


                val dayGap = articlePublishedZonedTime.toLocalDate().until(currentZonedTime, DAYS)
                val date = when(dayGap){
                    0L -> context.getString(R.string.today)
                    1L -> context.getString(R.string.yesterday)
                    else -> articlePublishedZonedTime.format(dateFormatter)
                }

                textview_date_time.text = date.toString()

                val internationizedDateTimePublished: String = articlePublishedZonedTime.format(dateFormatter)
                val internationizedDateTimeNow: String = currentZonedTime.format(dateFormatter)


                textview_date_time_published.text = "Published: $internationizedDateTimePublished"
                textview_date_time_current.text = "Current: $internationizedDateTimeNow"


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

fun LocalDate.isYesterday(): Boolean = this.isEqual(LocalDate.now().minusDays(1L))

fun LocalDate.isToday(): Boolean = this.isEqual(LocalDate.now())