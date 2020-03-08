package com.learn.gitrepo.presentation.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.learn.domain.GitRepo
import com.learn.gitrepo.databinding.LayoutRepoBinding
import androidx.databinding.DataBindingUtil
import kotlinx.android.synthetic.main.layout_repo.view.*

class RepoAdapter(private val items: List<GitRepo>) : RecyclerView.Adapter<RepoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutRepoBinding.inflate(inflater)
        return ViewHolder(binding.root)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var binding : LayoutRepoBinding? = DataBindingUtil.bind(itemView)

        fun bind(repo: GitRepo) {

            binding?.repo = repo

            itemView.langImg.setColorFilter(Color.parseColor(repo.languageColor),android.graphics.PorterDuff.Mode.MULTIPLY)

            itemView.setOnClickListener{

                if(itemView.group.visibility == View.GONE){
                    itemView.group.visibility = View.VISIBLE
                } else {
                    itemView.group.visibility = View.GONE
                }
            }
        }
    }
}