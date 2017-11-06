package org.ekstep.ipa.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import org.ekstep.genie.BuildConfig;
import org.ekstep.ipa.model.Student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * A DAO class for creating Student Information
 * @author vinayagasundar
 */

@SuppressWarnings("WeakerAccess")
public final class StudentDAO {


    private static final String TAG = "StudentDAO";
    private static final boolean DEBUG = BuildConfig.DEBUG;


    /**
     * Name of the Table
     */
    private static final String TABLE_NAME = "student_info";


    // Table Column Names

    public static final String COLUMN_DISTRICT = "district";
    public static final String COLUMN_BLOCK = "block";
    public static final String COLUMN_CLUSTER = "cluster";
    public static final String COLUMN_SCHOOL_CODE = "school_code";
    public static final String COLUMN_SCHOOL_NAME = "school_name";
    public static final String COLUMN_STUDENT_ID = "student_id";
    public static final String COLUMN_CHILD_NAME = "child_name";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_CLASS = "class";
    public static final String COLUMN_FATHER_NAME = "father_name";
    public static final String COLUMN_MOTHER_NAME = "mother_name";
    public static final String COLUMN_DOB = "dob";
    public static final String COLUMN_UID = "uid";
    public static final String COLUMN_SYNC = "sync";


    private static final String [] ALL_COLUMN_MAP = {
            COLUMN_DISTRICT,
            COLUMN_BLOCK,
            COLUMN_CLUSTER,
            COLUMN_SCHOOL_CODE,
            COLUMN_SCHOOL_NAME,
            COLUMN_STUDENT_ID,
            COLUMN_CHILD_NAME,
            COLUMN_GENDER,
            COLUMN_CLASS,
            COLUMN_FATHER_NAME,
            COLUMN_MOTHER_NAME,
            COLUMN_DOB,
            COLUMN_UID
    };

    private static final String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME
            + " ("
            + COLUMN_STUDENT_ID + " TEXT PRIMARY KEY,"
            + COLUMN_DISTRICT + " TEXT,"
            + COLUMN_BLOCK + " TEXT,"
            + COLUMN_CLUSTER + " TEXT,"
            + COLUMN_SCHOOL_CODE + " TEXT,"
            + COLUMN_SCHOOL_NAME + " TEXT,"
            + COLUMN_CHILD_NAME + " TEXT,"
            + COLUMN_GENDER + " TEXT,"
            + COLUMN_CLASS + " TEXT,"
            + COLUMN_FATHER_NAME + " TEXT,"
            + COLUMN_MOTHER_NAME + " TEXT,"
            + COLUMN_DOB + " TEXT,"
            + COLUMN_UID + " TEXT,"
            + COLUMN_SYNC + " INTEGER DEFAULT 0"
            + ");";



    public static final String [] DEFAULT_INSERT_COLUMN_MAP = {
            COLUMN_DISTRICT,
            COLUMN_BLOCK,
            COLUMN_CLUSTER,
            COLUMN_SCHOOL_CODE,
            COLUMN_SCHOOL_NAME,
            COLUMN_CLASS,
            COLUMN_STUDENT_ID,
            COLUMN_CHILD_NAME,
            COLUMN_GENDER,
            COLUMN_DOB,
            COLUMN_FATHER_NAME,
            COLUMN_MOTHER_NAME
    };



    private static StudentDAO mInstance;



    private StudentDAO() {
        // To avoid object creation
    }


    public static StudentDAO getInstance() {
        if (mInstance == null) {
            mInstance = new StudentDAO();
        }
        return mInstance;
    }


    static String getCreateTableQuery() {
        return CREATE_TABLE_QUERY;
    }


    /**
     * Insert a List of Student information into the Table
     * @param valuesList List of Content Value contain the student information
     */
    public void insertData(List<ContentValues> valuesList) {
        if (DEBUG) {
            Log.i(TAG, "insertData: Total -> " + valuesList.size());
        }

        SQLiteDatabase database = PartnerDB.getInstance().getDatabase();

        database.beginTransaction();

        for (ContentValues values : valuesList) {
            String email = values.getAsString(COLUMN_STUDENT_ID);

            if (isValueExists(database, email)) {
                final String where = COLUMN_STUDENT_ID + " = ?";
                final String[] whereArgs = {
                        email
                };
                database.update(TABLE_NAME, values, where, whereArgs);
            } else {
                database.insert(TABLE_NAME, null, values);
            }
        }

        database.setTransactionSuccessful();
        database.endTransaction();
    }

    public void insertData(Student student) {
        ContentValues values = new ContentValues();

        values.put(COLUMN_STUDENT_ID, student.getStudentId());
        values.put(COLUMN_CHILD_NAME, student.getName());
        values.put(COLUMN_FATHER_NAME, student.getFatherName());
        values.put(COLUMN_MOTHER_NAME, student.getMotherName());
        values.put(COLUMN_GENDER, student.getGender());
        values.put(COLUMN_DISTRICT, student.getDistrict());
        values.put(COLUMN_BLOCK, student.getBlock());
        values.put(COLUMN_CLUSTER, student.getCluster());
        values.put(COLUMN_SCHOOL_CODE, student.getSchoolCode());
        values.put(COLUMN_SCHOOL_NAME, student.getSchoolName());
        values.put(COLUMN_CLASS, student.getStudentClass());
        values.put(COLUMN_UID, student.getUid());

        PartnerDB.getInstance().insert(TABLE_NAME, null, values);
    }




    /**
     * Get the all unique value for a field in a given Table
     * @param fieldName Field which need to be get from the Table
     * @param placeHolder A string which is used to display as Hint
     * @return {@code List<String>}
     */
    public ArrayList<String> getUniqueFieldData(String fieldName, String placeHolder) {
        ArrayList<String> fieldData = new ArrayList<>();

        if (TextUtils.isEmpty(fieldName)) {
            return fieldData;
        }


        if (!TextUtils.isEmpty(placeHolder)) {
            fieldData.add(placeHolder);
        }

        Cursor cursor = PartnerDB.getInstance().query(true, TABLE_NAME, new String[]{fieldName},
                null, null, null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                fieldData.add(cursor.getString(0));
            }

        }

        if (fieldData.size() == 2) {
            fieldData.remove(0);
        }

        if (cursor != null) {
            cursor.close();
        }

        return fieldData;
    }


    /**
     * Get the all unique value for a field in a given Table
     * @param fieldName Field which need to be get from the Table
     * @param placeHolder A string which is used to display as Hint
     * @param optionalParams it'll contain both Condition Fields and Value so
     *                       it'll should have always Even count
     *                       first will be field names followed by values
     * @return {@code List<String>}
     */
    public ArrayList<String> getUniqueFieldData(String fieldName, String placeHolder,
                                                String... optionalParams) {

        ArrayList<String> fieldData = new ArrayList<>();

        if (TextUtils.isEmpty(fieldName)) {
            return fieldData;
        }


        if (!TextUtils.isEmpty(placeHolder)) {
            fieldData.add(placeHolder);
        }


        if (optionalParams != null && optionalParams.length % 2 == 0) {
            StringBuilder builder = new StringBuilder();

            for (int i = 0; i < optionalParams.length / 2; i++) {
                if (i != 0) {
                    builder.append(" AND ");
                }
                builder.append(" ");
                builder.append(optionalParams[i]);
                builder.append(" = ?");
            }

            String where = builder.toString();

            if (DEBUG) {
                Log.i(TAG, "getUniqueFieldData: Where " + where);
            }


            String [] whereArgs = new String[optionalParams.length / 2];

            int counter = 0;

            for (int i = optionalParams.length / 2; i < optionalParams.length; i++) {
                whereArgs[counter] = optionalParams[i];
                counter++;
            }

            if (DEBUG) {
                Log.i(TAG, "getUniqueFieldData: Where Args " + Arrays.toString(whereArgs));
            }

            Cursor cursor = PartnerDB.getInstance().query(true, TABLE_NAME, new String[]{fieldName},
                    where, whereArgs, null, null, null, null);

            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    fieldData.add(cursor.getString(0));
                }
            }

            if (cursor != null) {
                cursor.close();
            }

        } else {
            throw new IllegalArgumentException("optionalParams is null or not having even length" +
                    " args");
        }

        if (fieldData.size() == 2) {
            fieldData.remove(0);
        }

        return fieldData;
    }


    /**
     * Return the School Names and codes in a Hash Map
     * @param district District value
     * @param block block Value
     * @param cluster cluster value
     * @return Map of schools & code match with given parameters
     */
    public HashMap<String, String> getAllSchool(String district, String block, String cluster) {
        HashMap<String, String> hashMap = new HashMap<>();

        final String selection = COLUMN_DISTRICT + " = ? AND " + COLUMN_BLOCK + " = ? AND "
                + COLUMN_CLUSTER + " = ? ";

        final String [] selectionArgs = {
                district,
                block,
                cluster
        };

        final String [] columns = {
                COLUMN_SCHOOL_CODE,
                COLUMN_SCHOOL_NAME
        };


        Cursor cursor = PartnerDB.getInstance().query(TABLE_NAME, columns, selection, selectionArgs,
                null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                hashMap.put(cursor.getString(0), cursor.getString(1));
            }
        }

        if (cursor != null) {
            cursor.close();
        }
        return hashMap;
    }


    /**
     * Add student information into the local database
     * @param studentMap HashMap contain the student information
     */
    public void addNewStudent(HashMap<String, Object> studentMap) {
        ContentValues contentValues = new ContentValues();

        Set<String> keySet = studentMap.keySet();

        for (String key : keySet) {
            String value = studentMap.get(key).toString();
            contentValues.put(key, value);
        }

        PartnerDB.getInstance().insert(TABLE_NAME, null, contentValues);
    }


    /**
     * It'll return the List of Student Information which is not synced to Google Excel
     * @return List of Student Information in List of list object format
     */
    public List<List<Object>> getAllUnSyncedData() {
        List<List<Object>> allData = new ArrayList<>();

        final String selection = COLUMN_SYNC + " = ?";
        final String [] selectionArgs = {
                "1"
        };

        Cursor cursor = PartnerDB.getInstance().query(TABLE_NAME, DEFAULT_INSERT_COLUMN_MAP,
                selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                List<Object> studentInfo = new ArrayList<>();
                final int columnCount = cursor.getColumnCount();

                for (int index = 0; index < columnCount; index++) {
                    studentInfo.add(cursor.getString(index));
                }

                allData.add(studentInfo);
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        return allData;
    }


    public void updateSyncState(List<List<Object>> syncedData) {
        if (syncedData != null && syncedData.size() > 0) {
            for (List<Object> studentInfo : syncedData) {
                String studentId = studentInfo.get(7).toString();

                final String where = COLUMN_STUDENT_ID + " = ?";
                final String []whereArgs = {
                        studentId
                };

                ContentValues values = new ContentValues();
                values.put(COLUMN_SYNC, 0);

                PartnerDB.getInstance().update(TABLE_NAME, values, where, whereArgs);
            }
        }
    }



    public Student [] getAllStudentObject(String schoolCode, String grade, String searchBy) {
        Student[] studentInfos = new Student[0];

        if (TextUtils.isEmpty(schoolCode) || TextUtils.isEmpty(searchBy)
                || TextUtils.isEmpty(grade)) {
            throw new IllegalArgumentException("Some parameters are null");
        }

        final String selection = COLUMN_SCHOOL_CODE + " = ? AND " + COLUMN_CHILD_NAME
                + " LIKE '%" + searchBy + "%' AND " + COLUMN_CLASS + " = ?";

        final String []selectionArgs = {
                schoolCode,
                grade
        };

        Cursor cursor = PartnerDB.getInstance().query(TABLE_NAME, null, selection, selectionArgs,
                null, null, null);


        if (cursor != null && cursor.getCount() > 0) {
            studentInfos = new Student[cursor.getCount()];
            int counter = 0;

            while (cursor.moveToNext()) {
                studentInfos[counter] = new Student();

                studentInfos[counter].setDistrict(
                        cursor.getString(cursor.getColumnIndex(COLUMN_DISTRICT)));
                studentInfos[counter].setBlock(
                        cursor.getString(cursor.getColumnIndex(COLUMN_BLOCK)));
                studentInfos[counter].setCluster(
                        cursor.getString(cursor.getColumnIndex(COLUMN_CLUSTER)));
                studentInfos[counter].setSchoolCode(
                        cursor.getString(cursor.getColumnIndex(COLUMN_SCHOOL_CODE)));
                studentInfos[counter].setSchoolName(
                        cursor.getString(cursor.getColumnIndex(COLUMN_SCHOOL_NAME)));
                studentInfos[counter].setStudentClass(
                        cursor.getString(cursor.getColumnIndex(COLUMN_CLASS)));
                studentInfos[counter].setStudentId(
                        cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_ID)));
                studentInfos[counter].setName(
                        cursor.getString(cursor.getColumnIndex(COLUMN_CHILD_NAME)));
                studentInfos[counter].setGender(
                        cursor.getString(cursor.getColumnIndex(COLUMN_GENDER)));
                studentInfos[counter].setFatherName(
                        cursor.getString(cursor.getColumnIndex(COLUMN_FATHER_NAME)));
                studentInfos[counter].setUid(
                        cursor.getString(cursor.getColumnIndex(COLUMN_UID)));
                studentInfos[counter].setMotherName(
                        cursor.getString(cursor.getColumnIndex(COLUMN_MOTHER_NAME)));
                studentInfos[counter].setDob(cursor.getString(cursor.getColumnIndex(COLUMN_DOB)));

                counter++;
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        return studentInfos;
    }


    @Nullable
    public List<Student> getAllGenieStudent() {
        List<Student> studentList = null;

        String where = COLUMN_UID + " IS NOT NULL";

        Cursor cursor = PartnerDB.getInstance().query(TABLE_NAME, null, where,
                null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            studentList = new ArrayList<>();

            while (cursor.moveToNext()) {
                Student student = new Student();

                student.setDistrict(
                        cursor.getString(cursor.getColumnIndex(COLUMN_DISTRICT)));
                student.setBlock(
                        cursor.getString(cursor.getColumnIndex(COLUMN_BLOCK)));
                student.setCluster(
                        cursor.getString(cursor.getColumnIndex(COLUMN_CLUSTER)));
                student.setSchoolCode(
                        cursor.getString(cursor.getColumnIndex(COLUMN_SCHOOL_CODE)));
                student.setSchoolName(
                        cursor.getString(cursor.getColumnIndex(COLUMN_SCHOOL_NAME)));
                student.setStudentClass(
                        cursor.getString(cursor.getColumnIndex(COLUMN_CLASS)));
                student.setStudentId(
                        cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_ID)));
                student.setName(
                        cursor.getString(cursor.getColumnIndex(COLUMN_CHILD_NAME)));
                student.setGender(
                        cursor.getString(cursor.getColumnIndex(COLUMN_GENDER)));
                student.setFatherName(
                        cursor.getString(cursor.getColumnIndex(COLUMN_FATHER_NAME)));
                student.setUid(
                        cursor.getString(cursor.getColumnIndex(COLUMN_UID)));
                student.setMotherName(
                        cursor.getString(cursor.getColumnIndex(COLUMN_MOTHER_NAME)));
                student.setDob(cursor.getString(cursor.getColumnIndex(COLUMN_DOB)));

                studentList.add(student);
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        return studentList;
    }


    @Nullable
    public List<Student> getAllStudent(String schoolId, String klass) {
        List<Student> studentList = null;

        final String where = COLUMN_SCHOOL_CODE + " = ? AND " + COLUMN_CLASS + " = ?";
        final String [] whereArgs = {
                schoolId,
                klass
        };

        Cursor cursor = PartnerDB.getInstance().query(TABLE_NAME, null, where,
                whereArgs, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            studentList = new ArrayList<>();

            while (cursor.moveToNext()) {
                Student student = new Student();

                student.setDistrict(
                        cursor.getString(cursor.getColumnIndex(COLUMN_DISTRICT)));
                student.setBlock(
                        cursor.getString(cursor.getColumnIndex(COLUMN_BLOCK)));
                student.setCluster(
                        cursor.getString(cursor.getColumnIndex(COLUMN_CLUSTER)));
                student.setSchoolCode(
                        cursor.getString(cursor.getColumnIndex(COLUMN_SCHOOL_CODE)));
                student.setSchoolName(
                        cursor.getString(cursor.getColumnIndex(COLUMN_SCHOOL_NAME)));
                student.setStudentClass(
                        cursor.getString(cursor.getColumnIndex(COLUMN_CLASS)));
                student.setStudentId(
                        cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_ID)));
                student.setName(
                        cursor.getString(cursor.getColumnIndex(COLUMN_CHILD_NAME)));
                student.setGender(
                        cursor.getString(cursor.getColumnIndex(COLUMN_GENDER)));
                student.setFatherName(
                        cursor.getString(cursor.getColumnIndex(COLUMN_FATHER_NAME)));
                student.setUid(
                        cursor.getString(cursor.getColumnIndex(COLUMN_UID)));
                student.setMotherName(
                        cursor.getString(cursor.getColumnIndex(COLUMN_MOTHER_NAME)));
                student.setDob(cursor.getString(cursor.getColumnIndex(COLUMN_DOB)));

                studentList.add(student);
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        return studentList;
    }



    public List<Student> searchOnStudent(String schoolId, String klass, String searchBy) {
        List<Student> studentList = null;

        if (TextUtils.isEmpty(schoolId) || TextUtils.isEmpty(searchBy)
                || TextUtils.isEmpty(klass)) {
            throw new IllegalArgumentException("Some parameters are null");
        }


        final String selection = COLUMN_SCHOOL_CODE + " = ? AND " + COLUMN_CHILD_NAME
                + " LIKE '%" + searchBy + "%' AND " + COLUMN_CLASS + " = ?";

        final String []selectionArgs = {
                schoolId,
                klass
        };

        Cursor cursor = PartnerDB.getInstance().query(TABLE_NAME, null, selection, selectionArgs,
                null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            studentList = new ArrayList<>();

            while (cursor.moveToNext()) {
                Student student = new Student();

                student.setDistrict(
                        cursor.getString(cursor.getColumnIndex(COLUMN_DISTRICT)));
                student.setBlock(
                        cursor.getString(cursor.getColumnIndex(COLUMN_BLOCK)));
                student.setCluster(
                        cursor.getString(cursor.getColumnIndex(COLUMN_CLUSTER)));
                student.setSchoolCode(
                        cursor.getString(cursor.getColumnIndex(COLUMN_SCHOOL_CODE)));
                student.setSchoolName(
                        cursor.getString(cursor.getColumnIndex(COLUMN_SCHOOL_NAME)));
                student.setStudentClass(
                        cursor.getString(cursor.getColumnIndex(COLUMN_CLASS)));
                student.setStudentId(
                        cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_ID)));
                student.setName(
                        cursor.getString(cursor.getColumnIndex(COLUMN_CHILD_NAME)));
                student.setGender(
                        cursor.getString(cursor.getColumnIndex(COLUMN_GENDER)));
                student.setFatherName(
                        cursor.getString(cursor.getColumnIndex(COLUMN_FATHER_NAME)));
                student.setUid(
                        cursor.getString(cursor.getColumnIndex(COLUMN_UID)));
                student.setMotherName(
                        cursor.getString(cursor.getColumnIndex(COLUMN_MOTHER_NAME)));
                student.setDob(cursor.getString(cursor.getColumnIndex(COLUMN_DOB)));

                studentList.add(student);
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        return studentList;
    }

//
//
//    /**
//     * Validate email & password and return if they exits it'll return User Info Hash map
//     * @param email Email id used to be validate
//     * @param password password of given Email
//     * @return if email & password is exits it'll return HashMap of Student otherwise null
//     */
//    public HashMap<String, String> validateUserEmailAndPassword(String email, String password) {
//        final String where = COLUMN_STUDENT_ID + " = ? AND " + COLUMN_PASSWORD + " = ?";
//        final String[] whereArgs = {
//                email,
//                password
//        };
//
//        Cursor cursor = PartnerDB.getInstance().query(TABLE_NAME, null, where, whereArgs, null,
//                null, null);
//
//        HashMap<String, String> map = null;
//
//        if (cursor != null && cursor.getCount() > 0) {
//            cursor.moveToNext();
//
//            int columnCount = cursor.getColumnCount();
//
//            if (columnCount == ALL_COLUMN_MAP.length) {
//                map = new HashMap<>();
//
//                for (int i = 0; i < columnCount; i++) {
//                    String key = ALL_COLUMN_MAP[i];
//                    String value = cursor.getString(cursor.getColumnIndex(key));
//
//                    map.put(key, value);
//                }
//            }
//        }
//
//        if (cursor != null) {
//            cursor.close();
//        }
//
//        return map;
//    }
//
//
    /**
     * Update the UID in the Student Information
     * @param studentId Email id
     * @param uid UID map with Student Information
     */
    public void updateStudentUID(String studentId, String uid) {
        final String where = COLUMN_STUDENT_ID + " = ?";
        final String [] whereArgs = {
                studentId
        };

        ContentValues values = new ContentValues();
        values.put(COLUMN_UID, uid);

        PartnerDB.getInstance().update(TABLE_NAME, values, where, whereArgs);
    }
//
//
    /**
     * Check given studentId id is exits in table or not
     * @param database Database used to retrieve the data
     * @param studentId studentId which checked in the table
     * @return true if the <code>studentId</code> exists otherwise false
     */
    private boolean isValueExists(SQLiteDatabase database, String studentId) {
        final String selection = COLUMN_STUDENT_ID + " = ?";
        final String [] selectionArgs = {
            studentId
        };

        boolean isExists = false;

        Cursor cursor = database.query(TABLE_NAME, null, selection, selectionArgs, null, null,
                null);

        if (cursor != null && cursor.getCount() > 0) {
            isExists = true;
        }

        if (cursor != null) {
            cursor.close();
        }

        return isExists;
    }



}

