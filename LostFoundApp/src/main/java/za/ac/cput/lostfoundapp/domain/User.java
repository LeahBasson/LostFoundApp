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
    private boolean is_active;
    private String language;

    public User(int user_id, String student_staff_number, String email, String full_name, String campus, String contact_number, String role, String password_hash, boolean is_active, String language) {
        this.user_id = user_id;
        this.student_staff_number = student_staff_number;
        this.email = email;
        this.full_name = full_name;
        this.campus = campus;
        this.contact_number = contact_number;
        this.role = role;
        this.password_hash = password_hash;
        this.is_active = is_active;
        this.language = language;
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

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

}// end of class
