package com.swipechef.app.ui.deck

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.swipechef.app.databinding.FragmentRecipeDeckBinding
import com.swipechef.app.viewmodel.RecipeDeckViewModel

class RecipeDeckFragment : Fragment() {

    private var _binding: FragmentRecipeDeckBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RecipeDeckViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeDeckBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TODO: Implement full UI functionality
        binding.textPlaceholder.text = "SwipeChef Recipe Deck\n\nThis is a placeholder fragment. The full implementation will include:\n\n• Recipe cards in a grid or list\n• Search functionality\n• FAB for adding recipes\n• Navigation to recipe capture and planning\n\nShare an image with SwipeChef to test the recipe extraction feature!"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}