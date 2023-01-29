package com.example.myasteroidsnasaapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myasteroidsnasaapp.R
import com.example.myasteroidsnasaapp.databinding.CustomLayoutRecyclerViewBinding
import com.example.myasteroidsnasaapp.models.AsteroidData

class RecyclerAdapter(private val nasaData: List<AsteroidData> , private val onRecyclerViewClick : OnRecyclerViewClick) :
    RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {



    class MyViewHolder(var binding: CustomLayoutRecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }


//By Using ViewBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = CustomLayoutRecyclerViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = nasaData[position]
        holder.binding.nameCard.text = currentItem.codename
        holder.binding.descriptionCard.text = currentItem.closeApproachDate
        if (currentItem.isPotentiallyHazardous) {
            holder.binding.imageCard.setImageResource(R.drawable.ic_status_potentially_hazardous)
        } else {
            holder.binding.imageCard.setImageResource(R.drawable.ic_status_normal)
        }

        holder.binding.customLayout.setOnClickListener {
            onRecyclerViewClick.onClicked(position)
        }
    }


    override fun getItemCount(): Int {
        return nasaData.size
    }




    //By Using FindViewById()
    /* override fun onCreateViewHolder(parent : ViewGroup , viewType : Int)  : MyViewHolder {
     val itemView = LayoutInflater.from(parent.context).inflate(R.layout.custom_layout_recycler_view,parent,false)
     return MyViewHolder(itemView)
 }

    override fun onBindViewHolder(holder: MyViewHolder , position : Int){
     val currentItem = nasaData[position]
     holder.name.text = currentItem.codename
     holder.description.text = currentItem.closeApproachDate
      if (currentItem.isPotentiallyHazardous) {
         holder.pic.setImageResource(R.drawable.ic_status_potentially_hazardous)
     } else {
         holder.pic.setImageResource(R.drawable.ic_status_normal)
     }
 }

 class MyViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView){
     val name : TextView = itemView.findViewById(R.id.name_card)
     val description : TextView = itemView.findViewById(R.id.description_card)
     val pic : ImageView = itemView.findViewById(R.id.image_card)
 }

 override fun getItemCount(): Int {
        return nasaData.size
    }
 */

}