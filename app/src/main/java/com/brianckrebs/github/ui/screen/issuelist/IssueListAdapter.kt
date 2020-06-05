package com.brianckrebs.github.ui.screen.issuelist

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.brianckrebs.github.data.model.RepositoryIssue
import com.brianckrebs.github.databinding.ListItemIssueBinding
import com.squareup.picasso.Picasso

class IssueListAdapter :
    PagedListAdapter<RepositoryIssue, IssueListAdapter.ViewHolder>(IssueListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListItemIssueBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ListItemIssueBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: RepositoryIssue?) {
            binding.root.setOnClickListener {
                binding.root.context.startActivity(
                    Intent(Intent.ACTION_VIEW, Uri.parse(item?.url))
                )
            }

            // image
            Picasso
                .get()
                .load(item?.user?.avatarUrl)
                // TODO - add placeholder and error images
//                .placeholder()
//                .error()
                .into(binding.image)


            // state
            @SuppressLint("DefaultLocale")
            binding.state.text = item?.state?.capitalize()

            // title
            binding.title.text = item?.title

            // body
            item?.body?.let {
                binding.body.text = it
            }
        }
    }

    class IssueListDiffCallback : DiffUtil.ItemCallback<RepositoryIssue>() {
        override fun areItemsTheSame(oldItem: RepositoryIssue, newItem: RepositoryIssue): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: RepositoryIssue,
            newItem: RepositoryIssue
        ): Boolean {
            return oldItem == newItem
        }
    }
}