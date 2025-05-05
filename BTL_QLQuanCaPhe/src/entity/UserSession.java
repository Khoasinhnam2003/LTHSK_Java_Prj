package entity;

public class UserSession {
    private static Employee currentEmployee;

    // Lấy đối tượng Employee hiện tại
    public static Employee getCurrentEmployee() {
        return currentEmployee;
    }

    // Đặt đối tượng Employee mới
    public static void setCurrentEmployee(Employee employee) {
        currentEmployee = employee;
    }
}
