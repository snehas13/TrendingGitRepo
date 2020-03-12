package com.learn.gitrepo.presentation.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.learn.domain.GitRepo
import com.learn.gitrepo.databinding.LayoutRepoBinding
import androidx.databinding.DataBindingUtil
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_repo.view.*
import jp.wasabeef.picasso.transformations.CropCircleTransformation


class RepoAdapter : RecyclerView.Adapter<RepoAdapter.ViewHolder>() {

    private val items:MutableList<GitRepo> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutRepoBinding.inflate(inflater,parent,false)
        return ViewHolder(binding.root)
    }

    fun update(repos: List<GitRepo>) {
        items.clear()
        items.addAll(repos)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var binding : LayoutRepoBinding? = DataBindingUtil.bind(itemView)

        fun bind(repo: GitRepo) {

            binding?.repo = repo

            if(repo.languageColor.isNullOrBlank()) {
                itemView.langImg.visibility = View.GONE
            } else {
                itemView.langImg.setColorFilter(Color.parseColor(repo.languageColor))
            }

            Picasso.get().load(repo.avatar).transform(CropCircleTransformation()).into(itemView.image)

            itemView.setOnClickListener{

                itemView.group.visibility  =  if (itemView.group.visibility == View.GONE) View.VISIBLE else View.GONE

            }
        }
    }
}