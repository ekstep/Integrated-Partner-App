package org.ekstep.genie.util;

import org.ekstep.genie.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by swayangjit_gwl on 4/16/2016.
 */
public class AvatarUtil {

    public static Map<String, Integer> populateAvatars() {
        Map<String, Integer> avatarMap = new LinkedHashMap<>();
        avatarMap.put("@drawable/ic_avatar1", R.drawable.avatar_normal_1);
        avatarMap.put("@drawable/ic_avatar2", R.drawable.avatar_normal_2);
        avatarMap.put("@drawable/ic_avatar3", R.drawable.avatar_normal_3);
        avatarMap.put("@drawable/ic_avatar4", R.drawable.avatar_normal_4);
        avatarMap.put("@drawable/ic_avatar5", R.drawable.avatar_normal_5);
        avatarMap.put("@drawable/ic_avatar6", R.drawable.avatar_normal_6);
        avatarMap.put("@drawable/ic_avatar7", R.drawable.avatar_normal_7);
        return avatarMap;
    }

    public static Map<String, Integer> populateMaleAvatars() {
        Map<String, Integer> avatarMap = new LinkedHashMap<>();
        avatarMap.put("@drawable/ic_avatar1", R.drawable.avatar_normal_1);
        avatarMap.put("@drawable/ic_avatar2", R.drawable.avatar_normal_2);
        avatarMap.put("@drawable/ic_avatar5", R.drawable.avatar_normal_5);

        //female avatar appended at the end
        avatarMap.put("@drawable/ic_avatar3", R.drawable.avatar_normal_3);
        avatarMap.put("@drawable/ic_avatar4", R.drawable.avatar_normal_4);
        avatarMap.put("@drawable/ic_avatar6", R.drawable.avatar_normal_6);
        avatarMap.put("@drawable/ic_avatar7", R.drawable.avatar_normal_7);
        return avatarMap;
    }

    public static Map<String, Integer> populateFemaleAvatars() {
        Map<String, Integer> avatarMap = new LinkedHashMap<>();
        avatarMap.put("@drawable/ic_avatar3", R.drawable.avatar_normal_3);
        avatarMap.put("@drawable/ic_avatar4", R.drawable.avatar_normal_4);
        avatarMap.put("@drawable/ic_avatar6", R.drawable.avatar_normal_6);
        avatarMap.put("@drawable/ic_avatar7", R.drawable.avatar_normal_7);

        //male avatar appended at the end
        avatarMap.put("@drawable/ic_avatar1", R.drawable.avatar_normal_1);
        avatarMap.put("@drawable/ic_avatar2", R.drawable.avatar_normal_2);
        avatarMap.put("@drawable/ic_avatar5", R.drawable.avatar_normal_5);
        return avatarMap;
    }

    public static Map<String, Integer> populateSwitchAvatars() {
        Map<String, Integer> avatarMap = new LinkedHashMap<>();
        avatarMap.put("@drawable/ic_avatar1", R.drawable.avatar_normal_1);
        avatarMap.put("@drawable/ic_avatar2", R.drawable.avatar_normal_2);
        avatarMap.put("@drawable/ic_avatar3", R.drawable.avatar_normal_3);
        avatarMap.put("@drawable/ic_avatar4", R.drawable.avatar_normal_4);
        avatarMap.put("@drawable/ic_avatar5", R.drawable.avatar_normal_5);
        avatarMap.put("@drawable/ic_avatar6", R.drawable.avatar_normal_6);
        avatarMap.put("@drawable/ic_avatar7", R.drawable.avatar_normal_7);
        avatarMap.put("Akshara", R.drawable.avatar_normal_5);
        avatarMap.put("@drawable/anonymous", R.drawable.avatar_anonymous);
        return avatarMap;
    }

    public static Map<String, Integer> populateBadges() {
        Map<String, Integer> avatarMap = new LinkedHashMap<>();
        avatarMap.put("@drawable/ic_badge1", R.drawable.img_badge01);
        avatarMap.put("@drawable/ic_badge2", R.drawable.img_badge02);
        avatarMap.put("@drawable/ic_badge3", R.drawable.img_badge03);
        avatarMap.put("@drawable/ic_badge4", R.drawable.img_badge04);
        avatarMap.put("@drawable/ic_badge5", R.drawable.img_badge05);
        avatarMap.put("@drawable/ic_badge6", R.drawable.img_badge06);
        avatarMap.put("@drawable/ic_badge7", R.drawable.img_badge07);
        avatarMap.put("@drawable/ic_badge8", R.drawable.img_badge08);
        avatarMap.put("@drawable/ic_badge9", R.drawable.img_badge09);
        avatarMap.put("@drawable/ic_badge10", R.drawable.img_badge10);
        return avatarMap;
    }

    public static Map<String, Integer> populateSelectedBadges() {
        Map<String, Integer> avatarMap = new LinkedHashMap<>();
        avatarMap.put("@drawable/ic_badge1", R.drawable.img_badge01_selected);
        avatarMap.put("@drawable/ic_badge2", R.drawable.img_badge02_selected);
        avatarMap.put("@drawable/ic_badge3", R.drawable.img_badge03_selected);
        avatarMap.put("@drawable/ic_badge4", R.drawable.img_badge04_selected);
        avatarMap.put("@drawable/ic_badge5", R.drawable.img_badge05_selected);
        avatarMap.put("@drawable/ic_badge6", R.drawable.img_badge06_selected);
        avatarMap.put("@drawable/ic_badge7", R.drawable.img_badge07_selected);
        avatarMap.put("@drawable/ic_badge8", R.drawable.img_badge08_selected);
        avatarMap.put("@drawable/ic_badge9", R.drawable.img_badge09_sleected);
        avatarMap.put("@drawable/ic_badge10", R.drawable.img_badge10_selected);
        return avatarMap;
    }

    public static List<String> populateSelectedBadgeNames() {
        List<String> selectedBadgeList = new ArrayList<>();
        selectedBadgeList.add("img_badge01_selected.png");
        selectedBadgeList.add("img_badge02_selected.png");
        selectedBadgeList.add("img_badge03_selected.png");
        selectedBadgeList.add("img_badge04_selected.png");
        selectedBadgeList.add("img_badge05_selected.png");
        selectedBadgeList.add("img_badge06_selected.png");
        selectedBadgeList.add("img_badge07_selected.png");
        selectedBadgeList.add("img_badge08_selected.png");
        selectedBadgeList.add("img_badge09_selected.png");
        selectedBadgeList.add("img_badge10_selected.png");
        return selectedBadgeList;
    }

    public static List<String> populateBadgeNames() {
        List<String> selectedBadgeList = new ArrayList<>();
        selectedBadgeList.add("img_badge01.png");
        selectedBadgeList.add("img_badge02.png");
        selectedBadgeList.add("img_badge03.png");
        selectedBadgeList.add("img_badge04.png");
        selectedBadgeList.add("img_badge05.png");
        selectedBadgeList.add("img_badge06.png");
        selectedBadgeList.add("img_badge07.png");
        selectedBadgeList.add("img_badge08.png");
        selectedBadgeList.add("img_badge09.png");
        selectedBadgeList.add("img_badge10.png");
        return selectedBadgeList;
    }

    public static List<String> populateMaleAvatarNames() {
        List<String> maleAvatarList = new ArrayList<>();

        maleAvatarList.add("avatar_normal_1.png");
        maleAvatarList.add("avatar_normal_2.png");
        maleAvatarList.add("avatar_normal_5.png");

        //female avatar appended at the end
        maleAvatarList.add("avatar_normal_3.png");
        maleAvatarList.add("avatar_normal_4.png");
        maleAvatarList.add("avatar_normal_6.png");
        maleAvatarList.add("avatar_normal_7.png");

        return maleAvatarList;
    }

    public static List<String> populateFemaleAvatarNames() {
        List<String> femaleAvatarList = new ArrayList<>();

        femaleAvatarList.add("avatar_normal_3.png");
        femaleAvatarList.add("avatar_normal_4.png");
        femaleAvatarList.add("avatar_normal_6.png");
        femaleAvatarList.add("avatar_normal_7.png");

        //male avatar appended at the end
        femaleAvatarList.add("avatar_normal_1.png");
        femaleAvatarList.add("avatar_normal_2.png");
        femaleAvatarList.add("avatar_normal_5.png");

        return femaleAvatarList;
    }

    public static List<String> populateAvatarNames() {
        List<String> avatarNamesList = new ArrayList<>();
        avatarNamesList.add("avatar_normal_1.png");
        avatarNamesList.add("avatar_normal_2.png");
        avatarNamesList.add("avatar_normal_3.png");
        avatarNamesList.add("avatar_normal_4.png");
        avatarNamesList.add("avatar_normal_5.png");
        avatarNamesList.add("avatar_normal_6.png");
        avatarNamesList.add("avatar_normal_7.png");
        return avatarNamesList;
    }

    public static List<String> populateSwitchAvatarsNames() {
        List<String> switchAvatarNamesList = new ArrayList<>();
        switchAvatarNamesList.add("avatar_normal_1.png");
        switchAvatarNamesList.add("avatar_normal_2.png");
        switchAvatarNamesList.add("avatar_normal_3.png");
        switchAvatarNamesList.add("avatar_normal_4.png");
        switchAvatarNamesList.add("avatar_normal_5.png");
        switchAvatarNamesList.add("avatar_normal_6.png");
        switchAvatarNamesList.add("avatar_normal_7.png");
        switchAvatarNamesList.add("avatar_normal_5.png");
        switchAvatarNamesList.add("avatar_anonymous.png");
        return switchAvatarNamesList;
    }

    public static int getPosition(Map<String, Integer> avatarMap, String avatar) {
        List<String> avatarList = new ArrayList<>(avatarMap.keySet());
        return avatarList.indexOf(avatar);
    }

    public static String getAvatarData(Map<String, Integer> avatarMap, int position) {
        List<String> avatarList = new ArrayList<>(avatarMap.keySet());
        return avatarList.get(position);
    }

    public static int getAvatar(Map<String, Integer> avatarMap, int position) {
        List<Integer> avatarList = (new ArrayList<>(avatarMap.values()));
        return avatarList.get(position);
    }

    public static Map<String, String> getProfileImagePath() {
        Map<String, String> avatarMap = new LinkedHashMap<>();
        avatarMap.put("@drawable/ic_avatar1", "/avatar_normal_1.png");
        avatarMap.put("@drawable/ic_avatar2", "/avatar_normal_2.png");
        avatarMap.put("@drawable/ic_avatar3", "/avatar_normal_3.png");
        avatarMap.put("@drawable/ic_avatar4", "/avatar_normal_4.png");
        avatarMap.put("@drawable/ic_avatar5", "/avatar_normal_5.png");
        avatarMap.put("@drawable/ic_avatar6", "/avatar_normal_6.png");
        avatarMap.put("@drawable/ic_avatar7", "/avatar_normal_7.png");
        avatarMap.put("@drawable/anonymous", "/avatar_anonymous.png");
        avatarMap.put("@drawable/ic_badge1", "/img_badge01.png");
        avatarMap.put("@drawable/ic_badge2", "/img_badge02.png");
        avatarMap.put("@drawable/ic_badge3", "/img_badge03.png");
        avatarMap.put("@drawable/ic_badge4", "/img_badge04.png");
        avatarMap.put("@drawable/ic_badge5", "/img_badge05.png");
        avatarMap.put("@drawable/ic_badge6", "/img_badge06.png");
        avatarMap.put("@drawable/ic_badge7", "/img_badge07.png");
        avatarMap.put("@drawable/ic_badge8", "/img_badge08.png");
        avatarMap.put("@drawable/ic_badge9", "/img_badge09.png");
        avatarMap.put("@drawable/ic_badge10", "/img_badge10.png");
        return avatarMap;
    }
}
