package scraper;

import java.io.IOException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 
 * @author wenbolin
 *
 */
public class GetPage {
	
	//test cases 1: digital camera, 30
	//test cases 2: xxxxxx, 15
	//test cases 3: xxxxxx
	//url problems: how to build url
	
	/**
	 * 
	 * @param query
	 * @throws IOException
	 */
	public void calculateProductNum(String query) throws IOException {
		//TASK : error handling
		if(query.equals("")) {
			System.out.println("Error message : input is invalid, please type again. ");
			return;
		}
		
		BuildUrl newClient = new BuildUrl();
		
		Document doc = newClient.getDOMTree(query, 1);
		
		Element pageSelection = doc.getElementById("pagination");
		int totalPageNum = 1;
		
		if(pageSelection != null) {
			Elements pages = pageSelection.getElementsByTag("option");
			totalPageNum = pages.size();
		}
		
		//TASK : test
		System.out.println("totalPageNum: " + totalPageNum);
		
		
		Element totalNumDiv = doc.getElementById("nmbProdItems");
		
		//TASK : deal with error
		if(totalNumDiv == null) {
			System.out.println("Error message: sorry, no such products found.");
			return;
		}
		
		Element totalNumEle = totalNumDiv.select("span").first();
		String str = new String(totalNumEle.text());
		String[] sArr = str.split("\\W+");
		int totalProductNumInt = Integer.parseInt(sArr[4]);
		
		//TASK : print out result
		if(str.charAt(str.length() - 1) == '+')
			System.out.println("Total product number is " + totalProductNumInt + "+.");
		else System.out.println("Total product number is " + totalProductNumInt + ".");
		
	}
	
	/**
	 * 
	 * @param arguments
	 * @throws IOException
	 */
	public void showProductsOnAPage(String[] arguments) throws IOException {
		
		String query = arguments[0];
		
		//TASK : test
		//System.out.println(encodedQuery);
		
		int pageNum = Integer.parseInt(arguments[1]);
		
		//TASK : retrieve page
		
		//HttpClient 
		
		BuildUrl newClient = new BuildUrl();
		
		Document doc = newClient.getDOMTree(query, pageNum);

		//TASK : get page number
		Element pageSelection = doc.getElementById("pagination");
		
		int totalPageNum = 1;
		
		if(pageSelection != null) {
			//TASK : test
			//System.out.println("pageSelection is not null");
			Elements pages = pageSelection.getElementsByTag("option");
			totalPageNum = pages.size();
		}
		
		//TASK : handle error input
		if(pageNum > totalPageNum) {
			int upperLimit = totalPageNum + 1;
			System.out.println("Error Message: ");
			System.out.println("Sorry, the page you request is beyond total page number. "
					+ "Please input a number less than " + upperLimit + ".");
			return;
		}
		
		//TASK : get objects
		Element allProducts = doc.getElementById("cardsHolder");
		
		//TASK : handle errors
		if(allProducts == null) {
			System.out.println("Error message: sorry, there is not any products under this query.");
			return;
		}
		
		Elements productContainers = allProducts.getElementsByClass("cardContainer");
		
		//TASK : test
		//System.out.println(productContainers.size());
		
		ProductInfo[] productArr = new ProductInfo[productContainers.size()];
		
		//TASK : initialize array
		for(int i = 0; i < productArr.length; i++) {
			productArr[i] = new ProductInfo();
			productArr[i].setVendor("sears");
		}
		
		//TASK : output result
		System.out.println("There are " + productContainers.size() + " products on this page.");
		System.out.println("They are: ");
		System.out.println("");
		System.out.println("================================="
				+ "======================================");
		
		//TASK : GET product information
		for(int i = 0; i < productContainers.size(); i++) {
			Element curEle = productContainers.get(i);
			
			//TASK : deal with product name
			Element curProductNameEle = curEle.getElementsByClass("cardProdTitle").first();
			Element productName = curProductNameEle.getElementsByTag("h2").first().
										getElementsByTag("a").first().
										getElementsByAttribute("title").first();
			productArr[i].setName(productName.text());
			//TASk : test
			System.out.println("Product Name: " + productArr[i].getName());
			
			
			//TASK : deal with product price
			Element curProductPriceEle = curEle.getElementsByClass("SGLView").first();
			Element productPrice = curProductPriceEle.getElementsByTag("input").first();
			String productPriceStr = productPrice.val();
			String[] productPriceStrArr = productPriceStr.split(",");
			double productPriceDouble = Double.parseDouble(productPriceStrArr[0]);
			productArr[i].setPrice(productPriceDouble);
			//TASk : test
			System.out.println("Product Price: $" + productArr[i].getPrice());
			
			//TASK : deal with product vendor
			Element curProductVendorEle = curEle.getElementById("mrkplc");
			if(curProductVendorEle != null) {
				Element vendorElement = 
						curProductVendorEle.getElementsByTag("p").first();
				String vendorWholeText = vendorElement.text();
				//TASK : test
				//System.out.println(vendorWholeText);
				if(vendorWholeText.contains("Sold by ")) {
					String vendorName = vendorWholeText.substring("Sold by ".length());
					productArr[i].setVendor(vendorName);
				}
				else {
					productArr[i].setVendor("Other vendors");
				}
			}
			//TASk : test
			System.out.println("Product Vendor: " + productArr[i].getVendor());
			System.out.println("====================================="
					+ "==================================");
		}
	}
	
	/**
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//TASK : process query
		
		/*
		//TASK : test
		String[] test_args = new String[2];
		test_args[0] = "digital camera";
		test_args[1] = "3";
		*/
		
		GetPage test = new GetPage();
		if(args.length == 1) {
			test.calculateProductNum(args[0]);
		}
		else if(args.length == 2) {
			test.showProductsOnAPage(args);
		}
		else {
			System.out.println("Error message: input is not right. There should be at least one argument and no more than 2 arguments.");
		}
	}

}
