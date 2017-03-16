package main;

import java.util.ArrayList;
import java.util.Random;

import knap.Knapsack;
import tsp.Tsp;
import tsp.Tsp2D;

public class Population {
	int nIndividual; //number of individual
	int nTask; //number of task
	int lenGen; //length of gen per individual
	int minLenGen;
	ArrayList<Individual> individuals= null;
	//ArrayList<ArrayList<Double>> bestFitnessInTask = new ArrayList<ArrayList<Double>>();
	
	ArrayList<Task> tasks;
	public Population(int n, int nTask,ArrayList<Task> tasks) {
		this.nIndividual = n;
		this.nTask = nTask;
		this.tasks=tasks;
		// here
		int max=0;
		int min=Integer.MAX_VALUE;
		for(int i=0;i<tasks.size();i++){
			if(tasks.get(i).getLenGen()>max) max=tasks.get(i).getLenGen();
			if(tasks.get(i).getLenGen()<min) min=tasks.get(i).getLenGen();
		}
		lenGen=max;
		minLenGen=min;
	}
	
	//Step 1-2 in algo
	void init(){
		Random r= new Random();
		individuals= new ArrayList<Individual>();
		//init random individuals
		//System.out.println("Population::init--length of gen = "+lenGen);
		for(int i=0;i<nIndividual;i++){
			ArrayList<Double> g= new ArrayList<Double>();
			for(int j=0;j<lenGen;j++){
				g.add(r.nextDouble());
			}
			if(!checkIndvidualVail(g)) makeIndividualVail(g);
			
			ArrayList<Double> fitnessTa= new ArrayList<Double>();
			for(int j=0;j<tasks.size();j++){
				fitnessTa.add(tasks.get(j).getValue(g));
			}
			
			Individual ind= new Individual(g,fitnessTa);
			individuals.add(ind);
			
		}
		updateRankPopulation();
		
	}
	public boolean checkIndvidualVail(ArrayList<Double> ind){
		for(int i=0;i<tasks.size();i++){
			Task t=tasks.get(i);
			if(!t.checkIndivialVail(ind)) {
				//System.out.println("Population::CheckIndvidualVail--- false");
				return false;
			}
		}
		//System.out.println("Population::CheckIndvidualVail--- true");
		return true;
	}
	public void makeIndividualVail(ArrayList<Double> ind){
		//System.out.println("Population::makeIndividual");
		int i=0;
		int xd=0;
		while(true){
			Task t= tasks.get(i);
			if(!t.checkIndivialVail(ind)){
				//System.out.println("task["+i+"]: false ==> make available");
				xd=0;
				t.makeIndivialVail(ind);
			} else {
				xd++;
			}
			if(xd>=tasks.size()){
				break;
			}
			i=(i+1)%tasks.size();
		}
	}
	public void updateRankPopulation(){
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
			ind.setScalarFitness(1.0/(min_rank));
		}
		
	}
	

	//problem: two child in the same task
	void add(ArrayList<Individual> offsprings){
		for(int i=0;i< offsprings.size();i++){
			individuals.add(offsprings.get(i));
		}
		
		for(int in=0; in<offsprings.size(); in++){
			Individual child = offsprings.get(in);
			int child_task = child.getSkillFactor();
			
			ArrayList<Individual> rankInTask = countRank(child_task);
			int index=-1;
			for(int j=0; j<rankInTask.size(); j++){
				if(rankInTask.get(j).getFitnessTask().get(child_task) > child.getFitnessTask().get(child_task)){
					index = j; 
					break;
				}
			}
			//child.setScalarFitness(1/index);
			if(index >-1)
			for(int j=index; j<rankInTask.size(); j++){
				Individual tmp = rankInTask.get(j);
				ArrayList<Integer> rank = tmp.getFactorial_rank();
				rank.set(child_task, rank.get(child_task)+1);
				tmp.setFactorial_rank(rank);
			} else{
				index=rankInTask.size();
			}
			ArrayList<Integer> facRankInd=new ArrayList<Integer>();
			for(int ii=0;ii<nTask;ii++)
				facRankInd.add(individuals.size()+1);
			facRankInd.set(child_task, index+1);
			child.setFactorial_rank(facRankInd);
			offsprings.set(in, child);
		}	
		
		for(int i=0;i< offsprings.size();i++){
			Individual ind= offsprings.get(i);
			ind.setScalarFitness(1/(ind.getMinFactorialRank()));
		}
	}
	
	public ArrayList<Individual> countRank(int task){
		ArrayList<Individual> lstIndividualInTask = new ArrayList<Individual>();
		
		for(int i_in=0; i_in < individuals.size(); i_in++){
			Individual ind = individuals.get(i_in);
			//for(int i=0; i<nTask; i++){
				boolean check = true;
				for(int j=0; j<lstIndividualInTask.size(); j++){
					if(lstIndividualInTask.get(j).getFitnessTask().get(task) 
							> ind.getFitnessTask().get(task)){
						lstIndividualInTask.add(j,ind);
						check = false;
						break;
					}
				}
				if(check==true){
					lstIndividualInTask.add(ind);
				}
			//}
		}
		
		return lstIndividualInTask;
	}
	
	public Individual getIndividualBestOfTask(int task){
		Individual best= null;
		for(int i=0;i<individuals.size();i++)
			if(individuals.get(i).factorial_rank.get(task)==1) best=individuals.get(i); 
		return best;
	}
	
	public String name(){
		return "Population::";
	}
}
