package main;

import java.util.ArrayList;
import java.util.Random;

import knap.Knapsack;
import tsp.Tsp;

public class Population {
	int n;
	int nTask;
	int lenGen;
	ArrayList<Individual> individuals= new ArrayList<Individual>();
	ArrayList<ArrayList<Individual>> rankInTask= new ArrayList<ArrayList<Individual>>();
	Tsp tsp;
	Knapsack kp; 
	public Population(int n, int nTask,Tsp tsp,Knapsack kp) {
		this.n = n;
		this.nTask = nTask;
		this.tsp=tsp;
		this.kp=kp;
		for(int i=0;i<nTask;i++){
			ArrayList<Individual> aI= new ArrayList<Individual>();
			rankInTask.add(aI);
		}
		if(tsp.getN()>kp.getN()) lenGen=tsp.getN();
		else lenGen=kp.getN();
		Random r= new Random();
		for(int i=0;i<n;i++){
			ArrayList<Double> g= new ArrayList<Double>();
			for(int j=0;j<lenGen;j++){
				g.add(r.nextDouble());
			}
			ArrayList<Integer> kpd=kp.decode(g);
			if(kp.getWeight(kpd)>kp.getB()) kp.makeIndivialVail(g);
			ArrayList<Double> fitnessTa= new ArrayList<Double>();
			fitnessTa.add(tsp.getDistance(tsp.decode(g)));
			fitnessTa.add(kp.getValue(kp.decode(g)));
			System.out.println("g is: "+fitnessTa);
			Individual ind= new Individual(g,fitnessTa);
			individuals.add(ind);
			updateRank(ind);
		}
	}
	void updateRank(Individual ind){
		for(int i=0;i<rankInTask.size();i++){
			ArrayList<Individual> t= rankInTask.get(i);
			boolean xd=true;
			for(int j=0;j<t.size();j++)
				if(t.get(j).getFitnessTask().get(i)>ind.getFitnessTask().get(i)){
					t.add(j, ind);
					xd=false;
					break;
					
				}
			if(xd==true) t.add( ind);
			rankInTask.set(i, t);
		}
		
	}
	
	void add(Individual ind){
		updateRank(ind);
		individuals.add(ind);
	}
	void init(){
		
	}
	void selection(){
		
	}
}
