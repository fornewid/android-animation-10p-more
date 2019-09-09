package soup.animation.sample.animator.recyclerview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_animator_recycler_view.*
import soup.animation.sample.R

class RecyclerViewFragment : Fragment() {

    private lateinit var listAdapter: RecyclerViewAdapter
    private val list = arrayListOf<Item>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_animator_recycler_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listAdapter = RecyclerViewAdapter {
            if (list.remove(it)) {
                listAdapter.submitList(ArrayList(list))
            }
        }
        listView.adapter = listAdapter
        fab.setOnClickListener {
            val id = list.lastOrNull()?.id?.plus(1) ?: 0
            list.add(Item(id))
            listAdapter.submitList(ArrayList(list))
        }
    }
}
