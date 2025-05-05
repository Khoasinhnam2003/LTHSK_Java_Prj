package entity;

import java.util.Date;

public class Promotion {
    private String promotionId;
    private String promotionName;
    private double discount;
    private Date startDate;
    private Date endDate;

    public Promotion() {
        // Default constructor
    }

    public Promotion(String promotionId) {
        this.promotionId = promotionId;
    }

    public Promotion(String promotionId, String promotionName, double discount, Date startDate, Date endDate) {
        this.promotionId = promotionId;
        this.promotionName = promotionName;
        this.discount = discount;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    
    public Object[] toRow() {
        return new Object[] {
            promotionId,
            promotionName,
            discount,
            startDate,
            endDate
        };
    }

    public String getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Promotion [promotionId=" + promotionId + ", promotionName=" + promotionName + ", discount=" + discount
                + ", startDate=" + startDate + ", endDate=" + endDate + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((promotionId == null) ? 0 : promotionId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Promotion other = (Promotion) obj;
        if (promotionId == null) {
            if (other.promotionId != null)
                return false;
        } else if (!promotionId.equals(other.promotionId))
            return false;
        return true;
    }
}