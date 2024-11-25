import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projetointegradorubs.R

data class Appointment(
    val ubsName: String,
    val appointmentType: String,
    val date: String,
    val time: String
)

class AppointmentAdapter(private val appointments: List<Appointment>) :
    RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder>() {

    class AppointmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val appointmentType: TextView = itemView.findViewById(R.id.textViewAppointmentType)
        val ubsName: TextView = itemView.findViewById(R.id.textViewUBS)
        val date: TextView = itemView.findViewById(R.id.textViewDate)
        val time: TextView = itemView.findViewById(R.id.textView_time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_appointment, parent, false)
        return AppointmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        val appointment = appointments[position]
        holder.appointmentType.text = appointment.appointmentType
        holder.ubsName.text = appointment.ubsName
        holder.date.text = appointment.date
        holder.time.text = appointment.time
    }

    override fun getItemCount(): Int = appointments.size
}
