package main;

import java.util.ArrayList;
import java.util.List;

public interface Task {
	public ArrayList<Double> makeIndivialVail(ArrayList<Double> ind);
	public boolean checkIndivialVail(ArrayList<Double> ind);
	public Double getValue(ArrayList<Double> ind);
	public int getLenGen();
	//public ArrayList<Object> decode(ArrayList<Double> inds);

}
