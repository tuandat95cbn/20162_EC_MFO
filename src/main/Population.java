package main;

import java.util.ArrayList;
import java.util.Random;

import knap.Knapsack;
import tsp.Tsp;

public class Population {
	int n;
	int nTask;
	int lenGen;
	ArrayList<Individual> individuals;
	ArrayList<ArrayList<Individual>> rankInTask;
	Tsp tsp= new Tsp();
	Knapsack kp= new Knapsack();
	public Population(int n, int nTask) {
		this.n = n;
		this.nTask = nTask;
		if(tsp.getN()>kp.getN()) lenGen=tsp.getN();
		else lenGen=kp.getN();
		Random r= new Random();
		for(int i=0;i<n;i++){
			ArrayList<Double> g= new ArrayList<Double>();
			for(int j=0;j<lenGen;j++){
				g.add(r.nextDouble());
			}
			ArrayList<Double> fitnessTa= new ArrayList<Double>();
			fitnessTa.add(tsp.getDistance(tsp.decode(g)));
			fitnessTa.add(kp.getValue(kp.decode(g)));
			Individual ind= new Individual(g,fitnessTa);
			individuals.add(ind);
			//here
		}
	}
	void updateRank(Individual ind){
		for(int i=0;i<rankInTask.size();i++){
			ArrayList<Individual> t= rankInTask.get(i);
			boolean xd=true;
			for(int j=0;j<t.size();j++)
				if(t.get(j).getFitnessTask().get(i)<ind.getFitnessTask().get(i)){
					t.add(j, ind);
					xd=false;
					break;
					
				}
			if(xd==true) t.add( ind);
			rankInTask.set(i, t);
		}
		
	}
	void init(){
		
	}
	void selection(){
		
	}
}
