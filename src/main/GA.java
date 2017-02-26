package main;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import knap.Knapsack;
import tsp.Tsp;

public class GA {
	int nTask=2;
	Tsp tsp= new Tsp();
	Knapsack kp= new Knapsack();
	Population p= new Population(10,nTask,tsp,kp);
	
	
	GA(){
		//System.out.println("rank in task:"+p.rankInTask);
	}
	void process(int inter,int nN){
		Random r= new Random();
		
		for(int i=0;i<inter;i++){
			ArrayList<Individual> individuals = p.individuals;
			for(int j=0;j<nN;j++){
				Individual a=individuals.get(r.nextInt(individuals.size()));
				Individual b=individuals.get(r.nextInt(individuals.size()));
				while(a==b) b=individuals.get(r.nextInt(individuals.size()));
				int ta=a.getSkillFactor();
				int tb=b.getSkillFactor();
				double t= r.nextDouble();
				if((ta==tb) || (t>0.0001)){
					crossOver(a, b);
				} else {
					mutation(a.getGen());
					mutation(b.getGen());
				}
				selection();
			}
		}
	}
	void crossOver(Individual a, Individual b){
		Random r= new Random();
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
		
		ArrayList<Integer> kpd=kp.decode(ca);
		if(kp.getWeight(kpd)>kp.getB()) kp.makeIndivialVail(ca);
		ArrayList<Double> fitnessTa= new ArrayList<Double>();
		fitnessTa.add(tsp.getDistance(tsp.decode(ca)));
		fitnessTa.add(kp.getValue(kp.decode(ca)));
		
		Individual ind= new Individual(ca, fitnessTa);
//		double rand = Math.random();
//		
//		//Compute scalar fitness (Alg3)
//		if(rand<0.5){
//			int skill_factor_ca = a.getSkillFactor();
//			ind.setSkillFactor(skill_factor_ca);
//			//ind.setScalarFitness(fitnessTa.get(skill_factor_ca));
//		}
		p.add(ind);
		
		
		kpd=kp.decode(cb);
		if(kp.getWeight(kpd)>kp.getB()) kp.makeIndivialVail(cb);
		fitnessTa= new ArrayList<Double>();
		fitnessTa.add(tsp.getDistance(tsp.decode(cb)));
		fitnessTa.add(kp.getValue(kp.decode(cb)));
		ind= new Individual(cb, fitnessTa);
		p.add(ind);
	}
	
	void mutation(ArrayList<Double> a){
		Random r= new Random();
		int t= r.nextInt();
		ArrayList<Double> c= new ArrayList<Double>();
		for(int i=0;i<a.size();i++) c.add(a.get(i));
		c.set(t, r.nextDouble());
		ArrayList<Integer> kpd=kp.decode(c);
		if(kp.getWeight(kpd)>kp.getB()) kp.makeIndivialVail(c);
		ArrayList<Double> fitnessTa= new ArrayList<Double>();
		fitnessTa.add(tsp.getDistance(tsp.decode(c)));
		fitnessTa.add(kp.getValue(kp.decode(c)));
		Individual ind= new Individual(c, fitnessTa);
		p.add(ind);
	}
	
	void selection(){
		p.updatePopulation();
		Collections.sort(p.individuals, new Comparator<Individual>() {
			
			@Override
			public int compare(Individual i1, Individual i2){
				Double di1 = new Double(i1.getScalarFitness());
				Double di2 = new Double(i2.getScalarFitness());
				return di1.compareTo(di2);
			}
		});
		
		ArrayList<Individual> new_individuals = new ArrayList<Individual>();
		for(int i=0; i<p.n; i++){
			new_individuals.add(p.individuals.get(i));
		}
		p.individuals = new_individuals;
	}
}
