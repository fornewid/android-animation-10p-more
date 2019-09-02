package soup.animation.sample

import android.animation.AnimatorInflater
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_anim.*

class AnimFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_anim, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val flipOut = AnimatorInflater.loadAnimator(view.context, R.animator.flip_out)
        val flipIn = AnimatorInflater.loadAnimator(view.context, R.animator.flip_in)
        facingButton.setOnClickListener {
            if (it.isSelected) {
                it.isSelected = false
                flipOut.setTarget(facingFrontButton)
                flipIn.setTarget(facingBackButton)
                flipOut.start()
                flipIn.start()
            } else {
                it.isSelected = true
                flipOut.setTarget(facingBackButton)
                flipIn.setTarget(facingFrontButton)
                flipOut.start()
                flipIn.start()
            }
            it.isSelected = !it.isSelected
        }
    }
}
