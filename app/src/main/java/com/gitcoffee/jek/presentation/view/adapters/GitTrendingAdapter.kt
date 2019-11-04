package com.gitcoffee.jek.presentation.view.adapters

import android.content.res.Resources
import android.graphics.Color
import android.graphics.PorterDuff
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.gitcoffee.jek.R
import com.gitcoffee.jek.presentation.common.ViewAnimatorSlideUpDown
import com.gitcoffee.jek.presentation.models.TrendingRepoItem
import javax.inject.Inject

class GitTrendingAdapter @Inject constructor() :
    RecyclerView.Adapter<GitTrendingAdapter.GitRepoHolder>() {

    var trendingRepoItems: List<TrendingRepoItem> = ArrayList()

    private var prevSelectedPosition:Int = -1

    override fun getItemCount(): Int {
        return trendingRepoItems.size
    }

    override fun onBindViewHolder(holder: GitRepoHolder, position: Int) {
        holder.itemView.setOnClickListener {
            if(position == prevSelectedPosition){
                ViewAnimatorSlideUpDown.hideView(holder.rlExpandedView){
                    prevSelectedPosition = -1
                    notifyItemChanged(prevSelectedPosition)
                }
            }else{
                notifyItemChanged(prevSelectedPosition)
                prevSelectedPosition = position
                holder.rlExpandedView.visibility = View.VISIBLE
                ViewAnimatorSlideUpDown.slideDown(holder.rlExpandedView){
                    notifyItemChanged(prevSelectedPosition)
                }
            }

        }

        if(prevSelectedPosition != position){
            holder.rlExpandedView.visibility = View.GONE
        }else{
            holder.rlExpandedView.visibility = View.VISIBLE
        }

        holder.bind(trendingRepoItems[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitRepoHolder {
        return GitRepoHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_git_repo, parent, false)
        )
    }


    class GitRepoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvAuthorName: TextView = itemView.findViewById(R.id.tv_authorName)
        private val tvRepoName: TextView = itemView.findViewById(R.id.tv_repoName)
        private val ivIcon: ImageView = itemView.findViewById(R.id.iv_icon)

        private val tvStars: TextView = itemView.findViewById(R.id.tv_stars)
        private val tvForks: TextView = itemView.findViewById(R.id.tv_forks)
        private val tvDesc: TextView = itemView.findViewById(R.id.tv_desc)
        private val tvLanguage: TextView = itemView.findViewById(R.id.tv_language)

        val rlExpandedView:RelativeLayout = itemView.findViewById(R.id.rl_expandedView)


        fun bind(trendingRepoItem: TrendingRepoItem) {


            trendingRepoItem.author?.let {
                tvAuthorName.text = it
                tvAuthorName.visibility = View.VISIBLE
            } ?: run{
                tvAuthorName.visibility = View.GONE
            }

            trendingRepoItem.name?.let {
                tvRepoName.text = it
                tvRepoName.visibility = View.VISIBLE
            } ?: run{
                tvRepoName.visibility = View.GONE
            }

            trendingRepoItem.description?.let {
                tvDesc.text = it
                tvDesc.visibility = View.VISIBLE
            } ?: run{
                tvDesc.visibility = View.GONE
            }


            tvStars.text = "${trendingRepoItem.stars}"
            tvForks.text = "${trendingRepoItem.forks}"



            tvLanguage.visibility = if(!TextUtils.isEmpty(trendingRepoItem.language)){
                tvLanguage.text = "${trendingRepoItem.language}"

                val colorLength = trendingRepoItem.languageColor?.length ?:0
                val color:String
                val langColor = trendingRepoItem.languageColor?: ""
                color = if(colorLength == 4){
                    "#${langColor[1]}${langColor[1]}${langColor[2]}${langColor[2]}${langColor[3]}${langColor[3]}"
                }else{
                    langColor
                }
                if(!TextUtils.isEmpty(color)){
                    tvLanguage.compoundDrawables[0]?.setColorFilter(
                        Color.parseColor(color),
                        PorterDuff.Mode.SRC_ATOP
                    )
                }

                 View.VISIBLE
            }else{
                 View.GONE
            }



            Glide.with(ivIcon).load(trendingRepoItem.avatar)
                .apply(RequestOptions.circleCropTransform().override((32 * Resources.getSystem().displayMetrics.density).toInt()))
                .transition(DrawableTransitionOptions().crossFade())
                .into(ivIcon)

        }

    }
}