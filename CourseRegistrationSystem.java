import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class CourseRegistrationSystem {
    private ArrayList<Course> courses;
    private LinkedList<Student> students;
    private Scanner sc;

    public CourseRegistrationSystem() {
        courses = new ArrayList<>();
        students = new LinkedList<>();
        sc = new Scanner(System.in);  // Initialize the scanner
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void displayAvailableCourses() {
        System.out.println("Available Courses:");
        for (Course course : courses) {
            course.displayCourseDetails();
            System.out.println();
        }
    }

    public Student findStudent(String studentID) {
        for (Student student : students) {
            if (student.getStudentID().equals(studentID)) {
                return student;
            }
        }
        return null;
    }

    public Course findCourse(String courseCode) {
        for (Course course : courses) {
            if (course.getCode().equals(courseCode)) {
                return course;
            }
        }
        return null;
    }

    // Helper method to get a valid student
    private Student getValidStudent() {
        System.out.print("Enter Student ID: ");
        String studentID = sc.nextLine();
        Student student = findStudent(studentID);
        
        if (student == null) {
            System.out.println("Student not found.");
            return null;
        }
        return student;
    }

    public void studentRegistration() {
        Student student = getValidStudent();
        if (student == null) return;  // Exit if student not found

        displayAvailableCourses();
        System.out.print("Enter Course Code to Register: ");
        String courseCode = sc.nextLine();
        Course course = findCourse(courseCode);

        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        if (student.registerCourse(course)) {
            System.out.println("Successfully registered!");
        } else {
            System.out.println("Registration failed. Course is full.");
        }
    }

    public void studentCourseRemoval() {
        Student student = getValidStudent();
        if (student == null) return;  // Exit if student not found

        student.displayRegisteredCourses();
        System.out.print("Enter Course Code to Drop: ");
        String courseCode = sc.nextLine();
        Course course = findCourse(courseCode);

        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        if (student.dropCourse(course)) {
            System.out.println("Successfully dropped the course.");
        } else {
            System.out.println("Course removal failed. You are not registered in the course.");
        }
    }

    public static void main(String[] args) {
        CourseRegistrationSystem system = new CourseRegistrationSystem();

        // Adding some courses
        Course course1 = new Course("CS101", "Intro to Computer Science", "Basics of CS", 30);
        Course course2 = new Course("CS102", "Data Structures", "Learn Data Structures", 25);
        system.addCourse(course1);
        system.addCourse(course2);

        // Adding some students
        Student student1 = new Student("1", "Shrinika");
        Student student2 = new Student("2", "Kashvi");
        system.addStudent(student1);
        system.addStudent(student2);

        // Simulating the registration and removal process
        system.studentRegistration();
        system.studentCourseRemoval();

        system.sc.close();  // Close the scanner to avoid resource leak
    }
}

class Course {
    private String code;
    private String title;
    private String description;
    private int maxEnrollment;
    private ArrayList<Student> registeredStudents;

    public Course(String code, String title, String description, int maxEnrollment) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.maxEnrollment = maxEnrollment;
        this.registeredStudents = new ArrayList<>();
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public int getAvailableSlots() {
        return maxEnrollment - registeredStudents.size();
    }

    public boolean registerStudent(Student student) {
        if (registeredStudents.size() < maxEnrollment) {
            registeredStudents.add(student);
            return true;
        } else {
            return false;
        }
    }

    public boolean removeStudent(Student student) {
        if (registeredStudents.contains(student)) {
            registeredStudents.remove(student);
            return true;
        } else {
            return false;
        }
    }

    public void displayCourseDetails() {
        System.out.println("Course Code: " + code);
        System.out.println("Title: " + title);
        System.out.println("Description: " + description);
        System.out.println("Available Slots: " + getAvailableSlots());
    }
}

class Student {
    private String studentID;
    private String name;
    private ArrayList<Course> registeredCourses;

    public Student(String studentID, String name) {
        this.studentID = studentID;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public String getStudentID() {
        return studentID;
    }

    public boolean registerCourse(Course course) {
        if (!registeredCourses.contains(course)) {
            if (course.registerStudent(this)) {
                registeredCourses.add(course);
                return true;
            } else {
                return false;
            }
        } else {
            System.out.println("Already registered in the course.");
            return false;
        }
    }

    public boolean dropCourse(Course course) {
        if (registeredCourses.contains(course)) {
            if (course.removeStudent(this)) {
                registeredCourses.remove(course);
                return true;
            } else {
                return false;
            }
        } else {
            System.out.println("Not registered in the course.");
            return false;
        }
    }

    public void displayRegisteredCourses() {
        System.out.println("Registered Courses for " + name + ":");
        for (Course course : registeredCourses) {
            System.out.println("- " + course.getTitle());
        }
    }
}
