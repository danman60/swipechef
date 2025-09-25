package com.swipechef.app.ui.swipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.swipechef.app.databinding.FragmentSwipePlanningBinding

class SwipePlanningFragment : Fragment() {

    private var _binding: FragmentSwipePlanningBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSwipePlanningBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textPlaceholder.text = "Swipe Planning\n\nThis fragment will feature:\n• ViewPager2 with recipe cards\n• Swipe left/right gestures\n• Selected recipes counter\n• Generate grocery list button\n• Progress tracking"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}