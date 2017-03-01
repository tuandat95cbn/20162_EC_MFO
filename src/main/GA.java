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
		p.init();
		for(int i=0;i<inter;i++){
			ArrayList<Individual> individuals = p.individuals;
			for(int j=0;j<nN;j++){
				Individual a=individuals.get(r.nextInt(individuals.size()));
				Individual b=individuals.get(r.nextInt(individuals.size()));
				while(a==b) b=individuals.get(r.nextInt(individuals.size()));
				int ta=a.getSkillFactor();
				int tb=b.getSkillFactor();
				double t= r.nextDouble();
				
				ArrayList<Individual> childrens = new ArrayList<Individual>();;
				
				if((ta==tb) || (t>0.0001)){
					childrens = crossOver(a, b);
				} else {
					Individual ia = mutation(a);
					Individual ib = mutation(b);
					childrens.add(ia);
					childrens.add(ib);
				}
				
				p.add(childrens);
				selection();
			}
		}
	}
	
	ArrayList<Individual> crossOver(Individual a, Individual b){
		ArrayList<Individual> childrens = new ArrayList<Individual>();
		
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
		ArrayList<Integer> kpd=kp.decode(ca);
		if(kp.getWeight(kpd)>kp.getB()) kp.makeIndivialVail(ca);
		ArrayList<Double> fitnessTa= new ArrayList<Double>();
		fitnessTa.add(tsp.getDistance(tsp.decode(ca)));
		fitnessTa.add(kp.getValue(kp.decode(ca)));
		Individual inda= new Individual(ca, fitnessTa);
		
		double rand = Math.random();
		if(rand < 0.5){
			inda.setSkillFactor(a.getSkillFactor());
		}else{
			inda.setSkillFactor(b.getSkillFactor());
		}
		childrens.add(inda);
		
		kpd=kp.decode(cb);
		if(kp.getWeight(kpd)>kp.getB()) kp.makeIndivialVail(cb);
		fitnessTa= new ArrayList<Double>();
		fitnessTa.add(tsp.getDistance(tsp.decode(cb)));
		fitnessTa.add(kp.getValue(kp.decode(cb)));
		Individual indb= new Individual(cb, fitnessTa);
		
		rand = Math.random();
		if(rand < 0.5){
			indb.setSkillFactor(a.getSkillFactor());
		}else{
			indb.setSkillFactor(b.getSkillFactor());
		}
		childrens.add(indb);
		return childrens;
	}
	
	Individual mutation(Individual a){
		Random r= new Random();
		int t= r.nextInt();
		ArrayList<Double> c= new ArrayList<Double>();
		for(int i=0;i<a.getGen().size();i++) c.add(a.getGen().get(i));
		c.set(t, r.nextDouble());
		ArrayList<Integer> kpd=kp.decode(c);
		if(kp.getWeight(kpd)>kp.getB()) kp.makeIndivialVail(c);
		ArrayList<Double> fitnessTa= new ArrayList<Double>();
		fitnessTa.add(tsp.getDistance(tsp.decode(c)));
		fitnessTa.add(kp.getValue(kp.decode(c)));
		Individual ind= new Individual(c, fitnessTa);
		ind.setSkillFactor(a.getSkillFactor());
		
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
