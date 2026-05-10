package models;

/**
 * Person (Abstract base class).
 * OOP Concepts: Abstraction, Encapsulation, Inheritance (parent class)
 *
 * Both Landowner and Worker share Name, CNIC, Phone — so they go here.
 * Child classes inherit these fields and the getDetails() method.
 */
public abstract class Person {

    // ── Private fields (Encapsulation) ──────────────────────────────────────
    private String name;
    private String cnic;
    private String phone;

    // ── Constructor ─────────────────────────────────────────────────────────
    public Person(String name, String cnic, String phone) {
        this.name  = name;
        this.cnic  = cnic;
        this.phone = phone;
    }

    // ── Getters & Setters (Encapsulation — controlled access) ───────────────
    public String getName()              { return name;  }
    public void   setName(String name)   { this.name = name; }

    public String getCnic()              { return cnic;  }
    public void   setCnic(String cnic)   { this.cnic = cnic; }

    public String getPhone()             { return phone; }
    public void   setPhone(String phone) { this.phone = phone; }

    /**
     * Abstract method — every subclass MUST implement its own version.
     * OOP Concept: Abstraction
     */
    public abstract String getDetails();

    /**
     * toString — used when printing a Person object directly.
     */
    @Override
    public String toString() {
        return getDetails();
    }
}
