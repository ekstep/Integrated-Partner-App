package org.ekstep.genie.customview.treeview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.ekstep.genie.R;
import org.ekstep.genie.customview.treeview.model.TreeNode;


/**
 * Created by shriharsh on 21/2/17.
 */

public class TreeItemHolder extends TreeNode.BaseNodeViewHolder<TreeItemHolder.IconTreeItem> {
    private TextView tvValue;
    private View view;
    private ImageView ivValue;

    public TreeItemHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(TreeNode node, IconTreeItem value) {
        final LayoutInflater inflater = LayoutInflater.from(context);

        if (node.isLeaf()) {
            //Along with the checking if the node is leaf node, we also need to check if the leaf node
            //is at level 1, if it is at level 1, then we show the background of that node as parent node
            if (value.level == 0 || value.level == 1) {
                view = inflater.inflate(R.layout.layout_tree_parent_node, null, false);
            } else {
                view = inflater.inflate(R.layout.layout_tree_leaf_node, null, false);
            }
        } else {
            view = inflater.inflate(R.layout.layout_tree_parent_node, null, false);
        }

        tvValue = (TextView) view.findViewById(R.id.node_value);

        String nodeString = getTabsBasedOnLevel(value.level) + value.text;
        tvValue.setText(nodeString);

        if (value.text.contains(context.getString(R.string.label_textbook_table_of_contents))) {
            ivValue = (ImageView) view.findViewById(R.id.node_image);
            ivValue.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_toc_down));
        } else {
            ivValue = null;
        }

        return view;
    }

    /**
     * This method gives you the number of tabs to be added based on the level
     * <p>
     * Number of tabs = 2 * level
     *
     * @param level
     * @return
     */
    private String getTabsBasedOnLevel(int level) {
        String tabs = "";

        if (level > 0) {
            for (int i = 0; i < level; i++) {
                tabs = tabs + "\t\t";
            }
        }

        return tabs;
    }

    @Override
    public void toggle(boolean active) {
        if (ivValue != null) {
            if (active) {
                ivValue.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_toc_up));
            } else {
                ivValue.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_toc_down));
            }
        }
    }

    public static class IconTreeItem {
        public String text;
        public int icon;
        public int level;
        public int tocFirstLevelItemPosition;

        public IconTreeItem(String text, int icon, int level, int tocFirstLevelItemPosition) {
            this.text = text;
            this.icon = icon;
            this.level = level;
            this.tocFirstLevelItemPosition = tocFirstLevelItemPosition;
        }
    }
}
