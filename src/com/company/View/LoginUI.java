package com.company.View;
import com.company.Controller.LoginController;
import com.company.Entity.Customer;
import com.company.Utils.UserInputOutput;


import java.io.IOException;
import java.util.Scanner;

import java.util.*;

/**
 * Login UI to allow user to sign in as admin or movie goer
 * @author Group 2 - SS6
 * @version 1.0
 * @since 2019-11-12
 */
public class LoginUI implements GeneralUI {
    /**
     * Display UI for user to choose user type ( Admin / Movie Goer )
     */
   public void displayHomePage() {
      LoginController lc = new LoginController();
      lc.updateMovieStatus();
      System.out.println("Select User Type: ");
      System.out.println("1) Admin");
      System.out.println("2) Movie Goer");

      boolean exit = false;
      while(!exit) {
         Scanner sc = new Scanner(System.in);
         try {
            int choice = sc.nextInt();
            switch(choice) {
               case 1:
                  displayAdminLoginPage();
                  exit=true;
                  break;
               case 2:
                  displayLoginRegisterPage();
                  exit=true;
                  break;
               default:
                  System.out.println("Please input 1 or 2.");
            }
         }
         catch (InputMismatchException e) {
            System.out.println("Please input an integer.");
         }
      }
   }

    /**
     * Allow Movie Goer to login or register
     */
   public void displayLoginRegisterPage() {
      UserInputOutput.displayHeader("Login/Register");
      boolean end= false;

      while (!end) {
         System.out.println("Please make a selection");
         System.out.println("1) Log In");
         System.out.println("2) Register");

         int select = UserInputOutput.getUserChoice(1,2);
         switch (select) {
            case 1:
               displayPublicLoginPage();
               end = true;
               break;
            case 2:
               displayPublicRegisterPage();
               break;
            default:
               System.out.println("Please select 1 or 2");
         }

      }
   }

    /**
     * Allow user goer to  register a new account
     */
   public void displayPublicRegisterPage() {
      UserInputOutput.displayHeader("Customer Register");
      LoginController logCtrl = new LoginController();
      ArrayList<Customer> customers = new ArrayList<>();

      boolean  credentialCheck = false;

         System.out.println("Please input Email:");
         Scanner sc = new Scanner(System.in);
         String email = sc.nextLine();
         credentialCheck = logCtrl.checkCustomer(email);
         if(credentialCheck) {
            System.out.println("Account already exist!");
            displayLoginRegisterPage();
         }
         else
         {
            System.out.println("Please enter your name");
            String name = sc.nextLine();
            System.out.println("Please enter mobile phone number");
            String hpnum = sc.nextLine();

            customers = logCtrl.readCustomer();

            Customer newcustomer = new Customer(name,hpnum, email);
            customers.add(newcustomer);
            logCtrl.writeCustomer(customers);
             System.out.println("Successfully Registered!");
             System.out.println("Name: "+ newcustomer.getName());
             System.out.println("Phone number: " + newcustomer.getPhone());
             System.out.println("Email: " + newcustomer.getEmail());
         }
      }
      //MovieGoerUI mui = new MovieGoerUI();
      //mui.getHomeView();


    /**
     *  Display Movie Goer Log in Page and allow them to login with their email address
     */
   public void displayPublicLoginPage() {
      LoginController logCtrl = new LoginController();
      UserInputOutput.displayHeader("Customer Login");
      boolean  credentialCheck = false;
         System.out.println("Please input Email:");
         Scanner sc = new Scanner(System.in);
         String email = sc.nextLine();
         credentialCheck = logCtrl.checkCustomer(email);
         if(!credentialCheck) {
            System.out.println("Invalid account.");
         }

      if (credentialCheck == true) {
         Customer c = logCtrl.getCusCookie();

         System.out.println("Hi " + c.getName() + "!");
         MovieGoerUI mui = new MovieGoerUI();
         UIDisplay uid = new UIDisplay(mui);
         uid.displayHomePage();
      }
      else{
         displayLoginRegisterPage();
      }
   }

    /**
     * display admin Login page and allow user to log in
     */
   public void displayAdminLoginPage() {
      LoginController logCtrl = new LoginController();
      UserInputOutput.displayHeader("Admin Login");
   	
   	//Check password
      boolean credentialCheck = false;
      while(!credentialCheck) {
         Scanner sc = new Scanner(System.in);
         System.out.println("Please input Username:");
         String username = sc.nextLine();
         System.out.println("Please input Password:");
         String password = sc.nextLine();
         credentialCheck = logCtrl.checkAdmin(username,password);
         if(!credentialCheck) {
            System.out.println("Invalid account.");
         }
      }
      StaffUI sui = new StaffUI();
      UIDisplay uid = new UIDisplay(sui);
      uid.displayHomePage();
   }
}
