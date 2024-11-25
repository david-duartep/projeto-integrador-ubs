import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projetointegradorubs.R

data class Appointment(
    val type: String,
    val ubsName: String,
    val date: String,
    val time: String
)

class AppointmentAdapter(
    private val appointments: List<Appointment>, // Lista de objetos Appointment
    private val onEdit: (Appointment) -> Unit,   // Callback para editar
    private val onDelete: (Appointment) -> Unit  // Callback para excluir
) : RecyclerView.Adapter<AppointmentAdapter.ViewHolder>() {

    // ViewHolder para associar os componentes do layout
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewAppointmentType: TextView = itemView.findViewById(R.id.textViewAppointmentType)
        val textViewUBS: TextView = itemView.findViewById(R.id.textViewUBS)
        val textViewDate: TextView = itemView.findViewById(R.id.textViewDate)
        val textViewTime: TextView = itemView.findViewById(R.id.textView_time)
        val buttonEdit: Button = itemView.findViewById(R.id.buttonEdit)
        val buttonDelete: Button = itemView.findViewById(R.id.buttonDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_appointment, parent, false) // Inflar o layout XML
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val appointment = appointments[position]

        // Associar dados aos componentes
        holder.textViewAppointmentType.text = "Tipo: ${appointment.type}"
        holder.textViewUBS.text = "UBS: ${appointment.ubsName}"
        holder.textViewDate.text = "Data: ${appointment.date}"
        holder.textViewTime.text = "Horário: ${appointment.time}"

        // Configurar ações para os botões
        holder.buttonEdit.setOnClickListener { onEdit(appointment) }
        holder.buttonDelete.setOnClickListener { onDelete(appointment) }
    }

    override fun getItemCount(): Int = appointments.size
}
