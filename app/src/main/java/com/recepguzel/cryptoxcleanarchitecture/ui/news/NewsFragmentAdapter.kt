package com.recepguzel.cryptoxcleanarchitecture.ui.news

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class NewsFragmentAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(
    fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {
    private val fragmentList: MutableList<Pair<Fragment, String>> = mutableListOf()

    override fun getCount(): Int = fragmentList.size

    override fun getItem(position: Int): Fragment = fragmentList[position].first

    override fun getPageTitle(position: Int): CharSequence? = fragmentList[position].second

    fun addFragment(fragment: Fragment, title: String) {
        fragmentList.add(Pair(fragment, title))
    }
}
