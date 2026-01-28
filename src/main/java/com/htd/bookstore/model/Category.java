package com.htd.bookstore.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryId;

    private String name;

    @OneToMany(mappedBy = "category")
    private List<Book> books;
}
