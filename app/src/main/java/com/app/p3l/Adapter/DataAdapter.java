//package com.app.p3l.Adapter;
//
//import android.provider.ContactsContract;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.app.p3l.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataView> {
//    List<Integer> image = new ArrayList<>();
//    List<String> title = new ArrayList<>();
//
//    public DataAdapter(List<Integer> image, List<String> title) {
//        this.image = image;
//        this.title = title;
//    }
//
//
//
//
//    @NonNull
//    @Override
//    public DataView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_data,parent,false);
//        return new DataView(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull DataView DataView, int position) {
//        DataView.Image.setImageResource(image.get(position));
//        DataView.Title.setText(title.get(position));
//    }
//
//    @Override
//    public int getItemCount() {
//        return image.size();
//    }
//
//    public class DataView extends RecyclerView.ViewHolder {
//        ImageView Image;
//        TextView Title;
//        public DataView(@NonNull View itemView) {
//            super(itemView);
//            Image = (ImageView)itemView.findViewById(R.id.Image);
//            Title = (TextView)itemView.findViewById(R.id.Title);
//
//        }
//    }
//}
