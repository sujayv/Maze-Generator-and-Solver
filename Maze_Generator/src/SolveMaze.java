import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.Random;

public class SolveMaze {
	
	int[][] north;
	int[][] south;
	int[][] west;
	int[][] east;
	
	//Global integer array indicating if a cell has been visited or not
	int[][] visited;
	
	//The dimension of the array retrieved from the GenerateMaze class
	int dimension;
	
	//ResultSet that contains (x,y) value pairs of the cells that form the final path
	Set<Entry<Integer, Integer>> resultset = new HashSet<>();
	
	//Constructor that calls solve to solve the maze generated from GenerateMaze.java
	SolveMaze() throws IOException
	{
		solve();
	}
	
	//Solve using DFS approach
	private void solve() throws IOException
	{
		GenerateMaze maze = new GenerateMaze();
		dimension = maze.getDimension();
		visited = new int[dimension][dimension];
		System.out.println();
		System.out.println();
		System.out.println("Now we will solve the maze using the same DFS approach");
		north = maze.getNorth();
		south = maze.getSouth();
		west = maze.getWest();
		east = maze.getEast();
		int row = 0;
		int column = 0;
		resultset.add(findPath(row,column,row,column));
		resultset.add(new SimpleEntry<Integer,Integer>(0,0));
		printPath(row,column);
		System.out.println();
	}

	//Method to find path using DFS
	private Entry<Integer,Integer> findPath(int row,int column,int prevrow,int prevcolumn)
	{
		Set<Entry<Integer, Integer>> cells = new HashSet<>();
		visited = new int[dimension][dimension];
		visited[prevrow][prevcolumn] = 1;	//Ensuring that we do not consider the cell that we arrived from as a possible neighbor to visit again
		int temprow = prevrow;
		int tempcolumn = prevcolumn;
		visited[row][column] = 1;
		prevrow = row;
		prevcolumn = column;
		if(column+1 < dimension)
		{
			if(east[row][column] == 0)
				cells.add(new SimpleEntry<Integer,Integer>(row,column+1));
		}
		if(column-1 >= 0)
		{
			if(west[row][column] == 0 )
				cells.add(new SimpleEntry<Integer,Integer>(row,column-1));
		}
		if(row-1 >= 0)
		{
			if( north[row][column] == 0)
				cells.add(new SimpleEntry<Integer,Integer>(row-1,column));
		}
		if(row+1 < dimension)
		{
			if(south[row][column] == 0 )
				cells.add(new SimpleEntry<Integer,Integer>(row+1,column));
		}
		Random generator = new Random();
		while(!cells.isEmpty())
		{
			visited[temprow][tempcolumn] = 1;
			Iterator<Entry<Integer,Integer>> it = cells.iterator();
			int choice;
			while(true)
			{
				choice = generator.nextInt(4);
				if(choice == 0 && row+1 < dimension)
				{
					row = row +1;
				}
				else if(choice == 1 && column+1 < dimension)
				{
					column = column + 1;
				}
				else if(choice == 2 && column-1 >= 0)
				{
					column = column - 1;
				}
				else if(choice == 3 && row-1 >= 0)
				{
					row = row - 1;
				}
				if(cells.contains(new SimpleEntry<Integer,Integer>(row,column)))
				{
					break;
				}
			}
			cells.remove(new SimpleEntry<Integer,Integer>(row,column));
			if(visited[row][column] != 1 && !(row == dimension-1 && column == dimension-1))
			{
				visited[row][column] = 1;
				Entry<Integer,Integer> result = findPath(row,column,prevrow,prevcolumn);
				if(result.getKey() != -1)
				{
					resultset.add(result);
					Entry<Integer,Integer> result1 = new SimpleEntry<Integer,Integer>(row,column);
					return result1;
				}
				else
				{
					//System.out.println("Did not reach Returned null");
					//return null;
				}
			}
			else if(visited[row][column] != 1 && row == dimension-1 && column == dimension-1)
			{
				visited[row][column] = 1;
				Entry<Integer,Integer> result = new SimpleEntry<Integer,Integer>(row,column);
				return result;
			}
		}
		return new SimpleEntry<Integer,Integer>(-1,-1);
	}
	
	//Method to print the path - '~' and '!' stand for walls and '#' stands for the path taken to reach the destination
	private void printPath(int row, int column)
	{
		System.out.println();
		System.out.print("        ");
		for(int i=0;i<dimension;i++)
		{
			for(int j=0;j<dimension;j++)
			{
				if(i-1 < 0)
				{
					System.out.print(" ~~~");
				}
				else if(north[i][j] == 1)
				{
					System.out.print(" ~~~");
				}
				else
					System.out.print("    ");
			}
			System.out.println();
			if(i-1 < 0)
			{
				System.out.print("Enter-->");
			}
			else
			{
				System.out.print("        ");
			}
			for(int j=0;j<dimension;j++)
			{
				if(j-1 < 0 && i-1 > 0 && !resultset.contains(new SimpleEntry<Integer,Integer>(i,j)))
				{
					System.out.print("!   ");
				}
				else if(j-1 < 0 && i-1 > 0 && resultset.contains(new SimpleEntry<Integer,Integer>(i,j)))
				{
					System.out.print("! # ");
				}
				else if(west[i][j] == 1 && !resultset.contains(new SimpleEntry<Integer,Integer>(i,j)))
				{
					System.out.print("!   ");
				}
				else if(west[i][j] == 1 && resultset.contains(new SimpleEntry<Integer,Integer>(i,j)))
				{
					System.out.print("! # ");
				}
				else if(resultset.contains(new SimpleEntry<Integer,Integer>(i,j)))
				{
					System.out.print("  # ");
				}
				else
					System.out.print("    ");
				if(j == dimension-1 && i != dimension-1)
				{
					System.out.print("!");
				}
				else if(j == dimension-1 && i == dimension-1)
					System.out.print(" -->Exit");
			}
			System.out.println();
			System.out.print("        ");
		}
		for(int i=0;i<dimension;i++)
		{
			System.out.print(" ~~~");
		}
	}
	
	public static void main(String args[]) throws IOException
	{
		SolveMaze mazeSolution = new SolveMaze();
	}
}
