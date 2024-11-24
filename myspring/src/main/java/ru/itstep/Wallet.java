package ru.itstep;

import java.io.Serializable;

import org.hibernate.annotations.ColumnDefault;
import jakarta.persistence.*;

@Entity
@Table(name="Buy")  // Возможность назвать таблицу по-другому
public class Wallet implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue
    Long id;
    
    @Column(nullable=false)
    String purpose;
    
    @ColumnDefault("0")
    Double quantity;
    
    @ColumnDefault("21")
    @Column(nullable=false)
    Long userId;
    
    public Wallet() {
    	purpose = "lost";
    	quantity = 0.0;
    	userId = (long) 21;
    }
    
    public Wallet(String p, Double q, Long userId) {
    	purpose = p;
    	quantity = q;
    	this.userId = userId;
    }
    
    // Задание: проверить, что будет, если нет геттеров:
    // По логике таблицу он создаст, а insert - не сможет
    public String getPurpose() {
    	return purpose;
    }
    
    public void setPurpose(String purp) {
    	purpose = purp;
    }
    
    public Double getQuantity() {
    	return quantity;
    }
    
    public void setQuantity(Double q) {
    	quantity = q;
    }
    
    public Long getUserId() {
    	return userId;
    }
    public void setUserId(Long userId) {
    	this.userId = userId;
    }
    
    
    public boolean isIncome() {
    	return quantity > 0;
    }
    
    public String toString() {
    	return purpose + ": $" + quantity + "";
    }
}

