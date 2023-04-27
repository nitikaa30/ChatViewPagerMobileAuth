package com.example.viewpager.fragments.viewPager

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.viewpager.R
import com.example.viewpager.databinding.FragmentIntoBinding
import com.example.viewpager.fragments.chat.ChatMessage

class IntoFragment : Fragment() {
    private lateinit var layouts: IntArray
    private lateinit var binding: FragmentIntoBinding
    private var myViewPagerAdapter: MyViewPagerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentIntoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btn2.visibility=View.GONE
        binding.btn.visibility=View.VISIBLE
        setUpViewPager()
        binding.btn.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ChatMessage()).commit()
        }

    }

    private fun setUpViewPager() {
        layouts= intArrayOf(R.layout.intro1, R.layout.intro2, R.layout.intro3)

        myViewPagerAdapter = MyViewPagerAdapter()
        binding.viewpager.adapter = myViewPagerAdapter
        binding.viewpager.addOnPageChangeListener(viewPagerPageChangeListener)
        binding.tabLayout.setupWithViewPager(binding.viewpager, true)
    }
    inner class MyViewPagerAdapter:PagerAdapter(){
        private var layoutInflater: LayoutInflater = LayoutInflater.from(context)
        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            layoutInflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = layoutInflater.inflate(layouts[position], container, false)
            container.addView(view)
            return view
        }

        override fun getCount(): Int {
            return layouts.size
        }

        override fun isViewFromObject(
            view: View,
            obj: Any
        ): Boolean {
            return view === obj
        }

        override fun destroyItem(
            container: ViewGroup,
            position: Int,
            `object`: Any
        ) {
            val view = `object` as View
            container.removeView(view)
        }

    }
    var viewPagerPageChangeListener: ViewPager.OnPageChangeListener = object :
        ViewPager.OnPageChangeListener {
        override fun onPageSelected(position: Int) {
            binding.btn2.visibility=View.GONE
            binding.btn.visibility=View.VISIBLE
            when(position){
                0->{
                    binding.text1.text=resources.getString(R.string.connect_with_nanny)
                    binding.text2.text=resources.getString(R.string.view_text)
                }
                1->{
                    binding.text1.text=resources.getString(R.string.find_the_be)
                    binding.text2.text=resources.getString(R.string.view_text)
                }
                2->{
                    binding.text1.text=resources.getString(R.string.choose_the_)
                    binding.text2.text=resources.getString(R.string.view_text)
                    binding.btn2.visibility=View.VISIBLE
                    binding.btn.visibility=View.GONE
                    binding.btn2.setOnClickListener {
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, ChatMessage()).commit()
                    }
                }
            }
        }
        override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {}
        override fun onPageScrollStateChanged(arg0: Int) {}
    }

}