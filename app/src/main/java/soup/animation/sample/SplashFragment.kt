package soup.animation.sample

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_splash.*

class SplashFragment : Fragment() {

    private var doNavigate = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        icon.animate()
            .setDuration(1000L)
            .rotation(-1000f)
            .setListener(
                object : AnimatorListenerAdapter() {

                    override fun onAnimationEnd(animation: Animator) {
                        navigateToHome()
                    }
                }
            )
    }

    override fun onResume() {
        super.onResume()
        if (doNavigate) {
            navigateToHome()
        }
    }

    override fun onPause() {
        super.onPause()
        icon.animate().cancel()
    }

    private fun navigateToHome() {
        if (!isStateSaved) {
            findNavController().navigate(SplashFragmentDirections.actionToHome())
        } else {
            doNavigate = true
        }
    }
}
