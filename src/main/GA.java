package main;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import knap.Knapsack;
import tsp.Tsp;

public class GA {
	int nTask=2;
	Tsp tsp= new Tsp();
	Knapsack kp= new Knapsack();
	Population p= new Population(10,nTask,tsp,kp);
	
	
	GA(){
		System.out.println(p.rankInTask);
		
	
	}
	void process(int inter,int nN){
		Random r= new Random();
		
		for(int i=0;i<inter;i++){
			ArrayList<Individual> individuals = p.individuals;
			for(int j=0;j<nN;j++){
				Individual a=individuals.get(r.nextInt(individuals.size()));
				Individual b=individuals.get(r.nextInt(individuals.size()));
				while(a==b) b=individuals.get(r.nextInt(individuals.size()));
				int ta=p.getArgminOfRank(a);
				int tb=p.getArgminOfRank(b);
				double t= r.nextDouble();
				if((ta==tb) || (t>0.0001)){
					crossOver(a.getGen(), b.getGen());
				} else {
					mutation(a.getGen());
					mutation(b.getGen());
				}
				//here
			}
		}
	}
	void crossOver(ArrayList<Double> a, ArrayList<Double> b){
		Random r= new Random();
		int t=r.nextInt(a.size()-1);
		ArrayList<Double> cb= new ArrayList<Double>();
		ArrayList<Double> ca= new ArrayList<Double>();
		for(int i=0;i<t;i++){
			ca.add(a.get(i));
			cb.add(b.get(i));
		}
		for(int i=t;i<a.size();i++){
			ca.add(b.get(i));
			cb.add(a.get(i));
		}
		
		ArrayList<Integer> kpd=kp.decode(ca);
		if(kp.getWeight(kpd)>kp.getB()) kp.makeIndivialVail(ca);
		ArrayList<Double> fitnessTa= new ArrayList<Double>();
		fitnessTa.add(tsp.getDistance(tsp.decode(ca)));
		fitnessTa.add(kp.getValue(kp.decode(ca)));
		Individual ind= new Individual(ca, fitnessTa);
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
}
