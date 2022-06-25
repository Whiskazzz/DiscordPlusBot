package model;


import javax.persistence.*;

@Entity
@Table(name = "Customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    String name;

    @Column
    Long plusCount;

    @Column
    String tag;

    @Column
    Long discordId;

    public Customer() {
    }

    public Customer(String name, Long plusCount, String tag, Long discordId) {
        this.name = name;
        this.plusCount = plusCount;
        this.tag = tag;
        this.discordId = discordId;
    }

    public Customer(Long id, String name, Long plusCount, String tag, Long discordId) {
        this.id = id;
        this.name = name;
        this.plusCount = plusCount;
        this.tag = tag;
        this.discordId = discordId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPlusCount() {
        return plusCount;
    }

    public void setPlusCount(Long plusCount) {
        this.plusCount = plusCount;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String mention) {
        this.tag = mention;
    }

    public Long getDiscordId() {
        return discordId;
    }

    public void setDiscordId(Long discordId) {
        this.discordId = discordId;
    }
}
