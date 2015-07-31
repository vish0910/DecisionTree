import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class DesisionTreeJava {
	static ArrayList<Attribute> record;
	static ArrayList<Record> recordsList = new ArrayList<Record>();
	static int numberOfAttributes = 5;
	static int[] numberOfCategories = { 3, 3, 2, 2, 2 };
	static String[][] attributeCategories = { { "sunny", "overcast", "rain" },
			{ "hot", "mild", "cool" }, { "high", "normal", "" },
			{ "weak", "strong", "" }, { "no", "yes", "" } };

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Read data from the file
		if (readData() == 0)
			System.exit(0);

		Node root = new Node(recordsList);
		root.setName("playtennis(Root)");
		root.setAttributeUsed(numberOfAttributes - 1);

		// Call to Recursive method to create a Tree
		generateTree(root);
		// printTree(root);
		System.out.println("============Running Test================");
		String[] decision = new String[recordsList.size()];
		int ind = 0;
		for (Record rt : recordsList) {
			decision[ind] = runTest(rt, root) == 0 ? "no" : "yes";
			System.out.println("Test data =>" + rt.toString());
			System.out.println("Prediction Result Should:" + decision[ind]);
			ind++;
		}
		getAccuracy(recordsList, decision);

		// Printing the Decision Tree
		System.out.println("============Decision Tree================");
		printTree(root);
	} // End of Main

	// Calculate accuracy and print the test report
	public static void getAccuracy(ArrayList<Record> r, String[] decision) {
		ArrayList<Attribute> al;
		int correctCount = 0;
		int totalCount = 0;
		for (int i = 0; i < decision.length; i++) {
			al = r.get(i).getRecord();

			if (al.get(4).getValue().equals(decision[i])) {
				correctCount++;
			}
			totalCount++;
		}
		double accuracy = (double) correctCount / totalCount * 100.0;
		System.out.println("============Test Report================");
		System.out.println("Total Predictions carried out:" + totalCount
				+ "\nNumber of Correct Predictions: " + correctCount);
		System.out.println("The accuracy is: " + accuracy + "%");
	}
	// Run the test on the dataset to get the prediction
	public static int runTest(Record r, Node head) {
		ArrayList<Attribute> al = r.getRecord();
		if (head.getDecision() > -1) {
			return head.getDecision();
		}

		String childType = head.getChildType();
		switch (childType) {
		case "outlook":
			if (al.get(0).getValue().equals(attributeCategories[0][0]))
				return runTest(r, head.getChildren().get(0));
			else if (al.get(0).getValue().equals(attributeCategories[0][1]))
				return runTest(r, head.getChildren().get(1));
			else if (al.get(0).equals(attributeCategories[0][2]))
				return runTest(r, head.getChildren().get(2));
			break;
		case "temperature":
			if (al.get(1).getValue().equals(attributeCategories[1][0]))
				return runTest(r, head.getChildren().get(0));
			else if (al.get(1).getValue().equals(attributeCategories[1][1]))
				return runTest(r, head.getChildren().get(1));
			else if (al.get(1).getValue().equals(attributeCategories[1][2]))
				return runTest(r, head.getChildren().get(2));
			break;
		case "humidity":
			if (al.get(2).getValue().equals(attributeCategories[2][0]))
				return runTest(r, head.getChildren().get(0));
			else if (al.get(2).getValue().equals(attributeCategories[2][1]))
				return runTest(r, head.getChildren().get(1));
			break;
		case "wind":
			if (al.get(2).getValue().equals(attributeCategories[3][0]))
				return runTest(r, head.getChildren().get(0));
			else if (al.get(2).getValue().equals(attributeCategories[3][1]))
				return runTest(r, head.getChildren().get(1));
			break;
		}

		return -1;
	}
	
	//Prints the tree
	public static void printTree(Node root) {
		root.printTree("");
	}

	//Gets subset of the dataset based on the attribute
	public static ArrayList<ArrayList<Record>> getSubsets(int i,
			ArrayList<Record> records) {
		ArrayList<ArrayList<Record>> subsets = new ArrayList<ArrayList<Record>>();
		ArrayList<Record> subset;
		System.out.println("Number of Categories for: "
				+ Attribute.names.values()[i].toString() + "is:"
				+ numberOfCategories[i]);
		for (int j = 0; j < numberOfCategories[i]; j++) {
			subset = new ArrayList<Record>();
			for (Record oldrecord : records) {
				ArrayList<Attribute> al = oldrecord.getRecord();
				// System.out.println("Working on:"+attributeCategories[i][j]);
				if (al.get(i).getValue().equals(attributeCategories[i][j])) {
					System.out.println("Working on:"
							+ attributeCategories[i][j]);
					subset.add(oldrecord);
				}
			}
			subsets.add(subset);
		}
		System.out.println("Size of subset:" + subsets.size());
		return subsets;
	}

	//Recursive method to generate decision tree
	public static void generateTree(Node root) {
		// Node root = new Node(records);
		System.out.println("@@@@@@@@@@Generating Tree for:@@@@@@@@@@@@" + root.getName());
		ArrayList<Record> records = root.getData();
		double rootEntropy = Entropy.calculateEntropy(records);
		root.setEntropy(rootEntropy);
		double maxGain = 0.0;
		int attributeToSpliton = -1;
		int rootSize = records.size();
		ArrayList<ArrayList<Record>> maxSubsets = new ArrayList<ArrayList<Record>>();
		
		for (int i = 0; i < numberOfAttributes - 1; i++) {
			//If attribute is already used, skip it.
			if (root.getAttributeUsed()[i]) {
				continue;
			}
			ArrayList<ArrayList<Record>> subsets = getSubsets(i, records);
			double infoGain = rootEntropy;
			System.out.println("Size of subset returned:" + subsets.size());
			for (int s = 0; s < subsets.size(); s++) {
				System.out.println("S:" + s);
				ArrayList<Record> subset = subsets.get(s);
				
				System.out.println("Size of subset:" + s + "is ==>"
						+ subset.size());
				double subSetEntropy = Entropy.calculateEntropy(subset);
				try {
					// System.out.println("Entered try catch");
					double weight = (double) subset.size() / rootSize;
					System.out.println("==>Weight:" + weight);
					infoGain -= weight * subSetEntropy;
				} catch (Exception e) {
					System.out.println("Divide by zero:" + e);
					System.exit(0);
				}
			}
			System.out.println("Information gain of:"
					+ Attribute.names.values()[i].toString() + " is: "
					+ infoGain + "\n----VSD-----");
			if (infoGain > maxGain) {
				maxGain = infoGain;
				maxSubsets = subsets;
				attributeToSpliton = i;
			}
		}
		System.out.println("**********Attribute to split on:"
				+ Attribute.names.values()[attributeToSpliton].toString() + ":"
				+ maxGain+"**********");
		
		String childType = Attribute.names.values()[attributeToSpliton]
				.toString();
		root.setChildType(childType);
		
		System.out.println("Creating children for root:");
		if (maxSubsets.size() != 0) {
			for (int i = 0; i < maxSubsets.size(); i++) {
				System.out.println("MaxSubsets' size:" + maxSubsets.size());

				String childName = childType + "("
						+ attributeCategories[attributeToSpliton][i] + ")";
				System.out.println("Inserting Child:" + childName);
				System.out.println(maxSubsets.get(i).toString());
				Node child = new Node(maxSubsets.get(i));
				child.setName(childName);
				child.setAttributeUsed(root.getAttributeUsed());
				child.setAttributeUsed(attributeToSpliton);

				child.setDecision(checkPurity(maxSubsets.get(i)));
				System.out.println(child.toString());
				root.insertChild(child);
			}
		} else {
			System.out.println("MAX SUBSET IS ZERO");
		}
		System.out.println("***Printing Node:******\n" + root.toString());
		// Processing children
		for (Node n : root.getChildren()) {
			System.out.println(n.toString());
			if (n.getDecision() != -1) {
				continue;
			}
			generateTree(n);
		}

	}
	
	//Checks the purity and returns the decision at the leaf
	public static int checkPurity(ArrayList<Record> rec) {
		int yesCount = 0;
		int noCount = 0;
		for (Record record : rec) {
			ArrayList<Attribute> al = record.getRecord();
			if (al.get(al.size() - 1).getValue().equals("yes")) {
				yesCount++;
			} else {
				noCount++;
			}
		}
		if (yesCount == 0) {
			return 0;
		} else if (noCount == 0) {
			return 1;
		} else {
			return -1;
		}
	}

	//Set if an attribute is used
	public static void setAttributeUsed(int i, Node n) {
		n.setAttributeUsed(i);
	}

	//Check if attribute is used
	public static boolean isUsed(int i, boolean[] b) {
		return b[i];
	}

	//Read data from the file
	public static int readData() {
		try {
			BufferedReader br = new BufferedReader(
					new FileReader("dataset.txt"));
			br.readLine();
			String line = br.readLine();
			while (line != null) {
				String[] values = line.split("\t");
				record = new ArrayList<Attribute>();
				for (int i = 0; i < values.length; i++) {
					record.add(new Attribute(i, values[i]));
				}
				recordsList.add(new Record(record));
				line = br.readLine();
			}
			System.out.println("DataRead Successfully!");
			br.close();
		} catch (Exception e) {
			System.out.println("Exception occured while reading the file" + e);
			return 0;
		}
		return 1;
	}
	
	//Print the data that was read
	public static void printData(ArrayList<Record> rList) {
		for (Record r : rList) {
			for (Attribute a : r.getRecord()) {
				System.out.println(a.toString());
			}
		}
	}

}
