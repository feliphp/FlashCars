package aplicacion.android.jotajotavm.flashcards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends BaseAdapter {
    private Context context;
    private List<Card> list;
    private int layout;

    public MyAdapter(Context context, List<Card> list, int layout) {
        this.context = context;
        this.list = list;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Card getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layout, null);
            vh = new ViewHolder();
            vh.vin = (TextView) convertView.findViewById(R.id.textViewVIN);
            vh.pregunta = (TextView) convertView.findViewById(R.id.textViewPregunta);
            vh.respuesta = (TextView) convertView.findViewById(R.id.textViewRespuesta);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        Card currentCard = list.get(position);

        vh.vin.setText(currentCard.getVIN() + "");
        vh.pregunta.setText(currentCard.getPregunta());
        vh.respuesta.setText(currentCard.getRespuesta());

        return convertView;
    }

    public class ViewHolder {
        TextView vin;
        TextView pregunta;
        TextView respuesta;
    }
}
