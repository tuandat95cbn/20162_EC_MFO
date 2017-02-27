package main;

import java.util.ArrayList;
import java.util.Random;

import knap.Knapsack;
import tsp.Tsp;

public class Population {
	int nIndividual; //number of individual
	int nTask; //number of task
	int lenGen; //length of gen per individual
	ArrayList<Individual> individuals= new ArrayList<Individual>();
	//ArrayList<ArrayList<Double>> bestFitnessInTask = new ArrayList<ArrayList<Double>>();
	Tsp tsp;
	Knapsack kp; 
	public Population(int n, int nTask,Tsp tsp,Knapsack kp) {
		this.nIndividual = n;
		this.nTask = nTask;
		this.tsp=tsp;
		this.kp=kp;
		if(tsp.getN()>kp.getN()) lenGen=tsp.getN();
		else lenGen=kp.getN();
	}
	
	//Step 1-2 in algo
	void init(){
		Random r= new Random();
		
		//init random individuals
		for(int i=0;i<nIndividual;i++){
			ArrayList<Double> g= new ArrayList<Double>();
			for(int j=0;j<lenGen;j++){
				g.add(r.nextDouble());
			}
			ArrayList<Integer> kpd=kp.decode(g);
			if(kp.getWeight(kpd)>kp.getB()) kp.makeIndivialVail(g);
			ArrayList<Double> fitnessTa= new ArrayList<Double>();
			fitnessTa.add(tsp.getDistance(tsp.decode(g)));
			fitnessTa.add(kp.getValue(kp.decode(g)));
			Individual ind= new Individual(g,fitnessTa);
			individuals.add(ind);
			//updateRank(ind);
		}
		
		//compute rank, skillfactor, scalar fitness
		ArrayList<ArrayList<Individual>> rankInTask = new ArrayList<ArrayList<Individual>>();
		for(int i=0; i<nTask; i++){
			ArrayList<Individual> lstIndividualInTask = new ArrayList<Individual>();
			rankInTask.add(lstIndividualInTask);
		}
		for(int i_in=0; i_in < nIndividual; i_in++){
			Individual ind = individuals.get(i_in);
			for(int i=0; i<nTask; i++){
				ArrayList<Individual> lstIndividualInTask = rankInTask.get(i);
				boolean check = true;
				for(int j=0; j<lstIndividualInTask.size(); j++){
					if(lstIndividualInTask.get(j).getFitnessTask().get(i) > ind.getFitnessTask().get(i)){
						lstIndividualInTask.add(j,ind);
						check = false;
						break;
					}
				}
				if(check==true){
					lstIndividualInTask.add(ind);
				}
				rankInTask.set(i, lstIndividualInTask);
			}
		}
		for(int i=0; i<nIndividual; i++){
			Individual ind = individuals.get(i);
			ArrayList<Integer> factorial_rank = new ArrayList<Integer>();
			int min_rank = nIndividual+2;
			int task_rank_min = -1;
			for(int j=0; j<nTask; j++){
				int rankj = rankInTask.get(j).indexOf(ind)+1;
				factorial_rank.add(rankj);
				if(rankj < min_rank){
					min_rank = rankj;
					task_rank_min = j;
				}
			}
			ind.setFactorial_rank(factorial_rank);
			ind.setSkillFactor(task_rank_min);
			ind.setScalarFitness(1.0/min_rank);
		}
		
	}
		
	void add(Individual ind){
		individuals.add(ind);
	}
	
	void updateRankInTask(int task){
		
	}
}
