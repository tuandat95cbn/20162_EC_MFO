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
	double cw[];
	int vtcw[];
	void sort(double a[],int na,int vt[]){
		for(int i=0;i<na;i++) vt[i]=i;
		for(int i=0;i<na-1;i++)
			for(int j=i+1;j<na;j++)
				if(a[i]>a[j]){
					double tmp=a[i];
					a[i]=a[j];
					a[j]=tmp;
					int tmp2=vt[i];
					vt[i]=vt[j];
					vt[j]=tmp2;
				}
	}
	void readData(String fileName){
		try {
			Scanner fileIn = new Scanner( new File(fileName));
			n=fileIn.nextInt();
			b=fileIn.nextInt();
			cw= new double[n];
			w= new int[n];
			c= new int[n];
			vtcw= new int[n];
			for(int i=0;i<n;i++){
				w[i]=fileIn.nextInt();
				c[i]=fileIn.nextInt();
				cw[i]=c[i]*1.0/w[i];
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(n+" "+b);
		//for(int i=0;i<n;i++)
			//System.out.println(w[i]+" "+c[i]);
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
	
	public double getWeight(ArrayList<Integer> x){
		double res=0;
		for(int i=0;i<x.size();i++)
			res+=w[i]*x.get(i);
		return res;
	}
	
	public ArrayList<Double> makeIndivialVail(ArrayList<Double> x){
		double wx=getWeight(decode(x));
		int i=0;
		Random r= new Random();
		while(wx>b){
			if(Math.round( x.get(vtcw[i]))==1){
				wx=wx-w[vtcw[i]];
			}
			x.set(vtcw[i], r.nextDouble()*5/10);
			i++;
		}
		return x;
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
		sort(cw, n, vtcw);
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
		
		double a[]={};
		ArrayList<Double> ts= new ArrayList<Double>();
		ts.add(0.7232676594086257);
		ts.add(0.4152143638642146);
		ts.add(0.6159508458051836);
		ts.add(0.9341003134699865);
		ts.add(0.9299141560686104);
		System.out.println(kp.decode(ts));
		System.out.println(kp.getValue(kp.decode(ts))+" "+kp.getWeight(kp.decode(ts)));
		kp.makeIndivialVail(ts);
		System.out.println(ts);
		System.out.println(kp.decode(ts));
		System.out.println(kp.getValue(kp.decode(ts))+" "+kp.getWeight(kp.decode(ts)));
	}
}
