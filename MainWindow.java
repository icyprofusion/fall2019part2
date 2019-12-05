// Weiran Su
// CSCE 314
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;

public class MainWindow extends JFrame 
{
    private static final long serialVersionUID = -3880026026104218593L;
    private Primes m_Primes;
    private JTextField tfPrimeFileName;
    private JTextField tfCrossFileName;
    private JLabel lblPrimeCount;
    private JLabel lblCrossCount;
    private JLabel lblLargestPrime;
    private JLabel lblLargestCross;
    private JLabel lblStatus;

 
    public MainWindow(String name, Primes p) 
    {
        this.m_Primes = p;

        JFrame main = new JFrame(name);
        GridBagLayout layout = new GridBagLayout();
        main.getContentPane().setBackground(new Color(52, 0, 0));
        main.getContentPane().setLayout(layout);

        GridBagConstraints gbcFrame = new GridBagConstraints();
        gbcFrame.fill = GridBagConstraints.HORIZONTAL;
        gbcFrame.anchor = GridBagConstraints.WEST;
        gbcFrame.ipady = 10;
        gbcFrame.weightx = .5;

        JPanel primes = new JPanel();
        JPanel hexes = new JPanel();
        primes.setLayout(new GridBagLayout());
        hexes.setLayout(new GridBagLayout());
        primeFileFrame(primes);
        crossFileFrame(hexes);

        // Generate primes/crosses code

        GridBagConstraints gbcPanel = new GridBagConstraints();
        gbcPanel.fill = GridBagConstraints.NONE;
        gbcPanel.anchor = GridBagConstraints.CENTER;
        gbcPanel.insets = new Insets(1, 0, 2, 0);
        gbcPanel.gridy = 0;
        gbcPanel.weightx = .5;
        gbcPanel.gridheight = 2;

        JPanel genPanel = new JPanel();
        genPanel.setLayout(new GridBagLayout());
        // buttons for generate crosses and primes
        JButton genPrimes = new JButton("Generate Primes");
        genPrimes.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                popupGeneratePrimes();
            }
        });
        JButton genCrosses = new JButton("Generate Crosses");
        genCrosses.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                m_Primes.generateTwinPrimes();
                m_Primes.generateHexPrimes();
                Pair<Integer> cross = m_Primes.sizeofLastCross();
                lblLargestCross.setText("The largest cross has " + cross.left() + " and " + cross.right() + " digits");
                updateStats();
            }
        });
        gbcPanel.gridx = 0;
        genPanel.add(genPrimes, gbcPanel);
        gbcPanel.gridx = 2;
        genPanel.add(genCrosses, gbcPanel);

        // prints the largest prime and cross
        lblLargestPrime = new JLabel("The largest prime has 0 digits");
        lblLargestCross = new JLabel("The largest cross has 0 and 0 digits");
        lblLargestPrime.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblLargestCross.setFont(new Font("Tahoma", Font.BOLD, 12));
        gbcPanel.gridx = 1;
        gbcPanel.gridheight = 1;
        genPanel.add(lblLargestPrime, gbcPanel);
        gbcPanel.gridy = 1;
        genPanel.add(lblLargestCross, gbcPanel);

        // Status
        GridBagConstraints gbcStatus = new GridBagConstraints();
        gbcStatus.anchor = GridBagConstraints.WEST;
        gbcStatus.weightx = .5;
        gbcStatus.insets = new Insets(0, 2, 0, 0);

        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new GridBagLayout());
        lblStatus = new JLabel("Status: Bored.");
        lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 12));
        statusPanel.add(lblStatus, gbcStatus);

        main.add(primes, gbcFrame);
        gbcFrame.gridy = 1;
        main.add(hexes, gbcFrame);
        gbcFrame.gridy = 2;
        main.add(genPanel, gbcFrame);
        gbcFrame.gridy = 3;
        gbcFrame.insets = new Insets(0, 0, 0, 0);
        main.add(statusPanel, gbcFrame);
        main.setSize(1000, 400);
        main.pack();
        main.setVisible(true);
    }

    // Prime file panel
    private void primeFileFrame(JPanel panel) 
    {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = .5;
        gbc.insets = new Insets(0, 2, 0, 0);
        // displays file name
        lblPrimeCount = new JLabel("0", JLabel.CENTER);
        lblPrimeCount.setFont(new Font("Tahoma", Font.BOLD, 12));
        tfPrimeFileName = new JTextField(Config.PRIMEFILENAME);
        tfPrimeFileName.setColumns(50);
        lblPrimeCount.setLabelFor(tfPrimeFileName);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(tfPrimeFileName, gbc);
        gbc.gridwidth = 1;
        gbc.gridx = 2;
        gbc.gridy = 0;
        panel.add(lblPrimeCount, gbc);
        // Creates Primes File label
        JLabel primeTitle = new JLabel("Primes File", JLabel.LEFT);
        primeTitle.setFont(new Font("Tahoma", Font.PLAIN, 24));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(primeTitle, gbc);
        // Load button created
        JButton primeLoad = new JButton("Load");
        // if load button for primes is clicked
        primeLoad.addActionListener(new ActionListener() 
        {
            // is primes loaded?
            public void actionPerformed(ActionEvent e) 
            {
                if (FileAccess.loadPrimes(m_Primes, tfPrimeFileName.getText()))
                    lblStatus.setText("Status: Loaded primes");
                else
                    lblStatus.setText("Status: Failed to load primes");
                updateStats();
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(primeLoad, gbc);
        // creates save button
        JButton primeSave = new JButton("Save");
        // if save button for primes is clicked
        primeSave.addActionListener(new ActionListener() 
        {
            // is primes saved?
            public void actionPerformed(ActionEvent e) 
            {
                if (FileAccess.savePrimes(m_Primes, tfPrimeFileName.getText()))
                    lblStatus.setText("Status: Saved primes");
                else
                    lblStatus.setText("Status: Failed to save primes");
                updateStats();
            }
        });
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(primeSave, gbc);
    }

    // Cross file panel
    private void crossFileFrame(JPanel panel) 
    {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = .5;
        gbc.insets = new Insets(0, 2, 0, 0);
        // displays file name
        lblCrossCount = new JLabel("0", JLabel.CENTER);
        lblCrossCount.setFont(new Font("Tahoma", Font.BOLD, 12));
        tfCrossFileName = new JTextField(Config.CROSSFILENAME);
        tfCrossFileName.setColumns(50);
        lblCrossCount.setLabelFor(tfCrossFileName);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(tfCrossFileName, gbc);
        gbc.gridwidth = 1;
        gbc.gridx = 2;
        gbc.gridy = 0;
        panel.add(lblCrossCount, gbc);
        // displays CSCE 314 Part 2
        JLabel crossTitle = new JLabel("CSCE 314 Part 2", JLabel.LEFT);
        crossTitle.setFont(new Font("Tahoma", Font.PLAIN, 24));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(crossTitle, gbc);
        // Load button created
        JButton primeLoad = new JButton("Load");
        // if load button for crosses is clicked
        primeLoad.addActionListener(new ActionListener() 
        {
            // did crosses load?
            public void actionPerformed(ActionEvent e) 
            {
                if (FileAccess.loadCrosses(m_Primes, tfCrossFileName.getText()))
                    lblStatus.setText("Status: Loaded crosses");
                else
                    lblStatus.setText("Status: Failed to load crosses");
                updateStats();
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(primeLoad, gbc);
        // Creates save button
        JButton primeSave = new JButton("Save");
        // if save button is clicked
        primeSave.addActionListener(new ActionListener() 
        {   
            // is crosses saved?
            public void actionPerformed(ActionEvent e) 
            {
                if (FileAccess.saveCrosses(m_Primes, tfCrossFileName.getText()))
                    lblStatus.setText("Status: Saved crosses");
                else
                    lblStatus.setText("Status: Failed to save crosses");
                updateStats();
            }
        });
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(primeSave, gbc);
    }

    // opens up new pop up to generate primes
    protected void popupGeneratePrimes() 
    {
        JDialog dPrimes = new JDialog(this, "Prime Number Generation");
        GridBagLayout gridLayout = new GridBagLayout();
        dPrimes.getContentPane().setBackground(new Color(52, 0, 0));
        dPrimes.getContentPane().setLayout(gridLayout);

        GridBagConstraints gbcDialog = new GridBagConstraints();
        gbcDialog.fill = GridBagConstraints.HORIZONTAL;
        gbcDialog.anchor = GridBagConstraints.WEST;
        gbcDialog.ipady = 10;
        gbcDialog.weightx = .5;
        gbcDialog.gridx = 0;
        gbcDialog.gridy = 0;

        GridBagConstraints gbcPanel = new GridBagConstraints();
        gbcPanel.anchor = GridBagConstraints.WEST;
        gbcPanel.weightx = .5;
        gbcPanel.gridx = 0;
        gbcPanel.gridy = 0;

        JPanel pnlGenerate = new JPanel();
        pnlGenerate.setLayout(new GridBagLayout());
        
        JLabel lblCount = new JLabel("Number of Primes to Generate");
        lblCount.setFont(new Font("Tahoma", Font.PLAIN, 12));
        pnlGenerate.add(lblCount, gbcPanel);
        // user input for number of primes to generate
        JTextField tfCount = new JTextField();
        lblCount.setLabelFor(tfCount);
        tfCount.setColumns(30);
        gbcPanel.gridx = 1;
        pnlGenerate.add(tfCount, gbcPanel);

        JLabel lblStart = new JLabel("Starting Number (does not have to be prime)");
        lblStart.setFont(new Font("Tahoma", Font.PLAIN, 12));
        gbcPanel.gridx = 0;
        gbcPanel.gridy = 1;
        pnlGenerate.add(lblStart, gbcPanel);

        JTextField tfStart = new JTextField();
        lblStart.setLabelFor(tfStart);
        tfStart.setColumns(30);
        gbcPanel.gridx = 1;
        pnlGenerate.add(tfStart, gbcPanel);

        dPrimes.add(pnlGenerate, gbcDialog);

        JPanel pnlButtons = new JPanel();
        pnlButtons.setLayout(new GridBagLayout());
        // button for generating primes
        JButton btnGeneratePrimes = new JButton("Generate Primes");
        // if generate primes button is pushed
        btnGeneratePrimes.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                try 
                {
                    BigInteger start = new BigInteger(tfStart.getText());
                    int count = Integer.parseInt(tfCount.getText());
                    m_Primes.generatePrimes(start, count);
                    m_Primes.generateTwinPrimes();
                    lblStatus.setText("Status: Excited. Primes have been generated.");
                    lblLargestPrime.setText("The largest prime has " + m_Primes.sizeofLastPrime() + " digits");
                    updateStats();
                    dPrimes.dispose();
                } catch (NumberFormatException ex) 
                
                {
                    lblStatus.setText("You failed to type in an integer successfully. You are terrible at math. Shame.");
                    dPrimes.dispose();
                }

            }
        });
        gbcPanel.gridx = 0;
        gbcPanel.gridy = 0;
        pnlButtons.add(btnGeneratePrimes, gbcPanel);

        JButton btnCancel = new JButton("Cancel Generation");
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dPrimes.dispose();
            }
        });
        gbcPanel.anchor = GridBagConstraints.EAST;
        gbcPanel.gridx = 1;
        pnlButtons.add(btnCancel, gbcPanel);

        gbcDialog.gridy = 1;
        dPrimes.add(pnlButtons, gbcDialog);

        JPanel pnlStatus = new JPanel();
        pnlStatus.setLayout(new GridBagLayout());

        gbcPanel.anchor = GridBagConstraints.SOUTHWEST;
        gbcPanel.weightx = .5;
        gbcPanel.insets = new Insets(1, 2, 0, 0);
        gbcPanel.gridx = 0;
        gbcPanel.gridy = 1;

        JLabel lblNotice = new JLabel("Warning: This application is single threaded, and will freeze while generating primes.");
        lblNotice.setFont(new Font("Tahoma", Font.PLAIN, 12));
        pnlStatus.add(lblNotice, gbcPanel);

        gbcDialog.gridy = 2;
        dPrimes.add(pnlStatus, gbcDialog);

        dPrimes.setSize(200, 200);
        dPrimes.pack(); // Knowing what this is and why it is needed is important. You should read the documentation on this function!
        dPrimes.setVisible(true);
    }

    // This function updates all the GUI statistics. (# of primes, # of crosses, etc)
    private void updateStats() 
    {
        lblPrimeCount.setText("" + m_Primes.primeCount());
        lblCrossCount.setText("" + m_Primes.crossesCount());
    }
}
