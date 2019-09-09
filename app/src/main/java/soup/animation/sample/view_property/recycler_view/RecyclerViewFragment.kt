package soup.animation.sample.view_property.recycler_view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.SlideInUpAnimator
import kotlinx.android.synthetic.main.fragment_view_property_recycler_view.*
import soup.animation.sample.R

class RecyclerViewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_view_property_recycler_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listAdapter = RecyclerViewAdapter()
        listView.itemAnimator = SlideInUpAnimator()
        listView.adapter = listAdapter
        fab.setOnClickListener {
            listAdapter.addItemToLast()
        }
    }
}
