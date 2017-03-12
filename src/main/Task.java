package main;

import java.util.ArrayList;
import java.util.List;

public interface Task {
	public ArrayList<Double> makeIndivialVail(ArrayList<Double> inds);
	public boolean checkIndivialVail(ArrayList<Double> inds);
	public Double getvalue(ArrayList<Double> inds);
	public ArrayList<Object> decode(ArrayList<Double> inds);

}
