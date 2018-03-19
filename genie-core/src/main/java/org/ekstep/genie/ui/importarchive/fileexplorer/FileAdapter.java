package org.ekstep.genie.ui.importarchive.fileexplorer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.ekstep.genie.R;
import org.ekstep.genie.callback.IMenuItemClick;
import org.ekstep.genie.model.FileItem;

import java.util.List;

/**
 * Created on 8/30/2016.
 *
 * @author swayangjit_gwl
 */
public class FileAdapter extends RecyclerView.Adapter<FileAdapter.ViewHolder> {

    private List<FileItem> mList;
    private IMenuItemClick mMenuItemClick = null;

    public FileAdapter(List<FileItem> list, IMenuItemClick menuItemClick) {
        this.mList = list;
        this.mMenuItemClick = menuItemClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_file_item, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        FileItem item = mList.get(position);

        if (item != null) {
            viewHolder.vhImgFile.setImageResource(item.icon);
            viewHolder.vhtvItem.setText(item.file);
        }

        viewHolder.view.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        private TextView vhtvItem;
        private ImageView vhImgFile;
        private RelativeLayout vhLayoutMain;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            view = itemLayoutView;
            vhtvItem = (TextView) itemLayoutView.findViewById(R.id.txt_file_name);
            vhImgFile = (ImageView) itemLayoutView.findViewById(R.id.img_file_icon);
            vhLayoutMain = (RelativeLayout) itemLayoutView.findViewById(R.id.layout_main);
            vhLayoutMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = Integer.valueOf(v.getTag().toString());
                    mMenuItemClick.onMenuItemClick(position);
                }
            });
        }
    }

}