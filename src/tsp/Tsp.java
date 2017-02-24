package tsp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Tsp {
	int n,m;
	int a[][];
	void readData(String fileName){
		try {
			Scanner fileIn = new Scanner( new File(fileName));
			n=fileIn.nextInt();
			m=fileIn.nextInt();
			a=new int[n][n];
			for(int i=0;i<n;i++)
				for(int j=0;j<n;j++){
					a[i][j]=Integer.MIN_VALUE;
				}
			
			for(int i=0;i<m;i++){
				int u=fileIn.nextInt();
				int v= fileIn.nextInt();
				a[u][v]=fileIn.nextInt();
				a[v][u]=a[u][v];
			}
				
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++)
				System.out.print(a[i][j]+"    ");
			System.out.println();
		}
			
	}
	
	
	ArrayList<Double> encode(ArrayList<Integer> x){
		Random r = new Random();
		Double ts[]=new Double[n];
		for(int i=0;i<n;i++)
			ts[i]=r.nextDouble();
		Arrays.sort(ts);
		ArrayList<Double> res= new ArrayList<Double>();
		for(int i=0; i<n;i++)
			res.add(ts[x.get(i)]);
		return res;
	}
	
	ArrayList<Integer> decode(ArrayList<Double> x){
		ArrayList<Integer> lA= new ArrayList<Integer>();
		Double ts[]= new Double[n];
		for(int i=0;i<n;i++)
			ts[i]=x.get(i);
		Arrays.sort(ts);
		for(int i=0;i<n;i++){
			lA.add(x.indexOf(ts[i]));
		}
		return  lA;
	}
	//
	double getDistance(){
		return 0;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Tsp tsp= new Tsp();
		tsp.readData("tsp.txt");
		ArrayList<Integer> l= new ArrayList<Integer>();
		for(int i=0;i<tsp.n;i++){
			l.add(tsp.n-i-1);
		}
		System.out.println(tsp.encode(l));
		System.out.println(tsp.decode(tsp.encode(l)));
	}
}
