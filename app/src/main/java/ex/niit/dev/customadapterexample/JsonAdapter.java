package ex.niit.dev.customadapterexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

public class JsonAdapter extends BaseAdapter {

    private JSONArray items;
    private LayoutInflater layoutInflater;


    public JsonAdapter(Context context,JSONArray items){
        this.items = items;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return items.length();
    }

    @Override
    public Object getItem(int position) {
        try {
            return items.get(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return -1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View view = layoutInflater.inflate(R.layout.item_row,parent,false);
        TextView titleView = (TextView) view.findViewById(R.id.title);
        TextView userIdView = (TextView)view.findViewById(R.id.userId);

        try {
            titleView.setText(items.getJSONObject(position).getString("title"));
            userIdView.setText("ID: " +items.getJSONObject(position).getInt("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return view;
    }
}
