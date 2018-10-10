/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.aerlingus;

/**
 *
 * @author a00237745
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//import javax.swing.event.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
//import java.net.*;

public class AerLingusUI extends javax.swing.JFrame {

    Connection con;	// database connection
    Statement stmt;      // used for executing database stmts
    ResultSet rs;	// holds result of stmt, moves to next row in database
    ResultSet rs2;
    int count;
    int current;

    String cName;
    String cAddr;
    String cType;
    String cNation;
    String cPhonNo;

    String cflightno;
    String cfrom;
    String cto;
    String cdeparture;
    String carrival;

    @SuppressWarnings("LeakingThisInConstructor")
    /**
     * Creates new form AerLingusUI
     */
    public AerLingusUI() {
        initComponents();
        con = null;
        stmt = null;
        rs = null;
        rs2 = null;
        count = 0;
        current = 0;

        dbConn();		// method to connect to database using odbc-jdbc
        initDB();		// method to initialise gui with database info


    }

    private void dbConn() {
        try {
            // driver to use with named database
            String url = "jdbc:ucanaccess://E:/Database/Database1.accdb";

            // connection represents a session with a specific database
            con = DriverManager.getConnection(url);
        
            // stmt used for executing sql statements and obtaining results
            stmt = con.createStatement();
        
            rs = stmt.executeQuery("SELECT * FROM Customers");
            rs2 = stmt.executeQuery("SELECT * FROM Flights");

            while (rs.next()) // count number of rows in table
            {
                count++;
                System.out.println("Number of rows : " + count);
            }
            rs.close();

            while (rs2.next()) // count number of rows in table
            {
                count++;
                System.out.println("Number of rows : " + count);
            }
            rs2.close();
        } catch (Exception e) {
            System.out.println("Error in start up......" + e);
        }
    }

    private void initDB() {
        try // display database info in gui
        {
            rs = stmt.executeQuery("SELECT * FROM Customers");
            rs2 = stmt.executeQuery("SELECT * FROM Flights");
            rs.next();
            rs2.next();

            cName = rs.getString("CustName");
            cAddr = rs.getString("CustAddr");
            cType = rs.getString("CustType");
            cNation = rs.getString("Nationality");
            cPhonNo = rs.getString("CustPhoneNo");

            cflightno = rs2.getString("FlightNo");
            cfrom = rs2.getString("From");
            cto = rs2.getString("To");
            cdeparture = rs2.getString("Departure");
            carrival = rs2.getString("ArrivalTime");

            CustomerName.setText(cName);
            Address.setText(cAddr);
            CustomerType.setText(cType);
            PhoneNo.setText(cPhonNo);
            Nationality.setText(cNation);
            current = 1;
            rs.close();

            flightno.setText(cflightno);
            from.setText(cfrom);
            to.setText(cto);
            departure.setText(cdeparture);
            arrivaltime.setText(carrival);
            current = 1;
            rs2.close();
        } catch (Exception e) {
            System.out.println("Error in initialising DB info to GUI" + e);
        }
    }

    public void findCus(String nameToFind) {
        int foundCus = 0;
        boolean found = false;

        try // get name to search database for, use .equals() to match names
        {
            System.out.println("Finding: " + nameToFind);
            rs = stmt.executeQuery("SELECT * FROM Customers");

            while (rs.next() && found == false) {
                foundCus++;
                String curName = rs.getString("CustName");
                curName.trim();

                if (curName.equals(nameToFind)) {
                    found = true;
                } else {
                    Show.setText("Not Found");
                }
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error in finding Customers in database");
        }

        if (found == true) {
            Show.setText("Customer Name : " + cName + "\nCustomer Type : " + cType + "\nAddress : " + cAddr + "\nPhone No : " + cPhonNo + "\nNationality : " + cNation);
        }

    }

    public void findflight(String nameToFind2) {
        int foundflight = 0;
        boolean found2 = false;

        try // get name to search database for, use .equals() to match names
        {
            System.out.println("Finding: " + nameToFind2);
            rs2 = stmt.executeQuery("SELECT * FROM Flights");

            while (rs2.next() && found2 == false) {
                foundflight++;
                String flightNo = rs2.getString("FlightNo");
                flightNo.trim();

                if (flightNo.equals(nameToFind2)) {
                    found2 = true;
                } else {
                    Showflight.setText("Not Found");
                }
            }
            rs2.close();
        } catch (Exception e) {
            System.out.println("Error in finding Flights in database");
        }

        if (found2 == true) {
            Showflight.setText("Flight No : " + cflightno + "\nFrom : " + cfrom + "\nTo : " + cto + "\nDeparture : " + cdeparture + "\nArrival Time : " + carrival);
        }

    }

    public void moveToRow(int index) {
        try {
            rs = stmt.executeQuery("SELECT * FROM Customers");
            rs2 = stmt.executeQuery("SELECT * FROM Flights");

            for (int i = 0; i < index; i++) {
                rs.next(); 
                rs2.next();                // move to specific row in table (at index)
            }
            cName = rs.getString("CustName");
            cAddr = rs.getString("CustAddr");		// get info by type
            cType = rs.getString("CustType");
            cNation = rs.getString("Nationality");
            cPhonNo = rs.getString("CustPhoneNo");
            
            cflightno = rs2.getString("FlightNo");
            cfrom = rs2.getString("From");
            cto = rs2.getString("To");
            cdeparture = rs2.getString("Departure");
            carrival = rs2.getString("ArrivalTime");

            CustomerName.setText(cName);
            Address.setText(cAddr);
            CustomerType.setText(cType);
            PhoneNo.setText(cPhonNo);
            Nationality.setText(cNation);

            flightno.setText(cflightno);
            from.setText(cfrom);
            to.setText(cto);
            departure.setText(cdeparture);
            arrivaltime.setText(carrival);
            current = 1;
            
            current = index;
            rs.close();
            rs2.close();
        } catch (Exception e) {
            System.out.println("Error in moving to row at index specified" + e);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        EnterCustomerName = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        Show = new javax.swing.JTextArea();
        Clear = new javax.swing.JButton();
        Search = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        enterflightno = new javax.swing.JTextField();
        flightsearch = new javax.swing.JButton();
        ClearFlight = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        Showflight = new javax.swing.JTextArea();
        jPanel8 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        CustomerName = new javax.swing.JTextField();
        Address = new javax.swing.JTextField();
        PhoneNo = new javax.swing.JTextField();
        Nationality = new javax.swing.JTextField();
        Save = new javax.swing.JButton();
        CustomerType = new javax.swing.JTextField();
        Next = new javax.swing.JButton();
        Previous = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        flightno = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        from = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        to = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        departure = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        arrivaltime = new javax.swing.JTextField();
        ClearChange = new javax.swing.JButton();
        Delete = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(0, 131, 116));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/my/aerlingus/newpackage/aerlingus.png"))); // NOI18N

        jLabel1.setBackground(new java.awt.Color(0, 131, 116));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/my/aerlingus/newpackage/header.jpg"))); // NOI18N
        jLabel1.setOpaque(true);

        jPanel4.setBackground(new java.awt.Color(0, 131, 116));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 102), 3));

        jLabel13.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Search Information");

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 102), 3));

        jLabel12.setText("Enter Customer Name:");

        EnterCustomerName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EnterCustomerNameActionPerformed(evt);
            }
        });

        Show.setColumns(20);
        Show.setRows(5);
        jScrollPane1.setViewportView(Show);

        Clear.setText("Clear");
        Clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearActionPerformed(evt);
            }
        });

        Search.setText("Search");
        Search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(Clear, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(18, 18, 18)
                                .addComponent(EnterCustomerName, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Search)
                        .addGap(0, 11, Short.MAX_VALUE))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(EnterCustomerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Search))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(Clear)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 102), 3));

        jLabel7.setText("Enter Flight No:");

        flightsearch.setText("Search");
        flightsearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                flightsearchActionPerformed(evt);
            }
        });

        ClearFlight.setText("Clear");
        ClearFlight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearFlightActionPerformed(evt);
            }
        });

        Showflight.setColumns(20);
        Showflight.setRows(5);
        jScrollPane2.setViewportView(Showflight);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(ClearFlight, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(enterflightno)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(flightsearch)))
                .addGap(14, 14, 14))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(enterflightno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(flightsearch))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(ClearFlight)
                .addGap(40, 40, 40))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(283, 283, 283)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 193, Short.MAX_VALUE))
                .addGap(0, 20, Short.MAX_VALUE))
        );

        jPanel8.setBackground(new java.awt.Color(0, 131, 116));
        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 102), 3));

        jLabel14.setBackground(new java.awt.Color(255, 255, 255));
        jLabel14.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Change Information");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 102), 3));
        jPanel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel2.setText("Customer Type:");

        jLabel3.setText("Customer Name:");

        jLabel4.setText("Address:");

        jLabel5.setText("PhoneNo:");

        jLabel6.setText("Nationality:");

        CustomerName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CustomerNameActionPerformed(evt);
            }
        });

        Address.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddressActionPerformed(evt);
            }
        });

        Nationality.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NationalityActionPerformed(evt);
            }
        });

        Save.setText("Save");
        Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveActionPerformed(evt);
            }
        });

        CustomerType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CustomerTypeActionPerformed(evt);
            }
        });

        Next.setText("Next");
        Next.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NextActionPerformed(evt);
            }
        });

        Previous.setText("Previous");
        Previous.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PreviousActionPerformed(evt);
            }
        });

        jLabel15.setText("Flight No:");

        jLabel17.setText("From:");

        jLabel18.setText("To:");

        jLabel19.setText("Departure:");

        jLabel20.setText("Arrival Time:");

        ClearChange.setText("Clear");
        ClearChange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearChangeActionPerformed(evt);
            }
        });

        Delete.setText("Delete");
        Delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(PhoneNo, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                            .addComponent(Nationality)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(CustomerType, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CustomerName, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Address, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(departure, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                        .addComponent(arrivaltime, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(flightno, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(from, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(to, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(46, 46, 46))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Previous, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Next, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Save, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Delete)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ClearChange, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(98, 98, 98))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel2, jLabel3, jLabel4, jLabel5, jLabel6});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {arrivaltime, departure, flightno, from, to});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {ClearChange, Delete, Next, Previous, Save});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(CustomerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(flightno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(CustomerType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(from, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(Address, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(to, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(departure, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(PhoneNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel19)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(Nationality, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(arrivaltime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Save)
                    .addComponent(Next)
                    .addComponent(Previous)
                    .addComponent(ClearChange)
                    .addComponent(Delete))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel2, jLabel3, jLabel4, jLabel5, jLabel6});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {arrivaltime, departure, flightno, from, to});

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(283, 283, 283)
                        .addComponent(jLabel14))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jButton1.setText("API");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(87, 87, 87)
                        .addComponent(jButton1)
                        .addGap(267, 267, 267)
                        .addComponent(jLabel11))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(57, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jButton1)))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 167, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void NationalityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NationalityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NationalityActionPerformed

    private void SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveActionPerformed
        try {
            rs = stmt.executeQuery("SELECT * FROM Customers");
            rs2 = stmt.executeQuery("SELECT * FROM Flights");
            
            String fli = flightno.getText();
            String fro = from.getText();
            String too  = to.getText();
            String dep = departure.getText();
            String arr = arrivaltime.getText();
            String newfli = "INSERT INTO Flights(FlightNo, From, To, Departure ,ArrivalTime)VALUES('" + fli + "', '" + fro + "','" + too + "','" + dep + "','" + arr + "')";
            stmt.executeUpdate(newfli);
            
            System.out.println(fli + " stored in database");
            
            
            String cus = CustomerName.getText();
            String add = Address.getText();
            String pho = PhoneNo.getText();
            String typ = CustomerType.getText();
            String nat = Nationality.getText();
            String newCus = "INSERT INTO Customers(CustName, CustAddr, CustPhoneNo, CustType ,Nationality)VALUES('" + cus + "', '" + add + "','" + pho + "','" + typ + "','" + nat + "')";
            stmt.executeUpdate(newCus);

            System.out.println(cus + " stored in database");
            
            JOptionPane.showMessageDialog(null, "Save succeed");
            
        } catch (Exception e) {
            System.out.println("Error in creating new Customer row in database" + e);
        }

        count++;
    }//GEN-LAST:event_SaveActionPerformed

    private void flightsearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_flightsearchActionPerformed
        try {
            rs2 = stmt.executeQuery("SELECT * FROM Flights");
            cflightno = enterflightno.getText();
            findflight(cflightno);

        } catch (SQLException ex) {
            Logger.getLogger(AerLingusUI.class.getName()).log(Level.SEVERE, null, ex);
        }   // TODO add your handling code here:
    }//GEN-LAST:event_flightsearchActionPerformed

    private void SearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchActionPerformed
        {
            try {
                rs = stmt.executeQuery("SELECT * FROM Customers");

                cName = EnterCustomerName.getText();
                findCus(cName);

            } catch (Exception e) {
                System.out.println("Error in find button");
            }
        }


    }//GEN-LAST:event_SearchActionPerformed

    private void CustomerNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CustomerNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CustomerNameActionPerformed

    private void CustomerTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CustomerTypeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CustomerTypeActionPerformed

    private void AddressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddressActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AddressActionPerformed

    private void NextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NextActionPerformed
        if (current != count) {
            moveToRow(current + 1);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_NextActionPerformed

    private void DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteActionPerformed
        try {
            rs = stmt.executeQuery("SELECT * From Customers");
           
            
            
            cName = CustomerName.getText();
            PreparedStatement pstmt = con.prepareStatement("DELETE FROM Customers WHERE CustName = ?");
            pstmt.setString(1, cName);
            pstmt.executeUpdate();
            System.out.println(cName + " deleted from database");
            count--;
            JOptionPane.showMessageDialog(null, "Deleted Customer " +cName);
        } catch (SQLException ex) {
            Logger.getLogger(AerLingusUI.class.getName()).log(Level.SEVERE, null, ex);
        }

//		try	// get name to delete from database, use preparedstmt with wildcard
//		{
//                      rs = stmt.executeQuery("SELECT * FROM Customers");
//			cName = CustName.getText();
//			
//			PreparedStatement pstmt = con.prepareStatement("DELETE FROM Customers WHERE CustName = ?");
//			pstmt.setString(1,cName);
//			pstmt.executeUpdate();
//
//			System.out.println(cName +" deleted from database");
//
//			count --;
//		}
//		catch(Exception e) {System.out.println("Error in delete button");}
//	}
    }//GEN-LAST:event_DeleteActionPerformed

    private void PreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PreviousActionPerformed
        if (current != count) {
            moveToRow(current - 1);
        }
    }//GEN-LAST:event_PreviousActionPerformed

    private void ClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearActionPerformed
        try {
            rs = stmt.executeQuery("SELECT * FROM Customers");
            {
                Show.setText("");
                EnterCustomerName.setText("");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AerLingusUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_ClearActionPerformed

    private void EnterCustomerNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EnterCustomerNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EnterCustomerNameActionPerformed

    private void ClearFlightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearFlightActionPerformed
        try {
            rs2 = stmt.executeQuery("SELECT * FROM Flights");
            {

                Showflight.setText("");
                enterflightno.setText("");

            }
        } catch (SQLException ex) {
            Logger.getLogger(AerLingusUI.class.getName()).log(Level.SEVERE, null, ex);
        }  // TODO add your handling code here:
    }//GEN-LAST:event_ClearFlightActionPerformed

    private void ClearChangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearChangeActionPerformed
        try {
            rs = stmt.executeQuery("SELECT * FROM Customers");
            {
                CustomerName.setText("");
                Address.setText("");
                CustomerType.setText("");
                PhoneNo.setText("");
                Nationality.setText("");
            }
            
             rs2 = stmt.executeQuery("SELECT * FROM Flights");
            {
                flightno.setText("");
                from.setText("");
                to.setText("");
                departure.setText("");
                arrivaltime.setText("");
            }

        } catch (SQLException ex) {
            Logger.getLogger(AerLingusUI.class.getName()).log(Level.SEVERE, null, ex);
        }// TODO add your handling code here:
    }//GEN-LAST:event_ClearChangeActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
//            String javadoc = "";
//            try{
//            File docfile = new File(javadoc);
//            Desktop.getDesktop().open(docfile);
//            }
//    }catch (Exceprion e){
//    System.out.println("Error Opening File");
//        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AerLingusUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AerLingusUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AerLingusUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AerLingusUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AerLingusUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Address;
    private javax.swing.JButton Clear;
    private javax.swing.JButton ClearChange;
    private javax.swing.JButton ClearFlight;
    private javax.swing.JTextField CustomerName;
    private javax.swing.JTextField CustomerType;
    private javax.swing.JButton Delete;
    private javax.swing.JTextField EnterCustomerName;
    private javax.swing.JTextField Nationality;
    private javax.swing.JButton Next;
    private javax.swing.JTextField PhoneNo;
    private javax.swing.JButton Previous;
    private javax.swing.JButton Save;
    private javax.swing.JButton Search;
    private javax.swing.JTextArea Show;
    private javax.swing.JTextArea Showflight;
    private javax.swing.JTextField arrivaltime;
    private javax.swing.JTextField departure;
    private javax.swing.JTextField enterflightno;
    private javax.swing.JTextField flightno;
    private javax.swing.JButton flightsearch;
    private javax.swing.JTextField from;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField to;
    // End of variables declaration//GEN-END:variables
}
