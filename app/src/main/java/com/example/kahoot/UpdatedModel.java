package com.example.kahoot;

public class UpdatedModel {
    String questionNo,question,questionMarks,option1,option2,option3,option4,correctOption,userAnswer,gainMarks;

    public UpdatedModel(String questionNo, String question, String questionMarks, String option1, String option2, String option3, String option4, String correctOption, String userAnswer, String gainMarks) {
        this.questionNo = questionNo;
        this.question = question;
        this.questionMarks = questionMarks;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.correctOption = correctOption;

        if(userAnswer==null)
            this.userAnswer="Nan";
        else
            this.userAnswer = userAnswer;

        if(gainMarks==null)
            this.gainMarks="Nan";
        else
            this.gainMarks = gainMarks;
    }

    public UpdatedModel() {
    }

    public String getGainMarks() {
        return gainMarks;
    }

    public void setGainMarks(String gainMarks) {
        this.gainMarks = gainMarks;
    }

    public String getQuestionNo() {
        return questionNo;
    }

    public void setQuestionNo(String questionNo) {
        this.questionNo = questionNo;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestionMarks() {
        return questionMarks;
    }

    public void setQuestionMarks(String questionMarks) {
        this.questionMarks = questionMarks;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public String getCorrectOption() {
        return correctOption;
    }

    public void setCorrectOption(String correctOption) {
        this.correctOption = correctOption;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }
}
