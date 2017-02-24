package main;

import java.util.ArrayList;

public class Individual {
	ArrayList<Double> gen;
	ArrayList<Double> fitnessTask;
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
	
}
