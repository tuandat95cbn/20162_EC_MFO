package main;

import java.util.ArrayList;

public class GA {
	int nTask=2;
	Population p= new Population(10,nTask);
	
	
	GA(){
		p.init();
		
	}
	ArrayList<Double> crossOver(ArrayList<Double> a, ArrayList<Double> b){
		return null;
	}
	
	ArrayList<Double> mutation(ArrayList<Double> a){
		return null;
	}
}
