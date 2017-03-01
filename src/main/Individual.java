package main;

import java.util.ArrayList;

public class Individual {
	ArrayList<Double> gen;
	ArrayList<Double> fitnessTask;
	ArrayList<Integer> factorial_rank;
	int skillFactor;
	double scalarFitness;
	
	public Individual(ArrayList<Double> gen,ArrayList<Double> fitnessTask) {
		super();
		this.gen = gen;
		this.fitnessTask=fitnessTask;
	}
	
	public Integer getMinFactorialRank(){
		Integer min=10000000;
		for(int i=0;i<factorial_rank.size();i++){
			Integer tmp=factorial_rank.get(i);
			if(min> tmp) min=tmp;
		}
		return min;
	}
	public ArrayList<Double> getGen() {
		return gen;
	}
	public void setGen(ArrayList<Double> gen) {
		this.gen = gen;
	}
	public ArrayList<Double> getFitnessTask() {
		return fitnessTask;
	}
	public void setFitnessTask(ArrayList<Double> fitnessTask) {
		this.fitnessTask = fitnessTask;
	}
	public int getSkillFactor() {
		return skillFactor;
	}
	public void setSkillFactor(int skillFactor) {
		this.skillFactor = skillFactor;
	}
	public double getScalarFitness() {
		return scalarFitness;
	}
	public void setScalarFitness(double scalarFitness) {
		this.scalarFitness = scalarFitness;
	}
	public ArrayList<Integer> getFactorial_rank() {
		return factorial_rank;
	}
	public void setFactorial_rank(ArrayList<Integer> factorial_rank) {
		this.factorial_rank = factorial_rank;
	}
	@Override
	public String toString() {
		return "Individual [gen=" + gen + ", fitnessTask=" + fitnessTask
				+ ", factorial_rank=" + factorial_rank + ", skillFactor="
				+ skillFactor + ", scalarFitness=" + scalarFitness + "]";
	}
	
}
