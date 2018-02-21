
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.*
import com.example.gredu.androidapp.R
import com.example.gredu.androidapp.model.CityEvent

class AdapterMain( var listViewType: ArrayList<CityEvent>,  var contetxt: Context) : RecyclerView.Adapter<AdapterMain.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder? {
        val view: View
        when (viewType) {
            TYPE_1 -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.item_header, null)
                return ItemPertamaViewHolder(view)
            }
            TYPE_2 -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, null)
                return ItemKeduaViewHolder(view)
            }
            else -> return null
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val viewType:CityEvent = listViewType.get(position)
        if (viewType != null){
            when (viewType.type) {
                TYPE_1 -> {
                    val itemPertamaViewHolder = holder as ItemPertamaViewHolder
                    itemPertamaViewHolder.textViewItemPertama.text = viewType.description
                }
                TYPE_2 -> {
                    val itemKeduaViewHolder = holder as ItemKeduaViewHolder
                    itemKeduaViewHolder.textViewItemKedua.text = viewType.name
                }
            }
        }
    }


    override fun getItemCount(): Int {
        if (listViewType == null)
            return 0;
        return listViewType.size
    }

    override fun getItemViewType(position: Int): Int {
        if (listViewType != null) {
            val mList: CityEvent = listViewType.get(position)
            if (mList != null) {
                return mList.type
            }
        }
        return 0;
    }

    open class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

     inner class ItemPertamaViewHolder(itemView: View) : ViewHolder(itemView) {

        //  komponen
        var textViewItemPertama: TextView = itemView.findViewById(R.id.textHeader)

    }

     inner class ItemKeduaViewHolder(itemView: View) : ViewHolder(itemView) {

        //  komponen
        var textViewItemKedua: TextView = itemView.findViewById(R.id.textItem)

    }

    companion object {
        val TYPE_1 = 1
        val TYPE_2 = 2
    }
}