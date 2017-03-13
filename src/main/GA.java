package main;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import knap.Knapsack;
import tsp.Tsp;
import tsp.Tsp2D;

public class GA {
	int nTask=2;
	
	Population p;
	int timeResetPopulation=0;
	double pOfMutaion=0;
	ArrayList<Task> tasks= new ArrayList<Task>();
	public static final double LIMIT =10000000000.0;
	
	GA(int numOfInd,double pOfMutaion,int timeResetPopulation){
		Task tsp= new Tsp2D();
		Task kp= new Knapsack();
		tasks.add(tsp);
		tasks.add(kp);
		this.timeResetPopulation=timeResetPopulation;
		this.pOfMutaion=pOfMutaion;
		p= new Population(numOfInd,nTask,tasks);;
		//System.out.println("rank in task:"+p.rankInTask);
		//process(2, 3);
	}
	void process(int inter,int nN){
		ArrayList<Individual> bestSolution= new ArrayList<Individual>();
		Random r= new Random();
		p.init();
		for(int i=0;i<nTask;i++)
			bestSolution.add(p.individuals.get(i));
		int changebest=0;
		for(int i=0;i<inter;i++){
			System.out.println("it "+i);
			
			for(int ii=0;ii<nTask;ii++){
				Individual ind=p.getIndividualBestOfTask(ii);
				if(bestSolution.get(ii).fitnessTask.get(ii)>ind.getFitnessTask().get(ii)) {
					changebest=0;
					bestSolution.set(ii, ind);
				}
				System.out.println("The best of task "+ii+" : "+ind.getFitnessTask());
			}
			System.out.println("Best gobal: ");
			for(int ii=0;ii<tasks.size();ii++)
				System.out.println(bestSolution.get(ii).getFitnessTask());
			System.out.println("********************");
			changebest++;
			if(changebest>=timeResetPopulation){
				p.init();
				changebest=0;
				System.out.println("Change best:  ");
			}
			ArrayList<Individual> individuals = p.individuals;
			ArrayList<Individual> childrens = new ArrayList<Individual>();;
			for(int j=0;j<nN;j++){//
				Individual a=individuals.get(r.nextInt(individuals.size()));
				Individual b=individuals.get(r.nextInt(individuals.size()));
				while(a==b) b=individuals.get(r.nextInt(individuals.size()));
				int ta=a.getSkillFactor();
				int tb=b.getSkillFactor();
				double t= r.nextDouble();
				
				
				if((ta==tb) || (t>pOfMutaion)){
					childrens.addAll(crossOver(a, b));
				} else {
					Individual ia = mutation(a);
					Individual ib = mutation(b);
					childrens.add(ia);
					childrens.add(ib);
				}
			}
			p.add(childrens);
			selection();
			reComputeFitnessTaskForChild(childrens);
			p.updateRankPopulation();
			
		}
		System.out.println("THE FINAL SOLUTION IN "+ inter+ " LOOP");
		for(int i=0;i<bestSolution.size();i++){
			System.out.println(bestSolution.get(i));
		}
	}
	public void reComputeFitnessTaskForChild(ArrayList<Individual> chids){
		for(int i=0;i<chids.size();i++){
			ArrayList<Double> fT= chids.get(i).getFitnessTask();
			for(int j=0;j<tasks.size();j++)
				if(fT.get(j)==LIMIT){
				Task t=tasks.get(j);
				fT.set(j, t.getValue(chids.get(i).gen));
			}
		}
	}
	
	ArrayList<Individual> crossOver(Individual a, Individual b){
		ArrayList<Individual> childrens = new ArrayList<Individual>();
		ArrayList<Integer> fR= new ArrayList<Integer>();
		for(int i=0;i<nTask;i++) fR.add(p.individuals.size()+1);
		Random r= new Random();
		
		//generate gen for childrens
		int t=r.nextInt(a.getGen().size()-1);
		ArrayList<Double> cb= new ArrayList<Double>();
		ArrayList<Double> ca= new ArrayList<Double>();
		for(int i=0;i<t;i++){
			ca.add(a.getGen().get(i));
			cb.add(b.getGen().get(i));
		}
		for(int i=t;i<a.getGen().size();i++){
			ca.add(b.getGen().get(i));
			cb.add(a.getGen().get(i));
		}
		//make attribute for childrens
		if(p.checkIndvidualVail(ca)) 
			p.makeIndividualVail(ca);
		Individual inda= new Individual(ca, null);
		//algorithms 3
		double rand = Math.random();
		if(rand < 0.5){
			inda.setSkillFactor(a.getSkillFactor());
		}else{
			inda.setSkillFactor(b.getSkillFactor());
		}
		ArrayList<Double> fitnessTa= new ArrayList<Double>();
		for(int i=0;i<tasks.size();i++)
			if(i!=inda.getSkillFactor())
				fitnessTa.add(LIMIT);
			else
				fitnessTa.add(tasks.get(i).getValue(ca));
		
		inda.setFitnessTask(fitnessTa);
		inda.setFactorial_rank(fR);
		childrens.add(inda);
		if(p.checkIndvidualVail(cb)) 
			p.makeIndividualVail(cb);
		
		Individual indb= new Individual(cb, null);
		
		rand = Math.random();
		if(rand < 0.5){
			indb.setSkillFactor(a.getSkillFactor());
		}else{
			indb.setSkillFactor(b.getSkillFactor());
		}
		fitnessTa= new ArrayList<Double>();
		for(int i=0;i<tasks.size();i++)
			if(i!=indb.getSkillFactor())
				fitnessTa.add(LIMIT);
			else
				fitnessTa.add(tasks.get(i).getValue(cb));
		
		indb.setFitnessTask(fitnessTa);
		indb.setFactorial_rank(fR);
		childrens.add(indb);
		return childrens;
	}
	
	Individual mutation(Individual a){
		Random r= new Random();
		ArrayList<Integer> fR= new ArrayList<Integer>();
		for(int i=0;i<nTask;i++) fR.add(p.individuals.size()+1);
		int t= r.nextInt(a.getGen().size());
		ArrayList<Double> c= new ArrayList<Double>();
		for(int i=0;i<a.getGen().size();i++) c.add(a.getGen().get(i));
		c.set(t, r.nextDouble());
		if(p.checkIndvidualVail(c)) 
			p.makeIndividualVail(c);
		Individual ind= new Individual(c, null);
		ind.setSkillFactor(a.getSkillFactor());
		ArrayList<Double> fitnessTa= new ArrayList<Double>();
		for(int i=0;i<tasks.size();i++)
			if(i!=ind.getSkillFactor())
				fitnessTa.add(LIMIT);
			else
				fitnessTa.add(tasks.get(i).getValue(c));
		ind.setFitnessTask(fitnessTa);
		ind.setFactorial_rank(fR);
		
		return ind;
	}
	
	void selection(){
		//p.updatePopulation();
		Collections.sort(p.individuals, new Comparator<Individual>() {
			
			@Override
			public int compare(Individual i1, Individual i2){
				Double di1 = new Double(i1.getScalarFitness());
				Double di2 = new Double(i2.getScalarFitness());
				return di1.compareTo(di2);
			}
		});
		
		ArrayList<Individual> new_individuals = new ArrayList<Individual>();
		for(int i=0; i<p.nIndividual; i++){
			new_individuals.add(p.individuals.get(i));
		}
		p.individuals = new_individuals;
	}
}
