package soup.animation.sample.spring.recycler_view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.SpringSlideInAnimator
import kotlinx.android.synthetic.main.fragment_spring_recycler_view.*
import soup.animation.sample.R

class RecyclerViewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_spring_recycler_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listAdapter = RecyclerViewAdapter()
        listView.itemAnimator = SpringSlideInAnimator()
        listView.adapter = listAdapter
        fab.setOnClickListener {
            listAdapter.shuffle()
        }
    }
}
