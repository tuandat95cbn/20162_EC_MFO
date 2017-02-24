package knap;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Knapsack {
	int n,b;
	int w[],c[];
	void readData(String fileName){
		try {
			Scanner fileIn = new Scanner( new File(fileName));
			n=fileIn.nextInt();
			b=fileIn.nextInt();
			w= new int[n];
			c= new int[n];
			for(int i=0;i<n;i++){
				w[i]=fileIn.nextInt();
				c[i]=fileIn.nextInt();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(n+" "+b);
		for(int i=0;i<n;i++)
			System.out.println(w[i]+" "+c[i]);
	}
	ArrayList<Double> encode(ArrayList<Integer> x){
		return null;
	}
	
	ArrayList<Integer> decode(ArrayList<Double> x){
		return null;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Knapsack kp= new Knapsack();
		kp.readData("kp.txt");
	}
}
