package com.accenture.flowershop.be.entity.user;

import com.accenture.flowershop.be.entity.customer.Customer;
import com.accenture.flowershop.fe.enums.User.UserRole;
import org.jooq.impl.TableImpl;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name = "user")
public class User {

    /*Все пользователи имеют основные атрибуты для аутентификации:
        логин и пароль. Логин пользователя в системе должен быть уникален.
    Покупатели имеют дополнительные атрибуты: ФИО, адрес, телефон,
    денежный баланс (например, 550.05р.), скидка в % (целочисленная, например, 3%)
    */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "role", columnDefinition = "smallint")
    private short role;

    /*@Enumerated(EnumType.ORDINAL)
    @Column(name = "role", columnDefinition = "smallint")
    private UserRole role; //роль юзера(АДМИН, ПОКУПАТЕЛЬ)*/


    @OneToOne(mappedBy = "user")
    private Customer customer;

    public short getRole() {
        return role;
    }

    public void setRole(short role) {
        this.role = role;
    }

    public User() {
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;

    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /*public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }*/


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", customer=" + customer +
                '}';
    }
}
