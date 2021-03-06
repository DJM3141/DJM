
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class PrintInterface {
	PrintInterface() {
		double userCredits = DisplayInterface.getUserMaxCredits();
		JFrame f = new JFrame("PrintInterface");
		f.setContentPane(new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Background3.jpg")))));
		ImageIcon img = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("dog1.png.png")));
		f.setIconImage(img.getImage());


		// Main add to frame
		f.setSize(800, 800);
		f.setLayout(null);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Code for printing out the Final Schedule
		scheduler newScheduler = new scheduler(DraftInterface.getCourseList()); 
		ArrayList<schedule> finalData = newScheduler.createSchedules(userCredits);
		
		if (finalData.isEmpty()) {
			if (userCredits < newScheduler.getPriorityCreditSum()) {
				JOptionPane.showMessageDialog(null, "Error: Cannot add all priority classes due to too many credits in priority list for the amount of desired credits");
			} else {
				JOptionPane.showMessageDialog(null, "Error: Cannot add all priority classes due to conflicting classes");
			}
			return;
		}
		
		// Prints out the Final Schedule to a File
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new File("Finalized Class List.txt"));
			//Print template
			pw.println("******************************************************************************************");
			pw.println("            			Scheduler.exe was developed by:");
			pw.println("            			Matt L., Joell E., and Dan R.");
			pw.println();
			pw.println();
			pw.println("This program was developed to help assist you in creating multiple schedules");
			pw.println("for you courses each semester. It takes into account your priority class/departmental");
			pw.println("and fills in any non priority classes if they fit.");
			pw.println();
			pw.println("Each calendar printed out goes day by day and includes a list of the CRNS for easy use");
			pw.println("to register. Feel free to edit the text file and add semester names or class names.");
			pw.println("******************************************************************************************");
			pw.println();
			
			for (int scheduleNumber = 0; scheduleNumber < finalData.size(); scheduleNumber++) {
				pw.println("Schedule " + (scheduleNumber + 1));
				pw.println();
				
				ArrayList<String> crns = new ArrayList<String>();
				
				for (int day = 0; day < 7; day++) {
					if (day == 0)
						pw.println("Sunday");
					else if (day == 1)
						pw.println("Monday");
					else if (day == 2)
						pw.println("Tuesday");
					else if (day == 3)
						pw.println("Wednesday");
					else if (day == 4)
						pw.println("Thursday");
					else if (day == 5)
						pw.println("Friday");
					else if (day == 6)
						pw.println("Saturday");
					for (int j = 0; j < finalData.get(scheduleNumber).getOfferingsList(day).size(); j++){
						pw.println(finalData.get(scheduleNumber).getOfferingsList(day).get(j).getDepartment() + finalData.get(scheduleNumber).getOfferingsList(day).get(j).getLevel() + "      "
								+ finalData.get(scheduleNumber).getOfferingsList(day).get(j).getStartTime() + " - "
								+ finalData.get(scheduleNumber).getOfferingsList(day).get(j).getEndTime());
						
						if (!crns.contains(finalData.get(scheduleNumber).getOfferingsList(day).get(j).getCourseNumber())) {
							crns.add(finalData.get(scheduleNumber).getOfferingsList(day).get(j).getCourseNumber());
						}
						
					}
					pw.println();
				}
				pw.println();
				
				pw.println("Available Non-Priority Courses");
				for (int index = 0; index < finalData.get(scheduleNumber).getFillerList().size(); index++) {
					pw.println(finalData.get(scheduleNumber).getFillerList().get(index).getDepartment() + finalData.get(scheduleNumber).getFillerList().get(index).getLevel()
							+ "     " + finalData.get(scheduleNumber).getFillerList().get(index).getStartTime() + " - "
							+ finalData.get(scheduleNumber).getFillerList().get(index).getEndTime() + "   CRN: " + finalData.get(scheduleNumber).getFillerList().get(index).getCourseNumber());
				}
				pw.println();
				
				pw.println("CRNS For Priority Classes in This Schedule:");
				for (int i = 0; i < crns.size(); i++) {
					pw.println(crns.get(i));
				}
				pw.println();
				pw.println();
			}
			
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			pw.close();
		}
		
		// Display the final schedules in the application
		JTextArea CurrentCourseList = new JTextArea();
		String courseList = "";
		
		for (int scheduleNumber = 0; scheduleNumber < finalData.size(); scheduleNumber++) {
			courseList = courseList + "Schedule " + (scheduleNumber + 1) + "\n\n";
			for (int day = 0; day < 7; day++) {
				if (day == 0)
					courseList = courseList + "Sunday\n\n";
				else if (day == 1)
					courseList = courseList + "Monday\n\n";
				else if (day == 2)
					courseList = courseList + "Tuesday\n\n";
				else if (day == 3)
					courseList = courseList + "Wednesday\n\n";
				else if (day == 4)
					courseList = courseList + "Thursday\n\n";
				else if (day == 5)
					courseList = courseList + "Friday\n\n";
				else if (day == 6)
					courseList = courseList + "Saturday\n\n";
				for (int j = 0; j < finalData.get(scheduleNumber).getOfferingsList(day).size(); j++)
					courseList = courseList + finalData.get(scheduleNumber).getOfferingsList(day).get(j).getDepartment() + finalData.get(scheduleNumber).getOfferingsList(day).get(j).getLevel() + "      "
							+ finalData.get(scheduleNumber).getOfferingsList(day).get(j).getStartTime() + " - "
							+ finalData.get(scheduleNumber).getOfferingsList(day).get(j).getEndTime() + "\n";
				courseList = courseList + "\n";
			}
			courseList = courseList + "\n\n";
			
			courseList = courseList + "Available Non-Priority Courses\n";
			for (int index = 0; index < finalData.get(scheduleNumber).getFillerList().size(); index++) {
				courseList = courseList + finalData.get(scheduleNumber).getFillerList().get(index).getDepartment() + finalData.get(scheduleNumber).getFillerList().get(index).getLevel()
						+ "     " + finalData.get(scheduleNumber).getFillerList().get(index).getStartTime() + " - "
						+ finalData.get(scheduleNumber).getFillerList().get(index).getEndTime() + "   CRN: " + finalData.get(scheduleNumber).getFillerList().get(index).getCourseNumber() + "\n";
			}
			courseList = courseList + "\n\n";
		}
		
		CurrentCourseList.setText(courseList);
		CurrentCourseList.setOpaque(true);
		CurrentCourseList.setBounds(225, 200, 300, 300);
		CurrentCourseList.setEditable(false);
		CurrentCourseList.setVisible(true);
		CurrentCourseList.setLineWrap(true);
		
		JScrollPane scrollV = new JScrollPane (CurrentCourseList); 
		scrollV.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollV.setBounds(225, 200, 300, 300);
		f.add(scrollV);
		
	}
	
	public static void main(String[] args) {
		new PrintInterface();
	}
}