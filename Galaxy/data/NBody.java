public class NBody{
	public double readRadius(String[] args) {
		In in = new In("planets.txt");

		int firstItemInFile = in.readInt();
		double secondItemInFile = in.readDouble();
		String earth = in.readString();
		String mars = in.readString();
		String mercury = in.readString();
		String sun = in.readString();
		String venus = in.readString();

		System.out.println(secondItemInFile);

	}
}
