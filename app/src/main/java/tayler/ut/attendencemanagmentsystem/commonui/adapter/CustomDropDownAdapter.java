package tayler.ut.attendencemanagmentsystem.commonui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import tayler.ut.attendencemanagmentsystem.R;
import tayler.ut.attendencemanagmentsystem.commonui.fragments.SpinnerModel;


/**
 * Created by amitwalke on 4/13/18.
 */

public class CustomDropDownAdapter extends BaseAdapter {

    List<SpinnerModel> model;
    Context mContext;
    private final LayoutInflater mInflater;

    public CustomDropDownAdapter(Context mContext, List<SpinnerModel> model) {
        this.model = model;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }


    @Override
    public int getCount() {
        return model.size();
    }

    @Override
    public Object getItem(int i) {
        return model.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View converView, ViewGroup parent) {
        final View view = mInflater.inflate(R.layout.spinner_item, parent, false);
        TextView txtItem = (TextView) view.findViewById(R.id.txt_spinner_item);
        txtItem.setText(model.get(i).getMonthName());
        return view;
    }


}
