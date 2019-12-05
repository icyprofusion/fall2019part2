// This file gives access to the underlying datafile and stores the data in the Workout class.
// Weiran Su
// CSCE 314

import java.io.File;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Scanner;

public class FileAccess 
{
    // Load primes
    public static boolean loadPrimes(Primes primes, String filename) 
    {
        // reads in primes from file, clears list first
        primes.clearPrimes();
        Scanner sc = null;
        try 
        {
            sc = new Scanner(new File(Config.DATAPATH + filename));
        } catch (Exception e) 
        
        {
            return false;
        }
        //reads while end of file is not reached
        while (sc.hasNext()) 
        {
            BigInteger next = new BigInteger(sc.nextLine());
            primes.addPrime(next);
        }

        return true;
    }

    // Load crosses
    public static boolean loadCrosses(Primes primes, String filename) 
    {
        // read in crosses from file, clears list first
        primes.clearCrosses();
        Scanner sc = null;
        try 
        {
            sc = new Scanner(new File(Config.DATAPATH + filename));
        } catch (Exception e) 
        
        {
            return false;
        }

        while (sc.hasNext()) 
        {
            // splits the input and adds it to prime list
            String[] cross = sc.nextLine().split(",");
            primes.addCross(new Pair<>(new BigInteger(cross[0]), new BigInteger(cross[1])));
        }
        return true;
    }

    // Save primes
    public static boolean savePrimes(Primes primes, String filename) 
    {
        PrintWriter pw;

        try {
            pw = new PrintWriter(new File(Config.DATAPATH + filename));
        } catch (Exception e) 
        
        {
            return false;
        }
        // iterates through primes
        primes.iteratePrimes().forEach(num -> pw.println(num));
        pw.close();
        return true;
    }

    // Save crosses
    public static boolean saveCrosses(Primes primes, String filename) {
        PrintWriter pw;

        try {
            pw = new PrintWriter(new File(Config.DATAPATH + filename));
        } catch (Exception e) 
        
        {
            return false;
        }
        // iterates through crosses
        primes.iterateCrosses().forEach(pair -> pw.println(pair.left() + "," + pair.right()));
        pw.close();
        return true;
    }
}