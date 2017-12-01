package org.ekstep.ipa.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author vinayagasundar
 */

public class Student implements Parcelable {

    private String studentId;

    private String district;
    private String block;
    private String cluster;

    private String schoolCode;
    private String schoolName;

    private String studentClass;

    private String name;
    private String gender;
    private String dob;
    private String motherTongue;
    private String fatherName;
    private String motherName;

    private String uid;

    private int sync;

    public Student() {
    }


    protected Student(Parcel in) {
        studentId = in.readString();
        district = in.readString();
        block = in.readString();
        cluster = in.readString();
        schoolCode = in.readString();
        schoolName = in.readString();
        studentClass = in.readString();
        name = in.readString();
        gender = in.readString();
        dob = in.readString();
        motherTongue = in.readString();
        fatherName = in.readString();
        motherName = in.readString();
        uid = in.readString();
        sync = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(studentId);
        dest.writeString(district);
        dest.writeString(block);
        dest.writeString(cluster);
        dest.writeString(schoolCode);
        dest.writeString(schoolName);
        dest.writeString(studentClass);
        dest.writeString(name);
        dest.writeString(gender);
        dest.writeString(dob);
        dest.writeString(motherTongue);
        dest.writeString(fatherName);
        dest.writeString(motherName);
        dest.writeString(uid);
        dest.writeInt(sync);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getMotherTongue() {
        return motherTongue;
    }

    public void setMotherTongue(String motherTongue) {
        this.motherTongue = motherTongue;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getSync() {
        return sync;
    }

    public void setSync(int sync) {
        this.sync = sync;
    }
}
