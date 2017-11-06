package org.ekstep.genie.ui.importarchive;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.ekstep.genie.R;

import java.io.File;
import java.util.List;

/**
 * Created on 26/10/2015.
 *
 * @author swayangjit_gwl
 */
public class ImportFileAdapter extends RecyclerView.Adapter<ImportFileAdapter.ViewHolder> {

    private List<File> mFileList = null;
    private ImportArchiveContract.Presenter mPresenter = null;

    public ImportFileAdapter(List<File> fileList, ImportArchiveContract.Presenter presenter) {
        mFileList = fileList;
        mPresenter = presenter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_file_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final File file = mFileList.get(position);
        String filePath = file.toString();
        String fileName = filePath.substring(filePath.lastIndexOf('/') + 1, filePath.length());

        holder.vhTxtFileName.setText(fileName);
        holder.vhImgFile.setImageResource(R.drawable.ic_file);
        holder.vhLinearMain.setTag(file.toString());
    }

    @Override
    public int getItemCount() {
        return mFileList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView vhTxtFileName;
        ImageView vhImgFile;
        RelativeLayout vhLinearMain;

        public ViewHolder(View itemView) {
            super(itemView);
            vhTxtFileName = (TextView) itemView.findViewById(R.id.txt_file_name);
            vhLinearMain = (RelativeLayout) itemView.findViewById(R.id.layout_main);
            vhImgFile = (ImageView) itemView.findViewById(R.id.img_file_icon);
            vhLinearMain.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int i = v.getId();
            if (i == R.id.layout_main) {
                mPresenter.importFile(v.getTag().toString());

            } else {
            }
        }
    }

}
