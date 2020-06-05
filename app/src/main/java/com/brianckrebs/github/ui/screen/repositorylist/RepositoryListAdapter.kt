package com.brianckrebs.github.ui.screen.repositorylist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.brianckrebs.github.R
import com.brianckrebs.github.data.model.Repository
import com.brianckrebs.github.databinding.ListItemRepositoryBinding
import com.brianckrebs.github.ui.screen.issuelist.IssueListActivity
import com.squareup.picasso.Picasso

class RepositoryListAdapter :
    ListAdapter<Repository, RepositoryListAdapter.ViewHolder>(RepositoryListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemRepositoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ListItemRepositoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Repository) {
            binding.root.setOnClickListener {
                binding.root.context.startActivity(
                    IssueListActivity.createIntent(
                        binding.root.context,
                        item.id,
                        item.owner.login,
                        item.name
                    )
                )
            }

            // image
            Picasso
                .get()
                .load(item.owner.avatarUrl)
                // TODO - add placeholder and error images
//                .placeholder()
//                .error()
                .into(binding.image)

            // open issue count
            binding.openIssueCount.text = binding.openIssueCount.resources.getQuantityString(
                R.plurals.repository_list_issue_count,
                item.openIssueCount,
                item.openIssueCount
            )

            // name
            binding.name.text = item.name
        }
    }

    class RepositoryListDiffCallback : DiffUtil.ItemCallback<Repository>() {
        override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean {
            return oldItem == newItem
        }
    }
}