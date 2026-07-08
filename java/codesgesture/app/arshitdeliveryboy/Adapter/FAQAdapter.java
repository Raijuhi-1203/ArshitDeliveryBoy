package codesgesture.app.arshitdeliveryboy.Adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import codesgesture.app.arshitdeliveryboy.Models.PolicyModel;
import codesgesture.app.arshitdeliveryboy.R;
import codesgesture.app.arshitdeliveryboy.Utils.SessionManage;

public class FAQAdapter extends RecyclerView.Adapter<FAQAdapter.ViewHolder> {
    private ArrayList<PolicyModel> arrayList;
    private Context context;
    String Userid="";
    private int layout;

    public FAQAdapter(Context context, ArrayList<PolicyModel> arrayList, int layout) {
        this.arrayList = arrayList;
        this.context = context;
        this.layout=layout;
        this.Userid = SessionManage.getCurrentUser(context.getApplicationContext()).getDelivery_boy_id();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int i) {
        final PolicyModel data = arrayList.get(i);

        holder.question.setText(Html.fromHtml(data.getFaq_question()));
        holder.answer.setText(Html.fromHtml(data.getFaq_description()));

    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView question,answer;

        ViewHolder(View view) {
            super(view);
            question = view.findViewById(R.id.question);
            answer= view.findViewById(R.id.answer);
        }
    }

    public void updateList(ArrayList<PolicyModel> list) {
        arrayList = list;
        notifyDataSetChanged();
    }

}