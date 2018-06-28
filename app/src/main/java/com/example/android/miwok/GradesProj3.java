package com.example.android.miwok;

/**
 * Created by redne on 4/7/2018.
 */

/* Keeps track of grades via getter, setter, and summary methods
*
 */
class GradesProj3 {

    //global variables
    private String studentSubject;
    private int studentSubjectGrade;

    //default constructor
    GradesProj3 (String mySubject,int myGrade){
        studentSubject = mySubject;
        studentSubjectGrade  =myGrade;
    }
    //setter method
     public void setSubject(String mySubject){ studentSubject=mySubject;}
     public void studentSubjectGrade(int myGrade){ studentSubjectGrade=myGrade;}
    //getter method
    public String getStudentSubject() {return studentSubject; }
    public int getStudentSubjectGrade(){return studentSubjectGrade;}
    // Summar toString method

    @Override
    public String toString() {
        //Your code here!  Return a representation of
        //the report card rather than the empty string

        return "The Student earned an " + studentSubjectGrade + " in " + studentSubject + "." ;
    }


}
