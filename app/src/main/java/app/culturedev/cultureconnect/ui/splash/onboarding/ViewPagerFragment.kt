package app.culturedev.cultureconnect.ui.splash.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import app.culturedev.cultureconnect.R
import app.culturedev.cultureconnect.ui.MainActivity
import app.culturedev.cultureconnect.ui.adapter.ViewPagerAdapter


class ViewPagerFragment : Fragment() {
    val viewpagerListener = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
        }

        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (onBoardingFinished()) {
            startActivity(Intent(requireContext(), MainActivity::class.java))
            requireActivity().finish()
        }

        val view = inflater.inflate(R.layout.fragment_view_pager, container, false)

        val fragmentList = arrayListOf(
            FirstScreenFragment(),
            SecondScreenFragment()
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )
        view.findViewById<ViewPager2>(R.id.viewPager).apply {
            this.adapter = adapter
            registerOnPageChangeCallback(viewpagerListener)
        }
        return view
    }

    private fun onBoardingFinished(): Boolean {
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished", false)
    }
}