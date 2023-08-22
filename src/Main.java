import java.util.*;

abstract class People {
    private String name;
    private int id;
    public People(String name, int id) {
        this.name = name;
        this.id = id;
    }
    public People(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}

class Student extends People implements Comparable<Student> {
    private int course;
    private int yearOfBirth;
    private int groupNumber;

    public Student(String name, int id, int course, int yearOfBirth) {
        super(name, id);
        this.course = course;
        this.yearOfBirth = yearOfBirth;
    }
    public Student(String name) {
        super(name);
    }

    public Student(String name, int id, int course, int yearOfBirth, int groupNumber) {
        super(name, id);
        this.course = course;
        this.yearOfBirth = yearOfBirth;
        this.groupNumber = groupNumber;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public int getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(int groupNumber) {
        this.groupNumber = groupNumber;
    }

    @Override
    public int compareTo(Student o) {
        return Integer.compare(this.yearOfBirth, o.yearOfBirth);
    }

    @Override
    public String toString() {
        return "Student{ " +
                (getName() != null ? "firstName=" + getName() + " " : "") +
                (course != 0 ? "course=" + course + " " : "") +
                (yearOfBirth != 0 ? "age=" + yearOfBirth + " " : "") +
                (groupNumber != 0 ? "groupNumber=" + groupNumber + " " : "") +
                '}';
    }
}

class StudentGroup implements Iterable<Student> {
    private List<Student> studentList;
    private int groupNumber;

    public StudentGroup(List<Student> studentList, int groupNumber) {
        this.studentList = studentList;
        this.groupNumber = groupNumber;
    }

    public int getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(int groupNumber) {
        this.groupNumber = groupNumber;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    @Override
    public Iterator<Student> iterator() {
        return new StudentGroupIterator(this);
    }
}
class StudentGroupIterator implements Iterator<Student> {
    private final List<Student> students;
    private int cursor;

    public StudentGroupIterator(StudentGroup studentGroup) {
        this.students = studentGroup.getStudentList();
    }

    @Override
    public boolean hasNext() {
        return cursor < students.size();
    }

    @Override
    public Student next() {
        return students.get(cursor++);
    }

    @Override
    public void remove() {
        this.students.remove(cursor);
    }
}

class Potok implements Iterable<StudentGroup>{
    private List<StudentGroup> studentGroup;

    public Potok(List<StudentGroup> studentGroup) {
        this.studentGroup = studentGroup;
    }

    public List<StudentGroup> getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(List<StudentGroup> studentGroup) {
        this.studentGroup = studentGroup;
    }

    @Override
    public Iterator<StudentGroup> iterator() {
        return new PotokIterator(this);
    }
}


class PotokIterator implements Iterator<StudentGroup> {
    private int cursor;
    private final Potok groundStream;
    private final List<StudentGroup> studentGroups;

    public PotokIterator(Potok groundStream) {
        this.groundStream = groundStream;
        this.studentGroups = groundStream.getStudentGroup();
    }

    @Override
    public boolean hasNext() {
        return cursor < studentGroups.size();
    }

    @Override
    public StudentGroup next() {
        return studentGroups.get(cursor++);
    }

    @Override
    public void remove() {
        this.studentGroups.remove(cursor);
    }
}

class PotokComparator implements Comparator<Potok> {
    @Override
    public int compare(Potok o1, Potok o2) {
        int o11=o1.getStudentGroup().size();
        int o22=o2.getStudentGroup().size();
        return Integer.compare(o11, o22);
    }
}

interface Controller<E, I> {
    E create(E entity);

    E findById(I id);
}
interface UserController<E extends People, I> extends Controller<E, I> {
    E findByLastName(String LastName);
}

public class Main {
    public static void main(String[] args) {
       Student st1 = new Student("Саша", 1, 1, 1992, 393);
       Student st2 = new Student("Маша", 2, 2, 1993, 222);
       Student st3 = new Student("Петя", 3, 2, 1993,222);

       ArrayList<Student> studentGroups = new ArrayList<>();
       studentGroups.add(st1);
       studentGroups.add(st2);
       studentGroups.add(st3);
       System.out.println(studentGroups);

       StudentGroup sg1 = new StudentGroup(studentGroups, 393);
       StudentGroup sg2 = new StudentGroup(studentGroups, 222);
        System.out.println(sg1);
        System.out.println(sg2);

        Potok p1 = new Potok(Collections.singletonList(sg1));
        System.out.println(p1);
    }
}