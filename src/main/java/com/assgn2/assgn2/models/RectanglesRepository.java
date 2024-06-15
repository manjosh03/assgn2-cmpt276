package com.assgn2.assgn2.models;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RectanglesRepository extends JpaRepository<Rectangles,Integer>{
    List<Rectangles> findByWidthAndHeight(int width, int height);
    List<Rectangles> findByWidth(int width);
    List<Rectangles> findByHeight(int height);
    List<Rectangles> findByName(String name);
    void deleteById(int rid);
}
