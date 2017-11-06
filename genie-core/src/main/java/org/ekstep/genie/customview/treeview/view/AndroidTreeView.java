package org.ekstep.genie.customview.treeview.view;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import org.ekstep.genie.R;
import org.ekstep.genie.customview.treeview.TreeItemHolder;
import org.ekstep.genie.customview.treeview.holder.SimpleViewHolder;
import org.ekstep.genie.customview.treeview.model.TreeNode;
import org.ekstep.genie.util.DeviceUtility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Bogdan Melnychuk on 2/10/15.
 */
public class AndroidTreeView {
    private static final String NODES_PATH_SEPARATOR = ";";

    protected TreeNode mRoot;
    //get height of the screen
    int mHeightOfScreen;
    private Context mContext;
    private boolean applyForRoot;
    private int containerStyle = 0;
    private Class<? extends TreeNode.BaseNodeViewHolder> defaultViewHolderClass = SimpleViewHolder.class;
    private TreeNode.TreeNodeClickListener nodeClickListener;
    private TreeNode.TreeNodeLongClickListener nodeLongClickListener;
    private boolean mSelectionModeEnabled;
    private boolean mUseDefaultAnimation = false;
    private boolean use2dScroll = false;
    private boolean enableAutoToggle = true;
    private ScrollView mScrollView;
    private boolean animationOffWhenCollapseAll;

    public AndroidTreeView(Context context) {
        mContext = context;
    }

    public AndroidTreeView(Context context, TreeNode root) {
        mRoot = root;
        mContext = context;
        mHeightOfScreen = DeviceUtility.getDeviceHeight((Activity) mContext);
    }

    private static void expand(final View v) {
        v.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        v.getLayoutParams().height = 0;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? LinearLayout.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    private static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public void setRoot(TreeNode mRoot) {
        this.mRoot = mRoot;
    }

    public void setDefaultAnimation(boolean defaultAnimation) {
        this.mUseDefaultAnimation = defaultAnimation;
    }

    public void setDefaultContainerStyle(int style) {
        setDefaultContainerStyle(style, false);
    }

    public void setDefaultContainerStyle(int style, boolean applyForRoot) {
        containerStyle = style;
        this.applyForRoot = applyForRoot;
    }

    public void setUse2dScroll(boolean use2dScroll) {
        this.use2dScroll = use2dScroll;
    }

    public boolean is2dScrollEnabled() {
        return use2dScroll;
    }

    public void setUseAutoToggle(boolean enableAutoToggle) {
        this.enableAutoToggle = enableAutoToggle;
    }

    public boolean isAutoToggleEnabled() {
        return enableAutoToggle;
    }

    public void setDefaultViewHolder(Class<? extends TreeNode.BaseNodeViewHolder> viewHolder) {
        defaultViewHolderClass = viewHolder;
    }

    public void setDefaultNodeClickListener(TreeNode.TreeNodeClickListener listener) {
        nodeClickListener = listener;
    }

    public void setDefaultNodeLongClickListener(TreeNode.TreeNodeLongClickListener listener) {
        nodeLongClickListener = listener;
    }

    public void expandAll() {
        expandNode(mRoot, true);
    }

    public void collapseAll() {
        for (TreeNode n : mRoot.getChildren()) {
            collapseNode(n, true);
        }
    }

    public View getView(int style) {
        final ViewGroup view;
        if (style > 0) {
            ContextThemeWrapper newContext = new ContextThemeWrapper(mContext, style);
            view = use2dScroll ? new TwoDScrollView(newContext) : new ScrollView(newContext);
        } else {
            view = use2dScroll ? new TwoDScrollView(mContext) : new ScrollView(mContext);
        }

        Context containerContext = mContext;
        if (containerStyle != 0 && applyForRoot) {
            containerContext = new ContextThemeWrapper(mContext, containerStyle);
        }
        final LinearLayout viewTreeItems = new LinearLayout(containerContext, null, containerStyle);

        viewTreeItems.setId(R.id.tree_items);
        viewTreeItems.setOrientation(LinearLayout.VERTICAL);
        view.addView(viewTreeItems);

        mRoot.setViewHolder(new TreeNode.BaseNodeViewHolder(mContext) {
            @Override
            public View createNodeView(TreeNode node, Object value) {
                return null;
            }

            @Override
            public ViewGroup getNodeItemsView() {
                return viewTreeItems;
            }
        });

        expandNode(mRoot, false);
        return view;
    }

    public View getView() {
        mScrollView = (ScrollView) getView(-1);
        return mScrollView;
    }

    public void expandLevel(int level) {
        for (TreeNode n : mRoot.getChildren()) {
            expandLevel(n, level);
        }
    }

    private void expandLevel(TreeNode node, int level) {
        if (node.getLevel() <= level) {
            expandNode(node, false);
        }
        for (TreeNode n : node.getChildren()) {
            expandLevel(n, level);
        }
    }

    public void expandNode(TreeNode node) {
        expandNode(node, false);
    }

    public void collapseNode(TreeNode node) {
        collapseNode(node, false);
    }

    public String getSaveState() {
        final StringBuilder builder = new StringBuilder();
        getSaveState(mRoot, builder);
        if (builder.length() > 0) {
            builder.setLength(builder.length() - 1);
        }
        return builder.toString();
    }

    public void restoreState(String saveState) {
        if (!TextUtils.isEmpty(saveState)) {
            collapseAll();
            final String[] openNodesArray = saveState.split(NODES_PATH_SEPARATOR);
            final Set<String> openNodes = new HashSet<>(Arrays.asList(openNodesArray));
            restoreNodeState(mRoot, openNodes);
        }
    }

    private void restoreNodeState(TreeNode node, Set<String> openNodes) {
        for (TreeNode n : node.getChildren()) {
            if (openNodes.contains(n.getPath())) {
                expandNode(n);
                restoreNodeState(n, openNodes);
            }
        }
    }

    private void getSaveState(TreeNode root, StringBuilder sBuilder) {
        for (TreeNode node : root.getChildren()) {
            if (node.isExpanded()) {
                sBuilder.append(node.getPath());
                sBuilder.append(NODES_PATH_SEPARATOR);
                getSaveState(node, sBuilder);
            }
        }
    }

    public void toggleNode(TreeNode node, boolean isScrollRequired) {
        if (node.isExpanded()) {
            collapseNode(node, false);
        } else {
            //this logic is changed wih the requirement that, when the user clicks on the Table of contents,
            // then it should be expanded completely
            TreeItemHolder.IconTreeItem item = (TreeItemHolder.IconTreeItem) node.getValue();
            if (item.text.equalsIgnoreCase(mContext.getString(R.string.label_textbook_table_of_contents))) {
                if (!node.isExpanded()) {
                    expandAll();
                }
            } else {
                expandNode(node, false);
            }

            if (isScrollRequired) {
                mScrollView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mScrollView.smoothScrollBy(0, 50);
                    }
                }, 50);
            }
        }

    }

    private void collapseNode(TreeNode node, final boolean includeSubnodes) {
        node.setExpanded(false);
        TreeNode.BaseNodeViewHolder nodeViewHolder = getViewHolderForNode(node);

        if (mUseDefaultAnimation && !animationOffWhenCollapseAll) {
            collapse(nodeViewHolder.getNodeItemsView());
        } else {
            nodeViewHolder.getNodeItemsView().setVisibility(View.GONE);
        }
        nodeViewHolder.toggle(false);
        if (includeSubnodes) {
            for (TreeNode n : node.getChildren()) {
                collapseNode(n, includeSubnodes);
            }
        }
    }

    //------------------------------------------------------------
    //  Selection methods

    private void expandNode(final TreeNode node, boolean includeSubnodes) {
        node.setExpanded(true);
        final TreeNode.BaseNodeViewHolder parentViewHolder = getViewHolderForNode(node);
        parentViewHolder.getNodeItemsView().removeAllViews();


        parentViewHolder.toggle(true);

        for (final TreeNode n : node.getChildren()) {
            addNode(parentViewHolder.getNodeItemsView(), n);

            if (n.isExpanded() || includeSubnodes) {
                expandNode(n, includeSubnodes);
            }

        }
        if (mUseDefaultAnimation) {
            expand(parentViewHolder.getNodeItemsView());
        } else {
            parentViewHolder.getNodeItemsView().setVisibility(View.VISIBLE);
        }

    }

    private void addNode(ViewGroup container, final TreeNode n) {
        final TreeNode.BaseNodeViewHolder viewHolder = getViewHolderForNode(n);
        final View nodeView = viewHolder.getView();
        container.addView(nodeView);
        if (mSelectionModeEnabled) {
            viewHolder.toggleSelectionMode(mSelectionModeEnabled);
        }

        nodeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (n.getClickListener() != null) {
                    n.getClickListener().onClick(n, n.getValue());
                } else if (nodeClickListener != null) {
                    nodeClickListener.onClick(n, n.getValue());
                }
                if (enableAutoToggle) {
                    //get the locations of the view on screen
                    int[] location = new int[2];
                    nodeView.getLocationOnScreen(location);

                    //get the absolute Y location of the view
                    int absoluteYLocation = (location[1] + nodeView.getHeight());

                    //if the difference is less than +50 or -1 then the view has to scroll up
                    int differenceInHeights = mHeightOfScreen - absoluteYLocation;
                    if (differenceInHeights < 50 || differenceInHeights < -1) {
                        toggleNode(n, true);
                    } else {
                        toggleNode(n, false);
                    }
                }
            }
        });

        nodeView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (n.getLongClickListener() != null) {
                    return n.getLongClickListener().onLongClick(n, n.getValue());
                } else if (nodeLongClickListener != null) {
                    return nodeLongClickListener.onLongClick(n, n.getValue());
                }
                if (enableAutoToggle) {
                    toggleNode(n, false);
                }
                return false;
            }
        });
    }

    public <E> List<E> getSelectedValues(Class<E> clazz) {
        List<E> result = new ArrayList<>();
        List<TreeNode> selected = getSelected();
        for (TreeNode n : selected) {
            Object value = n.getValue();
            if (value != null && value.getClass().equals(clazz)) {
                result.add((E) value);
            }
        }
        return result;
    }

    public boolean isSelectionModeEnabled() {
        return mSelectionModeEnabled;
    }

    public void setSelectionModeEnabled(boolean selectionModeEnabled) {
        if (!selectionModeEnabled) {
            deselectAll();
        }
        mSelectionModeEnabled = selectionModeEnabled;

        for (TreeNode node : mRoot.getChildren()) {
            toggleSelectionMode(node, selectionModeEnabled);
        }

    }

    private void toggleSelectionMode(TreeNode parent, boolean mSelectionModeEnabled) {
        toogleSelectionForNode(parent, mSelectionModeEnabled);
        if (parent.isExpanded()) {
            for (TreeNode node : parent.getChildren()) {
                toggleSelectionMode(node, mSelectionModeEnabled);
            }
        }
    }

    public List<TreeNode> getSelected() {
        if (mSelectionModeEnabled) {
            return getSelected(mRoot);
        } else {
            return new ArrayList<>();
        }
    }

    private List<TreeNode> getSelected(TreeNode parent) {
        List<TreeNode> result = new ArrayList<>();
        for (TreeNode n : parent.getChildren()) {
            if (n.isSelected()) {
                result.add(n);
            }
            result.addAll(getSelected(n));
        }
        return result;
    }

    public void selectAll(boolean skipCollapsed) {
        makeAllSelection(true, skipCollapsed);
    }

    public void deselectAll() {
        makeAllSelection(false, false);
    }

    private void makeAllSelection(boolean selected, boolean skipCollapsed) {
        if (mSelectionModeEnabled) {
            for (TreeNode node : mRoot.getChildren()) {
                selectNode(node, selected, skipCollapsed);
            }
        }
    }

    public void selectNode(TreeNode node, boolean selected) {
        if (mSelectionModeEnabled) {
            node.setSelected(selected);
            toogleSelectionForNode(node, true);
        }
    }

    private void selectNode(TreeNode parent, boolean selected, boolean skipCollapsed) {
        parent.setSelected(selected);
        toogleSelectionForNode(parent, true);
        boolean toContinue = skipCollapsed ? parent.isExpanded() : true;
        if (toContinue) {
            for (TreeNode node : parent.getChildren()) {
                selectNode(node, selected, skipCollapsed);
            }
        }
    }

    private void toogleSelectionForNode(TreeNode node, boolean makeSelectable) {
        TreeNode.BaseNodeViewHolder holder = getViewHolderForNode(node);
        if (holder.isInitialized()) {
            getViewHolderForNode(node).toggleSelectionMode(makeSelectable);
        }
    }

    private TreeNode.BaseNodeViewHolder getViewHolderForNode(TreeNode node) {
        TreeNode.BaseNodeViewHolder viewHolder = node.getViewHolder();
        if (viewHolder == null) {
            try {
                final Object object = defaultViewHolderClass.getConstructor(Context.class).newInstance(mContext);
                viewHolder = (TreeNode.BaseNodeViewHolder) object;
                node.setViewHolder(viewHolder);
            } catch (Exception e) {
                throw new RuntimeException("Could not instantiate class " + defaultViewHolderClass);
            }
        }
        if (viewHolder.getContainerStyle() <= 0) {
            viewHolder.setContainerStyle(containerStyle);
        }
        if (viewHolder.getTreeView() == null) {
            viewHolder.setTreeViev(this);
        }
        return viewHolder;
    }

    //-----------------------------------------------------------------
    //Add / Remove

    public void addNode(TreeNode parent, final TreeNode nodeToAdd) {
        parent.addChild(nodeToAdd);
        if (parent.isExpanded()) {
            final TreeNode.BaseNodeViewHolder parentViewHolder = getViewHolderForNode(parent);
            addNode(parentViewHolder.getNodeItemsView(), nodeToAdd);
        }
    }

    public void removeNode(TreeNode node) {
        if (node.getParent() != null) {
            TreeNode parent = node.getParent();
            int index = parent.deleteChild(node);
            if (parent.isExpanded() && index >= 0) {
                final TreeNode.BaseNodeViewHolder parentViewHolder = getViewHolderForNode(parent);
                parentViewHolder.getNodeItemsView().removeViewAt(index);
            }
        }
    }

    /**
     * This method turns off the default animation when collapsing all the nodes.
     * <p>
     * This is related to a custom requirement, where in the user clicks on any sub-node other than Table of contents,
     * it should collapse all, and if the animation is true, then collapse all animation was jittery, to turn off that animation
     * only during collapsing all, we will turn off the animation using this method
     *
     * @param animationOffWhenCollapseAll
     */
    public void setAnimationOffWhenCollapseAll(boolean animationOffWhenCollapseAll) {
        this.animationOffWhenCollapseAll = animationOffWhenCollapseAll;
    }
}
