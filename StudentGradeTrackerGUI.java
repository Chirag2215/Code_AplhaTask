import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class StudentGradeTrackerGUI extends JFrame {
    private JTextField nameField, gradeField;
    private JTextArea outputArea;
    private ArrayList<Student> students = new ArrayList<>();

    static class Student {
        String name;
        int grade;

        Student(String name, int grade) {
            this.name = name;
            this.grade = grade;
        }
    }

    public StudentGradeTrackerGUI() {
        setTitle("Student Grade Tracker");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        add(new JLabel("Student Name:"));
        nameField = new JTextField(20);
        add(nameField);

        add(new JLabel("Grade:"));
        gradeField = new JTextField(5);
        add(gradeField);

        JButton addButton = new JButton("Add Student");
        JButton reportButton = new JButton("Show Report");
        add(addButton);
        add(reportButton);

        outputArea = new JTextArea(15, 40);
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea));

        addButton.addActionListener(e -> addStudent());
        reportButton.addActionListener(e -> showReport());
    }

    private void addStudent() {
        String name = nameField.getText();
        String gradeText = gradeField.getText();

        if (name.isEmpty() || gradeText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both name and grade.");
            return;
        }

        try {
            int grade = Integer.parseInt(gradeText);
            students.add(new Student(name, grade));
            outputArea.append("Added: " + name + " - Grade: " + grade + "\n");
            nameField.setText("");
            gradeField.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Grade must be a number.");
        }
    }

    private void showReport() {
        if (students.isEmpty()) {
            outputArea.setText("No students to report.\n");
            return;
        }

        int total = 0, highest = Integer.MIN_VALUE, lowest = Integer.MAX_VALUE;
        String topStudent = "", lowStudent = "";

        StringBuilder report = new StringBuilder("=== Summary Report ===\n");

        for (Student s : students) {
            report.append(s.name).append(" - Grade: ").append(s.grade).append("\n");
            total += s.grade;

            if (s.grade > highest) {
                highest = s.grade;
                topStudent = s.name;
            }

            if (s.grade < lowest) {
                lowest = s.grade;
                lowStudent = s.name;
            }
        }

        double average = (double) total / students.size();
        report.append("\nAverage Grade: ").append(String.format("%.2f", average));
        report.append("\nHighest Grade: ").append(highest).append(" (").append(topStudent).append(")");
        report.append("\nLowest Grade: ").append(lowest).append(" (").append(lowStudent).append(")");

        outputArea.setText(report.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StudentGradeTrackerGUI().setVisible(true);
        });
    }
}