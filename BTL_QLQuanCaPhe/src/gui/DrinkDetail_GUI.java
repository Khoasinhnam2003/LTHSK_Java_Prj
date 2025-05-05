package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import dao.DrinkDetail_DAO;

public class DrinkDetail_GUI extends JFrame {
    private JTextField txtDrinkId, txtIngredientId, txtQuantity;
    private JButton btnAdd, btnUpdate, btnDelete, btnSearch;
    private JTable tblDrinkDetails;
    private DefaultTableModel model;

    public DrinkDetail_GUI() {
        setTitle("Quản lý Chi tiết Đồ uống");
        setLayout(new BorderLayout());

        // Panel nhập liệu
        JPanel pnlInput = new JPanel(new GridLayout(4, 2, 5, 5));
        pnlInput.add(new JLabel("Drink ID:"));
        txtDrinkId = new JTextField();
        pnlInput.add(txtDrinkId);

        pnlInput.add(new JLabel("Ingredient ID:"));
        txtIngredientId = new JTextField();
        pnlInput.add(txtIngredientId);

        pnlInput.add(new JLabel("Quantity:"));
        txtQuantity = new JTextField();
        pnlInput.add(txtQuantity);

        add(pnlInput, BorderLayout.NORTH);

        // Bảng hiển thị dữ liệu
        String[] columns = {"Drink ID", "Ingredient ID", "Quantity"};
        model = new DefaultTableModel(columns, 0);
        tblDrinkDetails = new JTable(model);
        add(new JScrollPane(tblDrinkDetails), BorderLayout.CENTER);

        // Panel nút chức năng
        JPanel pnlButtons = new JPanel();
        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnSearch = new JButton("Search");
        
        pnlButtons.add(btnAdd);
        pnlButtons.add(btnUpdate);
        pnlButtons.add(btnDelete);
        pnlButtons.add(btnSearch);
        add(pnlButtons, BorderLayout.SOUTH);

        // Sự kiện cho nút
//        btnAdd.addActionListener(e -> addDrinkDetail());
//        btnUpdate.addActionListener(e -> updateDrinkDetail());
//        btnDelete.addActionListener(e -> deleteDrinkDetail());
//        btnSearch.addActionListener(e -> searchDrinkDetails());

//        loadDrinkDetails();
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

//    private void loadDrinkDetails() {
//        ArrayList<DrinkDetail> details = DrinkDetail_DAO.getAllDrinkDetails();
//        model.setRowCount(0);
//        for (DrinkDetail detail : details) {
//            model.addRow(new Object[]{detail.getDrink().getDrinkId(), detail.getIngredient().getIngredientId(), detail.getQuantity()});
//        }
//    }

//    private void addDrinkDetail() {
//        String drinkId = txtDrinkId.getText();
//        String ingredientId = txtIngredientId.getText();
//        double quantity = Double.parseDouble(txtQuantity.getText());
//
//        DrinkDetail detail = new DrinkDetail(new Ingredient(ingredientId), new Drink(drinkId), quantity);
//        new DrinkDetail_DAO().addDrinkDetail(detail);
//        loadDrinkDetails();
//    }

//    private void updateDrinkDetail() {
//        String drinkId = txtDrinkId.getText();
//        String ingredientId = txtIngredientId.getText();
//        double quantity = Double.parseDouble(txtQuantity.getText());
//
//        DrinkDetail detail = new DrinkDetail(new Ingredient(ingredientId), new Drink(drinkId), quantity);
//        new DrinkDetail_DAO().updateDrinkDetail(detail);
//        loadDrinkDetails();
//    }

    private void deleteDrinkDetail() {
        String drinkId = txtDrinkId.getText();
        String ingredientId = txtIngredientId.getText();

        new DrinkDetail_DAO().deleteDrinkDetail(ingredientId, drinkId);
//        loadDrinkDetails();
    }

//    private void searchDrinkDetails() {
//        String drinkId = txtDrinkId.getText();
//        ArrayList<DrinkDetail> details = new DrinkDetail_DAO().findDrinkDetailsByDrinkId(drinkId);
//        model.setRowCount(0);
//        for (DrinkDetail detail : details) {
//            model.addRow(new Object[]{detail.getDrink().getDrinkId(), detail.getIngredient().getIngredientId(), detail.getQuantity()});
//        }
//    }

    public static void main(String[] args) {
        new DrinkDetail_GUI();
    }
}
