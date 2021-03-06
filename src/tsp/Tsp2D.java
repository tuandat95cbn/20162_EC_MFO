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

import main.Task;

public class Tsp2D implements Task{
	int n;
	double x[],y[];
	void readData(String fileName){
		try {
			Scanner fileIn = new Scanner( new File(fileName));
			n=fileIn.nextInt();
			x=new double[n];
			y= new double[n];
			
			for(int i=0;i<n;i++){
				x[i]=fileIn.nextDouble();
				y[i]= fileIn.nextDouble() ;
			}
				
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		for(int i=0;i<n;i++){
//			for(int j=0;j<n;j++)
//				//System.out.print(a[i][j]+"    ");
//			//System.out.println();
//		}
			
	}
	
	
	/*ArrayList<Double> encode(ArrayList<Integer> x){
		Random r = new Random();
		Double ts[]=new Double[n];
		for(int i=0;i<n;i++)
			ts[i]=r.nextDouble();
		Arrays.sort(ts);
		ArrayList<Double> res= new ArrayList<Double>();
		for(int i=0;i<n;i++) res.add(0.0);
		for(int i=0; i<n;i++)
			res.set(x.get(i),ts[x.get(i)]);
		return res;
	}*/
	ArrayList<Double> encode(ArrayList<Integer> x){
		Random r = new Random();
		Double ts[]=new Double[n];
		for(int i=0;i<n;i++)
			ts[i]=r.nextDouble();
		Arrays.sort(ts);
		ArrayList<Double> res= new ArrayList<Double>();
		for(int i=0;i<n;i++) res.add(0.0);
		for(int i=0; i<n;i++)
			res.set(x.get(i),ts[i]);
		return res;
	}
	/*public ArrayList<Integer> decode(ArrayList<Double> tx){
		ArrayList<Double> x=new ArrayList<Double>( tx.subList(0, n));
		ArrayList<Integer> lA= new ArrayList<Integer>();
		Double ts[]= new Double[n];
		for(int i=0;i<n;i++){
			ts[i]=x.get(i);
		}
		
		Arrays.sort(ts);
		for(int i=0;i<n;i++)lA.add(0);
		for(int i=0;i<n;i++){
			lA.set(x.indexOf(ts[i]),i);
		}
		return  lA;
	}*/
	public ArrayList<Integer> decode(ArrayList<Double> tx){
		ArrayList<Double> x=new ArrayList<Double>();
		ArrayList<Double> tmp_tx = new ArrayList<Double>();
		for(int i=0; i<tx.size(); i++){
			tmp_tx.add(tx.get(i));
		}
		if(tmp_tx.size()>n){
			double window[] = {Math.random(),Math.random()};
			int stride;
			if((tmp_tx.size()- window.length)%(n-1)==0){
				stride = (tmp_tx.size()- window.length)/(n-1);
			}else{
				stride = (tmp_tx.size()- window.length)/(n-1) +1;
				int zerro_padding = (n-1)*stride + window.length - tx.size();
				for(int i=0; i<zerro_padding; i++){
					tmp_tx.add(0.0);
				}
			}
			for(int i=0; i<tmp_tx.size(); i+=stride){
				double tmp_x =0;
				for(int j=0; j<window.length; j++){
					tmp_x += tmp_tx.get(i+j)*window[j];
				}
				x.add(tmp_x);
			}
			//System.out.println("TSP::decode--length of gen > point of TSP");
		}else{
			x = tmp_tx;
		}
		//System.out.print("TSP::decode--length of gen"+x.size());
		
		ArrayList<Integer> lA= new ArrayList<Integer>();
		Double ts[]= new Double[n];
		for(int i=0;i<n;i++){
			ts[i]=x.get(i);
		}
		
		Arrays.sort(ts);
		for(int i=0;i<n;i++){
			lA.add(x.indexOf(ts[i]));
		}
		
		//System.out.println(name()+"decode--gen length = "+lA.size());
		
		return  lA;
	}
	public double distance2Point(double x1,double y1, double x2, double y2){
		return Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
	}
	//
	@Override
	public Double getValue(ArrayList<Double> ind){
		ArrayList<Integer> xx=decode(ind);
		//System.out.println("Distance x is"+x);
		double c=0;
		for(int i=0;i<xx.size()-1;i++){
			c+=distance2Point(x[xx.get(i)], y[xx.get(i)], x[xx.get(i+1)], y[xx.get(i+1)]);
		}
		
		//System.out.println("Distance is: "+c );
		return c;
	}
	
	public Tsp2D() {
		super();
		readData("tsp52.txt");
	}

	
	public int getN() {
		return n;
	}


	public void setN(int n) {
		this.n = n;
	}





	public double[] getX() {
		return x;
	}


	public void setX(double[] x) {
		this.x = x;
	}


	public double[] getY() {
		return y;
	}


	public void setY(double[] y) {
		this.y = y;
	}

	public String name(){
		return "Tsp2D::";
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Tsp2D tsp2D= new Tsp2D();
		tsp2D.readData("tsp.txt");
		Scanner fileIn;
		ArrayList<Integer> a= new ArrayList<Integer>();
		try {
			fileIn = new Scanner( new File("sol.txt"));
			int m=fileIn.nextInt();
			for(int i=0;i<m;i++)
				a.add(fileIn.nextInt()-1);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//System.out.println(tsp2D.getValue(a));
		//System.out.println(tsp.decode(tsp.encode(l)));
	}


	@Override
	public ArrayList<Double> makeIndivialVail(ArrayList<Double> ind) {
		// TODO Auto-generated method stub
		return ind;
	}


	@Override
	public boolean checkIndivialVail(ArrayList<Double> ind) {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public int getLenGen() {
		// TODO Auto-generated method stub
		return n;
	}




}
