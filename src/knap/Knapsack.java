package knap;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import main.Task;

public class Knapsack implements Task {
	int n,b;
	int w[],c[];
	double cw[];
	int vtcw[];
	double window[] = {Math.random(),Math.random(),Math.random(),Math.random()};
	//System.out.println("Knapsack::decode"+"stride window=["+window[0]+", "+window[1]+"]");
	int stride;
	double window_max(){
		double max = -1;
		for(int i=0; i<window.length; i++){
			if(window[i]>max){
				max = window[i];
			}
		}
		return max;
	}
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
	@Override
	public Double getValue(ArrayList<Double> ind){
		ArrayList<Integer> x=decode(ind);
		double res=0;
		for(int i=0;i<x.size();i++)
			res-=c[i]*x.get(i);
		return res;
	}
	@Override
	public boolean checkIndivialVail(ArrayList<Double> ind){
		ArrayList<Integer> x=decode(ind);
		double res=0;
		for(int i=0;i<x.size();i++)
			res+=w[i]*x.get(i);
		boolean re_v = res<=b;
		//System.out.println("Knapsack check: "+re_v);
		return re_v;
	}
	public Double getWeight(ArrayList<Double> ind){
		ArrayList<Integer> x=decode(ind);
		double res=0;
		for(int i=0;i<x.size();i++)
			res+=w[i]*x.get(i);
		return res;
	}
	public ArrayList<Double> makeIndivialVail(ArrayList<Double> x){
//		System.out.println(name()+"makeIndivialVail--x.size="+x.size());
//		System.out.println(name()+"makeIndivialVail--input x="+x.toString());
		ArrayList<Integer> x_decode = decode(x);
		Double wx=getWeight(x);
//		System.out.println(name()+"makeIndivialVail--wx="+wx+"  b="+b);
		int i=0;
		Random r= new Random();
		while(wx>b){
//			System.out.println(name()+"makeIndivialVail--vtcw["+i+"]="+vtcw[i] + "  wx="+wx);
			if(x_decode.get(vtcw[i])==1){
				wx=wx-w[vtcw[i]];
				for(int k = 0; k<window.length; k++){
					x.set(vtcw[i]*stride+k, 0.5/(window.length*window_max()));
				}
			}
			i++;
		}
//		System.out.println(name()+"makeIndivialVail-- return x="+x.toString());
		return x;
	}
	public ArrayList<Integer> decode(ArrayList<Double> x){
		//copy x to process in function
		ArrayList<Double> tmp_x = new ArrayList<Double>();
		for(int i=0; i<x.size(); i++){
			tmp_x.add(x.get(i));
		}
		
		ArrayList<Integer> kp= new ArrayList<Integer>();
		if(tmp_x.size()>n){
			if((tmp_x.size()- window.length)%(n-1)==0){
				stride = (tmp_x.size()- window.length)/(n-1);
			}else{
				stride = (tmp_x.size()- window.length)/(n-1) +1;
				int zerro_padding = (n-1)*stride + window.length - x.size();
				//System.out.println(name()+"decode--zerro_padding = "+zerro_padding);
				for(int i=0; i<zerro_padding; i++){
					tmp_x.add(0.0);
				}
			}
			//System.out.println(name()+"decode--stride = "+stride+"  tmp_x.size = "+tmp_x.size());
			
			for(int i=0; i<tmp_x.size(); i+=stride){
				double tmp =0;
				for(int j=0; j<window.length; j++){
					tmp += tmp_x.get(i+j)*window[j];
				}
				if(tmp>1) tmp/=10;
				//System.out.println("element "+i+" of gen: "+tmp);
				kp.add((int)Math.round(tmp));
			}
		}else{
			for(int i=0;i<n;i++)
				kp.add((int) Math.round(tmp_x.get(i)));
		}
		
		//System.out.println(name()+"decode::gen size = "+kp.size());
		//System.out.println(name()+"decode::gen kp=[");
		
//		for(int i=0; i<kp.size(); i++){
//			System.out.print(kp.get(i)+", ");
//		}
//		System.out.println();
		return kp;
	}
	
	public Knapsack() {
		super();
		readData("test200_31741.txt");
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
	
	public String name(){
		return "Knapsack::";
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
		kp.makeIndivialVail(ts);
		System.out.println(ts);
		System.out.println(kp.decode(ts));
	}
	@Override
	public int getLenGen() {
		// TODO Auto-generated method stub
		return n;
	}
	
	
}
