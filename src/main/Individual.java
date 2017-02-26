package main;

import java.util.ArrayList;

public class Individual {
	ArrayList<Double> gen;
	ArrayList<Double> fitnessTask;
	int skillFactor;
	double scalarFitness;
	
	public Individual(ArrayList<Double> gen,ArrayList<Double> fitnessTask) {
		super();
		this.gen = gen;
		this.fitnessTask=fitnessTask;
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
	@Override
	public String toString() {
		return "Individual [gen=" + gen + ", fitnessTask=" + fitnessTask
				+ ", skillFactor=" + skillFactor + ", scalarFitness="
				+ scalarFitness + "]";
	}
}
