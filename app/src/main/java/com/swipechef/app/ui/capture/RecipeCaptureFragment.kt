package com.swipechef.app.ui.capture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.swipechef.app.databinding.FragmentRecipeCaptureBinding

class RecipeCaptureFragment : Fragment() {

    private var _binding: FragmentRecipeCaptureBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeCaptureBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textPlaceholder.text = "Recipe Capture\n\nThis fragment will handle:\n• Display shared images\n• AI-powered recipe extraction\n• Editable recipe forms\n• Image upload to Supabase\n• Save recipes to database"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}