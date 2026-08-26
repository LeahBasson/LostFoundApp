package za.ac.cput.lostfoundapp.domain;

public class User {

    private int user_id;
    private String student_staff_number;
    private String email;
    private String full_name;
    private String campus;
    private String contact_number;
    private String role;
    private String password_hash;
    private String language;

    public User(String full_name, String contact_number, String campus, String language, String email, String student_staff_number, String role){
        this.full_name = full_name;
        this.contact_number = contact_number;
        this.campus = campus;
        this.language = language;
        this.email = email;
        this.student_staff_number = student_staff_number;
        this.role = role;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getStudent_staff_number() {
        return student_staff_number;
    }

    public void setStudent_staff_number(String student_staff_number) {
        this.student_staff_number = student_staff_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

}// end of class
