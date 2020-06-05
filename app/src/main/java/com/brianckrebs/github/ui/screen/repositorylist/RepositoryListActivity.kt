package com.brianckrebs.github.ui.screen.repositorylist

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.brianckrebs.github.App
import com.brianckrebs.github.databinding.ActivityRepositoryListBinding

class RepositoryListActivity : AppCompatActivity() {

    private val viewModel by viewModels<RepositoryListViewModel> {
        RepositoryListViewModelFactory((this.application as App).dataRepository)
    }

    private lateinit var binding: ActivityRepositoryListBinding
    private val adapter = RepositoryListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRepositoryListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.adapter = adapter
        val itemDecoration = DividerItemDecoration(this, RecyclerView.VERTICAL)
        binding.recyclerView.addItemDecoration(itemDecoration)

        viewModel.repositoryList.observe(this, Observer { items -> adapter.submitList(items) })
    }
}