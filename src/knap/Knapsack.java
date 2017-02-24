package knap;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
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
		Random r= new Random();
		ArrayList<Double> kp= new ArrayList<Double>();
		for(int i=0;i<n;i++){
			if (x.get(i)==0)
			kp.add(r.nextDouble()*5/10);
			else kp.add(0.5+r.nextDouble()*5/10);
		}
		return kp;
	}
	//
	public double getValue(ArrayList<Integer> x){
		double res=0;
		for(int i=0;i<x.size();i++)
			res-=c[i]*x.get(i);
		return res;
	}
	public ArrayList<Integer> decode(ArrayList<Double> x){
		ArrayList<Integer> kp= new ArrayList<Integer>();
		for(int i=0;i<n;i++)
			kp.add((int) Math.round(x.get(i)));
		return kp;
	}
	
	public Knapsack() {
		super();
		readData("kp.txt");
	}
	
	public int getN() {
		return n;
	}
	public void setN(int n) {
		this.n = n;
	}
	public int getB() {
		return b;
	}
	public void setB(int b) {
		this.b = b;
	}
	public int[] getW() {
		return w;
	}
	public void setW(int[] w) {
		this.w = w;
	}
	public int[] getC() {
		return c;
	}
	public void setC(int[] c) {
		this.c = c;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Knapsack kp= new Knapsack();
		kp.readData("kp.txt");
		ArrayList<Integer> ts= new ArrayList<Integer>();
		for(int i=0;i<kp.n;i++){
			ts.add(1);
		}
		System.out.println(kp.encode(ts));
		System.out.println(kp.decode(kp.encode(ts)));
	}
}
