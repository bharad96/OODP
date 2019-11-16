package com.company.Utils;
import com.company.Entity.Staff;
import java.io.IOException;
import java.util.ArrayList;

public class staffConfig {
    public static void main(String[] args){
        //Staff configuration
        try {
            Staff staff1 = new Staff("S001", "admin", "password", "SuperAdmin");
            ArrayList<Staff> staffList = new ArrayList<Staff>();
            staffList.add(staff1);
            Utils.writeObject("staff.txt", (Object)staffList);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            ArrayList<Staff> staffList2 = (ArrayList<Staff>)Utils.readObject("staff.txt");
            for(Staff c: staffList2) {
                System.out.println(c.getName());
                System.out.println(c.getUserName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
