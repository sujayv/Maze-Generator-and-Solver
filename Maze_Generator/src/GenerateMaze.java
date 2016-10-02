import java.io.*;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
public class GenerateMaze {
	
	private int north[][];
	private int south[][];
	private int west[][];
	private int east[][];
	private int visited[][];
	
	GenerateMaze() throws IOException
	{
		create();
	}
	
	private void create() throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter the size of the maze(nxn)");
		int dimension = Integer.parseInt(br.readLine());
		north = new int[dimension][dimension];
		south = new int[dimension][dimension];
		west = new int[dimension][dimension];
		east = new int[dimension][dimension];
		visited = new int[dimension][dimension];
		for(int i=0;i<dimension;i++)
		{
			for(int j=0;j<dimension;j++)
			{
				north[i][j] = 1;
				south[i][j] = 1;
				west[i][j] = 1;
				east[i][j] = 1;
				visited[i][j] = 0;
			}
		}
		Random generator = new Random();
		int row = generator.nextInt(dimension);
		int column = generator.nextInt(dimension);
		visitCells(row,column,dimension);
		drawMaze(dimension);
	}
	
	private void visitCells(int row, int column, int dimension)
	{
		Set<Entry<Integer, Integer>> cells = new HashSet<>();
		Random generator = new Random();
		int neighbours[][][] = new int[dimension][dimension][4];
		visited[row][column] = 1;
		if((row+1)!= dimension)
		{
			cells.add(new SimpleEntry<Integer,Integer>(row+1,column));
		}
		if(column+1 != dimension)
		{
			cells.add(new SimpleEntry<Integer,Integer>(row,column+1));
		}
		if(column-1 >= 0)
		{
			cells.add(new SimpleEntry<Integer,Integer>(row,column-1));
		}
		if(row-1 >= 0)
		{
			cells.add(new SimpleEntry<Integer,Integer>(row-1,column));
		}
		while(!cells.isEmpty())
		{
			int choice = generator.nextInt(4);
			if(neighbours[row][column][choice] != 1)
			{
				neighbours[row][column][choice] = 1;
				if(choice == 0 && (row+1)!= dimension)
				{
					row = row +1;
				}
				else if(choice == 1 && column+1 != dimension)
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
				cells.remove(new SimpleEntry<Integer,Integer>(row,column));
				//System.out.println("Now it does not contain"+cells.contains(new SimpleEntry<Integer,Integer>(row,column)));
				if(visited[row][column] != 1)
				{
					if(choice == 0)
					{
						south[row-1][column] = 0;
						north[row][column] = 0;
					}
					else if(choice == 1)
					{
						east[row][column-1] = 0;
						west[row][column] = 0;
					}
					else if(choice == 2)
					{
						east[row][column] = 0;
						west[row][column+1] = 0;
					}
					else if(choice == 3)
					{
						south[row][column] = 0;
						north[row+1][column] = 0;
					}
					visitCells(row,column,dimension);
				}
			}
		}
	}
	
	private void drawMaze(int dimension)
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
				if(j-1 < 0 && i-1 > 0)
				{
					System.out.print("!   ");
				}
				else if(west[i][j] == 1)
				{
					System.out.print("!   ");
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
	
	public int[][] getNorth()
	{
		return north;
	}
	
	public int[][] getSouth()
	{
		return south;
	}
	
	public int[][] getEast()
	{
		return east;
	}
	
	public int[][] getWest()
	{
		return west;
	}
	
	public int getDimension()
	{
		return west.length;
	}
	
	public static void main(String args[]) throws IOException
	{
		GenerateMaze maze = new GenerateMaze();
	}
}
