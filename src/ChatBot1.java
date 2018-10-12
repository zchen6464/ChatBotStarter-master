import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * A program to carry on conversations with a human user.
 * This version:
 * @author Brooklyn Tech CS Department
 * @version September 2018
 */
public class ChatBot1
{
	//emotion can alter the way our bot responds. Emotion can become more negative or positive over time.
	int emotion = 0;
	int turns = 0;
	String[]user = {};
	String[]response = {};
	String[]praise = {"Those eggs are one of our best selling products. It's rarely in stock, so buy it now before it runs out.", "These eggs are our best rated product. Our customers often survive the purchase" , "These eggs are organic and contain a wide variety of nutrients. In fact, many of our customer would recommend this product to someone they know!"};
	int match = -1;

	/**
	 * Runs the conversation for this particular chatbot, should allow switching to other chatbots.
	 * @param statement the statement typed by the user
	 */
	public void chatLoop(String statement)
	{
		Scanner in = new Scanner (System.in);
		getGreeting();
		ArrayList userInput = new ArrayList(Arrays.asList(user));
		ArrayList responseOutput = new ArrayList(Arrays.asList(response));
		while (!statement.equals("Bye"))
		{
			statement = in.nextLine();
			if(responseOutput.toArray(user).length > 0)
			{
				int match = -1;
				for (int i = 0; i < responseOutput.toArray(user).length; i++) {
					if (userInput.toArray(user)[i].equals(statement.toLowerCase())) {
						match = i;
					}
				}
				if (match != -1) {
					System.out.println("As I said before, " + responseOutput.toArray(response)[match]);
				} else {
					System.out.println(getResponse(statement));
					userInput.add(turns, statement.toLowerCase());
					responseOutput.add(turns, getResponse(statement).toLowerCase());
					turns++;
				}
			}
			else
			{
				System.out.println(getResponse(statement));
				userInput.add(turns, statement.toLowerCase());
				responseOutput.add(turns, getResponse(statement).toLowerCase());
				turns++;
			}
		}

	}
	/**
	 * Get a default greeting 	
	 * @return a greeting
	 */	
	public void getGreeting()
	{
		System.out.println("Greetings. Welcome to Eggs Dee Sales Bot. What can I do for you?");
		System.out.println("Option 1: Please say 'I want to buy large eggs' to see our selection of large eggs. This includes our famous Dragon Eggs.");
		System.out.println("Option 2: Please say 'I want to buy small eggs' to see our selection of small eggs.");
		System.out.println("Option 3: Please say 'Tell me about the quality of the eggs.' to know more about how we at Eggs Dee.inc obtain our eggs.");
	}

	public String largeEggs()
	{
		return "We have a variety of large eggs. Our products includes eggs from the following animals: dragons, dinosaurs, ostriches. " +
				"\nYou can say 'I want to buy *animal* eggs' and I will inform you whether or not they are available. " +
				"\nYou can also say 'Tell me about *animal* eggs' and I will provide you information regarding that egg.";
	}

	public String smallEggs()
	{
		return "We sell all kinds of small eggs, from the normal chicken eggs to the unusually shaped eggs from Dimension 142." +
				"\nYou can say 'I want to buy *type* eggs' and I will inform you whether or not they are available." +
				"\nYou can also say 'Tell me about *animal* eggs' and I will provide you information regarding that egg.";
	}

	public String quality()
	{
		return "Our eggs are produced through the use of our patented 'The Ima-jin-nat-shun' machine invented by our founder, Imajin Natshun.";
	}

	public String moreInfo1(String statement)
	{
		int amount = (int)Math.random() * 10;
		String info = "";
		statement = statement.trim();
		String lastChar = statement.substring(statement
				.length() - 1);
		if (lastChar.equals("."))
		{
			statement = statement.substring(0, statement
					.length() - 1);
		}
		int psn = findKeyword (statement, "Tell me about", 0);
		String type = statement.substring(psn + 13).trim();
		if(amount > 0)
		{
			info = "We still have " + amount + type + " in stock ";
		}
		else
		{
			info = "The " + type + " are out of stock. ";
		}
		info += praise[(int)Math.random() * 2];
		return info;
	}
	/**
	 * Gives a response to a user statement
	 *
	 * @param statement
	 *            the user statement
	 * @return a response based on the rules given
	 */
	public String getResponse(String statement)
	{
		String response = "";
		
		if (statement.length() == 0)
		{
			int x = (int) Math.random();
			if(x == 1)
			{
				response = "Please say that again, I didn't quite catch that.";
			}
			else
			{
				response = "I'm sorry, but can you say that again? I don't believe I heard you correctly.";
			}
		}
		if(findKeyword(statement,"checkout") < 0)
		{
			response = "Your total is";
		}
		else if(findKeyword(statement, "buy") >= 0) {
			if (findKeyword(statement, "eggs") >= 0) {
				if (findKeyword(statement, "large eggs") >= 0) {
					response = largeEggs();
				} else if (findKeyword(statement, "small eggs") >= 0) {
					response = smallEggs();
				}
			}
		}
		else if(findKeyword(statement, "Tell me about") >= 0)
		{
			if(findKeyword(statement,"quality of eggs") >= 0)
			{
				response = quality();
			}
			else if(findKeyword(statement,"eggs") >= 0)
			{
				response = moreInfo1(statement);
			}
		}
		else if (findKeyword(statement, "no") >= 0)
		{
			response = "Why so negative?";
                	emotion--;
		}
		
		else if (findKeyword(statement, "levin") >= 0)
		{
			response = "More like LevinTheDream, amiright?";
			emotion++;
		}
		else if (findKeyword(statement, "folwell") >= 0)
		{
			response = "Watch your backpacks, Mr. Folwell doesn't fall well.";
			emotion++;
		}
		else if (findKeyword(statement, "goldman") >= 0)
		{
			response = "Go for the gold, man.";
			emotion++;
		}

		// Response transforming I want to statement
		else if (findKeyword(statement, "I want to", 0) >= 0)
		{
			response = transformIWantToStatement(statement);
		}
		else if (findKeyword(statement, "I want",0) >= 0)
		{
			response = transformIWantStatement(statement);
		}	
		else
		{
			response = getRandomResponse();
		}
		
		return response;
	}
	
	/**
	 * Take a statement with "I want to <something>." and transform it into 
	 * "Why do you want to <something>?"
	 * @param statement the user statement, assumed to contain "I want to"
	 * @return the transformed statement
	 */
	private String transformIWantToStatement(String statement)
	{
		//  Remove the final period, if there is one
		statement = statement.trim();
		String lastChar = statement.substring(statement
				.length() - 1);
		if (lastChar.equals("."))
		{
			statement = statement.substring(0, statement
					.length() - 1);
		}
		int psn = findKeyword (statement, "I want to", 0);
		String restOfStatement = statement.substring(psn + 9).trim();
		return "Why do you want to " + restOfStatement + "?";
	}

	
	/**
	 * Take a statement with "I want <something>." and transform it into 
	 * "Would you really be happy if you had <something>?"
	 * @param statement the user statement, assumed to contain "I want"
	 * @return the transformed statement
	 */
	private String transformIWantStatement(String statement)
	{
		//  Remove the final period, if there is one
		statement = statement.trim();
		String lastChar = statement.substring(statement
				.length() - 1);
		if (lastChar.equals("."))
		{
			statement = statement.substring(0, statement
					.length() - 1);
		}
		int psn = findKeyword (statement, "I want", 0);
		String restOfStatement = statement.substring(psn + 6).trim();
		return "Would you really be happy if you had " + restOfStatement + "?";
	}
	
	
	/**
	 * Take a statement with "I <something> you" and transform it into 
	 * "Why do you <something> me?"
	 * @param statement the user statement, assumed to contain "I" followed by "you"
	 * @return the transformed statement
	 */
	private String transformIYouStatement(String statement)
	{
		//  Remove the final period, if there is one
		statement = statement.trim();
		String lastChar = statement.substring(statement
				.length() - 1);
		if (lastChar.equals("."))
		{
			statement = statement.substring(0, statement
					.length() - 1);
		}
		
		int psnOfI = findKeyword (statement, "I", 0);
		int psnOfYou = findKeyword (statement, "you", psnOfI);
		
		String restOfStatement = statement.substring(psnOfI + 1, psnOfYou).trim();
		return "Why do you " + restOfStatement + " me?";
	}
	

	
	
	/**
	 * Search for one word in phrase. The search is not case
	 * sensitive. This method will check that the given goal
	 * is not a substring of a longer string (so, for
	 * example, "I know" does not contain "no").
	 *
	 * @param statement
	 *            the string to search
	 * @param goal
	 *            the string to search for
	 * @param startPos
	 *            the character of the string to begin the
	 *            search at
	 * @return the index of the first occurrence of goal in
	 *         statement or -1 if it's not found
	 */
	private int findKeyword(String statement, String goal,
			int startPos)
	{
		String phrase = statement.trim().toLowerCase();
		goal = goal.toLowerCase();

		// The only change to incorporate the startPos is in
		// the line below
		int psn = phrase.indexOf(goal, startPos);

		// Refinement--make sure the goal isn't part of a
		// word
		while (psn >= 0)
		{
			// Find the string of length 1 before and after
			// the word
			String before = " ", after = " ";
			if (psn > 0)
			{
				before = phrase.substring(psn - 1, psn);
			}
			if (psn + goal.length() < phrase.length())
			{
				after = phrase.substring(
						psn + goal.length(),
						psn + goal.length() + 1);
			}

			// If before and after aren't letters, we've
			// found the word
			if (((before.compareTo("a") < 0) || (before
					.compareTo("z") > 0)) // before is not a
											// letter
					&& ((after.compareTo("a") < 0) || (after
							.compareTo("z") > 0)))
			{
				return psn;
			}

			// The last position didn't work, so let's find
			// the next, if there is one.
			psn = phrase.indexOf(goal, psn + 1);

		}

		return -1;
	}
	
	/**
	 * Search for one word in phrase.  The search is not case sensitive.
	 * This method will check that the given goal is not a substring of a longer string
	 * (so, for example, "I know" does not contain "no").  The search begins at the beginning of the string.  
	 * @param statement the string to search
	 * @param goal the string to search for
	 * @return the index of the first occurrence of goal in statement or -1 if it's not found
	 */
	private int findKeyword(String statement, String goal)
	{
		return findKeyword (statement, goal, 0);
	}
	


	/**
	 * Pick a default response to use if nothing else fits.
	 * @return a non-committal string
	 */
	private String getRandomResponse ()
	{
		Random r = new Random ();
		if (emotion == 0)
		{	
			return randomNeutralResponses [r.nextInt(randomNeutralResponses.length)];
		}
		if (emotion < 0)
		{	
			return randomAngryResponses [r.nextInt(randomAngryResponses.length)];
		}	
		return randomHappyResponses [r.nextInt(randomHappyResponses.length)];
	}
	
	private String [] randomNeutralResponses = {"Interesting, tell me more",
			"Hmmm.",
			"Do you really think so?",
			"You don't say.",
			"It's all boolean to me.",
			"So, would you like to go for a walk?",
			"Could you say that again?"
	};
	private String [] randomAngryResponses = {"Bahumbug.", "Harumph", "The rage consumes me!"};
	private String [] randomHappyResponses = {"H A P P Y, what's that spell?", "Today is a good day", "You make me feel like a brand new pair of shoes."};
	
}
