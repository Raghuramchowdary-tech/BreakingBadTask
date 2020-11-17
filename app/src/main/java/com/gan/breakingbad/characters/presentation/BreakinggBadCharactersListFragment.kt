package com.gan.breakingbad.characters.presentation

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gan.breakingbad.R
import com.gan.breakingbad.characters.data.BreakingBadCharacter
import com.gan.breakingbad.characters.domain.BreakingBadCharacterClickObserver
import com.gan.breakingbad.characters.domain.BreakingBadCharacterListPlaceHolderStringResolver
import com.gan.breakingbad.characters.domain.BreakingBadModule
import com.gan.breakingbad.databinding.FragmentBreakingBadCharactersListBinding
import com.gan.breakingbad.utils.runOnUiThread


class BreakinggBadCharactersListFragment : Fragment(), BreakingBadCharacterClickObserver {

    private val presenter by lazy {
        BreakingBadCharactersListPresenter(
            BreakingBadModule.breakingBadRepository,
            BreakingBadCharacterListPlaceHolderStringResolver(requireContext())
        )
    }

    private lateinit var binding: FragmentBreakingBadCharactersListBinding
    private lateinit var breakingBadCharacterListAdapter: BreakingBadCharactersListAdapter
    private var searchView: SearchView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_breaking_bad_characters_list, container, false
        )
        binding.actionHandler = presenter
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        presenter.attachView(this::updateViewState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? AppCompatActivity)?.setSupportActionBar(binding.toolbar)
        breakingBadCharacterListAdapter = BreakingBadCharactersListAdapter(this)
        binding.breakingBadList.adapter = breakingBadCharacterListAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.menu_main, menu)
        val filterItem = menu.findItem(R.id.action_filter)
        val searchItem = menu.findItem(R.id.action_search)
        searchItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)
        searchView = searchItem.actionView as SearchView
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(queryText: String?): Boolean {
                presenter.onFilterTextChanged(queryText)
                return true
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> {
                return false
            }
            R.id.action_filter -> {
                searchView?.setQuery("", false)
                searchView?.clearFocus()
                presenter.filterClicked()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateViewState(viewState: BreakingBadCharactersListPresenter.ViewState) {
        runOnUiThread {
            binding.viewState = viewState
            if (viewState.showFilter) {
                openDialogWithAppearances(viewState.seasonsAppearance ?: listOf())
            } else {
                breakingBadCharacterListAdapter.submitList(viewState.breakingBadCharactersList)
            }
        }
    }

    private fun openDialogWithAppearances(seasons: List<Int>) {
        if (seasons.isEmpty()) return
        val builder = AlertDialog.Builder(requireContext())
        val seasonsList = seasons.map { "Season $it" }.toTypedArray()
        builder.setItems(seasonsList) { dialog, which ->
            presenter.filterBySeasonAppearanceClicked(which+1)
        }
        builder.setTitle("Select Season")
        builder.create().show()
    }

    override fun onBreakingBadCharacterClicked(
        breakingBarCharacter: BreakingBadCharacter,
    ) {
        val action =
            BreakinggBadCharactersListFragmentDirections.actionFirstFragmentToSecondFragment(
                breakingBarCharacter
            )
        findNavController().navigate(action)
    }

    override fun onPause() {
        super.onPause()
        presenter.detachView()
    }

}