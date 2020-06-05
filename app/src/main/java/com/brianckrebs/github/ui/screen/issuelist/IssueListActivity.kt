package com.brianckrebs.github.ui.screen.issuelist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.brianckrebs.github.App
import com.brianckrebs.github.R
import com.brianckrebs.github.data.model.IssueStateType
import com.brianckrebs.github.databinding.ActivityIssueListBinding

class IssueListActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_REPO_ID = "EXTRA_REPO_ID"
        const val EXTRA_OWNER = "EXTRA_OWNER"
        const val EXTRA_REPO_NAME = "EXTRA_REPO_NAME"

        fun createIntent(context: Context, repoId: Int, owner: String, repoName: String): Intent {
            val intent = Intent(context, IssueListActivity::class.java)
            intent.putExtra(EXTRA_REPO_ID, repoId)
            intent.putExtra(EXTRA_OWNER, owner)
            intent.putExtra(EXTRA_REPO_NAME, repoName)
            return intent
        }
    }

    private val viewModel by viewModels<IssueListViewModel> {
        IssueListViewModelFactory(
            (this.application as App).dataRepository,
            this.intent.extras?.getInt(EXTRA_REPO_ID)!!,
            this.intent.extras?.getString(EXTRA_OWNER)!!,
            this.intent.extras?.getString(EXTRA_REPO_NAME)!!
        )
    }

    private lateinit var binding: ActivityIssueListBinding
    private val adapter = IssueListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityIssueListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.adapter = adapter
        val itemDecoration = DividerItemDecoration(this, RecyclerView.VERTICAL)
        binding.recyclerView.addItemDecoration(itemDecoration)

        viewModel.issues.observe(this, Observer { issues -> adapter.submitList(issues) })

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val issueStateType = when (checkedId) {
                R.id.all_button -> IssueStateType.ALL
                R.id.open_button -> IssueStateType.OPEN
                R.id.closed_button -> IssueStateType.CLOSED
                else -> null
            }
            issueStateType?.let { viewModel.setStateFilter(issueStateType) }
        }

        // select the all button by default
        binding.allButton.isChecked = true
    }
}