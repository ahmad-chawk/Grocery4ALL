import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.ArrayList;

public class Grocery4ALL extends JFrame{

    private ArrayList<Item> inventoryItems; //Array list of all items in invenntory
    private DefaultTableModel TableModel; //Used to control the tale of items

    // Declaration of all items viewed in the GUI, will be explained in detail below
    private JButton addItem, sellItem, restockItem;
    private JButton revenue, costs, profit, invValue , lastTranscations;
    private JPanel buttons,transcations, calculations1 , calculations2;
    private JTable inventory;
    private JScrollPane tableScroll;
    private JLabel transactionsTitle, CalculationsTitle, InventoryTitle;

    // Variables used in the calculations, intially zero
    private double totalRevenue = 0;
    private double totalCosts = 0;

    // Variables used to print transaction log
    private String transactionLog = "Previous Transactions: \n";
    private int counter = 0;  // counter to count the number of transacrtion done
    
    public Grocery4ALL(){

        // Setting defaults of the GUI
        setSize(650,550); // GUI size
        setLayout(new BorderLayout()); // GUI layout
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // GUI closeing implemantation
        setTitle("Grocery4ALL Booking System"); // Window Title
        setLocationRelativeTo(null); // Open GUI in midle of screen
        setResizable(false); // Do not allow user to change size

        //Set the column titles of the tables
        String[] columnNames = {"ID", "Name", "Quantity", "Purchase Price", "Sell Price"};

        //Set the cells of teh table not editable 
        TableModel = new DefaultTableModel(columnNames, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }  
        };
        
        inventory = createTable(TableModel); // create the table from the function explained below
        tableScroll = new JScrollPane(inventory); // create a table scroll pane for when the items number increases

        InitializeItems(); // Initialze the 10 initilal items
        InitializeButtons(); // Initialze the buttons for transcations and calcualtions
        InitializePannels(); // Initialze the pannels including the buttons
        RefreshItems(); // Refrush the items to add them to table
        
        // GUI is splited into two part
        add(buttons,BorderLayout.NORTH); //North part including the buttons
        add(tableScroll,BorderLayout.CENTER); // Center including the table

        // Add functionality to all the buttons, each with its own function
        addItem.addActionListener(e -> {AddItem();});
        sellItem.addActionListener(e -> {SellItem();});
        restockItem.addActionListener(e -> {RestockItem();});
        revenue.addActionListener(e -> {TotalRevenue();});
        costs.addActionListener(e -> {TotalCosts();;});
        profit.addActionListener(e -> {TotalProfit();});
        invValue.addActionListener(e -> {InventoryValue();});
        lastTranscations.addActionListener(e -> {TransactionLog();});
        
    }

    private void InitializeItems(){
        // Initialze 10 random items into an arraylist
        inventoryItems = new ArrayList<>();
        inventoryItems.add(new Item("1", "Milk", 30, 2.0, 3.0));
        inventoryItems.add(new Item("2", "Eggs", 10, 1.5, 2.5));
        inventoryItems.add(new Item("3", "Cheese", 40, 3.0, 5.0));
        inventoryItems.add(new Item("4", "Butter", 25, 2.5, 4.0));
        inventoryItems.add(new Item("5", "Bread", 60, 1.2, 2.0));
        inventoryItems.add(new Item("6", "Apples", 150, 1.0, 1.6));
        inventoryItems.add(new Item("7", "Rice", 30, 0.6, 1.3));
        inventoryItems.add(new Item("8", "Tomatoes", 70, 1.0, 1.6));
        inventoryItems.add(new Item("9", "Chicken", 15, 2.0, 3.6));
        inventoryItems.add(new Item("10", "Bananas", 60, 0.6, 1.0));
    }

    private void InitializeButtons(){
        // Intialize the styled buttons with their styling defined in the createButtons function
        // Each buttons has its own titles 
        addItem = createButtons("Add Item");
        sellItem = createButtons("Sell Item");
        restockItem = createButtons("Restock Item");
        revenue = createButtons("Show Revenue");
        costs = createButtons("Show Costs");
        profit = createButtons("Show Profit");
        invValue = createButtons("Show Inventory Value");
        lastTranscations = createButtons("Show Last Transactions");
    }

    private void InitializePannels(){
        // Define the control panel called buttons
        buttons = new JPanel();
        // Panel will be divided into 6 rows (Title, transcation buttons, Title, cacluation buttons, Title)
        buttons.setLayout(new GridLayout(6,1));

        // Create three pannels, one for transcation buttons and two for calculation buttons.
        transcations = createPannel();
        calculations1 = createPannel();
        calculations2 = createPannel();
        
        // Create the three styled titles from the title creation function
        transactionsTitle = createTitle("Transactions");
        CalculationsTitle = createTitle("Calculations");
        InventoryTitle = createTitle("Inventory");

        // Add buttons to the transcation panel
        transcations.add(addItem);
        transcations.add(sellItem);
        transcations.add(restockItem);

        // Add buttons to the calcualtion panels
        calculations1.add(revenue);
        calculations1.add(invValue);
        calculations1.add(costs);
        calculations2.add(profit);
        calculations2.add(lastTranscations); 

        // Add all elements to the control panel
        buttons.add(transactionsTitle);
        buttons.add(transcations);
        buttons.add(CalculationsTitle);
        buttons.add(calculations1);
        buttons.add(calculations2);
        buttons.add(InventoryTitle);
    }

    // Function to create styled buttons
    private JButton createButtons(String title){
        JButton button = new JButton(title); // create a button with the title taken when function is called
        button.setBackground(Color.GRAY); // set the color of the button
        button.setForeground(Color.white); // set the color of the text on the button
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15)); // Create nargin around the text in the button
        button.setFont(new Font("Arial", Font.BOLD, 16)); // Choose font and font size of text 
        return button; // return the button styled
    }

    // Function to create panels
    private JPanel createPannel(){
        JPanel panel = new JPanel(); // create a new oannel
        panel.setLayout(new FlowLayout()); // set the layout of the panel to flowlayout, item will be added in addition order
        return panel; // return the panel
    }

    // Function to create items table
    private JTable createTable(DefaultTableModel TableModel){
        JTable table = new JTable(TableModel); // create a table from table model used to control the tabel
        table.setFillsViewportHeight(true); // set the table height always enough to fill the view
        table.setFont(new Font("Arial", Font.PLAIN, 14));// choose font type and size of text in table cells
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14)); // choose font type and size of text in coulmn titles
        table.setRowHeight(25); // set the row heigh of each row of the table
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer(); // create a TableCellRenderer to center the text in the cells
        centerRenderer.setHorizontalAlignment(JLabel.CENTER); // set the horizantal allignment to center
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer); // change all text allignment in all cells to center
        }

        return table; // retun the styled cell
    }

    // Function to create styled title
    private JLabel createTitle(String label){
        JLabel title = new JLabel(label); // Create a label with the text provided
        title.setFont(new Font("Arial", Font.BOLD, 25)); // set the font type type an d size of the title
        title.setHorizontalAlignment(SwingConstants.CENTER); // center the text in the midle of the gui
        return title; // return the styled title
    }

    // Function to add items to table once created
    private void RefreshItems(){
        TableModel.setRowCount(0); // empty the table to fill it with all items
        for (Item item : inventoryItems) { // iterate through all the declared items
            TableModel.addRow(new Object[]{ // add an item with all its details (id,name,quantity,purchaseprice,sellprice)
                    item.Id(),
                    item.Name(),
                    item.Quantity(),
                    item.PurchasePrice(),
                    item.SellPrice()
            });
        }
    }

    // Function to search items by their id to sell or restock them
    private Item searchItemById(String id) {
        for (Item item : inventoryItems) { // iterate through all decalred items
            if (item.Id().equals(id)) { // check if the itemid equal the id we are searching form
                return item; // return th eitem when true
            }
        }
        return null; // return null if entered id is not available
    }

    
    // Function to add items to the inventory
    private void AddItem(){
        String itemId = JOptionPane.showInputDialog("Enter item ID:"); // Ask user to enter item id
        if (itemId == null) { // Check for cancel pressed
            return;
        }
        if (searchItemById(itemId) != null){ // check if id is not in use
            JOptionPane.showMessageDialog(this, "ID already in use!");// show message and return
            return;
        } else if(!isInteger(itemId)){ // check if id is not a number
            JOptionPane.showMessageDialog(this, "ID should be a whole number");// show message and return
            return;
        } else{
            String itemName = JOptionPane.showInputDialog("Enter item Name:"); // Ask user to enter item name
            if (itemName == null) { // Check for cancel pressed
                return;
            }
            String itemQuantity = JOptionPane.showInputDialog("Enter Quantity:"); // Ask user to enter item quantity
            if (itemQuantity == null) { // Check for cancel pressed
                return;
            }
            if (isInteger(itemQuantity)) { // Check the quantity is an integer
                int intItemQuant = Integer.parseInt(itemQuantity); // change quantity to int to be used for calcualtions
                String itemPurchasePrice = JOptionPane.showInputDialog("Enter Purchasing Price:"); // Ask user to enter item purchase price
                if (itemPurchasePrice == null) { // Check for cancel pressed
                    return;
                }
                if (!isDouble(itemPurchasePrice)) {// return if price is not number
                    JOptionPane.showMessageDialog(this, "Purchase price should be a number");// show message and return
                    return;
                }
                String itemSellingPrice = (JOptionPane.showInputDialog("Enter Selling Price:")); // Ask user to enter item selling price
                if (itemSellingPrice == null) { // Check for cancel pressed
                    return;
                }
                if (!isDouble(itemSellingPrice)) {// return if price is not number
                    JOptionPane.showMessageDialog(this, "Selling price should be a number"); // show message and return
                    return;
                }
                // add the item with all info taken from user
                Item item = new Item(itemId, itemName, intItemQuant, Double.parseDouble(itemPurchasePrice), Double.parseDouble(itemSellingPrice));
                inventoryItems.add(item);
                // refresh items add new items to inventory
                RefreshItems();
                totalCosts += intItemQuant * Double.parseDouble(itemPurchasePrice); // add the cost of this item to the total cost of the inventory
                counter++; // counter to count the number of transacrtion done is added 1
                transactionLog = transactionLog + counter + ". "  + ("Added item: " + itemName + ", Quantity: " + intItemQuant + "\n"); // Log the transaction to previous transactions
            }else{
                JOptionPane.showMessageDialog(this, "Quantity should be a whole number"); // return if quantity is not number
                return;
            }
        }
    }
    // Function to sell item from inventory
    private void SellItem(){
        String id = JOptionPane.showInputDialog("Enter item ID to Sell:");// Ask user to enter item id
        if (id == null) { // Check for cancel pressed
            return;
        }
        Item item = searchItemById(id); // search if item with this id is avaialble
        if (item != null) {
            String quantity = (JOptionPane.showInputDialog("Enter Quantity to Sell:"));// Ask user to enter item quantity
            if (quantity == null) { // Check for cancel pressed
                return;
            }
            if (!quantity.isEmpty() && isInteger(quantity) && quantity!= null &&item.Quantity() >= Integer.parseInt(quantity)){
                item.setQuantity(item.Quantity() - Integer.parseInt(quantity)); // set the item's quantity to knew quantity
                if (item.Quantity() == 0) { // Check if new quantity is zero and ask user what to do
                    // Confirmation dialog to get user response
                    int n = JOptionPane.showConfirmDialog(this, "The quantity of the item is now 0, would you like to remove it from the inventory?","Remove Item?" ,JOptionPane.YES_NO_OPTION);
                    if (n == JOptionPane.YES_OPTION){
                        inventoryItems.remove(item); // remove item from inventory if user approves
                    }
                }
                RefreshItems(); // reload items with new details
                totalRevenue += Integer.parseInt(quantity) * item.SellPrice(); // increase the revenue by the revenue of this sell transcation
                counter++; // counter to count the number of transacrtion done is added 1
                transactionLog = transactionLog + counter + ". "  + ("Sold item: " + item.Name() + ", Quantity: " + quantity+"\n"); // Log the transaction to previous transactions
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Item Quantity!");// return if quantity is not number or not avaialbe
                return;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid item ID!"); // return if id is not valid
            return;
        }
    }

    // Function to restock items
    private void RestockItem(){
        String id = JOptionPane.showInputDialog("Enter item ID to Restock:");// Ask user to enter item id
        if (id == null) { // Check for cancel pressed
            return;
        }
        Item item = searchItemById(id); // earch if item with this id is avaialble
        if (item != null) {
            String quantity = (JOptionPane.showInputDialog("Enter Quantity to Restock:"));// Ask user to enter item quantity
            if (quantity == null) { // Check for cancel pressed
                return;
            }
            if (quantity.isEmpty() || !isInteger(quantity)) {
                JOptionPane.showMessageDialog(this, "Invalid Item Quantity!");// return if quantity is not number 
                return;
            }
            item.setQuantity(item.Quantity() + Integer.parseInt(quantity)); // set the new quantity of the item
            RefreshItems(); // reload items with new details
            totalCosts += Integer.parseInt(quantity) * item.PurchasePrice(); // add the cost of restocking to total costs
            counter++; // counter to count the number of transacrtion done is added 1
            transactionLog =  transactionLog + counter + ". " + ("Restocked item: " + item.Name() + ", Quantity: " + Integer.parseInt(quantity)+"\n");// Log the transaction to previous transactions
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Item ID!");// return if id is not valid
        }
    }

    // Function to show the total revenue to the user in a message dialog
    private void TotalRevenue(){
        JOptionPane.showMessageDialog(this, "Total revenue of inventory is: $" + (Math.round(totalRevenue * 100.0) / 100.0),"Total Revenue",JOptionPane.INFORMATION_MESSAGE);// print the message
        
    }
    // Function to show the total costs to the user in a message dialog
    private void TotalCosts(){
        JOptionPane.showMessageDialog(this, "Total cost of inventory is: $" + (Math.round(totalCosts * 100.0) / 100.0), "Total Cost",JOptionPane.INFORMATION_MESSAGE);// print the message
    }
    // Function to show the total profit to the user in a message dialog
    private void TotalProfit(){
        double totalProfit = totalRevenue - totalCosts; // total profit = revenue - costs
        JOptionPane.showMessageDialog(this, "Total profit of inventory is: $" + (Math.round(totalProfit * 100.0) / 100.0), "Total Profit",JOptionPane.INFORMATION_MESSAGE);// print the message
    }
    // Function to show the inventory value to the user in a message dialog
    private void InventoryValue(){
        double totalInventoryValue = 0;
        for (Item item : inventoryItems) {
            totalInventoryValue += item.Quantity() * item.PurchasePrice(); // Iterratte through all items and calcualte their value through the quantity*price
        }
        JOptionPane.showMessageDialog(this, "Total value of inventory is: $" + totalInventoryValue , "Inventory Value",JOptionPane.INFORMATION_MESSAGE); // print the message
    }
    // Function to show the Transaction Log to the user in a message dialog
    private void TransactionLog(){
        JOptionPane.showMessageDialog(this, transactionLog, "Transactions Log",JOptionPane.INFORMATION_MESSAGE);// print the message
    }

    // Function to check if input is intetger
    public static boolean isInteger(String str) {
        if (str == null || str.isEmpty()) { // Chcek if input is empty or null
            return false;
        }
        try {
            Integer.parseInt(str); // check if parsing to integer is succesful
            return true;
        } catch (NumberFormatException e) {// return false if input not integer
            return false;
        }
    }
    
    // Function to check if input is double
    public static boolean isDouble(String str) {// Chcek if input is empty or null
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(str); // check if parsing to double is succesful
            return true;
        } catch (NumberFormatException e) { // return false if input not double
            return false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Grocery4ALL window = new Grocery4ALL(); // Initialze the window
            window.setVisible(true); // View the GUI window
        });
    }
}

// Class to provide details and represent an item in the inventory
class Item{
    private String itemId; // idnetifier of the item
    private String itemName; // name of the item
    private int itemQuantity; // quantity of the item
    private double itemPurchasePrice; // buy price of the item
    private double itemSellingPrice; // sell price of the item

    // Constructor of the item class withh all parametere
    public Item(String itemId, String itemName, int itemQuantity, double itemPurchasePrice, double itemSellingPrice) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.itemPurchasePrice = itemPurchasePrice;
        this.itemSellingPrice = itemSellingPrice;
    } 

    // Function to return Id of item
    public String Id() {
        return itemId;
    }
    // Function to return Name of item
    public String Name() {
        return itemName;
    }
    // Function to return Quantity of item
    public int Quantity() {
        return itemQuantity;
    }
    // Function to return Purchase Price of item
    public double PurchasePrice() {
        return itemPurchasePrice;
    }
    // Function to return Sell Price of item
    public double SellPrice() {
        return itemSellingPrice;
    } 
    // Function to return Quantity of item
    public void setQuantity(int quantity) {
        this.itemQuantity = quantity;
    }
}
